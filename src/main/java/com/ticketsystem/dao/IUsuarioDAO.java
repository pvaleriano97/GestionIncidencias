package com.ticketsystem.dao;

import com.ticketsystem.model.Usuario;
import java.time.LocalDateTime;
import java.util.List;

public interface IUsuarioDAO {

    // CRUD
    boolean insertar(Usuario usuario) throws Exception;
    boolean actualizar(Usuario usuario) throws Exception;
    boolean eliminar(int id) throws Exception;
    Usuario buscarPorId(int id) throws Exception;

    // Listados
    List<Usuario> listar() throws Exception;
    List<Usuario> listar(int page, int size, String filtro) throws Exception;
    int contar(String filtro) throws Exception;

    // Recuperación de contraseña
    Usuario buscarPorCorreo(String correo) throws Exception;
public void guardarTokenRecuperacion(String correo, String token) throws Exception;
public Usuario buscarPorToken(String token) throws Exception;
    public boolean actualizarPasswordPorToken(String token, String nuevaContrasena) throws Exception;
    public void actualizarSecretKey(Usuario usuario) throws Exception;

public boolean existeCorreo(String correo, Integer idExcluir);
public boolean existeNombreApellido(String nombre, String apellido, Integer idExcluir);
public List<Usuario> listarTodos() throws Exception;
public boolean actualizarSinContrasena(Usuario u) throws Exception;
public boolean esAdmin(int idUsuario);
public boolean actualizarContrasena(int idUsuario, String nuevaContrasena) throws Exception;
public String obtenerMensajeAccion(String accion, boolean ok)throws Exception;
public boolean eliminarUsuario(int idUsuario)throws Exception;
    // Técnicos
    List<Usuario> listarTecnicos() throws Exception;
}
