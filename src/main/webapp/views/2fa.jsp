<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Verificación 2FA</title>

    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">

    <!-- FontAwesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>

<body class="bg-light">

<div class="container mt-5">

    <c:if test="${show2FA == true}">
        <div class="card shadow mx-auto" style="max-width:450px;">
            <div class="card-body text-center">

                <h3 class="mb-3">
                    <i class="fa-solid fa-lock me-2"></i> Verificación 2FA
                </h3>

                <p>Hola, <strong>${nombre}</strong></p>
                <p>Escanea este código QR en Google Authenticator:</p>

                <!-- QR generado desde backend -->
                <img src="${qrUrl}"
                     alt="QR Authenticator"
                     class="img-thumbnail mb-3"
                     width="250">

                <!-- FORMULARIO PARA VERIFICAR CÓDIGO -->
                <form action="${pageContext.request.contextPath}/LoginServlet" method="post">

                    <label class="fw-bold mb-2">Ingrese el código de 6 dígitos</label>
                    <input type="text"
                           name="codigo2FA"
                           maxlength="6"
                           class="form-control mb-3 text-center"
                           placeholder="123456"
                           required>

                    <button class="btn btn-primary w-100">
                        <i class="fa-solid fa-check me-2"></i> Verificar
                    </button>
                </form>

                <!-- MENSAJE DE ERROR 2FA -->
                <c:if test="${not empty error2FA}">
                    <div class="alert alert-danger mt-3">${error2FA}</div>
                </c:if>

            </div>
        </div>
    </c:if>

</div>

</body>
</html>
