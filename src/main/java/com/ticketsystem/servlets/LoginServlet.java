package com.ticketsystem.servlets;

import com.ticketsystem.dao.UsuarioDAO;
import com.ticketsystem.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO;

    @Override
    public void init() throws ServletException {
        usuarioDAO = new UsuarioDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            // Redirige al dashboard según rol
            Usuario u = (Usuario) session.getAttribute("user");
            String rol = u.getRol().toLowerCase();
            if ("admin".equals(rol)) {
                response.sendRedirect(request.getContextPath() + "/views/adminDashboard.jsp");
            } else if ("tecnico".equals(rol)) {
                response.sendRedirect(request.getContextPath() + "/views/tecnicoDashboard.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "/views/login.jsp");
            }
            return;
        }

        String logoutParam = request.getParameter("logout");
        if ("success".equals(logoutParam)) {
            request.setAttribute("mensaje", "Sesión cerrada exitosamente");
        }

        request.getRequestDispatcher("/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");
        String rememberMe = request.getParameter("rememberMe");

        // Validaciones básicas
        if (correo == null || correo.isEmpty() || contrasena == null || contrasena.isEmpty()) {
            request.setAttribute("error", "Todos los campos son obligatorios");
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
            return;
        }

        // Autenticación
        Usuario authenticatedUser = usuarioDAO.authenticate(correo, contrasena);

        if (authenticatedUser != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", authenticatedUser);
            session.setAttribute("email", authenticatedUser.getCorreo());
            session.setAttribute("name", authenticatedUser.getNombre());
            session.setAttribute("role", authenticatedUser.getRol());
            session.setAttribute("userId", authenticatedUser.getIdUsuario());

            // Configurar tiempo de sesión
            if ("on".equals(rememberMe)) {
                session.setMaxInactiveInterval(7 * 24 * 60 * 60); // 7 días
            } else {
                session.setMaxInactiveInterval(30 * 60); // 30 minutos
            }

            // Redirige según rol
            String rol = authenticatedUser.getRol().toLowerCase();
            if ("admin".equals(rol)) {
                response.sendRedirect(request.getContextPath() + "/views/adminDashboard.jsp");
            } else if ("tecnico".equals(rol)) {
                response.sendRedirect(request.getContextPath() + "/views/tecnicoDashboard.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "/views/login.jsp");
            }

        } else {
            request.setAttribute("error", "Correo electrónico o contraseña incorrectos");
            request.setAttribute("correo", correo);
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
        }
    }
}
