package com.ticketsystem.servlets;

import com.ticketsystem.dao.IncidenciaDAO;
import com.ticketsystem.dao.TecnicoDAO;
import com.ticketsystem.model.Incidencia;
import com.ticketsystem.model.Tecnico; // si tienes este modelo
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

        // Verificar rol admin (ignorando mayúsculas/minúsculas)
        String rol = (String) request.getSession().getAttribute("role");
        if (rol == null || !rol.equalsIgnoreCase("admin")) {
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

        // Top 5 técnicos (asegúrate que el método no retorne null)
        List<?> topTecnicos = tecnicoDAO.obtenerTopTecnicos(5);
        if (topTecnicos == null) topTecnicos = new ArrayList<>();
        request.setAttribute("topTecnicos", topTecnicos);

        // Últimas 5 incidencias
        List<Incidencia> ultimas = incidenciaDAO.obtenerUltimasIncidencias(5);
        if (ultimas == null) ultimas = new ArrayList<>();
        request.setAttribute("ultimasIncidencias", ultimas);

        // ===== Gráfico de incidencias por día (última semana) =====
        LocalDate today = LocalDate.now();
        List<String> diasSemana = new ArrayList<>();
        List<Integer> incidenciasPorDia = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE"); // Lun, Mar, etc.

        for (int i = 6; i >= 0; i--) {
            LocalDate dia = today.minusDays(i);
            diasSemana.add(dia.format(formatter));
            int count = incidenciaDAO.contarPorFecha(dia); // asegúrate que existe y funciona
            incidenciasPorDia.add(count);
        }

        request.setAttribute("diasSemana", diasSemana);
        request.setAttribute("incidenciasPorDia", incidenciasPorDia);

        // DEBUG (temporal: quita o usa logger en producción)
        System.out.println("AdminDashboard: total=" + totalIncidencias + ", abiertas=" + abiertas +
                ", cerradas=" + cerradas + ", enProceso=" + enProceso);
        System.out.println("Top tecnicos size: " + topTecnicos.size() + " - ultimas size: " + ultimas.size());

        // Forward al JSP (usa la ruta que tengas realmente)
        request.getRequestDispatcher("/views/adminDashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}
