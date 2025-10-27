package com.ticketsystem.dao;

import com.ticketsystem.model.Incidencia;
import com.ticketsystem.util.DatabaseConnection;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class IncidenciaDAO {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    // Listar incidencias con búsqueda y paginación
    public List<Incidencia> listar(String search, int offset, int limit) {
        List<Incidencia> lista = new ArrayList<>();
        String sql = "SELECT i.idIncidencia, i.descripcion, i.estado, i.idUsuario, i.idEquipo, i.idTecnico, i.fechaRegistro, " +
                     "u.nombre AS nombreUsuario, " +
                     "e.codigoEquipo, e.tipo AS tipoEquipo, " +
                     "t.nombre AS nombreTecnico " +
                     "FROM incidencia i " +
                     "LEFT JOIN usuario u ON i.idUsuario = u.idUsuario " +
                     "LEFT JOIN equipo e ON i.idEquipo = e.idEquipo " +
                     "LEFT JOIN tecnico t ON i.idTecnico = t.idTecnico " +
                     "WHERE i.descripcion LIKE ? OR i.estado LIKE ? OR u.nombre LIKE ? OR e.codigoEquipo LIKE ? OR e.tipo LIKE ? OR t.nombre LIKE ? " +
                     "ORDER BY i.fechaRegistro DESC " +
                     "LIMIT ? OFFSET ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String filtro = "%" + search + "%";
            for (int i = 1; i <= 6; i++) ps.setString(i, filtro);
            ps.setInt(7, limit);
            ps.setInt(8, offset);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Incidencia iObj = mapearIncidencia(rs);
                lista.add(iObj);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Obtener incidencia por ID
    public Incidencia obtenerPorId(int id) {
        String sql = "SELECT i.idIncidencia, i.descripcion, i.estado, i.idUsuario, i.idEquipo, i.idTecnico, i.fechaRegistro, " +
                     "u.nombre AS nombreUsuario, e.codigoEquipo, e.tipo AS tipoEquipo, t.nombre AS nombreTecnico " +
                     "FROM incidencia i " +
                     "LEFT JOIN usuario u ON i.idUsuario = u.idUsuario " +
                     "LEFT JOIN equipo e ON i.idEquipo = e.idEquipo " +
                     "LEFT JOIN tecnico t ON i.idTecnico = t.idTecnico " +
                     "WHERE i.idIncidencia=?";
        Incidencia i = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                i = mapearIncidencia(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return i;
    }

    // Insertar incidencia (usa SP si existe)
    public void insertar(Incidencia i) {
        String sql = "{CALL sp_insertar_incidencia(?, ?, ?, ?, ?)}";
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, i.getDescripcion());
            cs.setString(2, i.getEstado());
            cs.setInt(3, i.getIdUsuario());
            cs.setInt(4, i.getIdEquipo());
            if (i.getIdTecnico() != null)
                cs.setInt(5, i.getIdTecnico());
            else
                cs.setNull(5, Types.INTEGER);

            cs.execute();

        } catch (SQLException e) {
            // SP no existe, insertar normal
            insertarSinSP(i);
        }
    }

    private void insertarSinSP(Incidencia i) {
        String sql = "INSERT INTO incidencia (descripcion, estado, idUsuario, idEquipo, idTecnico) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, i.getDescripcion());
            ps.setString(2, i.getEstado());
            ps.setInt(3, i.getIdUsuario());
            ps.setInt(4, i.getIdEquipo());
            if (i.getIdTecnico() != null)
                ps.setInt(5, i.getIdTecnico());
            else
                ps.setNull(5, Types.INTEGER);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Actualizar incidencia
    public void actualizar(Incidencia i) {
        String sql = "{CALL sp_actualizar_incidencia(?, ?, ?, ?, ?, ?)}";
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, i.getIdIncidencia());
            cs.setString(2, i.getDescripcion());
            cs.setString(3, i.getEstado());
            cs.setInt(4, i.getIdUsuario());
            cs.setInt(5, i.getIdEquipo());
            if (i.getIdTecnico() != null)
                cs.setInt(6, i.getIdTecnico());
            else
                cs.setNull(6, Types.INTEGER);

            cs.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Eliminar incidencia
    public void eliminar(int id) {
        String sql = "DELETE FROM incidencia WHERE idIncidencia=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Contar registros para paginación
    public int contarRegistros(String search) {
        String sql = "SELECT COUNT(*) FROM incidencia i " +
                     "LEFT JOIN usuario u ON i.idUsuario = u.idUsuario " +
                     "LEFT JOIN equipo e ON i.idEquipo = e.idEquipo " +
                     "LEFT JOIN tecnico t ON i.idTecnico = t.idTecnico " +
                     "WHERE i.descripcion LIKE ? OR i.estado LIKE ? OR u.nombre LIKE ? OR e.codigoEquipo LIKE ? OR e.tipo LIKE ? OR t.nombre LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String filtro = "%" + search + "%";
            for (int i = 1; i <= 6; i++) ps.setString(i, filtro);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Mapea ResultSet a objeto Incidencia
private Incidencia mapearIncidencia(ResultSet rs) throws SQLException {
    Incidencia i = new Incidencia();
    i.setIdIncidencia(rs.getInt("idIncidencia"));
    i.setDescripcion(rs.getString("descripcion"));
    i.setEstado(rs.getString("estado"));
    i.setIdUsuario(rs.getInt("idUsuario"));
    i.setIdEquipo(rs.getInt("idEquipo"));
    int tecnicoId = rs.getInt("idTecnico");
    if (!rs.wasNull()) i.setIdTecnico(tecnicoId);
    i.setFechaRegistro(rs.getTimestamp("fechaRegistro")); // <-- Esto ya setea fechaRegistroStr

    i.setNombreUsuario(rs.getString("nombreUsuario"));
    i.setCodigoEquipo(rs.getString("codigoEquipo"));
    i.setTipoEquipo(rs.getString("tipoEquipo"));
    i.setNombreTecnico(rs.getString("nombreTecnico"));
    return i;
}
}
