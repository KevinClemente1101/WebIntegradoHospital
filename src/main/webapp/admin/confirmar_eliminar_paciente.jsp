<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="../WEB-INF/header_min_admin.jsp">
    <jsp:param name="title" value="Confirmar Eliminación"/>
</jsp:include>

<div class="container-fluid mt-4">
    <div class="row">
        <div class="col-md-3">
            <jsp:include page="sdebar.jsp"/>
        </div>
        <div class="col-md-9">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2>Confirmar Eliminación de Paciente</h2>
                <a href="${pageContext.request.contextPath}/admin/pacientes" class="btn btn-secondary">
                    <i class="fas fa-arrow-left"></i> Volver a Pacientes
                </a>
            </div>

            <c:if test="${tieneCitasActivas}">
                <div class="alert alert-warning" role="alert">
                    <h4 class="alert-heading">
                        <i class="fas fa-exclamation-triangle"></i> ¡Atención!
                    </h4>
                    <p>No se puede eliminar este paciente porque tiene citas activas (pendientes o confirmadas).</p>
                    <hr>
                    <p class="mb-0">Debe cancelar todas las citas activas antes de poder eliminar al paciente.</p>
                </div>
            </c:if>

            <div class="row">
                <div class="col-md-6">
                    <div class="card border-danger">
                        <div class="card-header bg-danger text-white">
                            <h5 class="card-title mb-0">
                                <i class="fas fa-user-times"></i> Información del Paciente a Eliminar
                            </h5>
                        </div>
                        <div class="card-body">
                            <div class="row mb-3">
                                <div class="col-sm-4"><strong>ID:</strong></div>
                                <div class="col-sm-8">${paciente.id}</div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-sm-4"><strong>Nombre:</strong></div>
                                <div class="col-sm-8">${paciente.nombre} ${paciente.apellido}</div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-sm-4"><strong>DNI:</strong></div>
                                <div class="col-sm-8">
                                    <i class="fas fa-id-card text-primary"></i> ${paciente.dni}
                                </div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-sm-4"><strong>Email:</strong></div>
                                <div class="col-sm-8">
                                    <i class="fas fa-envelope text-info"></i> ${paciente.email}
                                </div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-sm-4"><strong>Teléfono:</strong></div>
                                <div class="col-sm-8">
                                    <c:choose>
                                        <c:when test="${not empty paciente.telefono}">
                                            <i class="fas fa-phone text-success"></i> ${paciente.telefono}
                                        </c:when>
                                        <c:otherwise>
                                            <span class="text-muted"><i class="fas fa-phone-slash"></i> No registrado</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-sm-4"><strong>Estado:</strong></div>
                                <div class="col-sm-8">
                                    <c:choose>
                                        <c:when test="${paciente.estado}">
                                            <span class="badge bg-success">Activo</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-danger">Inactivo</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="card border-warning">
                        <div class="card-header bg-warning text-dark">
                            <h5 class="card-title mb-0">
                                <i class="fas fa-calendar-check"></i> Historial de Citas (${citasPaciente.size()})
                            </h5>
                        </div>
                        <div class="card-body">
                            <c:choose>
                                <c:when test="${empty citasPaciente}">
                                    <div class="text-center text-muted py-3">
                                        <i class="fas fa-calendar-times fa-2x mb-2"></i>
                                        <p>Este paciente no tiene citas registradas</p>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="table-responsive">
                                        <table class="table table-sm table-striped">
                                            <thead class="table-dark">
                                                <tr>
                                                    <th>Fecha</th>
                                                    <th>Estado</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="cita" items="${citasPaciente}">
                                                    <tr>
                                                        <td>
                                                            <c:if test="${not empty cita.fecha}">
                                                                <fmt:formatDate value="${cita.fecha}" pattern="dd/MM/yyyy"/>
                                                            </c:if>
                                                        </td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${cita.estado eq 'pendiente'}">
                                                                    <span class="badge bg-warning">Pendiente</span>
                                                                </c:when>
                                                                <c:when test="${cita.estado eq 'confirmada'}">
                                                                    <span class="badge bg-success">Confirmada</span>
                                                                </c:when>
                                                                <c:when test="${cita.estado eq 'completada'}">
                                                                    <span class="badge bg-primary">Completada</span>
                                                                </c:when>
                                                                <c:when test="${cita.estado eq 'cancelada'}">
                                                                    <span class="badge bg-danger">Cancelada</span>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <span class="badge bg-secondary">${cita.estado}</span>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>

            <c:if test="${not tieneCitasActivas}">
                <div class="card border-danger mt-4">
                    <div class="card-header bg-danger text-white">
                        <h5 class="card-title mb-0">
                            <i class="fas fa-exclamation-triangle"></i> Confirmación de Eliminación
                        </h5>
                    </div>
                    <div class="card-body">
                        <div class="alert alert-danger" role="alert">
                            <h4 class="alert-heading">¡ADVERTENCIA!</h4>
                            <p>Está a punto de eliminar permanentemente al paciente <strong>${paciente.nombre} ${paciente.apellido}</strong>.</p>
                            <hr>
                            <p class="mb-0">Esta acción es <strong>IRREVERSIBLE</strong> y eliminará todos los datos del paciente del sistema.</p>
                        </div>
                        
                        <form action="${pageContext.request.contextPath}/admin/eliminar-paciente" method="post">
                            <input type="hidden" name="id" value="${paciente.id}">
                            
                            <div class="mb-3">
                                <label for="confirmacion" class="form-label">
                                    Para confirmar, escriba <strong>ELIMINAR</strong> en el siguiente campo:
                                </label>
                                <input type="text" class="form-control" id="confirmacion" name="confirmacion" 
                                       placeholder="Escriba ELIMINAR para confirmar" required>
                                <div class="form-text">Esta confirmación es necesaria para prevenir eliminaciones accidentales.</div>
                            </div>
                            
                            <div class="d-flex justify-content-end gap-2">
                                <a href="${pageContext.request.contextPath}/admin/pacientes" class="btn btn-secondary">
                                    <i class="fas fa-times"></i> Cancelar
                                </a>
                                <button type="submit" class="btn btn-danger" onclick="return confirm('¿Está seguro de que desea eliminar este paciente?')">
                                    <i class="fas fa-trash"></i> Eliminar Paciente
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
</div>

<jsp:include page="../WEB-INF/footer.jsp"/> 