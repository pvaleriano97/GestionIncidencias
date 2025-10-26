<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="header">
    <div class="header-left">
        <!-- Botón hamburguesa para móviles -->
        <button class="menu-toggle" onclick="toggleMenu()">
            <i class="fa-solid fa-bars"></i>
        </button>
        <h2>Gestión de Incidencias</h2>
    </div>

    <div class="header-right">
        <!-- Saludo dinámico al usuario -->
        <span class="user-info">
            <i class="fa-solid fa-user"></i>
            Bienvenido, 
            <c:out value="${sessionScope.name != null ? sessionScope.name : 'Invitado'}"/>
        </span>

        <!-- Botón de cerrar sesión -->
        <a href="${pageContext.request.contextPath}/LogoutServlet" class="logout-btn">
            <i class="fa-solid fa-right-from-bracket"></i> Cerrar sesión
        </a>
    </div>
</div>

<!-- Script opcional para alternar menú en móviles -->
<script>
function toggleMenu() {
    const sidebar = document.querySelector('.sidebar');
    sidebar.classList.toggle('active');
}
</script>
