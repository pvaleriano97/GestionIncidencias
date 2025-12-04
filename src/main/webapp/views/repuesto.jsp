<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestión de Repuestos</title>

    <link rel="stylesheet" href="<c:url value='/css/main.css'/>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

    <script>
        function editar(id, nombre, stock, costo) {
            document.getElementById("idRepuesto").value = id;
            document.getElementById("nombre").value = nombre;
            document.getElementById("stock").value = stock;
            document.getElementById("costo").value = costo;

            document.getElementById("form-title").innerText = "Editar Repuesto";
            document.getElementById("btn-guardar").innerText = "Actualizar";
            document.getElementById("form-action").value = "actualizar";
            document.getElementById("btn-cancelar").style.display = "inline-block";
        }

        function cancelarEdicion() {
            document.getElementById("idRepuesto").value = "";
            document.getElementById("nombre").value = "";
            document.getElementById("stock").value = "";
            document.getElementById("costo").value = "";

            document.getElementById("form-title").innerText = "Registrar Repuesto";
            document.getElementById("btn-guardar").innerText = "Guardar";
            document.getElementById("form-action").value = "registrar";
            document.getElementById("btn-cancelar").style.display = "none";
        }
    </script>
</head>

<body>

<div class="dashboard-container">

    <jsp:include page="/views/menu.jsp"/>

    <div class="main-content">

        <jsp:include page="/views/header.jsp"/>

        <!-- FORMULARIO -->
        <div class="form-container">
            <h3 id="form-title">Registrar Repuesto</h3>

            <form class="incidencia-form" action="RepuestoServlet" method="post">
                <input type="hidden" name="action" id="form-action" value="registrar">
                <input type="hidden" id="idRepuesto" name="idRepuesto">

                <div class="form-row">
                    <div class="form-group">
                        <label>Nombre</label>
                        <input type="text" id="nombre" name="nombre" required />
                    </div>

                    <div class="form-group">
                        <label>Stock</label>
                        <input type="number" id="stock" name="stock" required />
                    </div>

                    <div class="form-group">
                        <label>Costo</label>
                        <input type="number" step="0.01" id="costo" name="costo" required />
                    </div>
                </div>

                <div class="form-buttons">
                    <button type="submit" id="btn-guardar" class="btn-primary">Guardar</button>

                    <button type="button" id="btn-cancelar"
                            class="btn-secondary"
                            onclick="cancelarEdicion()"
                            style="display:none;">
                        Cancelar
                    </button>
                </div>

            </form>
        </div>

        <!-- BUSCADOR -->
        <div class="search-container">
            <form method="get" action="RepuestoServlet">
                <input type="text"
                       name="search"
                       class="search-input"
                       placeholder="Buscar repuesto..."
                       value="${search}">
            </form>
        </div>

        <!-- TABLA -->
        <table class="ticket-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Stock</th>
                    <th>Costo</th>
                    <th>Acciones</th>
                </tr>
            </thead>

            <tbody>
            <c:forEach var="r" items="${lista}">
                <tr>
                    <td>${r.idRepuesto}</td>
                    <td>${r.nombre}</td>
                    <td>${r.stock}</td>
                    <td>S/. ${r.costo}</td>
                    <td>
                        <a href="#" onclick="editar('${r.idRepuesto}','${r.nombre}','${r.stock}','${r.costo}')">
                            <i class="fa-solid fa-pen-to-square icon-btn"></i>
                        </a>

                        <a href="RepuestoServlet?action=eliminar&id=${r.idRepuesto}"
                           onclick="return confirm('¿Eliminar este repuesto?')">
                            <i class="fa-solid fa-trash icon-btn delete"></i>
                        </a>
                    </td>
                </tr>
            </c:forEach>

            <c:if test="${empty lista}">
                <tr><td colspan="5" style="text-align:center;">No se encontraron repuestos</td></tr>
            </c:if>
            </tbody>
        </table>

        <!-- PAGINACIÓN -->
        <div class="pagination">
            <c:forEach var="i" begin="1" end="${totalPaginas}">
                <a href="RepuestoServlet?pagina=${i}&search=${search}"
                   class="${i == paginaActual ? 'active' : ''}">
                    ${i}
                </a>
            </c:forEach>
        </div>

    </div>
</div>

</body>
</html>
