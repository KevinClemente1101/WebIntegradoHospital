<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:include page="../WEB-INF/header.jsp">
    <jsp:param name="title" value="Mis Citas"/>
</jsp:include>

<div class="container-fluid mt-4">
    <div class="row">
        <div class="col-md-3">
            <jsp:include page="sidebar.jsp"/>
        </div>
        <div class="col-md-9">
            <h2>Mis Citas</h2>
            <div class="table-responsive">
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>Paciente</th>
                            <th>Fecha y Hora</th>
                            <th>Estado</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="cita" items="${citas}">
                            <tr>
                                <td>${cita.pacienteNombre}</td>
                                <td>${cita.fechaHora}</td>
                                <td>
                                    <span class="badge bg-${cita.estado == 'Pendiente' ? 'warning' : 'success'}">
                                        ${cita.estado}
                                    </span>
                                </td>
                                <td>
                                    <a href="ver_cita.jsp?id=${cita.id}" class="btn btn-info btn-sm">
                                        <i class="fas fa-eye"></i> Ver
                                    </a>
                                    <a href="cancelarCita?id=${cita.id}" class="btn btn-danger btn-sm" onclick="return confirm('¿Seguro que desea cancelar la cita?')">
                                        <i class="fas fa-times"></i> Cancelar
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../WEB-INF/footer.jsp"/>