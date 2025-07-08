<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../WEB-INF/header_min_doctor.jsp">
    <jsp:param name="title" value="Panel Doctor"/>
</jsp:include>

<div class="container-fluid mt-4">
    <div class="row">
        <div class="col-md-3">
            <jsp:include page="sdebarD.jsp"/>
        </div>
        <div class="col-md-9">
            <h2>Bienvenido, Dr. ${sessionScope.usuario.nombre}</h2>
            
            <!-- Resumen de citas -->
            <div class="row mt-4">
                <div class="col-md-4">
                    <div class="card text-white bg-primary mb-3">
                        <div class="card-body">
                            <h5 class="card-title">Citas Hoy</h5>
                            <p class="card-text display-4">${citasHoy}</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card text-white bg-success mb-3">
                        <div class="card-body">
                            <h5 class="card-title">Citas Pendientes</h5>
                            <p class="card-text display-4">${citasPendientes}</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card text-white bg-info mb-3">
                        <div class="card-body">
                            <h5 class="card-title">Total Citas</h5>
                            <p class="card-text display-4">${totalCitas}</p>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Próximas citas -->
            <h4 class="mt-5">Próximas Citas</h4>
            <div class="table-responsive">
                <table class="table table-striped table-hover">
                    <thead class="table-dark">
                        <tr>
                            <th>Fecha</th>
                            <th>Hora</th>
                            <th>Paciente</th>
                            <th>Tipo Consulta</th>
                            <th>Estado</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${empty proximasCitas}">
                                <tr>
                                    <td colspan="6" class="text-center text-muted">
                                        <i class="fas fa-calendar-times"></i> No hay próximas citas programadas
                                    </td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="cita" items="${proximasCitas}">
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
                                            <c:if test="${not empty cita.paciente}">
                                                ${cita.paciente.nombre} ${cita.paciente.apellido}
                                            </c:if>
                                            <c:if test="${empty cita.paciente and not empty cita.pacienteNombre}">
                                                ${cita.pacienteNombre}
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
                                        <td>
                                            <div class="btn-group" role="group">
                                                <a href="${pageContext.request.contextPath}/doctor/ver-cita?id=${cita.id}" 
                                                   class="btn btn-sm btn-outline-primary" title="Ver detalles">
                                                    <i class="fas fa-eye"></i>
                                                </a>
                                                <c:if test="${cita.estado eq 'pendiente' or cita.estado eq 'confirmada'}">
                                                    <a href="${pageContext.request.contextPath}/doctor/atender-cita-form?id=${cita.id}" 
                                                       class="btn btn-sm btn-outline-success" title="Atender cita">
                                                        <i class="fas fa-stethoscope"></i>
                                                    </a>
                                                </c:if>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

