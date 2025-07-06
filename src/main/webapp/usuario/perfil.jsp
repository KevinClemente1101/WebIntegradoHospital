<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../WEB-INF/header.jsp">
    <jsp:param name="title" value="Mi Perfil"/>
</jsp:include>

<%
    com.mycompany.hospital_citas.Usuario usuario = (com.mycompany.hospital_citas.Usuario) session.getAttribute("usuario");
%>

<div class="container mt-4 mb-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <h2>Mi Perfil</h2>
            <div class="card mb-4">
                <div class="card-body text-center">
                    <img src="${pageContext.request.contextPath}/assets/img/usuarios/<%= (usuario.getFoto() != null && !usuario.getFoto().isEmpty()) ? usuario.getFoto() : "default.jpg" %>"
                         alt="Foto de perfil"
                         class="rounded-circle mb-3"
                         style="width: 180px; height: 180px; object-fit: cover; border: 2px solid #007bff;">
                    <h4 class="mb-1"><%= usuario.getNombre() %> <%= usuario.getApellido() %></h4>
                    <p class="text-muted mb-2"><%= usuario.getEmail() %></p>
                </div>
            </div>
            <div class="card mb-4">
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/usuario/actualizarPerfil" method="post" enctype="multipart/form-data">
                        <div class="mb-3">
                            <label for="foto" class="form-label">Cambiar foto de perfil</label>
                            <input class="form-control" type="file" id="foto" name="foto" accept="image/*">
                        </div>
                        <button type="submit" class="btn btn-primary">Actualizar Foto</button>
                    </form>
                </div>
            </div>
            <div class="card mb-4">
                <div class="card-body">
                    <h5>Datos de la Cuenta</h5>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item"><strong>Nombre:</strong> <%= usuario.getNombre() %></li>
                        <li class="list-group-item"><strong>Apellido:</strong> <%= usuario.getApellido() %></li>
                        <li class="list-group-item"><strong>Email:</strong> <%= usuario.getEmail() %></li>
                        <li class="list-group-item"><strong>DNI:</strong> <%= usuario.getDni() %></li>
                        <li class="list-group-item"><strong>Teléfono:</strong> <%= usuario.getTelefono() %></li>
                        <li class="list-group-item"><strong>Dirección:</strong> <%= usuario.getDireccion() %></li>
                        <li class="list-group-item"><strong>Fecha de Nacimiento:</strong> <%= usuario.getFechaNacimiento() %></li>
                        <li class="list-group-item"><strong>Género:</strong> <%= usuario.getGenero() %></li>
                    </ul>
                </div>
            </div>
            <div class="card">
                <div class="card-body">
                    <h5>Cambiar Contraseña</h5>
                    <form action="${pageContext.request.contextPath}/usuario/actualizarPerfil" method="post">
                        <input type="hidden" name="cambiarPassword" value="1" />
                        <div class="mb-3">
                            <label for="passwordActual" class="form-label">Contraseña Actual</label>
                            <input type="password" class="form-control" id="passwordActual" name="passwordActual" required>
                        </div>
                        <div class="mb-3">
                            <label for="nuevaPassword" class="form-label">Nueva Contraseña</label>
                            <input type="password" class="form-control" id="nuevaPassword" name="nuevaPassword" required>
                        </div>
                        <div class="mb-3">
                            <label for="confirmarPassword" class="form-label">Confirmar Nueva Contraseña</label>
                            <input type="password" class="form-control" id="confirmarPassword" name="confirmarPassword" required>
                        </div>
                        <button type="submit" class="btn btn-warning">Cambiar Contraseña</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../WEB-INF/footer.jsp"/>