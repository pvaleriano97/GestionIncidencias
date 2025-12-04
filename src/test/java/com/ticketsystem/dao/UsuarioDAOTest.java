package com.ticketsystem.dao;

import com.ticketsystem.model.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioDAOTest {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Test
    @DisplayName("Login correcto con credenciales válidas")
    void testLoginCorrecto() throws Exception {
        Usuario u = usuarioDAO.autenticar("jean.rubio@bcp.com.pe", "@B123");
        assertNotNull(u, "El usuario no debería ser null para credenciales válidas");
        assertEquals("jean.rubio@bcp.com.pe", u.getCorreo());
    }

    @Test
    @DisplayName("Login con usuario inexistente")
    void testLoginUsuarioInexistente() throws Exception {
        Usuario u = usuarioDAO.autenticar("no.existe@demo.com", "@B123");
        assertNull(u, "Debe ser null para usuario inexistente");
    }

    @Test
    @DisplayName("Login con contraseña incorrecta")
    void testLoginConPasswordIncorrecto() throws Exception {
        Usuario u = usuarioDAO.autenticar("jean.rubio@bcp.com.pe", "clave-incorrecta");
        assertNull(u, "Debe ser null con contraseña incorrecta");
    }

    @Test
    @DisplayName("Login NO permite inyección SQL")
    void testLoginNoPermiteSQLInjection() throws Exception {
        // payloads típicos de inyección
        String inj1 = "' OR '1'='1";
        String inj2 = "' OR 1=1 -- ";
        String inj3 = "'; DROP TABLE usuario; --";

        // No debe autenticar con cadenas maliciosas
        assertNull(usuarioDAO.autenticar(inj1, inj1), "No debe autenticar con payload inj1");
        assertNull(usuarioDAO.autenticar(inj2, inj2), "No debe autenticar con payload inj2");
        assertNull(usuarioDAO.autenticar(inj3, inj3), "No debe autenticar con payload inj3");

        // Mezclas: correo válido + pass malicioso / correo malicioso + pass válido
        assertNull(usuarioDAO.autenticar("jean.rubio@bcp.com.pe", inj1),
                "No debe autenticar con pass inyectado");
        assertNull(usuarioDAO.autenticar(inj1, "@B123"),
                "No debe autenticar con correo inyectado");
    }
}
