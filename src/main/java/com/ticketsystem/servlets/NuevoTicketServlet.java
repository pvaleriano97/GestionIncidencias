package com.ticketsystem.servlets;

import com.ticketsystem.dao.TicketDAO;
import com.ticketsystem.model.Ticket;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class NuevoTicketServlet extends HttpServlet {
    
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
        
        // Mostrar el formulario de nuevo ticket
        request.getRequestDispatcher("/views/nuevo-ticket.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        try {
            // Obtener parámetros del formulario
            String titulo = request.getParameter("titulo");
            String descripcion = request.getParameter("descripcion");
            String categoria = request.getParameter("categoria");
            String prioridad = request.getParameter("prioridad");
            String empresa = request.getParameter("empresa");
            String departamento = request.getParameter("departamento");
            String fechaInicioStr = request.getParameter("fechaInicio");
            String fechaFinStr = request.getParameter("fechaFin");
            
            // Obtener información del usuario de la sesión
            int usuarioId = (Integer) session.getAttribute("userId");
            String usuarioNombre = (String) session.getAttribute("name");
            String usuarioEmail = (String) session.getAttribute("email");
            
            // Convertir fechas
            LocalDate fechaInicio = LocalDate.parse(fechaInicioStr);
            LocalDate fechaFin = fechaFinStr != null && !fechaFinStr.isEmpty() ? 
                                LocalDate.parse(fechaFinStr) : null;
            
            // Crear objeto Ticket
            Ticket ticket = new Ticket();
            ticket.setTitulo(titulo);
            ticket.setDescripcion(descripcion);
            ticket.setCategoria(categoria);
            ticket.setPrioridad(prioridad);
            ticket.setEmpresa(empresa);
            ticket.setDepartamento(departamento);
            ticket.setFechaInicio(fechaInicio);
            ticket.setFechaFin(fechaFin);
            ticket.setUsuarioId(usuarioId);
            ticket.setUsuarioNombre(usuarioNombre);
            ticket.setUsuarioEmail(usuarioEmail);
            ticket.setEstado("ABIERTO"); // Estado por defecto
            
            // Guardar en base de datos
            boolean creado = ticketDAO.crearTicket(ticket);
            
            if (creado) {
                session.setAttribute("mensajeExito", "Ticket creado exitosamente");
                response.sendRedirect(request.getContextPath() + "/mis-tickets");
            } else {
                request.setAttribute("error", "Error al crear el ticket. Intente nuevamente.");
                request.getRequestDispatcher("/views/nuevo-ticket.jsp").forward(request, response);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error en el formulario: " + e.getMessage());
            request.getRequestDispatcher("/views/nuevo-ticket.jsp").forward(request, response);
        }
    }
}