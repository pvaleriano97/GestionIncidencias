
package com.ticketsystem.dao;
import com.ticketsystem.model.HistorialEquipo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;

public class HistorialEquipoDAO {
    
    private final Connection conn;

public HistorialEquipoDAO(Connection conn) {
        this.conn = conn;
    }

// Obtener historial de un equipo específico
    public List<HistorialEquipo> getHistorialByEquipo(int idEquipo) throws SQLException {
        List<HistorialEquipo> historialList = new ArrayList<>();
        // Asumiendo que has agregado un campo 'fechaRegistro' a historialequipo
        String sql = "SELECT idHistorial, idEquipo, detalle, fechaRegistro FROM historialequipo WHERE idEquipo = ? ORDER BY fechaRegistro DESC"; 

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEquipo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    HistorialEquipo he = new HistorialEquipo();
                    he.setIdHistorial(rs.getInt("idHistorial"));
                    he.setIdEquipo(rs.getInt("idEquipo"));
                    he.setDetalle(rs.getString("detalle"));
                    he.setFechaRegistro(rs.getTimestamp("fechaRegistro"));
                    historialList.add(he);
                }
            }
        }
        return historialList;
    }




}