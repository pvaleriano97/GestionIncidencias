package com.ticketsystem.dao;

import com.ticketsystem.model.HistorialEquipo;
import com.ticketsystem.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistorialEquipoDAO implements IHistorialEquipoDAO {

    @Override
    public boolean insertar(HistorialEquipo h) throws Exception {
        String sql = "{ CALL sp_insertar_historial_equipo(?, ?) }";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, h.getIdEquipo());
            cs.setString(2, h.getDetalle());

            return cs.executeUpdate() > 0;
        }
    }

    @Override
    public boolean actualizar(HistorialEquipo h) throws Exception {
        String sql = "{ CALL sp_actualizar_historial_equipo(?, ?, ?) }";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, h.getIdHistorial());
            cs.setInt(2, h.getIdEquipo());
            cs.setString(3, h.getDetalle());

            return cs.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminar(int id) throws Exception {
        String sql = "{ CALL sp_eliminar_historial_equipo(?) }";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, id);
            return cs.executeUpdate() > 0;
        }
    }

    @Override
    public HistorialEquipo buscarPorId(int id) throws Exception {

        String sql = "SELECT h.idHistorial, h.idEquipo, h.detalle, " +
                     "e.codigoEquipo, e.tipo " +
                     "FROM historialequipo h " +
                     "JOIN equipo e ON h.idEquipo = e.idEquipo " +
                     "WHERE h.idHistorial=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                HistorialEquipo h = new HistorialEquipo();
                h.setIdHistorial(rs.getInt("idHistorial"));
                h.setIdEquipo(rs.getInt("idEquipo"));
                h.setDetalle(rs.getString("detalle"));
                h.setCodigoEquipo(rs.getString("codigoEquipo"));
                h.setTipoEquipo(rs.getString("tipo"));
                return h;
            }
        }
        return null;
    }

    @Override
    public List<HistorialEquipo> listar() throws Exception {

        List<HistorialEquipo> lista = new ArrayList<>();

        String sql = "SELECT h.idHistorial, h.idEquipo, h.detalle, " +
                     "e.codigoEquipo, e.tipo " +
                     "FROM historialequipo h " +
                     "JOIN equipo e ON h.idEquipo = e.idEquipo " +
                     "ORDER BY h.idHistorial DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                HistorialEquipo h = new HistorialEquipo();
                h.setIdHistorial(rs.getInt("idHistorial"));
                h.setIdEquipo(rs.getInt("idEquipo"));
                h.setDetalle(rs.getString("detalle"));
                h.setCodigoEquipo(rs.getString("codigoEquipo"));
                h.setTipoEquipo(rs.getString("tipo"));
                lista.add(h);
            }
        }
        return lista;
    }

    @Override
    public List<HistorialEquipo> listarPorEquipo(int idEquipo) throws Exception {

        List<HistorialEquipo> lista = new ArrayList<>();

        String sql = "SELECT idHistorial, idEquipo, detalle " +
                     "FROM historialequipo WHERE idEquipo=? ORDER BY idHistorial DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idEquipo);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                HistorialEquipo h = new HistorialEquipo();
                h.setIdHistorial(rs.getInt("idHistorial"));
                h.setIdEquipo(rs.getInt("idEquipo"));
                h.setDetalle(rs.getString("detalle"));
                lista.add(h);
            }
        }
        return lista;
    }
}
