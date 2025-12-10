<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Restablecer Contraseña</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">

    <style>
        body {
            background-color: #f5f6fa;
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .reset-container {
            background: #fff;
            padding: 30px 25px;
            border-radius: 12px;
            box-shadow: 0 8px 20px rgba(0,0,0,0.1);
            width: 100%;
            max-width: 400px;
        }
        .reset-container h2 {
            margin-bottom: 20px;
            text-align: center;
            color: #34495e;
        }
        .btn-reset {
            background-color: #28a745;
            color: #fff;
        }
        .btn-reset:hover {
            background-color: #218838;
        }

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
<body>

<div class="reset-container">
    <h2><i class="fas fa-key me-2"></i>Restablecer Contraseña</h2>

    <form action="${pageContext.request.contextPath}/ResetPasswordServlet" method="post">
        <input type="hidden" name="token" value="${token}" />

        <div class="mb-3">
    <label for="password" class="form-label">Nueva contraseña</label>
    <div class="d-flex align-items-center position-relative">
        <input type="password" id="password" name="password" required class="form-control pe-5" placeholder="Ingresa tu nueva contraseña">
        <i class="fa-solid fa-eye toggle-password" style="position:absolute; right:15px; cursor:pointer;"></i>
    </div>
</div>

<div class="mb-3">
    <label for="confirmar" class="form-label">Confirmar contraseña</label>
    <div class="d-flex align-items-center position-relative">
        <input type="password" id="confirmar" name="confirmar" required class="form-control pe-5" placeholder="Confirma tu contraseña">
        <i class="fa-solid fa-eye toggle-password" style="position:absolute; right:15px; cursor:pointer;"></i>
    </div>
</div>

        <button type="submit" class="btn btn-reset w-100">Restablecer</button>
    </form>

    <div class="text-center mt-3">
        <a href="${pageContext.request.contextPath}/views/login.jsp" class="text-decoration-none">Volver al login</a>
    </div>
</div>

<!-- Modal de éxito o error -->
<div class="modal fade" id="mensajeModal" tabindex="-1" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content p-3">
      <div class="modal-header">
        <h5 class="modal-title">Notificación</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
      </div>
      <div class="modal-body text-center">
        <c:choose>
            <c:when test="${not empty mensaje}">
                <div class="text-success">${mensaje}</div>
            </c:when>
            <c:when test="${not empty error}">
                <div class="text-danger">${error}</div>
            </c:when>
        </c:choose>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary w-100" data-bs-dismiss="modal">Aceptar</button>
      </div>
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Toggle password
    const togglePasswordIcons = document.querySelectorAll('.toggle-password');
    togglePasswordIcons.forEach(icon => {
        icon.addEventListener('click', () => {
            const input = icon.previousElementSibling;
            if (input.type === 'password') {
                input.type = 'text';
                icon.classList.remove('fa-eye');
                icon.classList.add('fa-eye-slash');
            } else {
                input.type = 'password';
                icon.classList.remove('fa-eye-slash');
                icon.classList.add('fa-eye');
            }
        });
    });

    // Mostrar modal automáticamente si hay mensaje o error
    window.addEventListener('load', function() {
        var mensaje = "${mensaje}";
        var error = "${error}";
        if (mensaje || error) {
            var mensajeModal = new bootstrap.Modal(document.getElementById('mensajeModal'));
            mensajeModal.show();

            // Redirigir al login si hubo éxito
            if (mensaje) {
                var modalEl = document.getElementById('mensajeModal');
                modalEl.addEventListener('hidden.bs.modal', function () {
                    window.location.href = "${pageContext.request.contextPath}/views/login.jsp";
                });
            }
        }
    });
</script>

</body>
</html>
