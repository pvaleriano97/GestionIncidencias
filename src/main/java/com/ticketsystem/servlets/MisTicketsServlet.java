package com.ticketsystem.servlets;

import com.ticketsystem.dao.TicketDAO;
import com.ticketsystem.model.Ticket;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class MisTicketsServlet extends HttpServlet {
    
    private TicketDAO ticketDAO;
    
    @Override
    public void init() throws ServletException {
        ticketDAO = new TicketDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        try {
            int usuarioId = (Integer) session.getAttribute("userId");
            
            // Obtener parámetros de filtro
            String filtroEstado = request.getParameter("estado");
            String filtroPrioridad = request.getParameter("prioridad");
            String filtroCategoria = request.getParameter("categoria");
            
            // Obtener tickets según los filtros
            List<Ticket> tickets = ticketDAO.obtenerTicketsPorUsuario(usuarioId);
            
            // Aplicar filtros si existen
            if (filtroEstado != null && !filtroEstado.equals("todos")) {
                tickets.removeIf(ticket -> !ticket.getEstado().equals(filtroEstado));
            }
            
            if (filtroPrioridad != null && !filtroPrioridad.equals("todas")) {
                tickets.removeIf(ticket -> !ticket.getPrioridad().equals(filtroPrioridad));
            }
            
            if (filtroCategoria != null && !filtroCategoria.equals("todas")) {
                tickets.removeIf(ticket -> !ticket.getCategoria().equals(filtroCategoria));
            }
            
            // Establecer atributos para la vista
            request.setAttribute("tickets", tickets);
            request.setAttribute("filtroEstado", filtroEstado);
            request.setAttribute("filtroPrioridad", filtroPrioridad);
            request.setAttribute("filtroCategoria", filtroCategoria);
            
            // Verificar si hay mensaje de éxito
            String mensajeExito = (String) session.getAttribute("mensajeExito");
            if (mensajeExito != null) {
                request.setAttribute("mensajeExito", mensajeExito);
                session.removeAttribute("mensajeExito"); // Limpiar después de usar
            }
            
            request.getRequestDispatcher("/views/mis-tickets.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al cargar los tickets: " + e.getMessage());
            request.getRequestDispatcher("/views/mis-tickets.jsp").forward(request, response);
        }
    }
}