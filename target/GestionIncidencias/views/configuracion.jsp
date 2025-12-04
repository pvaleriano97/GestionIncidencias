<%-- 
    Document   : configuracion
    Created on : 12 oct 2025, 9:44:33 p.m.
    Author     : acuar
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Configuración - HP Soporte BCP</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/configuracion.css">
</head>
<body>
    <div class="config-container">
        <!-- Sidebar -->
        <div class="sidebar">
            <div class="sidebar-header">
                <h2>HP Soporte BCP</h2>
                <p>Sistema de Tickets</p>
            </div>
            <nav class="sidebar-nav">
                <a href="${pageContext.request.contextPath}/dashboard" class="nav-item">
                    <span>📊</span> Dashboard
                </a>
                <a href="${pageContext.request.contextPath}/nuevo-ticket" class="nav-item">
                    <span>➕</span> Nuevo Ticket
                </a>
                <a href="${pageContext.request.contextPath}/mis-tickets" class="nav-item">
                    <span>🎫</span> Mis Tickets
                </a>
                <a href="${pageContext.request.contextPath}/configuracion" class="nav-item active">
                    <span>⚙️</span> Configuración
                </a>
                <a href="${pageContext.request.contextPath}/logout" class="nav-item">
                    <span>🚪</span> Cerrar Sesión
                </a>
            </nav>
        </div>

        <!-- Main Content -->
        <div class="main-content">
            <!-- Header -->
            <header class="content-header">
                <div class="header-left">
                    <h1>Configuración</h1>
                    <p>Gestiona tu perfil y preferencias</p>
                </div>
                <div class="header-right">
                    <span class="user-info">${emailUsuario}</span>
                    <span class="user-role">${rolUsuario}</span>
                </div>
            </header>

            <!-- Mensajes -->
            <% if (request.getAttribute("error") != null) { %>
                <div class="alert alert-error">
                    <span>❌</span> ${error}
                </div>
            <% } %>
            
            <% if (request.getAttribute("success") != null) { %>
                <div class="alert alert-success">
                    <span>✅</span> ${success}
                </div>
            <% } %>

            <!-- Formulario de Configuración -->
            <div class="config-form-card">
                <form action="${pageContext.request.contextPath}/configuracion" method="post" class="config-form">
                    
                    <!-- Información Personal -->
                    <div class="form-section">
                        <h3>Información Personal</h3>
                        <div class="form-row">
                            <div class="form-group">
                                <label for="nombre">Nombre *</label>
                                <input type="text" id="nombre" name="nombre" value="${nombreUsuario}" required>
                            </div>
                            <div class="form-group">
                                <label for="apellido">Apellido *</label>
                                <input type="text" id="apellido" name="apellido" value="${apellido != null ? apellido : 'Usuario'}" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="email">Email *</label>
                            <input type="email" id="email" name="email" value="${emailUsuario}" required>
                        </div>

                        <div class="form-row">
                            <div class="form-group">
                                <label for="telefono">Teléfono</label>
                                <input type="tel" id="telefono" name="telefono" value="${telefono != null ? telefono : '+51 987 654 321'}" placeholder="+51 XXX XXX XXX">
                            </div>
                            <div class="form-group">
                                <label for="departamento">Departamento</label>
                                <select id="departamento" name="departamento">
                                    <option value="tecnologia" ${departamento == 'tecnologia' ? 'selected' : ''}>Tecnología</option>
                                    <option value="operaciones" ${departamento == 'operaciones' ? 'selected' : ''}>Operaciones</option>
                                    <option value="marketing" ${departamento == 'marketing' ? 'selected' : ''}>Marketing</option>
                                    <option value="finanzas" ${departamento == 'finanzas' ? 'selected' : ''}>Finanzas</option>
                                    <option value="rrhh" ${departamento == 'rrhh' ? 'selected' : ''}>Recursos Humanos</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="form-group">
                                <label for="cargo">Cargo</label>
                                <input type="text" id="cargo" name="cargo" value="${cargo != null ? cargo : 'Ingeniero de Sistemas'}" placeholder="Tu cargo actual">
                            </div>
                            <div class="form-group">
                                <label for="ubicacion">Ubicación</label>
                                <input type="text" id="ubicacion" name="ubicacion" value="${ubicacion != null ? ubicacion : 'Lima, Perú'}" placeholder="Ciudad, País">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="descripcion">Descripción Personal</label>
                            <textarea id="descripcion" name="descripcion" rows="4" placeholder="Describe tu perfil profesional...">${descripcion != null ? descripcion : 'Profesional dedicado al área de tecnología con experiencia en desarrollo de software y gestión de proyectos.'}</textarea>
                        </div>
                    </div>

                    <!-- Cambio de Contraseña -->
                    <div class="form-section">
                        <h3>Cambio de Contraseña</h3>
                        <p class="section-description">Deja estos campos en blanco si no deseas cambiar la contraseña.</p>
                        
                        <div class="form-row">
                            <div class="form-group">
                                <label for="password">Nueva Contraseña</label>
                                <input type="password" id="password" name="password" placeholder="Mínimo 8 caracteres">
                                <div class="password-strength">
                                    <div class="strength-bar"></div>
                                    <span class="strength-text">Seguridad: Baja</span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="confirmPassword">Confirmar Contraseña</label>
                                <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Repite la nueva contraseña">
                            </div>
                        </div>
                    </div>

                    <!-- Botones de Acción -->
                    <div class="form-actions">
                        <button type="submit" class="btn-primary">Guardar Cambios</button>
                        <button type="reset" class="btn-secondary">Cancelar</button>
                        <a href="${pageContext.request.contextPath}/dashboard" class="btn-link">Volver al Dashboard</a>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // Validación de contraseña
            const newPassword = document.getElementById('password');
            const confirmPassword = document.getElementById('confirmPassword');
            const strengthBar = document.querySelector('.strength-bar');
            const strengthText = document.querySelector('.strength-text');

            if (newPassword) {
                newPassword.addEventListener('input', function() {
                    const password = this.value;
                    let strength = 0;
                    
                    if (password.length >= 8) strength += 25;
                    if (/[A-Z]/.test(password)) strength += 25;
                    if (/[0-9]/.test(password)) strength += 25;
                    if (/[^A-Za-z0-9]/.test(password)) strength += 25;
                    
                    strengthBar.style.width = strength + '%';
                    
                    if (strength < 50) {
                        strengthBar.style.background = '#dc3545';
                        strengthText.textContent = 'Seguridad: Débil';
                    } else if (strength < 75) {
                        strengthBar.style.background = '#ffc107';
                        strengthText.textContent = 'Seguridad: Media';
                    } else {
                        strengthBar.style.background = '#28a745';
                        strengthText.textContent = 'Seguridad: Fuerte';
                    }
                });
            }

            // Validación del formulario
            document.querySelector('.config-form').addEventListener('submit', function(e) {
                const newPassword = document.getElementById('password');
                const confirmPassword = document.getElementById('confirmPassword');
                
                if (newPassword && confirmPassword && newPassword.value !== confirmPassword.value) {
                    e.preventDefault();
                    alert('Las contraseñas no coinciden');
                    return;
                }
                
                if (newPassword && newPassword.value && newPassword.value.length < 8) {
                    e.preventDefault();
                    alert('La contraseña debe tener al menos 8 caracteres');
                    return;
                }
            });
        });
    </script>
</body>
</html>