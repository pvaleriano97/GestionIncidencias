package com.ticketsystem.dao;

import com.ticketsystem.model.Tecnico;
import java.util.List;

public interface ITecnicoDAO {

    boolean insertar(Tecnico tecnico) throws Exception;

    boolean actualizar(Tecnico tecnico) throws Exception;

    boolean eliminar(int id) throws Exception;

    Tecnico buscarPorId(int id) throws Exception;

    List<Tecnico> listar(int page, int size, String filtro) throws Exception;

    int contar(String filtro) throws Exception;

    List<Tecnico> listar(); // para combos sin paginación
}