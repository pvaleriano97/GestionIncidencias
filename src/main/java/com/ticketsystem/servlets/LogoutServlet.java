package com.ticketsystem.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("=== LOGOUT SERVLET EJECUTADO ===");

        // Obtener sesión sin crear una nueva
        HttpSession session = request.getSession(false);

        if (session != null) {
            String userEmail = (String) session.getAttribute("email");
            System.out.println("Cerrando sesión de: " + userEmail);

            // Invalidar sesión
            session.invalidate();
            System.out.println("✅ Sesión invalidada correctamente.");
        } else {
            System.out.println("ℹ️ No había sesión activa.");
        }

        // Redirigir correctamente a login.jsp dentro de /views/
        String redirectURL = request.getContextPath() + "/views/login.jsp?logout=success";
        System.out.println("➡️ Redirigiendo a: " + redirectURL);

        response.sendRedirect(redirectURL);
    }
}
