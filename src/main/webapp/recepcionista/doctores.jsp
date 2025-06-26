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
            <h2>Gestión de Doctores</h2>

            <div class="card">
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-striped table-hover">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Nombre Completo</th>
                                    <th>Especialidad</th>
                                    <th>Email</th>
                                    <th>Código Colegiatura</th>
                                    <th>Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="doctor" items="${doctores}">
                                    <tr>
                                        <td>${doctor.id}</td>
                                        <td>${doctor.usuario.nombre} ${doctor.usuario.apellido}</td>
                                        <td>${doctor.especialidad.nombre}</td>
                                        <td>${doctor.email}</td>
                                        <td>${doctor.codigoColegiatura}</td>
                                        <td>
                                            <div class="btn-group" role="group">
                                                <a href="#" class="btn btn-sm btn-outline-primary"><i class="fas fa-eye"></i></a>
                                                <a href="#" class="btn btn-sm btn-outline-info"><i class="fas fa-edit"></i></a>
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