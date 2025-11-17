
package com.ticketsystem.dao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class ReportesDAO {
    private final Connection conn;

    public ReportesDAO(Connection conn) {
        this.conn = conn;
    }

    // Reporte 1: Incidencias Abiertas por Mes (retorna mes y cuenta)
    public List<Map<String, Object>> getIncidenciasPorMes() throws SQLException {
        List<Map<String, Object>> data = new ArrayList<>();
        // Asumiendo que 'fechaRegistro' es de tipo DATETIME o TIMESTAMP
        String sql = "SELECT DATE_FORMAT(fechaRegistro, '%Y-%m') as mes, COUNT(*) as total " +
                     "FROM incidencia " +
                     "GROUP BY mes " +
                     "ORDER BY mes ASC";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Map<String, Object> item = new HashMap<>();
                item.put("label", rs.getString("mes"));
                item.put("value", rs.getInt("total"));
                data.add(item);
            }
        }
        return data;
    }

    // Reporte 2: Top 5 Repuestos más Solicitados (retorna nombre y total)
    public List<Map<String, Object>> getTopRepuestos(int limit) throws SQLException {
        List<Map<String, Object>> data = new ArrayList<>();
        String sql = "SELECT r.nombre, SUM(sr.cantidad) AS total " +
                     "FROM solicitudrepuesto sr " +
                     "JOIN repuesto r ON sr.idRepuesto = r.idRepuesto " +
                     "GROUP BY r.nombre " +
                     "ORDER BY total DESC " +
                     "LIMIT " + limit;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Map<String, Object> item = new HashMap<>();
                item.put("label", rs.getString("nombre"));
                item.put("value", rs.getInt("total"));
                data.add(item);
            }
        }
        return data;
    }
    
}
