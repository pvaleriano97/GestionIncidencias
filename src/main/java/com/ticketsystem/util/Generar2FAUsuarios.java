package com.ticketsystem.util;

import com.ticketsystem.dao.UsuarioDAO;
import com.ticketsystem.model.Usuario;
import java.util.List;

public class Generar2FAUsuarios {

    public static void main(String[] args) {

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        try {
            // Obtener todos los usuarios
            List<Usuario> usuarios = usuarioDAO.listarTodos();

            for (Usuario u : usuarios) {

                // Verificar si no tiene secret_key
                if (u.getSecret_key() == null || u.getSecret_key().isEmpty()) {

                    // Generar nueva clave 2FA
                    String secret = TwoFactorAuth.generarSecret();
                    u.setSecret_key(secret);
                    u.setTwofa_enabled(1); // Activar 2FA

                    // Actualizar en la base de datos
                    usuarioDAO.actualizarSecretKey(u);

                    System.out.println("✅ 2FA generado para: " + u.getCorreo() + " (" + u.getRol() + ")");
                }
            }

            System.out.println("✅ Proceso completado. Todos los usuarios tienen 2FA generado si no lo tenían.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
