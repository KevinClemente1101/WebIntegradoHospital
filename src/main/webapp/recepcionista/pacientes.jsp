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
                <h2>Gestión de Pacientes</h2>
                <a href="${pageContext.request.contextPath}/recepcionista/registrar_paciente.jsp" class="btn btn-primary">
                    <i class="fas fa-plus"></i> Nuevo Paciente
                </a>
            </div>

            <!-- Mensajes de éxito y error -->
            <c:if test="${not empty success}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <i class="fas fa-check-circle"></i> ${success}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <i class="fas fa-exclamation-circle"></i> ${error}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            </c:if>

            <div class="card">
                <div class="card-body">

                    <div class="table-responsive">
                        <table class="table table-striped table-hover">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Nombre Completo</th>
                                    <th>DNI</th>
                                    <th>Email</th>
                                    <th>Teléfono</th>
                                    <th>Fecha de Nacimiento</th>
                                    <th>Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="paciente" items="${pacientes}">
                                    <tr>
                                        <td>${paciente.id}</td>
                                        <td>${paciente.nombre} ${paciente.apellido}</td>
                                        <td><i class="fas fa-id-card text-primary"></i> ${paciente.dni}</td>
                                        <td><i class="fas fa-envelope text-info"></i> ${paciente.email}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty paciente.telefono}">
                                                    <i class="fas fa-phone text-success"></i> ${paciente.telefono}
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="text-muted"><i class="fas fa-phone-slash"></i> No registrado</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty paciente.fechaNacimiento}">
                                                    <i class="fas fa-calendar-alt text-warning"></i> ${paciente.fechaNacimiento}
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="text-muted"><i class="fas fa-calendar-times"></i> No registrado</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <div class="btn-group" role="group">
                                                <a href="${pageContext.request.contextPath}/recepcionista/ver-paciente?id=${paciente.id}" 
                                                   class="btn btn-sm btn-outline-primary" title="Ver detalles">
                                                    <i class="fas fa-eye"></i>
                                                </a>
                                                <a href="${pageContext.request.contextPath}/recepcionista/editar-paciente?id=${paciente.id}" 
                                                   class="btn btn-sm btn-outline-info" title="Editar paciente">
                                                    <i class="fas fa-edit"></i>
                                                </a>
                                                <a href="${pageContext.request.contextPath}/recepcionista/eliminar-paciente?id=${paciente.id}" 
                                                   class="btn btn-sm btn-outline-danger" title="Eliminar paciente">
                                                    <i class="fas fa-trash"></i>
                                                </a>
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

<jsp:include page="../WEB-INF/footer.jsp"/> 