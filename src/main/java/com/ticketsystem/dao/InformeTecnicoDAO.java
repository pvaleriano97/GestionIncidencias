package com.ticketsystem.dao;

import com.ticketsystem.model.InformeTecnico;
import com.ticketsystem.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InformeTecnicoDAO {

    // LISTAR (sin paginar) - sigue disponible
    public List<InformeTecnico> listar() {
        List<InformeTecnico> lista = new ArrayList<>();
        String sql = "{CALL sp_listar_informe_tecnico()}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cs = conn.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                InformeTecnico it = mapearInforme(rs);
                lista.add(it);
            }

        } catch (SQLException e) {
            System.err.println("ERROR InformeTecnicoDAO.listar: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    // MAPEAR RESULTSET -> OBJETO
    private InformeTecnico mapearInforme(ResultSet rs) throws SQLException {
        InformeTecnico it = new InformeTecnico();
        it.setIdInforme(rs.getInt("idInforme"));
        it.setFechaCierre(rs.getString("fechaCierre"));
        it.setObservaciones(rs.getString("observaciones"));
        it.setIdIncidencia(rs.getInt("idIncidencia"));
        // algunos SPs devuelven nombreTecnico y descripcionIncidencia con esos alias
        try { it.setIdTecnico(rs.getInt("idTecnico")); } catch (SQLException ignored) {}
        try { it.setDescripcionIncidencia(rs.getString("descripcionIncidencia")); } catch (SQLException ignored) {}
        try { it.setNombreTecnico(rs.getString("nombreTecnico")); } catch (SQLException ignored) {}
        return it;
    }

    // ================================
    // LISTAR PAGINADO + BÚSQUEDA (server-side)
    // ================================
    public List<InformeTecnico> listarPaginado(String search, int offset, int limit) {
        List<InformeTecnico> lista = new ArrayList<>();
        // Buscamos por descripcionIncidencia, nombreTecnico, observaciones o fecha (texto)
        String sql =
            "SELECT it.idInforme, it.fechaCierre, it.observaciones, it.idIncidencia, it.idTecnico, " +
            "i.descripcion AS descripcionIncidencia, t.nombre AS nombreTecnico " +
            "FROM informetecnico it " +
            "LEFT JOIN incidencia i ON i.idIncidencia = it.idIncidencia " +
            "LEFT JOIN tecnico t ON t.idTecnico = it.idTecnico " +
            "WHERE (i.descripcion LIKE ? OR t.nombre LIKE ? OR it.observaciones LIKE ? OR DATE_FORMAT(it.fechaCierre, '%Y-%m-%d %H:%i') LIKE ?) " +
            "ORDER BY it.fechaCierre DESC LIMIT ? OFFSET ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String filtro = "%" + (search == null ? "" : search.trim()) + "%";
            ps.setString(1, filtro);
            ps.setString(2, filtro);
            ps.setString(3, filtro);
            ps.setString(4, filtro);
            ps.setInt(5, limit);
            ps.setInt(6, offset);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                InformeTecnico it = mapearInforme(rs);
                lista.add(it);
            }

        } catch (SQLException e) {
            System.err.println("ERROR InformeTecnicoDAO.listarPaginado: " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }

    // ================================
    // CONTAR REGISTROS (para paginación)
    // ================================
    public int contarRegistros(String search) {
        String sql =
            "SELECT COUNT(*) FROM informetecnico it " +
            "LEFT JOIN incidencia i ON i.idIncidencia = it.idIncidencia " +
            "LEFT JOIN tecnico t ON t.idTecnico = it.idTecnico " +
            "WHERE (i.descripcion LIKE ? OR t.nombre LIKE ? OR it.observaciones LIKE ? OR DATE_FORMAT(it.fechaCierre, '%Y-%m-%d %H:%i') LIKE ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String filtro = "%" + (search == null ? "" : search.trim()) + "%";
            ps.setString(1, filtro);
            ps.setString(2, filtro);
            ps.setString(3, filtro);
            ps.setString(4, filtro);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            System.err.println("ERROR InformeTecnicoDAO.contarRegistros: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    // (INSERTAR / ACTUALIZAR / ELIMINAR usando SP ya mostrados)
    public boolean insertar(InformeTecnico it) {
        String sql = "{CALL sp_insertar_informe_tecnico(?, ?, ?, ?)}";
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, it.getFechaCierre());
            cs.setString(2, it.getObservaciones());
            cs.setInt(3, it.getIdIncidencia());
            cs.setInt(4, it.getIdTecnico());
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("ERROR InformeTecnicoDAO.insertar: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(InformeTecnico it) {
        String sql = "{CALL sp_actualizar_informe_tecnico(?, ?, ?, ?, ?)}";
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, it.getIdInforme());
            cs.setString(2, it.getFechaCierre());
            cs.setString(3, it.getObservaciones());
            cs.setInt(4, it.getIdIncidencia());
            cs.setInt(5, it.getIdTecnico());
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("ERROR InformeTecnicoDAO.actualizar: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "{CALL sp_eliminar_informe_tecnico(?)}";
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, id);
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("ERROR InformeTecnicoDAO.eliminar: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public InformeTecnico obtenerPorId(int id) {
    InformeTecnico it = null;
    String sql = "{CALL sp_obtener_informe_tecnico(?)}";

    try (Connection conn = DatabaseConnection.getConnection();
         CallableStatement cs = conn.prepareCall(sql)) {

        cs.setInt(1, id);

        try (ResultSet rs = cs.executeQuery()) {
            if (rs.next()) {
                it = new InformeTecnico();
                // Mapear con los nombres/alias tal como los devuelve tu SP
                it.setIdInforme(rs.getInt("idInforme"));
                it.setFechaCierre(rs.getString("fechaCierre"));
                it.setObservaciones(rs.getString("observaciones"));

                // idIncidencia puede venir como INT
                int idInc = rs.getInt("idIncidencia");
                if (!rs.wasNull()) it.setIdIncidencia(idInc);

                // idTecnico puede venir null
                int idTec = rs.getInt("idTecnico");
                if (!rs.wasNull()) it.setIdTecnico(idTec);

                // Alias devueltos por el SP
                try { it.setDescripcionIncidencia(rs.getString("descripcionIncidencia")); } catch (SQLException ignore) {}
                try { it.setNombreTecnico(rs.getString("nombreTecnico")); } catch (SQLException ignore) {}
            }
        }

    } catch (SQLException e) {
        // Log completo para depuración
        System.err.println("ERROR InformeTecnicoDAO.obtenerPorId id=" + id + " -> " + e.getMessage());
        e.printStackTrace();
    }
    return it;
}
}
