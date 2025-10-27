<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Dashboard - Sistema de Tickets</title>
    <!-- CSS principal unificado -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <!-- FontAwesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body class="dashboard-body">

<div class="dashboard-container">

    <!-- Sidebar -->
    <jsp:include page="menu.jsp" />

    <!-- Main content -->
    <div class="main-content">
        <jsp:include page="header.jsp" />

        <div class="page-container">
            <h1>Dashboard de Incidencias</h1>

            <!-- Tarjetas de resumen -->
            <div class="dashboard-cards">
                <div class="card">
                    <h3>Total Incidencias</h3>
                    <p>${totalIncidencias}</p>
                </div>
                <div class="card">
                    <h3>Incidencias Abiertas</h3>
                    <p>${abiertas}</p>
                </div>
                <div class="card">
                    <h3>Incidencias Cerradas</h3>
                    <p>${cerradas}</p>
                </div>
            </div>

            <!-- Últimas incidencias -->
            <h2>Últimas Incidencias</h2>
            <table class="ticket-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Descripción</th>
                        <th>Estado</th>
                        <th>Usuario</th>
                        <th>Fecha</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="incidencia" items="${ultimasIncidencias}">
                        <tr>
                            <td>${incidencia.idIncidencia}</td>
                            <td>${incidencia.descripcion}</td>
                            <td>${incidencia.estado}</td>
                            <td>${incidencia.nombreUsuario}</td>
                            <td>${incidencia.fechaRegistroStr}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

</body>
</html>
