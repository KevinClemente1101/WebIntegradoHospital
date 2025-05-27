<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../WEB-INF/header.jsp">
    <jsp:param name="title" value="Citas"/>
</jsp:include>
<div class="container-fluid mt-4">
    <div class="row">
        <div class="col-md-3">
            <jsp:include page="sidebar.jsp"/>
        </div>
        <div class="col-md-9">
            <h2>Gestión de Citas</h2>
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>Paciente</th>
                        <th>Médico</th>
                        <th>Fecha y Hora</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="cita" items="${citas}">
                        <tr>
                            <td>${cita.pacienteNombre}</td>
                            <td>${cita.doctorNombre}</td>
                            <td>${cita.fechaHora}</td>
                            <td>${cita.estado}</td>
                            <td>
                                <a href="editar_cita.jsp?id=${cita.id}" class="btn btn-primary btn-sm">Editar</a>
                                <a href="eliminarCita?id=${cita.id}" class="btn btn-danger btn-sm" onclick="return confirm('¿Seguro?')">Eliminar</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<jsp:include page="../WEB-INF/footer.jsp"/>