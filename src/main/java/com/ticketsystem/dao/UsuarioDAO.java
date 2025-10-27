/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ticketsystem.dao;

import com.ticketsystem.model.Usuario;
import com.ticketsystem.util.DatabaseConnection;
import java.sql.*;
import java.util.*;

public class UsuarioDAO {
      public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();

        String sql = "SELECT idUsuario, nombre, correo, rol FROM usuario ORDER BY nombre";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("idUsuario"));
                u.setNombre(rs.getString("nombre"));
                u.setCorreo(rs.getString("correo"));
                u.setRol(rs.getString("rol"));
                lista.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
