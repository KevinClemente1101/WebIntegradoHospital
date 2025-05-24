<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:include page="../WEB-INF/header.jsp">
    <jsp:param name="title" value="Médicos"/>
</jsp:include>
<div class="container-fluid mt-4">
    <div class="row">
        <div class="col-md-3">
            <jsp:include page="sdebar.jsp"/>
        </div>
        <div class="col-md-9">
            <h2>Gestión de Médicos</h2>
            <a href="nuevo_medico.jsp" class="btn btn-success mb-3">Agregar Médico</a>
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>Nombre</th>
                        <th>Especialidad</th>
                        <th>Correo</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="medico" items="${medicos}">
                        <tr>
                            <td>${medico.nombre}</td>
                            <td>${medico.especialidad}</td>
                            <td>${medico.email}</td>
                            <td>
                                <a href="editar_medico.jsp?id=${medico.id}" class="btn btn-primary btn-sm">Editar</a>
                                <a href="eliminarMedico?id=${medico.id}" class="btn btn-danger btn-sm" onclick="return confirm('¿Seguro?')">Eliminar</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<jsp:include page="../WEB-INF/footer.jsp"/>