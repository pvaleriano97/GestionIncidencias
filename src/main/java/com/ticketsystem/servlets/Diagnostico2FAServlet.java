package com.ticketsystem.servlets;

import com.ticketsystem.util.TwoFactorAuth;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;

@WebServlet("/Diagnostico2FA")
public class Diagnostico2FAServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<html><head><title>Diagnóstico 2FA</title>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<style>");
        out.println("body { padding: 20px; }");
        out.println(".card { margin-bottom: 20px; }");
        out.println("</style>");
        out.println("</head><body>");
        out.println("<div class='container'>");
        out.println("<h1 class='mb-4'>🩺 Diagnóstico Autenticación 2FA</h1>");
        
        String correo = request.getParameter("correo");
        String codigo = request.getParameter("codigo");
        String accion = request.getParameter("accion");
        
        if ("verificar".equals(accion) && correo != null && codigo != null) {
            out.println("<div class='card'>");
            out.println("<div class='card-header'>Resultado de Verificación</div>");
            out.println("<div class='card-body'>");
            
            // LLAMADA DIRECTA - NO USAR REFLEXIÓN
            String codigoAlmacenado = TwoFactorAuth.obtenerCodigoActual(correo);
            
            out.println("<p><strong>Correo:</strong> " + correo + "</p>");
            out.println("<p><strong>Código ingresado:</strong> " + codigo + "</p>");
            out.println("<p><strong>Código almacenado:</strong> " + (codigoAlmacenado != null ? codigoAlmacenado : "NO ENCONTRADO") + "</p>");
            
            boolean valido = TwoFactorAuth.verificarCodigo(correo, codigo);
            out.println("<div class='alert " + (valido ? "alert-success" : "alert-danger") + "'>");
            out.println("<h5>Resultado: " + (valido ? "✅ VÁLIDO" : "❌ INVÁLIDO") + "</h5>");
            out.println("</div>");
            
            out.println("</div></div>");
        }
        
        if ("generar".equals(accion) && correo != null) {
            out.println("<div class='card'>");
            out.println("<div class='card-header'>Nuevo Código Generado</div>");
            out.println("<div class='card-body'>");
            
            // LLAMADA DIRECTA - NO USAR REFLEXIÓN
            String nuevoCodigo = TwoFactorAuth.generarYGuardarCodigo(correo);
            
            out.println("<div class='alert alert-success'>");
            out.println("<h5>✅ Nuevo código generado para " + correo + "</h5>");
            out.println("<h2 class='text-center my-4'>" + nuevoCodigo + "</h2>");
            out.println("<p>Usa este código en el formulario de verificación</p>");
            out.println("</div>");
            
            out.println("</div></div>");
        }
        
        // Resto del código permanece igual...
        out.println("<div class='card'>");
        out.println("<div class='card-header'>Herramientas de Diagnóstico</div>");
        out.println("<div class='card-body'>");
        
        out.println("<form method='get' class='row g-3'>");
        out.println("<div class='col-md-6'>");
        out.println("<label for='correo' class='form-label'>Correo Electrónico:</label>");
        out.println("<input type='email' id='correo' name='correo' class='form-control' value='" + (correo != null ? correo : "jean.rubio@bcp.com.pe") + "' required>");
        out.println("</div>");
        
        out.println("<div class='col-md-6'>");
        out.println("<label for='codigo' class='form-label'>Código 2FA (6 dígitos):</label>");
        out.println("<input type='text' id='codigo' name='codigo' class='form-control' maxlength='6' pattern='[0-9]{6}' value='" + (codigo != null ? codigo : "") + "'>");
        out.println("</div>");
        
        out.println("<div class='col-12'>");
        out.println("<button type='submit' name='accion' value='verificar' class='btn btn-primary me-2'>");
        out.println("🔍 Verificar Código");
        out.println("</button>");
        out.println("<button type='submit' name='accion' value='generar' class='btn btn-success me-2'>");
        out.println("🔄 Generar Nuevo Código");
        out.println("</button>");
        out.println("<a href='login.jsp' class='btn btn-secondary'>");
        out.println("← Volver al Login");
        out.println("</a>");
        out.println("</div>");
        out.println("</form>");
        
        out.println("</div></div>");
        
        // Información del sistema
        out.println("<div class='card'>");
        out.println("<div class='card-header'>Información del Sistema</div>");
        out.println("<div class='card-body'>");
        out.println("<p><strong>URL Actual:</strong> " + request.getRequestURL() + "</p>");
        out.println("<p><strong>Sesión ID:</strong> " + request.getSession().getId() + "</p>");
        out.println("<p><strong>Correo en sesión:</strong> " + request.getSession().getAttribute("correoPendiente") + "</p>");
        out.println("</div></div>");
        
        out.println("</div>");
        out.println("</body></html>");
    }
}