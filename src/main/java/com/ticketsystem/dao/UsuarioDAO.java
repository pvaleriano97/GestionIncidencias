package com.ticketsystem.dao;

import com.ticketsystem.model.Usuario;
import com.ticketsystem.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements IUsuarioDAO {

    public UsuarioDAO() {}

    // ✅ Listar con paginación y filtro
    @Override
    public List<Usuario> listar(int page, int size, String filtro) throws Exception {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT idUsuario, nombre, apellido, correo, contrasena, rol, area " +
                     "FROM usuario " +
                     "WHERE nombre LIKE ? OR apellido LIKE ? OR correo LIKE ? OR rol LIKE ? OR area LIKE ? " +
                     "ORDER BY idUsuario DESC LIMIT ? OFFSET ?";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            String like = "%" + (filtro == null ? "" : filtro) + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);
            ps.setString(4, like);
            ps.setString(5, like);
            ps.setInt(6, size);
            ps.setInt(7, (page - 1) * size);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Usuario u = new Usuario();
                    u.setIdUsuario(rs.getInt("idUsuario"));
                    u.setNombre(rs.getString("nombre"));
                    u.setApellido(rs.getString("apellido"));
                    u.setCorreo(rs.getString("correo"));
                    u.setContrasena(rs.getString("contrasena"));
                    u.setRol(rs.getString("rol"));
                    u.setArea(rs.getString("area"));
                    lista.add(u);
                }
            }
        }
        return lista;
    }

    // ✅ Contar registros (para paginación)
    @Override
    public int contar(String filtro) throws Exception {
        String sql = "SELECT COUNT(*) FROM usuario " +
                     "WHERE nombre LIKE ? OR apellido LIKE ? OR correo LIKE ? OR rol LIKE ? OR area LIKE ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            String like = "%" + (filtro == null ? "" : filtro) + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);
            ps.setString(4, like);
            ps.setString(5, like);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }

    // ✅ Buscar por ID
    @Override
    public Usuario buscarPorId(int id) throws Exception {
        String sql = "SELECT idUsuario, nombre, apellido, correo, contrasena, rol, area FROM usuario WHERE idUsuario = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario();
                    u.setIdUsuario(rs.getInt("idUsuario"));
                    u.setNombre(rs.getString("nombre"));
                    u.setApellido(rs.getString("apellido"));
                    u.setCorreo(rs.getString("correo"));
                    u.setContrasena(rs.getString("contrasena"));
                    u.setRol(rs.getString("rol"));
                    u.setArea(rs.getString("area"));
                    return u;
                }
            }
        }
        return null;
    }

    // ✅ Insertar (usa procedimiento almacenado con 6 parámetros)
    @Override
    public boolean insertar(Usuario u) throws Exception {
        String sql = "CALL sp_insertar_usuario(?, ?, ?, ?, ?, ?)";
        try (Connection c = DatabaseConnection.getConnection();
             CallableStatement cs = c.prepareCall(sql)) {

            cs.setString(1, u.getNombre());
            cs.setString(2, u.getApellido());
            cs.setString(3, u.getCorreo());
            cs.setString(4, u.getContrasena());
            cs.setString(5, u.getRol());
            cs.setString(6, u.getArea());
            return cs.executeUpdate() > 0;
        }
    }

    // ✅ Actualizar (usa procedimiento almacenado con 7 parámetros)
    @Override
    public boolean actualizar(Usuario u) throws Exception {
        String sql = "CALL sp_actualizar_usuario(?, ?, ?, ?, ?, ?, ?)";
        try (Connection c = DatabaseConnection.getConnection();
             CallableStatement cs = c.prepareCall(sql)) {

            cs.setInt(1, u.getIdUsuario());
            cs.setString(2, u.getNombre());
            cs.setString(3, u.getApellido());
            cs.setString(4, u.getCorreo());
            cs.setString(5, u.getContrasena());
            cs.setString(6, u.getRol());
            cs.setString(7, u.getArea());
            return cs.executeUpdate() > 0;
        }
    }

    // ✅ Eliminar
    @Override
    public boolean eliminar(int id) throws Exception {
        String sql = "CALL sp_eliminar_usuario(?)";
        try (Connection c = DatabaseConnection.getConnection();
             CallableStatement cs = c.prepareCall(sql)) {

            cs.setInt(1, id);
            return cs.executeUpdate() > 0;
        }
    }

    // ✅ Listar sin paginación (para combos o selects)
    @Override
    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT idUsuario, nombre, apellido, correo, contrasena, rol, area FROM usuario ORDER BY nombre";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("idUsuario"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setCorreo(rs.getString("correo"));
                u.setContrasena(rs.getString("contrasena"));
                u.setRol(rs.getString("rol"));
                u.setArea(rs.getString("area"));
                lista.add(u);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }
       public Usuario authenticate(String correo, String contrasena) {
        String sql = "SELECT * FROM usuario WHERE correo=? AND contrasena=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, correo);
            ps.setString(2, contrasena); // ⚠️ Si usas hash, aplicar aquí

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("idUsuario"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setCorreo(rs.getString("correo"));
                u.setContrasena(rs.getString("contrasena"));
                u.setRol(rs.getString("rol"));
                u.setArea(rs.getString("area"));
                return u;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // usuario no encontrado o error
    }

}
