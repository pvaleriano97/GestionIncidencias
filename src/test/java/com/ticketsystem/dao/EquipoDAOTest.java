package com.ticketsystem.dao;

import com.ticketsystem.model.Equipo;
import com.ticketsystem.util.DatabaseConnection;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas para EquipoDAO
 *
 * IMPORTANTE:
 * - Usa la misma BD gestion_incidencias que tu app.
 * - Debe existir la tabla "equipo" con PK idEquipo.
 */
public class EquipoDAOTest {

    private final EquipoDAO equipoDAO = new EquipoDAO();

    // =======================
    // Helpers para las pruebas
    // =======================

    /** Cuenta cuántos equipos hay en la tabla. */
    private int contarEquipos() throws Exception {
        String sql = "SELECT COUNT(*) FROM equipo";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    /** Obtiene el último idEquipo insertado. */
    private int obtenerUltimoIdEquipo() throws Exception {
        String sql = "SELECT idEquipo FROM equipo ORDER BY idEquipo DESC LIMIT 1";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getInt("idEquipo");
        }
        throw new IllegalStateException("No hay equipos en la tabla para obtener el último ID");
    }

    // =======================
    // CASOS DE PRUEBA
    // =======================

    /**
     * EQ001 – Registrar equipo correcto
     * Verifica que al insertar un equipo el total de registros aumenta en 1.
     */
    @Test
    public void testRegistrarEquipoCorrecto() throws Exception {
        System.out.println("EQ001 - Registrar equipo correcto");

        int totalAntes = contarEquipos();

        Equipo eq = new Equipo();
        eq.setCodigoEquipo("EQ-JUNIT-" + System.currentTimeMillis());
        eq.setTipo("Cámara");      // Ajusta si tu modelo usa otros valores/campos

        // Si tienes más campos obligatorios en Equipo, setéalos aquí:
        // eq.setMarca("Hikvision");
        // eq.setModelo("DS-2CD2043G0");
        // eq.setEstado("Operativo");
        // eq.setUbicacion("Sala de Monitoreo");

        equipoDAO.insertar(eq);

        int totalDespues = contarEquipos();

        assertEquals(totalAntes + 1, totalDespues,
                "El total de equipos debe aumentar en 1 después de insertar");
    }

    /**
     * EQ002 – Buscar equipo por ID existente
     * Inserta un equipo y luego lo recupera por su ID.
     */
    @Test
    public void testBuscarEquipoPorIdExistente() throws Exception {
        System.out.println("EQ002 - Buscar equipo por ID existente");

        // Primero insertamos uno para asegurar que existe
        Equipo eq = new Equipo();
        eq.setCodigoEquipo("EQ-JUNIT-BUSCAR-" + System.currentTimeMillis());
        eq.setTipo("Grabador");

        equipoDAO.insertar(eq);

        int ultimoId = obtenerUltimoIdEquipo();

        Equipo encontrado = equipoDAO.buscarPorId(ultimoId);

        assertNotNull(encontrado, "El equipo no debería ser null para un ID existente");
        assertEquals(ultimoId, encontrado.getIdEquipo(),
                "El ID recuperado debe coincidir");
    }

    /**
     * EQ003 – Buscar equipo por ID inexistente
     * Debe devolver null cuando el ID no existe.
     */
    @Test
    public void testBuscarEquipoPorIdInexistente() throws Exception {
        System.out.println("EQ003 - Buscar equipo por ID inexistente");

        int idInexistente = -1;  // o un ID muy grande que sepas que no existe
        Equipo encontrado = equipoDAO.buscarPorId(idInexistente);

        assertNull(encontrado, "El equipo debe ser null cuando el ID no existe");
    }

    /**
     * EQ004 – Actualizar equipo
     * Cambia el tipo del equipo y verifica que se actualiza.
     */
    @Test
    public void testActualizarEquipo() throws Exception {
        System.out.println("EQ004 - Actualizar equipo");

        // Insertar un equipo base
        Equipo eq = new Equipo();
        eq.setCodigoEquipo("EQ-JUNIT-UPD-" + System.currentTimeMillis());
        eq.setTipo("Cámara");

        equipoDAO.insertar(eq);
        int id = obtenerUltimoIdEquipo();

        // Recuperar y modificar
        Equipo editable = equipoDAO.buscarPorId(id);
        assertNotNull(editable, "El equipo de prueba debe existir antes de actualizar");

        editable.setTipo("Switch");   // nuevo tipo
        equipoDAO.actualizar(editable);

        // Volver a leer
        Equipo actualizado = equipoDAO.buscarPorId(id);
        assertNotNull(actualizado, "El equipo debe seguir existiendo tras actualizar");
        assertEquals("Switch", actualizado.getTipo(),
                "El tipo del equipo debería haberse actualizado a 'Switch'");
    }

    /**
     * EQ005 – Eliminar equipo
     * Inserta un equipo y luego lo elimina, verificando que ya no exista.
     */
    @Test
    public void testEliminarEquipo() throws Exception {
        System.out.println("EQ005 - Eliminar equipo");

        // Insertar equipo a eliminar
        Equipo eq = new Equipo();
        eq.setCodigoEquipo("EQ-JUNIT-DEL-" + System.currentTimeMillis());
        eq.setTipo("Router");

        equipoDAO.insertar(eq);
        int id = obtenerUltimoIdEquipo();

        // Eliminar
        equipoDAO.eliminar(id);

        // Verificar que ya no existe
        Equipo eliminado = equipoDAO.buscarPorId(id);
        assertNull(eliminado, "El equipo debería ser null después de eliminarlo");
    }
}

