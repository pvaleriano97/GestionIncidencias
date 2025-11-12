package com.ticketsystem.servlets;

import com.ticketsystem.dao.*;
import com.ticketsystem.model.HistorialEquipo;
import com.ticketsystem.model.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import com.ticketsystem.dao.IEquipoDAO;
import com.ticketsystem.dao.EquipoDAO;

@WebServlet("/HistorialEquipoServlet")
public class HistorialEquipoServlet extends HttpServlet {

    private final IHistorialEquipoDAO historialDAO = new HistorialEquipoDAO();
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
                    HistorialEquipo historialEdit = historialDAO.buscarPorId(idEdit);
                    request.setAttribute("historialEdit", historialEdit);
                    listar(request, response);
                    break;
                case "delete":
                    int idDel = Integer.parseInt(request.getParameter("id"));
                    historialDAO.eliminar(idDel);
                    listar(request, response);
                    break;
                default:
                    listar(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String idStr = request.getParameter("idHistorial");

        try {
            HistorialEquipo h = new HistorialEquipo();
            h.setIdEquipo(Integer.parseInt(request.getParameter("idEquipo")));
            h.setDetalle(request.getParameter("detalle"));

            if (idStr == null || idStr.isEmpty()) {
                historialDAO.insertar(h);
            } else {
                h.setIdHistorial(Integer.parseInt(idStr));
                historialDAO.actualizar(h);
            }

            listar(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }

   private void listar(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    try {
        int pagina = 1, registros = 5;
        String search = request.getParameter("search");
        if (search == null) search = "";
        if (request.getParameter("pagina") != null)
            pagina = Integer.parseInt(request.getParameter("pagina"));

        List<HistorialEquipo> lista = historialDAO.listar(pagina, registros, search);
        int total = historialDAO.contar(search);
        int totalPaginas = (int) Math.ceil(total / (double) registros);

          IEquipoDAO equipoDAO = new EquipoDAO();
        request.setAttribute("listaEquipos", equipoDAO.listar());
        request.setAttribute("listaHistorial", lista);
        request.setAttribute("paginaActual", pagina);
        request.setAttribute("totalPaginas", totalPaginas);
        request.setAttribute("search", search);

        request.getRequestDispatcher("/views/historial_equipo.jsp").forward(request, response);

    } catch (Exception e) {
        e.printStackTrace();
        request.setAttribute("error", e.getMessage());
        request.getRequestDispatcher("/views/error.jsp").forward(request, response);
    }
}}
