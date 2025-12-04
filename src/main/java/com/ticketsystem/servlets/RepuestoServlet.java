package com.ticketsystem.servlets;

import com.ticketsystem.dao.IRepuestoDAO;
import com.ticketsystem.dao.RepuestoDAO;
import com.ticketsystem.model.Repuesto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "RepuestoServlet", urlPatterns = {"/RepuestoServlet"})
public class RepuestoServlet extends HttpServlet {

    private IRepuestoDAO repuestoDAO = new RepuestoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            String action = req.getParameter("action");
            if (action == null) action = "listar";

            switch (action) {
                case "editar": cargarParaEditar(req, resp); break;
                case "eliminar": eliminar(req, resp); break;
                default: listar(req, resp); break;
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void listar(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        int pagina = 1;
        int tamano = 5;
        if (req.getParameter("pagina") != null)
            pagina = Integer.parseInt(req.getParameter("pagina"));

        String filtro = req.getParameter("search");
        if (filtro == null) filtro = "";

        List<Repuesto> lista = repuestoDAO.listar(pagina, tamano, filtro);
        int total = repuestoDAO.contar(filtro);
        int totalPaginas = (int) Math.ceil(total / (double) tamano);

        req.setAttribute("lista", lista);
        req.setAttribute("paginaActual", pagina);
        req.setAttribute("totalPaginas", totalPaginas);
        req.setAttribute("search", filtro);

        req.getRequestDispatcher("/views/repuesto.jsp").forward(req, resp);
    }
      
    private void cargarParaEditar(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        int id = Integer.parseInt(req.getParameter("id"));
        Repuesto r = repuestoDAO.obtener(id);
        req.setAttribute("repuesto", r);
        listar(req, resp);
    }

    private void eliminar(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        int id = Integer.parseInt(req.getParameter("id"));
        repuestoDAO.eliminar(id);
        resp.sendRedirect("RepuestoServlet");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = req.getParameter("idRepuesto") == null || req.getParameter("idRepuesto").isEmpty()
                    ? 0
                    : Integer.parseInt(req.getParameter("idRepuesto"));

            String nombre = req.getParameter("nombre");
            int stock = Integer.parseInt(req.getParameter("stock"));
            double costo = Double.parseDouble(req.getParameter("costo"));

            Repuesto r = new Repuesto(id, nombre, stock, costo);

            if (id == 0) repuestoDAO.insertar(r);
            else repuestoDAO.actualizar(r);

            resp.sendRedirect("RepuestoServlet");

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
