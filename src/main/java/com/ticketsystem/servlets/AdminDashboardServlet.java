package com.ticketsystem.servlets;

import com.ticketsystem.dao.IncidenciaDAO;
import com.ticketsystem.dao.TecnicoDAO;
import com.ticketsystem.model.Incidencia;
import com.ticketsystem.model.Tecnico;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@WebServlet("/AdminDashboardServlet")
public class AdminDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            IncidenciaDAO incidenciaDAO = new IncidenciaDAO();
            TecnicoDAO tecnicoDAO = new TecnicoDAO();

            /* =======================
               1. TOTALES
               ======================= */
req.setAttribute("totalIncidencias", incidenciaDAO.contarTodas());
req.setAttribute("abiertas", incidenciaDAO.contarPorEstado("Abierta"));
req.setAttribute("enProceso", incidenciaDAO.contarPorEstado("En Proceso"));
req.setAttribute("cerradas", incidenciaDAO.contarPorEstado("Cerrada"));


            /* =======================
               2. TOP 5 TECNICOS
               ======================= */
            List<Tecnico> top = tecnicoDAO.obtenerTopTecnicos(5);

            // IMPORTANTE: El JSP usa getTotalResueltas()
            req.setAttribute("topTecnicos", top);


            /* =======================
               3. ÚLTIMAS 5 INCIDENCIAS
               ======================= */
            List<Incidencia> ultimasInc = incidenciaDAO.listarUltimas(5);
            req.setAttribute("ultimasIncidencias", ultimasInc);


            /* =======================
               4. GRÁFICO SEMANAL
               ======================= */
            List<String> diasSemana = new ArrayList<>();
            List<Integer> incidenciasPorDia = new ArrayList<>();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");

            for (int i = 6; i >= 0; i--) {
                LocalDate dia = LocalDate.now().minusDays(i);
                diasSemana.add(dia.format(formatter));

                  int totalDia = incidenciaDAO.contarPorFecha(dia); 
                incidenciasPorDia.add(totalDia);
            }

            req.setAttribute("diasSemana", diasSemana);
            req.setAttribute("incidenciasPorDia", incidenciasPorDia);


            /* =======================
               5. ENVIAR A JSP
               ======================= */
            req.getRequestDispatcher("views/adminDashboard.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(500, "Error en Dashboard: " + e.getMessage());
        }
    }
}
