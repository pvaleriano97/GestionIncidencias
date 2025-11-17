package com.ticketsystem.dao;

import com.ticketsystem.model.SolicitudRepuesto;
import com.ticketsystem.model.Incidencia;
import com.ticketsystem.model.Repuesto;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SolicitudRepuestoDAO {

    private final Connection conn;

    public SolicitudRepuestoDAO(Connection conn) {
        this.conn = conn;
    }

    // ======================================================
    // INSERTAR
    // ======================================================
    public void insertar(HttpServletRequest request) throws Exception {

        String sql = "INSERT INTO solicitudrepuesto (idIncidencia, idRepuesto, cantidad, fechaSolicitud) " +
                     "VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(request.getParameter("idIncidencia")));
            ps.setInt(2, Integer.parseInt(request.getParameter("idRepuesto")));
            ps.setInt(3, Integer.parseInt(request.getParameter("cantidad")));

            String fecha = request.getParameter("fechaSolicitud");
            ps.setTimestamp(4, Timestamp.valueOf(fecha.replace("T", " ") + ":00"));

            ps.executeUpdate();
        }
    }

    // ======================================================
    // EDITAR
    // ======================================================
    public void editar(HttpServletRequest request) throws Exception {

        String sql = "UPDATE solicitudrepuesto SET idIncidencia=?, idRepuesto=?, cantidad=?, fechaSolicitud=? " +
                     "WHERE idSolicitud=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(request.getParameter("idIncidencia")));
            ps.setInt(2, Integer.parseInt(request.getParameter("idRepuesto")));
            ps.setInt(3, Integer.parseInt(request.getParameter("cantidad")));

            String fecha = request.getParameter("fechaSolicitud");
            ps.setTimestamp(4, Timestamp.valueOf(fecha.replace("T", " ") + ":00"));

            ps.setInt(5, Integer.parseInt(request.getParameter("idSolicitud")));

            ps.executeUpdate();
        }
    }

    // ======================================================
    // ELIMINAR
    // ======================================================
    public void eliminar(int idSolicitud) throws Exception {

        String sql = "DELETE FROM solicitudrepuesto WHERE idSolicitud=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idSolicitud);
            ps.executeUpdate();
        }
    }

    // ======================================================
    // OBTENER POR ID (para editar)
    // ======================================================
    public SolicitudRepuesto obtenerPorId(int idSolicitud) throws Exception {

        String sql = "SELECT * FROM solicitudrepuesto WHERE idSolicitud=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idSolicitud);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                SolicitudRepuesto s = new SolicitudRepuesto();
                s.setIdSolicitud(rs.getInt("idSolicitud"));
                s.setIdIncidencia(rs.getInt("idIncidencia"));
                s.setIdRepuesto(rs.getInt("idRepuesto"));
                s.setCantidad(rs.getInt("cantidad"));
                s.setFechaSolicitud(rs.getTimestamp("fechaSolicitud"));
                return s;
            }
        }
        return null;
    }

    // ======================================================
   // -----------------------------------------------------
// CONTAR TOTAL DE SOLICITUDES SEGÚN BÚSQUEDA
// -----------------------------------------------------
public int contarSolicitudes(String search) throws Exception {
    String sql =
        "SELECT COUNT(*) FROM solicitudrepuesto sr " +
        "INNER JOIN incidencia i ON sr.idIncidencia = i.idIncidencia " +
        "INNER JOIN repuesto r ON sr.idRepuesto = r.idRepuesto " +
        "WHERE i.descripcion LIKE ? OR r.nombre LIKE ?";

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, "%" + search + "%");
        ps.setString(2, "%" + search + "%");

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
    }
    return 0;
}

// -----------------------------------------------------
// LISTAR SOLICITUDES CON PAGINACIÓN Y BÚSQUEDA
// -----------------------------------------------------
public List<SolicitudRepuesto> listarPaginado(String search, int inicio, int registrosPorPagina) throws Exception {
    List<SolicitudRepuesto> lista = new ArrayList<>();

    String sql =
        "SELECT sr.idSolicitud, i.descripcion, r.nombre, sr.cantidad, r.stock, sr.fechaSolicitud " +
        "FROM solicitudrepuesto sr " +
        "INNER JOIN incidencia i ON sr.idIncidencia = i.idIncidencia " +
        "INNER JOIN repuesto r ON sr.idRepuesto = r.idRepuesto " +
        "WHERE i.descripcion LIKE ? OR r.nombre LIKE ? " +
        "ORDER BY sr.idSolicitud DESC " +
        "LIMIT ? OFFSET ?";

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, "%" + search + "%");
        ps.setString(2, "%" + search + "%");
        ps.setInt(3, registrosPorPagina);
        ps.setInt(4, inicio);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            SolicitudRepuesto s = new SolicitudRepuesto();
            s.setIdSolicitud(rs.getInt("idSolicitud"));
            s.setDescripcion(rs.getString("descripcion"));
            s.setNombre(rs.getString("nombre"));
            s.setCantidad(rs.getInt("cantidad"));
            s.setStock(rs.getInt("stock"));
            s.setFechaSolicitud(rs.getTimestamp("fechaSolicitud"));
            lista.add(s);
        }
    }
    return lista;
}


    // ======================================================
    // LISTAR INCIDENCIAS
    // ======================================================
    public List<Incidencia> listarIncidencias() throws Exception {

        List<Incidencia> lista = new ArrayList<>();

        String sql = "SELECT idIncidencia, descripcion FROM incidencia";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Incidencia inc = new Incidencia();
                inc.setIdIncidencia(rs.getInt("idIncidencia"));
                inc.setDescripcion(rs.getString("descripcion"));
                lista.add(inc);
            }
        }

        return lista;
    }

    // ======================================================
    // LISTAR REPUESTOS
    // ======================================================
    public List<Repuesto> listarRepuestos() throws Exception {

        List<Repuesto> lista = new ArrayList<>();

        String sql = "SELECT idRepuesto, nombre, stock FROM repuesto";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Repuesto r = new Repuesto();
                r.setIdRepuesto(rs.getInt("idRepuesto"));
                r.setNombre(rs.getString("nombre"));
                r.setStock(rs.getInt("stock"));
                lista.add(r);
            }
        }

        return lista;
    } 
      
    
}
