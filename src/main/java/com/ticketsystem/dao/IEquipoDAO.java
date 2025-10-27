package com.ticketsystem.dao;

import com.ticketsystem.model.Equipo;
import java.util.List;

public interface IEquipoDAO {

    /**
     * Lista los equipos con paginación y filtro.
     */
    List<Equipo> listar(int page, int size, String filtro) throws Exception;

    /**
     * Cuenta la cantidad total de equipos según el filtro aplicado.
     */
    int contar(String filtro) throws Exception;

    /**
     * Busca un equipo por su ID.
     */
    Equipo buscarPorId(int id) throws Exception;

    /**
     * Inserta un nuevo equipo usando procedimiento almacenado.
     */
    boolean insertar(Equipo e) throws Exception;

    /**
     * Actualiza un equipo existente.
     */
    boolean actualizar(Equipo e) throws Exception;

    /**
     * Elimina un equipo por su ID.
     */
    boolean eliminar(int id) throws Exception;
}
