package com.ticketsystem.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Incidencia {

    private Integer idIncidencia;
    private String descripcion;
    private String estado;
    private Integer idUsuario;
    private Integer idEquipo;
    private Integer idTecnico;
    private Timestamp fechaRegistro;

    // Campos adicionales
    private String nombreUsuario;
    private String codigoEquipo;
    private String tipoEquipo;
    private String nombreTecnico;

    private String fechaRegistroStr;

    // Constructor vacío CORRECTO
    public Incidencia() {
    }

    // Constructor válido (el único necesario)
    public Incidencia(Integer idIncidencia, String descripcion, String estado,
                      Integer idUsuario, Integer idEquipo, Integer idTecnico) {

        this.idIncidencia = idIncidencia;
        this.descripcion = descripcion;
        this.estado = estado;
        this.idUsuario = idUsuario;
        this.idEquipo = idEquipo;
        this.idTecnico = idTecnico;
    }

    // Getters y Setters
    public Integer getIdIncidencia() { return idIncidencia; }
    public void setIdIncidencia(Integer idIncidencia) { this.idIncidencia = idIncidencia; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public Integer getIdEquipo() { return idEquipo; }
    public void setIdEquipo(Integer idEquipo) { this.idEquipo = idEquipo; }

    public Integer getIdTecnico() { return idTecnico; }
    public void setIdTecnico(Integer idTecnico) { this.idTecnico = idTecnico; }

    public Timestamp getFechaRegistro() { return fechaRegistro; }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
        if (fechaRegistro != null)
            this.fechaRegistroStr = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(fechaRegistro);
        else
            this.fechaRegistroStr = "";
    }

    public String getFechaRegistroStr() { return fechaRegistroStr; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getCodigoEquipo() { return codigoEquipo; }
    public void setCodigoEquipo(String codigoEquipo) { this.codigoEquipo = codigoEquipo; }

    public String getTipoEquipo() { return tipoEquipo; }
    public void setTipoEquipo(String tipoEquipo) { this.tipoEquipo = tipoEquipo; }

    public String getNombreTecnico() { return nombreTecnico; }
    public void setNombreTecnico(String nombreTecnico) { this.nombreTecnico = nombreTecnico; }
}
