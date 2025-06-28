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
   :root {
    --color-principal: #4FC3F7;
    --color-secundario: #29B6F6;
    --color-acento: #0288D1;
    --color-claro: #E1F5FE;
    --color-texto: #263238;
}

.navbar {
    background-color: var(--color-principal) !important;
    box-shadow: 0 2px 20px rgba(79, 195, 247, 0.3);
}

/* FONDO PRINCIPAL CAMBIADO A BLANCO */
body {
    background: #ffffff; /* Cambiado a blanco puro */
    min-height: 100vh;
    margin: 0;
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    position: relative;
}

/* ELIMINAMOS EL OVERLAY DE GRADIENTES AZULES */
/* body::before eliminado para mantener el fondo completamente blanco */

.center-wrapper {
    min-height: calc(100vh - 70px);
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 20px;
}

.login-container {
    background: linear-gradient(145deg, #ffffff 0%, #f8fffe 100%);
    padding: 3rem 2.5rem;
    border-radius: 20px;
    width: 400px;
    min-height: 520px;
    box-shadow: 
        0 20px 40px rgba(79, 195, 247, 0.15),
        0 10px 20px rgba(41, 182, 246, 0.1),
        inset 0 1px 0 rgba(255, 255, 255, 0.9);
    border: 1px solid rgba(79, 195, 247, 0.2);
    position: relative;
    overflow: hidden;
    backdrop-filter: blur(10px);
    transition: all 0.3s ease;
}

.login-container:hover {
    transform: translateY(-5px);
    box-shadow: 
        0 30px 60px rgba(79, 195, 247, 0.2),
        0 15px 30px rgba(41, 182, 246, 0.15),
        inset 0 1px 0 rgba(255, 255, 255, 0.9);
}

.login-container::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    height: 4px;
    background: linear-gradient(90deg, var(--color-principal), var(--color-secundario), var(--color-acento));
    border-radius: 20px 20px 0 0;
}

.login-title {
    color: var(--color-texto);
    font-weight: 700;
    text-align: center;
    margin-bottom: 35px;
    font-size: 2.2rem;
    letter-spacing: 0.5px;
    background: linear-gradient(135deg, var(--color-acento), var(--color-principal));
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    background-clip: text;
    position: relative;
}

.login-title::after {
    content: '';
    position: absolute;
    bottom: -10px;
    left: 50%;
    transform: translateX(-50%);
    width: 60px;
    height: 3px;
    background: linear-gradient(90deg, var(--color-principal), var(--color-secundario));
    border-radius: 2px;
}

.form-control {
    border: 2px solid rgba(79, 195, 247, 0.2);
    border-radius: 12px;
    background: rgba(255, 255, 255, 0.9);
    font-size: 1.1rem;
    margin-bottom: 25px;
    padding: 15px 15px 15px 50px;
    transition: all 0.3s ease;
    box-shadow: 0 2px 10px rgba(79, 195, 247, 0.1);
}

.form-control:focus {
    border-color: var(--color-principal);
    box-shadow: 
        0 0 0 0.25rem rgba(79, 195, 247, 0.25),
        0 5px 15px rgba(79, 195, 247, 0.2);
    background: #ffffff;
    outline: none;
    transform: translateY(-2px);
}

.form-control::placeholder {
    color: rgba(38, 50, 56, 0.6);
    font-weight: 400;
}

.input-icon {
    position: absolute;
    left: 15px;
    top: 50%;
    transform: translateY(-50%);
    font-size: 1.2rem;
    color: var(--color-principal);
    transition: all 0.3s ease;
    z-index: 10;
}

.form-group {
    position: relative;
    margin-bottom: 20px;
}

.form-group:focus-within .input-icon {
    color: var(--color-acento);
    transform: translateY(-50%) scale(1.1);
}

.form-check {
    margin-bottom: 25px;
}

.form-check-input {
    border: 2px solid var(--color-principal);
    border-radius: 6px;
}

.form-check-input:checked {
    background-color: var(--color-principal);
    border-color: var(--color-principal);
}

.form-check-label {
    color: var(--color-texto);
    font-size: 0.95rem;
    font-weight: 500;
    margin-left: 8px;
}

.btn-login {
    width: 100%;
    background: linear-gradient(135deg, var(--color-principal) 0%, var(--color-secundario) 100%);
    color: #ffffff;
    font-weight: 600;
    border: none;
    border-radius: 12px;
    padding: 15px;
    margin-top: 10px;
    margin-bottom: 25px;
    font-size: 1.1rem;
    transition: all 0.3s ease;
    box-shadow: 0 4px 15px rgba(79, 195, 247, 0.3);
    position: relative;
    overflow: hidden;
}

.btn-login:hover {
    background: linear-gradient(135deg, var(--color-secundario) 0%, var(--color-acento) 100%);
    transform: translateY(-2px);
    box-shadow: 0 8px 25px rgba(79, 195, 247, 0.4);
}

.btn-login:active {
    transform: translateY(0);
    box-shadow: 0 2px 10px rgba(79, 195, 247, 0.3);
}

.btn-login::before {
    content: '';
    position: absolute;
    top: 0;
    left: -100%;
    width: 100%;
    height: 100%;
    background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.2), transparent);
    transition: left 0.5s;
}

.btn-login:hover::before {
    left: 100%;
}

.forgot-link {
    color: var(--color-acento);
    font-weight: 600;
    text-align: center;
    display: block;
    margin-bottom: 20px;
    text-decoration: none;
    font-size: 0.95rem;
    transition: all 0.3s ease;
}

.forgot-link:hover {
    color: var(--color-secundario);
    text-decoration: none;
    transform: translateY(-1px);
}

.create-account-text {
    color: var(--color-texto);
    text-align: center;
    margin-bottom: 15px;
    font-size: 0.95rem;
    font-weight: 500;
}

.btn-create {
    width: 100%;
    background: linear-gradient(135deg, #ffffff 0%, #f8fffe 100%);
    color: var(--color-acento);
    font-weight: 600;
    border: 2px solid var(--color-principal);
    border-radius: 12px;
    padding: 12px;
    font-size: 1rem;
    transition: all 0.3s ease;
    box-shadow: 0 2px 10px rgba(79, 195, 247, 0.1);
}

.btn-create:hover {
    background: linear-gradient(135deg, var(--color-claro) 0%, #ffffff 100%);
    border-color: var(--color-secundario);
    color: var(--color-acento);
    transform: translateY(-2px);
    box-shadow: 0 5px 20px rgba(79, 195, 247, 0.2);
}

.btn-create:active {
    transform: translateY(0);
}

/* Animaciones suaves */
@keyframes fadeInUp {
    from {
        opacity: 0;
        transform: translateY(30px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

.login-container {
    animation: fadeInUp 0.6s ease-out;
}

/* Responsive */
@media (max-width: 480px) {
    .login-container {
        width: 100%;
        margin: 0 20px;
        padding: 2rem 1.5rem;
    }
    
    .login-title {
        font-size: 1.8rem;
    }
}
</style>
<body>
    <div class="center-wrapper">
        <div class="login-container">
            <div class="login-title">BIENVENIDO(A)</div>
            <% if(request.getAttribute("error") != null) { %>
                <div class="alert alert-danger" style="background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; border-radius: 5px; padding: 10px; margin-bottom: 15px; text-align: center;">
                    <i class="bi bi-exclamation-triangle-fill"></i> <%= request.getAttribute("error") %>
                </div>
            <% } %>
            <form action="login" method="post">
                <div class="form-group mb-3">
                    <span class="input-icon"><i class="bi bi-person"></i></span>
                    <input type="text" class="form-control" name="dni" placeholder="Número de Documento" required>
                </div>
                <div class="form-group mb-3">
                    <span class="input-icon"><i class="bi bi-person-circle"></i></span>
                    <input type="password" class="form-control" name="password" placeholder="Contraseña" required>
                </div>
                <div class="form-check mb-3">
                    <input class="form-check-input" type="checkbox" id="recordarCuenta" name="recordarCuenta">
                    <label class="form-check-label" for="recordarCuenta">Recordar Cuenta</label>
                </div>
                <button type="submit" class="btn btn-login">Ingresar</button>
            </form>
            <a href="#" class="forgot-link">¿Olvidaste tu Contraseña?</a>
            <div class="create-account-text">¿Aun no tienes cuenta?</div>
            <a href="registro.jsp" class="btn btn-create">Crear Cuenta</a>
        </div>
    </div>
</body>
</html>


