<%@ page contentType="text/html;charset=UTF-8" %>
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
            <h2>Gestión de Médicos</h2>
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
            <!-- Tabla de médicos existentes -->
            <h3 class="mt-4">Médicos Registrados</h3>
            <div class="table-responsive">
                <table class="table table-bordered table-striped">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Especialidad</th>
                            <th>Correo</th>
                            <th>Biografía</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="medico" items="${medicos}">
                            <tr>
                                <td>${medico.id}</td>
                                <td>Dr. ${medico.usuario.nombre}</td>
                                <td>${medico.especialidadNombre}</td>
                                <td>${medico.usuario.email}</td>
                                <td>${medico.biografia}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/eliminarMedico?id=${medico.id}" class="btn btn-danger btn-sm" onclick="return confirm('¿Seguro?')">Eliminar</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
