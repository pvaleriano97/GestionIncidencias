<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestión de Usuarios</title>

    <link rel="stylesheet" href="<c:url value='/css/main.css'/>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

    <style>
        .error { color:#d32f2f; font-size:12px; }
        .ok { color:#2e7d32; font-size:12px; }

        .alert {
            padding: 10px;
            border-radius: 6px;
            margin-bottom: 10px;
        }
        .alert-success { background:#d4edda; color:#155724; }
        .alert-danger { background:#f8d7da; color:#721c24; }

        .pagination .page-link {
            border-radius: 8px;
            margin: 0 4px;
        }
    </style>
</head>

<body>

<div class="dashboard-container">

    <!-- Sidebar -->
    <jsp:include page="/views/menu.jsp" />

    <!-- Main -->
    <div class="main-content">

        <jsp:include page="/views/header.jsp" />

        <!-- Mensajes -->
        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <!-- Formulario -->
        <div class="form-container">
            <h3>Gestión de Usuarios</h3>

            <form class="incidencia-form" action="<c:url value='/UsuarioServlet'/>" method="post">

                <input type="hidden" name="idUsuario"
                       value="${usuarioEdit != null ? usuarioEdit.idUsuario : ''}"/>

                <div class="form-row">
                    <div class="form-group">
                        <label>Nombre</label>
                        <input type="text" id="nombre" name="nombre"
                               value="${usuarioEdit != null ? usuarioEdit.nombre : ''}"
                               required onkeyup="validar()"/>
                        <small id="msgNombre"></small>
                    </div>

                    <div class="form-group">
                        <label>Apellido</label>
                        <input type="text" id="apellido" name="apellido"
                               value="${usuarioEdit != null ? usuarioEdit.apellido : ''}"
                               required onkeyup="validar()"/>
                        <small id="msgApellido"></small>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label>Correo</label>
                        <input type="email" id="correo" name="correo"
                               value="${usuarioEdit != null ? usuarioEdit.correo : ''}"
                               required onkeyup="validar()"/>
                        <small id="msgCorreo"></small>
                    </div>

                    <div class="form-group">
                        <label>Contraseña</label>
                        <input type="password" name="contrasena" class="form-control"
                               ${usuarioEdit != null && usuarioEdit.rol == 'admin' ? 'disabled' : ''}/>
                        <c:if test="${usuarioEdit != null && usuarioEdit.rol == 'admin'}">
                            <small class="error">No se puede cambiar la contraseña del administrador</small>
                        </c:if>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label>Área</label>
                        <input type="text" name="area"
                               value="${usuarioEdit != null ? usuarioEdit.area : ''}"
                               required />
                    </div>

                    <div class="form-group">
                        <label>Rol</label>
                        <select name="rol" required>
                            <option value="">--Seleccione--</option>
                            <option value="admin"
                                ${usuarioEdit != null && usuarioEdit.rol=='admin' ? 'selected' : ''}>
                                admin
                            </option>
                            <option value="tecnico"
                                ${usuarioEdit != null && usuarioEdit.rol=='tecnico' ? 'selected' : ''}>
                                técnico
                            </option>
                        </select>
                    </div>
                </div>

                <div class="form-buttons">
                    <button type="submit" class="btn-primary">Guardar</button>
                    <button type="reset" class="btn-secondary">Limpiar</button>
                </div>

            </form>
        </div>

        <!-- Buscador -->
        <div class="search-container">
            <input type="text" id="searchInput" class="search-input"
                   placeholder="Buscar usuario..." value="${search}">
        </div>

        <!-- Tabla -->
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
                            <i class="fa-solid fa-pen-to-square"></i>
                        </a>
                        <a href="<c:url value='/UsuarioServlet?action=delete&id=${u.idUsuario}'/>"
                           onclick="return confirm('¿Eliminar usuario?')">
                            <i class="fa-solid fa-trash"></i>
                        </a>
                    </td>
                </tr>
            </c:forEach>

            <c:if test="${empty listaUsuarios}">
                <tr><td colspan="6" style="text-align:center">No hay usuarios</td></tr>
            </c:if>
            </tbody>
        </table>

        <!-- Paginación -->
        <c:if test="${totalPaginas > 1}">
            <nav>
                <ul class="pagination justify-content-center mt-3">

                    <c:if test="${paginaActual > 1}">
                        <li class="page-item">
                            <a class="page-link"
                               href="<c:url value='/UsuarioServlet?pagina=${paginaActual - 1}&search=${search}'/>">« Anterior</a>
                        </li>
                    </c:if>

                    <c:forEach begin="1" end="${totalPaginas}" var="i">
                        <li class="page-item ${i == paginaActual ? 'active' : ''}">
                            <a class="page-link"
                               href="<c:url value='/UsuarioServlet?pagina=${i}&search=${search}'/>">${i}</a>
                        </li>
                    </c:forEach>

                    <c:if test="${paginaActual < totalPaginas}">
                        <li class="page-item">
                            <a class="page-link"
                               href="<c:url value='/UsuarioServlet?pagina=${paginaActual + 1}&search=${search}'/>">Siguiente »</a>
                        </li>
                    </c:if>

                </ul>
            </nav>
        </c:if>

    </div>
</div>


<!-- Busqueda instantánea -->
<script>
document.getElementById("searchInput").addEventListener("keyup", function () {
    const filter = this.value.toLowerCase();
    document.querySelectorAll("tbody tr").forEach(row => {
        row.style.display = row.innerText.toLowerCase().includes(filter) ? "" : "none";
    });
});
</script>

<!-- Validación AJAX -->
<script>
function validar() {
    const params = new URLSearchParams({
        nombre: document.getElementById("nombre").value,
        apellido: document.getElementById("apellido").value,
        correo: document.getElementById("correo").value,
        idUsuario: document.querySelector("[name=idUsuario]").value
    });

    fetch("${pageContext.request.contextPath}/ValidarUsuarioServlet?" + params)
        .then(r => r.json())
        .then(d => {
            let error = false;

            if (d.duplicadoNombre) {
                msgNombre.textContent = "❌ Nombre y apellido ya existen";
                msgNombre.className = "error";
                error = true;
            } else {
                msgNombre.textContent = "✅ Disponible";
                msgNombre.className = "ok";
            }

            if (d.duplicadoCorreo) {
                msgCorreo.textContent = "❌ Correo ya registrado";
                msgCorreo.className = "error";
                error = true;
            } else {
                msgCorreo.textContent = "✅ Disponible";
                msgCorreo.className = "ok";
            }

            document.querySelector("button[type='submit']").disabled = error;
        });
}
</script>

</body>
</html>
