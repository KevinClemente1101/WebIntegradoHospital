<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Solicitar Cita - Sistema de Citas Médicas</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="navbar.jsp" />
    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header">
                        <h3>Solicitar Cita Médica</h3>
                    </div>
                    <div class="card-body">
                        <form action="solicitar_cita" method="post">
                            <input type="hidden" name="doctor_id" value="${param.doctor_id}">
                            <div class="mb-3">
                                <label for="fecha" class="form-label">Fecha de la Cita</label>
                                <input type="date" class="form-control" id="fecha" name="fecha" required>
                            </div>
                            <div class="mb-3">
                                <label for="hora" class="form-label">Hora de la Cita</label>
                                <select class="form-select" id="hora" name="hora" required>
                                    <option value="">Seleccione una hora</option>
                                    <c:forEach items="${horas_disponibles}" var="hora">
                                        <option value="${hora}">${hora}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label for="motivo" class="form-label">Motivo de la Consulta</label>
                                <textarea class="form-control" id="motivo" name="motivo" rows="3" required></textarea>
                            </div>
                            <div class="mb-3">
                                <label for="sintomas" class="form-label">Síntomas (opcional)</label>
                                <textarea class="form-control" id="sintomas" name="sintomas" rows="3"></textarea>
                            </div>
                            <div class="d-grid gap-2">
                                <button type="submit" class="btn btn-primary">Solicitar Cita</button>
                                <a href="doctores" class="btn btn-secondary">Cancelar</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Establecer la fecha mínima como hoy
        const today = new Date().toISOString().split('T')[0];
        document.getElementById('fecha').min = today;
    </script>
</body>
</html>