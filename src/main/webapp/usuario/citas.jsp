<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="../WEB-INF/header.jsp" %>

<div class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Mis Citas</h2>
        <a href="nueva-cita" class="btn btn-primary">
            <i class="fas fa-plus"></i> Nueva Cita
        </a>
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
                                        <form action="cancelarCita" method="post" style="display:inline;">
                                            <input type="hidden" name="cita_id" value="${cita.id}" />
                                            <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('¿Seguro que deseas cancelar esta cita?')">
                                                <i class="fas fa-times"></i> Cancelar
                                            </button>
                                        </form>
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