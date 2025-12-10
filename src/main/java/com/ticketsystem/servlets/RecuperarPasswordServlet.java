package com.ticketsystem.servlets;

import com.ticketsystem.dao.UsuarioDAO;
import com.ticketsystem.model.Usuario;
import com.ticketsystem.util.EmailSender;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "RecuperarPasswordServlet", urlPatterns = {"/RecuperarPasswordServlet"})
public class RecuperarPasswordServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String correo = request.getParameter("correo");
        Usuario usuario = null;

        try {
            usuario = usuarioDAO.buscarPorCorreo(correo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (usuario == null) {
            request.setAttribute("error", "El correo no está registrado.");
            request.getRequestDispatcher("views/login.jsp").forward(request, response);
            return;
        }

        // Generar token de recuperación
        String token = UUID.randomUUID().toString();

        try {
            usuarioDAO.guardarTokenRecuperacion(correo, token);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", "No se pudo generar el token. Intenta más tarde.");
            request.getRequestDispatcher("views/login.jsp").forward(request, response);
            return;
        }

        // Enlace de recuperación (codificando el token)
        String link = request.getScheme() + "://" +
                      request.getServerName() + ":" +
                      request.getServerPort() +
                      request.getContextPath() +
                      "/ResetPasswordServlet?token=" + URLEncoder.encode(token, StandardCharsets.UTF_8.toString());

        System.out.println("Enlace de recuperación: " + link); // Para pruebas

        String cuerpo = "<h2>Recuperación de contraseña</h2>"
                      + "<p>Haz clic en el enlace para crear una nueva contraseña:</p>"
                      + "<a href='" + link + "'>Restablecer contraseña</a>"
                      + "<p>Este enlace expira en 30 minutos.</p>";

 try {
    EmailSender.enviar(
        correo,
        "Recuperar contraseña",
        cuerpo,
        true
    );
    request.setAttribute("mensaje", "Se envió un enlace de recuperación a tu correo.");
} catch (MessagingException ex) {
    ex.printStackTrace();
    request.setAttribute("error", "No se pudo enviar el correo. Intenta más tarde.");
}

request.getRequestDispatcher("views/login.jsp").forward(request, response);

}}
