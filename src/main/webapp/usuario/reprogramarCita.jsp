<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../WEB-INF/header.jsp">
    <jsp:param name="title" value="Reprogramar Cita"/>
</jsp:include>

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card">
                <div class="card-header bg-warning text-dark">
                    <h3 class="mb-0">Reprogramar Cita</h3>
                </div>
                <div class="card-body">
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>
                    <form action="${pageContext.request.contextPath}/usuario/cancelarReprogramarCitaPaciente" method="post">
                        <input type="hidden" name="cita_id" value="${cita.id}" />
                        <input type="hidden" name="motivo" value="${motivo}" />
                        <div class="mb-3">
                            <label class="form-label">Doctor</label>
                            <input type="text" class="form-control" value="Dr. ${cita.doctor.usuario.nombre} ${cita.doctor.usuario.apellido} (${cita.doctor.especialidad.nombre})" readonly />
                        </div>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="fecha" class="form-label">Nueva Fecha</label>
                                <input type="date" class="form-control" id="fecha" name="fecha" required>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="hora" class="form-label">Nueva Hora</label>
                                <select class="form-select" id="hora" name="hora" required>
                                    <option value="">Seleccione una hora...</option>
                                </select>
                            </div>
                        </div>
                        <div class="d-flex justify-content-end">
                            <a href="${pageContext.request.contextPath}/usuario/citas" class="btn btn-secondary me-2">Cancelar</a>
                            <button type="submit" class="btn btn-warning">Reprogramar Cita</button>
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
    var doctorId = '${cita.doctor.id}';
    document.addEventListener('DOMContentLoaded', function() {
        const fechaInput = document.getElementById('fecha');
        const horaSelect = document.getElementById('hora');
        // Cargar fechas disponibles para el doctor
        fetch(contextPath + '/api/doctor-fechas-disponibles?doctorId=' + doctorId)
            .then(response => response.json())
            .then(rangos => {
                if (rangos.length > 0) {
                    if (rangos.length === 1) {
                        fechaInput.min = rangos[0].fecha_inicio;
                        fechaInput.max = rangos[0].fecha_fin;
                    } else {
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
        fechaInput.addEventListener('change', function() {
            const fecha = this.value;
            horaSelect.innerHTML = '<option>Cargando...</option>';
            if (doctorId && fecha) {
                fetch(contextPath + '/api/horarios-disponibles?doctorId=' + doctorId + '&fecha=' + fecha)
                    .then(response => {
                        if (!response.ok) {
                            throw new Error('Error al cargar los horarios. Código: ' + response.status);
                        }
                        return response.json();
                    })
                    .then(horas => {
                        horaSelect.innerHTML = '';
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
    });
</script> 