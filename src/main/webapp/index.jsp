<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="WEB-INF/header.jsp">
    <jsp:param name="title" value="Inicio"/>
</jsp:include>

<section class="hero-section py-5">
    <div class="container">
        <div class="row align-items-center">
            <div class="col-lg-6 hero-content">
                <h1 class="display-4">Bienvenido a Hospital Carlos Lafranco La Hoz</h1>
                <p class="lead">El Hospital Carlos Lanfranco La Hoz, también conocido como Hospital Puente Piedra, 
                    es un hospital general de categoría II-2 ubicado en Puente Piedra, Lima. Brinda servicios de salud básicos y especializados, 
                    con un enfoque integral en la atención a la persona, familia y comunidad. Fue inaugurado en 1971 y ha evolucionado para atender 
                    a más de 500,000 habitantes de Puente Piedra y distritos cercanos</p>
            </div>
            <div class="col-lg-6 hero-image">
                <img src="assets/img/backgrounds/HospitalLaHoz.jpg" class="img-fluid rounded shadow mobile-bg-image" alt="Hospital Carlos Lafranco La Hoz">
            </div>
        </div>
    </div>
</section>

<!-- Especialidades Section -->
<section class="especialidades-section">
    <div class="container">
        <h2 class="text-center">Nuestras Especialidades</h2>
        <div class="row g-4">
            <c:forEach var="especialidad" items="${especialidades}">
                <div class="col-md-4 col-lg-3">
                    <div class="card especialidad-card h-100 text-center">
                        <div class="card-body">
                            <img src="assets/img/especialidades/${especialidad.nombre.toLowerCase()}.jpeg"
                                 class="img-fluid mb-3"
                                 alt="${especialidad.nombre}"
                                 style="max-height: 100px; object-fit: contain;">
                            <h5 class="card-title">${especialidad.nombre}</h5>
                            <p class="card-text">${especialidad.descripcion}</p>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</section>

<!-- Doctores Destacados -->
<section class="doctores-section">
    <div class="container">
        <h2 class="text-center">Nuestros Doctores</h2>
        <div class="row g-4">
            <c:forEach var="doctor" items="${doctores}">
                <div class="col-md-6 col-lg-3">
                    <div class="card doctor-card h-100">
                        <img src="assets/img/doctores/${doctor.usuario.foto}" class="card-img-top" alt="Dr. ${doctor.usuario.nombre} ${doctor.usuario.apellido}">
                        <div class="card-body">
                            <h5 class="card-title">Dr. ${doctor.usuario.nombre} ${doctor.usuario.apellido}</h5>
                            <p class="card-text">${doctor.especialidad.nombre}</p>
                            <p class="card-text">${doctor.biografia}</p>
                            <a href="perfil_doctor.jsp?id=${doctor.id}" class="btn btn-primary">Ver Perfil</a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</section>

<jsp:include page="WEB-INF/footer.jsp"/>