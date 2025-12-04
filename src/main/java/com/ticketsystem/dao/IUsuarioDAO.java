package com.ticketsystem.dao;

import com.ticketsystem.model.Usuario;
import java.util.List;

public interface IUsuarioDAO {
    boolean insertar(Usuario usuario) throws Exception;
    boolean actualizar(Usuario usuario) throws Exception;
    boolean eliminar(int id) throws Exception;
    Usuario buscarPorId(int id) throws Exception;
    List<Usuario> listar(int page, int size, String filtro) throws Exception;
    int contar(String filtro) throws Exception;
    List<Usuario> listar(); // para combos sin paginación
}
