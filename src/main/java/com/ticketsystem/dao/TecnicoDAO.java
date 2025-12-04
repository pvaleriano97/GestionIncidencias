package com.ticketsystem.dao;

import com.ticketsystem.model.Tecnico;
import com.ticketsystem.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TecnicoDAO implements ITecnicoDAO {

    public TecnicoDAO() {}

    // ============================================================
    // LISTAR CON PAGINACIÓN + BUSQUEDA
    // ============================================================
    @Override
    public List<Tecnico> listar(int page, int size, String filtro) throws Exception {
        List<Tecnico> lista = new ArrayList<>();

        String sql = "SELECT idTecnico, nombre, especialidad, disponibilidad, idUsuario "
                   + "FROM tecnico "
                   + "WHERE nombre LIKE ? OR especialidad LIKE ? "
                   + "ORDER BY idTecnico DESC LIMIT ? OFFSET ?";

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
                    t.setIdUsuario(rs.getInt("idUsuario"));
                    lista.add(t);
                }
            }
        }
        return lista;
    }

    // ============================================================
    // CONTAR REGISTROS
    // ============================================================
    @Override
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

    // ============================================================
    // BUSCAR POR ID
    // ============================================================
    @Override
    public Tecnico buscarPorId(int id) throws Exception {
        String sql = "SELECT idTecnico, nombre, especialidad, disponibilidad, idUsuario "
                   + "FROM tecnico WHERE idTecnico = ?";

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
                    t.setIdUsuario(rs.getInt("idUsuario"));
                    return t;
                }
            }
        }
        return null;
    }

    // ============================================================
    // INSERTAR
    // ============================================================
    @Override
    public boolean insertar(Tecnico t) throws Exception {
        String sql = "INSERT INTO tecnico (nombre, especialidad, disponibilidad, idUsuario) "
                   + "VALUES (?, ?, ?, ?)";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, t.getNombre());
            ps.setString(2, t.getEspecialidad());
            ps.setInt(3, t.getDisponibilidad());
            ps.setInt(4, t.getIdUsuario());

            return ps.executeUpdate() > 0;
        }
    }

    // ============================================================
    // ACTUALIZAR
    // ============================================================
    @Override
    public boolean actualizar(Tecnico t) throws Exception {
        String sql = "UPDATE tecnico SET nombre=?, especialidad=?, disponibilidad=?, idUsuario=? "
                   + "WHERE idTecnico=?";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, t.getNombre());
            ps.setString(2, t.getEspecialidad());
            ps.setInt(3, t.getDisponibilidad());
            ps.setInt(4, t.getIdUsuario());
            ps.setInt(5, t.getIdTecnico());

            return ps.executeUpdate() > 0;
        }
    }

    // ============================================================
    // ELIMINAR
    // ============================================================
    @Override
    public boolean eliminar(int id) throws Exception {
        String sql = "DELETE FROM tecnico WHERE idTecnico=?";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // ============================================================
    // LISTAR TODOS (para combos)
    // ============================================================
    @Override
    public List<Tecnico> listar() {
        List<Tecnico> lista = new ArrayList<>();

        String sql = "SELECT idTecnico, nombre, especialidad, disponibilidad, idUsuario "
                   + "FROM tecnico ORDER BY nombre";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Tecnico t = new Tecnico();
                t.setIdTecnico(rs.getInt("idTecnico"));
                t.setNombre(rs.getString("nombre"));
                t.setEspecialidad(rs.getString("especialidad"));
                t.setDisponibilidad(rs.getInt("disponibilidad"));
                t.setIdUsuario(rs.getInt("idUsuario"));
                lista.add(t);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return lista;
    }

    // ============================================================
    // TOP TECNICOS (dashboard admin)
    // ============================================================
    @Override
    public List<Tecnico> obtenerTopTecnicos(int limite) {
        List<Tecnico> lista = new ArrayList<>();

        String sql = "SELECT t.idTecnico, t.nombre, t.especialidad, "
                   + "COUNT(i.idIncidencia) AS totalResueltas "
                   + "FROM tecnico t "
                   + "LEFT JOIN incidencia i ON t.idTecnico = i.idTecnico "
                   + "AND LOWER(i.estado) LIKE 'cerrad%' "
                   + "GROUP BY t.idTecnico, t.nombre, t.especialidad "
                   + "ORDER BY totalResueltas DESC LIMIT ?";

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

    // ============================================================
    // BUSCAR TÉCNICO POR ID DE USUARIO
    // ============================================================
    @Override
  public Tecnico buscarPorIdUsuario(int idUsuario) {

    String sql = "SELECT idTecnico, nombre, idUsuario FROM tecnico WHERE idUsuario = ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, idUsuario);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Tecnico t = new Tecnico();
            t.setIdTecnico(rs.getInt("idTecnico"));
            t.setNombre(rs.getString("nombre"));
            t.setIdUsuario(rs.getInt("idUsuario"));
            return t;
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return null;
}
}