package com.ticketsystem.servlets;

import com.ticketsystem.dao.EquipoDAO;
import com.ticketsystem.dao.IEquipoDAO;
import com.ticketsystem.model.Equipo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "EquipoServlet", urlPatterns = {"/EquipoServlet"})
public class EquipoServlet extends HttpServlet {

    private final IEquipoDAO equipoDAO = new EquipoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "listar";

        try {
            switch (action) {
                case "edit":
                    int idEdit = Integer.parseInt(request.getParameter("id"));
                    Equipo equipoEdit = equipoDAO.buscarPorId(idEdit);
                    request.setAttribute("equipoEdit", equipoEdit);
                    listarEquipos(request, response);
                    break;

                case "delete":
                    int idDel = Integer.parseInt(request.getParameter("id"));
                    equipoDAO.eliminar(idDel);
                    listarEquipos(request, response);
                    break;

                default:
                    listarEquipos(request, response);
                    break;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String idEquipoStr = request.getParameter("idEquipo");

        try {
            Equipo equipo = new Equipo();
            equipo.setCodigoEquipo(request.getParameter("codigoEquipo"));
            equipo.setTipo(request.getParameter("tipo"));
            equipo.setEstado(request.getParameter("estado"));

            if (idEquipoStr == null || idEquipoStr.isEmpty()) {
                // Insertar nuevo
                equipoDAO.insertar(equipo);
            } else {
                // Actualizar existente
                equipo.setIdEquipo(Integer.parseInt(idEquipoStr));
                equipoDAO.actualizar(equipo);
            }

            listarEquipos(request, response);

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }

    // ✅ Método auxiliar para listar y paginar equipos
    private void listarEquipos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int pagina = 1;
            int registrosPorPagina = 5;
            String search = request.getParameter("search");
            if (search == null) search = "";

            if (request.getParameter("pagina") != null) {
                pagina = Integer.parseInt(request.getParameter("pagina"));
            }

            List<Equipo> listaEquipos = equipoDAO.listar(pagina, registrosPorPagina, search);
            int totalRegistros = equipoDAO.contar(search);
            int totalPaginas = (int) Math.ceil(totalRegistros / (double) registrosPorPagina);

            request.setAttribute("listaEquipos", listaEquipos);
            request.setAttribute("paginaActual", pagina);
            request.setAttribute("totalPaginas", totalPaginas);
            request.setAttribute("search", search);

            request.getRequestDispatcher("/views/equipo.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet para gestión de Equipos con JSP y paginación";
    }
}
