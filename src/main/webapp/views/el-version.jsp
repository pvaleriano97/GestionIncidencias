<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.el.ExpressionFactory, java.lang.Package" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Versión de EL</title>
</head>
<body>
<h2>Prueba de Expression Language (EL)</h2>
<%
    ExpressionFactory factory = ExpressionFactory.newInstance();
    Package pkg = factory.getClass().getPackage();
    out.println("<p><b>Implementación EL:</b> " + factory.getClass().getName() + "</p>");
    out.println("<p><b>Versión EL:</b> " + pkg.getImplementationVersion() + "</p>");
%>

<h3>Prueba de operador ternario:</h3>
<p>Resultado esperado = <b>Verdadero</b></p>
<p>Resultado EL = 
    ${1 == 1 ? 'Verdadero' : 'Falso'}
</p>
</body>
</html>
