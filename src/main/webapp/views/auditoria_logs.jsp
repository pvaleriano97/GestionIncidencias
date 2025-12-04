<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Auditoría de Logins</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.11.5/css/dataTables.bootstrap5.min.css">
    <style>
        body {
            background: #f5f7fa;
            padding: 20px;
        }
        .audit-card {
            background: white;
            border-radius: 10px;
            padding: 25px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
            margin-bottom: 20px;
        }
        .status-badge {
            padding: 5px 10px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 600;
        }
        .status-exitoso { background: #d4edda; color: #155724; }
        .status-fallido { background: #f8d7da; color: #721c24; }
        .status-pendiente { background: #fff3cd; color: #856404; }
        .filter-box {
            background: #e9ecef;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <div class="container-fluid">
        <div class="audit-card">
            <h2 class="mb-4">🔍 Auditoría de Inicios de Sesión</h2>
            
            <div class="filter-box">
                <div class="row">
                    <div class="col-md-4">
                        <label class="form-label">Filtrar por estado:</label>
                        <select id="filterStatus" class="form-select">
                            <option value="">Todos</option>
                            <option value="EXITOSO">Exitosos</option>
                            <option value="FALLIDO">Fallidos</option>
                            <option value="2FA">2FA</option>
                        </select>
                    </div>
                    <div class="col-md-4">
                        <label class="form-label">Buscar usuario:</label>
                        <input type="text" id="filterUser" class="form-control" placeholder="Correo o nombre">
                    </div>
                    <div class="col-md-4">
                        <label class="form-label">Acciones:</label>
                        <div>
                            <a href="AuditoriaServlet?action=estadisticas" class="btn btn-info btn-sm">
                                📊 Ver Estadísticas
                            </a>
                            <a href="AdminDashboardServlet" class="btn btn-secondary btn-sm">
                                ← Volver
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            
            <div class="table-responsive">
                <table id="logsTable" class="table table-hover">
                    <thead class="table-dark">
                        <tr>
                            <th>Fecha</th>
                            <th>Usuario</th>
                            <th>Correo</th>
                            <th>Rol</th>
                            <th>IP</th>
                            <th>Navegador</th>
                            <th>Sistema</th>
                            <th>Estado</th>
                            <th>Código 2FA</th>
                            <th>Session ID</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="log" items="${logs}">
                            <tr>
                                <td>${log.fechaLogin}</td>
                                <td>${log.nombreUsuario}</td>
                                <td>${log.correoUsuario}</td>
                                <td><span class="badge bg-primary">${log.rolUsuario}</span></td>
                                <td><code>${log.ipAddress}</code></td>
                                <td>${log.navegador}</td>
                                <td>${log.sistemaOperativo}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${log.statusLogin.contains('EXITOSO')}">
                                            <span class="status-badge status-exitoso">✅ ${log.statusLogin}</span>
                                        </c:when>
                                        <c:when test="${log.statusLogin.contains('FALLIDO')}">
                                            <span class="status-badge status-fallido">❌ ${log.statusLogin}</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="status-badge status-pendiente">⏳ ${log.statusLogin}</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:if test="${not empty log.codigo2fa}">
                                        <span class="badge bg-warning">${log.codigo2fa}</span>
                                    </c:if>
                                </td>
                                <td><small>${log.sessionId.substring(0, 10)}...</small></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            
            <div class="mt-4">
                <div class="alert alert-info">
                    <strong>📈 Total de registros:</strong> ${logs.size()} eventos de login
                </div>
            </div>
        </div>
    </div>
    
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.11.5/js/dataTables.bootstrap5.min.js"></script>
    <script>
        $(document).ready(function() {
            var table = $('#logsTable').DataTable({
                "order": [[0, "desc"]],
                "language": {
                    "url": "//cdn.datatables.net/plug-ins/1.11.5/i18n/es-ES.json"
                },
                "pageLength": 25
            });
            
            // Filtro por estado
            $('#filterStatus').on('change', function() {
                var status = $(this).val();
                if (status === '2FA') {
                    table.column(8).search('\\d{6}', true, false).draw();
                } else if (status) {
                    table.column(7).search(status).draw();
                } else {
                    table.column(7).search('').draw();
                    table.column(8).search('').draw();
                }
            });
            
            // Filtro por usuario
            $('#filterUser').on('keyup', function() {
                table.search(this.value).draw();
            });
        });
    </script>
</body>
</html>