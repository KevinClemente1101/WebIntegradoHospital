<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<!-- Font Awesome -->
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
<!-- Tu CSS personalizado -->
<link href="${pageContext.request.contextPath}/assets/css/styles.css" rel="stylesheet">
<!-- Navbar principal -->
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/index">Hospital Carlos Lafranco La Hoz</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/index">Inicio</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/doctores">Doctores</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/usuario/citas">Mis Citas</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/nosotros">Nosotros</a>
                </li>
            </ul>
            <div class="navbar-nav">
                <c:choose>
                    <c:when test="${not empty sessionScope.usuario}">
                        <span class="nav-item nav-link text-white">
                            Bienvenido, ${sessionScope.usuario.nombre}
                        </span>
                        <a class="nav-link" href="${pageContext.request.contextPath}/perfil.jsp">Mi Perfil</a>
                        <a class="nav-link" href="${pageContext.request.contextPath}/logout">Cerrar Sesión</a>
                    </c:when>
                    <c:otherwise>
                        <a class="nav-link" href="${pageContext.request.contextPath}/login.jsp">Iniciar Sesión</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</nav>