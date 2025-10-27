<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="content-header">
    <div class="header-left">
        <button class="menu-toggle" onclick="toggleMenu()">
            <i class="fa-solid fa-bars"></i>
        </button>
        <div>
            <h1>Gestión de Incidencias</h1>
            <p>Bienvenido al sistema</p>
        </div>
    </div>

    <div class="header-right">
        <span class="user-info">
            <i class="fa-solid fa-user"></i>
            <c:out value="${sessionScope.name != null ? sessionScope.name : 'Invitado'}"/>
        </span>
        <a href="${pageContext.request.contextPath}/LogoutServlet" class="btn-primary">
            <i class="fa-solid fa-right-from-bracket"></i> Cerrar sesión
        </a>
    </div>
</div>

<script>
function toggleMenu() {
    const sidebar = document.querySelector('.sidebar');
    sidebar.classList.toggle('active');
}
</script>

