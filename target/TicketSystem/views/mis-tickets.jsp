<%-- 
    Document   : mis-tickets
    Created on : 12 oct 2025, 9:16:00 p.m.
    Author     : acuar
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mis Tickets - HP Soporte BCP</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mis-tickets.css">
</head>
<body>
    <div class="dashboard-container">
        <!-- Sidebar -->
        <div class="sidebar">
            <div class="sidebar-header">
                <h2>HP Soporte BCP</h2>
                <p>Sistema de Tickets</p>
            </div>
            <nav class="sidebar-nav">
                <a href="${pageContext.request.contextPath}/dashboard" class="nav-item">
                    <span>📊</span> Panel de Control
                </a>
                <a href="${pageContext.request.contextPath}/nuevo-ticket" class="nav-item">
                    <span>➕</span> Nuevo Ticket
                </a>
                <a href="${pageContext.request.contextPath}/mis-tickets" class="nav-item active">
                    <span>🎫</span> Mis Tickets
                </a>
                <a href="${pageContext.request.contextPath}/configuracion" class="nav-item">
                    <span>⚙️</span> Configuración
                </a>
                <a href="${pageContext.request.contextPath}/logout" class="nav-item">
                    <span>🚪</span> Cerrar Sesión
                </a>
            </nav>
        </div>

        <!-- Main Content -->
        <div class="main-content">
            <!-- Header -->
            <header class="content-header">
                <div class="header-left">
                    <h1>Mis Tickets</h1>
                    <p>Gestiona y revisa todos tus tickets de soporte</p>
                </div>
                <div class="header-right">
                    <span class="user-info">${sessionScope.email}</span>
                    <span class="user-role">${sessionScope.role}</span>
                </div>
            </header>

            <!-- Mensajes de éxito/error -->
            <c:if test="${not empty mensajeExito}">
                <div class="alert alert-success">
                    <span>✅</span> ${mensajeExito}
                </div>
            </c:if>
            
            <c:if test="${not empty error}">
                <div class="alert alert-error">
                    <span>❌</span> ${error}
                </div>
            </c:if>

            <!-- Estadísticas Rápidas -->
            <div class="stats-overview">
                <div class="stat-card">
                    <div class="stat-icon total">📋</div>
                    <div class="stat-info">
                        <h3>Total</h3>
                        <span class="stat-number">${not empty tickets ? tickets.size() : 0}</span>
                    </div>
                </div>
                
                <div class="stat-card">
                    <div class="stat-icon abierto">🔓</div>
                    <div class="stat-info">
                        <h3>Abiertos</h3>
                        <span class="stat-number">
                            <c:set var="abiertos" value="0" />
                            <c:forEach items="${tickets}" var="ticket">
                                <c:if test="${ticket.estado == 'ABIERTO'}">
                                    <c:set var="abiertos" value="${abiertos + 1}" />
                                </c:if>
                            </c:forEach>
                            ${abiertos}
                        </span>
                    </div>
                </div>
                
                <div class="stat-card">
                    <div class="stat-icon proceso">🔄</div>
                    <div class="stat-info">
                        <h3>En Proceso</h3>
                        <span class="stat-number">
                            <c:set var="proceso" value="0" />
                            <c:forEach items="${tickets}" var="ticket">
                                <c:if test="${ticket.estado == 'EN_PROCESO'}">
                                    <c:set var="proceso" value="${proceso + 1}" />
                                </c:if>
                            </c:forEach>
                            ${proceso}
                        </span>
                    </div>
                </div>
                
                <div class="stat-card">
                    <div class="stat-icon cerrado">✅</div>
                    <div class="stat-info">
                        <h3>Cerrados</h3>
                        <span class="stat-number">
                            <c:set var="cerrados" value="0" />
                            <c:forEach items="${tickets}" var="ticket">
                                <c:if test="${ticket.estado == 'CERRADO'}">
                                    <c:set var="cerrados" value="${cerrados + 1}" />
                                </c:if>
                            </c:forEach>
                            ${cerrados}
                        </span>
                    </div>
                </div>
            </div>

            <!-- Filtros y Búsqueda -->
            <div class="filters-card">
                <div class="card-header">
                    <h3>Filtrar Tickets</h3>
                </div>
                <form action="${pageContext.request.contextPath}/mis-tickets" method="get" class="filters-form">
                    <div class="filter-row">
                        <div class="filter-group">
                            <label for="estado">Estado:</label>
                            <select id="estado" name="estado" class="filter-select">
                                <option value="todos" ${empty filtroEstado or filtroEstado == 'todos' ? 'selected' : ''}>Todos los estados</option>
                                <option value="ABIERTO" ${filtroEstado == 'ABIERTO' ? 'selected' : ''}>Abiertos</option>
                                <option value="EN_PROCESO" ${filtroEstado == 'EN_PROCESO' ? 'selected' : ''}>En Proceso</option>
                                <option value="CERRADO" ${filtroEstado == 'CERRADO' ? 'selected' : ''}>Cerrados</option>
                            </select>
                        </div>
                        
                        <div class="filter-group">
                            <label for="prioridad">Prioridad:</label>
                            <select id="prioridad" name="prioridad" class="filter-select">
                                <option value="todas" ${empty filtroPrioridad or filtroPrioridad == 'todas' ? 'selected' : ''}>Todas las prioridades</option>
                                <option value="alta" ${filtroPrioridad == 'alta' ? 'selected' : ''}>Alta</option>
                                <option value="media" ${filtroPrioridad == 'media' ? 'selected' : ''}>Media</option>
                                <option value="baja" ${filtroPrioridad == 'baja' ? 'selected' : ''}>Baja</option>
                                <option value="urgente" ${filtroPrioridad == 'urgente' ? 'selected' : ''}>Urgente</option>
                            </select>
                        </div>
                        
                        <div class="filter-group">
                            <label for="categoria">Categoría:</label>
                            <select id="categoria" name="categoria" class="filter-select">
                                <option value="todas" ${empty filtroCategoria or filtroCategoria == 'todas' ? 'selected' : ''}>Todas las categorías</option>
                                <option value="accesos" ${filtroCategoria == 'accesos' ? 'selected' : ''}>Accesos</option>
                                <option value="hardware" ${filtroCategoria == 'hardware' ? 'selected' : ''}>Hardware</option>
                                <option value="software" ${filtroCategoria == 'software' ? 'selected' : ''}>Software</option>
                                <option value="redes" ${filtroCategoria == 'redes' ? 'selected' : ''}>Redes</option>
                                <option value="impresoras" ${filtroCategoria == 'impresoras' ? 'selected' : ''}>Impresoras</option>
                                <option value="aplicaciones" ${filtroCategoria == 'aplicaciones' ? 'selected' : ''}>Aplicaciones</option>
                            </select>
                        </div>
                        
                        <button type="submit" class="btn-primary">
                            <span>🔍</span> Aplicar Filtros
                        </button>
                        
                        <a href="${pageContext.request.contextPath}/mis-tickets" class="btn-secondary">
                            <span>🔄</span> Limpiar
                        </a>
                    </div>
                </form>
            </div>

            <!-- Lista de Tickets -->
            <div class="tickets-section">
                <div class="section-header">
                    <h2>Tus Tickets de Soporte</h2>
                    <span class="ticket-count">${not empty tickets ? tickets.size() : 0} tickets encontrados</span>
                </div>

                <div class="tickets-grid">
                    <c:choose>
                        <c:when test="${not empty tickets}">
                            <c:forEach items="${tickets}" var="ticket">
                                <div class="ticket-card">
                                    <div class="ticket-header">
                                        <div class="ticket-title-section">
                                            <h3>${ticket.titulo}</h3>
                                            <span class="ticket-id">#${ticket.numeroTicket}</span>
                                        </div>
                                        <div class="ticket-status-badge">
                                            <span class="status status-${fn:toLowerCase(ticket.estado)}">
                                                <c:choose>
                                                    <c:when test="${ticket.estado == 'ABIERTO'}">Abierto</c:when>
                                                    <c:when test="${ticket.estado == 'EN_PROCESO'}">En Proceso</c:when>
                                                    <c:when test="${ticket.estado == 'CERRADO'}">Cerrado</c:when>
                                                    <c:otherwise>${ticket.estado}</c:otherwise>
                                                </c:choose>
                                            </span>
                                        </div>
                                    </div>
                                    
                                    <div class="ticket-body">
                                        <p class="ticket-description">
                                            <c:choose>
                                                <c:when test="${fn:length(ticket.descripcion) > 150}">
                                                    ${fn:substring(ticket.descripcion, 0, 150)}...
                                                </c:when>
                                                <c:otherwise>
                                                    ${ticket.descripcion}
                                                </c:otherwise>
                                            </c:choose>
                                        </p>
                                        
                                        <div class="ticket-meta">
                                            <div class="meta-item">
                                                <span class="meta-label">Prioridad:</span>
                                                <span class="priority priority-${ticket.prioridad}">
                                                    <c:choose>
                                                        <c:when test="${ticket.prioridad == 'alta'}">Alta</c:when>
                                                        <c:when test="${ticket.prioridad == 'media'}">Media</c:when>
                                                        <c:when test="${ticket.prioridad == 'baja'}">Baja</c:when>
                                                        <c:when test="${ticket.prioridad == 'urgente'}">Urgente</c:when>
                                                        <c:otherwise>${ticket.prioridad}</c:otherwise>
                                                    </c:choose>
                                                </span>
                                            </div>
                                            <div class="meta-item">
                                                <span class="meta-label">Categoría:</span>
                                                <span class="category">${ticket.categoria}</span>
                                            </div>
                                            <div class="meta-item">
                                                <span class="meta-label">Empresa:</span>
                                                <span class="company">${ticket.empresa}</span>
                                            </div>
                                            <div class="meta-item">
                                                <span class="meta-label">Creado:</span>
                                                <span class="date">${ticket.fechaCreacion}</span>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <div class="ticket-actions">
                                        <button class="btn btn-outline" onclick="verDetalle('${ticket.id}')">
                                            <span>👁️</span> Ver Detalles
                                        </button>
                                        <c:if test="${ticket.estado != 'CERRADO'}">
                                            <button class="btn btn-secondary" onclick="editarTicket('${ticket.id}')">
                                                <span>✏️</span> Editar
                                            </button>
                                        </c:if>
                                        <c:if test="${ticket.estado == 'CERRADO'}">
                                            <button class="btn btn-secondary" onclick="reabrirTicket('${ticket.id}')">
                                                <span>🔄</span> Reabrir
                                            </button>
                                        </c:if>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div class="empty-state">
                                <div class="empty-icon">🎫</div>
                                <h3>No se encontraron tickets</h3>
                                <p>No hay tickets que coincidan con los criterios de búsqueda.</p>
                                <a href="${pageContext.request.contextPath}/nuevo-ticket" class="btn-primary">
                                    <span>➕</span> Crear Primer Ticket
                                </a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>

    <script>
        function verDetalle(ticketId) {
            alert('Funcionalidad de ver detalles será implementada próximamente para el ticket: ' + ticketId);
            // window.location.href = '${pageContext.request.contextPath}/detalle-ticket?id=' + ticketId;
        }
        
        function editarTicket(ticketId) {
            alert('Funcionalidad de editar será implementada próximamente para el ticket: ' + ticketId);
            // window.location.href = '${pageContext.request.contextPath}/editar-ticket?id=' + ticketId;
        }
        
        function reabrirTicket(ticketId) {
            if (confirm('¿Estás seguro de que quieres reabrir este ticket?')) {
                alert('Funcionalidad de reabrir será implementada próximamente para el ticket: ' + ticketId);
                // Aquí iría la lógica AJAX para reabrir el ticket
            }
        }
        
        // Auto-submit form cuando cambien algunos filtros
        document.addEventListener('DOMContentLoaded', function() {
            const filterSelects = document.querySelectorAll('.filter-select');
            filterSelects.forEach(select => {
                select.addEventListener('change', function() {
                    // Auto-submit solo para cambios rápidos, no para todos los filtros
                    if (this.id === 'estado' || this.id === 'prioridad') {
                        this.form.submit();
                    }
                });
            });
        });
    </script>
</body>
</html>