package com.ticketsystem.dao;

import com.ticketsystem.model.Equipo;
import com.ticketsystem.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipoDAO implements IEquipoDAO {

    // ✅ Constructor vacío (usa DatabaseConnection)
    public EquipoDAO() {}

    public List<Equipo> listar(int page, int size, String filtro) throws Exception {
        List<Equipo> lista = new ArrayList<>();
        String sql = "SELECT idEquipo, codigoEquipo, tipo, estado FROM equipo " +
                     "WHERE codigoEquipo LIKE ? OR tipo LIKE ? OR estado LIKE ? " +
                     "ORDER BY idEquipo DESC LIMIT ? OFFSET ?";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            String like = "%" + (filtro == null ? "" : filtro) + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);
            ps.setInt(4, size);
            ps.setInt(5, (page - 1) * size);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Equipo e = new Equipo();
                    e.setIdEquipo(rs.getInt("idEquipo"));
                    e.setCodigoEquipo(rs.getString("codigoEquipo"));
                    e.setTipo(rs.getString("tipo"));
                    e.setEstado(rs.getString("estado"));
                    lista.add(e);
                }
            }
        }
        return lista;
    }

    public int contar(String filtro) throws Exception {
        String sql = "SELECT COUNT(*) FROM equipo WHERE codigoEquipo LIKE ? OR tipo LIKE ? OR estado LIKE ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            String like = "%" + (filtro == null ? "" : filtro) + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }

    public Equipo buscarPorId(int id) throws Exception {
        String sql = "SELECT idEquipo, codigoEquipo, tipo, estado FROM equipo WHERE idEquipo = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Equipo e = new Equipo();
                    e.setIdEquipo(rs.getInt("idEquipo"));
                    e.setCodigoEquipo(rs.getString("codigoEquipo"));
                    e.setTipo(rs.getString("tipo"));
                    e.setEstado(rs.getString("estado"));
                    return e;
                }
            }
        }
        return null;
    }

    public boolean insertar(Equipo e) throws Exception {
        String sql = "CALL sp_insertar_equipo(?,?,?)";
        try (Connection c = DatabaseConnection.getConnection();
             CallableStatement cs = c.prepareCall(sql)) {

            cs.setString(1, e.getCodigoEquipo());
            cs.setString(2, e.getTipo());
            cs.setString(3, e.getEstado());
            return cs.executeUpdate() > 0;
        }
    }

    public boolean actualizar(Equipo e) throws Exception {
        String sql = "CALL sp_actualizar_equipo(?,?,?,?)";
        try (Connection c = DatabaseConnection.getConnection();
             CallableStatement cs = c.prepareCall(sql)) {

            cs.setInt(1, e.getIdEquipo());
            cs.setString(2, e.getCodigoEquipo());
            cs.setString(3, e.getTipo());
            cs.setString(4, e.getEstado());
            return cs.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws Exception {
        String sql = "CALL sp_eliminar_equipo(?)";
        try (Connection c = DatabaseConnection.getConnection();
             CallableStatement cs = c.prepareCall(sql)) {

            cs.setInt(1, id);
            return cs.executeUpdate() > 0;
        }
    }

    /**
     * Método adicional para combos o listados simples sin paginación.
     */
    public List<Equipo> listar() {
        List<Equipo> lista = new ArrayList<>();
        String sql = "SELECT idEquipo, codigoEquipo, tipo, estado FROM equipo ORDER BY codigoEquipo";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Equipo e = new Equipo();
                e.setIdEquipo(rs.getInt("idEquipo"));
                e.setCodigoEquipo(rs.getString("codigoEquipo"));
                e.setTipo(rs.getString("tipo"));
                e.setEstado(rs.getString("estado"));
                lista.add(e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }
}
