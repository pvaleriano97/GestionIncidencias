package com.ticketsystem.servlets;

import com.ticketsystem.dao.UsuarioDAO;
import com.ticketsystem.dao.TecnicoDAO;
import com.ticketsystem.model.Usuario;
import com.ticketsystem.model.Tecnico;
import com.ticketsystem.util.TwoFactorAuth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        String codigo2FA = request.getParameter("codigo2FA");
        String rememberMe = request.getParameter("rememberMe");

        /* =================================================
           📌 PASO 1: VALIDAR CÓDIGO 2FA
        ================================================= */
        if (codigo2FA != null) {

            Usuario usuarioPendiente = (Usuario) session.getAttribute("usuarioPendiente");

            if (usuarioPendiente == null) {
                response.sendRedirect("views/login.jsp");
                return;
            }

            boolean valido = TwoFactorAuth.validarCodigo(
                usuarioPendiente.getSecret_key(),   // <--- verifica tu getter
                codigo2FA
            );

            if (!valido) {
                request.setAttribute("error2FA", "Código incorrecto.");
                request.setAttribute("show2FA", true);
                request.setAttribute("nombre", usuarioPendiente.getNombre());
                request.setAttribute("qrUrl",
                        TwoFactorAuth.generarQR(
                                usuarioPendiente.getCorreo(),
                                "GestionIncidencias",
                                usuarioPendiente.getSecret_key()
                        )
                );
                request.getRequestDispatcher("views/login.jsp").forward(request, response);
                return;
            }

            // 2FA correcto → completar login
            session.removeAttribute("usuarioPendiente");
            completarLogin(usuarioPendiente, session, rememberMe, response, request);
            return;
        }

        /* =================================================
           📌 PASO 2: LOGIN NORMAL (correo + contraseña)
        ================================================= */
        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");

        Usuario usuario = usuarioDAO.autenticar(correo, contrasena);

        if (usuario == null) {
            request.setAttribute("error", "Correo o contraseña incorrectos.");
            request.getRequestDispatcher("views/login.jsp").forward(request, response);
            return;
        }

        /* =================================================
           📌 PASO 3: MOSTRAR 2FA
        ================================================= */
        session.setAttribute("usuarioPendiente", usuario);
        request.setAttribute("show2FA", true);
        request.setAttribute("nombre", usuario.getNombre());

        String qrUrl = TwoFactorAuth.generarQR(
                usuario.getCorreo(),
                "GestionIncidencias",
                usuario.getSecret_key()
        );

        request.setAttribute("qrUrl", qrUrl);
        request.getRequestDispatcher("views/login.jsp").forward(request, response);
    }

    /* =================================================
       📌 FINALIZAR LOGIN + REDIRECCIÓN POR ROL
    ================================================= */
    private void completarLogin(Usuario usuario,
                                HttpSession session,
                                String rememberMe,
                                HttpServletResponse response,
                                HttpServletRequest request)
            throws IOException, ServletException {

        // Datos de sesión
        session.setAttribute("user", usuario);
        session.setAttribute("email", usuario.getCorreo());
        session.setAttribute("name", usuario.getNombre() + " " + usuario.getApellido());
        session.setAttribute("role", usuario.getRol());
        session.setAttribute("userId", usuario.getIdUsuario());

        /* =================================================
           📌 ROL: TÉCNICO
        ================================================= */
        if ("tecnico".equalsIgnoreCase(usuario.getRol())) {

            TecnicoDAO tdao = new TecnicoDAO();
            Tecnico tecnico = tdao.buscarPorIdUsuario(usuario.getIdUsuario());

            if (tecnico == null) {
                session.invalidate();
                request.setAttribute("error", "Su usuario no está asociado a un técnico.");
                request.getRequestDispatcher("views/login.jsp").forward(request, response);
                return;
            }

            session.setAttribute("idTecnico", tecnico.getIdTecnico());
            session.setAttribute("nombreTecnico", tecnico.getNombre());

            response.sendRedirect(request.getContextPath() + "/TecnicoDashboardServlet");
            return;
        }

        /* =================================================
           📌 ROL: ADMIN
        ================================================= */
        if ("admin".equalsIgnoreCase(usuario.getRol())) {
            response.sendRedirect(request.getContextPath() + "/AdminDashboardServlet");
            return;
        }

        /* =================================================
           📌 ROL INVÁLIDO
        ================================================= */
        session.invalidate();
        request.setAttribute("error", "Rol no reconocido.");
        request.getRequestDispatcher("views/login.jsp").forward(request, response);
    }
}
