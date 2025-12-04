package com.ticketsystem.servlets;

import com.ticketsystem.dao.TecnicoDAO;
import com.ticketsystem.dao.UsuarioDAO;
import com.ticketsystem.model.Tecnico;
import com.ticketsystem.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "TecnicoServlet", urlPatterns = {"/TecnicoServlet"})
public class TecnicoServlet extends HttpServlet {

    private TecnicoDAO tecnicoDAO = new TecnicoDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "list";

        try {
            switch (action) {

                case "edit":
                    editar(request, response);
                    break;

                case "delete":
                    eliminar(request, response);
                    break;

                default:
                    listar(request, response);
                    break;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendError(500, "Error en TecnicoServlet");
        }
    }

    // ====================================================
    // LISTAR TÉCNICOS + PAGINACIÓN + BÚSQUEDA
    // ====================================================
    private void listar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // Combo para el formulario
        request.setAttribute("listaUsuarios", usuarioDAO.listarTecnicos());

        // Página actual
        int page = (request.getParameter("pagina") == null)
                ? 1
                : Integer.parseInt(request.getParameter("pagina"));

        int size = 10;

        // Búsqueda
        String search = request.getParameter("search");
        if (search == null) search = "";

        // Lista paginada
        request.setAttribute("listaTecnicos", tecnicoDAO.listar(page, size, search));
        request.setAttribute("paginaActual", page);
        request.setAttribute("search", search);

        // Total de páginas
        int total = tecnicoDAO.contar(search);
        request.setAttribute("totalPaginas", (int) Math.ceil((double) total / size));

        request.getRequestDispatcher("views/tecnico.jsp").forward(request, response);
    }

    // ====================================================
    // EDITAR
    // ====================================================
    private void editar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        int id = Integer.parseInt(request.getParameter("id"));

        Tecnico t = tecnicoDAO.buscarPorId(id);

        request.setAttribute("tecnicoEdit", t);

        // Necesario para cargar el combo otra vez
        request.setAttribute("listaUsuarios", usuarioDAO.listarTecnicos());

        listar(request, response);
    }

    // ====================================================
    // ELIMINAR
    // ====================================================
    private void eliminar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        int id = Integer.parseInt(request.getParameter("id"));
        tecnicoDAO.eliminar(id);

        response.sendRedirect("TecnicoServlet");
    }

    // ====================================================
    // INSERTAR / ACTUALIZAR (POST)
    // ====================================================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int idTecnico = (request.getParameter("idTecnico") == null ||
                    request.getParameter("idTecnico").isEmpty())
                    ? 0
                    : Integer.parseInt(request.getParameter("idTecnico"));

            String nombre = request.getParameter("nombre");
            String especialidad = request.getParameter("especialidad");
            int disponibilidad = Integer.parseInt(request.getParameter("disponibilidad"));
            int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));

            Tecnico t = new Tecnico();
            t.setIdTecnico(idTecnico);
            t.setNombre(nombre);
            t.setEspecialidad(especialidad);
            t.setDisponibilidad(disponibilidad);
            t.setIdUsuario(idUsuario);

            if (idTecnico == 0) {
                tecnicoDAO.insertar(t);    // Nuevo técnico
            } else {
                tecnicoDAO.actualizar(t);  // Editar técnico
            }

            response.sendRedirect("TecnicoServlet");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "Error en registro/actualización de técnico");
        }
    }
}
