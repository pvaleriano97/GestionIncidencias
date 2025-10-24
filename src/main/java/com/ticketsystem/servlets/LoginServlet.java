package com.ticketsystem.servlets;

import com.ticketsystem.dao.UserDAO;
import com.ticketsystem.model.User;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    
    private UserDAO userDAO;
    
    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("=== LOGIN SERVLET (GET) ===");
        
        // Verificar si ya está logueado
        HttpSession existingSession = request.getSession(false);
        if (existingSession != null && existingSession.getAttribute("user") != null) {
            System.out.println("Usuario ya logueado, redirigiendo a dashboard");
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }
        
        // Verificar si viene de logout exitoso
        String logoutParam = request.getParameter("logout");
        if ("success".equals(logoutParam)) {
            request.setAttribute("mensaje", "Sesión cerrada exitosamente");
            System.out.println("Mostrando mensaje de logout exitoso");
        }
        
        System.out.println("Mostrando página de login");
        request.getRequestDispatcher("/views/login.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("=== LOGIN SERVLET (POST) ===");
        
        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");
        String rememberMe = request.getParameter("rememberMe");
        
        System.out.println("Intento de login para: " + correo);
        
        // Validar credenciales con la base de datos
        User authenticatedUser = userDAO.authenticate(correo, contrasena);
        
        if (authenticatedUser != null) {
            System.out.println("✅ Autenticación EXITOSA para: " + correo);
            System.out.println("Usuario: " + authenticatedUser.getNombre());
            System.out.println("ID: " + authenticatedUser.getIdUsuario());
            System.out.println("Rol: " + authenticatedUser.getRol());
            
            // Crear sesión
            HttpSession session = request.getSession();
            session.setAttribute("user", authenticatedUser);
            session.setAttribute("email", authenticatedUser.getCorreo());
            session.setAttribute("name", authenticatedUser.getNombre());
            session.setAttribute("role", authenticatedUser.getRol());
            session.setAttribute("userId", authenticatedUser.getIdUsuario()); // ✅ AHORA SÍ FUNCIONA
            
            // Configurar tiempo de sesión si "Mantener sesión iniciada" está marcado
            if (rememberMe != null && rememberMe.equals("on")) {
                session.setMaxInactiveInterval(7 * 24 * 60 * 60); // 7 días
                System.out.println("Sesión extendida a 7 días");
            } else {
                session.setMaxInactiveInterval(30 * 60); // 30 minutos
                System.out.println("Sesión configurada para 30 minutos");
            }
            
            // Redirigir al dashboard
            response.sendRedirect(request.getContextPath() + "/dashboard");
            
        } else {
            System.out.println("❌ Autenticación FALLIDA para: " + correo);
            
            // Credenciales inválidas
            request.setAttribute("error", "Correo electrónico o contraseña incorrectos");
            request.setAttribute("email", correo); // Mantener el email ingresado
            
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
        }
    }
}