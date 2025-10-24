package com.ticketsystem.dao;

import com.ticketsystem.model.Ticket;
import com.ticketsystem.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {
    
    public boolean crearTicket(Ticket ticket) {
        String sql = "INSERT INTO tickets (titulo, descripcion, categoria, prioridad, empresa, " +
                    "departamento, fecha_inicio, fecha_fin, usuario_id, usuario_nombre, " +
                    "usuario_email, estado, numero_ticket) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, ticket.getTitulo());
            stmt.setString(2, ticket.getDescripcion());
            stmt.setString(3, ticket.getCategoria());
            stmt.setString(4, ticket.getPrioridad());
            stmt.setString(5, ticket.getEmpresa());
            stmt.setString(6, ticket.getDepartamento());
            stmt.setDate(7, Date.valueOf(ticket.getFechaInicio()));
            stmt.setDate(8, ticket.getFechaFin() != null ? Date.valueOf(ticket.getFechaFin()) : null);
            stmt.setInt(9, ticket.getUsuarioId());
            stmt.setString(10, ticket.getUsuarioNombre());
            stmt.setString(11, ticket.getUsuarioEmail());
            stmt.setString(12, ticket.getEstado());
            stmt.setString(13, ticket.getNumeroTicket());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Ticket> obtenerTicketsPorUsuario(int usuarioId) {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM tickets WHERE usuario_id = ? ORDER BY fecha_creacion DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, usuarioId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Ticket ticket = mapearTicket(rs);
                    tickets.add(ticket);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }
    
    public List<Ticket> obtenerTodosLosTickets() {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM tickets ORDER BY fecha_creacion DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Ticket ticket = mapearTicket(rs);
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }
    
    private Ticket mapearTicket(ResultSet rs) throws SQLException {
        Ticket ticket = new Ticket();
        ticket.setId(rs.getInt("id"));
        ticket.setTitulo(rs.getString("titulo"));
        ticket.setDescripcion(rs.getString("descripcion"));
        ticket.setCategoria(rs.getString("categoria"));
        ticket.setPrioridad(rs.getString("prioridad"));
        ticket.setEmpresa(rs.getString("empresa"));
        ticket.setDepartamento(rs.getString("departamento"));
        ticket.setFechaInicio(rs.getDate("fecha_inicio").toLocalDate());
        Date fechaFin = rs.getDate("fecha_fin");
        ticket.setFechaFin(fechaFin != null ? fechaFin.toLocalDate() : null);
        ticket.setFechaCreacion(rs.getDate("fecha_creacion").toLocalDate());
        ticket.setUsuarioId(rs.getInt("usuario_id"));
        ticket.setUsuarioNombre(rs.getString("usuario_nombre"));
        ticket.setUsuarioEmail(rs.getString("usuario_email"));
        ticket.setEstado(rs.getString("estado"));
        ticket.setNumeroTicket(rs.getString("numero_ticket"));
        return ticket;
    }
}