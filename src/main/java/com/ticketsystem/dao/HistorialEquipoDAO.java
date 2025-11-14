package com.ticketsystem.dao;

import com.ticketsystem.model.HistorialEquipo;
import com.ticketsystem.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistorialEquipoDAO {

    // ================== INSERTAR ===================
    public boolean insertar(HistorialEquipo h) {
        String sql = "{CALL sp_insertar_historial_equipo(?, ?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, h.getIdEquipo());
            stmt.setString(2, h.getDetalle());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    // ================== ACTUALIZAR ===================
    public boolean actualizar(HistorialEquipo h) {
        String sql = "{CALL sp_actualizar_historial_equipo(?, ?, ?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, h.getIdHistorial());
            stmt.setInt(2, h.getIdEquipo());
            stmt.setString(3, h.getDetalle());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    // ================== ELIMINAR ===================
    public boolean eliminar(int idHistorial) {
        String sql = "{CALL sp_eliminar_historial_equipo(?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idHistorial);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    // ================== OBTENER POR ID ===================
    public HistorialEquipo obtenerPorId(int id) {
        HistorialEquipo h = null;
        String sql = "{CALL sp_obtener_historial_equipo(?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                h = new HistorialEquipo();
                h.setIdHistorial(rs.getInt("idHistorial"));
                h.setIdEquipo(rs.getInt("idEquipo"));
                h.setDetalle(rs.getString("detalle"));
                h.setCodigoEquipo(rs.getString("codigoEquipo"));
                h.setTipoEquipo(rs.getString("tipoEquipo"));
            }

        } catch (Exception e) { e.printStackTrace(); }

        return h;
    }

    // ================== LISTAR ===================
    public List<HistorialEquipo> listar() {
        List<HistorialEquipo> lista = new ArrayList<>();
        String sql = "{CALL sp_listar_historial_equipo()}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                HistorialEquipo h = new HistorialEquipo();
                h.setIdHistorial(rs.getInt("idHistorial"));
                h.setIdEquipo(rs.getInt("idEquipo"));
                h.setDetalle(rs.getString("detalle"));
                h.setCodigoEquipo(rs.getString("codigoEquipo"));
                h.setTipoEquipo(rs.getString("tipoEquipo"));

                lista.add(h);
            }

        } catch (Exception e) { e.printStackTrace(); }

        return lista;
    }
}
