package com.ticketsystem.dao;

import com.ticketsystem.model.User;
import com.ticketsystem.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    
    public User authenticate(String correo, String contrasena) {
        String sql = "SELECT idUsuario, correo, nombre FROM usuario WHERE correo = ? AND contrasena = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, correo);
            stmt.setString(2, contrasena);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setIdUsuario(rs.getInt("idUsuario"));
                    user.setCorreo(rs.getString("correo"));
                    user.setNombre(rs.getString("nombre"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public User getUserByEmail(String email) {
        String sql = "SELECT idUsuario, correo, nombre FROM usuario WHERE correo = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setIdUsuario(rs.getInt("idUsuario"));
                    user.setCorreo(rs.getString("correo"));
                    user.setNombre(rs.getString("nombre"));
                    user.setRol(rs.getString("rol"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT idUsuario, correo, nombre FROM usuario ORDER BY name";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                User user = new User();
                user.setIdUsuario(rs.getInt("idUsuario"));
                user.setCorreo(rs.getString("correo"));
                user.setNombre(rs.getString("nombre"));
                user.setRol(rs.getString("rol"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}