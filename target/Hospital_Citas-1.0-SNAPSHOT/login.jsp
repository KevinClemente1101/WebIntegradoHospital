<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="WEB-INF/header.jsp">
    <jsp:param name="title" value="Login"/>
</jsp:include>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - Hospital La Hoz</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <!-- Iconos de Bootstrap -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">

</head>
<style>
    .navbar {
    background-color: var(--color-principal) !important;
}
    body {
        background: white;
        min-height: 100vh;
        margin: 0;
    }
    .center-wrapper {
        min-height: calc(100vh - 70px);
        display: flex;
        align-items: center;
        justify-content: center;
    }
    .login-container {
        background: #399fad;
        padding: 2rem;
        border-radius: 10px;
        width:  450px;
        height: 500px;
    }
    .login-title {
        color: #fff;
        font-weight: bold;
        text-align: center;
        margin-bottom: 30px;
        font-size: 2rem;
        letter-spacing: 1px;
    }
    .form-control {
        border: none;
        border-bottom: 3px solid #222;
        border-radius: 0;
        background: #fff;
        font-size: 1.1rem;
        margin-bottom: 20px;
        padding-left: 40px;
    }
    .input-icon {
        position: absolute;
        left: 10px;
        top: 50%;
        transform: translateY(-50%);
        font-size: 1.3rem;
        color: #222;
        pointer-events: none;
    }
    .form-group {
        position: relative;
    }
    .form-check-label {
        color: #111;
        font-size: 1rem;
    }
    .btn-login {
        width: 100%;
        background: #fff;
        color: #222;
        font-weight: bold;
        border-radius: 25px;
        margin-top: 10px;
        margin-bottom: 20px;
        font-size: 1.1rem;
    }
    .forgot-link {
        color: #fff;
        font-weight: bold;
        text-align: center;
        display: block;
        margin-bottom: 10px;
        text-decoration: none;
    }
    .forgot-link:hover {
        text-decoration: underline;
    }
    .create-account-text {
        color: #fff;
        text-align: center;
        margin-bottom: 10px;
    }
    .btn-create {
        width: 100%;
        background: #ccc;
        color: #222;
        font-weight: bold;
        border-radius: 25px;
        font-size: 1.1rem;
    }
</style>
<body>
    <div class="center-wrapper">
        <div class="login-container">
            <div class="login-title">BIENVENIDO(A)</div>
            <form action="login" method="post">
                <div class="form-group mb-3">
                    <span class="input-icon"><i class="bi bi-person"></i></span>
                    <input type="text" class="form-control" name="dni" placeholder="Número de Documento" required>
                </div>
                <div class="form-group mb-3">
                    <span class="input-icon"><i class="bi bi-key"></i></i></span>
                    <input type="password" class="form-control" name="password" placeholder="Contraseña" required>
                </div>
                <div class="form-check mb-3">
                    <input class="form-check-input" type="checkbox" id="recordarCuenta" name="recordarCuenta">
                    <label class="form-check-label" for="recordarCuenta">Recordar Cuenta</label>
                </div>
                <button type="submit" class="btn btn-login">Ingresar</button>
            </form>
            <a href="#" class="forgot-link">¿Olvidaste tu Contraseña?</a>
            <div class="create-account-text">¿Aun no tienes cuenta? <span><a href="registro.jsp" class="text-white">Crear Cuenta</a></span></div>
            
        </div>
    </div>
    
    <!-- Bootstrap -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>


