package com.ticketsystem.controller;

import com.ticketsystem.dao.EquipoDAO;
import com.ticketsystem.dao.HistorialEquipoDAO;
import com.ticketsystem.model.Equipo;
import com.ticketsystem.model.HistorialEquipo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/HistorialEquipoServlet")
public class HistorialEquipoServlet extends HttpServlet {

    private HistorialEquipoDAO historialDAO = new HistorialEquipoDAO();
    private EquipoDAO equipoDAO = new EquipoDAO();

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

            default:
                listar(request, response);
        }
    }

    // ================= LISTAR ======================
    private void listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<HistorialEquipo> listaHistorial = historialDAO.listar();
        List<Equipo> listaEquipos = equipoDAO.listar();

        // Valores base para paginación
        request.setAttribute("paginaActual", 1);
        request.setAttribute("totalPaginas", 1);
        request.setAttribute("search", "");

        request.setAttribute("listaHistorial", listaHistorial);
        request.setAttribute("listaEquipos", listaEquipos);

        request.getRequestDispatcher("views/historial_equipo.jsp").forward(request, response);
    }

    // ================= EDITAR ======================
    private void editar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        HistorialEquipo historial = historialDAO.obtenerPorId(id);

        List<Equipo> listaEquipos = equipoDAO.listar();
        List<HistorialEquipo> listaHistorial = historialDAO.listar();

        // En edición también enviamos paginación y búsqueda para que el JSP no falle
        request.setAttribute("paginaActual", 1);
        request.setAttribute("totalPaginas", 1);
        request.setAttribute("search", "");

        request.setAttribute("historial", historial);
        request.setAttribute("listaEquipos", listaEquipos);
        request.setAttribute("listaHistorial", listaHistorial);

        request.getRequestDispatcher("views/historial_equipo.jsp").forward(request, response);
    }

    // ================= ELIMINAR ======================
    private void eliminar(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        historialDAO.eliminar(id);

        response.sendRedirect("HistorialEquipoServlet");
    }

    // ================= INSERTAR / ACTUALIZAR ======================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idHistorialStr = request.getParameter("idHistorial");

        int idEquipo = Integer.parseInt(request.getParameter("idEquipo"));
        String detalle = request.getParameter("detalle");

        HistorialEquipo h = new HistorialEquipo();
        h.setIdEquipo(idEquipo);
        h.setDetalle(detalle);

        if (idHistorialStr == null || idHistorialStr.isEmpty()) {
            // INSERTAR
            historialDAO.insertar(h);
        } else {
            // ACTUALIZAR
            h.setIdHistorial(Integer.parseInt(idHistorialStr));
            historialDAO.actualizar(h);
        }

        response.sendRedirect("HistorialEquipoServlet");
    }
}
