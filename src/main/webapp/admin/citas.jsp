<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                                <a href="cancelarCitaAdmin?id=${cita.id}" class="btn btn-warning btn-sm">Cancelar</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
