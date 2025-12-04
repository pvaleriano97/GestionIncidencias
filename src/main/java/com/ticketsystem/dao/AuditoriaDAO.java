package com.ticketsystem.dao;

import com.ticketsystem.model.AuditoriaLogin;
import com.ticketsystem.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuditoriaDAO {
    
    // Registrar un evento de login
    public boolean registrarLogin(AuditoriaLogin auditoria) {
        String sql = "INSERT INTO auditoria_login (id_usuario, correo_usuario, nombre_usuario, " +
                    "rol_usuario, fecha_login, ip_address, user_agent, status_login, " +
                    "codigo_2fa, intentos_fallidos, session_id, navegador, sistema_operativo) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, auditoria.getIdUsuario());
            pstmt.setString(2, auditoria.getCorreoUsuario());
            pstmt.setString(3, auditoria.getNombreUsuario());
            pstmt.setString(4, auditoria.getRolUsuario());
            pstmt.setTimestamp(5, new Timestamp(auditoria.getFechaLogin().getTime()));
            pstmt.setString(6, auditoria.getIpAddress());
            pstmt.setString(7, auditoria.getUserAgent());
            pstmt.setString(8, auditoria.getStatusLogin());
            pstmt.setString(9, auditoria.getCodigo2fa());
            pstmt.setInt(10, auditoria.getIntentosFallidos());
            pstmt.setString(11, auditoria.getSessionId());
            pstmt.setString(12, auditoria.getNavegador());
            pstmt.setString(13, auditoria.getSistemaOperativo());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.out.println("❌ Error registrando auditoría: " + e.getMessage());
            return false;
        }
    }
    
    // Obtener todos los logs
    public List<AuditoriaLogin> obtenerTodosLogs() {
        List<AuditoriaLogin> logs = new ArrayList<>();
        String sql = "SELECT * FROM auditoria_login ORDER BY fecha_login DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                logs.add(mapearAuditoria(rs));
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Error obteniendo logs: " + e.getMessage());
        }
        
        return logs;
    }
    
    // Obtener logs por usuario
    public List<AuditoriaLogin> obtenerLogsPorUsuario(String correo) {
        List<AuditoriaLogin> logs = new ArrayList<>();
        String sql = "SELECT * FROM auditoria_login WHERE correo_usuario = ? ORDER BY fecha_login DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, correo);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                logs.add(mapearAuditoria(rs));
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Error obteniendo logs por usuario: " + e.getMessage());
        }
        
        return logs;
    }
    
    // Obtener estadísticas
    public List<Object[]> obtenerEstadisticas() {
        List<Object[]> stats = new ArrayList<>();
        String sql = "SELECT " +
                    "DATE(fecha_login) as fecha, " +
                    "COUNT(*) as total, " +
                    "SUM(CASE WHEN status_login = 'EXITOSO' THEN 1 ELSE 0 END) as exitosos, " +
                    "SUM(CASE WHEN status_login = 'FALLIDO' THEN 1 ELSE 0 END) as fallidos " +
                    "FROM auditoria_login " +
                    "GROUP BY DATE(fecha_login) " +
                    "ORDER BY fecha DESC " +
                    "LIMIT 30";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Object[] stat = new Object[4];
                stat[0] = rs.getDate("fecha");
                stat[1] = rs.getInt("total");
                stat[2] = rs.getInt("exitosos");
                stat[3] = rs.getInt("fallidos");
                stats.add(stat);
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Error obteniendo estadísticas: " + e.getMessage());
        }
        
        return stats;
    }
    
    // Mapear ResultSet a AuditoriaLogin
    private AuditoriaLogin mapearAuditoria(ResultSet rs) throws SQLException {
        AuditoriaLogin auditoria = new AuditoriaLogin();
        auditoria.setIdAuditoria(rs.getInt("id_auditoria"));
        auditoria.setIdUsuario(rs.getInt("id_usuario"));
        auditoria.setCorreoUsuario(rs.getString("correo_usuario"));
        auditoria.setNombreUsuario(rs.getString("nombre_usuario"));
        auditoria.setRolUsuario(rs.getString("rol_usuario"));
        auditoria.setFechaLogin(rs.getTimestamp("fecha_login"));
        auditoria.setIpAddress(rs.getString("ip_address"));
        auditoria.setUserAgent(rs.getString("user_agent"));
        auditoria.setStatusLogin(rs.getString("status_login"));
        auditoria.setCodigo2fa(rs.getString("codigo_2fa"));
        auditoria.setIntentosFallidos(rs.getInt("intentos_fallidos"));
        auditoria.setSessionId(rs.getString("session_id"));
        auditoria.setNavegador(rs.getString("navegador"));
        auditoria.setSistemaOperativo(rs.getString("sistema_operativo"));
        return auditoria;
    }
}