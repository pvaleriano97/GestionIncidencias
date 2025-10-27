package com.ticketsystem.servlet;

import com.ticketsystem.model.Tecnico;
import com.ticketsystem.model.Incidencia;
import com.ticketsystem.util.DatabaseConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection conn = DatabaseConnection.getConnection()) {

            // ===============================
            // 1️⃣ Top 5 Técnicos con más incidencias cerradas
            // ===============================
            List<Tecnico> topTecnicos = new ArrayList<>();
            String sqlTop = "SELECT t.idTecnico, t.nombre, t.especialidad, COUNT(i.idIncidencia) AS ticketsCerrados "
                    + "FROM tecnico t "
                    + "LEFT JOIN incidencia i ON t.idTecnico = i.idTecnico AND i.estado='Cerrada' "
                    + "GROUP BY t.idTecnico, t.nombre, t.especialidad "
                    + "ORDER BY ticketsCerrados DESC "
                    + "LIMIT 5";

            try (PreparedStatement ps = conn.prepareStatement(sqlTop);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Tecnico t = new Tecnico();
                    t.setIdTecnico(rs.getInt("idTecnico"));
                    t.setNombre(rs.getString("nombre"));
                    t.setEspecialidad(rs.getString("especialidad"));
                    t.setTicketsCerrados(rs.getInt("ticketsCerrados"));
                    topTecnicos.add(t);
                }
            }
            request.setAttribute("topTecnicos", topTecnicos);

            // ===============================
            // 2️⃣ Últimas 5 Incidencias
            // ===============================
            List<Incidencia> ultimasIncidencias = new ArrayList<>();
            String sqlInc = "SELECT i.idIncidencia, i.descripcion, i.estado, i.fechaRegistro, "
                    + "u.nombre AS nombreUsuario, t.nombre AS nombreTecnico, "
                    + "e.codigoEquipo, e.tipo AS tipoEquipo "
                    + "FROM incidencia i "
                    + "JOIN usuario u ON i.idUsuario = u.idUsuario "
                    + "JOIN equipo e ON i.idEquipo = e.idEquipo "
                    + "LEFT JOIN tecnico t ON i.idTecnico = t.idTecnico "
                    + "ORDER BY i.fechaRegistro DESC "
                    + "LIMIT 5";

            try (PreparedStatement ps = conn.prepareStatement(sqlInc);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Incidencia inc = new Incidencia();
                    inc.setIdIncidencia(rs.getInt("idIncidencia"));
                    inc.setDescripcion(rs.getString("descripcion"));
                    inc.setEstado(rs.getString("estado"));
                    inc.setFechaRegistro(rs.getTimestamp("fechaRegistro"));
                    inc.setNombreUsuario(rs.getString("nombreUsuario"));
                    inc.setNombreTecnico(rs.getString("nombreTecnico"));
                    inc.setCodigoEquipo(rs.getString("codigoEquipo"));
                    inc.setTipoEquipo(rs.getString("tipoEquipo"));
                    ultimasIncidencias.add(inc);
                }
            }
            request.setAttribute("ultimasIncidencias", ultimasIncidencias);

            // ===============================
            // 3️⃣ Totales de Incidencias
            // ===============================
            int totalIncidencias = 0;
            int abiertas = 0;
            int cerradas = 0;

            String sqlTotal = "SELECT COUNT(*) AS total, "
                    + "SUM(CASE WHEN estado='Abierta' THEN 1 ELSE 0 END) AS abiertas, "
                    + "SUM(CASE WHEN estado='Cerrada' THEN 1 ELSE 0 END) AS cerradas "
                    + "FROM incidencia";

            try (PreparedStatement ps = conn.prepareStatement(sqlTotal);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    totalIncidencias = rs.getInt("total");
                    abiertas = rs.getInt("abiertas");
                    cerradas = rs.getInt("cerradas");
                }
            }

            request.setAttribute("totalIncidencias", totalIncidencias);
            request.setAttribute("abiertas", abiertas);
            request.setAttribute("cerradas", cerradas);

            // ===============================
            // 4️⃣ Forward a JSP
            // ===============================
            request.getRequestDispatcher("/views/dashboard.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Error al cargar el dashboard", e);
        }
    }
}
