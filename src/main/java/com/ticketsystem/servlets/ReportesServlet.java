package com.ticketsystem.servlets;

import com.ticketsystem.dao.IncidenciaDAO;
import com.ticketsystem.dao.TecnicoDAO;
import com.ticketsystem.model.Incidencia;
import com.ticketsystem.model.Tecnico;
import com.ticketsystem.export.ExportarExcel;
import com.ticketsystem.export.ExportarPDF;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet(name = "ReportesServlet", urlPatterns = {"/ReportesServlet"})
public class ReportesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "historial";

        IncidenciaDAO incidenciaDAO = new IncidenciaDAO();
        TecnicoDAO tecnicoDAO = new TecnicoDAO();

        // Lista de técnicos siempre disponible en JSP
        List<Tecnico> tecnicos = tecnicoDAO.listar();
        request.setAttribute("tecnicos", tecnicos);

        switch (action) {
            case "historial":
                List<Incidencia> historial = incidenciaDAO.obtenerHistorial();
                request.setAttribute("historial", historial);
                request.getRequestDispatcher("views/reportes.jsp").forward(request, response);
                break;

            case "buscar":
                procesarFiltros(request, response, incidenciaDAO, false);
                break;

            case "excel_filtro":
                procesarFiltros(request, response, incidenciaDAO, true, "excel");
                break;

            case "pdf_filtro":
                procesarFiltros(request, response, incidenciaDAO, true, "pdf");
                break;

            default:
                response.sendRedirect("views/reportes.jsp");
        }
    }

    // ===========================================================
    //                  PROCESA FILTROS
    // ===========================================================
   private void procesarFiltros(HttpServletRequest request,
                             HttpServletResponse response,
                             IncidenciaDAO dao,
                             boolean exportar,
                             String... tipoExport)
        throws IOException, ServletException {

    // Obtener parámetros
    String inicio = request.getParameter("inicio");
    String fin = request.getParameter("fin");
    String estado = request.getParameter("estado");
    String tecnico = request.getParameter("tecnico");

    // Convertir tipos
    Date fechaInicio = (inicio != null && !inicio.isEmpty()) ? Date.valueOf(inicio) : null;
    Date fechaFin    = (fin != null && !fin.isEmpty()) ? Date.valueOf(fin) : null;
    String estadoFil = (estado != null && !estado.isEmpty()) ? estado : null;
    String tecnicoFil = (tecnico != null && !tecnico.isEmpty()) ? tecnico : null;

    // Buscar con filtros combinados
    List<Incidencia> lista = dao.buscarConFiltros(
            fechaInicio, fechaFin, estadoFil, tecnicoFil
    );

    // EXPORTAR
    if (exportar) {
        response.reset();

        if ("excel".equals(tipoExport[0])) {
            new ExportarExcel().historial(lista, response);
            return;
        }

        if ("pdf".equals(tipoExport[0])) {
            new ExportarPDF().historial(lista, response);
            return;
        }
    }

    // Mostrar en JSP
    request.setAttribute("historial", lista);
    request.getRequestDispatcher("views/reportes.jsp").forward(request, response);
}}


