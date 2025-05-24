<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${param.title} - Hospital Carlos Lafranco La Hoz</title>
    
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <!-- Tus estilos personalizados -->
    <link href="assets/css/styles.css" rel="stylesheet">
    
    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary ">
        <div class="container">
            <a class="navbar-brand" href="index">Hospital Carlos Lafranco La Hoz</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="index">Inicio</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="doctores.jsp">Doctores</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="citas.jsp">Mis Citas</a>
                    </li>
                </ul>
                <div class="navbar-nav">
                    <c:choose>
                        <c:when test="${not empty sessionScope.usuario}">
                            <span class="nav-item nav-link text-white">
                                Bienvenido, ${sessionScope.usuario.nombre}
                            </span>
                            <a class="nav-link" href="perfil.jsp">Mi Perfil</a>
                            <a class="nav-link" href="logout">Cerrar Sesión</a>
                        </c:when>
                        <c:otherwise>
                            <a class="nav-link" href="login.jsp">Iniciar Sesión</a>
                            <a class="nav-link" href="registro.jsp">Registrarse</a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </nav>
</body>
</html>