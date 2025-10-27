package com.ticketsystem.servlets;

import com.google.gson.Gson;
import com.ticketsystem.dao.EquipoDAO;
import com.ticketsystem.dao.IEquipoDAO;
import com.ticketsystem.model.Equipo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ROSA
 */
@WebServlet(name = "EquipoServlet", urlPatterns = {"/EquipoServlet"})
public class EquipoServlet extends HttpServlet {

    private final IEquipoDAO equipoDAO = new EquipoDAO();

    // ✅ Método GET: listar / buscar
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");
        Gson gson = new Gson();
        Map<String, Object> result = new HashMap<>();

        String action = request.getParameter("action");
        if (action == null) action = "";

        try {
            switch (action) {
                case "listar":
                    int page = Integer.parseInt(request.getParameter("page"));
                    int size = Integer.parseInt(request.getParameter("size"));
                    String filtro = request.getParameter("filtro");

                    List<Equipo> lista = equipoDAO.listar(page, size, filtro);
                    int total = equipoDAO.contar(filtro);

                    result.put("data", lista);
                    result.put("total", total);
                    break;

                case "buscar":
                    int id = Integer.parseInt(request.getParameter("id"));
                    Equipo e = equipoDAO.buscarPorId(id);
                    result.put("data", e);
                    break;

                default:
                    result.put("error", "Acción no válida o vacía");
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            result.put("error", ex.getMessage());
        }

        response.getWriter().write(gson.toJson(result));
    }

    // ✅ Método POST: insertar / actualizar / eliminar
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");
        Gson gson = new Gson();
        Map<String, Object> result = new HashMap<>();

        String action = request.getParameter("action");
        if (action == null) action = "";

        try {
            switch (action) {
                case "insertar":
                    Equipo nuevo = new Equipo();
                    nuevo.setCodigoEquipo(request.getParameter("codigoEquipo"));
                    nuevo.setTipo(request.getParameter("tipo"));
                    nuevo.setEstado(request.getParameter("estado"));
                    result.put("success", equipoDAO.insertar(nuevo));
                    break;

                case "actualizar":
                    Equipo act = new Equipo();
                    act.setIdEquipo(Integer.parseInt(request.getParameter("idEquipo")));
                    act.setCodigoEquipo(request.getParameter("codigoEquipo"));
                    act.setTipo(request.getParameter("tipo"));
                    act.setEstado(request.getParameter("estado"));
                    result.put("success", equipoDAO.actualizar(act));
                    break;

                case "eliminar":
                    int id = Integer.parseInt(request.getParameter("idEquipo"));
                    result.put("success", equipoDAO.eliminar(id));
                    break;

                default:
                    result.put("error", "Acción POST no válida o vacía");
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            result.put("error", ex.getMessage());
        }

        response.getWriter().write(gson.toJson(result));
    }

    @Override
    public String getServletInfo() {
        return "Servlet para gestión de equipos (CRUD con JSON y AJAX)";
    }
}
