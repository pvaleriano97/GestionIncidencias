package com.ticketsystem.servlets;

import com.ticketsystem.dao.TecnicoDAO;
import com.ticketsystem.dao.ITecnicoDAO;
import com.ticketsystem.model.Tecnico;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "TecnicoServlet", urlPatterns = {"/TecnicoServlet"})
public class TecnicoServlet extends HttpServlet {

    private final ITecnicoDAO tecnicoDAO = new TecnicoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "listar";

        try {
            switch (action) {
                case "edit":
                    int idEdit = Integer.parseInt(request.getParameter("id"));
                    Tecnico tecnicoEdit = tecnicoDAO.buscarPorId(idEdit);
                    request.setAttribute("tecnicoEdit", tecnicoEdit);
                    listarTecnicos(request, response);
                    break;

                case "delete":
                    int idDel = Integer.parseInt(request.getParameter("id"));
                    tecnicoDAO.eliminar(idDel);
                    listarTecnicos(request, response);
                    break;

                default:
                    listarTecnicos(request, response);
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
        String idTecnicoStr = request.getParameter("idTecnico");

        try {
            Tecnico tecnico = new Tecnico();
            tecnico.setNombre(request.getParameter("nombre"));
            tecnico.setEspecialidad(request.getParameter("especialidad"));
            tecnico.setDisponibilidad(
                Integer.parseInt(request.getParameter("disponibilidad"))
            );

            if (idTecnicoStr == null || idTecnicoStr.isEmpty()) {
                tecnicoDAO.insertar(tecnico);
            } else {
                tecnico.setIdTecnico(Integer.parseInt(idTecnicoStr));
                tecnicoDAO.actualizar(tecnico);
            }

            listarTecnicos(request, response);

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }

    private void listarTecnicos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int pagina = 1;
            int registrosPorPagina = 5;
            String search = request.getParameter("search");
            if (search == null) search = "";

            if (request.getParameter("pagina") != null) {
                pagina = Integer.parseInt(request.getParameter("pagina"));
            }

            List<Tecnico> listaTecnicos = tecnicoDAO.listar(pagina, registrosPorPagina, search);
            int totalRegistros = tecnicoDAO.contar(search);
            int totalPaginas = (int) Math.ceil(totalRegistros / (double) registrosPorPagina);

            request.setAttribute("listaTecnicos", listaTecnicos);
            request.setAttribute("paginaActual", pagina);
            request.setAttribute("totalPaginas", totalPaginas);
            request.setAttribute("search", search);

            request.getRequestDispatcher("/views/tecnico.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet para gestión de Técnicos con JSP y paginación";
    }
}
