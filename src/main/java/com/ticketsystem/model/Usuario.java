package com.ticketsystem.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Usuario {
    private int idUsuario;
    private String nombre;
    private String apellido;
    private String correo;
    private String contrasena;
    private String rol;
    private String area;
    
    
    private String tokenRecuperacion;
    private String secret_key;

private int twofa_enabled;
private Timestamp expiraToken;


    // Getters y Setters
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    public String getTokenRecuperacion() {
        return tokenRecuperacion;
    }

    public void setTokenRecuperacion(String tokenRecuperacion) {
        this.tokenRecuperacion = tokenRecuperacion;
    }

    public Timestamp getExpiraToken() {
        return expiraToken;
    }

    public void setExpiraToken(Timestamp expiraToken) {
        this.expiraToken = expiraToken;
    }

    public String getSecret_key() {
        return secret_key;
    }

    public void setSecret_key(String secret_key) {
        this.secret_key = secret_key;
    }

    public int getTwofa_enabled() {
        return twofa_enabled;
    }

    public void setTwofa_enabled(int twofa_enabled) {
        this.twofa_enabled = twofa_enabled;
    }




   

    
}
