<%-- 
    Document   : nuevo-ticket
    Created on : 12 oct 2025, 9:03:53 p.m.
    Author     : acuar
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nuevo Ticket - HP Soporte BCP</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/nuevo-ticket.css">
</head>
<body>
    <div class="ticket-container">
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
                <a href="${pageContext.request.contextPath}/nuevo-ticket" class="nav-item active">
                    <span>➕</span> Nuevo Ticket
                </a>
                <a href="${pageContext.request.contextPath}/mis-tickets" class="nav-item">
                    <span>🎫</span> Mis Tickets
                </a>
                <a href="${pageContext.request.contextPath}/configuracion" class="nav-item">
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
                    <h1>Realiza tu solicitud del ticket</h1>
                    <p>Complete el formulario para crear un nuevo ticket de soporte</p>
                </div>
                <div class="header-right">
                    <span class="user-info">${sessionScope.email}</span>
                    <span class="user-role">${sessionScope.role}</span>
                </div>
            </header>

            <div class="content-grid">
                <!-- Formulario -->
                <div class="form-section">
                    <form action="${pageContext.request.contextPath}/nuevo-ticket" method="post" class="ticket-form">
                        
                        <!-- Título del Ticket -->
                        <div class="form-group">
                            <label for="titulo">Título del Ticket*</label>
                            <input type="text" id="titulo" name="titulo" placeholder="Describe brevemente el problema" required>
                        </div>

                        <!-- Descripción -->
                        <div class="form-group">
                            <label for="descripcion">Descripción Detallada*</label>
                            <textarea id="descripcion" name="descripcion" rows="5" placeholder="Describe el problema en detalle..." required></textarea>
                        </div>

                        <div class="form-row">
                            <!-- Categoría -->
                            <div class="form-group">
                                <label for="categoria">Categoría*</label>
                                <select id="categoria" name="categoria" required>
                                    <option value="">Seleccione una categoría</option>
                                    <option value="accesos">Accesos</option>
                                    <option value="hardware">Hardware</option>
                                    <option value="software">Software</option>
                                    <option value="redes">Redes</option>
                                    <option value="impresoras">Impresoras</option>
                                    <option value="aplicaciones">Aplicaciones</option>
                                </select>
                            </div>

                            <!-- Prioridad -->
                            <div class="form-group">
                                <label for="prioridad">Prioridad*</label>
                                <select id="prioridad" name="prioridad" required>
                                    <option value="">Seleccione prioridad</option>
                                    <option value="baja">Baja</option>
                                    <option value="media">Media</option>
                                    <option value="alta">Alta</option>
                                    <option value="urgente">Urgente</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-row">
                            <!-- Empresa -->
                            <div class="form-group">
                                <label for="empresa">Empresa*</label>
                                <select id="empresa" name="empresa" required>
                                    <option value="">Seleccione empresa</option>
                                    <option value="bcp">BCP</option>
                                    <option value="yape">Yape</option>
                                    <option value="otros">Otros</option>
                                </select>
                            </div>

                            <!-- Departamento -->
                            <div class="form-group">
                                <label for="departamento">Departamento*</label>
                                <select id="departamento" name="departamento" required>
                                    <option value="">Seleccione departamento</option>
                                    <option value="tecnologia">Tecnología</option>
                                    <option value="operaciones">Operaciones</option>
                                    <option value="marketing">Marketing</option>
                                    <option value="finanzas">Finanzas</option>
                                    <option value="rrhh">Recursos Humanos</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-row">
                            <!-- Fecha de inicio -->
                            <div class="form-group">
                                <label for="fechaInicio">Fecha de inicio*</label>
                                <input type="date" id="fechaInicio" name="fechaInicio" required>
                            </div>

                            <!-- Fecha de fin -->
                            <div class="form-group">
                                <label for="fechaFin">Fecha de fin estimada</label>
                                <input type="date" id="fechaFin" name="fechaFin">
                            </div>
                        </div>

                        <!-- Botones -->
                        <div class="form-actions">
                            <button type="submit" class="btn-primary">Crear Ticket</button>
                            <a href="${pageContext.request.contextPath}/dashboard" class="btn-secondary">Cancelar</a>
                        </div>
                    </form>
                </div>

                <!-- Panel de consejos -->
                <div class="tips-section">
                    <div class="tips-card">
                        <h3>Consejo de la solicitud</h3>
                        <div class="tips-content">
                            <p>• Proporcione una descripción clara y detallada del problema</p>
                            <p>• Incluya pasos específicos para reproducir el issue</p>
                            <p>• Adjunte capturas de pantalla si es posible</p>
                            <p>• Especifique el impacto en sus operaciones</p>
                        </div>
                    </div>

                    <!-- Información de contacto -->
                    <div class="contact-card">
                        <h3>Soporte Rápido</h3>
                        <div class="contact-info">
                            <p><strong>📞 Teléfono:</strong> (01) 123-4567</p>
                            <p><strong>✉️ Email:</strong> soporte@hp.com</p>
                            <p><strong>🕒 Horario:</strong> Lun-Vie 9:00-18:00  Sabado 9:00-13:00</p>
                            
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        // Establecer fecha mínima como hoy
        document.getElementById('fechaInicio').min = new Date().toISOString().split('T')[0];
        
        // Actualizar fecha fin cuando cambie fecha inicio
        document.getElementById('fechaInicio').addEventListener('change', function() {
            document.getElementById('fechaFin').min = this.value;
        });

        // Validación del formulario
        document.querySelector('.ticket-form').addEventListener('submit', function(e) {
            const titulo = document.getElementById('titulo').value.trim();
            const descripcion = document.getElementById('descripcion').value.trim();
            
            if (titulo.length < 5) {
                e.preventDefault();
                alert('El título debe tener al menos 5 caracteres');
                return;
            }
            
            if (descripcion.length < 10) {
                e.preventDefault();
                alert('La descripción debe tener al menos 10 caracteres');
                return;
            }
        });
    </script>
</body>
</html>