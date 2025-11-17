package com.ticketsystem.dao;

import com.ticketsystem.model.Tecnico;
import com.ticketsystem.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TecnicoDAO implements ITecnicoDAO {

    // Constructor vacío
    public TecnicoDAO() {}

    /**
     * ============================================================
     *  LISTAR TÉCNICOS CON PAGINACIÓN + BÚSQUEDA
     * ============================================================
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
     * ============================================================
     *  CONTAR TÉCNICOS (para paginación)
     * ============================================================
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
     * ============================================================
     *  BUSCAR TÉCNICO POR ID
     * ============================================================
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
     * ============================================================
     *  INSERTAR TÉCNICO (SP)
     * ============================================================
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
     * ============================================================
     *  ACTUALIZAR TÉCNICO (SP)
     * ============================================================
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
     * ============================================================
     *  ELIMINAR TÉCNICO (SP)
     * ============================================================
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
     * ============================================================
     *  TOP TÉCNICOS (Dashboard Admin)
     * ============================================================
     */
    public List<Tecnico> obtenerTopTecnicos(int limite) {
        List<Tecnico> lista = new ArrayList<>();

      String sql =
    "SELECT t.idTecnico, t.nombre, t.especialidad, COUNT(i.idIncidencia) AS totalResueltas " +
    "FROM tecnico t " +
    "LEFT JOIN incidencia i ON t.idTecnico = i.idTecnico " +
    "WHERE i.estado = 'Cerrado' " +
    "GROUP BY t.idTecnico, t.nombre, t.especialidad " +
    "ORDER BY totalResueltas DESC " +
    "LIMIT ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, limite);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Tecnico t = new Tecnico();
                    t.setIdTecnico(rs.getInt("idTecnico"));
                    t.setNombre(rs.getString("nombre"));
                    t.setEspecialidad(rs.getString("especialidad"));
                    t.setTotalResueltas(rs.getInt("totalResueltas"));
                    lista.add(t);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return lista;
    }

    /**
     * ============================================================
     *  LISTAR TODOS (ComboBox, selects)
     * ============================================================
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
