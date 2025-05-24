<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Doctores - Sistema de Citas Médicas</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .doctor-card {
            border: 1px solid #ccc;
            border-radius: 10px;
            padding: 15px;
            text-align: center;
        }
        .doctor-card img {
            width: 150px;
            height: 150px;
            border-radius: 50%;
            object-fit: cover;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
    <jsp:include page="navbar.jsp" />
    <div class="container mt-4">
        <h2>Nuestros Doctores</h2>
        <%-- Puedes mantener el filtro por especialidad si es necesario --%>
        <%-- 
        <div class="row mb-4">
            <div class="col-md-4">
                <select class="form-select" id="especialidad" onchange="filtrarDoctores()">
                    <option value="">Todas las especialidades</option>
                    <c:forEach items="${especialidades}" var="especialidad">
                        <option value="${especialidad.id}">${especialidad.nombre}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        --%>
        <div class="row">
            <c:forEach items="${doctores}" var="doctor">
                <div class="col-md-4 mb-4">
                    <div class="card h-100 doctor-card">
                        <img src="${doctor.foto_doctor != null && not empty doctor.foto_doctor ? doctor.foto_doctor : 'assets/img/default_doctor.png'}" class="card-img-top mx-auto" alt="Foto del Doctor">
                        <div class="card-body">
                            <h5 class="card-title">Dr. ${doctor.nombre} ${doctor.apellido}</h5>
                            <%-- Aquí podrías mostrar la especialidad si está disponible en el objeto doctor --%>
                            <%-- <p class="card-text"><strong>Especialidad:</strong> ${doctor.especialidad.nombre}</p> --%>
                            <p class="card-text">${doctor.descripcion_doctor != null ? doctor.descripcion_doctor : 'Descripción no disponible.'}</p>
                            <%-- El botón de cita por ahora no funciona --%>
                            <a href="#" class="btn btn-success disabled">Quiero una cita con él</a>
                        </div>
                    </div>
                </div>
            </c:forEach>
             <c:if test="${empty doctores}">
                <div class="col-12">
                    <div class="alert alert-info" role="alert">
                        No hay doctores disponibles en este momento.
                    </div>
                </div>
            </c:if>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <%-- Si mantuviste el filtro por especialidad, descomenta este script --%>
    <%--
    <script>
        function filtrarDoctores() {
            const especialidad = document.getElementById('especialidad').value;
            window.location.href = 'doctores?especialidad=' + especialidad;
        }
    </script>
    --%>
</body>
</html>