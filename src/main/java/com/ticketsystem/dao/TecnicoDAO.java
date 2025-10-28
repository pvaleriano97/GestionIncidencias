package com.ticketsystem.dao;

import com.ticketsystem.model.Tecnico;
import com.ticketsystem.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TecnicoDAO implements ITecnicoDAO {

    // ✅ Constructor vacío
    public TecnicoDAO() {}

    /**
     * ✅ Listar técnicos con paginación y búsqueda
     */
    public List<Tecnico> listar(int page, int size, String filtro) throws Exception {
        List<Tecnico> lista = new ArrayList<>();
        String sql = "SELECT idTecnico, nombre, especialidad, disponibilidad FROM tecnico " +
                     "WHERE nombre LIKE ? OR especialidad LIKE ? " +
                     "ORDER BY idTecnico DESC LIMIT ? OFFSET ?";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            String like = "%" + (filtro == null ? "" : filtro) + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setInt(3, size);
            ps.setInt(4, (page - 1) * size);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Tecnico t = new Tecnico();
                    t.setIdTecnico(rs.getInt("idTecnico"));
                    t.setNombre(rs.getString("nombre"));
                    t.setEspecialidad(rs.getString("especialidad"));
                    t.setDisponibilidad(rs.getInt("disponibilidad"));
                    lista.add(t);
                }
            }
        }
        return lista;
    }

    /**
     * ✅ Contar registros filtrados (para paginación)
     */
    public int contar(String filtro) throws Exception {
        String sql = "SELECT COUNT(*) FROM tecnico WHERE nombre LIKE ? OR especialidad LIKE ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            String like = "%" + (filtro == null ? "" : filtro) + "%";
            ps.setString(1, like);
            ps.setString(2, like);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }

    /**
     * ✅ Buscar técnico por ID
     */
    public Tecnico buscarPorId(int id) throws Exception {
        String sql = "SELECT idTecnico, nombre, especialidad, disponibilidad FROM tecnico WHERE idTecnico = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Tecnico t = new Tecnico();
                    t.setIdTecnico(rs.getInt("idTecnico"));
                    t.setNombre(rs.getString("nombre"));
                    t.setEspecialidad(rs.getString("especialidad"));
                    t.setDisponibilidad(rs.getInt("disponibilidad"));
                    return t;
                }
            }
        }
        return null;
    }

    /**
     * ✅ Insertar técnico (usando procedimiento almacenado)
     */
    public boolean insertar(Tecnico t) throws Exception {
        String sql = "CALL sp_insertar_tecnico(?, ?, ?)";
        try (Connection c = DatabaseConnection.getConnection();
             CallableStatement cs = c.prepareCall(sql)) {

            cs.setString(1, t.getNombre());
            cs.setString(2, t.getEspecialidad());
            cs.setInt(3, t.getDisponibilidad());
            return cs.executeUpdate() > 0;
        }
    }

    /**
     * ✅ Actualizar técnico (usando procedimiento almacenado)
     */
    public boolean actualizar(Tecnico t) throws Exception {
        String sql = "CALL sp_actualizar_tecnico(?, ?, ?, ?)";
        try (Connection c = DatabaseConnection.getConnection();
             CallableStatement cs = c.prepareCall(sql)) {

            cs.setInt(1, t.getIdTecnico());
            cs.setString(2, t.getNombre());
            cs.setString(3, t.getEspecialidad());
            cs.setInt(4, t.getDisponibilidad());
            return cs.executeUpdate() > 0;
        }
    }

    /**
     * ✅ Eliminar técnico (usando procedimiento almacenado)
     */
    public boolean eliminar(int id) throws Exception {
        String sql = "CALL sp_eliminar_tecnico(?)";
        try (Connection c = DatabaseConnection.getConnection();
             CallableStatement cs = c.prepareCall(sql)) {

            cs.setInt(1, id);
            return cs.executeUpdate() > 0;
        }
    }

    /**
     * ✅ Listar todos (para combos o selects sin paginación)
     */
    public List<Tecnico> listar() {
        List<Tecnico> lista = new ArrayList<>();
        String sql = "SELECT idTecnico, nombre, especialidad, disponibilidad FROM tecnico ORDER BY nombre";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Tecnico t = new Tecnico();
                t.setIdTecnico(rs.getInt("idTecnico"));
                t.setNombre(rs.getString("nombre"));
                t.setEspecialidad(rs.getString("especialidad"));
                t.setDisponibilidad(rs.getInt("disponibilidad"));
                lista.add(t);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }
}
