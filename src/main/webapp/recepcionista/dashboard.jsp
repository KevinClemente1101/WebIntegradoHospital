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
            <h2>Dashboard Recepcionista</h2>
            <p class="text-muted">Bienvenido, ${usuario.nombre} ${usuario.apellido}</p>

            <!-- Tarjetas de estadísticas -->
            <div class="row mb-4">
                <div class="col-md-3">
                    <div class="card bg-primary text-white">
                        <div class="card-body">
                            <h5 class="card-title">Citas Hoy</h5>
                            <h3 class="card-text">${citasHoy}</h3>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card bg-success text-white">
                        <div class="card-body">
                            <h5 class="card-title">Citas Pendientes</h5>
                            <h3 class="card-text">${citasPendientes}</h3>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card bg-info text-white">
                        <div class="card-body">
                            <h5 class="card-title">Pacientes Registrados</h5>
                            <h3 class="card-text">${totalPacientes}</h3>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card bg-warning text-white">
                        <div class="card-body">
                            <h5 class="card-title">Doctores Activos</h5>
                            <h3 class="card-text">${doctoresActivos}</h3>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Próximas citas -->
            <div class="row">
                <div class="col-md-8">
                    <div class="card">
                        <div class="card-header">
                            <h5>Próximas Citas</h5>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                            <th>Paciente</th>
                                            <th>Doctor</th>
                                            <th>Especialidad</th>
                                            <th>Fecha</th>
                                            <th>Hora</th>
                                            <th>Estado</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="cita" items="${proximasCitas}">
                                            <tr>
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
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card">
                        <div class="card-header">
                            <h5>Acciones Rápidas</h5>
                        </div>
                        <div class="card-body">
                            <div class="d-grid gap-2">
                                <a href="${pageContext.request.contextPath}/recepcionista/nueva-cita" class="btn btn-primary">
                                    <i class="fas fa-plus"></i> Nueva Cita
                                </a>
                                <a href="${pageContext.request.contextPath}/recepcionista/pacientes" class="btn btn-success">
                                    <i class="fas fa-user-plus"></i> Registrar Paciente
                                </a>
                                <a href="${pageContext.request.contextPath}/recepcionista/citas" class="btn btn-info">
                                    <i class="fas fa-calendar"></i> Ver Todas las Citas
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../WEB-INF/footer.jsp"/> 