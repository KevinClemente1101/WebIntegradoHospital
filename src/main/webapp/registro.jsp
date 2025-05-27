<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Crear Cuenta</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="assets/css/styles.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        .center-wrapper {
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .register-box {
            background: #399fad;
            border-radius: 8px;
            padding: 40px 30px 30px 30px;
            width: 350px;
            box-shadow: 0 4px 16px rgba(0,0,0,0.08);
        }
        .register-title {
            color: #fff;
            font-weight: bold;
            text-align: center;
            margin-bottom: 30px;
            font-size: 1.5rem;
            letter-spacing: 1px;
        }
        .form-group {
            position: relative;
            margin-bottom: 22px;
        }
        .input-icon {
            position: absolute;
            left: 10px;
            top: 10px;
            font-size: 1.3rem;
            color: #222;
        }
        .form-control {
            border: none;
            border-bottom: 3px solid #222;
            border-radius: 0;
            background: #fff;
            font-size: 1.1rem;
            padding-left: 40px;
            box-shadow: none;
        }
        .btn-register {
            width: 100%;
            background: #fff;
            color: #222;
            font-weight: bold;
            border-radius: 25px;
            margin-top: 10px;
            margin-bottom: 20px;
            font-size: 1.1rem;
        }
        .btn-login {
            width: 100%;
            background: #f8f8f8;
            color: #222;
            font-weight: bold;
            border-radius: 25px;
            font-size: 1.1rem;
        }
        .login-link {
            color: #fff;
            text-align: center;
            display: block;
            margin-bottom: 10px;
            text-decoration: none;
        }
        .login-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="center-wrapper">
    <div class="register-box">
        <div class="register-title">CREAR CUENTA</div>
        <% if(request.getAttribute("error") != null) { %>
            <div class="alert alert-danger">
                <%= request.getAttribute("error") %>
            </div>
        <% } %>
        <form action="verificar-correo" method="post" autocomplete="off">
            <div class="form-group">
                <span class="input-icon"><i class="fa fa-user"></i></span>
                <input type="text" class="form-control" name="nombre" placeholder="Nombre" required>
            </div>
            <div class="form-group">
                <span class="input-icon"><i class="fa fa-user"></i></span>
                <input type="text" class="form-control" name="apellido" placeholder="Apellido" required>
            </div>
            <div class="form-group">
                <span class="input-icon"><i class="fa fa-user"></i></span>
                <input type="text" class="form-control" name="dni" placeholder="Número de Documento" pattern="[0-9]{8}" maxlength="8" required>
            </div>
            <div class="form-group">
                <span class="input-icon"><i class="fa fa-calendar"></i></span>
                <input type="date" class="form-control" name="fecha_nacimiento" placeholder="Fecha de Nacimiento" required>
            </div>
            <div class="form-group">
                <span class="input-icon"><i class="fa fa-at"></i></span>
                <input type="email" class="form-control" name="email" placeholder="Correo" required>
            </div>
            <div class="form-group">
                <span class="input-icon"><i class="fa fa-whatsapp"></i></span>
                <input type="text" class="form-control" name="telefono" placeholder="Celular">
            </div>
            <div class="form-group">
                <span class="input-icon"><i class="fa fa-user-circle"></i></span>
                <input type="password" class="form-control" name="password" placeholder="Contraseña" minlength="8" required>
            </div>
            <div class="form-group">
                <span class="input-icon"><i class="fa fa-user-circle"></i></span>
                <input type="password" class="form-control" name="confirmar_password" placeholder="Confirma Contraseña" minlength="8" required>
            </div>
            <button type="submit" class="btn btn-register">Registrarme</button>
        </form>
        <div class="text-center mt-2">
            <span class="login-link">¿Ya tienes cuenta?</span>
            <a href="login.jsp" class="btn btn-login">Iniciar Sesión</a>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>