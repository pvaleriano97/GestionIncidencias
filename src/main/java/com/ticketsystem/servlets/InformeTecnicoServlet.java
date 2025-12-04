package com.ticketsystem;

import com.ticketsystem.dao.IncidenciaDAO;
import com.ticketsystem.dao.InformeTecnicoDAO;
import com.ticketsystem.model.Incidencia;
import com.ticketsystem.model.InformeTecnico;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/InformeTecnicoServlet")
public class InformeTecnicoServlet extends HttpServlet {

    private InformeTecnicoDAO informeDAO = new InformeTecnicoDAO();
    private IncidenciaDAO incidenciaDAO = new IncidenciaDAO();

    // registros por página (ajusta si quieres)
    private static final int REGISTROS_POR_PAGINA = 8;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "listar";

        switch (action) {
            case "editar":
                editar(request, response);
                break;
            case "eliminar":
                eliminar(request, response);
                break;
            case "fetchTecnico":
                fetchTecnico(request, response);
                break;
            default:
                listar(request, response);
        }
    }

    // LISTAR con paginación y búsqueda
    private void listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String search = request.getParameter("search");
        if (search == null) search = "";

        int paginaActual = 1;
        String pagParam = request.getParameter("pagina");
        try {
            if (pagParam != null) paginaActual = Integer.parseInt(pagParam);
            if (paginaActual < 1) paginaActual = 1;
        } catch (NumberFormatException ignored) {}

        int offset = (paginaActual - 1) * REGISTROS_POR_PAGINA;

        List<InformeTecnico> lista = informeDAO.listarPaginado(search, offset, REGISTROS_POR_PAGINA);
        int totalRegistros = informeDAO.contarRegistros(search);
        int totalPaginas = (int) Math.ceil((double) totalRegistros / REGISTROS_POR_PAGINA);
        if (totalPaginas < 1) totalPaginas = 1;

        List<Incidencia> listaIncidencias = incidenciaDAO.listarSimple();

        request.setAttribute("lista", lista);
        request.setAttribute("listaIncidencias", listaIncidencias);
        request.setAttribute("paginaActual", paginaActual);
        request.setAttribute("totalPaginas", totalPaginas);
        request.setAttribute("search", search);
        request.setAttribute("registrosPorPagina", REGISTROS_POR_PAGINA);

        request.getRequestDispatcher("views/informe_tecnico.jsp").forward(request, response);
    }

    private void editar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        InformeTecnico informe = informeDAO.obtenerPorId(id);
        List<Incidencia> listaIncidencias = incidenciaDAO.listarSimple();

        request.setAttribute("informe", informe);
        request.setAttribute("listaIncidencias", listaIncidencias);

        request.getRequestDispatcher("views/informe_tecnico.jsp").forward(request, response);
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        informeDAO.eliminar(id);
        response.sendRedirect("InformeTecnicoServlet");
    }

    // AJAX: devuelve JSON con idTecnico y nombreTecnico para una incidencia
    private void fetchTecnico(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String idIncStr = request.getParameter("idIncidencia");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            if (idIncStr == null || idIncStr.isEmpty()) {
                out.print("{\"ok\":false}");
                return;
            }
            int idInc = Integer.parseInt(idIncStr);
            Incidencia inc = incidenciaDAO.obtenerPorId(idInc);
            if (inc == null) {
                out.print("{\"ok\":false}");
                return;
            }
            Integer idTec = inc.getIdTecnico();
            String nombre = inc.getNombreTecnico() != null ? inc.getNombreTecnico() : "";
            if (idTec == null) {
                out.print("{\"ok\":true, \"idTecnico\": null, \"nombreTecnico\": \"\"}");
            } else {
                nombre = nombre.replace("\"", "\\\"");
                out.print("{\"ok\":true, \"idTecnico\": " + idTec + ", \"nombreTecnico\": \"" + nombre + "\"}");
            }
        } catch (Exception e) {
            response.getWriter().print("{\"ok\":false}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idInformeStr = request.getParameter("idInforme");

        String fechaParam = request.getParameter("fechaCierre");
        String fechaMysql = null;
        if (fechaParam != null && !fechaParam.isEmpty()) {
            fechaMysql = fechaParam.replace("T", " ") + ":00";
        }

        InformeTecnico it = new InformeTecnico();
        it.setFechaCierre(fechaMysql);
        it.setObservaciones(request.getParameter("observaciones"));

        int idInc = Integer.parseInt(request.getParameter("idIncidencia"));
        it.setIdIncidencia(idInc);

        String idTecParam = request.getParameter("idTecnico");
        if (idTecParam != null && !idTecParam.isEmpty()) {
            it.setIdTecnico(Integer.parseInt(idTecParam));
        } else {
            Incidencia inc = incidenciaDAO.obtenerPorId(idInc);
            it.setIdTecnico((inc != null && inc.getIdTecnico() != null) ? inc.getIdTecnico() : 0);
        }

        try {
            if (idInformeStr == null || idInformeStr.isEmpty()) {
                informeDAO.insertar(it);
            } else {
                it.setIdInforme(Integer.parseInt(idInformeStr));
                informeDAO.actualizar(it);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Mantener búsqueda/página si venimos de ellas (opcional)
        response.sendRedirect("InformeTecnicoServlet");
    }
}
