<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestión de Equipos</title>
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
            <h2>Gestión de Equipos</h2>

            <form class="equipo-form" action="<c:url value='/EquipoServlet'/>" method="post">
                <input type="hidden" name="idEquipo" value="${equipoEdit != null ? equipoEdit.idEquipo : ''}" />

                <div class="form-row">
                    <div class="form-group">
                        <label>Código del Equipo</label>
                        <input type="text" name="codigoEquipo" required value="${equipoEdit != null ? equipoEdit.codigoEquipo : ''}">
                    </div>
                    <div class="form-group">
                        <label>Tipo</label>
                        <input type="text" name="tipo" required value="${equipoEdit != null ? equipoEdit.tipo : ''}">
                    </div>
                </div>

                <div class="form-group">
                    <label>Estado</label>
                    <select name="estado" required>
                        <option value="">--Seleccione--</option>
                        <option value="Activo" ${equipoEdit != null && equipoEdit.estado == 'Activo' ? 'selected' : ''}>Activo</option>
                        <option value="En Reparación" ${equipoEdit != null && equipoEdit.estado == 'En Reparación' ? 'selected' : ''}>En Reparación</option>
                        <option value="Inactivo" ${equipoEdit != null && equipoEdit.estado == 'Inactivo' ? 'selected' : ''}>Inactivo</option>
                    </select>
                </div>

                <div class="form-buttons">
                    <button type="submit" class="btn-primary">Guardar</button>
                    <button type="reset" class="btn-secondary">Limpiar</button>
                </div>
            </form>
        </div>

        <!-- Buscar equipos -->
        <div class="search-container">
            <input type="text" id="searchInput" class="search-input" placeholder="Buscar por código, tipo o estado..." value="${search}">
        </div>

        <!-- Tabla de equipos -->
        <table class="ticket-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Código</th>
                    <th>Tipo</th>
                    <th>Estado</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="equipo" items="${listaEquipos}">
                    <tr>
                        <td>${equipo.idEquipo}</td>
                        <td>${equipo.codigoEquipo}</td>
                        <td>${equipo.tipo}</td>
                        <td>${equipo.estado}</td>
                        <td>
                            <a href="<c:url value='/EquipoServlet?action=edit&id=${equipo.idEquipo}'/>">
                                <i class="fa-solid fa-pen-to-square icon-btn"></i>
                            </a>
                            <a href="<c:url value='/EquipoServlet?action=delete&id=${equipo.idEquipo}'/>"
                               onclick="return confirm('¿Desea eliminar este equipo?')">
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
                <a href="<c:url value='/EquipoServlet?pagina=${i}&search=${search}'/>" 
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
