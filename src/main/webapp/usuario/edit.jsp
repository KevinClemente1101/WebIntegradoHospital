<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form action="${pageContext.request.contextPath}/usuarios/cita" method="post">
    <input type="hidden" name="id" value="${cita.id}" />

    <div class="mb-3">
        <label for="usuarioId" class="form-label">ID Paciente:</label>
        <input type="number" class="form-control" name="usuarioId" id="usuarioId" value="${cita.usuarioId}" required />
    </div>

    <div class="mb-3">
        <label for="doctorId" class="form-label">ID Doctor:</label>
        <input type="number" class="form-control" name="doctorId" id="doctorId" value="${cita.doctorId}" required />
    </div>

    <div class="mb-3">
        <label for="fecha" class="form-label">Fecha:</label>
        <input type="date" class="form-control" name="fecha" id="fecha" value="${cita.fecha}" required />
    </div>

    <div class="mb-3">
        <label for="hora" class="form-label">Hora:</label>
        <input type="time" class="form-control" name="hora" id="hora" value="${cita.hora}" required />
    </div>

    <div class="mb-3">
        <label for="estado" class="form-label">Estado:</label>
        <select class="form-select" name="estado" id="estado" required>
            <option value="pendiente" ${cita.estado == 'pendiente' ? 'selected' : ''}>Pendiente</option>
            <option value="confirmada" ${cita.estado == 'confirmada' ? 'selected' : ''}>Confirmada</option>
            <option value="completada" ${cita.estado == 'completada' ? 'selected' : ''}>Completada</option>
            <option value="cancelada" ${cita.estado == 'cancelada' ? 'selected' : ''}>Cancelada</option>
            <option value="no_asistio" ${cita.estado == 'no_asistio' ? 'selected' : ''}>No asistió</option>
        </select>
    </div>

    <div class="d-grid">
        <button type="submit" class="btn btn-primary">Guardar Cambios</button>
    </div>
</form>
