package com.ticketsystem.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ConfiguracionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Verificar si el usuario está logueado
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        // Pasar datos a la vista usando nuestra estructura de sesión
        request.setAttribute("nombreUsuario", session.getAttribute("name"));
        request.setAttribute("emailUsuario", session.getAttribute("email"));
        request.setAttribute("rolUsuario", session.getAttribute("role"));
        request.setAttribute("userId", session.getAttribute("userId"));
        
        // Mostrar la página de configuración
        request.getRequestDispatcher("/views/configuracion.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Verificar sesión
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        // Obtener datos del formulario
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        String descripcion = request.getParameter("descripcion");
        String departamento = request.getParameter("departamento");
        String cargo = request.getParameter("cargo");
        String telefono = request.getParameter("telefono");
        String ubicacion = request.getParameter("ubicacion");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        
        // Validar que las contraseñas coincidan
        if (password != null && !password.isEmpty() && !password.equals(confirmPassword)) {
            request.setAttribute("error", "Las contraseñas no coinciden");
            
            // Recargar datos en el request para mantener el formulario
            request.setAttribute("nombreUsuario", session.getAttribute("name"));
            request.setAttribute("emailUsuario", session.getAttribute("email"));
            request.setAttribute("rolUsuario", session.getAttribute("role"));
            request.setAttribute("apellido", apellido);
            request.setAttribute("descripcion", descripcion);
            request.setAttribute("departamento", departamento);
            request.setAttribute("cargo", cargo);
            request.setAttribute("telefono", telefono);
            request.setAttribute("ubicacion", ubicacion);
            
            request.getRequestDispatcher("/views/configuracion.jsp").forward(request, response);
            return;
        }
        
        // Validar longitud mínima de contraseña
        if (password != null && !password.isEmpty() && password.length() < 8) {
            request.setAttribute("error", "La contraseña debe tener al menos 8 caracteres");
            
            // Recargar datos en el request
            request.setAttribute("nombreUsuario", session.getAttribute("name"));
            request.setAttribute("emailUsuario", session.getAttribute("email"));
            request.setAttribute("rolUsuario", session.getAttribute("role"));
            request.setAttribute("apellido", apellido);
            request.setAttribute("descripcion", descripcion);
            request.setAttribute("departamento", departamento);
            request.setAttribute("cargo", cargo);
            request.setAttribute("telefono", telefono);
            request.setAttribute("ubicacion", ubicacion);
            
            request.getRequestDispatcher("/views/configuracion.jsp").forward(request, response);
            return;
        }
        
        // Aquí iría la lógica para actualizar en la base de datos
        System.out.println("=== ACTUALIZANDO CONFIGURACIÓN ===");
        System.out.println("Nombre: " + nombre);
        System.out.println("Apellido: " + apellido);
        System.out.println("Email: " + email);
        System.out.println("Descripción: " + descripcion);
        System.out.println("Departamento: " + departamento);
        System.out.println("Cargo: " + cargo);
        System.out.println("Teléfono: " + telefono);
        System.out.println("Ubicación: " + ubicacion);
        System.out.println("Cambio de contraseña: " + (password != null && !password.isEmpty() ? "SÍ" : "NO"));
        
        // Actualizar datos en sesión
        if (nombre != null && !nombre.isEmpty()) {
            session.setAttribute("name", nombre);
        }
        if (email != null && !email.isEmpty()) {
            session.setAttribute("email", email);
        }
        
        // Guardar otros atributos en sesión para persistencia
        session.setAttribute("apellido", apellido);
        session.setAttribute("descripcion", descripcion);
        session.setAttribute("departamento", departamento);
        session.setAttribute("cargo", cargo);
        session.setAttribute("telefono", telefono);
        session.setAttribute("ubicacion", ubicacion);
        
        // Mostrar mensaje de éxito
        request.setAttribute("success", "Configuración actualizada correctamente");
        
        // Recargar datos actualizados en el request
        request.setAttribute("nombreUsuario", session.getAttribute("name"));
        request.setAttribute("emailUsuario", session.getAttribute("email"));
        request.setAttribute("rolUsuario", session.getAttribute("role"));
        request.setAttribute("apellido", apellido);
        request.setAttribute("descripcion", descripcion);
        request.setAttribute("departamento", departamento);
        request.setAttribute("cargo", cargo);
        request.setAttribute("telefono", telefono);
        request.setAttribute("ubicacion", ubicacion);
        
        request.getRequestDispatcher("/views/configuracion.jsp").forward(request, response);
    }
}