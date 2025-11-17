package com.ticketsystem.dao;

import com.ticketsystem.model.Repuesto;
import com.ticketsystem.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepuestoDAO implements IRepuestoDAO {

    @Override
    public List<Repuesto> listar() throws Exception {
        List<Repuesto> lista = new ArrayList<>();
        String sql = "SELECT * FROM repuesto ORDER BY idRepuesto ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Repuesto r = new Repuesto();
                r.setIdRepuesto(rs.getInt("idRepuesto"));
                r.setNombre(rs.getString("nombre"));
                r.setStock(rs.getInt("stock"));
                r.setCosto(rs.getDouble("costo"));
                lista.add(r);
            }
        }
        return lista;
    }

    @Override
   public List<Repuesto> listar(int page, int size, String filtro) throws Exception {
    List<Repuesto> lista = new ArrayList<>();

    String sql = "SELECT * FROM repuesto " +
                 "WHERE nombre LIKE ? " +
                 "ORDER BY idRepuesto ASC " +
                 "LIMIT ? OFFSET ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, "%" + filtro + "%");
        ps.setInt(2, size);                 // número de registros
        ps.setInt(3, (page - 1) * size);     // desde dónde empieza

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Repuesto r = new Repuesto();
            r.setIdRepuesto(rs.getInt("idRepuesto"));
            r.setNombre(rs.getString("nombre"));
            r.setStock(rs.getInt("stock"));
            r.setCosto(rs.getDouble("costo"));

            lista.add(r);
        }
    }
    return lista;
}


    @Override
    public int contar(String filtro) throws Exception {
        String sql = "SELECT COUNT(*) AS total FROM repuesto WHERE nombre LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + filtro + "%");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("total");
        }
        return 0;
    }

    @Override
    public Repuesto obtener(int id) throws Exception {
        String sql = "SELECT * FROM repuesto WHERE idRepuesto=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Repuesto r = new Repuesto();
                r.setIdRepuesto(rs.getInt("idRepuesto"));
                r.setNombre(rs.getString("nombre"));
                r.setStock(rs.getInt("stock"));
                r.setCosto(rs.getDouble("costo"));
                return r;
            }
        }
        return null;
    }

    @Override
    public void insertar(Repuesto r) throws Exception {
        String sql = "INSERT INTO repuesto(nombre, stock, costo) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, r.getNombre());
            ps.setInt(2, r.getStock());
            ps.setDouble(3, r.getCosto());
            ps.executeUpdate();
        }
    }

    @Override
    public void actualizar(Repuesto r) throws Exception {
        String sql = "UPDATE repuesto SET nombre=?, stock=?, costo=? WHERE idRepuesto=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, r.getNombre());
            ps.setInt(2, r.getStock());
            ps.setDouble(3, r.getCosto());
            ps.setInt(4, r.getIdRepuesto());
            ps.executeUpdate();
        }
    }

    @Override
    public void eliminar(int id) throws Exception {
        String sql = "DELETE FROM repuesto WHERE idRepuesto=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public List<Repuesto> listarSimple() throws Exception {
        List<Repuesto> lista = new ArrayList<>();
        String sql = "SELECT idRepuesto, nombre, stock FROM repuesto";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Repuesto r = new Repuesto();
                r.setIdRepuesto(rs.getInt("idRepuesto"));
                r.setNombre(rs.getString("nombre"));
                r.setStock(rs.getInt("stock"));
                lista.add(r);
            }
        }
        return lista;
    }
    public int obtenerStock(int idRepuesto) {
        String sql = "SELECT stock FROM repuesto WHERE idRepuesto=?";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idRepuesto);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) return rs.getInt("stock");
        } catch(Exception e) { e.printStackTrace(); }
        return 0;
    }
}
