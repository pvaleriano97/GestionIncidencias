<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Error</title>
    <link rel="stylesheet" href="<c:url value='/css/main.css'/>">
</head>
<body>
    <h1>Ocurrió un error</h1>
    <p style="color:red;">
        ${error}
    </p>
    <a href="<c:url value='/UsuarioServlet'/>">Volver a usuarios</a>
</body>
</html>
