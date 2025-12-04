package com.ticketsystem.dao;

import com.ticketsystem.model.Tecnico;
import java.util.List;

public interface ITecnicoDAO {

    List<Tecnico> listar();
    List<Tecnico> listar(int page, int size, String filtro) throws Exception;

    int contar(String filtro) throws Exception;
    Tecnico buscarPorId(int id) throws Exception;

    boolean insertar(Tecnico t) throws Exception;
    boolean actualizar(Tecnico t) throws Exception;
    boolean eliminar(int id) throws Exception;

    Tecnico buscarPorIdUsuario(int idUsuario);

    List<Tecnico> obtenerTopTecnicos(int limite);  // <---- AGREGADO
}
