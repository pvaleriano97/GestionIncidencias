
package com.ticketsystem.dao;
import com.ticketsystem.model.InformeTecnico;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class InformeTecnicoDAO {
    private final Connection conn;

    public InformeTecnicoDAO(Connection conn) {
        this.conn = conn;
    }

    // Obtener todos los informes con el nombre del técnico asociado
    public List<InformeTecnico> getInformesConDetalle() throws SQLException {
        List<InformeTecnico> informeList = new ArrayList<>();
        String sql = "SELECT it.idInforme, it.fechaCierre, it.observaciones, it.idIncidencia, t.nombre AS nombreTecnico " +
                     "FROM informetecnico it " +
                     "JOIN incidencia i ON it.idIncidencia = i.idIncidencia " +
                     "JOIN tecnico t ON i.idTecnicoAsignado = t.idTecnico " + // Asumiendo que la incidencia tiene el técnico asignado
                     "ORDER BY it.fechaCierre DESC";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                InformeTecnico it = new InformeTecnico();
                it.setIdInforme(rs.getInt("idInforme"));
                it.setFechaCierre(rs.getTimestamp("fechaCierre"));
                it.setObservaciones(rs.getString("observaciones"));
                it.setIdIncidencia(rs.getInt("idIncidencia"));
                it.setNombreTecnico(rs.getString("nombreTecnico"));
                informeList.add(it);
            }
        }
        return informeList;
    }
}
