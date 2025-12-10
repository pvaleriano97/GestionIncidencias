package com.ticketsystem.dao;

import com.ticketsystem.model.Incidencia;
import com.ticketsystem.util.DatabaseConnection;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IncidenciaDAOTest {

    private IncidenciaDAO dao;
    private static final String DESCRIPCION_PRUEBA = "Incidencia de prueba JUnit";

    // 🔴 LIMPIEZA TOTAL DE LA TABLA (CLAVE PARA QUE NO FALLE)
    @BeforeEach
    void limpiarTablaIncidencia() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("DELETE FROM incidencia");

        } catch (Exception e) {
            fail("Error limpiando la tabla incidencia: " + e.getMessage());
        }

        dao = new IncidenciaDAO();
    }

    // 🔹 CREA UNA INCIDENCIA ESTÁNDAR PARA LOS TESTS
    private Incidencia crearIncidenciaDePrueba() {

        boolean existeAntes = dao.existeIncidenciaConDescripcion(DESCRIPCION_PRUEBA);
        assertFalse(existeAntes, "No debería existir la incidencia antes del test");

        Incidencia inc = new Incidencia();
        inc.setDescripcion(DESCRIPCION_PRUEBA);
        inc.setEstado("Abierta");
        inc.setIdUsuario(1);
        inc.setIdEquipo(1);
        inc.setIdTecnico(null);

        dao.insertar(inc);

        boolean existeDespues = dao.existeIncidenciaConDescripcion(DESCRIPCION_PRUEBA);
        assertTrue(existeDespues, "La incidencia debería existir después de insertarla");

        return dao.obtenerUltimaIncidencia();
    }

    
}
