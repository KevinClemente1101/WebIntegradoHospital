<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Perfil del Doctor - Sistema de Citas Médicas</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="navbar.jsp" />
    <div class="container mt-4">
        <div class="row">
            <div class="col-md-4">
                <div class="card">
                    <div class="card-body text-center">
                        <img src="${doctor.foto}" alt="Foto del Doctor" class="rounded-circle mb-3" style="width: 200px; height: 200px; object-fit: cover;">
                        <h3>Dr. ${doctor.nombre} ${doctor.apellido}</h3>
                        <p class="text-muted">${doctor.especialidad.nombre}</p>
                    </div>
                </div>
            </div>
            <div class="col-md-8">
                <div class="card">
                    <div class="card-body">
                        <h4>Información Profesional</h4>
                        <hr>
                        <div class="row mb-3">
                            <div class="col-md-4"><strong>Especialidad:</strong></div>
                            <div class="col-md-8">${doctor.especialidad.nombre}</div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-md-4"><strong>Horario:</strong></div>
                            <div class="col-md-8">${doctor.horario}</div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-md-4"><strong>Consultorio:</strong></div>
                            <div class="col-md-8">${doctor.consultorio}</div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-md-4"><strong>Experiencia:</strong></div>
                            <div class="col-md-8">${doctor.experiencia} años</div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-md-4"><strong>Biografía:</strong></div>
                            <div class="col-md-8">${doctor.biografia}</div>
                        </div>
                        <div class="d-grid gap-2">
                            <a href="solicitar_cita.jsp?doctor_id=${doctor.id}" class="btn btn-primary">Solicitar Cita</a>
                            <a href="doctores" class="btn btn-secondary">Volver a Doctores</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>