<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
  <jsp:include page="../WEB-INF/header_min_admin.jsp">
      <jsp:param name="title" value="Panel Admin"/>
  </jsp:include>

<div class="container-fluid mt-4">
    <div class="row">
        <div class="col-md-3">
            <jsp:include page="sdebar.jsp"/> <%-- Incluir el sidebar de admin --%>
        </div>
        <div class="col-md-9">
            <h2>Registro de Nuevo Usuario</h2>

            <c:if test="${not empty error}">
                <div class="alert alert-danger" role="alert">
                    ${error}
                </div>
            </c:if>
            <c:if test="${not empty successMessage}">
                <div class="alert alert-success" role="alert">
                    ${successMessage}
                </div>
            </c:if>

            <form action="registroUsuarios" method="post" id="registroForm" novalidate>
                <%-- Campos comunes para todos los usuarios --%>
                <div class="mb-3">
                    <label for="nombre" class="form-label">Nombre</label>
                    <input type="text" class="form-control" id="nombre" name="nombre" 
                           pattern="^[A-Za-zÁáÉéÍíÓóÚúÑñ ]{2,50}$" 
                           title="Solo letras, espacios y caracteres españoles (2-50 caracteres)" 
                           required>
                    <div class="invalid-feedback" id="nombreError"></div>
                </div>
                <div class="mb-3">
                    <label for="apellido" class="form-label">Apellido</label>
                    <input type="text" class="form-control" id="apellido" name="apellido" 
                           pattern="^[A-Za-zÁáÉéÍíÓóÚúÑñ ]{2,50}$" 
                           title="Solo letras, espacios y caracteres españoles (2-50 caracteres)" 
                           required>
                    <div class="invalid-feedback" id="apellidoError"></div>
                </div>
                <div class="mb-3">
                    <label for="email" class="form-label">Email</label>
                    <input type="email" class="form-control" id="email" name="email" 
                           maxlength="100" 
                           pattern="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$" 
                           title="Formato de email válido" 
                           required>
                    <div class="invalid-feedback" id="emailError"></div>
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">Contraseña</label>
                    <input type="password" class="form-control" id="password" name="password" 
                           minlength="8" maxlength="50" 
                           pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,50}$" 
                           title="Mínimo 8 caracteres, con mayúscula, minúscula, número y carácter especial" 
                           required>
                    <div class="invalid-feedback" id="passwordError"></div>
                    <small class="form-text text-muted">
                        Mínimo 8 caracteres, debe contener mayúscula, minúscula, número y carácter especial
                    </small>
                </div>
                <div class="mb-3">
                    <label for="dni" class="form-label">DNI (8 dígitos)</label>
                    <input type="text" class="form-control" id="dni" name="dni" 
                           pattern="[0-9]{8}" 
                           title="Debe contener exactamente 8 dígitos" 
                           maxlength="8" 
                           required>
                    <div class="invalid-feedback" id="dniError"></div>
                </div>
                <div class="mb-3">
                    <label for="telefono" class="form-label">Teléfono</label>
                    <input type="text" class="form-control" id="telefono" name="telefono" 
                           pattern="^\+51[0-9]{9}$" 
                           title="Formato: +51 seguido de 9 dígitos (ejemplo: +51987654321)" 
                           placeholder="+51987654321" 
                           maxlength="12" 
                           required>
                    <div class="invalid-feedback" id="telefonoError"></div>
                    <small class="form-text text-muted">Formato: +51 + 9 dígitos (ejemplo: +51987654321)</small>
                </div>
                <div class="mb-3">
                    <label for="direccion" class="form-label">Dirección</label>
                    <textarea class="form-control" id="direccion" name="direccion" 
                              rows="3" maxlength="200" 
                              title="Máximo 200 caracteres"></textarea>
                    <div class="invalid-feedback" id="direccionError"></div>
                </div>
                <div class="mb-3">
                    <label for="fecha_nacimiento" class="form-label">Fecha de Nacimiento</label>
                    <input type="date" class="form-control" id="fecha_nacimiento" name="fecha_nacimiento" 
                           required>
                    <div class="invalid-feedback" id="fechaError"></div>
                </div>

                <div class="mb-3">
                    <label for="genero" class="form-label">Género</label>
                    <select class="form-select" id="genero" name="genero" required>
                        <option value="">Seleccione...</option>
                        <option value="M">Masculino</option>
                        <option value="F">Femenino</option>
                        <option value="O">Otro</option>
                    </select>
                    <div class="invalid-feedback" id="generoError"></div>
                </div>

                <div class="mb-3">
                    <label for="rol" class="form-label">Rol</label>
                    <select class="form-select" id="rol" name="rol" required>
                        <option value="">Seleccione...</option>
                        <option value="admin">Admin</option>
                        <option value="doctor">Doctor</option>
                        <option value="recepcionista">Recepcionista</option>
                    </select>
                    <div class="invalid-feedback" id="rolError"></div>
                </div>

                <%-- Campos específicos para el rol Doctor --%>
                <div id="doctorFields" style="display: none;">
                    <div class="mb-3">
                        <label for="especialidad_id" class="form-label">Especialidad</label>
                        <select class="form-select" id="especialidad_id" name="especialidad_id">
                            <option value="">Seleccione una especialidad...</option>
                            <c:forEach var="especialidad" items="${especialidades}">
                                <option value="${especialidad.id}">${especialidad.nombre}</option>
                            </c:forEach>
                        </select>
                        <div class="invalid-feedback" id="especialidadError"></div>
                    </div>
                    <div class="mb-3">
                        <label for="biografia" class="form-label">Biografía</label>
                        <textarea class="form-control" id="biografia" name="biografia" 
                                  rows="5" maxlength="500" 
                                  title="Máximo 500 caracteres"></textarea>
                        <div class="invalid-feedback" id="biografiaError"></div>
                        <small class="form-text text-muted">Máximo 500 caracteres</small>
                    </div>
                    <div class="mb-3" id="codigoColegiaturaField">
                        <label for="codigo_colegiatura" class="form-label">Código de Colegiatura</label>
                        <input type="text" class="form-control" id="codigo_colegiatura" name="codigo_colegiatura" 
                               pattern="^[A-Za-z0-9]{5,20}$" 
                               title="Solo números y letras (5-20 caracteres)" 
                               minlength="5" maxlength="20" 
                               required>
                        <div class="invalid-feedback" id="codigoError"></div>
                    </div>
                </div>

                <button type="submit" class="btn btn-primary" id="submitBtn">Registrar Usuario</button>
            </form>

<script>
                // Validación en tiempo real
                document.addEventListener('DOMContentLoaded', function() {
                    const form = document.getElementById('registroForm');
                    const submitBtn = document.getElementById('submitBtn');
                    const rolSelect = document.getElementById('rol');
                    const doctorFields = document.getElementById('doctorFields');
                    const especialidadSelect = document.getElementById('especialidad_id');
                    const codigoColegiaturaField = document.getElementById('codigo_colegiaturaField');

                    // Mostrar/ocultar campos de doctor
                    rolSelect.addEventListener('change', function() {
        if (this.value === 'doctor') {
            doctorFields.style.display = 'block';
                            especialidadSelect.required = true;
                            document.getElementById('codigo_colegiatura').required = true;
        } else {
            doctorFields.style.display = 'none';
                            especialidadSelect.required = false;
                            document.getElementById('codigo_colegiatura').required = false;
                        }
                        validateForm();
                    });

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

                    // Validación de biografía
                    document.getElementById('biografia').addEventListener('input', function() {
                        validateBiografia(this);
                    });

                    // Validación de código de colegiatura
                    document.getElementById('codigo_colegiatura').addEventListener('input', function() {
                        validateCodigoColegiatura(this);
                    });

                    // Validación de especialidad
                    document.getElementById('especialidad_id').addEventListener('change', function() {
                        validateEspecialidad(this);
                    });

                    // Validación de género y rol
                    ['genero', 'rol'].forEach(function(fieldName) {
                        document.getElementById(fieldName).addEventListener('change', function() {
                            validateSelect(this);
                        });
                    });

                    // Prevenir envío si hay errores
                    form.addEventListener('submit', function(e) {
                        if (!validateForm()) {
                            e.preventDefault();
                            alert('Por favor, corrija los errores en el formulario antes de enviar.');
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

                function validateBiografia(input) {
                    const biografia = input.value;
                    
                    if (biografia.length > 500) {
                        showError(input, 'Máximo 500 caracteres');
                        return false;
                    } else {
                        hideError(input);
                        return true;
                    }
                }

                function validateCodigoColegiatura(input) {
                    const codigo = input.value;
                    const pattern = /^[A-Za-z0-9]{5,20}$/;
                    
                    if (!pattern.test(codigo)) {
                        showError(input, 'Solo números y letras (5-20 caracteres)');
                        return false;
                    } else {
                        hideError(input);
                        return true;
                    }
                }

                function validateEspecialidad(input) {
                    if (document.getElementById('rol').value === 'doctor' && !input.value) {
                        showError(input, 'Debe seleccionar una especialidad');
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
                    const requiredFields = ['nombre', 'apellido', 'email', 'password', 'dni', 'telefono', 'fecha_nacimiento', 'genero', 'rol'];
                    
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
                    if (!validateEmail(document.getElementById('email'))) isValid = false;
                    if (!validatePassword(document.getElementById('password'))) isValid = false;
                    if (!validateDNI(document.getElementById('dni'))) isValid = false;
                    if (!validateTelefono(document.getElementById('telefono'))) isValid = false;
                    if (!validateFechaNacimiento(document.getElementById('fecha_nacimiento'))) isValid = false;
                    if (!validateSelect(document.getElementById('genero'))) isValid = false;
                    if (!validateSelect(document.getElementById('rol'))) isValid = false;

                    // Validaciones específicas para doctor
                    if (document.getElementById('rol').value === 'doctor') {
                        if (!validateEspecialidad(document.getElementById('especialidad_id'))) isValid = false;
                        if (!validateCodigoColegiatura(document.getElementById('codigo_colegiatura'))) isValid = false;
                    }

                    // Habilitar/deshabilitar botón de envío
                    document.getElementById('submitBtn').disabled = !isValid;
                    
                    return isValid;
                }
</script> 
        </div>
    </div>
</div> 