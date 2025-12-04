package com.ticketsystem.controller;

import static com.sun.jndi.toolkit.dir.DirSearch.search;
import com.ticketsystem.dao.SolicitudRepuestoDAO;
import com.ticketsystem.model.SolicitudRepuesto;
import com.ticketsystem.util.DatabaseConnection;
import com.ticketsystem.util.DatabaseConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/SolicitudRepuestoServlet")
public class SolicitudRepuestoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try (Connection conn = DatabaseConnection.getConnection()) {

            SolicitudRepuestoDAO dao = new SolicitudRepuestoDAO(conn);

            // -----------------------------------------------------
            // CARGAR FORMULARIO PARA EDITAR
            // -----------------------------------------------------
            if ("editarForm".equals(action)) {
                int id = Integer.parseInt(request.getParameter("idSolicitud"));
                SolicitudRepuesto s = dao.obtenerPorId(id);

                request.setAttribute("solicitudEdit", s);
            }

            // -----------------------------------------------------
            /// PAGINACIÓN
        // -----------------------------------------------------
        int registrosPorPagina = 5; // solo 5 registros por página
        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        int inicio = (page - 1) * registrosPorPagina;
String search = request.getParameter("search");
if (search == null) {
    search = ""; // valor por defecto si no hay búsqueda
}
        // Contar total de registros para la paginación
        int totalRegistros = dao.contarSolicitudes(search);
        int totalPaginas = (int) Math.ceil((double) totalRegistros / registrosPorPagina);

        // Listar solo 5 registros según la página actual
        request.setAttribute("listaSolicitudes", dao.listarPaginado(search, inicio, registrosPorPagina));
        request.setAttribute("page", page);
        request.setAttribute("totalPaginas", totalPaginas);
        request.setAttribute("search", search);

        // Listas de incidencias y repuestos
        request.setAttribute("listaIncidencias", dao.listarIncidencias());
        request.setAttribute("listaRepuestos", dao.listarRepuestos());

        // Redirige al JSP
        request.getRequestDispatcher("/views/solicitud_repuesto.jsp").forward(request, response);

    } catch (Exception e) {
        e.printStackTrace();
        throw new ServletException(e);
    }
}

    // ==========================================================
    // MÉTODOS POST (INSERTAR / EDITAR / ELIMINAR)
    // ==========================================================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try (Connection conn = DatabaseConnection.getConnection()) {

            SolicitudRepuestoDAO dao = new SolicitudRepuestoDAO(conn);

            switch (action) {

                case "insertar":
                    dao.insertar(request);
                    break;

                case "editar":
                    dao.editar(request);
                    break;

                case "eliminar":
                    int id = Integer.parseInt(request.getParameter("idSolicitud"));
                    dao.eliminar(id);
                    break;
            }

            // Luego de operar, regresar al GET (lista actualizada)
            response.sendRedirect("SolicitudRepuestoServlet");

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}
