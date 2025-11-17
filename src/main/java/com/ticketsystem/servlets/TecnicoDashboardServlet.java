package com.ticketsystem.servlets;

import com.ticketsystem.dao.IncidenciaDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "TecnicoDashboardServlet", urlPatterns = {"/TecnicoDashboardServlet"})
public class TecnicoDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener idTecnico desde sesión
        Integer idTecnico = (Integer) request.getSession().getAttribute("idTecnico");

        if (idTecnico == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Cargar métricas
        IncidenciaDAO dao = new IncidenciaDAO();

        request.setAttribute("totalAsignadas", dao.totalAsignadas(idTecnico));
        request.setAttribute("abiertas", dao.totalAbiertas(idTecnico));
        request.setAttribute("enProceso", dao.totalEnProceso(idTecnico));
        request.setAttribute("cerradas", dao.totalCerradas(idTecnico));

        request.setAttribute("incidenciasTecnico", dao.listarAsignadas(idTecnico));

        // Redirige al dashboard del técnico
        request.getRequestDispatcher("/views/tecnicoDashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
