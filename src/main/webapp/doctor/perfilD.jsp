<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="com.mycompany.hospital_citas.DoctorDao,com.mycompany.hospital_citas.Doctor,com.mycompany.hospital_citas.Usuario" %>
<jsp:include page="../WEB-INF/header_min_doctor.jsp">
    <jsp:param name="title" value="Mi Perfil"/>
</jsp:include>

<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    DoctorDao doctorDao = new DoctorDao();
    Doctor doctor = doctorDao.getDoctorByUsuarioId(usuario.getId());
    String biografia = doctor != null && doctor.getBiografia() != null ? doctor.getBiografia() : "";
    request.setAttribute("biografia", biografia);
%>

<div class="container mt-4 mb-5">
    <div class="row">
        <div class="col-md-3">
            <jsp:include page="sdebarD.jsp"/>
        </div>
        <div class="col-md-9">
            <h2>Mi Perfil</h2>
            <div class="card mb-4">
                <div class="card-body text-center">
                    <img src="${pageContext.request.contextPath}/assets/img/doctores/${sessionScope.usuario.foto != null && !sessionScope.usuario.foto.isEmpty() ? sessionScope.usuario.foto : 'default.jpg'}"
                         alt="Foto de perfil"
                         class="rounded-circle mb-3"
                         style="width: 180px; height: 180px; object-fit: cover; border: 2px solid #007bff;">
                    <h4 class="mb-1">Dr. ${sessionScope.usuario.nombre} ${sessionScope.usuario.apellido}</h4>
                    <p class="text-muted mb-2">${sessionScope.usuario.email}</p>
                </div>
            </div>
            <div class="card">
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/doctor/actualizarPerfil" method="post" enctype="multipart/form-data">
                        <div class="mb-3">
                            <label for="foto" class="form-label">Cambiar foto de perfil</label>
                            <input class="form-control" type="file" id="foto" name="foto" accept="image/*">
                        </div>
                        <div class="mb-3">
                            <label for="biografia" class="form-label">Biografía</label>
                            <textarea class="form-control" id="biografia" name="biografia" rows="4" maxlength="500">${biografia}</textarea>
                        </div>
                        <button type="submit" class="btn btn-primary">Guardar Cambios</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../WEB-INF/footer.jsp"/>