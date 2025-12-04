<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Estadísticas de Login</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        body { background: #f5f7fa; padding: 20px; }
        .stat-card {
            background: white;
            border-radius: 10px;
            padding: 20px;
            margin-bottom: 20px;
            box-shadow: 0 3px 10px rgba(0,0,0,0.1);
        }
        .stat-number {
            font-size: 2.5rem;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2>📊 Estadísticas de Inicios de Sesión</h2>
            <a href="AuditoriaServlet" class="btn btn-secondary">← Volver a Logs</a>
        </div>
        
        <div class="row mb-4">
            <div class="col-md-3">
                <div class="stat-card text-center">
                    <div class="text-primary stat-number">
                        <c:set var="totalSum" value="0" />
                        <c:forEach var="stat" items="${estadisticas}">
                            <c:set var="totalSum" value="${totalSum + stat[1]}" />
                        </c:forEach>
                        ${totalSum}
                    </div>
                    <div>Total Logins</div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="stat-card text-center">
                    <div class="text-success stat-number">
                        <c:set var="exitososSum" value="0" />
                        <c:forEach var="stat" items="${estadisticas}">
                            <c:set var="exitososSum" value="${exitososSum + stat[2]}" />
                        </c:forEach>
                        ${exitososSum}
                    </div>
                    <div>Exitosos</div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="stat-card text-center">
                    <div class="text-danger stat-number">
                        <c:set var="fallidosSum" value="0" />
                        <c:forEach var="stat" items="${estadisticas}">
                            <c:set var="fallidosSum" value="${fallidosSum + stat[3]}" />
                        </c:forEach>
                        ${fallidosSum}
                    </div>
                    <div>Fallidos</div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="stat-card text-center">
                    <div class="text-warning stat-number">
                        ${estadisticas.size()}
                    </div>
                    <div>Días registrados</div>
                </div>
            </div>
        </div>
        
        <div class="row">
            <div class="col-md-8">
                <div class="stat-card">
                    <h5>📈 Logins por Día (últimos 30 días)</h5>
                    <canvas id="loginChart"></canvas>
                </div>
            </div>
            <div class="col-md-4">
                <div class="stat-card">
                    <h5>📋 Detalle por Fecha</h5>
                    <div class="table-responsive">
                        <table class="table table-sm">
                            <thead>
                                <tr>
                                    <th>Fecha</th>
                                    <th>Total</th>
                                    <th>✓</th>
                                    <th>✗</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="stat" items="${estadisticas}">
                                <tr>
                                    <td>${stat[0]}</td>
                                    <td><strong>${stat[1]}</strong></td>
                                    <td class="text-success">${stat[2]}</td>
                                    <td class="text-danger">${stat[3]}</td>
                                </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script>
        // Datos para el gráfico
        const fechas = [
            <c:forEach var="stat" items="${estadisticas}" varStatus="loop">
                '${stat[0]}'${!loop.last ? ',' : ''}
            </c:forEach>
        ];
        
        const totales = [
            <c:forEach var="stat" items="${estadisticas}" varStatus="loop">
                ${stat[1]}${!loop.last ? ',' : ''}
            </c:forEach>
        ];
        
        const exitosos = [
            <c:forEach var="stat" items="${estadisticas}" varStatus="loop">
                ${stat[2]}${!loop.last ? ',' : ''}
            </c:forEach>
        ];
        
        const fallidos = [
            <c:forEach var="stat" items="${estadisticas}" varStatus="loop">
                ${stat[3]}${!loop.last ? ',' : ''}
            </c:forEach>
        ];
        
        // Crear gráfico
        const ctx = document.getElementById('loginChart').getContext('2d');
        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: fechas,
                datasets: [
                    {
                        label: 'Total Logins',
                        data: totales,
                        backgroundColor: 'rgba(54, 162, 235, 0.5)',
                        borderColor: 'rgb(54, 162, 235)',
                        borderWidth: 1
                    },
                    {
                        label: 'Exitosos',
                        data: exitosos,
                        backgroundColor: 'rgba(75, 192, 192, 0.5)',
                        borderColor: 'rgb(75, 192, 192)',
                        borderWidth: 1
                    },
                    {
                        label: 'Fallidos',
                        data: fallidos,
                        backgroundColor: 'rgba(255, 99, 132, 0.5)',
                        borderColor: 'rgb(255, 99, 132)',
                        borderWidth: 1
                    }
                ]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        position: 'top',
                    },
                    title: {
                        display: true,
                        text: 'Actividad de Login'
                    }
                }
            }
        });
    </script>
</body>
</html>