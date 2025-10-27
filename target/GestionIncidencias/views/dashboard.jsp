<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
                <div class="card">
                    <h3>Incidencias en Proceso</h3>
                    <p>${enProceso}</p>
                </div>
            </div>

            <!-- Top 5 técnicos con más incidencias cerradas -->
            <h2>Top 5 Técnicos</h2>
            <table class="ticket-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Especialidad</th>
                        <th>Tickets Cerrados</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="tec" items="${topTecnicos}">
                        <tr>
                            <td>${tec.idTecnico}</td>
                            <td>${tec.nombre}</td>
                            <td>${tec.especialidad}</td>
                            <td>${tec.ticketsCerrados}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <!-- Últimas 5 incidencias -->
            <h2>Últimas Incidencias</h2>
            <table class="ticket-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Descripción</th>
                        <th>Estado</th>
                        <th>Usuario</th>
                        <th>Técnico</th>
                        <th>Equipo</th>
                        <th>Fecha</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="inc" items="${ultimasIncidencias}">
                        <tr>
                            <td>${inc.idIncidencia}</td>
                            <td>${inc.descripcion}</td>
                            <td>${inc.estado}</td>
                            <td>${inc.nombreUsuario}</td>
                            <td>${inc.nombreTecnico}</td>
                            <td>${inc.codigoEquipo} (${inc.tipoEquipo})</td>
                            <td><fmt:formatDate value="${inc.fechaRegistro}" pattern="dd/MM/yyyy HH:mm"/></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <!-- Incidencias en proceso -->
            <h2>Incidencias en Proceso</h2>
            <table class="ticket-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Descripción</th>
                        <th>Estado</th>
                        <th>Usuario</th>
                        <th>Técnico</th>
                        <th>Equipo</th>
                        <th>Fecha</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="inc" items="${incidenciasEnProceso}">
                        <tr>
                            <td>${inc.idIncidencia}</td>
                            <td>${inc.descripcion}</td>
                            <td>${inc.estado}</td>
                            <td>${inc.nombreUsuario}</td>
                            <td>${inc.nombreTecnico}</td>
                            <td>${inc.codigoEquipo} (${inc.tipoEquipo})</td>
                            <td><fmt:formatDate value="${inc.fechaRegistro}" pattern="dd/MM/yyyy HH:mm"/></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

        </div>
    </div>
</div>

</body>
</html>
