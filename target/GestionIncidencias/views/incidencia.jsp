<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<c:url value='/css/main.css'/>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>
<div class="dashboard-container">

    <!-- Sidebar -->
    <jsp:include page="/views/menu.jsp" />

    <!-- Main content -->
    <div class="main-content">
        <jsp:include page="/views/header.jsp" />

        <!-- Contenedor del formulario -->
        <div class="form-container">
                  <form class="incidencia-form" action="<c:url value='/IncidenciaServlet'/>" method="post">
                <input type="hidden" name="idIncidencia" value="${incidenciaEdit != null ? incidenciaEdit.idIncidencia : ''}" />

                <div class="form-group">
                    <label>Descripción</label>
                    <textarea name="descripcion" required>${incidenciaEdit != null ? incidenciaEdit.descripcion : ''}</textarea>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label>Estado</label>
                        <select name="estado" required>
                            <option value="Abierta" ${incidenciaEdit != null && incidenciaEdit.estado == 'Abierta' ? 'selected' : ''}>Abierta</option>
                            <option value="En Proceso" ${incidenciaEdit != null && incidenciaEdit.estado == 'En Proceso' ? 'selected' : ''}>En Proceso</option>
                            <option value="Cerrada" ${incidenciaEdit != null && incidenciaEdit.estado == 'Cerrada' ? 'selected' : ''}>Cerrada</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Usuario</label>
                        <select name="idUsuario" required>
                            <option value="">--Seleccione--</option>
                            <c:forEach var="u" items="${listaUsuarios}">
                                <option value="${u.idUsuario}" ${incidenciaEdit != null && incidenciaEdit.idUsuario == u.idUsuario ? 'selected' : ''}>${u.nombre}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label>Equipo</label>
                        <select name="idEquipo" required>
                            <option value="">--Seleccione--</option>
                            <c:forEach var="e" items="${listaEquipos}">
                                <option value="${e.idEquipo}" ${incidenciaEdit != null && incidenciaEdit.idEquipo == e.idEquipo ? 'selected' : ''}>${e.codigoEquipo} (${e.tipo})</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Técnico</label>
                        <select name="idTecnico">
                            <option value="">--Seleccione--</option>
                            <c:forEach var="t" items="${listaTecnicos}">
                                <option value="${t.idTecnico}" ${incidenciaEdit != null && incidenciaEdit.idTecnico == t.idTecnico ? 'selected' : ''}>${t.nombre}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="form-buttons">
                    <button type="submit" class="btn-primary">Guardar</button>
                    <button type="reset" class="btn-secondary">Limpiar</button>
                </div>
            </form>
        </div>

        <!-- Buscar incidencias -->
        <div class="search-container">
            <input type="text" id="searchInput" class="search-input" placeholder="Buscar..." value="${search}">
        </div>

        <!-- Tabla de incidencias -->
        <table class="ticket-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Descripción</th>
                    <th>Estado</th>
                    <th>Usuario</th>
                    <th>Equipo</th>
                    <th>Técnico</th>
                    <th>Fecha</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="incidencia" items="${listaIncidencias}">
                    <tr>
                        <td>${incidencia.idIncidencia}</td>
                        <td>${incidencia.descripcion}</td>
                        <td>${incidencia.estado}</td>
                        <td>${incidencia.nombreUsuario}</td>
                        <td>${incidencia.codigoEquipo} (${incidencia.tipoEquipo})</td>
                        <td>${incidencia.nombreTecnico}</td>
                        <td>${incidencia.fechaRegistroStr}</td>
                        <td>
                            <a href="<c:url value='/IncidenciaServlet?action=edit&id=${incidencia.idIncidencia}'/>">
                                <i class="fa-solid fa-pen-to-square icon-btn"></i>
                            </a>
                            <a href="<c:url value='/IncidenciaServlet?action=delete&id=${incidencia.idIncidencia}'/>"
                               onclick="return confirm('¿Desea eliminar esta incidencia?')">
                                <i class="fa-solid fa-trash icon-btn delete"></i>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- Paginación centrada -->
        <div class="pagination">
            <c:forEach var="i" begin="1" end="${totalPaginas}">
                <a href="<c:url value='/IncidenciaServlet?pagina=${i}&search=${search}'/>" 
                   class="${i == paginaActual ? 'active' : ''}">${i}</a>
            </c:forEach>
        </div>
    </div>
</div>

<script>
    const searchInput = document.getElementById('searchInput');
    searchInput.addEventListener('keyup', function() {
        const filter = searchInput.value.toLowerCase();
        const rows = document.querySelectorAll('table tbody tr');
        rows.forEach(row => {
            row.style.display = row.innerText.toLowerCase().includes(filter) ? '' : 'none';
        });
    });
</script>
</body>
</html>
