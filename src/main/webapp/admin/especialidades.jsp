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
            <h2>Gestión de Especialidades</h2>
            <a href="nueva_especialidad.jsp" class="btn btn-success mb-3">Agregar Especialidad</a>
            <table class="table table-bordered table-hover align-middle">
                <thead class="table-light text-center">
                    <tr>
                        <th style="width: 5%">ID</th>
                        <th style="width: 20%">Nombre</th>
                        <th style="width: 55%">Descripción</th>
                        <th style="width: 20%">Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="especialidad" items="${especialidades}">
                        <tr>
                            <td class="text-center">${especialidad.id}</td>
                            <td>${especialidad.nombre}</td>
                            <td>${especialidad.descripcion}</td>
                            <td class="text-center">
                                <a href="editar_especialidad.jsp?id=${especialidad.id}" class="btn btn-primary btn-sm me-1">Editar</a>
                                <a href="${pageContext.request.contextPath}/eliminarEspecialidad?id=${especialidad.id}" class="btn btn-danger btn-sm" onclick="return confirm('¿Seguro?')">Eliminar</a>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty especialidades}">
                        <tr>
                            <td colspan="4" class="text-center">No hay especialidades registradas.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>
