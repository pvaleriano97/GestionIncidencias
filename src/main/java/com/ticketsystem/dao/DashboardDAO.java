package com.ticketsystem.dao;

import com.ticketsystem.model.Incidencia;
import com.ticketsystem.model.Tecnico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DashboardDAO {

    private Connection conn;

    public DashboardDAO(Connection conn) {
        this.conn = conn;
    }

    // Contar incidencias por estado
    public int countTicketsByEstado(String estado) throws SQLException {
        String sql = "SELECT COUNT(*) FROM incidencia WHERE estado = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, estado);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }

    // Contar incidencias cerradas hoy
    public int countTicketsCerradosHoy() throws SQLException {
        String sql = "SELECT COUNT(*) FROM incidencia WHERE estado = 'Cerrada' AND DATE(fechaRegistro) = CURDATE()";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    // Obtener últimos tickets
    public List<Incidencia> getUltimosTickets(int limit) throws SQLException {
        List<Incidencia> tickets = new ArrayList<>();
        String sql = "SELECT i.idIncidencia, i.descripcion, i.fechaRegistro, " +
                     "u.nombre AS nombreUsuario, e.codigoEquipo, e.tipo AS tipoEquipo, t.nombre AS nombreTecnico, i.estado " +
                     "FROM incidencia i " +
                     "JOIN usuario u ON i.idUsuario = u.idUsuario " +
                     "JOIN equipo e ON i.idEquipo = e.idEquipo " +
                     "LEFT JOIN tecnico t ON i.idTecnico = t.idTecnico " +
                     "ORDER BY i.fechaRegistro DESC LIMIT ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Incidencia t = new Incidencia();
                    t.setIdIncidencia(rs.getInt("idIncidencia"));
                    t.setDescripcion(rs.getString("descripcion"));
                    t.setFechaRegistro(rs.getTimestamp("fechaRegistro"));
                    t.setNombreUsuario(rs.getString("nombreUsuario"));
                    t.setCodigoEquipo(rs.getString("codigoEquipo"));
                    t.setTipoEquipo(rs.getString("tipoEquipo"));
                    t.setNombreTecnico(rs.getString("nombreTecnico"));
                    t.setEstado(rs.getString("estado"));
                    tickets.add(t);
                }
            }
        }
        return tickets;
    }

    // Top técnicos más activos
    public List<Tecnico> getTopTecnicos(int limit) throws SQLException {
        List<Tecnico> tecnicos = new ArrayList<>();
        String sql = "SELECT t.idTecnico, t.nombre, COUNT(i.idIncidencia) AS ticketsCerrados " +
                     "FROM incidencia i " +
                     "JOIN tecnico t ON i.idTecnico = t.idTecnico " +
                     "WHERE i.estado = 'Cerrada' " +
                     "GROUP BY t.idTecnico, t.nombre " +
                     "ORDER BY ticketsCerrados DESC LIMIT ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Tecnico tec = new Tecnico();
                    tec.setIdTecnico(rs.getInt("idTecnico"));
                    tec.setNombre(rs.getString("nombre"));
                    tec.setTicketsCerrados(rs.getInt("ticketsCerrados"));
                    tecnicos.add(tec);
                }
            }
        }
        return tecnicos;
    }
}
