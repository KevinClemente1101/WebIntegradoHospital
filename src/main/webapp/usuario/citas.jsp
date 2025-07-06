<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="../WEB-INF/header.jsp" %>

<div class="container mt-4">
    <div class="row">
        <div class="col-md-12">
            <h2>Mis Citas</h2>
            <c:if test="${param.cancelada == '1'}">
                <div class="alert alert-success">Cita cancelada exitosamente.</div>
            </c:if>
            <c:if test="${param.reprogramada == '1'}">
                <div class="alert alert-success">Cita reprogramada exitosamente.</div>
            </c:if>
            <a href="nueva-cita" class="btn btn-primary">
                <i class="fas fa-plus"></i> Nueva Cita
            </a>
        </div>
    </div>
    <div class="card">
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>Doctor</th>
                            <th>Especialidad</th>
                            <th>Fecha</th>
                            <th>Hora</th>
                            <th>Estado</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="cita" items="${citas}">
                            <tr>
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
                                <td>
                                    <c:if test="${cita.estado == 'pendiente'}">
                                        <a href="${pageContext.request.contextPath}/usuario/cancelarReprogramarCitaPaciente?id=${cita.id}" class="btn btn-danger btn-sm">
                                            <i class="fas fa-times"></i> Cancelar
                                        </a>
                                    </c:if>
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