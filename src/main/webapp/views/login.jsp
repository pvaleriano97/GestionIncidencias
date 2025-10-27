<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Iniciar Sesión - Sistema de Tickets</title>

    <!-- CSS unificado -->
    <link rel="stylesheet" href="<c:url value='/css/main.css'/>">

    <!-- Iconos -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body class="login-body">
    <div class="login-container">
        <div class="login-header">
            <h1>¡BIENVENIDO DE NUEVO!</h1>
            <p>Inicia sesión con tu correo corporativo</p>
        </div>

        <!-- Mensajes -->
        <c:if test="${not empty mensaje}">
            <div class="alert alert-success">
                ✅ ${mensaje}
            </div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="error-message">
                ${error}
            </div>
        </c:if>

        <!-- Formulario -->
        <form action="<c:url value='/login'/>" method="post">
            <div class="form-group">
                <label for="correo">Correo electrónico*</label>
                <input type="email" id="correo" name="correo"
                       value="${not empty correo ? correo : ''}" required>
            </div>

            <div class="form-group">
                <label for="contrasena">Contraseña*</label>
                <input type="password" id="contrasena" name="contrasena" required>
            </div>

            <div class="form-options">
                <label class="checkbox-container">
                    <input type="checkbox" name="rememberMe">
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

    <!-- JS opcional -->
    <script src="<c:url value='/js/script.js'/>"></script>
</body>
</html>
