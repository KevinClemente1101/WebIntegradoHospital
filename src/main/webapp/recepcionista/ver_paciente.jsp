<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="../WEB-INF/header_min_recepcionista.jsp">
    <jsp:param name="title" value="Detalles del Paciente"/>
</jsp:include>

<div class="container-fluid mt-4">
    <div class="row">
        <div class="col-md-3">
            <jsp:include page="sidebar.jsp"/>
        </div>
        <div class="col-md-9">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2>Detalles del Paciente</h2>
                <div>
                    <a href="${pageContext.request.contextPath}/recepcionista/editar-paciente?id=${paciente.id}" 
                       class="btn btn-primary me-2">
                        <i class="fas fa-edit"></i> Editar Paciente
                    </a>
                    <a href="${pageContext.request.contextPath}/recepcionista/pacientes" class="btn btn-secondary">
                        <i class="fas fa-arrow-left"></i> Volver a Pacientes
                    </a>
                </div>
            </div>

            <!-- Información Personal del Paciente -->
            <div class="row">
                <div class="col-md-6">
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title mb-0">
                                <i class="fas fa-user"></i> Información Personal
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
                                <div class="col-sm-4"><strong>Fecha de Nacimiento:</strong></div>
                                <div class="col-sm-8">
                                    <c:choose>
                                        <c:when test="${not empty paciente.fechaNacimiento}">
                                            <i class="fas fa-calendar-alt text-warning"></i> ${paciente.fechaNacimiento}
                                        </c:when>
                                        <c:otherwise>
                                            <span class="text-muted"><i class="fas fa-calendar-times"></i> No registrado</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-sm-4"><strong>Género:</strong></div>
                                <div class="col-sm-8">
                                    <c:choose>
                                        <c:when test="${paciente.genero eq 'M'}">Masculino</c:when>
                                        <c:when test="${paciente.genero eq 'F'}">Femenino</c:when>
                                        <c:when test="${paciente.genero eq 'O'}">Otro</c:when>
                                        <c:otherwise>No especificado</c:otherwise>
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
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5 class="card-title mb-0">
                                <i class="fas fa-map-marker-alt"></i> Información de Contacto
                            </h5>
                        </div>
                        <div class="card-body">
                            <div class="row mb-3">
                                <div class="col-sm-4"><strong>Dirección:</strong></div>
                                <div class="col-sm-8">
                                    <c:choose>
                                        <c:when test="${not empty paciente.direccion}">
                                            <i class="fas fa-home text-primary"></i> ${paciente.direccion}
                                        </c:when>
                                        <c:otherwise>
                                            <span class="text-muted"><i class="fas fa-home"></i> No registrada</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-sm-4"><strong>Tipo de Sangre:</strong></div>
                                <div class="col-sm-8">
                                    <c:choose>
                                        <c:when test="${not empty paciente.tipo_sangre}">
                                            <i class="fas fa-tint text-danger"></i> ${paciente.tipo_sangre}
                                        </c:when>
                                        <c:otherwise>
                                            <span class="text-muted"><i class="fas fa-tint"></i> No registrado</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-sm-4"><strong>Alergias:</strong></div>
                                <div class="col-sm-8">
                                    <c:choose>
                                        <c:when test="${not empty paciente.alergias}">
                                            <i class="fas fa-exclamation-triangle text-warning"></i> ${paciente.alergias}
                                        </c:when>
                                        <c:otherwise>
                                            <span class="text-muted"><i class="fas fa-check-circle"></i> Sin alergias registradas</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                            <div class="row mb-3">
                                <div class="col-sm-4"><strong>Enfermedades Crónicas:</strong></div>
                                <div class="col-sm-8">
                                    <c:choose>
                                        <c:when test="${not empty paciente.enfermedades_cronicas}">
                                            <i class="fas fa-heartbeat text-danger"></i> ${paciente.enfermedades_cronicas}
                                        </c:when>
                                        <c:otherwise>
                                            <span class="text-muted"><i class="fas fa-heart"></i> Sin enfermedades crónicas registradas</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Historial de Citas -->
            <div class="card">
                <div class="card-header">
                    <h5 class="card-title mb-0">
                        <i class="fas fa-calendar-check"></i> Historial de Citas (${citasPaciente.size()})
                    </h5>
                </div>
                <div class="card-body">
                    <c:choose>
                        <c:when test="${empty citasPaciente}">
                            <div class="text-center text-muted py-4">
                                <i class="fas fa-calendar-times fa-3x mb-3"></i>
                                <p>Este paciente no tiene citas registradas</p>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="table-responsive">
                                <table class="table table-striped table-hover">
                                    <thead class="table-dark">
                                        <tr>
                                            <th>Fecha</th>
                                            <th>Hora</th>
                                            <th>Doctor</th>
                                            <th>Especialidad</th>
                                            <th>Tipo Consulta</th>
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
                                                    <c:if test="${not empty cita.hora}">
                                                        <fmt:formatDate value="${cita.hora}" pattern="HH:mm"/>
                                                    </c:if>
                                                </td>
                                                <td>
                                                    <c:if test="${not empty cita.doctor and not empty cita.doctor.usuario}">
                                                        Dr. ${cita.doctor.usuario.nombre} ${cita.doctor.usuario.apellido}
                                                    </c:if>
                                                    <c:if test="${empty cita.doctor and not empty cita.doctorNombre}">
                                                        ${cita.doctorNombre}
                                                    </c:if>
                                                </td>
                                                <td>
                                                    <c:if test="${not empty cita.doctor and not empty cita.doctor.especialidad}">
                                                        ${cita.doctor.especialidad.nombre}
                                                    </c:if>
                                                </td>
                                                <td>
                                                    <span class="badge bg-info">${cita.tipo_consulta}</span>
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
</div>

<jsp:include page="../WEB-INF/footer.jsp"/> 