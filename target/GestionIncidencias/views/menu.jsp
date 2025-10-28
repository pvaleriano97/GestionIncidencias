<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Sidebar del panel -->
<div class="sidebar">
    <div class="logo">
        <!-- <img src="${pageContext.request.contextPath}/img/logo.png" alt="Logo" class="logo-img"> -->
        <p class="logo-text">Sistema de Tickets</p>
    </div>

    <nav>
        <ul>
            <li>
                <a href="${pageContext.request.contextPath}/dashboard"
                   class="${page == 'dashboard' ? 'active' : ''}">
                    <i class="fa-solid fa-chart-line"></i> Dashboard
                </a>
            </li>

            <li>
                <a href="${pageContext.request.contextPath}/UsuarioServlet?action=list"
                   class="${page == 'usuarios' ? 'active' : ''}">
                    <i class="fa-solid fa-users"></i> Usuarios
                </a>
            </li>

            <li>
                <a href="${pageContext.request.contextPath}/EquipoServlet?action=list"
                   class="${page == 'equipos' ? 'active' : ''}">
                    <i class="fa-solid fa-laptop-code"></i> Equipos
                </a>
            </li>

            <li>
                <a href="${pageContext.request.contextPath}/TecnicoServlet?action=list"
                   class="${page == 'tecnicos' ? 'active' : ''}">
                    <i class="fa-solid fa-user-gear"></i> Técnicos
                </a>
            </li>

            <li>
                <a href="${pageContext.request.contextPath}/IncidenciaServlet?action=list"
                   class="${page == 'incidencias' ? 'active' : ''}">
                    <i class="fa-solid fa-ticket-alt"></i> Incidencias
                </a>
            </li>

            <li>
                <a href="${pageContext.request.contextPath}/HistorialEquipoServlet?action=list"
                   class="${page == 'historial' ? 'active' : ''}">
                    <i class="fa-solid fa-clock-rotate-left"></i> Historial Equipo
                </a>
            </li>

            <li>
                <a href="${pageContext.request.contextPath}/InformeTecnicoServlet?action=list"
                   class="${page == 'informes' ? 'active' : ''}">
                    <i class="fa-solid fa-file-circle-check"></i> Informe Técnico
                </a>
            </li>

            <li>
                <a href="${pageContext.request.contextPath}/RepuestoServlet?action=list"
                   class="${page == 'repuestos' ? 'active' : ''}">
                    <i class="fa-solid fa-boxes-stacked"></i> Repuestos
                </a>
            </li>

            <li>
                <a href="${pageContext.request.contextPath}/SolicitudRepuestoServlet?action=list"
                   class="${page == 'solicitudes' ? 'active' : ''}">
                    <i class="fa-solid fa-file-signature"></i> Solicitudes de Repuestos
                </a>
            </li>
        </ul>
    </nav>

    <div class="logout" onclick="location.href='${pageContext.request.contextPath}/LogoutServlet'">
        <i class="fa-solid fa-right-from-bracket"></i> Cerrar sesión
    </div>
</div>
