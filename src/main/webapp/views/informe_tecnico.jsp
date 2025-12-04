<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Informe Técnico</title>
    <link rel="stylesheet" href="<c:url value='/css/main.css'/>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>

<body>
<div class="dashboard-container">

    <jsp:include page="/views/menu.jsp" />

    <div class="main-content">
        <jsp:include page="/views/header.jsp" />

        <!-- FORMULARIO -->
        <div class="form-container incidencia-form">
            <h3>Informe Técnico</h3>

            <form id="formInforme" action="<c:url value='/InformeTecnicoServlet'/>" method="post">

                <c:if test="${not empty informe}">
                    <input type="hidden" name="idInforme" value="${informe.idInforme}">
                </c:if>

                <input type="hidden" id="idTecnico" name="idTecnico"
                       value="${not empty informe ? informe.idTecnico : ''}">

                <!-- OBSERVACIONES ARRIBA -->
                <div class="form-group">
                    <label>Observaciones</label>
                    <textarea name="observaciones" rows="3" maxlength="150" required>
                        ${not empty informe ? informe.observaciones : ''}
                    </textarea>
                </div>

                <div class="form-row">

                    <div class="form-group">
                        <label>Incidencia</label>
                        <select id="idIncidencia" name="idIncidencia" required>
                            <option value="">--Seleccione incidencia--</option>

                            <c:forEach var="inc" items="${listaIncidencias}">
                                <option value="${inc.idIncidencia}"
                                    <c:if test="${not empty informe && informe.idIncidencia == inc.idIncidencia}">selected</c:if>>
                                    ${inc.idIncidencia} - ${inc.descripcion}
                                </option>
                            </c:forEach>

                        </select>
                    </div>

                    <div class="form-group">
                        <label>Descripción de la incidencia</label>
                        <input type="text" id="descIncidencia" readonly
                               value="${not empty informe ? informe.descripcionIncidencia : ''}">
                    </div>

                    <div class="form-group">
                        <label>Técnico asignado</label>
                        <input type="text" id="nombreTecnico" readonly
                               value="${not empty informe ? informe.nombreTecnico : ''}">
                    </div>

                    <div class="form-group">
                        <label>Fecha de Cierre</label>

                        <%
                            String dtVal = "";
                            if (request.getAttribute("informe") != null) {
                                com.ticketsystem.model.InformeTecnico it =
                                        (com.ticketsystem.model.InformeTecnico) request.getAttribute("informe");

                                if (it.getFechaCierre() != null && it.getFechaCierre().length() >= 16) {
                                    dtVal = it.getFechaCierre()
                                            .substring(0, 16)
                                            .replace(" ", "T");
                                }
                            }
                        %>

                        <input type="datetime-local" name="fechaCierre" required value="<%= dtVal %>">
                    </div>
                </div>

                <div class="form-buttons">
                    <button type="submit" class="btn-primary">Guardar</button>
                    <a href="<c:url value='/InformeTecnicoServlet'/>" class="btn-secondary">Limpiar</a>
                </div>

            </form>
        </div>

        <!-- ================== BUSCADOR AUTOMÁTICO ==================== -->
        <div class="search-container" style="margin-bottom:20px;">
            <form id="formSearch"
                  action="<c:url value='/InformeTecnicoServlet'/>"
                  method="get"
                  style="display:flex; gap:10px; align-items:center; width:100%;">

                <input type="hidden" name="pagina" value="1">

                <input type="text"
                       name="search"
                       id="searchInput"
                       class="search-input"
                       placeholder="Buscar por incidencia, técnico, fecha u observación…"
                       value="${search}"
                       style="width:100%; padding:10px 15px; border-radius:8px;
                              border:1px solid #ccc; font-size:15px;">
            </form>
        </div>

        <!-- TABLA -->
        <div class="table-card">
            <table class="ticket-table">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Fecha Cierre</th>
                    <th>Observaciones</th>
                    <th>Incidencia</th>
                    <th>Técnico</th>
                    <th>Acciones</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="it" items="${lista}">
                    <tr>
                        <td>${it.idInforme}</td>
                        <td>${it.fechaCierre}</td>
                        <td>${it.observaciones}</td>
                        <td>${it.idIncidencia} - ${it.descripcionIncidencia}</td>

                        <td>
                            ${it.idTecnico}
                            <c:if test="${not empty it.nombreTecnico}"> - ${it.nombreTecnico}</c:if>
                        </td>

                        <td>
                            <a href="<c:url value='/InformeTecnicoServlet?action=editar&id=${it.idInforme}'/>"
                               class="icon-link"><i class="fa fa-edit icon-btn"></i></a>

                            <a href="<c:url value='/InformeTecnicoServlet?action=eliminar&id=${it.idInforme}'/>"
                               onclick="return confirm('Eliminar informe?')"
                               class="icon-link"><i class="fa fa-trash icon-btn delete"></i></a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <!-- PAGINACIÓN -->
            <div class="pagination" aria-label="Paginación">

                <c:if test="${paginaActual > 1}">
                    <a href="<c:url value='/InformeTecnicoServlet?pagina=${paginaActual-1}&search=${fn:escapeXml(search)}'/>">
                        &laquo; Prev
                    </a>
                </c:if>

                <c:forEach var="i" begin="1" end="${totalPaginas}">
                    <a href="<c:url value='/InformeTecnicoServlet?pagina=${i}&search=${fn:escapeXml(search)}'/>"
                       class="${i == paginaActual ? 'active' : ''}">
                        ${i}
                    </a>
                </c:forEach>

                <c:if test="${paginaActual < totalPaginas}">
                    <a href="<c:url value='/InformeTecnicoServlet?pagina=${paginaActual+1}&search=${fn:escapeXml(search)}'/>">
                        Next &raquo;
                    </a>
                </c:if>

            </div>
        </div>

    </div>
</div>

<!-- ================= JS PARA BÚSQUEDA AUTOMÁTICA ================= -->
<script>
    let timer = null;

    document.getElementById("searchInput").addEventListener("input", function () {

        clearTimeout(timer); // Evita spam

        timer = setTimeout(() => {
            document.getElementById("formSearch").submit();
        }, 400); // 0.4 segundos
    });
</script>

<!-- ================= JS PARA AUTOCOMPLETAR TÉCNICO ================= -->
<script>
    document.getElementById('idIncidencia').addEventListener('change', function () {

        const id = this.value;
        const nombreInput = document.getElementById('nombreTecnico');
        const descInput   = document.getElementById('descIncidencia');
        const idTecHidden = document.getElementById('idTecnico');

        if (!id) {
            nombreInput.value = '';
            descInput.value = '';
            idTecHidden.value = '';
            return;
        }

        fetch('<c:url value="/InformeTecnicoServlet?action=fetchTecnico"/>&idIncidencia=' + id)
            .then(r => r.json())
            .then(data => {
                if (data.ok) {
                    idTecHidden.value = data.idTecnico || '';
                    nombreInput.value = data.nombreTecnico || '';

                    const sel = document.getElementById('idIncidencia');
                    const opt = sel.options[sel.selectedIndex];
                    const txt = opt.text;
                    descInput.value = txt.substring(txt.indexOf(" - ") + 3);
                }
            });
    });

    window.onload = function () {
        const sel = document.getElementById('idIncidencia');
        if (sel.value) sel.dispatchEvent(new Event('change'));
    };
</script>

</body>
</html>
