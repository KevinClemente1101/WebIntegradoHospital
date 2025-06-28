<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="/WEB-INF/header.jsp">
    <jsp:param name="title" value="Nuestros Doctores"/>
</jsp:include>

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

<div class="container mt-4">
    <h2>Nuestros Doctores</h2>
    
    <!-- Debug: Mostrar información de debugging -->
    
    
    <div class="row">
        <c:forEach items="${doctores}" var="doctor">
            <!-- DEBUG: Imprimir el objeto doctor para depuración -->
            

            <div class="col-md-4 mb-4">
                <div class="card h-100 doctor-card">
                    <img src="${pageContext.request.contextPath}/assets/img/doctores/${doctor.usuario.foto != null && not empty doctor.usuario.foto ? doctor.usuario.foto : 'default.jpg'}" class="card-img-top mx-auto" alt="Foto del Doctor">
                    <div class="card-body">
                        <h5 class="card-title">Dr. ${doctor.usuario.nombre} ${doctor.usuario.apellido}</h5>
                        <p class="card-text"><strong>Especialidad:</strong> ${doctor.especialidadNombre}</p>
                        <p class="card-text">${doctor.biografia != null ? doctor.biografia : 'Biografía no disponible.'}</p>
                        <a href="${pageContext.request.contextPath}/solicitarCita?doctorId=${doctor.id}" class="btn btn-primary">Solicitar Cita</a>
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
</div


<%-- Si mantuviste el filtro por especialidad, descomenta este script --%>
<%--
<script>
    function filtrarDoctores() {
        const especialidad = document.getElementById('especialidad').value;
        window.location.href = 'doctores?especialidad=' + especialidad;
    }
</script>
--%>

<jsp:include page="/WEB-INF/footer.jsp"/>