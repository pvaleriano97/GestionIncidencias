<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Sidebar del panel -->
<div class="sidebar">
    <div class="logo">
      <!--  <img src="${pageContext.request.contextPath}/img/logo.png" alt="Logo" class="logo-img"> -->
        <p class="logo-text">Sistema de Tickets</p>
    </div>

    <nav>
       <ul>
   <li>
    <a href="${pageContext.request.contextPath}/dashboard" class="${page == 'dashboard' ? 'active' : ''}">
        <i class="fa-solid fa-chart-line"></i> Dashboard
    </a>
</li>

<li>
    <a href="${pageContext.request.contextPath}/UsuariosServlet?action=list" class="${page == 'usuarios' ? 'active' : ''}">
        <i class="fa-solid fa-users"></i> Usuarios
    </a>
</li>

<li>
   <a href="${pageContext.request.contextPath}/EquipoServlet" class="nav-item">
    <i class="fa-solid fa-laptop-code"></i> Equipos
</a>
</li>

<li>
    <a href="${pageContext.request.contextPath}/TecnicosServlet?action=list" class="${page == 'tecnicos' ? 'active' : ''}">
        <i class="fa-solid fa-user-gear"></i> Técnicos
    </a>
</li>

<li>
    <a href="${pageContext.request.contextPath}/IncidenciaServlet?action=list" class="${page == 'incidencias' ? 'active' : ''}">
        <i class="fa-solid fa-ticket-alt"></i> Incidencias
    </a>
</li>

</ul>

    </nav>

    <div class="logout" onclick="location.href='${pageContext.request.contextPath}/LogoutServlet'">
        <i class="fa-solid fa-right-from-bracket"></i> Cerrar sesión
    </div>
</div>
