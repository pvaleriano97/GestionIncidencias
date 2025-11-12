
package com.ticketsystem.dao;

import com.ticketsystem.model.HistorialEquipo;
import java.util.List;
public interface IHistorialEquipoDAO {
    List<HistorialEquipo> listar(int page, int size, String filtro) throws Exception;
    int contar(String filtro) throws Exception;
    HistorialEquipo buscarPorId(int id) throws Exception;
    boolean insertar(HistorialEquipo h) throws Exception;
    boolean actualizar(HistorialEquipo h) throws Exception;
    boolean eliminar(int id) throws Exception;
    List<HistorialEquipo> listar(); // para combos sin paginación
}
