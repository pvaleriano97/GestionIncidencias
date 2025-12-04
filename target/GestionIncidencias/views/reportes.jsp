<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Reportes de Incidencias</title>

    <link rel="stylesheet" href="<c:url value='/css/main.css'/>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- =====================================
         CSS LOCAL SOLO PARA ESTE JSP
         (No afecta login ni otro módulo)
    ====================================== -->
    <style>
        .report-page .report-header {
            background: #f5f6fa;
            padding: 12px 20px;
            border-radius: 8px;
            margin-bottom: 18px;
        }

        .report-page .report-title {
            font-size: 20px;
            font-weight: bold;
            color: #34495e;
        }

        .report-page .report-filter-box {
            background: white;
            border-radius: 10px;
            padding: 18px;
            margin-bottom: 18px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.08);
        }

        .report-page .filters-grid {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 18px;
        }

        .report-page .filter-item label {
            font-weight: bold;
            color: #2c3e50;
        }

        .report-page .filter-item input,
        .report-page .filter-item select {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            border-radius: 6px;
            border: 1px solid #ccc;
        }

        .report-page .filter-buttons {
            grid-column: span 4;
            display: flex;
            justify-content: flex-end;
            gap: 10px;
            margin-top: 10px;
        }

        .report-page .btn-primary {
            background: #3498db;
            color: white;
            padding: 8px 14px;
            border-radius: 6px;
            border: none;
            cursor: pointer;
        }

        .report-page .btn-excel {
            background: #27ae60;
            color: white;
            padding: 8px 14px;
            border-radius: 6px;
            text-decoration: none;
        }

        .report-page .btn-pdf {
            background: #c0392b;
            color: white;
            padding: 8px 14px;
            border-radius: 6px;
            text-decoration: none;
        }

        .report-page .ticket-table {
            width: 100%;
            border-collapse: collapse;
        }

        .report-page .ticket-table th {
            background: #34495e;
            color: white;
            padding: 10px;
        }

        .report-page .ticket-table td {
            background: white;
            padding: 8px;
            border-bottom: 1px solid #ddd;
        }
    </style>
</head>

<body>

<div class="dashboard-container report-page">

    <!-- MENÚ -->
    <jsp:include page="/views/menu.jsp"/>

    <!-- CONTENIDO -->
    <div class="main-content">

        <!-- HEADER -->
        <jsp:include page="/views/header.jsp"/>

        <!-- TITULO -->
        <div class="report-header">
            <div class="report-title">📊 Reportes de Incidencias</div>
        </div>

        <!-- ============================
             FILTROS
        ============================ -->
        <div class="report-filter-box">

            <form action="${pageContext.request.contextPath}/ReportesServlet" method="get" class="filters-grid">
                <input type="hidden" name="action" value="buscar">

                <div class="filter-item">
                    <label>Desde</label>
                    <input type="date" name="inicio" value="${param.inicio}">
                </div>

                <div class="filter-item">
                    <label>Hasta</label>
                    <input type="date" name="fin" value="${param.fin}">
                </div>

                <div class="filter-item">
                    <label>Estado</label>
                    <select name="estado">
                        <option value="">Todos</option>
                        <option value="Abierta" ${param.estado == 'Abierta' ? 'selected' : ''}>Abierta</option>
                        <option value="En Proceso" ${param.estado == 'En Proceso' ? 'selected' : ''}>En Proceso</option>
                        <option value="Cerrada" ${param.estado == 'Cerrada' ? 'selected' : ''}>Cerrada</option>
                    </select>
                </div>

                <div class="filter-item">
                    <label>Técnico</label>
                    <select name="tecnico">
                        <option value="">Todos</option>
                        <c:forEach var="t" items="${tecnicos}">
                            <option value="${t.idTecnico}" ${param.tecnico == t.idTecnico ? 'selected' : ''}>
                                ${t.nombre}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="filter-buttons">
                    <button type="submit" class="btn-primary">
                        <i class="fa fa-search"></i> Buscar
                    </button>

               <!-- Botón Excel -->
    <a class="btn-excel"
       href="${pageContext.request.contextPath}/ReportesServlet?action=excel_filtro&inicio=${param.inicio}&fin=${param.fin}&estado=${param.estado}&tecnico=${param.tecnico}">
        <i class="fa-solid fa-file-excel"></i> Excel
    </a>

    <!-- Botón PDF -->
    <a class="btn-pdf"
       href="${pageContext.request.contextPath}/ReportesServlet?action=pdf_filtro&inicio=${param.inicio}&fin=${param.fin}&estado=${param.estado}&tecnico=${param.tecnico}">
        <i class="fa-solid fa-file-pdf"></i> PDF
    </a>
                </div>

            </form>
        </div>

        <!-- TABLA -->
        <table class="ticket-table" style="margin-top:12px;">
            <thead>
            <tr>
                <th>ID</th>
                <th>Fecha</th>
                <th>Descripción</th>
                <th>Estado</th>
                <th>Usuario</th>
                <th>Equipo</th>
                <th>Tipo</th>
                <th>Técnico</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach var="h" items="${historial}">
                <tr>
                    <td>${h.idIncidencia}</td>
                    <td>${h.fechaRegistroStr}</td>
                    <td style="max-width:480px; white-space:normal;">${h.descripcion}</td>
                    <td>${h.estado}</td>
                    <td>${h.nombreUsuario}</td>
                    <td>${h.codigoEquipo}</td>
                    <td>${h.tipoEquipo}</td>
                    <td>${h.nombreTecnico}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </div>
</div>

</body>
</html>
