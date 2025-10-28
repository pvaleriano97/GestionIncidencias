<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestión de Usuarios</title>
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
 

        <!-- 🧾 Formulario de registro / edición -->
        <div class="form-container">
             <h3>Gestión de Usuarios</h3>
            <form class="incidencia-form" action="<c:url value='/UsuarioServlet'/>" method="post">
                <input type="hidden" name="idUsuario" value="${usuarioEdit != null ? usuarioEdit.idUsuario : ''}" />

                <div class="form-row">
                    <div class="form-group">
                        <label>Nombre</label>
                        <input type="text" name="nombre" value="${usuarioEdit != null ? usuarioEdit.nombre : ''}" required />
                    </div>
                    <div class="form-group">
                        <label>Apellido</label>
                        <input type="text" name="apellido" value="${usuarioEdit != null ? usuarioEdit.apellido : ''}" required />
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label>Correo</label>
                        <input type="email" name="correo" value="${usuarioEdit != null ? usuarioEdit.correo : ''}" required />
                    </div>
                    <div class="form-group">
    <label>Contraseña</label>
    <input type="password" name="contrasena" value="${usuarioEdit != null ? usuarioEdit.contrasena : ''}" required />
</div>

<div class="form-group">
    <label>Área</label>
    <input type="text" name="area" value="${usuarioEdit != null ? usuarioEdit.area : ''}" required />
</div>

                    <div class="form-group">
                        <label>Rol</label>
                        <select name="rol" required>
                            <option value="">--Seleccione--</option>
                            <option value="Administrador" ${usuarioEdit != null && usuarioEdit.rol == 'Administrador' ? 'selected' : ''}>Administrador</option>
                            <option value="Técnico" ${usuarioEdit != null && usuarioEdit.rol == 'Técnico' ? 'selected' : ''}>Técnico</option>
                            <option value="Usuario" ${usuarioEdit != null && usuarioEdit.rol == 'Usuario' ? 'selected' : ''}>Usuario</option>
                        </select>
                        
                    </div>
                </div>

                <div class="form-buttons">
                    <button type="submit" class="btn-primary">
                        <c:choose>
                            <c:when test="${usuarioEdit != null}">Actualizar</c:when>
                            <c:otherwise>Guardar</c:otherwise>
                        </c:choose>
                    </button>
                    <button type="reset" class="btn-secondary">Limpiar</button>
                </div>
            </form>
        </div>

        <!-- 🔍 Buscar usuarios -->
        <div class="search-container">
            <input type="text" id="searchInput" class="search-input" placeholder="Buscar usuario..." value="${search}">
        </div>

        <!-- 📋 Tabla de usuarios -->
        <table class="ticket-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Apellido</th>
                    <th>Correo</th>
                    <th>Rol</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="u" items="${listaUsuarios}">
                    <tr>
                        <td>${u.idUsuario}</td>
                        <td>${u.nombre}</td>
                        <td>${u.apellido}</td>
                        <td>${u.correo}</td>
                        <td>${u.rol}</td>
                        <td>
                            <a href="<c:url value='/UsuarioServlet?action=edit&id=${u.idUsuario}'/>">
                                <i class="fa-solid fa-pen-to-square icon-btn"></i>
                            </a>
                            <a href="<c:url value='/UsuarioServlet?action=delete&id=${u.idUsuario}'/>"
                               onclick="return confirm('¿Desea eliminar este usuario?')">
                                <i class="fa-solid fa-trash icon-btn delete"></i>
                            </a>
                        </td>
                    </tr>
                </c:forEach>

                <c:if test="${empty listaUsuarios}">
                    <tr><td colspan="6" style="text-align:center;">No se encontraron usuarios</td></tr>
                </c:if>
            </tbody>
        </table>

        <!-- 📄 Paginación centrada -->
        <div class="pagination">
            <c:forEach var="i" begin="1" end="${totalPaginas}">
                <a href="<c:url value='/UsuarioServlet?pagina=${i}&search=${search}'/>"
                   class="${i == paginaActual ? 'active' : ''}">${i}</a>
            </c:forEach>
        </div>
    </div>
</div>

<!-- 🔍 Filtro de búsqueda instantánea -->
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
