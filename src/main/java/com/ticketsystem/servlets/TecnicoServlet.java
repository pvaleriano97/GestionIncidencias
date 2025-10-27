package com.ticketsystem.servlets;

import com.ticketsystem.dao.TecnicoDAO;
import com.ticketsystem.model.Tecnico;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "TecnicoServlet", urlPatterns = {"/TecnicoServlet"})
public class TecnicoServlet extends HttpServlet {

    private TecnicoDAO dao;

    @Override
    public void init() {
        dao = new TecnicoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "listar";

        try {
            switch (action) {
                case "nuevo":
                    request.getRequestDispatcher("tecnico-form.jsp").forward(request, response);
                    break;

                case "editar":
                    editarTecnico(request, response);
                    break;

                case "eliminar":
                    eliminarTecnico(request, response);
                    break;

                default:
                    listarTecnicos(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    private void listarTecnicos(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        int page = 1;
        int size = 5;
        String filtro = request.getParameter("buscar");

        if (request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page"));

        List<Tecnico> lista = dao.listar(page, size, filtro);
        int total = dao.contar(filtro);
        int totalPaginas = (int) Math.ceil((double) total / size);

        request.setAttribute("lista", lista);
        request.setAttribute("totalPaginas", totalPaginas);
        request.setAttribute("paginaActual", page);
        request.setAttribute("buscar", filtro);

        request.getRequestDispatcher("tecnico.jsp").forward(request, response);
    }

    private void editarTecnico(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        Tecnico tecnico = dao.buscarPorId(id);
        request.setAttribute("tecnico", tecnico);
        request.getRequestDispatcher("tecnico-form.jsp").forward(request, response);
    }

    private void eliminarTecnico(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        dao.eliminar(id);
        response.sendRedirect("TecnicoServlet");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("idTecnico");
        String nombre = request.getParameter("nombre");
        String especialidad = request.getParameter("especialidad");
        int disponibilidad = Integer.parseInt(request.getParameter("disponibilidad"));

        Tecnico tecnico = new Tecnico();
        tecnico.setNombre(nombre);
        tecnico.setEspecialidad(especialidad);
        tecnico.setDisponibilidad(disponibilidad);

        try {
            if (idStr == null || idStr.isEmpty()) {
                dao.insertar(tecnico);
            } else {
                tecnico.setIdTecnico(Integer.parseInt(idStr));
                dao.actualizar(tecnico);
            }
            response.sendRedirect("TecnicoServlet");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Controlador para gestión de técnicos";
    }
}
