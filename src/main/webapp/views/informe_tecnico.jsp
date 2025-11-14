<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                <input type="hidden" id="idTecnico" name="idTecnico" value="${not empty informe ? informe.idTecnico : ''}">

                <!-- OBSERVACIONES (arriba) -->
                <div class="form-group">
                    <label>Observaciones</label>
                    <textarea name="observaciones" rows="3" maxlength="150" required>${not empty informe ? informe.observaciones : ''}</textarea>
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
                        <input type="text" id="descIncidencia" readonly value="${not empty informe ? informe.descripcionIncidencia : ''}">
                    </div>

                    <div class="form-group">
                        <label>Técnico asignado</label>
                        <input type="text" id="nombreTecnico" readonly value="${not empty informe ? informe.nombreTecnico : ''}">
                    </div>

                    <div class="form-group">
                        <label>Fecha de Cierre</label>
                        <%
                            String dtVal = "";
                            if (request.getAttribute("informe") != null) {
                                com.ticketsystem.model.InformeTecnico it = (com.ticketsystem.model.InformeTecnico) request.getAttribute("informe");
                                if (it.getFechaCierre() != null && it.getFechaCierre().length() >= 16) {
                                    dtVal = it.getFechaCierre().substring(0,16).replace(" ", "T");
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

        <!-- BUSCADOR -->
        <div class="search-container">
            <form id="formSearch" action="<c:url value='/InformeTecnicoServlet'/>" method="get" style="display:flex; gap:8px; align-items:center;">
                <input type="hidden" name="pagina" value="1">
                <input type="text" name="search" class="search-input" placeholder="Buscar por incidencia, técnico, fecha u observación..." value="${search}">
                <button type="submit" class="btn-primary">Buscar</button>
                <a href="<c:url value='/InformeTecnicoServlet'/>" class="btn-secondary">Reset</a>
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
                        <td><c:out value="${it.idTecnico}"/> <c:if test="${not empty it.nombreTecnico}"> - ${it.nombreTecnico}</c:if></td>
                        <td>
                            <a href="<c:url value='/InformeTecnicoServlet?action=editar&id=${it.idInforme}'/>" class="icon-link"><i class="fa fa-edit icon-btn"></i></a>
                            <a href="<c:url value='/InformeTecnicoServlet?action=eliminar&id=${it.idInforme}'/>" class="icon-link" onclick="return confirm('Eliminar informe?')"><i class="fa fa-trash icon-btn delete"></i></a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <!-- PAGINACIÓN -->
            <div class="pagination" aria-label="Paginación">
                <c:set var="p" value="${paginaActual}" />
                <c:set var="tp" value="${totalPaginas}" />
                <c:if test="${paginaActual > 1}">
                    <a href="<c:url value='/InformeTecnicoServlet?pagina=${paginaActual-1}&search=${fn:escapeXml(search)}'/>">&laquo; Prev</a>
                </c:if>

                <c:forEach var="i" begin="1" end="${tp}">
                    <a href="<c:url value='/InformeTecnicoServlet?pagina=${i}&search=${fn:escapeXml(search)}'/>" class="${i == p ? 'active' : ''}">${i}</a>
                </c:forEach>

                <c:if test="${paginaActual < totalPaginas}">
                    <a href="<c:url value='/InformeTecnicoServlet?pagina=${paginaActual+1}&search=${fn:escapeXml(search)}'/>">Next &raquo;</a>
                </c:if>
            </div>
        </div>

    </div>
</div>

<script>
    // Manejo AJAX para autocompletar técnico y descripción al cambiar incidencia
    document.getElementById('idIncidencia').addEventListener('change', function () {
        const id = this.value;
        const nombreInput = document.getElementById('nombreTecnico');
        const descInput = document.getElementById('descIncidencia');
        const idTecHidden = document.getElementById('idTecnico');

        if (!id) {
            nombreInput.value = '';
            descInput.value = '';
            idTecHidden.value = '';
            return;
        }

        // Llamada al servlet (fetchTecnico). Ajusta URL si es necesario.
        fetch('<c:url value="/InformeTecnicoServlet?action=fetchTecnico"/>&idIncidencia=' + encodeURIComponent(id))
            .then(resp => resp.json())
            .then(data => {
                if (data && data.ok) {
                    idTecHidden.value = data.idTecnico === null ? '' : data.idTecnico;
                    nombreInput.value = data.nombreTecnico || '';
                    // descripción de incidencia la tomamos del select option text si no la devuelve el fetch
                    const sel = document.getElementById('idIncidencia');
                    const opt = sel.options[sel.selectedIndex];
                    // texto del option viene como "id - descripcion"
                    const optText = opt ? opt.text : '';
                    const dashIndex = optText.indexOf(' - ');
                    descInput.value = (dashIndex > -1) ? optText.substring(dashIndex + 3) : optText;
                } else {
                    idTecHidden.value = '';
                    nombreInput.value = '';
                    descInput.value = '';
                }
            }).catch(err => {
                console.error(err);
                idTecHidden.value = '';
                nombreInput.value = '';
                descInput.value = '';
            });
    });

    // Si ya estamos editando, disparar change para rellenar
    window.addEventListener('load', function () {
        const sel = document.getElementById('idIncidencia');
        if (sel && sel.value) sel.dispatchEvent(new Event('change'));
    });
</script>
</body>
</html>
