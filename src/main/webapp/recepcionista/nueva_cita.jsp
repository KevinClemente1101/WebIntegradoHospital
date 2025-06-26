<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
  <jsp:include page="../WEB-INF/header_min_recepcionista.jsp">
      <jsp:param name="title" value="Panel Recepcionista"/>
  </jsp:include>

<div class="container-fluid mt-4">
    <div class="row">
        <div class="col-md-3">
            <jsp:include page="sidebar.jsp"/>
        </div>
        <div class="col-md-9">
            <h2>Registrar Nueva Cita</h2>
            <p>Complete el formulario para agendar una nueva cita médica.</p>

            <c:if test="${not empty error}">
                <div class="alert alert-danger" role="alert">${error}</div>
            </c:if>
            <c:if test="${not empty successMessage}">
                <div class="alert alert-success" role="alert">${successMessage}</div>
            </c:if>

            <div class="card">
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/recepcionista/nueva-cita" method="post">
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="paciente_id" class="form-label">Paciente</label>
                                <select class="form-select" id="paciente_id" name="paciente_id" required>
                                    <option value="">Seleccione un paciente...</option>
                                    <c:forEach var="paciente" items="${pacientes}">
                                        <option value="${paciente.id}">${paciente.nombre} ${paciente.apellido} (DNI: ${paciente.dni})</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="doctor_id" class="form-label">Doctor</label>
                                <select class="form-select" id="doctor_id" name="doctor_id" required>
                                    <option value="">Seleccione un doctor...</option>
                                    <c:forEach var="doctor" items="${doctores}">
                                        <option value="${doctor.id}">Dr. ${doctor.usuario.nombre} ${doctor.usuario.apellido} (${doctor.especialidad.nombre})</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="fecha" class="form-label">Fecha de la Cita</label>
                                <input type="date" class="form-control" id="fecha" name="fecha" required>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="hora" class="form-label">Hora de la Cita</label>
                                <select class="form-select" id="hora" name="hora" required>
                                    <option value="">Seleccione una hora...</option>
                                </select>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="tipo_consulta" class="form-label">Tipo de Consulta</label>
                                <select class="form-select" id="tipo_consulta" name="tipo_consulta" required>
                                    <option value="primera_vez">Primera Vez</option>
                                    <option value="control">Control</option>
                                    <option value="emergencia">Emergencia</option>
                                </select>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="motivo" class="form-label">Motivo de la Consulta</label>
                            <textarea class="form-control" id="motivo" name="motivo" rows="3"></textarea>
                        </div>
                        
                        <div class="mb-3">
                            <label for="sintomas" class="form-label">Síntomas</label>
                            <textarea class="form-control" id="sintomas" name="sintomas" rows="3"></textarea>
                        </div>

                        <div class="d-flex justify-content-end">
                            <a href="${pageContext.request.contextPath}/recepcionista/citas" class="btn btn-secondary me-2">Cancelar</a>
                            <button type="submit" class="btn btn-primary">Registrar Cita</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../WEB-INF/footer.jsp"/>

<script>
    var contextPath = '${pageContext.request.contextPath}';

document.addEventListener('DOMContentLoaded', function() {
    const doctorSelect = document.getElementById('doctor_id');
    const fechaInput = document.getElementById('fecha');
    const horaSelect = document.getElementById('hora'); // Asumiendo que ahora será un select

    // Deshabilitar campos de fecha y hora al inicio
    fechaInput.disabled = true;
    horaSelect.disabled = true;

    doctorSelect.addEventListener('change', function() {
        // Al cambiar de doctor, limpiar y deshabilitar fecha y hora
        fechaInput.value = '';
        fechaInput.disabled = true;
        limpiarYDeshabilitarHora();
        if (this.value) {
            // Consultar los rangos de fechas disponibles del doctor
            fetch(contextPath + '/api/doctor-fechas-disponibles?doctorId=' + this.value)
                .then(response => response.json())
                .then(rangos => {
                    if (rangos.length > 0) {
                        // Si hay un solo rango, usar min y max
                        if (rangos.length === 1) {
                            fechaInput.min = rangos[0].fecha_inicio;
                            fechaInput.max = rangos[0].fecha_fin;
                        } else {
                            // Si hay varios rangos, permitir solo fechas dentro de esos rangos
                            // (opcional: podrías usar un calendario personalizado)
                            // Por ahora, usar el rango más amplio
                            const minFecha = rangos.map(r => r.fecha_inicio).sort()[0];
                            const maxFecha = rangos.map(r => r.fecha_fin).sort().reverse()[0];
                            fechaInput.min = minFecha;
                            fechaInput.max = maxFecha;
                        }
                        fechaInput.disabled = false;
                    } else {
                        fechaInput.min = '';
                        fechaInput.max = '';
                        fechaInput.disabled = true;
                        alert('El doctor no tiene horarios disponibles.');
                    }
                })
                .catch(() => {
                    fechaInput.min = '';
                    fechaInput.max = '';
                    fechaInput.disabled = true;
                    alert('Error al consultar las fechas disponibles del doctor.');
                });
        }
    });

    fechaInput.addEventListener('change', function() {
        const doctorId = doctorSelect.value;
        const fecha = this.value;

        limpiarYDeshabilitarHora();

        if (doctorId && fecha) {
            // Mostrar un indicador de carga
            horaSelect.innerHTML = '<option>Cargando...</option>';
            
            fetch(contextPath + '/api/horarios-disponibles?doctorId=' + doctorId + '&fecha=' + fecha)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Error al cargar los horarios. Código: ' + response.status);
                    }
                    return response.json();
                })
                .then(horas => {
                    horaSelect.innerHTML = ''; // Limpiar "Cargando..."
                    if (horas.length > 0) {
                        horaSelect.disabled = false;
                        horaSelect.innerHTML = '<option value="">Seleccione una hora...</option>';
                        horas.forEach(hora => {
                            const option = new Option(hora, hora);
                            horaSelect.add(option);
                        });
                    } else {
                        horaSelect.innerHTML = '<option>No hay horarios disponibles</option>';
                    }
                })
                .catch(error => {
                    console.error('Error en fetch:', error);
                    horaSelect.innerHTML = `<option>Error al cargar</option>`;
                    alert(error.message);
                });
        }
    });

    function limpiarYDeshabilitarHora() {
        horaSelect.innerHTML = '<option value="">--:--</option>';
        horaSelect.disabled = true;
    }
});
</script> 