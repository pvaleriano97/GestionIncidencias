package com.ticketsystem.servlets;

import com.ticketsystem.dao.*;
import com.ticketsystem.model.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/incidencia")
public class IncidenciaServlet extends HttpServlet {

    private IncidenciaDAO dao = new IncidenciaDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private EquipoDAO equipoDAO = new EquipoDAO();
    private TecnicoDAO tecnicoDAO = new TecnicoDAO();

    private static final int REGISTROS_POR_PAGINA = 5;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String search = request.getParameter("search") != null ? request.getParameter("search") : "";
        int pagina = 1;
        try { pagina = Integer.parseInt(request.getParameter("pagina")); } catch (Exception e) {}

        // Editar
        if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Incidencia incidenciaEdit = dao.obtenerPorId(id);
            request.setAttribute("incidenciaEdit", incidenciaEdit);
        }

        // Eliminar
        if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.eliminar(id);
            response.sendRedirect("incidencia");
            return;
        }

        // Listar combos
        request.setAttribute("listaUsuarios", usuarioDAO.listar());
        request.setAttribute("listaEquipos", equipoDAO.listar());
        request.setAttribute("listaTecnicos", tecnicoDAO.listar());

        // Listado con paginación
        int totalRegistros = dao.contarRegistros(search);
        int totalPaginas = (int)Math.ceil(totalRegistros * 1.0 / REGISTROS_POR_PAGINA);
        int offset = (pagina - 1) * REGISTROS_POR_PAGINA;
        List<Incidencia> lista = dao.listar(search, offset, REGISTROS_POR_PAGINA);

        request.setAttribute("listaIncidencias", lista);
        request.setAttribute("totalPaginas", totalPaginas);
        request.setAttribute("paginaActual", pagina);
        request.setAttribute("search", search);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/incidencia.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("idIncidencia");
        String descripcion = request.getParameter("descripcion");
        String estado = request.getParameter("estado");
        String idUsuarioStr = request.getParameter("idUsuario");
        String idEquipoStr = request.getParameter("idEquipo");
        String idTecnicoStr = request.getParameter("idTecnico");

        Incidencia i = new Incidencia();
        i.setDescripcion(descripcion);
        i.setEstado(estado);
        i.setIdUsuario(Integer.parseInt(idUsuarioStr));
        i.setIdEquipo(Integer.parseInt(idEquipoStr));
        i.setIdTecnico((idTecnicoStr == null || idTecnicoStr.isEmpty()) ? null : Integer.parseInt(idTecnicoStr));

        if (idStr == null || idStr.isEmpty()) {
            dao.insertar(i);
        } else {
            i.setIdIncidencia(Integer.parseInt(idStr));
            dao.actualizar(i);
        }

        response.sendRedirect("incidencia");
    }
}
