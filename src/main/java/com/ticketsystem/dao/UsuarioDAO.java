package com.ticketsystem.dao;

import com.ticketsystem.model.Tecnico;
import com.ticketsystem.model.Usuario;
import com.ticketsystem.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//Agregado
import org.apache.commons.codec.digest.DigestUtils;

public class UsuarioDAO implements IUsuarioDAO {

    public UsuarioDAO() {}

    /**
     * Adapter para los tests: misma lógica que authenticate(...) pero con la firma que esperan.
     * JUnit llama a este método.
     */
    public Usuario autenticar(String correo, String contrasena) {
        // Normalización simple para evitar falsos negativos por espacios
        String c = (correo == null) ? null : correo.trim();
        String p = (contrasena == null) ? null : contrasena.trim();

        // NO encriptar aquí - el método authenticate lo hará
        return authenticate(c, p);
    }

    /**
     * Método original (lo conservamos para compatibilidad con tu código actual).
     */
    public Usuario authenticate(String correo, String contrasena) {
        String sql = "SELECT * FROM usuario WHERE correo = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();

            //Corregindo
            if (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("idUsuario"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setCorreo(rs.getString("correo"));
                u.setContrasena(rs.getString("contrasena")); // Hash de la BD
                u.setRol(rs.getString("rol"));
                u.setArea(rs.getString("area"));

                // Encriptar la contraseña ingresada y comparar con el hash de la BD -- Pedro Valeriano
                String contrasenaEncriptada = DigestUtils.sha256Hex(contrasena);

                if (contrasenaEncriptada.equals(u.getContrasena())) {
                    return u;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ============================================================
    // LISTAR CON PAGINACIÓN + FILTRO
    // ============================================================
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

    // ============================================================
    // CONTAR USUARIOS
    // ============================================================
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

    // ============================================================
    // BUSCAR POR ID
    // ============================================================
    @Override
    public Usuario buscarPorId(int id) throws Exception {
        String sql = "SELECT * FROM usuario WHERE idUsuario = ?";

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

    // ============================================================
    // INSERTAR
    // ============================================================
    @Override
    public boolean insertar(Usuario u) throws Exception {
        String sql = "CALL sp_insertar_usuario(?, ?, ?, ?, ?, ?)";

        try (Connection c = DatabaseConnection.getConnection();
             CallableStatement cs = c.prepareCall(sql)) {

            cs.setString(1, u.getNombre());
            cs.setString(2, u.getApellido());
            cs.setString(3, u.getCorreo());
            // Encriptar la contraseña antes de actualizar
            String contrasenaEncriptada = DigestUtils.sha256Hex(u.getContrasena());
            cs.setString(5, contrasenaEncriptada);
            cs.setString(5, u.getRol());
            cs.setString(6, u.getArea());

            return cs.executeUpdate() > 0;
        }
    }

    // ============================================================
    // ACTUALIZAR
    // ============================================================
    @Override
    public boolean actualizar(Usuario u) throws Exception {
        String sql = "CALL sp_actualizar_usuario(?, ?, ?, ?, ?, ?, ?)";

        try (Connection c = DatabaseConnection.getConnection();
             CallableStatement cs = c.prepareCall(sql)) {

            cs.setInt(1, u.getIdUsuario());
            cs.setString(2, u.getNombre());
            cs.setString(3, u.getApellido());
            cs.setString(4, u.getCorreo());
            // Encriptar la contraseña antes de actualizar
            String contrasenaEncriptada = DigestUtils.sha256Hex(u.getContrasena());
            cs.setString(5, contrasenaEncriptada);
            cs.setString(6, u.getRol());
            cs.setString(7, u.getArea());

            return cs.executeUpdate() > 0;
        }
    }

    // ============================================================
    // ELIMINAR
    // ============================================================
    @Override
    public boolean eliminar(int id) throws Exception {
        String sql = "CALL sp_eliminar_usuario(?)";

        try (Connection c = DatabaseConnection.getConnection();
             CallableStatement cs = c.prepareCall(sql)) {

            cs.setInt(1, id);
            return cs.executeUpdate() > 0;
        }
    }

    // ============================================================
    // LISTAR TODOS (Combos / selects)
    // ============================================================
    @Override
    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();

        String sql = "SELECT idUsuario, nombre, apellido, correo, rol, area FROM usuario ORDER BY nombre";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("idUsuario"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setCorreo(rs.getString("correo"));
                u.setRol(rs.getString("rol"));
                u.setArea(rs.getString("area"));
                lista.add(u);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return lista;
    }

    // ============================================================
    // LOGIN / relación con Técnico por idUsuario
    // ============================================================
    public Tecnico buscarPorIdUsuario(int idUsuario) {
        String sql = "SELECT t.idTecnico, t.nombre, t.idUsuario " +
                     "FROM tecnico t " +
                     "JOIN usuario u ON t.idUsuario = u.idUsuario " +
                     "WHERE u.idUsuario = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Tecnico t = new Tecnico();
                    t.setIdTecnico(rs.getInt("idTecnico"));
                    t.setNombre(rs.getString("nombre"));
                    t.setIdUsuario(rs.getInt("idUsuario"));
                    return t;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // ============================================================
    // LISTAR SOLO USUARIOS CON ROL TÉCNICO
    // ============================================================
    public List<Usuario> listarTecnicos() {
        List<Usuario> lista = new ArrayList<>();

        String sql = "SELECT idUsuario, nombre, apellido, correo " +
                     "FROM usuario WHERE rol = 'tecnico' ORDER BY nombre";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("idUsuario"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setCorreo(rs.getString("correo"));
                lista.add(u);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}

