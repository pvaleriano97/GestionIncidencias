<%-- 
    Document   : dashboard
    Created on : 12 oct 2025, 8:35:59 p.m.
    Author     : acuar
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Panel de Control - HP Soporte BCP</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    </head>
    <body>
        <div class="dashboard-container">
            <!-- Sidebar -->
            <div class="sidebar">
                <div class="sidebar-header">
                    <h2>HP Soporte BCP</h2>
                    <p>Sistema de Tickets</p>
                </div>
                <nav class="sidebar-nav">
                    <a href="${pageContext.request.contextPath}/dashboard" class="nav-item active">
                        <span>📊</span> Dashboard
                    </a>
                    <a href="${pageContext.request.contextPath}/nuevo-ticket" class="nav-item">
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
                        <h1>Panel de control</h1>
                        <p>Bienvenido, ${sessionScope.name}</p>
                    </div>
                    <div class="header-right">
                        <span class="user-info">${sessionScope.email}</span>
                        <span class="user-role">${sessionScope.role}</span>
                    </div>
                </header>

                <!-- Stats Grid -->
                <div class="stats-grid">
                    <div class="stat-card">
                        <div class="stat-icon">🎫</div>
                        <div class="stat-info">
                            <h3>45</h3>
                            <p>Tickets Activos</p>
                        </div>
                    </div>
                    <div class="stat-card">
                        <div class="stat-icon">✅</div>
                        <div class="stat-info">
                            <h3>128</h3>
                            <p>Resueltos Hoy</p>
                        </div>
                    </div>
                    <div class="stat-card">
                        <div class="stat-icon">⏱️</div>
                        <div class="stat-info">
                            <h3>12</h3>
                            <p>En Espera</p>
                        </div>
                    </div>
                    <div class="stat-card">
                        <div class="stat-icon">📈</div>
                        <div class="stat-info">
                            <h3>94%</h3>
                            <p>Satisfacción</p>
                        </div>
                    </div>
                </div>

                <!-- Content Grid -->
                <div class="content-grid">
                    <!-- Últimos Tickets -->
                    <div class="content-card">
                        <div class="card-header">
                            <h3>Últimos Tickets</h3>
                            <a href="#" class="view-all">Mostrar todos los tickets</a>
                        </div>
                        <div class="tickets-list">
                            <div class="ticket-item">
                                <div class="ticket-avatar">FV</div>
                                <div class="ticket-info">
                                    <h4>Francisco Vivo</h4>
                                    <p>Error en aplicativo BPM</p>
                                </div>
                                <div class="ticket-meta">
                                    <span class="time">Hace 5:20 PM</span>
                                    <span class="priority high">Alta</span>
                                </div>
                            </div>
                            <div class="ticket-item">
                                <div class="ticket-avatar">BM</div>
                                <div class="ticket-info">
                                    <h4>Benjamin Mendoza</h4>
                                    <p>Instalar Software Gui</p>
                                </div>
                                <div class="ticket-meta">
                                    <span class="time">Hace 3:20 PM</span>
                                    <span class="priority medium">Media</span>
                                </div>
                            </div>
                            <div class="ticket-item">
                                <div class="ticket-avatar">HC</div>
                                <div class="ticket-info">
                                    <h4>Hugo Chavez</h4>
                                    <p>Solicitar acceso a grupo de red externa</p>
                                </div>
                                <div class="ticket-meta">
                                    <span class="time">Hace 3:20 PM</span>
                                    <span class="priority low">Baja</span>
                                </div>
                            </div>
                            <div class="ticket-item">
                                <div class="ticket-avatar">JR</div>
                                <div class="ticket-info">
                                    <h4>Jorge Roca</h4>
                                    <p>Renovación de equipo Laptop</p>
                                </div>
                                <div class="ticket-meta">
                                    <span class="time">Hace 1:20 PM</span>
                                    <span class="priority medium">Media</span>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Empleado más activo -->
                    <div class="content-card">
                        <div class="card-header">
                            <h3>Empleado más activo</h3>
                            <a href="#" class="view-all">Mostrar todo</a>
                        </div>
                        <div class="ranking-list">
                            <div class="rank-item">
                                <div class="rank-avatar">JR</div>
                                <div class="rank-info">
                                    <h4>Jorge Roca</h4>
                                    <p>1400 puntos</p>
                                </div>
                                <div class="rank-percentage">99%</div>
                            </div>
                            <div class="rank-item">
                                <div class="rank-avatar">BM</div>
                                <div class="rank-info">
                                    <h4>Benjamin Mendoza</h4>
                                    <p>1320 puntos</p>
                                </div>
                                <div class="rank-percentage">80%</div>
                            </div>
                            <div class="rank-item">
                                <div class="rank-avatar">FV</div>
                                <div class="rank-info">
                                    <h4>Francisco Vivo</h4>
                                    <p>930 puntos</p>
                                </div>
                                <div class="rank-percentage">50%</div>
                            </div>
                        </div>
                    </div>

                    <!-- Cliente más activo -->
                    <div class="content-card">
                        <div class="card-header">
                            <h3>Cliente más Activo</h3>
                            <a href="#" class="view-all">Mostrar todo</a>
                        </div>
                        <div class="clients-list">
                            <div class="client-item">
                                <div class="client-logo">🏦</div>
                                <div class="client-info">
                                    <h4>BCP</h4>
                                    <p>60 tickets</p>
                                </div>
                            </div>
                            <div class="client-item">
                                <div class="client-logo">🛒</div>
                                <div class="client-info">
                                    <h4>Yape</h4>
                                    <p>51 tickets</p>
                                </div>
                            </div>
                            <div class="client-item">
                                <div class="client-logo">📱</div>
                                <div class="client-info">
                                    <h4>Yape</h4>
                                    <p>20 tickets</p>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Rendimiento de departamento -->
                    <div class="content-card full-width">
                        <div class="card-header">
                            <h3>Rendimiento de departamento</h3>
                        </div>
                        <div class="performance-chart">
                            <div class="chart-placeholder">
                                <p>📊 Gráfico de rendimiento se mostrará aquí</p>
                                <p>Este espacio está reservado para gráficos y métricas avanzadas</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>