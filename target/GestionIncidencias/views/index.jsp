<%-- 
    Document   : index
    Created on : 12 oct 2025, 8:21:14 p.m.
    Author     : acuar
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sistema de Tickets</title>
    <script>
        // Redirección automática al login
        window.location.href = "${pageContext.request.contextPath}/login";
    </script>
</head>
<body>
    <div style="text-align: center; margin-top: 100px;">
        <h1>Sistema de Gestión de Tickets</h1>
        <p>Redirigiendo al login...</p>
        <p>Si no eres redirigido automáticamente, <a href="${pageContext.request.contextPath}/login">haz clic aquí</a>.</p>
    </div>
</body>
</html>