<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="WEB-INF/header.jsp">
    <jsp:param name="title" value="Login"/>
</jsp:include>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registro - Hospital La Hoz</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="assets/css/styles.css" rel="stylesheet">
    
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
        .regiter-card {
            background-color: var(--color-principal) !important;
        }
        .register-container {
            background: #399fad;
            padding: 2rem;
            border-radius: 10px;
            width:  450px;
            height: 500px;
        }
        .register-title {
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
</head>
<body>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header regiter-card text-white">
                        <h3 class="text-center register-title mb-0">Registro de Usuario</h3>
                    </div>
                    <div class="card-body">
                        <% if(request.getAttribute("error") != null) { %>
                            <div class="alert alert-danger">
                                <%= request.getAttribute("error") %>
                            </div>
                        <% } %>
                        
                        <form action="verificar-correo" method="post" id="registroForm" onsubmit="return validarFormulario()">
                            <div class="mb-3">
                                <label for="nombre" class="form-label">Nombre</label>
                                <input type="text" class="form-control" id="nombre" name="nombre" required
                                       pattern="[A-Za-zÁáÉéÍíÓóÚúÑñ ]+" 
                                       title="Solo se permiten letras">
                            </div>
                            
                            <div class="mb-3">
                                <label for="apellido" class="form-label">Apellido</label>
                                <input type="text" class="form-control" id="apellido" name="apellido" required
                                       pattern="[A-Za-zÁáÉéÍíÓóÚúÑñ ]+" 
                                       title="Solo se permiten letras">
                            </div>
                            
                            <div class="mb-3">
                                <label for="dni" class="form-label">DNI</label>
                                <input type="text" class="form-control" id="dni" name="dni" required
                                       pattern="[0-9]{8}" maxlength="8"
                                       title="Debe contener exactamente 8 números">
                            </div>
                            
                            <div class="mb-3">
                                <label for="fecha_nacimiento" class="form-label">Fecha de Nacimiento</label>
                                <input type="date" class="form-control" id="fecha_nacimiento" name="fecha_nacimiento" 
                                       required onchange="validarEdad()">
                                <div id="errorEdad" class="text-danger"></div>
                            </div>
                            
                            <div class="mb-3">
                                <label for="email" class="form-label">Correo Electrónico</label>
                                <input type="email" class="form-control" id="email" name="email" required>
                            </div>
                            
                            <div class="mb-3">
                                <label for="password" class="form-label">Contraseña</label>
                                <input type="password" class="form-control" id="password" name="password" 
                                       required minlength="8">
                            </div>
                            
                            <div class="mb-3">
                                <label for="confirm_password" class="form-label">Confirmar Contraseña</label>
                                <input type="password" class="form-control" id="confirm_password" name="confirmar_password" required minlength="8">
                            </div>
                            
                            <div class="d-grid gap-2">
                                <button type="submit" class="btn btn-primary">Registrarse</button>
                            </div>
                        </form>
                        
                        <div class="text-center mt-3">
                            <p>¿Ya tienes una cuenta? <a href="login.jsp">Inicia sesión</a></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js"></script>
    
    <script>
        function validarEdad() {
            const fechaNacimiento = new Date(document.getElementById('fecha_nacimiento').value);
            const hoy = new Date();
            const edad = hoy.getFullYear() - fechaNacimiento.getFullYear();
            const mes = hoy.getMonth() - fechaNacimiento.getMonth();
            
            if (mes < 0 || (mes === 0 && hoy.getDate() < fechaNacimiento.getDate())) {
                edad--;
            }
            
            const errorEdad = document.getElementById('errorEdad');
            if (edad < 18) {
                errorEdad.textContent = 'Debes ser mayor de edad para registrarte';
                document.getElementById('fecha_nacimiento').value = '';
            } else {
                errorEdad.textContent = '';
            }
        }
        
        function validarFormulario() {
            const password = document.getElementById('password').value;
            const confirmPassword = document.getElementById('confirm_password').value;
            
            if (password !== confirmPassword) {
                alert('Las contraseñas no coinciden');
                return false;
            }
            
            return true;
        }
    </script>
</body>
</html>