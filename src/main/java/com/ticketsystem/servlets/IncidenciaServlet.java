package com.tu.paquete;

import com.ticketsystem.model.Incidencia;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

import com.ticketsystem.dao.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.ticketsystem.model.*;

@WebServlet("/IncidenciaServlet")
public class IncidenciaServlet extends HttpServlet {

    private IncidenciaDAO incidenciaDAO = new IncidenciaDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private TecnicoDAO tecnicoDAO = new TecnicoDAO();
    private EquipoDAO equipoDAO = new EquipoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("edit".equals(action)) {
            cargarIncidenciaEditar(request);
        } else if ("delete".equals(action)) {
            eliminarIncidencia(request);
        }

        cargarDatosVista(request);
        request.getRequestDispatcher("/views/incidencia.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        registrarOActualizar(request);
        cargarDatosVista(request);
        request.getRequestDispatcher("/views/incidencia.jsp").forward(request, response);
    }

    // =====================================================
    // Cargar incidencia para editar
    // =====================================================
    private void cargarIncidenciaEditar(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Incidencia incidencia = incidenciaDAO.obtenerPorId(id);
        request.setAttribute("incidenciaEdit", incidencia);
    }

    // =====================================================
    // Eliminar incidencia
    // =====================================================
    private void eliminarIncidencia(HttpServletRequest request) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            incidenciaDAO.eliminar(id);
            request.setAttribute("exitoMensaje", "Incidencia eliminada correctamente.");
        } catch (Exception e) {
            request.setAttribute("errorMensaje", "No se pudo eliminar la incidencia.");
        }
    }

    // =====================================================
    // Registrar o actualizar
    // =====================================================
    private void registrarOActualizar(HttpServletRequest request) {

        try {
            String idIncStr = request.getParameter("idIncidencia");

            Incidencia i = new Incidencia();
            i.setDescripcion(request.getParameter("descripcion"));
            i.setEstado(request.getParameter("estado"));
            i.setIdUsuario(Integer.parseInt(request.getParameter("idUsuario")));
            i.setIdEquipo(Integer.parseInt(request.getParameter("idEquipo")));
            i.setIdTecnico(Integer.parseInt(request.getParameter("idTecnico")));

            if (idIncStr != null && !idIncStr.isEmpty()) {
                i.setIdIncidencia(Integer.parseInt(idIncStr));
                incidenciaDAO.actualizar(i);
                request.setAttribute("exitoMensaje", "Incidencia actualizada correctamente.");
            } else {
                incidenciaDAO.insertar(i);
                request.setAttribute("exitoMensaje", "Incidencia registrada correctamente.");
            }

        } catch (Exception e) {
            request.setAttribute("errorMensaje", "Error al guardar la incidencia.");
        }
    }

    // =====================================================
    // Cargar combos + paginación + búsqueda + lista
    // =====================================================
    private void cargarDatosVista(HttpServletRequest request) {

        HttpSession session = request.getSession();
        String rol = (String) session.getAttribute("role");
        Integer idTecnico = (Integer) session.getAttribute("idTecnico");

        // ==========================
        // BÚSQUEDA
        // ==========================
        String search = request.getParameter("search");
        if (search == null) search = "";
        request.setAttribute("search", search);

        // ==========================
        // PAGINACIÓN
        // ==========================
        int pagina = 1;
        int regXpag = 5;

        if (request.getParameter("pagina") != null) {
            try {
                pagina = Integer.parseInt(request.getParameter("pagina"));
            } catch (Exception ignored) {}
        }

        List<Incidencia> lista;
        int totalRegistros;

        // ==========================
        // FILTRO por rol
        // ==========================
        if ("admin".equals(rol)) {
            lista = incidenciaDAO.listar(search, pagina, regXpag);
            totalRegistros = incidenciaDAO.contar(search);
        } else {
            lista = incidenciaDAO.listarPorTecnico(idTecnico, search, pagina, regXpag);
            totalRegistros = incidenciaDAO.contarPorTecnico(idTecnico, search);
        }

        int totalPaginas = (int) Math.ceil((double) totalRegistros / regXpag);

        request.setAttribute("listaIncidencias", lista);
        request.setAttribute("paginaActual", pagina);
        request.setAttribute("totalPaginas", totalPaginas);

        // ==========================
        // COMB
        // ==========================
        request.setAttribute("listaUsuarios", usuarioDAO.listar());
        request.setAttribute("listaTecnicos", tecnicoDAO.listar());
        request.setAttribute("listaEquipos", equipoDAO.listar());
    }
}
