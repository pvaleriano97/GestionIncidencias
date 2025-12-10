package com.ticketsystem.servlets;

import com.ticketsystem.dao.UsuarioDAO;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ValidarUsuarioServlet")
public class ValidarUsuarioServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String nombre   = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String correo   = request.getParameter("correo");
        String idStr    = request.getParameter("idUsuario");

        Integer idExcluir = (idStr != null && !idStr.isEmpty())
                ? Integer.parseInt(idStr)
                : null;

        boolean duplicadoNombre = false;
        boolean duplicadoCorreo = false;

        // Validación segura de nombre+apellido
        if (nombre != null && !nombre.isEmpty() &&
            apellido != null && !apellido.isEmpty()) {

            duplicadoNombre =
                usuarioDAO.existeNombreApellido(nombre, apellido, idExcluir);
        }

        // Validación correo
        if (correo != null && !correo.isEmpty()) {
            duplicadoCorreo =
                usuarioDAO.existeCorreo(correo, idExcluir);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String json = String.format(
            "{ \"duplicadoNombre\": %s, \"duplicadoCorreo\": %s }",
            duplicadoNombre,
            duplicadoCorreo
        );

        response.getWriter().write(json);
    }
}
