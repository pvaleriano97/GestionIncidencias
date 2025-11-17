package com.ticketsystem.servlets;

import com.ticketsystem.dao.IncidenciaDAO;
import com.ticketsystem.dao.TecnicoDAO;
import com.ticketsystem.model.Incidencia;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AdminDashboardServlet", urlPatterns = {"/AdminDashboardServlet"})
public class AdminDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verificar rol admin
        String rol = (String) request.getSession().getAttribute("role");
        if (rol == null || !rol.equals("admin")) {
            response.sendRedirect(request.getContextPath() + "/LoginServlet");
            return;
        }

        // DAOs
        IncidenciaDAO incidenciaDAO = new IncidenciaDAO();
        TecnicoDAO tecnicoDAO = new TecnicoDAO();

        // Métricas generales
        int totalIncidencias = incidenciaDAO.totalIncidencias();
        int abiertas = incidenciaDAO.totalAbiertasAdmin();
        int cerradas = incidenciaDAO.totalCerradasAdmin();
        int enProceso = incidenciaDAO.totalEnProcesoAdmin();

        request.setAttribute("totalIncidencias", totalIncidencias);
        request.setAttribute("abiertas", abiertas);
        request.setAttribute("cerradas", cerradas);
        request.setAttribute("enProceso", enProceso);

        // Top 5 técnicos
        request.setAttribute("topTecnicos", tecnicoDAO.obtenerTopTecnicos(5));

        // Últimas 5 incidencias
        request.setAttribute("ultimasIncidencias", incidenciaDAO.obtenerUltimasIncidencias(5));

        // ===== Gráfico de incidencias por día (última semana) =====
        // Suponiendo que la fechaRegistro es tipo TIMESTAMP en BD
        LocalDate today = LocalDate.now();
        List<String> diasSemana = new ArrayList<>();
        List<Integer> incidenciasPorDia = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE");

        // Últimos 7 días
        for (int i = 6; i >= 0; i--) {
            LocalDate dia = today.minusDays(i);
            diasSemana.add(dia.format(formatter)); // Lun, Mar, etc.
            int count = incidenciaDAO.contarPorFecha(dia); // nuevo método
            incidenciasPorDia.add(count);
        }

        request.setAttribute("diasSemana", diasSemana);
        request.setAttribute("incidenciasPorDia", incidenciasPorDia);

        // Forward al JSP
        request.getRequestDispatcher("/views/adminDashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}
