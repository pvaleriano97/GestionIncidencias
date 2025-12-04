<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Dashboard - Sistema de Tickets</title>

    <!-- CSS general -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">

    <!-- Íconos -->
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>

<body class="dashboard-body">

<div class="dashboard-container">

    <!-- Sidebar -->
    <jsp:include page="menu.jsp"/>

    <!-- CONTENIDO PRINCIPAL -->
    <div class="main-content">

        <!-- HEADER -->
        <jsp:include page="header.jsp"/>

        <div class="page-container">

            <!-- ========================== -->
            <!-- TARJETAS DEL DASHBOARD     -->
            <!-- ========================== -->
            <div class="dashboard-cards">

                <div class="card card-stats">
                    <div class="card-icon">
                        <i class="fa-solid fa-ticket"></i>
                    </div>
                    <div class="card-info">
                        <h3>Total Incidencias</h3>
                        <p>${totalIncidencias}</p>
                    </div>
                </div>

                <div class="card card-stats">
                    <div class="card-icon">
                        <i class="fa-solid fa-folder-open"></i>
                    </div>
                    <div class="card-info">
                        <h3>Abiertas</h3>
                        <p>${abiertas}</p>
                    </div>
                </div>

                <div class="card card-stats">
                    <div class="card-icon">
                        <i class="fa-solid fa-check-circle"></i>
                    </div>
                    <div class="card-info">
                        <h3>Cerradas</h3>
                        <p>${cerradas}</p>
                    </div>
                </div>

                <div class="card card-stats">
                    <div class="card-icon">
                        <i class="fa-solid fa-spinner"></i>
                    </div>
                    <div class="card-info">
                        <h3>En Proceso</h3>
                        <p>${enProceso}</p>
                    </div>
                </div>
            </div>

            <!-- ========================== -->
            <!-- TOP 5 TÉCNICOS              -->
            <!-- ========================== -->
            <div class="section-title">
                <i class="fa-solid fa-user-gear"></i> Top 5 Técnicos
            </div>

            <div class="table-container">
                <table class="ticket-table modern-table">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Especialidad</th>
                        <th>Cerrados</th>
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
            </div>

            <!-- ========================== -->
            <!-- ÚLTIMAS INCIDENCIAS         -->
            <!-- ========================== -->
            <div class="section-title">
                <i class="fa-solid fa-clock-rotate-left"></i> Últimas Incidencias
            </div>

            <div class="table-container">
                <table class="ticket-table modern-table">
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
            </div>

            <!-- ========================== -->
            <!-- INCIDENCIAS EN PROCESO      -->
            <!-- ========================== -->
            <div class="section-title">
                <i class="fa-solid fa-spinner"></i> Incidencias en Proceso
            </div>

            <div class="table-container">
                <table class="ticket-table modern-table">
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
</div>

</body>
</html>
