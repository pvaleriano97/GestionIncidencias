<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- VALIDACIÓN DE SESIÓN Y ROL -->
<c:if test="${empty sessionScope.role || sessionScope.role.toLowerCase() ne 'admin'}">
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

    <%@ include file="menu.jsp" %>

    <div class="main-content">

        <%@ include file="header.jsp" %>

        <div class="content-header">
            <h1>Dashboard Administrador</h1>
        </div>

        <!-- GRID GENERAL: cards arriba, abajo dos columnas (charts + sidebar) -->
        <div class="dashboard-grid">

            <!-- CARDS -->
            <div class="cards-row">
                <div class="card small">
                    <h3>Total Incidencias</h3>
                    <div class="card-value">${totalIncidencias}</div>
                </div>
                <div class="card small">
                    <h3>Abiertas</h3>
                    <div class="card-value">${abiertas}</div>
                </div>
                <div class="card small">
                    <h3>Cerradas</h3>
                    <div class="card-value">${cerradas}</div>
                </div>
                <div class="card small">
                    <h3>En Proceso</h3>
                    <div class="card-value">${enProceso}</div>
                </div>
            </div>

            <!-- COLUMNAS INFERIORES -->
            <div class="bottom-columns">

                <!-- IZQUIERDA: GRÁFICOS -->
                <div class="charts-column">
                    <div class="chart-box">
                        <h4>Distribución de Incidencias</h4>
                        <canvas id="chartEstados"></canvas>
                    </div>

                    <div class="chart-box">
                        <h4>Incidencias por Día (Última Semana)</h4>
                        <canvas id="chartSemanal"></canvas>
                    </div>
                </div>

                <!-- DERECHA: TOP TÉCNICOS + ÚLTIMAS INCIDENCIAS -->
                <aside class="right-column">
                    <div class="panel">
                        <h4>Top 5 Técnicos</h4>

                        <c:if test="${empty topTecnicos}">
                            <p class="muted">No hay datos de técnicos.</p>
                        </c:if>

                        <table class="compact-table">
                            <thead>
                                <tr><th>ID</th><th>Nombre</th><th>Cerrados</th></tr>
                            </thead>
                            <tbody>
                                <c:forEach var="tec" items="${topTecnicos}">
                                    <tr>
                                        <td>${tec.idTecnico}</td>
                                        <td>${tec.nombre}</td>
                                        <td>${tec.ticketsCerrados}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>

                    <div class="panel">
                        <h4>Últimas Incidencias</h4>

                        <c:if test="${empty ultimasIncidencias}">
                            <p class="muted">No hay incidencias registradas.</p>
                        </c:if>

                        <table class="compact-table">
                            <thead><tr><th>ID</th><th>Desc</th><th>Estado</th></tr></thead>
                            <tbody>
                                <c:forEach var="inc" items="${ultimasIncidencias}">
                                    <tr>
                                        <td>${inc.idIncidencia}</td>
                                        <td><c:out value="${fn:length(inc.descripcion) > 30 ? fn:substring(inc.descripcion,0,30) : inc.descripcion}" /></td>
                                        <td>${inc.estado}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </aside>

            </div> <!-- bottom-columns -->

        </div> <!-- dashboard-grid -->

    </div> <!-- main-content -->
</div> <!-- dashboard-container -->

<!-- ================== SCRIPTS ================== -->
<script>
    // Valores seguros para JS (si vienen vacíos, asigna 0 o [])
    var abiertas = ${empty abiertas ? 0 : abiertas};
    var enProceso = ${empty enProceso ? 0 : enProceso};
    var cerradas = ${empty cerradas ? 0 : cerradas};

    // arrays para chart (si vienen vacíos, crear array vacío)
    var diasSemana = [
        <c:forEach var="d" items="${diasSemana}" varStatus="s">'${d}'${!s.last ? ',' : ''}</c:forEach>
    ];
    var incidenciasPorDia = [
        <c:forEach var="c" items="${incidenciasPorDia}" varStatus="s">${c}${!s.last ? ',' : ''}</c:forEach>
    ];

    // Chart 1 - Doughnut
    const ctx1 = document.getElementById('chartEstados').getContext('2d');
    new Chart(ctx1, {
        type: 'doughnut',
        data: {
            labels: ['Abiertas', 'En Proceso', 'Cerradas'],
            datasets: [{
                data: [abiertas, enProceso, cerradas],
                backgroundColor: ['#f39c12', '#3498db', '#2ecc71']
            }]
        },
        options: {
            maintainAspectRatio: false,
            plugins: { legend: { position: 'bottom', labels: { boxWidth:10 } } }
        }
    });

    // Chart 2 - Line
    const ctx2 = document.getElementById('chartSemanal').getContext('2d');
    new Chart(ctx2, {
        type: 'line',
        data: {
            labels: diasSemana,
            datasets: [{
                label: 'Incidencias',
                data: incidenciasPorDia,
                borderColor: '#3498db',
                backgroundColor: 'rgba(52,152,219,0.12)',
                fill: true,
                tension: 0.25,
                pointRadius: 3
            }]
        },
        options: {
            maintainAspectRatio: false,
            scales: { y: { beginAtZero: true } },
            plugins: { legend: { display: false } }
        }
    });
</script>

</body>
</html>
