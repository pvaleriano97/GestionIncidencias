package com.ticketsystem.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/gestion_incidencias";
    private static final String USER = "root";
    private static final String PASSWORD = "root";  // ← VERIFICA ESTA CONTRASEÑA
    
    public static Connection getConnection() throws SQLException {
        System.out.println("🔧 Intentando conectar a la base de datos...");
        System.out.println("URL: " + URL);
        System.out.println("Usuario: " + USER);
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✅ Driver MySQL cargado correctamente");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ ERROR: Driver MySQL no encontrado");
            throw new SQLException("MySQL Driver not found", e);
        }
        
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ CONEXIÓN EXITOSA a la base de datos");
            return conn;
        } catch (SQLException e) {
            System.out.println("❌ ERROR de conexión: " + e.getMessage());
            throw e;
        }
    }
}