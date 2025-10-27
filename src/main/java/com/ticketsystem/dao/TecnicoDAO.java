
package com.ticketsystem.dao;
import com.ticketsystem.model.Tecnico;
import com.ticketsystem.util.DatabaseConnection;
import java.sql.*;
import java.util.*;

public class TecnicoDAO {
    public List<Tecnico> listar() {
        List<Tecnico> lista = new ArrayList<>();

        String sql = "SELECT idTecnico, nombre, especialidad FROM tecnico ORDER BY nombre";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Tecnico t = new Tecnico();
                t.setIdTecnico(rs.getInt("idTecnico"));
                t.setNombre(rs.getString("nombre"));
                t.setEspecialidad(rs.getString("especialidad"));
                lista.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
