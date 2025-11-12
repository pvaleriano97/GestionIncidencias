
package com.ticketsystem.dao;

import com.ticketsystem.model.SolicitudRepuesto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class SolicitudRepuestoDAO {
    private final Connection conn;

    public SolicitudRepuestoDAO(Connection conn) {
        this.conn = conn;
    }

    // Obtener todas las solicitudes con el nombre del repuesto asociado
    public List<SolicitudRepuesto> getAllSolicitudesConDetalle() throws SQLException {
        List<SolicitudRepuesto> solicitudList = new ArrayList<>();
        String sql = "SELECT sr.idSolicitud, sr.cantidad, sr.fechaSolicitud, sr.idIncidencia, sr.idRepuesto, r.nombre AS nombreRepuesto " +
                     "FROM solicitudrepuesto sr " +
                     "JOIN repuesto r ON sr.idRepuesto = r.idRepuesto " +
                     "ORDER BY sr.fechaSolicitud DESC";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                SolicitudRepuesto sr = new SolicitudRepuesto();
                sr.setIdSolicitud(rs.getInt("idSolicitud"));
                sr.setCantidad(rs.getInt("cantidad"));
                sr.setFechaSolicitud(rs.getTimestamp("fechaSolicitud"));
                sr.setIdIncidencia(rs.getInt("idIncidencia"));
                sr.setIdRepuesto(rs.getInt("idRepuesto"));
                sr.setNombreRepuesto(rs.getString("nombreRepuesto"));
                solicitudList.add(sr);
            }
        }
        return solicitudList;
    }
}
