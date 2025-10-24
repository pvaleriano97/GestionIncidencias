<%-- 
    Document   : login
    Created on : 12 oct 2025, 8:08:43 p.m.
    Author     : acuar
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Iniciar Sesión - Sistema de Tickets</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="login-container">
        <div class="login-header">
            <h1>¡BIENVENIDO DE NUEVO!</h1>
            <p>Inicia sesión con tu correo corporativo</p>
        </div>
        
        <!-- Mostrar mensaje de logout exitoso -->
        <c:if test="${not empty mensaje}">
            <div class="alert alert-success">
                <span>✅</span> ${mensaje}
            </div>
        </c:if>
        
        <!-- Mostrar error de login -->
        <c:if test="${not empty error}">
            <div class="error-message">
                ${error}
            </div>
        </c:if>
        
        <form class="login-form" action="${pageContext.request.contextPath}/login" method="post">
            <div class="form-group">
                <label for="correo">Correo electrónico*</label>
                <input type="email" id="correo" name="correo" 
                       value="${not empty correo ? correo : 'frocab@bcp.com.pe'}" required>
            </div>
            
            <div class="form-group">
                <label for="contrasena">Contraseña*</label>
                <input type="password" id="contrasena" name="contrasena" 
                       value="${not empty correo ? '' : '@B123'}" required>
            </div>
            
            <div class="form-options">
                <label class="checkbox-container">
                    <input type="checkbox" id="rememberMe" name="rememberMe">
                    <span class="checkmark"></span>
                    Mantener sesión iniciada
                </label>
                
                <a href="#" class="forgot-password">¿Olvidaste tu contraseña?</a>
            </div>
            
            <button type="submit" class="login-button">Ingresar</button>
        </form>
        
        <div class="footer">
            <p>Designed by HP</p>
        </div>
    </div>
    
    <script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>