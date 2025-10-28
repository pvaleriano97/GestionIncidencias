<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestión de Técnicos</title>
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

        <!-- Formulario -->
        <div class="form-container">
            <h3>Gestión de Técnicos</h3>

            <form class="tecnico-form" action="<c:url value='/TecnicoServlet'/>" method="post">
                <input type="hidden" name="idTecnico" value="${tecnicoEdit != null ? tecnicoEdit.idTecnico : ''}" />

                <div class="form-row">
                    <div class="form-group">
                        <label>Nombre del Técnico</label>
                        <input type="text" name="nombre" required value="${tecnicoEdit != null ? tecnicoEdit.nombre : ''}">
                    </div>
                    <div class="form-group">
                        <label>Especialidad</label>
                        <input type="text" name="especialidad" required value="${tecnicoEdit != null ? tecnicoEdit.especialidad : ''}">
                    </div>
                </div>

                <div class="form-group">
                    <label>Disponibilidad</label>
                    <select name="disponibilidad" required>
                        <option value="">--Seleccione--</option>
                        <option value="1" ${tecnicoEdit != null && tecnicoEdit.disponibilidad == 1 ? 'selected' : ''}>Disponible</option>
                        <option value="0" ${tecnicoEdit != null && tecnicoEdit.disponibilidad == 0 ? 'selected' : ''}>No disponible</option>
                    </select>
                </div>

                <div class="form-buttons">
                    <button type="submit" class="btn-primary">Guardar</button>
                    <button type="reset" class="btn-secondary">Limpiar</button>
                </div>
            </form>
        </div>

        <!-- Buscar -->
        <div class="search-container">
            <input type="text" id="searchInput" class="search-input" placeholder="Buscar por nombre o especialidad..." value="${search}">
        </div>

        <!-- Tabla -->
        <table class="ticket-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Especialidad</th>
                    <th>Disponibilidad</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="t" items="${listaTecnicos}">
                    <tr>
                        <td>${t.idTecnico}</td>
                        <td>${t.nombre}</td>
                        <td>${t.especialidad}</td>
                        <td>
                            <c:choose>
                                <c:when test="${t.disponibilidad == 1}">
                                    <span class="status success">Disponible</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="status danger">No disponible</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <a href="<c:url value='/TecnicoServlet?action=edit&id=${t.idTecnico}'/>">
                                <i class="fa-solid fa-pen-to-square icon-btn"></i>
                            </a>
                            <a href="<c:url value='/TecnicoServlet?action=delete&id=${t.idTecnico}'/>"
                               onclick="return confirm('¿Desea eliminar este técnico?')">
                                <i class="fa-solid fa-trash icon-btn delete"></i>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- Paginación -->
        <div class="pagination">
            <c:forEach var="i" begin="1" end="${totalPaginas}">
                <a href="<c:url value='/TecnicoServlet?pagina=${i}&search=${search}'/>" 
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
