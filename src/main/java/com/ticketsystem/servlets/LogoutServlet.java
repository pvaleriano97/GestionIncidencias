package com.ticketsystem.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // ⚡ Cierra la sesión
        }

        // Redirige al login con mensaje de éxito
        response.sendRedirect(request.getContextPath() + "/LoginServlet?logout=success");
    }
}
