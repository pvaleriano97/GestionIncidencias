<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- VALIDACIÓN DE SESIÓN Y ROL -->
<c:if test="${sessionScope.role ne 'admin' && sessionScope.role ne 'ADMIN'}">
    <c:redirect url="/LoginServlet"/>
</c:if>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Dashboard Administrador</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>

<body>

<div class="dashboard-container">

    <!-- MENU -->
    <%@ include file="menu.jsp" %>

    <!-- AREA PRINCIPAL -->
    <div class="main-content">

        <!-- HEADER -->
        <%@ include file="header.jsp" %>

        <div class="content-header">
            <h1>Dashboard Administrador</h1>
                   </div>

        <!-- ========== CARDS ========== -->
        <div class="dashboard-cards">
            <div class="card">
                <h3>Total Incidencias</h3>
                <h1>${totalIncidencias}</h1>
            </div>

            <div class="card">
                <h3>Abiertas</h3>
                <h1>${abiertas}</h1>
            </div>

            <div class="card">
                <h3>Cerradas</h3>
                <h1>${cerradas}</h1>
            </div>

            <div class="card">
                <h3>En Proceso</h3>
                <h1>${enProceso}</h1>
            </div>
        </div>

        <!-- ========== GRÁFICOS ========== -->
        <div class="charts-container">
            <div class="chart-box">
                <h2>Distribución de Incidencias</h2>
                <canvas id="chartEstados"></canvas>
            </div>

            <div class="chart-box">
                <h2>Incidencias por Día (Última Semana)</h2>
                <canvas id="chartSemanal"></canvas>
            </div>
        </div>

        <!-- ========== TOP TÉCNICOS ========== -->
        <h2 class="section-title">Top 5 Técnicos</h2>
        <table class="ticket-table">
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

        <!-- ========== ÚLTIMAS INCIDENCIAS ========== -->
        <h2 class="section-title">Últimas Incidencias</h2>

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
                <c:if test="${empty ultimasIncidencias}">
                    <tr>
                        <td colspan="7" style="text-align:center;">No hay incidencias registradas</td>
                    </tr>
                </c:if>

                <c:forEach var="inc" items="${ultimasIncidencias}">
                    <tr>
                        <td>${inc.idIncidencia}</td>
                        <td>${inc.descripcion}</td>
                        <td>${inc.estado}</td>
                        <td>${inc.nombreUsuario}</td>
                        <td>
                            <c:choose>
                                <c:when test="${empty inc.nombreTecnico}">
                                    Sin asignar
                                </c:when>
                                <c:otherwise>
                                    ${inc.nombreTecnico}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${empty inc.codigoEquipo}">
                                    Sin equipo
                                </c:when>
                                <c:otherwise>
                                    ${inc.codigoEquipo} (${inc.tipoEquipo})
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <fmt:formatDate value="${inc.fechaRegistro}" pattern="dd/MM/yyyy HH:mm"/>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </div>
</div>

<!-- ================== SCRIPTS ================== -->
<script>
    new Chart(document.getElementById('chartEstados'), {
        type: 'doughnut',
        data: {
            labels: ['Abiertas', 'En Proceso', 'Cerradas'],
            datasets: [{
                data: [${abiertas}, ${enProceso}, ${cerradas}],
                backgroundColor: ['#f39c12', '#3498db', '#2ecc71']
            }]
        }
    });

    const diasSemana = [<c:forEach var="d" items="${diasSemana}" varStatus="s">'${d}'${!s.last ? ',' : ''}</c:forEach>];
    const incidenciasPorDia = [<c:forEach var="c" items="${incidenciasPorDia}" varStatus="s">${c}${!s.last ? ',' : ''}</c:forEach>];

    new Chart(document.getElementById('chartSemanal'), {
        type: 'line',
        data: {
            labels: diasSemana,
            datasets: [{
                label: 'Incidencias',
                data: incidenciasPorDia,
                borderColor: '#3498db',
                backgroundColor: 'rgba(52,152,219,0.2)',
                fill: true,
                tension: 0.3
            }]
        }
    });
</script>

</body>
</html>
