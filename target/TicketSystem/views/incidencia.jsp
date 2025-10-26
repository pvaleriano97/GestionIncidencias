    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestión de Incidencias</title>
    <!-- CSS principal -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>
    <div class="config-container">
        <!-- Menú lateral -->
        <jsp:include page="/views/menu.jsp" />

        <!-- Contenido principal -->
        <div class="main-content">
            <!-- Header superior -->
            <jsp:include page="/views/header.jsp" />

            <!-- Contenido de la página -->
            <div class="page-container">
                <h1>Registro de Incidencias</h1>

                <!-- === FORMULARIO === -->
                <div class="config-form-card">
                    <form action="${pageContext.request.contextPath}/incidencia" method="post">
                        <input type="hidden" name="idIncidencia" value="${incidenciaEdit != null ? incidenciaEdit.idIncidencia : ''}" />

                        <div class="form-row">
                            <div class="form-group">
                                <label>Descripción</label>
                                <textarea name="descripcion" required>${incidenciaEdit != null ? incidenciaEdit.descripcion : ''}</textarea>
                            </div>
                            <div class="form-group">
                                <label>Estado</label>
                                <select name="estado" required>
                                    <option value="Abierta" ${incidenciaEdit != null && incidenciaEdit.estado == 'Abierta' ? 'selected' : ''}>Abierta</option>
                                    <option value="En Proceso" ${incidenciaEdit != null && incidenciaEdit.estado == 'En Proceso' ? 'selected' : ''}>En Proceso</option>
                                    <option value="Cerrada" ${incidenciaEdit != null && incidenciaEdit.estado == 'Cerrada' ? 'selected' : ''}>Cerrada</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="form-group">
                                <label>Usuario</label>
                                <select name="idUsuario" required>
                                    <option value="">--Seleccione--</option>
                                    <c:forEach var="u" items="${listaUsuarios}">
                                        <option value="${u.idUsuario}" ${incidenciaEdit != null && incidenciaEdit.idUsuario == u.idUsuario ? 'selected' : ''}>
                                            ${u.nombre}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>Equipo</label>
                                <select name="idEquipo" required>
                                    <option value="">--Seleccione--</option>
                                    <c:forEach var="e" items="${listaEquipos}">
                                        <option value="${e.idEquipo}" ${incidenciaEdit != null && incidenciaEdit.idEquipo == e.idEquipo ? 'selected' : ''}>
                                            ${e.codigoEquipo} (${e.tipo})
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="form-group">
                                <label>Técnico</label>
                                <select name="idTecnico">
                                    <option value="">--Seleccione--</option>
                                    <c:forEach var="t" items="${listaTecnicos}">
                                        <option value="${t.idTecnico}" ${incidenciaEdit != null && incidenciaEdit.idTecnico == t.idTecnico ? 'selected' : ''}>
                                            ${t.nombre}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="form-actions">
                            <button type="submit" class="btn-primary">Guardar</button>
                            <button type="reset" class="btn-secondary">Limpiar</button>
                        </div>
                    </form>
                </div>

                <!-- === TABLA DE INCIDENCIAS === -->
                <div class="info-card">
                    <h3>Lista de Incidencias</h3>
                    <div class="table-search">
                        <input type="text" id="searchInput" placeholder="Buscar por descripción, estado o usuario..." value="${search}">
                    </div>

                    <table class="ticket-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Descripción</th>
                                <th>Estado</th>
                                <th>Usuario</th>
                                <th>Equipo</th>
                                <th>Técnico</th>
                                <th>Fecha Registro</th>
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
                                        <a href="${pageContext.request.contextPath}/incidencia?action=edit&id=${incidencia.idIncidencia}" title="Editar">
                                            <i class="fa-solid fa-pen-to-square icon-btn"></i>
                                        </a>
                                        <a href="${pageContext.request.contextPath}/incidencia?action=delete&id=${incidencia.idIncidencia}" onclick="return confirm('¿Desea eliminar esta incidencia?')" title="Eliminar">
                                            <i class="fa-solid fa-trash icon-btn delete"></i>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <!-- Paginación -->
                    <div class="pagination">
                        <c:if test="${paginaActual > 1}">
                            <a href="${pageContext.request.contextPath}/incidencia?pagina=1&search=${search}">« Primera</a>
                        </c:if>

                        <c:forEach var="i" begin="1" end="${totalPaginas}">
                            <a href="${pageContext.request.contextPath}/incidencia?pagina=${i}&search=${search}" class="${i == paginaActual ? 'active' : ''}">${i}</a>
                        </c:forEach>

                        <c:if test="${paginaActual < totalPaginas}">
                            <a href="${pageContext.request.contextPath}/incidencia?pagina=${totalPaginas}&search=${search}">Última »</a>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- === SCRIPT DE FILTRADO === -->
    <script>
        const searchInput = document.getElementById('searchInput');
        searchInput.addEventListener('keyup', function() {
            const filter = searchInput.value.toLowerCase();
            const rows = document.querySelectorAll('.ticket-table tbody tr');
            rows.forEach(row => {
                const text = row.innerText.toLowerCase();
                row.style.display = text.includes(filter) ? '' : 'none';
            });
        });
    </script>

    <!-- === SCRIPT HAMBURGUESA === -->
    <script>
        function toggleMenu() {
            const menu = document.querySelector('.sidebar');
            const content = document.querySelector('.main-content');
            menu.classList.toggle('active');
            content.classList.toggle('menu-open');
        }
    </script>
</body>
</html>
