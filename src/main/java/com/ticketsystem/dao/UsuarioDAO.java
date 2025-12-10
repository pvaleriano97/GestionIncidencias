package com.ticketsystem.dao;

import com.ticketsystem.model.Usuario;
import com.ticketsystem.model.Tecnico;
import com.ticketsystem.util.DatabaseConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import com.ticketsystem.util.TwoFactorAuth;


public class UsuarioDAO implements IUsuarioDAO {

    // ============================================================
    // LOGIN
    // ============================================================

    public Usuario autenticar(String correo, String password) {
        String sql = "SELECT * FROM usuario WHERE correo = ? AND contrasena = ?";
        String passwordHash = DigestUtils.sha256Hex(password);

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, correo);
            ps.setString(2, passwordHash);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("idUsuario"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setCorreo(rs.getString("correo"));
                u.setRol(rs.getString("rol"));
                u.setArea(rs.getString("area"));
                u.setSecret_key(rs.getString("secret_key"));
                u.setTwofa_enabled(rs.getInt("twofa_enabled"));
                return u;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
public List<Usuario> listarTodos() {
    List<Usuario> lista = new ArrayList<>();
    String sql = "SELECT * FROM usuario";
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
            u.setSecret_key(rs.getString("secret_key"));
            u.setTwofa_enabled(rs.getInt("twofa_enabled"));
            lista.add(u);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return lista;
}

public void actualizarSecretKey(Usuario usuario) {
    String sql = "UPDATE usuario SET secret_key = ?, twofa_enabled = ? WHERE idUsuario = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, usuario.getSecret_key());
        ps.setInt(2, usuario.getTwofa_enabled()); // 1 = activado, 0 = desactivado
        ps.setInt(3, usuario.getIdUsuario());

        ps.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    // ============================================================
    // CRUD
    // ============================================================
    @Override
    public boolean insertar(Usuario u) throws Exception {
        String sql = "INSERT INTO usuario (nombre, apellido, correo, contrasena, rol, area) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setString(3, u.getCorreo());
            ps.setString(4, DigestUtils.sha256Hex(u.getContrasena()));
            ps.setString(5, u.getRol());
            ps.setString(6, u.getArea());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
  public boolean actualizar(Usuario u) throws Exception {

    boolean cambiarContrasena = u.getContrasena() != null && !u.getContrasena().isEmpty();
    String sql;

    if (cambiarContrasena) {
        sql = "UPDATE usuario SET nombre=?, apellido=?, correo=?, contrasena=?, rol=?, area=? WHERE idUsuario=?";
    } else {
        sql = "UPDATE usuario SET nombre=?, apellido=?, correo=?, rol=?, area=? WHERE idUsuario=?";
    }

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, u.getNombre());
        ps.setString(2, u.getApellido());
        ps.setString(3, u.getCorreo());

        int index = 4;

        if (cambiarContrasena) {
            ps.setString(index++, DigestUtils.sha256Hex(u.getContrasena()));
        }

        ps.setString(index++, u.getRol());
        ps.setString(index++, u.getArea());
        ps.setInt(index, u.getIdUsuario());

        int filas = ps.executeUpdate();

        // ⚠ SI CAMBIÓ CONTRASEÑA → RESET 2FA
        if (filas > 0 && cambiarContrasena) {
            resetear2FA(u); 
        }

        return filas > 0;
    }
}

    @Override
    public boolean eliminar(int id) throws Exception {
        String sql = "DELETE FROM usuario WHERE idUsuario=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public Usuario buscarPorId(int id) throws Exception {
    String sql = "SELECT * FROM usuario WHERE idUsuario=?";
    
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Usuario u = new Usuario();
            u.setIdUsuario(rs.getInt("idUsuario"));
            u.setNombre(rs.getString("nombre"));
            u.setApellido(rs.getString("apellido"));
            u.setCorreo(rs.getString("correo"));
            u.setRol(rs.getString("rol"));
            u.setArea(rs.getString("area"));
            u.setContrasena(rs.getString("contrasena"));
            
            // ⚠ CAMPOS QUE ESTABAN FALTANDO
            u.setSecret_key(rs.getString("secret_key"));
            u.setTwofa_enabled(rs.getInt("twofa_enabled"));
            u.setTokenRecuperacion(rs.getString("token_recuperacion"));
            u.setExpiraToken(rs.getTimestamp("expira_token"));

            return u;
        }
    }
    return null;
}


    // ============================================================
    // LISTADOS
    // ============================================================
    @Override
    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT idUsuario, nombre, apellido, correo, rol, area FROM usuario";

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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

   public List<Usuario> listar(int page, int size, String filtro) {

    List<Usuario> lista = new ArrayList<>();
    int offset = (page - 1) * size;

    String sql = "SELECT idUsuario, nombre, apellido, correo, rol, area  FROM usuario  WHERE nombre LIKE ?  OR apellido LIKE ?  OR correo LIKE ?  ORDER BY idUsuario DESC LIMIT ? OFFSET ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        String like = "%" + filtro + "%";

        ps.setString(1, like);
        ps.setString(2, like);
        ps.setString(3, like);
        ps.setInt(4, size);
        ps.setInt(5, offset);

        ResultSet rs = ps.executeQuery();

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

    } catch (Exception e) {
        e.printStackTrace();
    }

    return lista;
}

    @Override
    public int contar(String filtro) {
         String sql = "SELECT COUNT(*)FROM usuario WHERE nombre LIKE ?  OR apellido LIKE ? OR correo LIKE ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        String like = "%" + filtro + "%";

        ps.setString(1, like);
        ps.setString(2, like);
        ps.setString(3, like);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return 0;
}
    


    // ============================================================
    // BÚSQUEDA POR CORREO (USADO EN RECUPERACIÓN)
    // ============================================================
    @Override
    // Buscar usuario por correo
    public Usuario buscarPorCorreo(String correo) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE correo = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, correo);
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
                u.setTokenRecuperacion(rs.getString("token_recuperacion"));
                u.setExpiraToken(rs.getTimestamp("expira_token"));
                return u;
            }
        }
        return null;
    }

    // Hash de contraseña (SHA-256)
    // Método para encriptar la contraseña con SHA-256
    private String hashPassword(String password) throws NoSuchAlgorithmException {
        return DigestUtils.sha256Hex(password);
    
}


 public void guardarTokenRecuperacion(String correo, String token) throws SQLException {
        String sql = "UPDATE usuario SET token_recuperacion = ?, expira_token = ? WHERE correo = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            LocalDateTime expira = LocalDateTime.now().plusMinutes(30);
            Timestamp ts = Timestamp.valueOf(expira);

            ps.setString(1, token);
            ps.setTimestamp(2, ts);
            ps.setString(3, correo);
            ps.executeUpdate();
        }
    }



  public Usuario buscarPorToken(String token) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE token_recuperacion = ? AND (expira_token IS NULL OR expira_token >= NOW())";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, token);
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
                u.setTokenRecuperacion(rs.getString("token_recuperacion"));
                u.setExpiraToken(rs.getTimestamp("expira_token"));
                return u;
            }
        }
        return null;
    }




   public boolean actualizarPasswordPorToken(String token, String nuevaContrasena) 
        throws SQLException, NoSuchAlgorithmException {
    
    String sql = "UPDATE usuario SET contrasena = ? WHERE token_recuperacion = ?";
    
    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        String hash = hashPassword(nuevaContrasena);
        ps.setString(1, hash);
        ps.setString(2, token);

        int filasActualizadas = ps.executeUpdate();

        // Si se actualizó al menos una fila, retorna true
        return filasActualizadas > 0;
    }
}
   
   
  public void eliminarToken(String token) throws SQLException {
        String sql = "UPDATE usuario SET token_recuperacion = NULL, expira_token = NULL WHERE token_recuperacion = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, token);
            ps.executeUpdate();
        }
    }



  





    // ============================================================
    // 2FA GOOGLE AUTH
    // ============================================================

   // 2FA usando tabla 'twofactor'
    // ==========================
    public boolean guardarCodigo2FA(int idUsuario, String codigo, LocalDateTime expiracion) {
        String sql = "REPLACE INTO twofactor (idUsuario, codigo, expiracion) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setString(2, codigo);
            ps.setTimestamp(3, Timestamp.valueOf(expiracion));
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Optional<String> obtenerCodigo2FA(int idUsuario) {
        String sql = "SELECT codigo, expiracion FROM twofactor WHERE idUsuario=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Timestamp expiracion = rs.getTimestamp("expiracion");
                if (expiracion != null && expiracion.toLocalDateTime().isAfter(LocalDateTime.now())) {
                    return Optional.of(rs.getString("codigo"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public boolean eliminarCodigo2FA(int idUsuario) {
        String sql = "DELETE FROM twofactor WHERE idUsuario=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean validarCodigoEmail(String correo, String codigo) {

        String sql = "SELECT 1 FROM usuario "
                   + "WHERE correo=? AND codigo_2fa_email=? AND expira_codigo > NOW()";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, correo);
            ps.setString(2, codigo);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) { e.printStackTrace(); }

        return false;
    }



public void limpiarCodigo2FA(String correo) {

    String sql = "UPDATE usuario SET codigo_2fa_email=NULL, expira_codigo=NULL WHERE correo=?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, correo);
        ps.executeUpdate();

    } catch (Exception e) {
        e.printStackTrace();
    }
}

public List<Usuario> listarTecnicos() throws Exception {
    List<Usuario> lista = new ArrayList<>();

    String sql = "SELECT idUsuario, nombre, apellido, correo, area "
               + "FROM usuario WHERE rol = 'tecnico'";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Usuario u = new Usuario();
            u.setIdUsuario(rs.getInt("idUsuario"));
            u.setNombre(rs.getString("nombre"));
            u.setApellido(rs.getString("apellido"));
            u.setCorreo(rs.getString("correo"));
            u.setArea(rs.getString("area"));

            lista.add(u);
        }
    } catch (Exception e) {
        e.printStackTrace();
        throw e;
    }

    return lista;
}

// Guardar secret_key en la tabla usuario
public boolean guardarSecret2FA(String correo, String secretKey) {
    String sql = "UPDATE usuario SET secret_key = ?, twofa_enabled = 1 WHERE correo = ?";
    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, secretKey);
        ps.setString(2, correo);
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
public void activar2FA(int idUsuario) {
    String sql = "UPDATE usuario SET twofa_enabled=1 WHERE idUsuario=?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, idUsuario);
        ps.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
public boolean desactivar2FA(int idUsuario) {
    String sql = "UPDATE usuario SET secret_key = NULL, twofa_enabled = 0 WHERE idUsuario = ?";
    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, idUsuario);
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
public boolean desactivar2FAPorCorreo(String correo) {
    String sql = "UPDATE usuario SET secret_key = NULL, twofa_enabled = 0 WHERE correo = ?";
    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, correo);
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
// Actualizar secret_key y twofa flag por id (útil si tienes usuario con id)
public boolean actualizarSecretKeyPorId(int idUsuario, String secretKey, int twofaEnabled) {
    String sql = "UPDATE usuario SET secret_key = ?, twofa_enabled = ? WHERE idUsuario = ?";
    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, secretKey);
        ps.setInt(2, twofaEnabled);
        ps.setInt(3, idUsuario);
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
public boolean existeNombreApellido(String nombre, String apellido, Integer idExcluir) {

    String sql = "SELECT 1 FROM usuario WHERE LOWER(nombre)=? AND LOWER(apellido)=?";

    if (idExcluir != null) {
        sql += " AND idUsuario <> ?";
    }

    try (Connection cn = DatabaseConnection.getConnection();
         PreparedStatement ps = cn.prepareStatement(sql)) {

        ps.setString(1, nombre.trim().toLowerCase());
        ps.setString(2, apellido.trim().toLowerCase());

        if (idExcluir != null) {
            ps.setInt(3, idExcluir);
        }

        try (ResultSet rs = ps.executeQuery()) {
            return rs.next();
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return false;
}


public boolean existeCorreo(String correo, Integer idExcluir) {

    String sql = "SELECT 1 FROM usuario WHERE LOWER(correo)=?";

    if (idExcluir != null) {
        sql += " AND idUsuario <> ?";
    }

    try (Connection cn = DatabaseConnection.getConnection();
         PreparedStatement ps = cn.prepareStatement(sql)) {

        // Normalizar correo
        ps.setString(1, correo.trim().toLowerCase());

        if (idExcluir != null) {
            ps.setInt(2, idExcluir);
        }

        try (ResultSet rs = ps.executeQuery()) {
            return rs.next();
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return false;
}
public void resetear2FA(Usuario usuario) {
    try {
        String nuevoSecreto = TwoFactorAuth.generarSecret();

        usuario.setSecret_key(nuevoSecreto);
        usuario.setTwofa_enabled(1);

        actualizarSecretKey(usuario);

    } catch (Exception e) {
        e.printStackTrace();
    }
}
public boolean actualizarSinContrasena(Usuario u) throws Exception {

    String sql = "UPDATE usuario SET nombre=?, apellido=?, correo=?, rol=?, area=? WHERE idUsuario=?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, u.getNombre());
        ps.setString(2, u.getApellido());
        ps.setString(3, u.getCorreo());
        ps.setString(4, u.getRol());
        ps.setString(5, u.getArea());
        ps.setInt(6, u.getIdUsuario());

        return ps.executeUpdate() > 0;
    }
}
public boolean esAdmin(int idUsuario) {
    String sql = "SELECT rol FROM usuario WHERE idUsuario = ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, idUsuario);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return "admin".equalsIgnoreCase(rs.getString("rol"));
        }

    } catch (Exception e) { e.printStackTrace(); }

    return false;
}
public boolean actualizarContrasena(int idUsuario, String nuevaContrasena) throws Exception {
    String sql = "UPDATE usuario SET contrasena=? WHERE idUsuario=?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, DigestUtils.sha256Hex(nuevaContrasena));
        ps.setInt(2, idUsuario);

        return ps.executeUpdate() > 0;
    }
}
public String obtenerMensajeAccion(String accion, boolean ok) {
    if (ok) {
        return "Usuario " + accion + " correctamente.";
    }
    return "Ocurrió un error al " + accion + " el usuario.";
}
public boolean eliminarUsuario(int idUsuario) {
    String sql = "DELETE FROM usuario WHERE idUsuario = ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, idUsuario);

        int filas = ps.executeUpdate();
        return filas > 0;

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return false;
}
}
