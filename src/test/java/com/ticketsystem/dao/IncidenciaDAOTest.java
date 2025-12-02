package com.ticketsystem.dao;

import com.ticketsystem.model.Incidencia;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IncidenciaDAOTest {

    private final IncidenciaDAO incidenciaDAO = new IncidenciaDAO();

    /**
     * Crea una incidencia de prueba con una descripción única
     * y la recupera desde la BD (con su idIncidencia).
     */
    private Incidencia crearIncidenciaDePrueba(String descripcionUnica) {
        // 1) Construimos el objeto
        Incidencia inc = new Incidencia();
        inc.setDescripcion(descripcionUnica);
        inc.setEstado("Abierto");
        inc.setIdUsuario(1); // Debe existir
        inc.setIdEquipo(1);  // Debe existir
        inc.setIdTecnico(1); // Debe existir

        // 2) Lo insertamos
        incidenciaDAO.insertar(inc);

        // 3) Lo buscamos usando la descripción única
        List<Incidencia> lista = incidenciaDAO.listar(descripcionUnica, 0, 1);
        assertFalse(lista.isEmpty(),
                "Debería existir al menos una incidencia con la descripción de prueba.");

        Incidencia desdeBD = lista.get(0);
        System.out.println("Incidencia creada para prueba: id=" +
                desdeBD.getIdIncidencia() + ", desc=" + desdeBD.getDescripcion());
        return desdeBD;
    }

    /**
     * PI001 – Registrar incidencia correcta
     */
    @Test
    void testRegistrarIncidenciaCorrecta() {
        int totalAntes = incidenciaDAO.totalIncidencias();

        String descripcionPrueba = "INCIDENCIA JUNIT OK";
        Incidencia nueva = crearIncidenciaDePrueba(descripcionPrueba);

        int totalDespues = incidenciaDAO.totalIncidencias();

        assertEquals(totalAntes + 1, totalDespues,
                "Después de insertar una incidencia el total debería aumentar en 1.");

        assertNotNull(nueva.getIdIncidencia(),
                "La incidencia recuperada debería tener un idIncidencia asignado.");
        assertEquals("Abierto", nueva.getEstado(),
                "El estado inicial esperado es 'Abierto'.");

        System.out.println("PI001 OK - Registrar incidencia correcta.");
    }

    /**
     * PI002 – Intentar registrar sin descripción
     * (validación de campos obligatorios).
     *
     * DAO captura la excepción y no la propaga, validamos
     * que el total de incidencias NO cambie.
     */
    @Test
    void testRegistrarIncidenciaSinDescripcion() {
        int totalAntes = incidenciaDAO.totalIncidencias();

        Incidencia inc = new Incidencia();
        inc.setDescripcion(""); // vacío
        inc.setEstado("Abierto");
        inc.setIdUsuario(1);
        inc.setIdEquipo(1);
        inc.setIdTecnico(1);

        incidenciaDAO.insertar(inc); // Esto debería fallar en la BD y no insertar nada

        int totalDespues = incidenciaDAO.totalIncidencias();

        assertEquals(totalAntes, totalDespues,
                "El total de incidencias no debe cambiar si la descripción está vacía.");

        System.out.println("PI002 OK - No se registran incidencias sin descripción.");
    }

    /**
     * PI003 – Cambiar estado a "En Proceso"
     */
    @Test
    void testCambiarEstadoEnProceso() {
        Incidencia inc = crearIncidenciaDePrueba("INC JUNIT ESTADO EN PROCESO");

        inc.setEstado("En Proceso");
        incidenciaDAO.actualizar(inc);

        Incidencia desdeBD = incidenciaDAO.obtenerPorId(inc.getIdIncidencia());
        assertNotNull(desdeBD, "La incidencia debería existir después de actualizar.");
        assertEquals("En Proceso", desdeBD.getEstado(),
                "El estado debería haberse actualizado a 'En Proceso'.");

        System.out.println("PI003 OK - Cambio de estado a 'En Proceso'.");
    }

    /**
     * PI004 – Cerrar incidencia ("Cerrado")
     */
    @Test
    void testCerrarIncidencia() {
        Incidencia inc = crearIncidenciaDePrueba("INC JUNIT ESTADO CERRADO");

        inc.setEstado("Cerrado");
        incidenciaDAO.actualizar(inc);

        Incidencia desdeBD = incidenciaDAO.obtenerPorId(inc.getIdIncidencia());
        assertNotNull(desdeBD, "La incidencia debería existir después de cerrar.");
        assertEquals("Cerrado", desdeBD.getEstado(),
                "El estado debería haberse actualizado a 'Cerrado'.");

        System.out.println("PI004 OK - Cierre de incidencia.");
    }

    /**
     * PI005 – Buscar incidencia por ID existente
     */
    @Test
    void testBuscarIncidenciaPorIdExistente() {
        Incidencia inc = crearIncidenciaDePrueba("INC JUNIT BUSCAR POR ID");

        Incidencia desdeBD = incidenciaDAO.obtenerPorId(inc.getIdIncidencia());
        assertNotNull(desdeBD, "La incidencia debería encontrarse por su ID.");
        assertEquals(inc.getIdIncidencia(), desdeBD.getIdIncidencia(),
                "El ID obtenido debe coincidir con el buscado.");
        assertEquals(inc.getDescripcion(), desdeBD.getDescripcion(),
                "La descripción debe coincidir.");

        System.out.println("PI005 OK - Búsqueda por ID existente.");
    }

    /**
     * PI006 – Buscar incidencia por ID inexistente
     */
    @Test
    void testBuscarIncidenciaPorIdInexistente() {
        int idInexistente = 999999; // elegir un ID fuera del rango real

        Incidencia inc = incidenciaDAO.obtenerPorId(idInexistente);
        assertNull(inc, "Para un ID inexistente el método debería devolver null.");

        System.out.println("PI006 OK - Búsqueda por ID inexistente.");
    }
}
