package com.ticketsystem.util;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class TwoFactorAuth {

    private static final GoogleAuthenticator gAuth = new GoogleAuthenticator();

    // ✅ Generar secret
    public static String generarSecret() {
        GoogleAuthenticatorKey key = gAuth.createCredentials();
        return key.getKey();
    }

    // ✅ Validar código 2FA
    public static boolean validarCodigo(String secret, String codigo) {
        try {
            int code = Integer.parseInt(codigo);
            return gAuth.authorize(secret, code);
        } catch (Exception e) {
            return false;
        }
    }

    // ✅ Generar QR (QuickChart)
    public static String generarQR(String account, String issuer, String secret) {

        try {
            String otpauth =
                    "otpauth://totp/" + issuer + ":" + account +
                    "?secret=" + secret +
                    "&issuer=" + issuer;

            return "https://quickchart.io/qr?size=250&text="
                    + URLEncoder.encode(otpauth, StandardCharsets.UTF_8.toString());

        } catch (Exception e) {
            return null;
        }
    }
}
