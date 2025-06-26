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
            <h2>Mi Perfil</h2>
            
            <c:if test="${not empty successMessage}">
                <div class="alert alert-success" role="alert">
                    ${successMessage}
                </div>
            </c:if>
            
            <c:if test="${not empty error}">
                <div class="alert alert-danger" role="alert">
                    ${error}
                </div>
            </c:if>

            <div class="row">
                <div class="col-md-6">
                    <div class="card">
                        <div class="card-header">
                            <h5>Información Personal</h5>
                        </div>
                        <div class="card-body">
                            <form action="${pageContext.request.contextPath}/recepcionista/actualizar-perfil" method="post">
                                <div class="mb-3">
                                    <label for="nombre" class="form-label">Nombre</label>
                                    <input type="text" class="form-control" id="nombre" name="nombre" 
                                           value="${usuario.nombre}" required>
                                </div>
                                <div class="mb-3">
                                    <label for="apellido" class="form-label">Apellido</label>
                                    <input type="text" class="form-control" id="apellido" name="apellido" 
                                           value="${usuario.apellido}" required>
                                </div>
                                <div class="mb-3">
                                    <label for="email" class="form-label">Email</label>
                                    <input type="email" class="form-control" id="email" name="email" 
                                           value="${usuario.email}" required>
                                </div>
                                <div class="mb-3">
                                    <label for="telefono" class="form-label">Teléfono</label>
                                    <input type="text" class="form-control" id="telefono" name="telefono" 
                                           value="${usuario.telefono}">
                                </div>
                                <div class="mb-3">
                                    <label for="direccion" class="form-label">Dirección</label>
                                    <textarea class="form-control" id="direccion" name="direccion" 
                                              rows="3">${usuario.direccion}</textarea>
                                </div>
                                <div class="mb-3">
                                    <label for="fecha_nacimiento" class="form-label">Fecha de Nacimiento</label>
                                    <input type="date" class="form-control" id="fecha_nacimiento" 
                                           name="fecha_nacimiento" value="${usuario.fechaNacimiento}" required>
                                </div>
                                <div class="mb-3">
                                    <label for="genero" class="form-label">Género</label>
                                    <select class="form-select" id="genero" name="genero" required>
                                        <option value="M" ${usuario.genero == 'M' ? 'selected' : ''}>Masculino</option>
                                        <option value="F" ${usuario.genero == 'F' ? 'selected' : ''}>Femenino</option>
                                        <option value="O" ${usuario.genero == 'O' ? 'selected' : ''}>Otro</option>
                                    </select>
                                </div>
                                <button type="submit" class="btn btn-primary">Actualizar Información</button>
                            </form>
                        </div>
                    </div>
                </div>
                
                <div class="col-md-6">
                    <div class="card">
                        <div class="card-header">
                            <h5>Cambiar Contraseña</h5>
                        </div>
                        <div class="card-body">
                            <form action="${pageContext.request.contextPath}/recepcionista/cambiar-password" method="post">
                                <div class="mb-3">
                                    <label for="password_actual" class="form-label">Contraseña Actual</label>
                                    <input type="password" class="form-control" id="password_actual" 
                                           name="password_actual" required>
                                </div>
                                <div class="mb-3">
                                    <label for="password_nuevo" class="form-label">Nueva Contraseña</label>
                                    <input type="password" class="form-control" id="password_nuevo" 
                                           name="password_nuevo" required>
                                </div>
                                <div class="mb-3">
                                    <label for="password_confirmar" class="form-label">Confirmar Nueva Contraseña</label>
                                    <input type="password" class="form-control" id="password_confirmar" 
                                           name="password_confirmar" required>
                                </div>
                                <button type="submit" class="btn btn-warning">Cambiar Contraseña</button>
                            </form>
                        </div>
                    </div>
                    
                    <div class="card mt-4">
                        <div class="card-header">
                            <h5>Información del Sistema</h5>
                        </div>
                        <div class="card-body">
                            <p><strong>Rol:</strong> Recepcionista</p>
                            <p><strong>DNI:</strong> ${usuario.dni}</p>
                            <%-- <p><strong>Fecha de Registro:</strong> ${usuario.fecha_creacion}</p> --%>
                            <%-- <p><strong>Última Actualización:</strong> ${usuario.fecha_actualizacion}</p> --%>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../WEB-INF/footer.jsp"/>

<script>
document.addEventListener('DOMContentLoaded', function() {
    // Validar que las contraseñas coincidan
    const passwordNuevo = document.getElementById('password_nuevo');
    const passwordConfirmar = document.getElementById('password_confirmar');
    
    function validarPassword() {
        if (passwordNuevo.value !== passwordConfirmar.value) {
            passwordConfirmar.setCustomValidity('Las contraseñas no coinciden');
        } else {
            passwordConfirmar.setCustomValidity('');
        }
    }
    
    passwordNuevo.addEventListener('change', validarPassword);
    passwordConfirmar.addEventListener('keyup', validarPassword);
});
</script> 