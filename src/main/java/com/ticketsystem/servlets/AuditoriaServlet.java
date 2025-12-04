package com.ticketsystem.servlets;

import com.ticketsystem.dao.AuditoriaDAO;
import com.ticketsystem.model.AuditoriaLogin;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.List;

@WebServlet("/AuditoriaServlet")
public class AuditoriaServlet extends HttpServlet {
    
    private AuditoriaDAO auditoriaDAO;
    
    @Override
    public void init() throws ServletException {
        auditoriaDAO = new AuditoriaDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Verificar que sea admin
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }
        
        String action = request.getParameter("action");
        
        if ("estadisticas".equals(action)) {
            List<Object[]> stats = auditoriaDAO.obtenerEstadisticas();
            request.setAttribute("estadisticas", stats);
            request.getRequestDispatcher("/views/auditoria_estadisticas.jsp").forward(request, response);
        } else {
            List<AuditoriaLogin> logs = auditoriaDAO.obtenerTodosLogs();
            request.setAttribute("logs", logs);
            request.getRequestDispatcher("/views/auditoria_logs.jsp").forward(request, response);
        }
    }
}