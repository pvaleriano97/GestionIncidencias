package com.ticketsystem.servlets;

import com.ticketsystem.dao.TecnicoDAO;
import com.ticketsystem.dao.UsuarioDAO;
import com.ticketsystem.model.Tecnico;
import com.ticketsystem.model.Usuario;
import com.ticketsystem.util.TwoFactorAuth;
import com.ticketsystem.dao.AuditoriaDAO;
import com.ticketsystem.model.AuditoriaLogin;
import com.ticketsystem.util.UserAgentParser;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Date;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO;
    private AuditoriaDAO auditoriaDAO; // Nuevo: DAO para auditoría

    @Override
    public void init() throws ServletException {
        usuarioDAO = new UsuarioDAO();
        auditoriaDAO = new AuditoriaDAO(); // Inicializar auditoría
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            Usuario u = (Usuario) session.getAttribute("user");
            String rol = u.getRol().toLowerCase();
            if ("admin".equals(rol)) {
                response.sendRedirect(request.getContextPath() + "/views/adminDashboard.jsp");
            } else if ("tecnico".equals(rol)) {
                response.sendRedirect(request.getContextPath() + "/views/tecnicoDashboard.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "/views/login.jsp");
            }
            return;
        }

        String logoutParam = request.getParameter("logout");
        if ("success".equals(logoutParam)) {
            request.setAttribute("mensaje", "Sesión cerrada exitosamente");
        }

        String error2FA = request.getParameter("error2FA");
        if (error2FA != null) {
            request.setAttribute("error", "Código de verificación incorrecto");
        }

        request.getRequestDispatcher("/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");
        String codigo2FA = request.getParameter("codigo2fa");
        String rememberMe = request.getParameter("rememberMe");

        System.out.println("\n=== LOGIN INTENTO ===");
        System.out.println("Correo recibido: " + correo);
        System.out.println("Código 2FA recibido: " + codigo2FA);
        System.out.println("Session ID: " + request.getSession().getId());
        System.out.println("====================\n");

        HttpSession session = request.getSession();
        
        // DEBUG: Mostrar estado de la sesión
        System.out.println("\n=== DEBUG SESIÓN 2FA ===");
        System.out.println("correoPendiente en sesión: " + session.getAttribute("correoPendiente"));
        System.out.println("usuarioPendiente2FA en sesión: " + session.getAttribute("usuarioPendiente2FA"));
        System.out.println("nombrePendiente en sesión: " + session.getAttribute("nombrePendiente"));
        System.out.println("==========================\n");
        
        // Validaciones básicas
        if (correo == null || correo.isEmpty()) {
            // SOLUCIÓN: Si correo viene vacío pero estamos en etapa 2FA, usar sesión
            if (codigo2FA != null && !codigo2FA.trim().isEmpty()) {
                correo = (String) session.getAttribute("correoPendiente");
                System.out.println("⚠ Correo estaba vacío, usando de sesión: " + correo);
            } else {
                request.setAttribute("error", "El correo es obligatorio");
                request.getRequestDispatcher("/views/login.jsp").forward(request, response);
                return;
            }
        }

        // ETAPA 2: Verificar código 2FA
        if (codigo2FA != null && !codigo2FA.trim().isEmpty()) {
            System.out.println("\n🔍🔍🔍 ETAPA 2 - DEBUG DETALLADO 🔍🔍🔍");
            System.out.println("Parámetros recibidos:");
            System.out.println("  correo param: " + correo);
            System.out.println("  codigo2fa param: " + codigo2FA);
            
            // Listar TODOS los parámetros para debug
            System.out.println("\nTodos los parámetros recibidos:");
            java.util.Enumeration<String> paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paramName = paramNames.nextElement();
                System.out.println("  " + paramName + " = " + request.getParameter(paramName));
            }
            
            // SOLUCIÓN PRINCIPAL: IGNORAR correo del formulario, SIEMPRE usar sesión
            String correoVerificar = (String) session.getAttribute("correoPendiente");
            System.out.println("\ncorreoPendiente en sesión: " + correoVerificar);
            
            // Si el correo del formulario está vacío o es diferente, usar el de sesión
            if (correoVerificar != null && !correoVerificar.isEmpty()) {
                correo = correoVerificar; // Forzar usar correo de sesión
                System.out.println("✅ Usando correo de sesión (ignorando formulario): " + correo);
            } else {
                System.out.println("⚠ No hay correo en sesión, usando del formulario: " + correo);
            }
            
            correoVerificar = correo; // Usar el correo que tenemos
            System.out.println("Correo final para verificar: " + correoVerificar);
            System.out.println("Código recibido: " + codigo2FA.trim());
            System.out.println("🔍🔍🔍 FIN DEBUG 🔍🔍🔍\n");
            
            if (correoVerificar == null || correoVerificar.isEmpty()) {
                System.out.println("❌ ERROR: Correo es null o vacío!");
                request.setAttribute("error", "No se pudo identificar el correo. Inicia sesión nuevamente.");
                request.getRequestDispatcher("/views/login.jsp").forward(request, response);
                return;
            }
            
            // Verificar código 2FA
            boolean codigoValido = TwoFactorAuth.verificarCodigo(correoVerificar, codigo2FA.trim());
            System.out.println("✅ Resultado verificación: " + (codigoValido ? "VÁLIDO" : "INVÁLIDO"));
            
            if (codigoValido) {
                // Código correcto
                Usuario usuarioPendiente = (Usuario) session.getAttribute("usuarioPendiente2FA");
                
                if (usuarioPendiente != null) {
                    // REGISTRAR AUDITORÍA: 2FA EXITOSO
                    registrarAuditoria(request, correoVerificar, "2FA_EXITOSO", 
                                      codigo2FA.trim(), usuarioPendiente);
                    
                    completarLogin(usuarioPendiente, session, rememberMe, response, request);
                } else {
                    System.out.println("❌ Usuario pendiente no encontrado en sesión");
                    request.setAttribute("error", "Sesión expirada. Inicia sesión nuevamente.");
                    request.getRequestDispatcher("/views/login.jsp").forward(request, response);
                }
            } else {
                // Código incorrecto
                System.out.println("❌ Código 2FA incorrecto");
                
                // Mostrar código almacenado para debug
                String codigoAlmacenado = TwoFactorAuth.obtenerCodigoActual(correoVerificar);
                System.out.println("🔍 Código almacenado para " + correoVerificar + ": " + codigoAlmacenado);
                
                // Obtener datos de sesión para reintentar
                Usuario usuarioPendiente = (Usuario) session.getAttribute("usuarioPendiente2FA");
                String correoSesion = (String) session.getAttribute("correoPendiente");
                String nombreSesion = (String) session.getAttribute("nombrePendiente");
                
                if (usuarioPendiente != null && correoSesion != null) {
                    // REGISTRAR AUDITORÍA: 2FA FALLIDO
                    registrarAuditoria(request, correoVerificar, "2FA_FALLIDO", 
                                      codigo2FA.trim(), usuarioPendiente);
                    
                    // Mantener los datos en la sesión para reintento
                    request.setAttribute("correo", correoSesion);
                    request.setAttribute("nombre", nombreSesion != null ? nombreSesion : usuarioPendiente.getNombre());
                    request.setAttribute("error", "Código de verificación incorrecto. Intenta nuevamente.");
                    request.getRequestDispatcher("/views/verificacion2fa.jsp").forward(request, response);
                } else {
                    System.out.println("❌ Sesión expirada, datos no encontrados");
                    request.setAttribute("error", "Sesión expirada. Inicia sesión nuevamente.");
                    request.getRequestDispatcher("/views/login.jsp").forward(request, response);
                }
            }
            return;
        }
        
        // ETAPA 1: Autenticación básica (usuario/contraseña)
        if (contrasena == null || contrasena.isEmpty()) {
            request.setAttribute("error", "La contraseña es obligatoria");
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
            return;
        }
        
        System.out.println("🔑 ETAPA 1 - Verificando credenciales");
        Usuario authenticatedUser = usuarioDAO.authenticate(correo, contrasena);

        if (authenticatedUser != null) {
            System.out.println("✅ Credenciales válidas para: " + correo);
            
            // REGISTRAR AUDITORÍA: CREDENCIALES VÁLIDAS
            registrarAuditoria(request, correo, "CREDENCIALES_VALIDAS", null, authenticatedUser);
            
            // Generar código 2FA
            String codigoGenerado = TwoFactorAuth.generarYGuardarCodigo(correo);
            
            // REGISTRAR AUDITORÍA: CÓDIGO 2FA GENERADO
            registrarAuditoria(request, correo, "2FA_GENERADO", codigoGenerado, authenticatedUser);
            
            // Guardar usuario temporalmente en sesión (CON MÁS DATOS)
            session.setAttribute("usuarioPendiente2FA", authenticatedUser);
            session.setAttribute("correoPendiente", correo);
            session.setAttribute("nombrePendiente", authenticatedUser.getNombre() + " " + authenticatedUser.getApellido());
            
            // Mostrar código en consola (para pruebas)
            System.out.println("\n📧 ===== CÓDIGO 2FA GENERADO =====");
            System.out.println("PARA: " + correo);
            System.out.println("NOMBRE: " + authenticatedUser.getNombre() + " " + authenticatedUser.getApellido());
            System.out.println("CÓDIGO: " + codigoGenerado);
            System.out.println("================================\n");
            
            // También mostrar en la sesión actual
            System.out.println("📋 DATOS GUARDADOS EN SESIÓN:");
            System.out.println("- correoPendiente: " + session.getAttribute("correoPendiente"));
            System.out.println("- nombrePendiente: " + session.getAttribute("nombrePendiente"));
            System.out.println("- usuarioPendiente2FA: " + session.getAttribute("usuarioPendiente2FA"));
            
            // Redirigir a página de verificación (CON DATOS EN SESIÓN)
            request.setAttribute("correo", correo);
            request.setAttribute("nombre", authenticatedUser.getNombre() + " " + authenticatedUser.getApellido());
            request.getRequestDispatcher("/views/verificacion2fa.jsp").forward(request, response);
            
        } else {
            System.out.println("❌ Credenciales inválidas para: " + correo);
            
            // REGISTRAR AUDITORÍA: CREDENCIALES INVÁLIDAS
            registrarAuditoria(request, correo, "CREDENCIALES_INVALIDAS", null, null);
            
            request.setAttribute("error", "Credenciales incorrectas");
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
        }
    }
    
    /**
     * Completa el login después de 2FA exitoso
     */
    private void completarLogin(Usuario usuario, HttpSession session, String rememberMe,
                               HttpServletResponse response, HttpServletRequest request) 
            throws IOException, ServletException {
        
        System.out.println("\n🎉 LOGIN COMPLETADO EXITOSAMENTE");
        System.out.println("Usuario: " + usuario.getNombre() + " " + usuario.getApellido());
        System.out.println("Correo: " + usuario.getCorreo());
        System.out.println("Rol: " + usuario.getRol());
        
        // Guardar en sesión
        session.setAttribute("user", usuario);
        session.setAttribute("email", usuario.getCorreo());
        session.setAttribute("name", usuario.getNombre() + " " + usuario.getApellido());
        session.setAttribute("role", usuario.getRol());
        session.setAttribute("userId", usuario.getIdUsuario());
        
        // Limpiar datos temporales de 2FA (IMPORTANTE: hacerlo al final)
        session.removeAttribute("usuarioPendiente2FA");
        session.removeAttribute("correoPendiente");
        session.removeAttribute("nombrePendiente");
        
        // Técnico específico
        if ("tecnico".equalsIgnoreCase(usuario.getRol())) {
            TecnicoDAO tdao = new TecnicoDAO();
            Tecnico tecnico = tdao.buscarPorIdUsuario(usuario.getIdUsuario());
            
            if (tecnico != null) {
                session.setAttribute("idTecnico", tecnico.getIdTecnico());
                session.setAttribute("nombreTecnico", tecnico.getNombre());
                System.out.println("ID Técnico: " + tecnico.getIdTecnico());
            } else {
                session.invalidate();
                request.setAttribute("error", "⚠ Su usuario no está asociado a un técnico del sistema.");
                request.getRequestDispatcher("/views/login.jsp").forward(request, response);
                return;
            }
        }
        
        // Remember Me cookie
        if ("on".equals(rememberMe)) {
            Cookie emailCookie = new Cookie("rememberEmail", usuario.getCorreo());
            emailCookie.setMaxAge(30 * 24 * 60 * 60);
            emailCookie.setPath(request.getContextPath());
            response.addCookie(emailCookie);
        }
        
        // Redirección según rol
        String contextPath = request.getContextPath();
        if ("admin".equalsIgnoreCase(usuario.getRol())) {
            response.sendRedirect(contextPath + "/AdminDashboardServlet");
        } else if ("tecnico".equalsIgnoreCase(usuario.getRol())) {
            response.sendRedirect(contextPath + "/TecnicoDashboardServlet");
        } else {
            response.sendRedirect(contextPath + "/views/dashboard.jsp");
        }
    }
    
    /**
     * MÉTODO NUEVO: Registrar evento en auditoría
     */
    private void registrarAuditoria(HttpServletRequest request, String correo, 
                                   String status, String codigo2fa, Usuario usuario) {
        try {
            System.out.println("\n📊 REGISTRANDO AUDITORÍA:");
            System.out.println("Correo: " + correo);
            System.out.println("Estado: " + status);
            System.out.println("Código 2FA: " + codigo2fa);
            
            AuditoriaLogin auditoria = new AuditoriaLogin();
            
            // Información del usuario
            if (usuario != null) {
                auditoria.setIdUsuario(usuario.getIdUsuario());
                auditoria.setNombreUsuario(usuario.getNombre() + " " + usuario.getApellido());
                auditoria.setRolUsuario(usuario.getRol());
            }
            
            auditoria.setCorreoUsuario(correo);
            auditoria.setStatusLogin(status);
            auditoria.setCodigo2fa(codigo2fa);
            auditoria.setFechaLogin(new Date());
            
            // Información de la solicitud
            auditoria.setIpAddress(UserAgentParser.getClientIP(request));
            auditoria.setUserAgent(request.getHeader("User-Agent"));
            auditoria.setSessionId(request.getSession().getId());
            auditoria.setNavegador(UserAgentParser.parseBrowser(request.getHeader("User-Agent")));
            auditoria.setSistemaOperativo(UserAgentParser.parseOS(request.getHeader("User-Agent")));
            
            // Registrar en base de datos
            boolean registrado = auditoriaDAO.registrarLogin(auditoria);
            
            if (registrado) {
                System.out.println("✅ Auditoría registrada exitosamente");
            } else {
                System.out.println("⚠ Auditoría no pudo registrarse");
            }
            
        } catch (Exception e) {
            System.out.println("❌ Error registrando auditoría: " + e.getMessage());
            e.printStackTrace();
        }
    }
}