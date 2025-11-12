
package com.ticketsystem.dao;
import com.ticketsystem.model.Repuesto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class RepuestoDAO {
    private final Connection conn;

    public RepuestoDAO(Connection conn) {
        this.conn = conn;
    }

    // Listar todos
    public List<Repuesto> getAllRepuestos() throws SQLException {
        List<Repuesto> repuestos = new ArrayList<>();
        String sql = "SELECT idRepuesto, nombre, stock, costo FROM repuesto ORDER BY nombre";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Repuesto r = new Repuesto();
                r.setIdRepuesto(rs.getInt("idRepuesto"));
                r.setNombre(rs.getString("nombre"));
                r.setStock(rs.getInt("stock"));
                r.setCosto(rs.getBigDecimal("costo"));
                repuestos.add(r);
            }
        }
        return repuestos;
    }

    // Insertar
    public void addRepuesto(Repuesto repuesto) throws SQLException {
        String sql = "INSERT INTO repuesto (nombre, stock, costo) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, repuesto.getNombre());
            ps.setInt(2, repuesto.getStock());
            ps.setBigDecimal(3, repuesto.getCosto());
            ps.executeUpdate();
        }
    }
    
    // Obtener por ID
    public Repuesto getRepuestoById(int id) throws SQLException {
        String sql = "SELECT idRepuesto, nombre, stock, costo FROM repuesto WHERE idRepuesto = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Repuesto r = new Repuesto();
                    r.setIdRepuesto(rs.getInt("idRepuesto"));
                    r.setNombre(rs.getString("nombre"));
                    r.setStock(rs.getInt("stock"));
                    r.setCosto(rs.getBigDecimal("costo"));
                    return r;
                }
            }
        }
        return null;
    }

    // Actualizar
    public void updateRepuesto(Repuesto repuesto) throws SQLException {
        String sql = "UPDATE repuesto SET nombre = ?, stock = ?, costo = ? WHERE idRepuesto = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, repuesto.getNombre());
            ps.setInt(2, repuesto.getStock());
            ps.setBigDecimal(3, repuesto.getCosto());
            ps.setInt(4, repuesto.getIdRepuesto());
            ps.executeUpdate();
        }
    }

    // Eliminar
    public void deleteRepuesto(int id) throws SQLException {
        String sql = "DELETE FROM repuesto WHERE idRepuesto = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
