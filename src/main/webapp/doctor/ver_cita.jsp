<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../WEB-INF/header_min_doctor.jsp">
    <jsp:param name="title" value="Detalles de Cita"/>
</jsp:include>

<div class="container-fluid mt-4">
    <div class="row">
        <div class="col-md-3">
            <jsp:include page="sdebarD.jsp"/>
        </div>
        <div class="col-md-9">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2>Detalles de la Cita</h2>
                <a href="${pageContext.request.contextPath}/doctor/dashboard" class="btn btn-secondary">
                    <i class="fas fa-arrow-left"></i> Volver al Dashboard
                </a>
            </div>

            <c:if test="${not empty error}">
                <div class="alert alert-danger" role="alert">
                    ${error}
                </div>
            </c:if>

            <c:if test="${not empty cita}">
                <div class="row">
                    <div class="col-md-8">
                        <div class="card">
                            <div class="card-header">
                                <h5 class="card-title mb-0">Información de la Cita</h5>
                            </div>
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-md-6">
                                        <p><strong>ID de Cita:</strong> ${cita.id}</p>
                                        <p><strong>Estado:</strong> 
                                            <span class="badge bg-${cita.estado == 'Pendiente' ? 'warning' : cita.estado == 'Completada' ? 'success' : 'danger'}">
                                                ${cita.estado}
                                            </span>
                                        </p>
                                        <p><strong>Tipo de Consulta:</strong> ${cita.tipo_consulta}</p>
                                        <p><strong>Fecha:</strong> 
                                            <fmt:formatDate value="${cita.fecha}" pattern="dd/MM/yyyy"/>
                                        </p>
                                        <p><strong>Hora:</strong> 
                                            <fmt:formatDate value="${cita.hora}" pattern="HH:mm"/>
                                        </p>
                                    </div>
                                    <div class="col-md-6">
                                        <p><strong>Motivo:</strong> ${cita.motivo}</p>
                                        <p><strong>Síntomas:</strong> ${cita.sintomas}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-4">
                        <div class="card">
                            <div class="card-header">
                                <h5 class="card-title mb-0">Información del Paciente</h5>
                            </div>
                            <div class="card-body">
                                <p><strong>Nombre:</strong> ${cita.paciente.nombre} ${cita.paciente.apellido}</p>
                                <p><strong>ID Paciente:</strong> ${cita.pacienteId}</p>
                            </div>
                        </div>
                        
                        <div class="card mt-3">
                            <div class="card-header">
                                <h5 class="card-title mb-0">Información del Doctor</h5>
                            </div>
                            <div class="card-body">
                                <p><strong>Doctor:</strong> Dr. ${cita.doctor.usuario.nombre} ${cita.doctor.usuario.apellido}</p>
                                <p><strong>Especialidad:</strong> ${cita.doctor.especialidad.nombre}</p>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row mt-4">
                    <div class="col-12">
                        <div class="card">
                            <div class="card-header">
                                <h5 class="card-title mb-0">Acciones</h5>
                            </div>
                            <div class="card-body">
                                <c:if test="${cita.estado == 'Pendiente'}">
                                    <a href="${pageContext.request.contextPath}/doctor/atender-cita?id=${cita.id}" 
                                       class="btn btn-primary">
                                        <i class="fas fa-stethoscope"></i> Atender Cita
                                    </a>
                                </c:if>
                                <a href="${pageContext.request.contextPath}/doctor/citas" class="btn btn-info">
                                    <i class="fas fa-list"></i> Ver Todas las Citas
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
</div>

<jsp:include page="../WEB-INF/footer.jsp"/> 