<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../WEB-INF/header.jsp">
    <jsp:param name="title" value="Médicos"/>
</jsp:include>
<div class="container-fluid mt-4">
    <div class="row">
        <div class="col-md-3">
            <jsp:include page="sdebar.jsp"/>
        </div>
        <div class="col-md-9">
            <h2>Gestión de Médicos</h2>
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
            <!-- Formulario para actualizar información de un médico -->
            <h3>Actualizar Médico</h3>
            <form action="medicos" method="post" enctype="multipart/form-data">
                <div class="mb-3">
                    <label for="medicoId" class="form-label">Seleccionar Médico</label>
                    <select class="form-select" id="medicoId" name="medicoId" required>
                        <option value="">Seleccione un Médico</option>
                        <c:forEach var="medico" items="${medicos}">
                            <option value="${medico.id}">
                                Dr. ${medico.nombre} - ${medico.especialidadNombre}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="mb-3">
                    <label for="biografia" class="form-label">Biografía</label>
                    <textarea class="form-control" id="biografia" name="biografia" rows="5"></textarea>
                </div>
                <div class="mb-3">
                    <label for="foto" class="form-label">Foto del Médico</label>
                    <input type="file" class="form-control" id="foto" name="foto" accept="image/*">
                </div>
                <button type="submit" class="btn btn-primary">Actualizar Información del Médico</button>
            </form>
            <!-- Tabla de médicos existentes -->
            <h3 class="mt-4">Médicos Registrados</h3>
            <div class="table-responsive">
                <table class="table table-bordered table-striped">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Especialidad</th>
                            <th>Correo</th>
                            <th>Biografía</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="medico" items="${medicos}">
                            <tr>
                                <td>${medico.id}</td>
                                <td>Dr. ${medico.nombre}</td>
                                <td>${medico.especialidadNombre}</td>
                                <td>${medico.email}</td>
                                <td>${medico.biografia}</td>
                                <td>
                                    <button class="btn btn-sm btn-primary" onclick="editarMedico('${medico.id}')">
                                        <i class="fas fa-edit"></i> Editar
                                    </button>
                                    <a href="eliminarMedico?id=${medico.id}" class="btn btn-danger btn-sm" onclick="return confirm('¿Seguro?')">Eliminar</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../WEB-INF/footer.jsp"/>
<script>
    function editarMedico(id) {
        document.getElementById('medicoId').value = id;
        document.getElementById('medicoId').scrollIntoView({ behavior: 'smooth' });
    }
</script>