//package com.ticketsystem.servlets;
//
//import com.ticketsystem.dao.IncidenciaDAO;
//import com.ticketsystem.model.Incidencia;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.*;
//import java.io.IOException;
//import java.util.List;
//
//@WebServlet("/mis-tickets")
//public class MisTicketsServlet extends HttpServlet {
//
//    private IncidenciaDAO ticketDAO;
//
//    @Override
//    public void init() throws ServletException {
//        ticketDAO = new IncidenciaDAO();
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        HttpSession session = request.getSession(false);
//
//        // ✅ Verifica si el usuario está autenticado
//        if (session == null || session.getAttribute("userId") == null) {
//            response.sendRedirect(request.getContextPath() + "/login");
//            return;
//        }
//
//        try {
//            int usuarioId = (Integer) session.getAttribute("userId");
//
//            // 🔍 Filtros opcionales (si el JSP los tiene)
//            String filtroEstado = request.getParameter("estado");
//            String filtroPrioridad = request.getParameter("prioridad");
//            String filtroCategoria = request.getParameter("categoria");
//
//            // 🔹 Obtener las incidencias de ese usuario
//            List<Incidencia> tickets = ticketDAO.obtenerIncidenciasPorUsuario(usuarioId);
//
//            // 🔹 Aplicar filtros si los hay
//            if (filtroEstado != null && !filtroEstado.equalsIgnoreCase("todos")) {
//                tickets.removeIf(ticket -> !ticket.getEstado().equalsIgnoreCase(filtroEstado));
//            }
//
//            if (filtroPrioridad != null && !filtroPrioridad.equalsIgnoreCase("todas")) {
//                tickets.removeIf(ticket -> !ticket.getPrioridad().equalsIgnoreCase(filtroPrioridad));
//            }
//
//            if (filtroCategoria != null && !filtroCategoria.equalsIgnoreCase("todas")) {
//                tickets.removeIf(ticket -> !ticket.getCategoria().equalsIgnoreCase(filtroCategoria));
//            }
//
//            // 🔹 Pasar datos a la vista JSP
//            request.setAttribute("tickets", tickets);
//            request.setAttribute("filtroEstado", filtroEstado);
//            request.setAttribute("filtroPrioridad", filtroPrioridad);
//            request.setAttribute("filtroCategoria", filtroCategoria);
//
//            // ✅ Mostrar mensaje de éxito si existe en sesión
//            String mensajeExito = (String) session.getAttribute("mensajeExito");
//            if (mensajeExito != null) {
//                request.setAttribute("mensajeExito", mensajeExito);
//                session.removeAttribute("mensajeExito");
//            }
//
//            // 🔹 Redirigir al JSP correcto
//            request.getRequestDispatcher("/views/mis-tickets.jsp").forward(request, response);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            request.setAttribute("error", "Error al cargar los tickets: " + e.getMessage());
//            request.getRequestDispatcher("/views/mis-tickets.jsp").forward(request, response);
//        }
//    }
//}
