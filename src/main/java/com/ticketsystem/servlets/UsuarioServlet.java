package com.ticketsystem.servlets;

import com.ticketsystem.dao.UsuarioDAO;
import com.ticketsystem.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/UsuarioServlet")
public class UsuarioServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String search = request.getParameter("search") != null ? request.getParameter("search") : "";

        if (action == null) action = "";

        switch (action) {
            case "edit":
                editarUsuario(request, response);
                return;
            case "delete":
                eliminarUsuario(request, response);
                return;
            default:
                listarUsuarios(request, response, search);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idUsuarioStr = request.getParameter("idUsuario");

        if (idUsuarioStr == null || idUsuarioStr.isEmpty()) {
            guardarUsuario(request, response);
        } else {
            actualizarUsuario(request, response);
        }
    }

    // ============================================================
    // LISTAR + PAGINAR
    // ============================================================
    private void listarUsuarios(HttpServletRequest request, HttpServletResponse response, String search)
            throws ServletException, IOException {

        int paginaActual = 1;
        int registrosPorPagina = 8;

        if (request.getParameter("pagina") != null) {
            paginaActual = Integer.parseInt(request.getParameter("pagina"));
        }

        // 🔥 Corrección: page = paginaActual, size = registrosPorPagina
        List<Usuario> lista = usuarioDAO.listar(paginaActual, registrosPorPagina, search);
        int totalRegistros = usuarioDAO.contar(search);
        int totalPaginas = (int) Math.ceil((double) totalRegistros / registrosPorPagina);

        request.setAttribute("listaUsuarios", lista);
        request.setAttribute("paginaActual", paginaActual);
        request.setAttribute("totalPaginas", totalPaginas);
        request.setAttribute("search", search);

        request.getRequestDispatcher("/views/usuario.jsp").forward(request, response);
    }

    // ============================================================
    // EDITAR
    // ============================================================
    private void editarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        try {
            Usuario usuario = usuarioDAO.buscarPorId(id);
            request.setAttribute("usuarioEdit", usuario);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "No se pudo cargar el usuario");
        }

        listarUsuarios(request, response, "");
    }

    // ============================================================
    // GUARDAR
    // ============================================================
    private void guardarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Usuario u = new Usuario();

        u.setNombre(request.getParameter("nombre"));
        u.setApellido(request.getParameter("apellido"));
        u.setCorreo(request.getParameter("correo"));
        u.setContrasena(request.getParameter("contrasena")); // DAO ya aplica SHA-256
        u.setArea(request.getParameter("area"));
        u.setRol(request.getParameter("rol"));

        try {
            boolean guardado = usuarioDAO.insertar(u);

            if (guardado) {
                request.setAttribute("success", "Usuario guardado correctamente");
            } else {
                request.setAttribute("error", "No se pudo guardar el usuario");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al guardar usuario");
        }

        listarUsuarios(request, response, "");
    }

    // ============================================================
    // ACTUALIZAR
    // ============================================================
    private void actualizarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
            Usuario original = usuarioDAO.buscarPorId(idUsuario);

            if (original == null) {
                request.setAttribute("error", "Usuario no encontrado");
                listarUsuarios(request, response, "");
                return;
            }

            String nuevaPass = request.getParameter("contrasena");

            // NO permitir cambiar pass del admin
            if ("admin".equalsIgnoreCase(original.getRol())) {
                if (nuevaPass != null && !nuevaPass.isEmpty()) {
                    request.setAttribute("error", "No se puede cambiar la contraseña del administrador");
                    listarUsuarios(request, response, "");
                    return;
                }
            }

            // Actualizar datos
            original.setNombre(request.getParameter("nombre"));
            original.setApellido(request.getParameter("apellido"));
            original.setCorreo(request.getParameter("correo"));
            original.setArea(request.getParameter("area"));
            original.setRol(request.getParameter("rol"));

            // Solo actualiza la contraseña si NO es admin
            if (!"admin".equalsIgnoreCase(original.getRol())) {
                if (nuevaPass != null && !nuevaPass.isEmpty()) {
                    original.setContrasena(nuevaPass);
                }
            }

            boolean actualizado = usuarioDAO.actualizar(original);

            if (actualizado) {
                request.setAttribute("success", "Usuario actualizado correctamente");
            } else {
                request.setAttribute("error", "No se pudo actualizar el usuario");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al actualizar usuario");
        }

        listarUsuarios(request, response, "");
    }

    // ============================================================
    // ELIMINAR
    // ============================================================
    private void eliminarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            boolean ok = usuarioDAO.eliminar(id);

            if (ok) {
                request.setAttribute("success", "Usuario eliminado correctamente");
            } else {
                request.setAttribute("error", "No se pudo eliminar el usuario");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error eliminando usuario");
        }

        listarUsuarios(request, response, "");
    }
}
