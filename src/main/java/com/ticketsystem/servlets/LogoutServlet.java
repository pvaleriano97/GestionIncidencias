package com.ticketsystem.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("=== LOGOUT SERVLET EJECUTADO ===");
        
        // Obtener la sesión actual (sin crear una nueva)
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            // Log de información antes de invalidar
            String userEmail = (String) session.getAttribute("email");
            System.out.println("Invalidando sesión para usuario: " + userEmail);
            
            // Invalidar la sesión - esto elimina todos los atributos
            session.invalidate();
            System.out.println("✅ Sesión invalidada exitosamente");
        } else {
            System.out.println("ℹ️ No había sesión activa para invalidar");
        }
        
        // Redirigir al login con mensaje de éxito
        String redirectURL = request.getContextPath() + "/login?logout=success";
        System.out.println("Redirigiendo a: " + redirectURL);
        response.sendRedirect(redirectURL);
    }
}