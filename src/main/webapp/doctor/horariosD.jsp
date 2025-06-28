<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
  <jsp:include page="../WEB-INF/header_min_doctor.jsp">
      <jsp:param name="title" value="Panel Doctor"/>
  </jsp:include>

<div class="container-fluid mt-4">
    <div class="row">
        <div class="col-md-3">
            <jsp:include page="sdebarD.jsp"/>
        </div>
        <div class="col-md-9">
            <h2>Mi Horario de Trabajo</h2>
            <p>Define los días y las horas en los que estarás disponible para atender citas.</p>

            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger" role="alert">${errorMessage}</div>
            </c:if>
            <c:if test="${not empty successMessage}">
                <div class="alert alert-success" role="alert">${successMessage}</div>
            </c:if>

            <!-- Formulario para añadir nuevo horario -->
            <div class="card mb-4">
                <div class="card-header">
                    <h5>Añadir Nuevo Bloque Horario</h5>
                </div>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/doctor/horarios" method="post">
                        <div class="row align-items-end">
                            <div class="col-md-3 mb-3">
                                <label for="fecha_inicio" class="form-label">Desde Fecha</label>
                                <input type="date" class="form-control" id="fecha_inicio" name="fecha_inicio" required>
                            </div>
                            <div class="col-md-3 mb-3">
                                <label for="fecha_fin" class="form-label">Hasta Fecha</label>
                                <input type="date" class="form-control" id="fecha_fin" name="fecha_fin" required>
                            </div>
                            <div class="col-md-2 mb-3">
                                <label for="hora_inicio" class="form-label">Hora Inicio</label>
                                <input type="time" class="form-control" id="hora_inicio" name="hora_inicio" required>
                            </div>
                            <div class="col-md-2 mb-3">
                                <label for="hora_fin" class="form-label">Hora Fin</label>
                                <input type="time" class="form-control" id="hora_fin" name="hora_fin" required>
                            </div>
                            <div class="col-md-2 mb-3">
                                 <button type="submit" class="btn btn-primary w-100">
                                    <i class="fas fa-plus"></i> Añadir
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>

            <!-- Tabla de horarios existentes -->
            <div class="card">
                <div class="card-header">
                    <h5>Mis Horarios Configurados</h5>
                </div>
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-striped table-hover">
                            <thead>
                                <tr>
                                    <th>Día de la Semana</th>
                                    <th>Hora de Inicio</th>
                                    <th>Hora de Fin</th>
                                    <th>Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="horario" items="${horarios}">
                                    <tr>
                                        <td>${horario.diasFormateados}</td>
                                        <td>${horario.horaInicio}</td>
                                        <td>${horario.horaFin}</td>
                                        <td>
                                            <form action="${pageContext.request.contextPath}/doctor/horarios" method="post" style="display:inline;">
                                                <input type="hidden" name="action" value="delete">
                                                <input type="hidden" name="horario_ids" value="${horario.idsParaBorrar}">
                                                <button type="submit" class="btn btn-sm btn-outline-danger" 
                                                        onclick="return confirm('¿Estás seguro de que quieres eliminar este bloque de horario?');">
                                                    <i class="fas fa-trash"></i>
                                                </button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                                <c:if test="${empty horarios}">
                                    <tr>
                                        <td colspan="4" class="text-center">Aún no has configurado ningún horario.</td>
                                    </tr>
                                </c:if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

