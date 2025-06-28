<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
  <jsp:include page="../WEB-INF/header_min_admin.jsp">
      <jsp:param name="title" value="Panel Admin"/>
  </jsp:include>

<div class="container-fluid mt-4">
    <div class="row">
        <div class="col-md-3">
            <jsp:include page="sdebar.jsp"/>
        </div>
        <div class="col-md-9">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2>Gestión de Pacientes</h2>
                <a href="${pageContext.request.contextPath}/admin/registroUsuarios" class="btn btn-primary">
                    <i class="fas fa-plus"></i> Nuevo Paciente
                </a>
            </div>

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

            <div class="card">
                <div class="card-header">
                    <h5 class="mb-0">Lista de Pacientes</h5>
                </div>
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
                                    <th>Género</th>
                                    <th>Estado</th>
                                    <th>Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="paciente" items="${pacientes}">
                                    <tr>
                                        <td>${paciente.id}</td>
                                        <td>${paciente.nombre} ${paciente.apellido}</td>
                                        <td>${paciente.dni}</td>
                                        <td>${paciente.email}</td>
                                        <td>${paciente.telefono}</td>
                                        <td>${paciente.fechaNacimiento}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${paciente.genero == 'M'}">Masculino</c:when>
                                                <c:when test="${paciente.genero == 'F'}">Femenino</c:when>
                                                <c:when test="${paciente.genero == 'O'}">Otro</c:when>
                                                <c:otherwise>-</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${paciente.estado}">
                                                    <span class="badge bg-success">Activo</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge bg-danger">Inactivo</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <div class="btn-group" role="group">
                                                <a href="#" class="btn btn-sm btn-outline-primary" title="Ver detalles">
                                                    <i class="fas fa-eye"></i>
                                                </a>
                                                <a href="#" class="btn btn-sm btn-outline-info" title="Editar">
                                                    <i class="fas fa-edit"></i>
                                                </a>
                                                <c:choose>
                                                    <c:when test="${paciente.estado}">
                                                        <a href="#" class="btn btn-sm btn-outline-warning" title="Desactivar">
                                                            <i class="fas fa-user-slash"></i>
                                                        </a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <a href="#" class="btn btn-sm btn-outline-success" title="Activar">
                                                            <i class="fas fa-user-check"></i>
                                                        </a>
                                                    </c:otherwise>
                                                </c:choose>
                                                <a href="#" class="btn btn-sm btn-outline-danger" title="Eliminar" 
                                                   onclick="return confirm('¿Está seguro de que desea eliminar este paciente?')">
                                                    <i class="fas fa-trash"></i>
                                                </a>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    
                    <c:if test="${empty pacientes}">
                        <div class="text-center py-4">
                            <i class="fas fa-users fa-3x text-muted mb-3"></i>
                            <h5 class="text-muted">No hay pacientes registrados</h5>
                            <p class="text-muted">Comience registrando el primer paciente.</p>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../WEB-INF/footer.jsp"/> 