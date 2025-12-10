<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestión de Equipos</title>
    <link rel="stylesheet" href="<c:url value='/css/main.css'/>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <style>
.pagination {
    list-style: none;
    padding-left: 0;
}
.pagination .page-link {
    border-radius: 8px;
    margin: 0 4px;
}
.pagination .page-item::before {
    content: none;
}
.error { color: red; font-size: 13px; }
.ok { color: green; font-size: 13px; }
</style>

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
            <h3>Gestión de Equipos</h3>

            <form class="equipo-form" action="<c:url value='/EquipoServlet'/>" method="post">
                <input type="hidden" name="idEquipo" value="${equipoEdit != null ? equipoEdit.idEquipo : ''}" />

                <div class="form-row">
                    <div class="form-group">
                        <label>Código del Equipo</label>
                       <input type="text" name="codigoEquipo" id="codigoEquipo" required  onkeyup="validarCodigoEquipo()"  value="${equipoEdit != null ? equipoEdit.codigoEquipo : ''}">
<span id="msgCodigo"></span>
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

       <!-- ================= PAGINACIÓN EQUIPO ================= -->
<c:if test="${totalPaginas > 1}">
    <nav aria-label="Page navigation">
        <ul class="pagination justify-content-center mt-3">

            <!-- Anterior -->
            <c:if test="${paginaActual > 1}">
                <li class="page-item">
                    <a class="page-link"
                       href="<c:url value='/EquipoServlet?pagina=${paginaActual - 1}&search=${search}'/>">
                        « Anterior
                    </a>
                </li>
            </c:if>

            <!-- Números -->
            <c:forEach begin="1" end="${totalPaginas}" var="i">
                <li class="page-item ${i == paginaActual ? 'active' : ''}">
                    <a class="page-link"
                       href="<c:url value='/EquipoServlet?pagina=${i}&search=${search}'/>">
                        ${i}
                    </a>
                </li>
            </c:forEach>

            <!-- Siguiente -->
            <c:if test="${paginaActual < totalPaginas}">
                <li class="page-item">
                    <a class="page-link"
                       href="<c:url value='/EquipoServlet?pagina=${paginaActual + 1}&search=${search}'/>">
                        Siguiente »
                    </a>
                </li>
            </c:if>

        </ul>
    </nav>
</c:if>

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
<script>
function validarCodigoEquipo() {

    const codigo = document.getElementById("codigoEquipo").value;
    const msg = document.getElementById("msgCodigo");
    const btn = document.querySelector(".btn-primary");
    const idEquipo = document.querySelector("[name=idEquipo]").value;

    if (codigo.trim() === "") {
        msg.innerText = "";
        btn.disabled = false;
        return;
    }

    const params = new URLSearchParams({
        codigoEquipo: codigo,
        idEquipo: idEquipo
    });

    fetch("${pageContext.request.contextPath}/ValidarEquipoServlet?" + params)
        .then(res => res.json())
        .then(data => {
            if (data.duplicado) {
                msg.innerText = "❌ Código de equipo ya existe";
                msg.className = "error";
                btn.disabled = true;
            } else {
                msg.innerText = "✅ Código disponible";
                msg.className = "ok";
                btn.disabled = false;
            }
        })
        .catch(() => {
            msg.innerText = "⚠ Error al validar";
            msg.className = "error";
            btn.disabled = true;
        });
}
</script>
</body>
</html>
