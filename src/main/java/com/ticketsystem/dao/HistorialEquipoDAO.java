
package com.ticketsystem.dao;
import com.ticketsystem.model.HistorialEquipo;
import com.ticketsystem.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistorialEquipoDAO implements IHistorialEquipoDAO {

    @Override
    public List<HistorialEquipo> listar(int page, int size, String filtro) throws Exception {
        List<HistorialEquipo> lista = new ArrayList<>();
        String sql = "SELECT h.idHistorial, h.idEquipo, e.codigoEquipo, h.detalle " +
                     "FROM historialequipo h " +
                     "JOIN equipo e ON h.idEquipo = e.idEquipo " +
                     "WHERE e.codigoEquipo LIKE ? OR h.detalle LIKE ? " +
                     "ORDER BY h.idHistorial DESC LIMIT ? OFFSET ?";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            String like = "%" + (filtro == null ? "" : filtro) + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setInt(3, size);
            ps.setInt(4, (page - 1) * size);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    HistorialEquipo h = new HistorialEquipo();
                    h.setIdHistorial(rs.getInt("idHistorial"));
                    h.setIdEquipo(rs.getInt("idEquipo"));
                    h.setDetalle(rs.getString("detalle"));
                    lista.add(h);
                }
            }
        }
        return lista;
    }

    @Override
    public int contar(String filtro) throws Exception {
        String sql = "SELECT COUNT(*) FROM historialequipo h JOIN equipo e ON h.idEquipo = e.idEquipo " +
                     "WHERE e.codigoEquipo LIKE ? OR h.detalle LIKE ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            String like = "%" + (filtro == null ? "" : filtro) + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    @Override
    public HistorialEquipo buscarPorId(int id) throws Exception {
        String sql = "SELECT * FROM historialequipo WHERE idHistorial = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                HistorialEquipo h = new HistorialEquipo();
                h.setIdHistorial(rs.getInt("idHistorial"));
                h.setIdEquipo(rs.getInt("idEquipo"));
                h.setDetalle(rs.getString("detalle"));
                return h;
            }
        }
        return null;
    }

    @Override
    public boolean insertar(HistorialEquipo h) throws Exception {
        String sql = "CALL sp_insertar_historial_equipo(?, ?)";
        try (Connection c = DatabaseConnection.getConnection();
             CallableStatement cs = c.prepareCall(sql)) {
            cs.setInt(1, h.getIdEquipo());
            cs.setString(2, h.getDetalle());
            return cs.executeUpdate() > 0;
        }
    }

    @Override
    public boolean actualizar(HistorialEquipo h) throws Exception {
        String sql = "CALL sp_actualizar_historial_equipo(?, ?, ?)";
        try (Connection c = DatabaseConnection.getConnection();
             CallableStatement cs = c.prepareCall(sql)) {
            cs.setInt(1, h.getIdHistorial());
            cs.setInt(2, h.getIdEquipo());
            cs.setString(3, h.getDetalle());
            return cs.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminar(int id) throws Exception {
        String sql = "CALL sp_eliminar_historial_equipo(?)";
        try (Connection c = DatabaseConnection.getConnection();
             CallableStatement cs = c.prepareCall(sql)) {
            cs.setInt(1, id);
            return cs.executeUpdate() > 0;
        }
    }

    @Override
    public List<HistorialEquipo> listar() {
        List<HistorialEquipo> lista = new ArrayList<>();
        String sql = "SELECT idHistorial, idEquipo, detalle FROM historialequipo ORDER BY idHistorial DESC";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                HistorialEquipo h = new HistorialEquipo();
                h.setIdHistorial(rs.getInt("idHistorial"));
                h.setIdEquipo(rs.getInt("idEquipo"));
                h.setDetalle(rs.getString("detalle"));
                lista.add(h);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    
    }




}