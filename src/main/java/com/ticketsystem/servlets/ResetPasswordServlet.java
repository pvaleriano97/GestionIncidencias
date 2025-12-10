package com.ticketsystem.servlets;

import com.ticketsystem.dao.UsuarioDAO;
import com.ticketsystem.model.Usuario;
import com.ticketsystem.util.TwoFactorAuth;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/ResetPasswordServlet")
public class ResetPasswordServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String token = request.getParameter("token");

        if (token == null || token.isEmpty()) {
            request.setAttribute("error", "Token inválido o expirado.");
            request.getRequestDispatcher("views/reset_password.jsp").forward(request, response);
            return;
        }

        request.setAttribute("token", token);
        request.getRequestDispatcher("views/reset_password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String password = request.getParameter("password");
        String confirmar = request.getParameter("confirmar");
        String token = request.getParameter("token");

        if (password == null || confirmar == null || token == null ||
            password.isEmpty() || confirmar.isEmpty() || token.isEmpty()) {

            request.setAttribute("error", "Todos los campos son obligatorios.");
            request.setAttribute("token", token);
            request.getRequestDispatcher("views/reset_password.jsp").forward(request, response);
            return;
        }

        if (!password.equals(confirmar)) {
            request.setAttribute("error", "Las contraseñas no coinciden.");
            request.setAttribute("token", token);
            request.getRequestDispatcher("views/reset_password.jsp").forward(request, response);
            return;
        }

        try {
            boolean actualizado = usuarioDAO.actualizarPasswordPorToken(token, password);

            if (actualizado) {
                // 🔹 Obtener usuario por token
                Usuario usuario = usuarioDAO.buscarPorToken(token);

                if (usuario != null) {
                    // 🔹 Generar secret_key si no tiene
                    if (usuario.getSecret_key() == null || usuario.getSecret_key().isEmpty()) {
                        usuario.setSecret_key(TwoFactorAuth.generarSecret());
                    }

                    // 🔹 Activar 2FA
                    usuario.setTwofa_enabled(1);

                    // 🔹 Guardar secret_key y 2FA en DB
                    usuarioDAO.actualizarSecretKey(usuario);

                    // 🔹 Generar QR para autenticador
                    String qrUrl = TwoFactorAuth.generarQR(
                            usuario.getCorreo(),
                            "GestionIncidencias",
                            usuario.getSecret_key()
                    );

                    request.setAttribute("qrUrl", qrUrl);
                    request.setAttribute("show2FA", true);
                    request.setAttribute("nombre", usuario.getNombre());
                }

                request.setAttribute("mensaje", "¡Contraseña actualizada correctamente!");
            } else {
                request.setAttribute("error", "El token es inválido o ya fue usado.");
            }

            request.getRequestDispatcher("views/reset_password.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Ocurrió un error inesperado.");
            request.getRequestDispatcher("views/reset_password.jsp").forward(request, response);
        }
    }
}
