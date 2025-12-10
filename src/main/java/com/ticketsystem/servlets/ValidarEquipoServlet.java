package com.ticketsystem.servlets;

import com.ticketsystem.dao.EquipoDAO;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/ValidarEquipoServlet")
public class ValidarEquipoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String codigo = request.getParameter("codigoEquipo");
        String idEquipoStr = request.getParameter("idEquipo");

        int idEquipo = 0;
        if (idEquipoStr != null && !idEquipoStr.isEmpty()) {
            idEquipo = Integer.parseInt(idEquipoStr);
        }

        EquipoDAO dao = new EquipoDAO();
        boolean duplicado = dao.existeCodigoEquipo(codigo, idEquipo);

        response.setContentType("application/json");
        response.getWriter()
                .write("{\"duplicado\": " + duplicado + "}");
    }
}
