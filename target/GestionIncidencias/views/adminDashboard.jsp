<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

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

    <style>
        /* ======= DASHBOARD ======= */
        .dashboard-container {
            display: flex;
            flex-direction: column;
            min-height: 100vh;
        }

        .main-content {
            flex: 1;
            padding: 20px;
        }

        .content-header h1 {
            margin-bottom: 20px;
            font-size: 1.8rem;
            color: #333;
        }

        /* CARDS */
        .cards-row {
            display: flex;
            flex-wrap: wrap;
            gap: 15px;
            margin-bottom: 20px;
        }

        .card {
            background: #fff;
            padding: 15px;
            border-radius: 8px;
            box-shadow: 0 3px 8px rgba(0,0,0,0.1);
            flex: 1 1 calc(25% - 15px);
            min-width: 150px;
            text-align: center;
        }

        .card h3 {
            font-size: 1rem;
            margin-bottom: 10px;
            color: #555;
        }

        .card-value {
            font-size: 1.5rem;
            font-weight: bold;
            color: #333;
        }

        /* BOTTOM COLUMNS */
        .bottom-columns {
            display: grid;
            grid-template-columns: 2fr 1fr;
            gap: 20px;
        }

        .charts-column {
            display: grid;
            gap: 15px;
        }

        .chart-box {
            background: #fff;
            padding: 10px;
            border-radius: 8px;
            box-shadow: 0 3px 8px rgba(0,0,0,0.1);
            height: 200px; /* altura más pequeña */
        }

        .chart-box h4 {
            font-size: 1rem; /* título más pequeño */
            margin-bottom: 10px;
        }

        .right-column .panel {
            background: #fff;
            padding: 15px;
            border-radius: 8px;
            box-shadow: 0 3px 8px rgba(0,0,0,0.1);
            margin-bottom: 20px;
        }

        .right-column table {
            width: 100%;
            border-collapse: collapse;
            font-size: 0.9rem;
        }

        .right-column th, .right-column td {
            padding: 6px 8px;
            text-align: left;
        }

        .right-column th {
            background: #f2f2f2;
            font-weight: 600;
        }

        .muted {
            color: #888;
            font-size: 0.9rem;
        }

        /* RESPONSIVE */
        @media (max-width: 1024px) {
            .bottom-columns {
                grid-template-columns: 1fr;
            }

            .cards-row {
                flex-direction: column;
            }

            .card {
                flex: 1 1 100%;
            }
        }
    </style>
</head>

<body>
<div class="dashboard-container">

    <%@ include file="menu.jsp" %>

    <div class="main-content">

        <%@ include file="header.jsp" %>

        <div class="content-header">
            <h1>Dashboard Administrador</h1>
        </div>

        <!-- GRID GENERAL -->
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

                    <!-- ESTADOS -->
                    <div class="chart-box">
                        <h4>Distribución de Incidencias</h4>
                        <canvas id="chartEstados" width="250" height="200"></canvas>
                    </div>

                    <!-- SEMANA -->
                    <div class="chart-box">
                        <h4>Incidencias por Día (Última Semana)</h4>
                        <canvas id="chartSemanal" width="250" height="200"></canvas>
                    </div>

                </div>

                <!-- DERECHA -->
                <aside class="right-column">

                    <!-- TOP TECNICOS -->
                    <div class="panel">
                        <h4>Top 5 Técnicos</h4>

                        <c:choose>
                            <c:when test="${not empty topTecnicos}">
                                <table class="compact-table">
                                    <thead>
                                        <tr><th>ID</th><th>Nombre</th><th>Cerrados</th></tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="tec" items="${topTecnicos}">
                                            <tr>
                                                <td>${tec.idTecnico}</td>
                                                <td>${tec.nombre}</td>
                                                <td>${tec.totalResueltas}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </c:when>
                            <c:otherwise>
                                <p class="muted">No hay datos de técnicos.</p>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <!-- ULTIMAS INCIDENCIAS -->
                    <div class="panel">
                        <h4>Últimas Incidencias</h4>

                        <c:choose>
                            <c:when test="${not empty ultimasIncidencias}">
                                <table class="compact-table">
                                    <thead>
                                        <tr><th>ID</th><th>Usuario</th><th>Estado</th></tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="inc" items="${ultimasIncidencias}">
                                            <tr>
                                                <td>${inc.idIncidencia}</td>
                                                <td>${inc.nombreUsuario}</td> <!-- mostrar nombre -->
                                                <td>${inc.estado}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </c:when>
                            <c:otherwise>
                                <p class="muted">No hay incidencias registradas.</p>
                            </c:otherwise>
                        </c:choose>
                    </div>

                </aside>

            </div> <!-- bottom-columns -->

        </div> <!-- dashboard-grid -->

    </div> <!-- main-content -->
</div> <!-- dashboard-container -->

<!-- ================== SCRIPTS ================== -->
<script>
    // Evitar null
    var abiertas = ${empty abiertas ? 0 : abiertas};
    var enProceso = ${empty enProceso ? 0 : enProceso};
    var cerradas = ${empty cerradas ? 0 : cerradas};

    var diasSemana = [
        <c:forEach var="d" items="${diasSemana}" varStatus="s">'${d}'${!s.last ? ',' : ''}</c:forEach>
    ];
    var incidenciasPorDia = [
        <c:forEach var="c" items="${incidenciasPorDia}" varStatus="s">${c}${!s.last ? ',' : ''}</c:forEach>
    ];

    // Chart Estados
    new Chart(document.getElementById('chartEstados'), {
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
            responsive: true,
            plugins: { legend: { position: 'bottom' } }
        }
    });

    // Chart Semanal
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
                tension: 0.3,
                pointRadius: 3
            }]
        },
        options: {
            maintainAspectRatio: false,
            responsive: true,
            plugins: { legend: { display: false } },
            scales: { y: { beginAtZero: true } }
        }
    });
</script>

</body>
</html>
