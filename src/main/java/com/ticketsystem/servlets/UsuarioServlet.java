package com.ticketsystem.servlets;

import com.ticketsystem.dao.UsuarioDAO;
import com.ticketsystem.dao.IUsuarioDAO;
import com.ticketsystem.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UsuarioServlet", urlPatterns = {"/UsuarioServlet"})
public class UsuarioServlet extends HttpServlet {

    private final IUsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "listar";

        try {
            switch (action) {
                case "edit":
                    int idEdit = Integer.parseInt(request.getParameter("id"));
                    Usuario usuarioEdit = usuarioDAO.buscarPorId(idEdit);
                    request.setAttribute("usuarioEdit", usuarioEdit);
                    listarUsuarios(request, response);
                    break;

                case "delete":
                    int idDel = Integer.parseInt(request.getParameter("id"));
                    usuarioDAO.eliminar(idDel);
                    listarUsuarios(request, response);
                    break;

                default:
                    listarUsuarios(request, response);
                    break;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String idUsuarioStr = request.getParameter("idUsuario");

        try {
            Usuario usuario = new Usuario();
            usuario.setNombre(request.getParameter("nombre"));
            usuario.setApellido(request.getParameter("apellido"));
            usuario.setCorreo(request.getParameter("correo"));
            usuario.setContrasena(request.getParameter("contrasena")); // 🔒 Nuevo campo
            usuario.setRol(request.getParameter("rol"));
            usuario.setArea(request.getParameter("area")); // 🏢 Nuevo campo

            if (idUsuarioStr == null || idUsuarioStr.isEmpty()) {
                usuarioDAO.insertar(usuario);
            } else {
                usuario.setIdUsuario(Integer.parseInt(idUsuarioStr));
                usuarioDAO.actualizar(usuario);
            }

            listarUsuarios(request, response);

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("error", ex.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }

    private void listarUsuarios(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int pagina = 1;
            int registrosPorPagina = 5;
            String search = request.getParameter("search");
            if (search == null) search = "";

            if (request.getParameter("pagina") != null) {
                pagina = Integer.parseInt(request.getParameter("pagina"));
            }

            List<Usuario> listaUsuarios = usuarioDAO.listar(pagina, registrosPorPagina, search);
            int totalRegistros = usuarioDAO.contar(search);
            int totalPaginas = (int) Math.ceil(totalRegistros / (double) registrosPorPagina);

            request.setAttribute("listaUsuarios", listaUsuarios);
            request.setAttribute("paginaActual", pagina);
            request.setAttribute("totalPaginas", totalPaginas);
            request.setAttribute("search", search);

            request.getRequestDispatcher("/views/usuario.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet para gestión de Usuarios (con contraseña y área) con JSP y paginación";
    }
}
