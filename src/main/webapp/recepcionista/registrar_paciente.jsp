<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
  <jsp:include page="../WEB-INF/header_min_recepcionista.jsp">
      <jsp:param name="title" value="Panel Recepcionista"/>
  </jsp:include>

<div class="container-fluid mt-4">
    <div class="row">
        <div class="col-md-3">
            <jsp:include page="sidebar.jsp"/>
        </div>
        <div class="col-md-9">
            <div class="card">
                <div class="card-header bg-primary text-white">
                    <h4 class="mb-0">Registrar Nuevo Paciente</h4>
                </div>
                <div class="card-body">
                    <% if(request.getAttribute("error") != null) { %>
                        <div class="alert alert-danger">
                            <%= request.getAttribute("error") %>
                        </div>
                    <% } %>
                    <form action="../verificar-correo" method="post" autocomplete="off" id="registroForm" novalidate>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="nombre" class="form-label">Nombre</label>
                                <input type="text" class="form-control" name="nombre" id="nombre" 
                                       placeholder="Nombre" 
                                       pattern="^[A-Za-zÁáÉéÍíÓóÚúÑñ ]{2,50}$" 
                                       title="Solo letras, espacios y caracteres españoles (2-50 caracteres)" 
                                       required>
                                <div class="invalid-feedback" id="nombreError"></div>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="apellido" class="form-label">Apellido</label>
                                <input type="text" class="form-control" name="apellido" id="apellido" 
                                       placeholder="Apellido" 
                                       pattern="^[A-Za-zÁáÉéÍíÓóÚúÑñ ]{2,50}$" 
                                       title="Solo letras, espacios y caracteres españoles (2-50 caracteres)" 
                                       required>
                                <div class="invalid-feedback" id="apellidoError"></div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="dni" class="form-label">Número de Documento</label>
                                <input type="text" class="form-control" name="dni" id="dni" 
                                       placeholder="Número de Documento" 
                                       pattern="[0-9]{8}" 
                                       title="Debe contener exactamente 8 dígitos" 
                                       maxlength="8" 
                                       required>
                                <div class="invalid-feedback" id="dniError"></div>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="fecha_nacimiento" class="form-label">Fecha de Nacimiento</label>
                                <input type="date" class="form-control" name="fecha_nacimiento" id="fecha_nacimiento" required>
                                <div class="invalid-feedback" id="fechaError"></div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="email" class="form-label">Correo</label>
                                <input type="email" class="form-control" name="email" id="email" 
                                       placeholder="Correo" 
                                       maxlength="100" 
                                       pattern="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$" 
                                       title="Formato de email válido" 
                                       required>
                                <div class="invalid-feedback" id="emailError"></div>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="telefono" class="form-label">Celular</label>
                                <input type="text" class="form-control" name="telefono" id="telefono" 
                                       placeholder="+51987654321" 
                                       pattern="^\+51[0-9]{9}$" 
                                       title="Formato: +51 seguido de 9 dígitos (ejemplo: +51987654321)" 
                                       maxlength="12" 
                                       required>
                                <div class="invalid-feedback" id="telefonoError"></div>
                                <small class="form-text text-muted">Formato: +51 + 9 dígitos (ejemplo: +51987654321)</small>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="genero" class="form-label">Género</label>
                                <select class="form-select" id="genero" name="genero" required>
                                    <option value="">Seleccione...</option>
                                    <option value="M">Masculino</option>
                                    <option value="F">Femenino</option>
                                    <option value="O">Otro</option>
                                </select>
                                <div class="invalid-feedback" id="generoError"></div>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="direccion" class="form-label">Dirección</label>
                                <textarea class="form-control" id="direccion" name="direccion" 
                                          rows="3" maxlength="200" 
                                          title="Máximo 200 caracteres" 
                                          placeholder="Dirección completa"></textarea>
                                <div class="invalid-feedback" id="direccionError"></div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="password" class="form-label">Contraseña</label>
                                <input type="password" class="form-control" name="password" id="password" 
                                       placeholder="Contraseña" 
                                       minlength="8" maxlength="50" 
                                       pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,50}$" 
                                       title="Mínimo 8 caracteres, con mayúscula, minúscula, número y carácter especial" 
                                       required>
                                <div class="invalid-feedback" id="passwordError"></div>
                                <small class="form-text text-muted">
                                    Mínimo 8 caracteres, debe contener mayúscula, minúscula, número y carácter especial
                                </small>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="confirmar_password" class="form-label">Confirma Contraseña</label>
                                <input type="password" class="form-control" name="confirmar_password" id="confirmar_password" 
                                       placeholder="Confirma Contraseña" 
                                       minlength="8" 
                                       required>
                                <div class="invalid-feedback" id="confirmarPasswordError"></div>
                            </div>
                        </div>
                        <div class="d-grid gap-2">
                            <button type="submit" class="btn btn-primary" id="submitBtn">Registrar Paciente</button>
                        </div>
                    </form>

                    <script>
                        // Validación en tiempo real
                        document.addEventListener('DOMContentLoaded', function() {
                            const form = document.getElementById('registroForm');
                            const submitBtn = document.getElementById('submitBtn');

                            // Validación de fecha de nacimiento
                            document.getElementById('fecha_nacimiento').addEventListener('change', function() {
                                validateFechaNacimiento(this);
                            });

                            // Validación de teléfono
                            document.getElementById('telefono').addEventListener('input', function() {
                                validateTelefono(this);
                            });

                            // Validación de DNI
                            document.getElementById('dni').addEventListener('input', function() {
                                this.value = this.value.replace(/[^0-9]/g, '');
                                validateDNI(this);
                            });

                            // Validación de contraseña
                            document.getElementById('password').addEventListener('input', function() {
                                validatePassword(this);
                            });

                            // Validación de confirmar contraseña
                            document.getElementById('confirmar_password').addEventListener('input', function() {
                                validateConfirmarPassword(this);
                            });

                            // Validación de nombre y apellido
                            ['nombre', 'apellido'].forEach(function(fieldName) {
                                document.getElementById(fieldName).addEventListener('input', function() {
                                    validateNombreApellido(this);
                                });
                            });

                            // Validación de email
                            document.getElementById('email').addEventListener('input', function() {
                                validateEmail(this);
                            });

                            // Validación de dirección
                            document.getElementById('direccion').addEventListener('input', function() {
                                validateDireccion(this);
                            });

                            // Validación de género
                            document.getElementById('genero').addEventListener('change', function() {
                                validateSelect(this);
                            });

                            // Prevenir envío si hay errores
                            form.addEventListener('submit', function(e) {
                                if (!validateForm()) {
                                    e.preventDefault();
                                    alert('Por favor, corrija los errores en el formulario antes de enviar.');
                                } else {
                                    // Debugging: mostrar datos que se van a enviar
                                    console.log('Datos del formulario a enviar:');
                                    console.log('Nombre:', document.getElementById('nombre').value);
                                    console.log('Apellido:', document.getElementById('apellido').value);
                                    console.log('DNI:', document.getElementById('dni').value);
                                    console.log('Email:', document.getElementById('email').value);
                                    console.log('Teléfono:', document.getElementById('telefono').value);
                                    console.log('Género:', document.getElementById('genero').value);
                                    console.log('Dirección:', document.getElementById('direccion').value);
                                    console.log('Fecha Nacimiento:', document.getElementById('fecha_nacimiento').value);
                                    
                                    // Verificar específicamente el género
                                    const generoValue = document.getElementById('genero').value;
                                    console.log('Valor del género:', generoValue);
                                    console.log('Género es null:', generoValue === null);
                                    console.log('Género está vacío:', generoValue === '');
                                    console.log('Longitud del género:', generoValue.length);
                                }
                            });
                        });

                        function validateFechaNacimiento(input) {
                            const fecha = new Date(input.value);
                            const hoy = new Date();
                            const edad = hoy.getFullYear() - fecha.getFullYear();
                            const mes = hoy.getMonth() - fecha.getMonth();
                            
                            if (mes < 0 || (mes === 0 && hoy.getDate() < fecha.getDate())) {
                                edad--;
                            }

                            if (fecha > hoy) {
                                showError(input, 'La fecha no puede ser futura');
                                return false;
                            } else if (edad < 18) {
                                showError(input, 'Debe tener al menos 18 años');
                                return false;
                            } else if (edad > 80) {
                                showError(input, 'La edad máxima permitida es 80 años');
                                return false;
                            } else {
                                hideError(input);
                                return true;
                            }
                        }

                        function validateTelefono(input) {
                            const telefono = input.value;
                            const pattern = /^\+51[0-9]{9}$/;
                            
                            if (!pattern.test(telefono)) {
                                showError(input, 'Formato: +51 seguido de 9 dígitos (ejemplo: +51987654321)');
                                return false;
                            } else {
                                hideError(input);
                                return true;
                            }
                        }

                        function validateDNI(input) {
                            const dni = input.value;
                            
                            if (dni.length !== 8) {
                                showError(input, 'El DNI debe tener exactamente 8 dígitos');
                                return false;
                            } else {
                                hideError(input);
                                return true;
                            }
                        }

                        function validatePassword(input) {
                            const password = input.value;
                            const pattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,50}$/;
                            
                            if (!pattern.test(password)) {
                                showError(input, 'Debe contener mayúscula, minúscula, número y carácter especial (mínimo 8 caracteres)');
                                return false;
                            } else {
                                hideError(input);
                                return true;
                            }
                        }

                        function validateConfirmarPassword(input) {
                            const password = document.getElementById('password').value;
                            const confirmarPassword = input.value;
                            
                            if (password !== confirmarPassword) {
                                showError(input, 'Las contraseñas no coinciden');
                                return false;
                            } else {
                                hideError(input);
                                return true;
                            }
                        }

                        function validateNombreApellido(input) {
                            const value = input.value;
                            const pattern = /^[A-Za-zÁáÉéÍíÓóÚúÑñ ]{2,50}$/;
                            
                            if (!pattern.test(value)) {
                                showError(input, 'Solo letras, espacios y caracteres españoles (2-50 caracteres)');
                                return false;
                            } else {
                                hideError(input);
                                return true;
                            }
                        }

                        function validateEmail(input) {
                            const email = input.value;
                            const pattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
                            
                            if (!pattern.test(email)) {
                                showError(input, 'Formato de email válido requerido');
                                return false;
                            } else {
                                hideError(input);
                                return true;
                            }
                        }

                        function validateDireccion(input) {
                            const direccion = input.value;
                            
                            if (direccion.length > 200) {
                                showError(input, 'Máximo 200 caracteres');
                                return false;
                            } else {
                                hideError(input);
                                return true;
                            }
                        }

                        function validateSelect(input) {
                            if (!input.value) {
                                showError(input, 'Debe seleccionar una opción');
                                return false;
                            } else {
                                hideError(input);
                                return true;
                            }
                        }

                        function showError(input, message) {
                            input.classList.add('is-invalid');
                            const errorDiv = document.getElementById(input.id + 'Error');
                            if (errorDiv) {
                                errorDiv.textContent = message;
                            }
                        }

                        function hideError(input) {
                            input.classList.remove('is-invalid');
                            input.classList.add('is-valid');
                            const errorDiv = document.getElementById(input.id + 'Error');
                            if (errorDiv) {
                                errorDiv.textContent = '';
                            }
                        }

                        function validateForm() {
                            let isValid = true;
                            
                            // Validar todos los campos requeridos
                            const requiredFields = ['nombre', 'apellido', 'dni', 'fecha_nacimiento', 'email', 'telefono', 'genero', 'password', 'confirmar_password'];
                            
                            requiredFields.forEach(function(fieldName) {
                                const field = document.getElementById(fieldName);
                                if (!field.value) {
                                    showError(field, 'Este campo es requerido');
                                    isValid = false;
                                }
                            });

                            // Validaciones específicas
                            if (!validateNombreApellido(document.getElementById('nombre'))) isValid = false;
                            if (!validateNombreApellido(document.getElementById('apellido'))) isValid = false;
                            if (!validateDNI(document.getElementById('dni'))) isValid = false;
                            if (!validateFechaNacimiento(document.getElementById('fecha_nacimiento'))) isValid = false;
                            if (!validateEmail(document.getElementById('email'))) isValid = false;
                            if (!validateTelefono(document.getElementById('telefono'))) isValid = false;
                            if (!validateSelect(document.getElementById('genero'))) isValid = false;
                            if (!validatePassword(document.getElementById('password'))) isValid = false;
                            if (!validateConfirmarPassword(document.getElementById('confirmar_password'))) isValid = false;

                            // Habilitar/deshabilitar botón de envío
                            document.getElementById('submitBtn').disabled = !isValid;
                            
                            return isValid;
                        }
                    </script>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../WEB-INF/footer.jsp"/> 