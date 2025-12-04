<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Iniciar Sesión</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body class="login-body">

<div class="d-flex justify-content-center align-items-center vh-100">
    <div class="login-container">
        <div class="text-center mb-4">
            <i class="fa-solid fa-tools" style="font-size: 55px; color:#34495E;"></i>
            <h1>Gestión de Incidencias</h1>
            <p>Inicia sesión para continuar</p>
        </div>

        <form action="${pageContext.request.contextPath}/LoginServlet" method="post">
            <div class="form-group mb-3">
                <label for="correo">Correo electrónico</label>
                <input type="email" id="correo" name="correo" required
                       value="<c:out value='${param.correo}'/>" class="form-control">
            </div>

            <div class="form-group mb-3">
                <label for="contrasena">Contraseña</label>
                <input type="password" id="contrasena" name="contrasena" required class="form-control">
            </div>

            <div class="remember mb-3">
                <input class="form-check-input" type="checkbox" name="rememberMe" id="rememberMe">
                <label class="form-check-label" for="rememberMe">Mantener sesión iniciada</label>
            </div>

            <button type="submit" class="login-button w-100">Ingresar</button>

            <!-- Mensajes -->
            <c:if test="${not empty error}">
                <div class="alert alert-danger mt-3 text-center">${error}</div>
            </c:if>
            <c:if test="${not empty mensaje}">
                <div class="alert alert-success mt-3 text-center">${mensaje}</div>
            </c:if>
            
            <!-- Info 2FA -->
            <div class="alert alert-info mt-3 text-center">
                <small>
                    <i class="fas fa-shield-alt me-1"></i>
                    Después de ingresar credenciales, se te pedirá un código de verificación
                </small>
            </div>
        </form>
    </div>
</div>

</body>
</html>