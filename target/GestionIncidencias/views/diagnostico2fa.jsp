<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Diagnóstico 2FA</title>
</head>
<body>
    <h2>Sesión 2FA:</h2>
    <p>correoPendiente: <%= session.getAttribute("correoPendiente") %></p>
    <p>nombrePendiente: <%= session.getAttribute("nombrePendiente") %></p>
    <p>usuarioPendiente2FA: <%= session.getAttribute("usuarioPendiente2FA") %></p>
    <p>Session ID: <%= session.getId() %></p>
    
    <form action="LoginServlet" method="post">
        <input type="hidden" name="correo" value="<%= session.getAttribute("correoPendiente") %>">
        <input type="text" name="codigo2fa" placeholder="Código">
        <button type="submit">Probar</button>
    </form>
</body>
</html>