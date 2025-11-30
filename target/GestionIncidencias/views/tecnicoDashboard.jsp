<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%
    String rol = (String) session.getAttribute("role");
    if (rol == null || !rol.equals("tecnico")) {
        response.sendRedirect(request.getContextPath() + "/LoginServlet");
        return;
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Dashboard Técnico</title>

    <!-- CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">

    <!-- ICONOS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

    <!-- CHART JS -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
    #chartTecnico {
        max-width: 400px;   /* ancho promedio más pequeño */
        max-height: 250px;  /* reduce la altura */
        margin: 0 auto;     /* centrar */
    }
</style>
</head>

<body>

<div class="dashboard-container">

    <%@ include file="menu.jsp" %>

    <div class="main-content">

        <%@ include file="header.jsp" %>

        <div class="content-header">
            <h1>Dashboard Técnico</h1>
            <p>Hola <strong>${sessionScope.name}</strong>, aquí tienes tu resumen de tareas.</p>
        </div>

        <!-- ===== CARDS ===== -->
        <div class="dashboard-cards">

            <div class="card">
                <h3>Total Asignadas</h3>
                <h1>${totalAsignadas}</h1>
            </div>

            <div class="card">
                <h3>En Proceso</h3>
                <h1>${enProceso}</h1>
            </div>

            <div class="card">
                <h3>Abiertas</h3>
                <h1>${abiertas}</h1>
            </div>

            <div class="card">
                <h3>Cerradas</h3>
                <h1>${cerradas}</h1>
            </div>

        </div>

        <!-- ===== GRÁFICO ===== -->
        <div class="charts-container">
            <div class="chart-box">
                <h2>Progreso de Incidencias</h2>
                <canvas id="chartTecnico" ></canvas>
            </div>
        </div>

        <!-- ===== LISTA DE INCIDENCIAS ASIGNADAS ===== -->
        <h2 class="section-title">Incidencias Asignadas</h2>

        <table class="ticket-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Descripción</th>
                    <th>Estado</th>
                    <th>Equipo</th>
                    <th>Usuario</th>
                    <th>Fecha Registro</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="inc" items="${incidenciasTecnico}">
                    <tr>
                        <td>${inc.idIncidencia}</td>
                        <td>${inc.descripcion}</td>
                        <td>${inc.estado}</td>
                        <td>${inc.codigoEquipo} (${inc.tipoEquipo})</td>
                        <td>${inc.nombreUsuario}</td>
                        <td><fmt:formatDate value="${inc.fechaRegistro}" pattern="dd/MM/yyyy HH:mm"/></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </div>

</div>

<!-- ====== SCRIPT DE GRÁFICO ====== -->
<script>
    const chartTecnico = new Chart(document.getElementById('chartTecnico'), {
        type: 'bar',
        data: {
            labels: ['Abiertas', 'En Proceso', 'Cerradas'],
            datasets: [{
                label: 'Incidencias',
                data: [
                    ${abiertas},
                    ${enProceso},
                    ${cerradas}
                ]
            }]
        },
        options: {
            responsive: true,
            plugins: { legend: { display: false } }
        }
    });
</script>

</body>
</html>
