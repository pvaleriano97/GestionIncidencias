<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Solicitudes de Repuestos</title>

    <link rel="stylesheet" href="<c:url value='/css/main.css'/>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>

<body>

<div class="dashboard-container">

    <!-- SIDEBAR -->
    <jsp:include page="/views/menu.jsp" />

    <!-- MAIN CONTENT -->
    <div class="main-content">

        <!-- HEADER -->
        <jsp:include page="/views/header.jsp" />

        <!-- FORMULARIO -->
        <div class="form-container">
            <h3>Solicitud de Repuesto</h3>

            <form id="formSolicitud" method="post" action="SolicitudRepuestoServlet">

                <input type="hidden" id="idSolicitud" name="idSolicitud"
                       value="${solicitudEdit != null ? solicitudEdit.idSolicitud : ''}">

                <input type="hidden" name="action"
                       value="${solicitudEdit != null ? 'editar' : 'insertar'}">

                <!-- INCIDENCIA + REPUESTO -->
                <div class="form-row">

                    <div class="form-group">
                        <label>Incidencia</label>
                        <select id="idIncidencia" name="idIncidencia" required>
                            <option value="" disabled selected>Seleccionar incidencia</option>

                            <c:forEach var="inc" items="${listaIncidencias}">
                                <option value="${inc.idIncidencia}"
                                        ${solicitudEdit != null && solicitudEdit.idIncidencia == inc.idIncidencia ? 'selected' : ''}>
                                    ${inc.descripcion}
                                </option>
                            </c:forEach>

                        </select>
                    </div>

                    <div class="form-group">
                        <label>Repuesto</label>
                        <select id="idRepuesto" name="idRepuesto" required>
                            <option value="" disabled selected>Seleccionar repuesto</option>

                            <c:forEach var="rep" items="${listaRepuestos}">
                                <option value="${rep.idRepuesto}" data-stock="${rep.stock}"
                                        ${solicitudEdit != null && solicitudEdit.idRepuesto == rep.idRepuesto ? 'selected' : ''}>
                                    ${rep.nombre}
                                </option>
                            </c:forEach>

                        </select>
                    </div>

                </div>

                <!-- CANTIDAD + STOCK -->
                <div class="form-row">

                    <div class="form-group">
                        <label>Cantidad</label>
                        <input type="number" id="cantidad" name="cantidad" min="1"
                               value="${solicitudEdit != null ? solicitudEdit.cantidad : ''}" required>
                    </div>

                    <div class="form-group">
                        <label>Stock</label>
                        <input type="number" id="stockActual" readonly>
                    </div>

                </div>

                <!-- FECHA -->
                <div class="form-row">
                    <div class="form-group full">
                        <label>Fecha</label>

                        <input type="datetime-local" id="fechaSolicitud" name="fechaSolicitud"
                               value="${solicitudEdit != null ? solicitudEdit.fechaSolicitud.toLocalDateTime().toString().replace(' ', 'T') : ''}">
                    </div>
                </div>

                <!-- BOTONES -->
                <div class="form-buttons">
                    <button type="submit" class="btn-primary">Guardar</button>
                    <button type="button" id="btnLimpiar" class="btn-secondary">Limpiar</button>
                </div>

            </form>
        </div>

        <!-- BUSCADOR -->
        <div class="search-container">
            <form method="get">
                <input type="text" name="search" value="${fn:escapeXml(search)}"
                       placeholder="Buscar por incidencia o repuesto">
                <button type="submit" class="btn-primary">Buscar</button>
            </form>
        </div>

        <!-- TABLA -->
        <table class="ticket-table">

            <thead>
            <tr>
                <th>ID</th>
                <th>Incidencia</th>
                <th>Repuesto</th>
                <th>Cantidad</th>
                <th>Stock</th>
                <th>Fecha</th>
                <th>Acciones</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach var="s" items="${listaSolicitudes}">
                <tr>
                    <td>${s.idSolicitud}</td>
                    <td>${s.descripcion}</td>
                    <td>${s.nombre}</td>
                    <td>${s.cantidad}</td>
                    <td>${s.stock}</td>
                    <td>${s.fechaSolicitud}</td>

                    <td>
                        <!-- EDITAR -->
                        <a href="SolicitudRepuestoServlet?action=editarForm&idSolicitud=${s.idSolicitud}"
                           class="icon-btn">
                            <i class="fa-solid fa-pen-to-square"></i>
                        </a>

                        <!-- ELIMINAR -->
                        <form method="post" action="SolicitudRepuestoServlet" style="display:inline;">
                            <input type="hidden" name="action" value="eliminar">
                            <input type="hidden" name="idSolicitud" value="${s.idSolicitud}">
                            <button type="submit" class="icon-btn delete"
                                    onclick="return confirm('¿Desea eliminar esta solicitud?');">
                                <i class="fa-solid fa-trash"></i>
                            </button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>

        </table>

        <!-- PAGINACIÓN -->
        <div class="pagination">
            <c:forEach begin="1" end="${totalPaginas}" var="i">
                <a href="SolicitudRepuestoServlet?page=${i}&search=${fn:escapeXml(search)}"
                   class="${i == page ? 'active' : ''}">
                    ${i}
                </a>
            </c:forEach>
        </div>

    </div>
</div>

<!-- SCRIPTS -->
<script>
    // STOCK AUTOMÁTICO
    const repuestoSel = document.getElementById("idRepuesto");
    const stockInput = document.getElementById("stockActual");

    repuestoSel.addEventListener("change", function () {
        const opt = this.options[this.selectedIndex];
        stockInput.value = opt.dataset.stock || '';
    });

    // CARGA INICIAL POR SI ESTÁ EDITANDO
    window.addEventListener("load", function () {
        if (repuestoSel.value) repuestoSel.dispatchEvent(new Event("change"));
    });

    // LIMPIAR FORMULARIO
    document.getElementById("btnLimpiar").addEventListener("click", function () {
        document.getElementById("formSolicitud").reset();
        stockInput.value = "";
    });
</script>

</body>
</html>
