package com.ticketsystem.dao;

import com.ticketsystem.model.Repuesto;
import java.util.List;

public interface IRepuestoDAO {

    // Listado completo
    List<Repuesto> listar() throws Exception;

    // Listado paginado y filtrado
    List<Repuesto> listar(int page, int size, String filtro) throws Exception;

    // Contar repuestos según filtro
    int contar(String filtro) throws Exception;

    // Obtener repuesto por ID
    Repuesto obtener(int id) throws Exception;

    // Insertar un repuesto
    void insertar(Repuesto r) throws Exception;

    // Actualizar un repuesto
    void actualizar(Repuesto r) throws Exception;

    // Eliminar un repuesto por ID
    void eliminar(int id) throws Exception;

    // Listado simple (ID, nombre, stock)
    List<Repuesto> listarSimple() throws Exception;
}
