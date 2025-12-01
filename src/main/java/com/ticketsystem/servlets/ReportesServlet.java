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

        // Siempre enviar lista de técnicos
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
    //             PROCESA BÚSQUEDA Y EXPORTACIÓN
    // ===========================================================
    private void procesarFiltros(HttpServletRequest request,
                                 HttpServletResponse response,
                                 IncidenciaDAO dao,
                                 boolean exportar,
                                 String... tipoExport) throws IOException, ServletException {

        String inicio = request.getParameter("inicio");
        String fin = request.getParameter("fin");
        String estado = request.getParameter("estado");
        String tecnico = request.getParameter("tecnico");

        List<Incidencia> lista;

        // ------------------------------
        // Prioridad: fechas > estado > técnico > todo
        // ------------------------------
        if (inicio != null && !inicio.isEmpty() &&
                fin != null && !fin.isEmpty()) {
            lista = dao.obtenerPorRangoFechas(Date.valueOf(inicio), Date.valueOf(fin));
        }
        else if (estado != null && !estado.isEmpty()) {
            lista = dao.obtenerPorEstado(estado);
        }
        else if (tecnico != null && !tecnico.isEmpty()) {
            lista = dao.obtenerPorTecnico(Integer.parseInt(tecnico));
        }
        else {
            lista = dao.obtenerHistorial();
        }

        // ===========================================================
        //                       EXPORTAR
        // ===========================================================
        if (exportar) {

            response.reset(); // LIMPIA BUFFER (OBLIGATORIO)

            // --- EXPORTAR A EXCEL ---
            if (tipoExport[0].equals("excel")) {
                new ExportarExcel().historial(lista, response);
                return; // ❗ evita cargar JSP
            }

            // --- EXPORTAR A PDF ---
            if (tipoExport[0].equals("pdf")) {
                new ExportarPDF().historial(lista, response);
                return;
            }
        }

        // ===========================================================
        //           MOSTRAR RESULTADOS EN EL JSP
        // ===========================================================
        request.setAttribute("historial", lista);
        request.getRequestDispatcher("views/reportes.jsp").forward(request, response);
    }
}
