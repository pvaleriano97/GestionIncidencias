<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<header class="top-header">

    <!-- Título del módulo -->
    <h1 class="header-title">${tituloPagina}</h1>

    <!-- Usuario -->
    <div class="header-user">
        <!-- Icono de usuario -->
        <i class="fa-solid fa-user user-icon"></i>

        <!-- Texto de bienvenida -->
        <span class="welcome-text">
            Bienvenido  
            <c:choose>
                <c:when test="${sessionScope.role == 'admin'}">Administrador</c:when>
                <c:when test="${sessionScope.role == 'tecnico'}">Técnico</c:when>
                <c:otherwise>Usuario</c:otherwise>
            </c:choose>
            ${sessionScope.name}
        </span>
    </div>

</header>
