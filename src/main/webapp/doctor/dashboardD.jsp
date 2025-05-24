<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:include page="../WEB-INF/header.jsp">
    <jsp:param name="title" value="Panel Doctor"/>
</jsp:include>

<div class="container-fluid mt-4">
    <div class="row">
        <div class="col-md-3">
            <jsp:include page="sdebarD.jsp"/>
        </div>
        <div class="col-md-9">
            <h2>Bienvenido, Dr. ${sessionScope.usuario.nombre}</h2>
            
            <!-- Resumen de citas -->
            <div class="row mt-4">
                <div class="col-md-4">
                    <div class="card text-white bg-primary mb-3">
                        <div class="card-body">
                            <h5 class="card-title">Citas Hoy</h5>
                            <p class="card-text display-4">${citasHoy}</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card text-white bg-success mb-3">
                        <div class="card-body">
                            <h5 class="card-title">Citas Pendientes</h5>
                            <p class="card-text display-4">${citasPendientes}</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card text-white bg-info mb-3">
                        <div class="card-body">
                            <h5 class="card-title">Total Citas</h5>
                            <p class="card-text display-4">${totalCitas}</p>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Próximas citas -->
            <h4 class="mt-5">Próximas Citas</h4>
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>Paciente</th>
                            <th>Fecha y Hora</th>
                            <th>Estado</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="cita" items="${proximasCitas}">
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