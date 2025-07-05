<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../WEB-INF/header_min_doctor.jsp">
    <jsp:param name="title" value="Atender Cita"/>
</jsp:include>

<div class="container mt-4">
    <div class="row">
        <div class="col-md-8 offset-md-2">
            <h2>Atender Cita</h2>
            <form action="${pageContext.request.contextPath}/doctor/AtenderCitaServlet" method="post">
                <input type="hidden" name="cita_id" value="${param.id}" />
                <!-- Aquí puedes mostrar más datos de la cita si los pasas como atributos -->
                <div class="mb-3">
                    <label for="diagnostico" class="form-label">Diagnóstico</label>
                    <textarea class="form-control" id="diagnostico" name="diagnostico" rows="3" required></textarea>
                </div>
                <div class="mb-3">
                    <label for="tratamiento" class="form-label">Tratamiento</label>
                    <textarea class="form-control" id="tratamiento" name="tratamiento" rows="2" required></textarea>
                </div>
                <h4>Receta Médica</h4>
                <div id="receta-section">
                    <div class="row g-2 align-items-end">
                        <div class="col-md-3">
                            <label for="medicamento_id" class="form-label">Medicamento</label>
                            <select class="form-select" id="medicamento_id">
                                <c:forEach var="med" items="${medicamentos}">
                                    <option value="${med.id}">${med.nombre}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-2">
                            <label for="dosis" class="form-label">Dosis</label>
                            <input type="text" class="form-control" id="dosis" name="dosis_temp">
                        </div>
                        <div class="col-md-2">
                            <label for="frecuencia" class="form-label">Frecuencia</label>
                            <input type="text" class="form-control" id="frecuencia" name="frecuencia_temp">
                        </div>
                        <div class="col-md-2">
                            <label for="duracion" class="form-label">Duración</label>
                            <input type="text" class="form-control" id="duracion" name="duracion_temp">
                        </div>
                        <div class="col-md-2">
                            <label for="observaciones_receta" class="form-label">Observaciones</label>
                            <input type="text" class="form-control" id="observaciones_receta" name="observaciones_receta_temp">
                        </div>
                        <div class="col-md-1">
                            <button type="button" class="btn btn-primary" onclick="agregarMedicamento()">Agregar</button>
                        </div>
                    </div>
                    <div class="mt-3">
                        <table class="table table-sm table-bordered" id="tablaRecetas">
                            <thead>
                                <tr>
                                    <th>Medicamento</th>
                                    <th>Dosis</th>
                                    <th>Frecuencia</th>
                                    <th>Duración</th>
                                    <th>Observaciones</th>
                                    <th>Quitar</th>
                                </tr>
                            </thead>
                            <tbody></tbody>
                        </table>
                    </div>
                </div>
                <script>
                function agregarMedicamento() {
                    var medSelect = document.getElementById('medicamento_id');
                    var medId = medSelect.value.trim();
                    var medText = medSelect.options[medSelect.selectedIndex].text;
                    var dosis = document.getElementById('dosis').value.trim();
                    var frecuencia = document.getElementById('frecuencia').value.trim();
                    var duracion = document.getElementById('duracion').value.trim();
                    var observaciones = document.getElementById('observaciones_receta').value.trim();

                    // Agrega este log:
                    console.log('medId:', medId, 'dosis:', dosis, 'frecuencia:', frecuencia, 'duracion:', duracion, 'observaciones:', observaciones);

                    // Validar que todos los campos requeridos estén llenos
                    if(!medId || !dosis || !frecuencia || !duracion) {
                        alert('Completa todos los campos de la receta');
                        return;
                    }
                    var tabla = document.getElementById('tablaRecetas').getElementsByTagName('tbody')[0];
                    var row = tabla.insertRow();
                    row.innerHTML =
                        '<td><input type="hidden" name="medicamento_id[]" value="' + medId + '">' + medText + '</td>' +
                        '<td><input type="hidden" name="dosis[]" value="' + dosis + '">' + dosis + '</td>' +
                        '<td><input type="hidden" name="frecuencia[]" value="' + frecuencia + '">' + frecuencia + '</td>' +
                        '<td><input type="hidden" name="duracion[]" value="' + duracion + '">' + duracion + '</td>' +
                        '<td><input type="hidden" name="observaciones_receta[]" value="' + observaciones + '">' + observaciones + '</td>' +
                        '<td><button type="button" class="btn btn-danger btn-sm" onclick="this.closest(\'tr\').remove()">Quitar</button></td>';
                    // Limpiar campos
                    document.getElementById('dosis').value = '';
                    document.getElementById('frecuencia').value = '';
                    document.getElementById('duracion').value = '';
                    document.getElementById('observaciones_receta').value = '';
                }
                </script>
                <button type="submit" class="btn btn-success">Guardar y Generar PDF</button>
                <a href="citas" class="btn btn-secondary">Cancelar</a>
            </form>
        </div>
    </div>
</div> 