package com.ticketsystem.model;

import java.util.Date;

public class AuditoriaLogin {
    private int idAuditoria;
    private int idUsuario;
    private String correoUsuario;
    private String nombreUsuario;
    private String rolUsuario;
    private Date fechaLogin;
    private String ipAddress;
    private String userAgent;
    private String statusLogin;
    private String codigo2fa;
    private int intentosFallidos;
    private String sessionId;
    private String navegador;
    private String sistemaOperativo;
    
    // Constructores
    public AuditoriaLogin() {}
    
    public AuditoriaLogin(String correoUsuario, String ipAddress, 
                         String userAgent, String statusLogin) {
        this.correoUsuario = correoUsuario;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.statusLogin = statusLogin;
        this.fechaLogin = new Date();
    }
    
    // Getters y Setters
    public int getIdAuditoria() { return idAuditoria; }
    public void setIdAuditoria(int idAuditoria) { this.idAuditoria = idAuditoria; }
    
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    
    public String getCorreoUsuario() { return correoUsuario; }
    public void setCorreoUsuario(String correoUsuario) { this.correoUsuario = correoUsuario; }
    
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    
    public String getRolUsuario() { return rolUsuario; }
    public void setRolUsuario(String rolUsuario) { this.rolUsuario = rolUsuario; }
    
    public Date getFechaLogin() { return fechaLogin; }
    public void setFechaLogin(Date fechaLogin) { this.fechaLogin = fechaLogin; }
    
    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
    
    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }
    
    public String getStatusLogin() { return statusLogin; }
    public void setStatusLogin(String statusLogin) { this.statusLogin = statusLogin; }
    
    public String getCodigo2fa() { return codigo2fa; }
    public void setCodigo2fa(String codigo2fa) { this.codigo2fa = codigo2fa; }
    
    public int getIntentosFallidos() { return intentosFallidos; }
    public void setIntentosFallidos(int intentosFallidos) { this.intentosFallidos = intentosFallidos; }
    
    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }
    
    public String getNavegador() { return navegador; }
    public void setNavegador(String navegador) { this.navegador = navegador; }
    
    public String getSistemaOperativo() { return sistemaOperativo; }
    public void setSistemaOperativo(String sistemaOperativo) { this.sistemaOperativo = sistemaOperativo; }
}