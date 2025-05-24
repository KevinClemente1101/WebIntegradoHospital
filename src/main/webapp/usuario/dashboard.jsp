<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:include page="../WEB-INF/header.jsp">
    <jsp:param name="title" value="Panel Usuario"/>
</jsp:include>

<div class="container-fluid mt-4">
    <div class="row">
        <div class="col-md-3">
            <jsp:include page="sidebar.jsp"/>
        </div>
        <div class="col-md-9">
            <h2>Bienvenido, ${sessionScope.usuario.nombre}</h2>
            <div class="row mt-4">
                <div class="col-md-6">
                    <div class="card text-white bg-primary mb-3">
                        <div class="card-body">
                            <h5 class="card-title">Citas Pendientes</h5>
                            <p class="card-text display-4">${citasPendientes}</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="card text-white bg-success mb-3">
                        <div class="card-body">
                            <h5 class="card-title">Citas Completadas</h5>
                            <p class="card-text display-4">${citasCompletadas}</p>
                        </div>
                    </div>
                </div>
            </div>
            <h4 class="mt-5">Próximas Citas</h4>
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>Médico</th>
                            <th>Especialidad</th>
                            <th>Fecha y Hora</th>
                            <th>Estado</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="cita" items="${proximasCitas}">
                            <tr>
                                <td>${cita.doctorNombre}</td>
                                <td>${cita.especialidad}</td>
                                <td>${cita.fechaHora}</td>
                                <td>
                                    <span class="badge bg-${cita.estado == 'Pendiente' ? 'warning' : 'success'}">
                                        ${cita.estado}
                                    </span>
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