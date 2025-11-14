
package com.ticketsystem.dao;

import com.ticketsystem.model.HistorialEquipo;
import java.util.List;

public interface IHistorialEquipoDAO {
    
boolean insertar(HistorialEquipo h) throws Exception;

    boolean actualizar(HistorialEquipo h) throws Exception;

    boolean eliminar(int id) throws Exception;

    HistorialEquipo buscarPorId(int id) throws Exception;

    List<HistorialEquipo> listar() throws Exception;

    List<HistorialEquipo> listarPorEquipo(int idEquipo) throws Exception;
}

