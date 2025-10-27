package com.ticketsystem.dao;

import com.ticketsystem.model.Equipo;
import com.ticketsystem.util.DatabaseConnection;
import java.sql.*;
import java.util.*;

public class EquipoDAO {

    public List<Equipo> listar() {
        List<Equipo> lista = new ArrayList<>();

        String sql = "SELECT idEquipo, codigoEquipo, tipo, estado FROM equipo ORDER BY codigoEquipo";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Equipo e = new Equipo();
                e.setIdEquipo(rs.getInt("idEquipo"));
                e.setCodigoEquipo(rs.getString("codigoEquipo"));
                e.setTipo(rs.getString("tipo"));
                e.setEstado(rs.getString("estado"));
                lista.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
