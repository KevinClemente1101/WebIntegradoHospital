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

            <%-- Revertimos la acciÃ³n del formulario a registroUsuarios --%>
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
                    <label for="password" class="form-label">ContraseÃ±a</label>
                    <input type="password" class="form-control" id="password" name="password" required>
                </div>
                <div class="mb-3">
                    <label for="dni" class="form-label">DNI (8 dÃ­gitos)</label>
                    <input type="text" class="form-control" id="dni" name="dni" pattern="[0-9]{8}" title="Debe contener exactamente 8 dÃ­gitos" required>
                </div>
                <div class="mb-3">
                    <label for="telefono" class="form-label">TelÃ©fono</label>
                    <input type="text" class="form-control" id="telefono" name="telefono">
                </div>
                <div class="mb-3">
                    <label for="direccion" class="form-label">DirecciÃ³n</label>
                    <textarea class="form-control" id="direccion" name="direccion" rows="3"></textarea>
                </div>
                <div class="mb-3">
                    <label for="fecha_nacimiento" class="form-label">Fecha de Nacimiento</label>
                    <input type="date" class="form-control" id="fecha_nacimiento" name="fecha_nacimiento" required>
                </div>

                <div class="mb-3">
                    <label for="genero" class="form-label">GÃ©nero</label>
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
                        <%-- Puedes aÃ±adir otros roles si es necesario --%>
                    </select>
                </div>

                <%-- Campos especÃ­ficos para el rol Doctor (mantenerlos para la lÃ³gica del servlet) --%>
                <div id="doctorFields" style="display: none;">
                    <div class="mb-3">
                        <label for="especialidad_id" class="form-label">Especialidad</label>
                        <select class="form-select" id="especialidad_id" name="especialidad_id">
                            <option value="">Seleccione una especialidad...</option>
                            <%-- AquÃ­ deberÃ­as llenar dinÃ¡micamente las especialidades desde el servidor --%>
                            <%-- Ejemplo (necesitarÃ­as pasar una lista de especialidades al JSP) --%>
                            <%-- <c:forEach var="especialidad" items="${especialidades}"> --%>
                            <%--     <option value="${especialidad.id}">${especialidad.nombre}</option> --%>
                            <%-- </c:forEach> --%>
                            <%-- Placeholder temporal: --%>
                            <option value="1">CardiologÃ­a</option>
                            <option value="2">PediatrÃ­a</option>
                            <option value="3">Medicina General</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="biografia" class="form-label">BiografÃ­a</label>
                        <textarea class="form-control" id="biografia" name="biografia" rows="5"></textarea>
                    </div>
                </div>

                <button type="submit" class="btn btn-primary">Registrar Usuario</button>
            </form>
        </div>
    </div>
</div>

<jsp:include page="../WEB-INF/footer.jsp"/>

<%-- Script para mostrar/ocultar campos de doctor segÃºn el rol seleccionado --%>
<script>
    document.getElementById('rol').addEventListener('change', function () {
        var doctorFields = document.getElementById('doctorFields');
        if (this.value === 'doctor') {
            doctorFields.style.display = 'block';
            // Puedes aÃ±adir lÃ³gica aquÃ­ para hacer los campos de doctor requeridos si es necesario
        } else {
            doctorFields.style.display = 'none';
            // Puedes aÃ±adir lÃ³gica aquÃ­ para remover el atributo 'required' si es necesario
        }
    });
</script> 