<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="sidebar" id="sidebar">

    <div class="sidebar-header">
        <h2 class="sidebar-title">Sistema de Tickets</h2>
    </div>

    <nav>
        <ul class="menu-list">

            <li>
                <a href="${pageContext.request.contextPath}/dashboard">
                    <i class="fa fa-chart-line"></i>
                    <span>Dashboard</span>
                </a>
            </li>

            <!-- ADMIN -->
            <c:if test="${sessionScope.role == 'admin'}">
                <li>
        <a href="${pageContext.request.contextPath}/AdminDashboardServlet">
            <i class="fa-solid fa-chart-pie"></i> Dashboard Admin
        </a>
    </li>
               <li>
                   <a href="${pageContext.request.contextPath}/UsuarioServlet"> <i class="fa-solid fa-desktop"></i>Gestionar Usuarios</a>
                          </li>
                  
                     <li><a href="${pageContext.request.contextPath}/EquipoServlet">
                    <i class="fa-solid fa-desktop"></i> Equipos </a>
                </li>

                <li><a href="${pageContext.request.contextPath}/TecnicoServlet">
                    <i class="fa-solid fa-user-tie"></i> Técnicos </a>
                </li>

                <li><a href="${pageContext.request.contextPath}/RepuestoServlet">
                    <i class="fa-solid fa-cogs"></i> Repuestos </a>
                </li>
                  <li>
                    <a href="${pageContext.request.contextPath}/IncidenciaServlet">
                        <i class="fa-solid fa-ticket"></i> Incidencias
                    </a>
                </li>

                <li><a href="${pageContext.request.contextPath}/SolicitudRepuestoServlet">
                    <i class="fa-solid fa-file-invoice"></i> Solicitudes </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/InformeTecnicoServlet">
                        <i class="fa-solid fa-file-lines"></i> Informe Técnico
                    </a>
                </li>
                 <li>
        <a href="${pageContext.request.contextPath}/ReportesServlet">
            <i class="fa-solid fa-file-chart-line"></i> Reportes
        </a>
    </li>
                
            </c:if>

            <!-- TECNICO -->
            <c:if test="${sessionScope.role == 'tecnico'}">
                <li>
        <a href="${pageContext.request.contextPath}/TecnicoDashboardServlet">
            <i class="fa-solid fa-chart-pie"></i> Dashboard Tecnicos
        </a>
    </li>
                <li>
                    <a href="${pageContext.request.contextPath}/IncidenciaServlet">
                        <i class="fa-solid fa-ticket"></i> Incidencias
                    </a>
                </li>

                <li>
                    <a href="${pageContext.request.contextPath}/HistorialEquipoServlet">
                        <i class="fa-solid fa-history"></i> Historial Equipo
                    </a>
                </li>

                <li>
                    <a href="${pageContext.request.contextPath}/InformeTecnicoServlet">
                        <i class="fa-solid fa-file-lines"></i> Informe Técnico
                    </a>
                </li>
          <li>
        <a href="${pageContext.request.contextPath}/ReportesServlet">
            <i class="fa-solid fa-file-chart-line"></i> Reportes
        </a>
    </li>
                 
            </c:if>

            <li class="logout">
                <a href="${pageContext.request.contextPath}/LogoutServlet">
                    <i class="fa-solid fa-right-from-bracket"></i>
                    <span>Cerrar sesión</span>
                </a>
            </li>

        </ul>
    </nav>
</div>
