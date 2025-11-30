package com.ticketsystem.dao;

import com.ticketsystem.model.Incidencia;
import com.ticketsystem.util.DatabaseConnection;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class IncidenciaDAO {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    // ============================================================
    //  MÉTODOS PARA LISTAR CON BÚSQUEDA + PAGINACIÓN (DASHBOARD ADMIN)
    // ============================================================
    public List<Incidencia> listar(String search, int offset, int limit) {
        List<Incidencia> lista = new ArrayList<>();

        String sql = "SELECT i.idIncidencia, i.descripcion, i.estado, i.idUsuario, i.idEquipo, i.idTecnico, i.fechaRegistro, " +
                "u.nombre AS nombreUsuario, " +
                "e.codigoEquipo, e.tipo AS tipoEquipo, " +
                "t.nombre AS nombreTecnico " +
                "FROM incidencia i " +
                "LEFT JOIN usuario u ON i.idUsuario = u.idUsuario " +
                "LEFT JOIN equipo e ON i.idEquipo = e.idEquipo " +
                "LEFT JOIN tecnico t ON i.idTecnico = t.idTecnico " +
                "WHERE i.descripcion LIKE ? OR i.estado LIKE ? OR u.nombre LIKE ? " +
                "OR e.codigoEquipo LIKE ? OR e.tipo LIKE ? OR t.nombre LIKE ? " +
                "ORDER BY i.fechaRegistro DESC " +
                "LIMIT ? OFFSET ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String filtro = "%" + search + "%";
            for (int i = 1; i <= 6; i++) ps.setString(i, filtro);

            ps.setInt(7, limit);
            ps.setInt(8, offset);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapearIncidencia(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Contar registros para paginación
    public int contarRegistros(String search) {
        String sql = "SELECT COUNT(*) FROM incidencia i " +
                "LEFT JOIN usuario u ON i.idUsuario = u.idUsuario " +
                "LEFT JOIN equipo e ON i.idEquipo = e.idEquipo " +
                "LEFT JOIN tecnico t ON i.idTecnico = t.idTecnico " +
                "WHERE i.descripcion LIKE ? OR i.estado LIKE ? OR u.nombre LIKE ? " +
                "OR e.codigoEquipo LIKE ? OR e.tipo LIKE ? OR t.nombre LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String filtro = "%" + search + "%";
            for (int i = 1; i <= 6; i++) ps.setString(i, filtro);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // ============================================================
    //  CRUD BÁSICO
    // ============================================================

    // Obtener incidencia por ID
    public Incidencia obtenerPorId(int id) {
        String sql = "SELECT i.*, u.nombre AS nombreUsuario, e.codigoEquipo, e.tipo AS tipoEquipo, t.nombre AS nombreTecnico " +
                "FROM incidencia i " +
                "LEFT JOIN usuario u ON i.idUsuario = u.idUsuario " +
                "LEFT JOIN equipo e ON i.idEquipo = e.idEquipo " +
                "LEFT JOIN tecnico t ON i.idTecnico = t.idTecnico " +
                "WHERE i.idIncidencia = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapearIncidencia(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Insertar usando SP (si existe)
    public void insertar(Incidencia i) {
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cs = conn.prepareCall("{CALL sp_insertar_incidencia(?, ?, ?, ?, ?)}")) {

            cs.setString(1, i.getDescripcion());
            cs.setString(2, i.getEstado());
            cs.setInt(3, i.getIdUsuario());
            cs.setInt(4, i.getIdEquipo());
            if (i.getIdTecnico() != null)
                cs.setInt(5, i.getIdTecnico());
            else
                cs.setNull(5, Types.INTEGER);

            cs.execute();

        } catch (SQLException e) {
            insertarSinSP(i);
        }
    }

    // Insertar normal
    private void insertarSinSP(Incidencia i) {
        String sql = "INSERT INTO incidencia (descripcion, estado, idUsuario, idEquipo, idTecnico) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, i.getDescripcion());
            ps.setString(2, i.getEstado());
            ps.setInt(3, i.getIdUsuario());
            ps.setInt(4, i.getIdEquipo());

            if (i.getIdTecnico() != null)
                ps.setInt(5, i.getIdTecnico());
            else
                ps.setNull(5, Types.INTEGER);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Actualizar
    public void actualizar(Incidencia i) {
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement cs = conn.prepareCall("{CALL sp_actualizar_incidencia(?, ?, ?, ?, ?, ?)}")) {

            cs.setInt(1, i.getIdIncidencia());
            cs.setString(2, i.getDescripcion());
            cs.setString(3, i.getEstado());
            cs.setInt(4, i.getIdUsuario());
            cs.setInt(5, i.getIdEquipo());

            if (i.getIdTecnico() != null)
                cs.setInt(6, i.getIdTecnico());
            else
                cs.setNull(6, Types.INTEGER);

            cs.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Eliminar
    public void eliminar(int id) {
        String sql = "DELETE FROM incidencia WHERE idIncidencia=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ============================================================
    //  LISTADO SIMPLE (combos, selects)
    // ============================================================
    public List<Incidencia> listarSimple() {
        List<Incidencia> lista = new ArrayList<>();
        String sql = "SELECT idIncidencia, descripcion FROM incidencia";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Incidencia i = new Incidencia();
                i.setIdIncidencia(rs.getInt("idIncidencia"));
                i.setDescripcion(rs.getString("descripcion"));
                lista.add(i);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // ============================================================
    //  MÉTODOS PARA DASHBOARD DEL TÉCNICO
    // ============================================================

    public int totalAsignadas(int idTecnico) {
    String sql = "SELECT COUNT(*) FROM incidencia WHERE idTecnico = ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, idTecnico);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) return rs.getInt(1);

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0;

    }

    public int totalAbiertas(int idTecnico) {
        return contarPorEstado(idTecnico, "Abierta");
    }

    public int totalEnProceso(int idTecnico) {
        return contarPorEstado(idTecnico, "En Proceso");
    }

    public int totalCerradas(int idTecnico) {
        return contarPorEstado(idTecnico, "Cerrada");
    }

    // Método reutilizable
    private int contarPorEstado(int idTecnico, String estado) {
    String sql = "SELECT COUNT(*) FROM incidencia " +
                 "WHERE idTecnico = ? AND LOWER(TRIM(estado)) = LOWER(TRIM(?))";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, idTecnico);
        ps.setString(2, estado);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) return rs.getInt(1);

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0;
}

    // Listar incidencias asignadas a un técnico
    public List<Incidencia> listarAsignadas(int idTecnico) {
        List<Incidencia> lista = new ArrayList<>();

        String sql = "SELECT i.*, u.nombre AS nombreUsuario, e.codigoEquipo, e.tipo AS tipoEquipo, t.nombre AS nombreTecnico " +
                "FROM incidencia i " +
                "LEFT JOIN usuario u ON i.idUsuario = u.idUsuario " +
                "LEFT JOIN equipo e ON i.idEquipo = e.idEquipo " +
                "LEFT JOIN tecnico t ON i.idTecnico = t.idTecnico " +
                "WHERE i.idTecnico = ? " +
                "ORDER BY i.fechaRegistro DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idTecnico);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) lista.add(mapearIncidencia(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // ============================================================
    //  MAPEO GENERAL
    // ============================================================

    private Incidencia mapearIncidencia(ResultSet rs) throws SQLException {
        Incidencia i = new Incidencia();

        i.setIdIncidencia(rs.getInt("idIncidencia"));
        i.setDescripcion(rs.getString("descripcion"));
        i.setEstado(rs.getString("estado"));
        i.setIdUsuario(rs.getInt("idUsuario"));
        i.setIdEquipo(rs.getInt("idEquipo"));

        int tecnicoId = rs.getInt("idTecnico");
        if (!rs.wasNull()) i.setIdTecnico(tecnicoId);

        i.setFechaRegistro(rs.getTimestamp("fechaRegistro"));

        // Datos relacionados
        i.setNombreUsuario(rs.getString("nombreUsuario"));
        i.setCodigoEquipo(rs.getString("codigoEquipo"));
        i.setTipoEquipo(rs.getString("tipoEquipo"));
        i.setNombreTecnico(rs.getString("nombreTecnico"));

        return i;
    }
    public int totalIncidencias() {
    String sql = "SELECT COUNT(*) FROM incidencia";
    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        if (rs.next()) {
            return rs.getInt(1);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return 0;
}
public int totalAbiertasAdmin() {
        String sql = "SELECT COUNT(*) FROM incidencia WHERE LOWER(estado) LIKE 'abiert%'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
public int totalCerradasAdmin() {
    String sql = "SELECT COUNT(*) FROM incidencia WHERE LOWER(estado) LIKE 'cerrad%'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
public int totalEnProcesoAdmin() {
        String sql = "SELECT COUNT(*) FROM incidencia WHERE LOWER(estado) LIKE 'en proceso%'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

public List<Incidencia> obtenerUltimasIncidencias(int limite) {
        List<Incidencia> lista = new ArrayList<>();

        String sql = "SELECT * FROM incidencia ORDER BY fechaRegistro DESC LIMIT ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, limite);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Incidencia inc = new Incidencia();
                    inc.setIdIncidencia(rs.getInt("idIncidencia"));
                     inc.setDescripcion(rs.getString("descripcion"));
                    inc.setEstado(rs.getString("estado"));
                    inc.setFechaRegistro(rs.getTimestamp("fechaRegistro"));
                    lista.add(inc);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
public int contarPorFecha(LocalDate fecha) {

        String sql = "SELECT COUNT(*) FROM incidencia WHERE DATE(fechaRegistro) = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(fecha));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
public int contarTodas() {
    String sql = "SELECT COUNT(*) FROM incidencia";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        if (rs.next()) {
            return rs.getInt(1);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return 0;
}
public int contarPorEstado(String estado) {
    String sql = "SELECT COUNT(*) FROM incidencia WHERE estado = ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, estado);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return 0;
}
public List<Incidencia> listarUltimas(int cantidad) {

    List<Incidencia> lista = new ArrayList<>();

    String sql = "SELECT i.idIncidencia, i.descripcion, i.estado, i.fechaRegistro, " +
                 "u.nombre AS nombreUsuario " +
                 "FROM incidencia i " +
                 "INNER JOIN usuario u ON u.idUsuario = i.idUsuario " +
                 "ORDER BY i.fechaRegistro DESC " +
                 "LIMIT ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, cantidad);

        try (ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Incidencia inc = new Incidencia();

                inc.setIdIncidencia(rs.getInt("idIncidencia"));
                inc.setDescripcion(rs.getString("descripcion"));
                inc.setEstado(rs.getString("estado"));
                inc.setFechaRegistro(rs.getTimestamp("fechaRegistro"));

                // ⬅️ AHORA MUESTRA EL NOMBRE REAL
                inc.setNombreUsuario(rs.getString("nombreUsuario"));

                lista.add(inc);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return lista;
}

    private static final String BASE_QUERY =
        "SELECT i.idIncidencia, i.descripcion, i.estado, i.fechaRegistro, " +
        "u.nombre AS usuarioNombre, " +
        "e.idEquipo AS codigoEquipo, " +
        "e.tipo AS tipoEquipo, " +
        "t.nombre AS tecnicoNombre " +
        "FROM incidencia i " +
        "JOIN usuario u ON i.idUsuario = u.idUsuario " +
        "JOIN equipo e ON i.idEquipo = e.idEquipo " +
        "LEFT JOIN tecnico t ON i.idTecnico = t.idTecnico ";

 
   public List<Incidencia> obtenerHistorial() {
    String sql = BASE_QUERY + " ORDER BY i.fechaRegistro DESC";
    return ejecutarConsulta(sql);
}
 
    public List<Incidencia> obtenerPorRangoFechas(Date inicio, Date fin) {
        String sql = BASE_QUERY +
                " WHERE DATE(i.fechaRegistro) BETWEEN ? AND ? " +
                " ORDER BY i.fechaRegistro DESC";

        return ejecutarConsulta(sql, inicio, fin);
    }

    // ==========================================
    //      FILTRO POR ESTADO
    // ==========================================
    public List<Incidencia> obtenerPorEstado(String estado) {
        String sql = BASE_QUERY +
                " WHERE i.estado = ? " +
                " ORDER BY i.fechaRegistro DESC";

        return ejecutarConsulta(sql, estado);
    }

    // ==========================================
    //      FILTRO POR TÉCNICO
    // ==========================================
    public List<Incidencia> obtenerPorTecnico(int idTecnico) {
        String sql = BASE_QUERY +
                " WHERE i.idTecnico = ? " +
                " ORDER BY i.fechaRegistro DESC";

        return ejecutarConsulta(sql, idTecnico);
    }

    // ==========================================
    //      MÉTODO GENÉRICO (REUTILIZABLE)
    // ==========================================
    private List<Incidencia> ejecutarConsulta(String sql, Object... params) {

        List<Incidencia> lista = new ArrayList<>();

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            // Insertar parámetros dinámicos
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Incidencia inc = new Incidencia();

                inc.setIdIncidencia(rs.getInt("idIncidencia"));
                inc.setDescripcion(rs.getString("descripcion"));
                inc.setEstado(rs.getString("estado"));
                inc.setFechaRegistro(rs.getTimestamp("fechaRegistro"));
                inc.setNombreUsuario(rs.getString("usuarioNombre"));
                inc.setCodigoEquipo(rs.getString("codigoEquipo"));
                inc.setTipoEquipo(rs.getString("tipoEquipo"));
                inc.setNombreTecnico(rs.getString("tecnicoNombre"));

                lista.add(inc);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
public int contar(String search) {
    int total = 0;
    String sql = "SELECT COUNT(*) FROM incidencia i " +
                 "LEFT JOIN usuario u ON i.idUsuario = u.idUsuario " +
                 "LEFT JOIN equipo e ON i.idEquipo = e.idEquipo " +
                 "LEFT JOIN tecnico t ON i.idTecnico = t.idTecnico " +
                 "WHERE i.descripcion LIKE ? OR i.estado LIKE ? OR u.nombre LIKE ? " +
                 "OR e.codigoEquipo LIKE ? OR e.tipo LIKE ? OR t.nombre LIKE ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        String filtro = "%" + search + "%";
        for (int i = 1; i <= 6; i++) ps.setString(i, filtro);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) total = rs.getInt(1);

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return total;
}

public List<Incidencia> listarTodas() {
    String sql = BASE_QUERY + " ORDER BY i.fechaRegistro DESC";
    return ejecutarConsulta(sql);
}
public List<Incidencia> listarPorTecnico(int idTecnico) {
    String sql = BASE_QUERY +
                 " WHERE i.idTecnico = ? " +
                 " ORDER BY i.fechaRegistro DESC";

    return ejecutarConsulta(sql, idTecnico);
}
public boolean actualizarIncidencia(int idIncidencia, Integer idUsuario, Integer idTecnico,
                                    Integer idEquipo, String descripcion, String estado) {
    String sql = "UPDATE incidencia SET idUsuario=?, idTecnico=?, idEquipo=?, descripcion=?, estado=? " +
                 "WHERE idIncidencia=?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, idUsuario);

        if (idTecnico != null)
            ps.setInt(2, idTecnico);
        else
            ps.setNull(2, Types.INTEGER);

        ps.setInt(3, idEquipo);
        ps.setString(4, descripcion);
        ps.setString(5, estado);
        ps.setInt(6, idIncidencia);

        return ps.executeUpdate() > 0;

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return false;
}
public List<Incidencia> listarPorTecnico(int idTecnico, String search, int pagina, int limit) {

    List<Incidencia> lista = new ArrayList<>();

    int offset = (pagina - 1) * limit;

    String sql = "SELECT i.idIncidencia, i.descripcion, i.estado, i.idUsuario, i.idEquipo, i.idTecnico, i.fechaRegistro, " +
            "u.nombre AS nombreUsuario, " +
            "e.codigoEquipo, e.tipo AS tipoEquipo, " +
            "t.nombre AS nombreTecnico " +
            "FROM incidencia i " +
            "LEFT JOIN usuario u ON i.idUsuario = u.idUsuario " +
            "LEFT JOIN equipo e ON i.idEquipo = e.idEquipo " +
            "LEFT JOIN tecnico t ON i.idTecnico = t.idTecnico " +
            "WHERE i.idTecnico = ? " +
            "AND (i.descripcion LIKE ? OR i.estado LIKE ? OR u.nombre LIKE ? OR e.codigoEquipo LIKE ? OR e.tipo LIKE ? OR t.nombre LIKE ?) " +
            "ORDER BY i.fechaRegistro DESC " +
            "LIMIT ? OFFSET ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, idTecnico);

        String filtro = "%" + search + "%";
        for (int i = 2; i <= 7; i++) ps.setString(i, filtro);

        ps.setInt(8, limit);
        ps.setInt(9, offset);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapearIncidencia(rs));
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return lista;
}
public int contarPorTecnico(int idTecnico, String search) {

    String sql = "SELECT COUNT(*) FROM incidencia i " +
            "LEFT JOIN usuario u ON i.idUsuario = u.idUsuario " +
            "LEFT JOIN equipo e ON i.idEquipo = e.idEquipo " +
            "LEFT JOIN tecnico t ON i.idTecnico = t.idTecnico " +
            "WHERE i.idTecnico = ? " +
            "AND (i.descripcion LIKE ? OR i.estado LIKE ? OR u.nombre LIKE ? OR e.codigoEquipo LIKE ? OR e.tipo LIKE ? OR t.nombre LIKE ?)";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, idTecnico);

        String filtro = "%" + search + "%";
        for (int i = 2; i <= 7; i++) ps.setString(i, filtro);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) return rs.getInt(1);

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return 0;
}

}

