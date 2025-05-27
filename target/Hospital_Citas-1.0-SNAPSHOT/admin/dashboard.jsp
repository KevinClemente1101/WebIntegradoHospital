<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../WEB-INF/header.jsp">
    <jsp:param name="title" value="Panel Admin"/>
</jsp:include>
<div class="container-fluid mt-4">
    <div class="row">
        <div class="col-md-3">
            <jsp:include page="sdebar.jsp"/>
        </div>
        <div class="col-md-9">
            <h2>Bienvenido, ${sessionScope.usuario.nombre}</h2>
            <div class="row mt-4">
                <div class="col-md-4">
                    <div class="card text-white bg-primary mb-3">
                        <div class="card-body">
                            <h5 class="card-title">Médicos</h5>
                            <p class="card-text">${medicosCount}</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card text-white bg-success mb-3">
                        <div class="card-body">
                            <h5 class="card-title">Citas</h5>
                            <p class="card-text">${citasCount}</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card text-white bg-info mb-3">
                        <div class="card-body">
                            <h5 class="card-title">Especialidades</h5>
                            <p class="card-text">${especialidadesCount}</p>
                        </div>
                    </div>
                </div>
            </div>
            <h4 class="mt-5">Últimas citas</h4>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Paciente</th>
                        <th>Médico</th>
                        <th>Fecha</th>
                        <th>Estado</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="cita" items="${ultimasCitas}">
                        <tr>
                            <td>${cita.pacienteNombre}</td>
                            <td>${cita.doctorNombre}</td>
                            <td>${cita.fechaHora}</td>
                            <td>${cita.estado}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<jsp:include page="../WEB-INF/footer.jsp"/>