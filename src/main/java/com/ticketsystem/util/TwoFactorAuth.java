package com.ticketsystem.util;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class TwoFactorAuth {
    
    private static final ConcurrentHashMap<String, Codigo2FA> codigosActivos = new ConcurrentHashMap<>();
    private static final long TIEMPO_EXPIRACION = 10 * 60 * 1000; // 10 minutos
    
    static class Codigo2FA {
        String codigo;
        long timestamp;
        String email;
        
        Codigo2FA(String codigo, String email) {
            this.codigo = codigo;
            this.email = email;
            this.timestamp = System.currentTimeMillis();
        }
        
        boolean haExpirado() {
            return (System.currentTimeMillis() - timestamp) > TIEMPO_EXPIRACION;
        }
    }
    
    /**
     * Genera un código de 6 dígitos
     */
    public static String generarCodigo6Digitos() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(999999));
    }
    
    /**
     * Genera y guarda código 2FA para un email
     */
    public static String generarYGuardarCodigo(String email) {
        // Limpiar códigos expirados primero
        limpiarCodigosExpirados();
        
        String codigo = generarCodigo6Digitos();
        codigosActivos.put(email, new Codigo2FA(codigo, email));
        
        // Para testing
        System.out.println("🔐 [2FA] Código generado para " + email + ": " + codigo);
        System.out.println("⏰ [2FA] Válido por 10 minutos");
        
        return codigo;
    }
    
    /**
     * Verifica si el código es válido
     */
    public static boolean verificarCodigo(String email, String codigoIngresado) {
        if (email == null || codigoIngresado == null) {
            return false;
        }
        
        Codigo2FA codigoAlmacenado = codigosActivos.get(email);
        
        if (codigoAlmacenado == null) {
            System.out.println("❌ [2FA] No hay código para: " + email);
            return false;
        }
        
        if (codigoAlmacenado.haExpirado()) {
            System.out.println("❌ [2FA] Código expirado para: " + email);
            codigosActivos.remove(email);
            return false;
        }
        
        // Verificar el código (comparación exacta)
        boolean esValido = codigoIngresado.trim().equals(codigoAlmacenado.codigo);
        
        System.out.println("🔍 [2FA] Verificación para " + email);
        System.out.println("   Código ingresado: " + codigoIngresado);
        System.out.println("   Código almacenado: " + codigoAlmacenado.codigo);
        System.out.println("   Resultado: " + (esValido ? "✅ VÁLIDO" : "❌ INVÁLIDO"));
        
        if (esValido) {
            // Eliminar código usado
            codigosActivos.remove(email);
        }
        
        return esValido;
    }
    
    /**
     * Verifica si hay un código pendiente
     */
    public static boolean tieneCodigoPendiente(String email) {
        Codigo2FA codigo = codigosActivos.get(email);
        return codigo != null && !codigo.haExpirado();
    }
    
    /**
     * Obtiene el código actual (para debug)
     */
    public static String obtenerCodigoActual(String email) {
        Codigo2FA codigo = codigosActivos.get(email);
        return codigo != null ? codigo.codigo : null;
    }
    
    /**
     * Limpia códigos expirados
     */
    private static void limpiarCodigosExpirados() {
        codigosActivos.entrySet().removeIf(entry -> entry.getValue().haExpirado());
    }
    
    /**
     * Elimina código específico
     */
    public static void eliminarCodigo(String email) {
        codigosActivos.remove(email);
    }
}