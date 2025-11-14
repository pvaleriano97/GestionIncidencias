package com.ticketsystem.servlets;

import com.ticketsystem.dao.HistorialEquipoDAO;
import com.ticketsystem.dao.EquipoDAO;
import com.ticketsystem.model.HistorialEquipo;
import com.ticketsystem.model.Equipo;

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

        try {
            switch (action) {

                case "nuevo":
                    cargarEquipos(request);
                    request.setAttribute("modo", "nuevo");
                    request.getRequestDispatcher("views/historial_equipo_form.jsp").forward(request, response);
                    break;

                case "editar":
                    int idEdit = Integer.parseInt(request.getParameter("id"));
                    HistorialEquipo h = historialDAO.buscarPorId(idEdit);
                    request.setAttribute("historial", h);
                    cargarEquipos(request);
                    request.setAttribute("modo", "editar");
                    request.getRequestDispatcher("views/historial_equipo_form.jsp").forward(request, response);
                    break;

                case "eliminar":
                    int idDel = Integer.parseInt(request.getParameter("id"));
                    historialDAO.eliminar(idDel);
                    response.sendRedirect("HistorialEquipoServlet");
                    break;

                case "listar":
                default:
                    listar(request, response);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
            request.getRequestDispatcher("views/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idHistorial = request.getParameter("idHistorial");
        int idEquipo = Integer.parseInt(request.getParameter("idEquipo"));
        String detalle = request.getParameter("detalle");

        HistorialEquipo h = new HistorialEquipo();
        h.setIdEquipo(idEquipo);
        h.setDetalle(detalle);

        try {
            if (idHistorial == null || idHistorial.isEmpty()) {
                // INSERTA (SP INSERTAR)
                historialDAO.insertar(h);
            } else {
                // ACTUALIZA (SP ACTUALIZAR)
                h.setIdHistorial(Integer.parseInt(idHistorial));
                historialDAO.actualizar(h);
            }

            response.sendRedirect("HistorialEquipoServlet");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
            request.getRequestDispatcher("views/error.jsp").forward(request, response);
        }
    }

    private void listar(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        List<HistorialEquipo> lista = historialDAO.listar();
        request.setAttribute("listaHistorial", lista);
        request.getRequestDispatcher("views/historial_equipo.jsp").forward(request, response);
    }

    private void cargarEquipos(HttpServletRequest request) throws Exception {
        List<Equipo> equipos = equipoDAO.listar();
        request.setAttribute("listaEquipos", equipos);
    }
}
