<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
  <jsp:include page="../WEB-INF/header_min_doctor.jsp">
      <jsp:param name="title" value="Panel Doctor"/>
  </jsp:include>

<div class="container-fluid mt-4">
    <div class="row">
        <div class="col-md-3">
            <jsp:include page="sdebarD.jsp"/>
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
                                    <a href="atender-cita-form?id=${cita.id}" class="btn btn-success btn-sm">
                                        <i class="fas fa-stethoscope"></i> Atender
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

