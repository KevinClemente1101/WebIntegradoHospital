<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:include page="../WEB-INF/header.jsp">
    <jsp:param name="title" value="Horarios"/>
</jsp:include>
<div class="container-fluid mt-4">
    <div class="row">
        <div class="col-md-3">
            <jsp:include page="sdebar.jsp"/>
        </div>
        <div class="col-md-9">
            <h2>Gestión de Horarios de Médicos</h2>
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>Médico</th>
                        <th>Día</th>
                        <th>Hora Inicio</th>
                        <th>Hora Fin</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="horario" items="${horarios}">
                        <tr>
                            <td>${horario.medicoNombre}</td>
                            <td>${horario.dia}</td>
                            <td>${horario.horaInicio}</td>
                            <td>${horario.horaFin}</td>
                            <td>
                                <a href="editar_horario.jsp?id=${horario.id}" class="btn btn-primary btn-sm">Editar</a>
                                <a href="eliminarHorario?id=${horario.id}" class="btn btn-danger btn-sm" onclick="return confirm('¿Seguro?')">Eliminar</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<jsp:include page="../WEB-INF/footer.jsp"/>