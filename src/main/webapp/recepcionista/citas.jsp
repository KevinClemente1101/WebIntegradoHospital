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
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2>Gestión de Citas</h2>
                <a href="${pageContext.request.contextPath}/recepcionista/nueva-cita" class="btn btn-primary">
                    <i class="fas fa-plus"></i> Nueva Cita
                </a>
            </div>

            <!-- Filtros -->
            <div class="card mb-4">
                <div class="card-body">
                    <form method="get" class="row g-3">
                        <div class="col-md-3">
                            <label for="fecha" class="form-label">Fecha</label>
                            <input type="date" class="form-control" id="fecha" name="fecha" value="${param.fecha}">
                        </div>
                        <div class="col-md-3">
                            <label for="doctor" class="form-label">Doctor</label>
                            <select class="form-select" id="doctor" name="doctor_id">
                                <option value="">Todos los doctores</option>
                                <c:forEach var="doctor" items="${doctores}">
                                    <option value="${doctor.id}" ${param.doctor_id == doctor.id ? 'selected' : ''}>
                                        Dr. ${doctor.usuario.nombre} ${doctor.usuario.apellido}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-3">
                            <label for="estado" class="form-label">Estado</label>
                            <select class="form-select" id="estado" name="estado">
                                <option value="">Todos los estados</option>
                                <option value="pendiente" ${param.estado == 'pendiente' ? 'selected' : ''}>Pendiente</option>
                                <option value="confirmada" ${param.estado == 'confirmada' ? 'selected' : ''}>Confirmada</option>
                                <option value="cancelada" ${param.estado == 'cancelada' ? 'selected' : ''}>Cancelada</option>
                                <option value="completada" ${param.estado == 'completada' ? 'selected' : ''}>Completada</option>
                            </select>
                        </div>
                        <div class="col-md-3">
                            <label class="form-label">&nbsp;</label>
                            <div>
                                <button type="submit" class="btn btn-primary">Filtrar</button>
                                <a href="${pageContext.request.contextPath}/recepcionista/citas" class="btn btn-secondary">Limpiar</a>
                            </div>
                        </div>
                    </form>
                </div>
            </div>

            <!-- Tabla de citas -->
            <div class="card">
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Paciente</th>
                                    <th>Doctor</th>
                                    <th>Especialidad</th>
                                    <th>Fecha</th>
                                    <th>Hora</th>
                                    <th>Estado</th>
                                    <th>Tipo</th>
                                    <th>Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="cita" items="${citas}">
                                    <tr>
                                        <td>${cita.id}</td>
                                        <td>${cita.paciente.nombre} ${cita.paciente.apellido}</td>
                                        <td>Dr. ${cita.doctor.usuario.nombre} ${cita.doctor.usuario.apellido}</td>
                                        <td>${cita.doctor.especialidad.nombre}</td>
                                        <td>${cita.fecha}</td>
                                        <td>${cita.hora}</td>
                                        <td>
                                            <span class="badge bg-${cita.estado == 'pendiente' ? 'warning' : 
                                                               cita.estado == 'confirmada' ? 'success' : 
                                                               cita.estado == 'cancelada' ? 'danger' : 'secondary'}">
                                                ${cita.estado}
                                            </span>
                                        </td>
                                        <td>${cita.tipo_consulta}</td>
                                        <td>
                                            <div class="btn-group" role="group">
                                                <button type="button" class="btn btn-sm btn-outline-primary" 
                                                        onclick="verDetalles(${cita.id})">
                                                    <i class="fas fa-eye"></i>
                                                </button>
                                                <c:if test="${cita.estado == 'pendiente'}">
                                                    <button type="button" class="btn btn-sm btn-outline-success" 
                                                            onclick="confirmarCita(${cita.id})">
                                                        <i class="fas fa-check"></i>
                                                    </button>
                                                    <button type="button" class="btn btn-sm btn-outline-danger" 
                                                            onclick="cancelarCita(${cita.id})">
                                                        <i class="fas fa-times"></i>
                                                    </button>
                                                </c:if>
                                                <button type="button" class="btn btn-sm btn-outline-info" 
                                                        onclick="editarCita(${cita.id})">
                                                    <i class="fas fa-edit"></i>
                                                </button>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Modal para detalles de cita -->
<div class="modal fade" id="detallesModal" tabindex="-1">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Detalles de la Cita</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body" id="detallesCita">
                <!-- Contenido dinámico -->
            </div>
        </div>
    </div>
</div>

<jsp:include page="../WEB-INF/footer.jsp"/>

<script>
function verDetalles(citaId) {
    // Implementar lógica para cargar detalles de la cita
    document.getElementById('detallesCita').innerHTML = 'Cargando...';
    var modal = new bootstrap.Modal(document.getElementById('detallesModal'));
    modal.show();
}

function confirmarCita(citaId) {
    if (confirm('¿Está seguro de confirmar esta cita?')) {
        // Implementar lógica para confirmar cita
        window.location.href = '${pageContext.request.contextPath}/recepcionista/confirmar-cita?id=' + citaId;
    }
}

function cancelarCita(citaId) {
    if (confirm('¿Está seguro de cancelar esta cita?')) {
        // Implementar lógica para cancelar cita
        window.location.href = '${pageContext.request.contextPath}/recepcionista/cancelar-cita?id=' + citaId;
    }
}

function editarCita(citaId) {
    window.location.href = '${pageContext.request.contextPath}/recepcionista/editar-cita?id=' + citaId;
}
</script> 