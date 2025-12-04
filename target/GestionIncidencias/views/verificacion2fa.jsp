<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Verificación en Dos Pasos</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            padding: 20px;
        }
        .verification-card {
            background: white;
            border-radius: 20px;
            padding: 40px;
            width: 100%;
            max-width: 450px;
            box-shadow: 0 20px 60px rgba(0,0,0,0.3);
            border: none;
        }
        .icon-2fa {
            font-size: 50px;
            text-align: center;
            margin-bottom: 20px;
            color: #667eea;
        }
        .user-info {
            background: #f8f9fa;
            padding: 15px;
            border-radius: 10px;
            margin: 20px 0;
            text-align: center;
            border-left: 4px solid #667eea;
        }
        .code-input {
            width: 50px;
            height: 60px;
            text-align: center;
            font-size: 24px;
            font-weight: bold;
            border: 2px solid #dee2e6;
            border-radius: 10px;
            margin: 0 5px;
            transition: all 0.3s;
        }
        .code-input:focus {
            border-color: #667eea;
            box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.25);
            transform: translateY(-2px);
            outline: none;
        }
        .btn-verify {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border: none;
            color: white;
            padding: 14px;
            width: 100%;
            border-radius: 10px;
            font-size: 16px;
            font-weight: 600;
            transition: all 0.3s;
            margin-top: 20px;
        }
        .btn-verify:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 20px rgba(102, 126, 234, 0.3);
        }
        .btn-verify:active {
            transform: translateY(0);
        }
        .form-text {
            font-size: 14px;
            color: #6c757d;
            margin-top: 10px;
        }
        .link-cancel {
            color: #6c757d;
            text-decoration: none;
            transition: color 0.3s;
        }
        .link-cancel:hover {
            color: #dc3545;
        }
        .input-group-codes {
            justify-content: center;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <div class="verification-card">
        <div class="icon-2fa">
            🔐
        </div>
        <h2 class="text-center mb-3" style="color: #333;">Verificación en Dos Pasos</h2>
        
        <%
            // OBTENER DATOS DIRECTAMENTE DE LA SESIÓN
            String correoSesion = (String) session.getAttribute("correoPendiente");
            String nombreSesion = (String) session.getAttribute("nombrePendiente");
            
            if (correoSesion == null) {
                // Redirigir al login si no hay sesión
                response.sendRedirect(request.getContextPath() + "/LoginServlet?error=Sesión expirada, por favor inicia sesión nuevamente");
                return;
            }
        %>
        
        <div class="user-info">
            <h5 class="mb-1" style="color: #333;"><%= nombreSesion != null ? nombreSesion : "Usuario" %></h5>
            <p class="mb-0" style="color: #6c757d; font-size: 14px;"><%= correoSesion %></p>
        </div>
        
        <p class="text-center mb-4" style="color: #495057;">
            Por favor, ingresa el código de 6 dígitos enviado a tu sesión
        </p>
        
        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert" style="border-radius: 10px;">
                ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        <% } %>
        
        <!-- FORMULARIO MEJORADO -->
        <form action="${pageContext.request.contextPath}/LoginServlet" method="post" id="verificationForm">
            <!-- CAMPO OCULTO CON EL CORREO -->
            <input type="hidden" name="correo" value="<%= correoSesion %>">
            
            <!-- Campo único para el código (más simple) -->
            <div class="mb-4">
                <label for="codigoCompleto" class="form-label" style="font-weight: 500; color: #333;">
                    Código de 6 dígitos
                </label>
                <input type="text" 
                       id="codigoCompleto" 
                       name="codigo2fa" 
                       class="form-control text-center" 
                       style="font-size: 24px; letter-spacing: 15px; height: 60px; border-radius: 10px; font-weight: bold;"
                       maxlength="6" 
                       pattern="[0-9]{6}" 
                       placeholder="000000" 
                       required 
                       autofocus
                       autocomplete="off"
                       onkeypress="return (event.charCode >= 48 && event.charCode <= 57)">
                <div class="form-text text-center">
                    Ingresa los 6 dígitos sin espacios
                </div>
            </div>
            
            <button type="submit" class="btn-verify">
                <span style="font-size: 18px; margin-right: 8px;">✓</span>
                Verificar y Continuar
            </button>
        </form>
        
        <div class="text-center mt-4">
            <a href="${pageContext.request.contextPath}/LoginServlet" 
               class="link-cancel">
                ← Volver al inicio de sesión
            </a>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Enfocar automáticamente el campo de código
        document.getElementById('codigoCompleto').focus();
        
        // Solo permitir números y manejar entrada
        document.getElementById('codigoCompleto').addEventListener('input', function(e) {
            // Solo permitir números
            this.value = this.value.replace(/[^0-9]/g, '');
            
            // Limitar a 6 dígitos
            if (this.value.length > 6) {
                this.value = this.value.substring(0, 6);
            }
            
            // Cambiar estilo cuando esté completo
            if (this.value.length === 6) {
                this.style.borderColor = '#28a745';
                this.style.backgroundColor = '#f8fff9';
            } else {
                this.style.borderColor = '#dee2e6';
                this.style.backgroundColor = '';
            }
        });
        
        // Permitir navegación con teclas
        document.getElementById('codigoCompleto').addEventListener('keydown', function(e) {
            // Permitir Ctrl+V, Ctrl+C, Ctrl+X, flechas, borrar, etc.
            if (e.ctrlKey && (e.key === 'v' || e.key === 'c' || e.key === 'x')) {
                return true;
            }
            
            // Permitir teclas de navegación
            if (['ArrowLeft', 'ArrowRight', 'Backspace', 'Delete', 'Tab'].includes(e.key)) {
                return true;
            }
            
            // Solo permitir números
            if (!(e.key >= '0' && e.key <= '9')) {
                e.preventDefault();
                return false;
            }
        });
        
        // Manejar pegado de código
        document.getElementById('codigoCompleto').addEventListener('paste', function(e) {
            e.preventDefault();
            const pasted = (e.clipboardData || window.clipboardData).getData('text');
            const numeros = pasted.replace(/[^0-9]/g, '');
            
            if (numeros.length >= 6) {
                this.value = numeros.substring(0, 6);
                // Cambiar estilo
                this.style.borderColor = '#28a745';
                this.style.backgroundColor = '#f8fff9';
            } else {
                this.value = numeros;
            }
        });
        
        // Efecto sutil al enfocar el botón
        const btn = document.querySelector('.btn-verify');
        btn.addEventListener('mouseenter', function() {
            this.style.transform = 'translateY(-2px)';
        });
        
        btn.addEventListener('mouseleave', function() {
            this.style.transform = 'translateY(0)';
        });
        
        // Validación simple al enviar
        document.getElementById('verificationForm').addEventListener('submit', function(e) {
            const codigoInput = document.getElementById('codigoCompleto');
            
            if (codigoInput.value.length !== 6) {
                e.preventDefault();
                alert('Por favor, ingresa los 6 dígitos del código.');
                codigoInput.focus();
                return false;
            }
            
            // Deshabilitar botón durante el envío
            btn.disabled = true;
            btn.innerHTML = '<span class="spinner-border spinner-border-sm me-2"></span> Verificando...';
            
            return true;
        });
    </script>
</body>
</html>