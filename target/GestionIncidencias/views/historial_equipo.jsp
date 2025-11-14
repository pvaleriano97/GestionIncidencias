<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Historial de Equipos</title>

    <link rel="stylesheet" href="<c:url value='/css/main.css'/>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>

<body>
<div class="dashboard-container">

    <!-- Sidebar -->
    <jsp:include page="/views/menu.jsp" />

    <!-- Main Content -->
    <div class="main-content">

        <!-- Encabezado -->
        <jsp:include page="/views/header.jsp" />

        <!-- ========== FORMULARIO ========== -->
        <div class="form-container">
            <h3>Gestión del Historial de Equipos</h3>

            <form class="equipo-form" action="<c:url value='/HistorialEquipoServlet'/>" method="post">

                <!-- ID oculto si se edita -->
                <c:if test="${not empty historial}">
                    <input type="hidden" name="idHistorial" value="${historial.idHistorial}">
                </c:if>

                <!-- Guardamos el ID del equipo seleccionado -->
                <c:set var="idEquipoSel" value="${not empty historial ? historial.idEquipo : 0}" />

                <div class="form-row">

                    <!-- Combo de Equipos -->
                    <div class="form-group">
                        <label>Equipo</label>
                        <select name="idEquipo" required>
                            <option value="">--Seleccione--</option>

                            <c:forEach var="ca" items="${listaEquipos}">
                                <option value="${ca.idEquipo}"
                                    <c:if test="${idEquipoSel == ca.idEquipo}">selected</c:if>>
                                    ${ca.codigoEquipo} - ${ca.tipo}
                                </option>
                            </c:forEach>

                        </select>
                    </div>

                    <!-- Detalle -->
                    <div class="form-group">
                        <label>Detalle</label>
                        <input type="text" name="detalle" maxlength="150" required
                               value="${not empty historial ? historial.detalle : ''}">
                    </div>

                </div>

                <div class="form-buttons">
                    <button type="submit" class="btn-primary">Guardar</button>
                    <a href="<c:url value='/HistorialEquipoServlet'/>" class="btn-secondary">Limpiar</a>
                </div>

            </form>
        </div>

        <!-- ========== BUSCADOR ========== -->
        <div class="search-container">
            <input type="text" id="searchInput" class="search-input"
                   placeholder="Buscar por equipo o detalle..." value="${search}">
        </div>

        <!-- ========== TABLA ========== -->
        <table class="ticket-table">
            <thead>
            <tr>
                <th>ID</th>
                <th>Código Equipo</th>
                <th>Tipo</th>
                <th>Detalle</th>
                <th>Acciones</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach var="h" items="${listaHistorial}">
                <tr>
                    <td>${h.idHistorial}</td>
                    <td>${h.codigoEquipo}</td>
                    <td>${h.tipoEquipo}</td>
                    <td>${h.detalle}</td>

                    <td>
                        <a href="<c:url value='/HistorialEquipoServlet?action=editar&id=${h.idHistorial}'/>">
                            <i class="fa-solid fa-pen-to-square icon-btn"></i>
                        </a>

                        <a href="<c:url value='/HistorialEquipoServlet?action=eliminar&id=${h.idHistorial}'/>"
                           onclick="return confirm('¿Desea eliminar este registro?')">
                            <i class="fa-solid fa-trash icon-btn delete"></i>
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <!-- ========== PAGINACIÓN ========== -->
        <div class="pagination">
            <c:forEach var="i" begin="1" end="${totalPaginas}">
                <a href="<c:url value='/HistorialEquipoServlet?pagina=${i}&search=${search}'/>"
                   class="${i == paginaActual ? 'active' : ''}">
                    ${i}
                </a>
            </c:forEach>
        </div>

    </div>
</div>

<!-- Buscador dinámico -->
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
