<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:include page="../WEB-INF/header.jsp">
    <jsp:param name="title" value="Especialidades"/>
</jsp:include>
<div class="container-fluid mt-4">
    <div class="row">
        <div class="col-md-3">
            <jsp:include page="sdebar.jsp"/>
        </div>
        <div class="col-md-9">
            <h2>Gestión de Especialidades</h2>
            <a href="nueva_especialidad.jsp" class="btn btn-success mb-3">Agregar Especialidad</a>
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>Nombre</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="especialidad" items="${especialidades}">
                        <tr>
                            <td>${especialidad.nombre}</td>
                            <td>
                                <a href="editar_especialidad.jsp?id=${especialidad.id}" class="btn btn-primary btn-sm">Editar</a>
                                <a href="eliminarEspecialidad?id=${especialidad.id}" class="btn btn-danger btn-sm" onclick="return confirm('¿Seguro?')">Eliminar</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<jsp:include page="../WEB-INF/footer.jsp"/>