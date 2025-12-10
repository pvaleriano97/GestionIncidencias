<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestión de Incidencias - Login</title>

    <!-- Bootstrap CSS y JS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    <!-- FontAwesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

    <!-- CSS personalizado -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
<style>
    .input-password-container {
        position: relative;
        margin-bottom: 1rem;
    }
    .input-password-container .form-control {
        height: 45px;
        padding-right: 2.5rem;
    }
    .input-password-container .toggle-password {
        position: absolute;
        top: 50%;
        right: 15px;
        transform: translateY(-50%);
        cursor: pointer;
        font-size: 1.2rem;
        color: #555;
    }
</style>

</head>
<body class="login-body">

<div class="d-flex justify-content-center align-items-center vh-100">
    <div class="login-container">
        <div class="text-center mb-4">
            <i class="fa-solid fa-tools" style="font-size: 55px; color:#34495E;"></i>
            <h1>Gestión de Incidencias</h1>
            <p>Inicia sesión para continuar</p>
        </div>

        <!-- ================= LOGIN FORM ================= -->
        <form action="${pageContext.request.contextPath}/LoginServlet" method="post">
            <div class="form-group mb-3">
                <label for="correo">Correo electrónico</label>
                <input type="email" id="correo" name="correo" required
                       value="<c:out value='${param.correo}'/>"
                       class="form-control">
            </div>

<div class="form-group mb-3">
    <label for="contrasena">Contraseña</label>
    <div class="input-password-container" style="position: relative;">
        <input type="password" id="contrasena" name="contrasena" required class="form-control" style="padding-right: 2.5rem; height:45px;">
        <i class="fa-solid fa-eye toggle-password" id="togglePassword" 
           style="position: absolute; top: 50%; right: 15px; transform: translateY(-50%); cursor:pointer; font-size:1.2rem; color:#555;"></i>
    </div>
</div>                
                       
            <div class="mb-3 form-check">
                <input class="form-check-input" type="checkbox" name="rememberMe" id="rememberMe">
                <label class="form-check-label" for="rememberMe">Mantener sesión iniciada</label>
            </div>

            <button type="submit" class="btn btn-primary w-100">Ingresar</button>

            <div class="text-center mt-2">
                <small>
                    <a href="#" class="text-decoration-none" data-bs-toggle="modal" data-bs-target="#recuperarModal">
                        ¿No recuerdas tu contraseña?
                    </a>
                </small>
            </div>
        </form>
    </div>
</div>

<!-- ================= MODAL RECUPERAR CONTRASEÑA ================= -->
<div class="modal fade" id="recuperarModal" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content p-3">
            <div class="modal-header">
                <h5 class="modal-title"><i class="fas fa-envelope me-2"></i>Recuperar Contraseña</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <form action="${pageContext.request.contextPath}/RecuperarPasswordServlet" method="post">
                    <div class="mb-3">
                        <label for="correoRecuperar" class="form-label">Correo electrónico</label>
                        <input type="email" id="correoRecuperar" name="correo" required class="form-control" placeholder="ejemplo@correo.com">
                    </div>
                    <button type="submit" class="btn btn-warning w-100">Enviar enlace de recuperación</button>
                </form>

                <!-- MENSAJES EN EL MODAL -->
                <c:if test="${not empty error}">
                    <div class="alert alert-danger mt-3 text-center">${error}</div>
                </c:if>
                <c:if test="${not empty mensaje}">
                    <div class="alert alert-success mt-3 text-center">${mensaje}</div>
                </c:if>
            </div>
        </div>
    </div>
</div>



<!-- ================= MODAL ÉXITO ================= -->
<c:if test="${not empty mensaje}">
<div class="modal fade" id="successModal" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content p-3">
            <div class="modal-header">
                <h5 class="modal-title"><i class="fas fa-check-circle me-2 text-success"></i>Éxito</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body text-center">
                <p>${mensaje}</p>
                <button class="btn btn-success w-100" data-bs-dismiss="modal">Aceptar</button>
            </div>
        </div>
    </div>
</div>
</c:if>

<!-- ================= MODAL 2FA ================= -->
<div class="modal fade" id="modal2FA" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content p-3">
            <div class="modal-header">
                <h5 class="modal-title"><i class="fas fa-lock me-2"></i>Verificación 2FA</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body text-center">
                <p class="fw-bold">Hola, <c:out value="${nombre}"/></p>
                <p>Escanea este código QR en Google Authenticator:</p>
                <c:if test="${not empty qrUrl}">
                    <img src="${qrUrl}" alt="QR Authenticator" class="img-thumbnail mb-3" width="250">
                </c:if>
                <form action="${pageContext.request.contextPath}/LoginServlet" method="post">
                    <label class="fw-bold mb-2">Código de Google Authenticator</label>
                    <input type="text" name="codigo2FA" class="form-control mb-3" maxlength="6" placeholder="123456" required>
                    <button class="btn btn-primary w-100">Verificar</button>
                </form>
                <c:if test="${not empty error2FA}">
                    <div class="alert alert-danger mt-3">${error2FA}</div>
                </c:if>
            </div>
        </div>
    </div>
</div>

<!-- ================= SCRIPT PARA ABRIR MODALES ================= -->
<script>
    // Modal 2FA
    <c:if test="${show2FA == true}">
    var modal2FA = new bootstrap.Modal(document.getElementById('modal2FA'));
    modal2FA.show();
    </c:if>

    // Modal recuperar contraseña
    <c:if test="${not empty error && not empty correoRecuperar}">
    var modalRecuperar = new bootstrap.Modal(document.getElementById('recuperarModal'));
    modalRecuperar.show();
    </c:if>

    // Modal reset contraseña y abrir éxito automáticamente
    <c:if test="${showResetModal == true}">
    var resetModalEl = document.getElementById('resetPasswordModal');
    var resetModal = new bootstrap.Modal(resetModalEl);
    resetModal.show();

    <c:if test="${not empty mensaje}">
    resetModalEl.addEventListener('shown.bs.modal', function () {
        setTimeout(function() {
            resetModal.hide(); // Cierra el modal de reset
            var successModal = new bootstrap.Modal(document.getElementById('successModal'));
            successModal.show(); // Abre el modal de éxito
        }, 500); // Espera medio segundo para suavizar la transición
    });
    </c:if>
    </c:if>

    // Modal éxito directo (si no viene del reset)
    <c:if test="${not empty mensaje && showResetModal != true}">
    var successModal = new bootstrap.Modal(document.getElementById('successModal'));
    successModal.show();
    </c:if>
</script>
<script>
    const togglePassword = document.querySelector("#togglePassword");
    const password = document.querySelector("#contrasena");

    togglePassword.addEventListener("click", function () {
        // Cambiar tipo de input
        const type = password.getAttribute("type") === "password" ? "text" : "password";
        password.setAttribute("type", type);
        // Cambiar ícono
        this.classList.toggle("fa-eye-slash");
    });
</script>
</body>
</html>
