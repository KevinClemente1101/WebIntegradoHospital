<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../WEB-INF/header.jsp">
    <jsp:param name="title" value="Registro de Usuarios"/>
</jsp:include>

<div class="container-fluid mt-4">
    <div class="row">
        <div class="col-md-3">
            <jsp:include page="sdebar.jsp"/> <%-- Incluir el sidebar de admin --%>
        </div>
        <div class="col-md-9">
            <h2>Registro de Nuevo Usuario</h2>

            <%--
            <c:if test="${not empty error}">
                <div class="alert alert-danger" role="alert">
                    ${error}
                </div>
            </c:if>
            --%>
            <%--
            <c:if test="${not empty successMessage}">
                <div class="alert alert-success" role="alert">
                    ${successMessage}
                </div>
            </c:if>
            --%>

            <form action="registroUsuarios" method="post">
                <%-- Campos comunes para todos los usuarios --%>
                <div class="mb-3">
                    <label for="nombre" class="form-label">Nombre</label>
                    <input type="text" class="form-control" id="nombre" name="nombre" required>
                </div>
                <div class="mb-3">
                    <label for="apellido" class="form-label">Apellido</label>
                    <input type="text" class="form-control" id="apellido" name="apellido" required>
                </div>
                <div class="mb-3">
                    <label for="email" class="form-label">Email</label>
                    <input type="email" class="form-control" id="email" name="email" required>
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">Contraseña</label>
                    <input type="password" class="form-control" id="password" name="password" required>
                </div>
                <div class="mb-3">
                    <label for="dni" class="form-label">DNI (8 dígitos)</label>
                    <input type="text" class="form-control" id="dni" name="dni" pattern="[0-9]{8}" title="Debe contener exactamente 8 dígitos" required>
                </div>
                <div class="mb-3">
                    <label for="telefono" class="form-label">Teléfono</label>
                    <input type="text" class="form-control" id="telefono" name="telefono">
                </div>
                <div class="mb-3">
                    <label for="direccion" class="form-label">Dirección</label>
                    <textarea class="form-control" id="direccion" name="direccion" rows="3"></textarea>
                </div>
                <div class="mb-3">
                    <label for="fecha_nacimiento" class="form-label">Fecha de Nacimiento</label>
                    <input type="date" class="form-control" id="fecha_nacimiento" name="fecha_nacimiento" required>
                </div>

                <div class="mb-3">
                    <label for="genero" class="form-label">Género</label>
                    <select class="form-select" id="genero" name="genero" required>
                        <option value="">Seleccione...</option>
                        <option value="M">Masculino</option>
                        <option value="F">Femenino</option>
                        <option value="O">Otro</option>
                    </select>
                </div>

                <div class="mb-3">
                    <label for="rol" class="form-label">Rol</label>
                    <select class="form-select" id="rol" name="rol" required>
                        <option value="">Seleccione...</option>
                        <option value="admin">Admin</option>
                        <option value="doctor">Doctor</option>
                    </select>
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
                    </div>
                    <div class="mb-3">
                        <label for="biografia" class="form-label">Biografía</label>
                        <textarea class="form-control" id="biografia" name="biografia" rows="5"></textarea>
                    </div>
                </div>

                <button type="submit" class="btn btn-primary">Registrar Usuario</button>
            </form>
        </div>
    </div>
</div>

<jsp:include page="../WEB-INF/footer.jsp"/>

<script>
    document.getElementById('rol').addEventListener('change', function() {
        var doctorFields = document.getElementById('doctorFields');
        var especialidad = document.getElementById('especialidad_id');
        var biografia = document.getElementById('biografia');
        
        if (this.value === 'doctor') {
            doctorFields.style.display = 'block';
            especialidad.required = true;
            biografia.required = true;
            
            // Debug: Verificar si hay opciones en el select
            console.log('Número de opciones en especialidad:', especialidad.options.length);
            for(var i = 0; i < especialidad.options.length; i++) {
                console.log('Opción', i, ':', especialidad.options[i].text);
            }
        } else {
            doctorFields.style.display = 'none';
            especialidad.required = false;
            biografia.required = false;
        }
    });

    // Ejecutar al cargar la página
    document.addEventListener('DOMContentLoaded', function() {
        var rolDropdown = document.getElementById('rol');
        var doctorFields = document.getElementById('doctorFields');
        var especialidad = document.getElementById('especialidad_id');
        var biografia = document.getElementById('biografia');
        
        // Debug: Verificar si hay opciones en el select al cargar
        console.log('Número de opciones en especialidad al cargar:', especialidad.options.length);
        for(var i = 0; i < especialidad.options.length; i++) {
            console.log('Opción', i, ':', especialidad.options[i].text);
        }
        
        if (rolDropdown.value === 'doctor') {
            doctorFields.style.display = 'block';
            especialidad.required = true;
            biografia.required = true;
        }
    });
</script> 