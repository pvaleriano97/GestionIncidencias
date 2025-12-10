/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ticketsystem.servlets;

import com.ticketsystem.dao.UsuarioDAO;
import com.ticketsystem.model.Usuario;
import com.ticketsystem.util.TwoFactorAuth;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ROSA SANDOVAL
 */
@WebServlet(name = "RegenerarQRServlet", urlPatterns = {"/RegenerarQRServlet"})
public class RegenerarQRServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
    Usuario u = (Usuario) session.getAttribute("usuarioPendiente");

    if (u == null) {
        response.sendRedirect("views/login.jsp");
        return;
    }

    // generar nuevo secret
    String newSecret = TwoFactorAuth.generarSecret();

    // guardar el nuevo secret del usuario (por correo)
    boolean ok = usuarioDAO.guardarSecret2FA(u.getCorreo(), newSecret);

    if (!ok) {
        request.setAttribute("error", "No se pudo generar nuevo código 2FA. Intente más tarde.");
        request.getRequestDispatcher("views/login.jsp").forward(request, response);
        return;
    }

    // desactivar 2FA por id (o usa desactivar2FAPorCorreo si prefieres)
    usuarioDAO.desactivar2FA(u.getIdUsuario());

    // actualizar objeto en sesión para reflejar cambio
    u.setSecret_key(newSecret);
    u.setTwofa_enabled(0);

    // generar QR con el nuevo secret
    String qrUrl = TwoFactorAuth.generarQR(u.getCorreo(), "GestionIncidencias", newSecret);

    request.setAttribute("mostrarQR", true);
    request.setAttribute("qrUrl", qrUrl);
    request.setAttribute("show2FA", true);
    request.setAttribute("nombre", u.getNombre());

    request.getRequestDispatcher("views/login.jsp").forward(request, response);
}}