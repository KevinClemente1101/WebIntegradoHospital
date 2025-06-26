<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../WEB-INF/header.jsp">
    <jsp:param name="title" value="Registrar Paciente"/>
</jsp:include>

<div class="container-fluid mt-4">
    <div class="row">
        <div class="col-md-3">
            <jsp:include page="sidebar.jsp"/>
        </div>
        <div class="col-md-9">
            <div class="card">
                <div class="card-header bg-primary text-white">
                    <h4 class="mb-0">Registrar Nuevo Paciente</h4>
                </div>
                <div class="card-body">
                    <% if(request.getAttribute("error") != null) { %>
                        <div class="alert alert-danger">
                            <%= request.getAttribute("error") %>
                        </div>
                    <% } %>
                    <form action="../verificar-correo" method="post" autocomplete="off">
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="nombre" class="form-label">Nombre</label>
                                <input type="text" class="form-control" name="nombre" id="nombre" placeholder="Nombre" required>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="apellido" class="form-label">Apellido</label>
                                <input type="text" class="form-control" name="apellido" id="apellido" placeholder="Apellido" required>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="dni" class="form-label">Número de Documento</label>
                                <input type="text" class="form-control" name="dni" id="dni" placeholder="Número de Documento" pattern="[0-9]{8}" maxlength="8" required>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="fecha_nacimiento" class="form-label">Fecha de Nacimiento</label>
                                <input type="date" class="form-control" name="fecha_nacimiento" id="fecha_nacimiento" required>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="email" class="form-label">Correo</label>
                                <input type="email" class="form-control" name="email" id="email" placeholder="Correo" required>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="telefono" class="form-label">Celular</label>
                                <input type="text" class="form-control" name="telefono" id="telefono" placeholder="Celular">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="password" class="form-label">Contraseña</label>
                                <input type="password" class="form-control" name="password" id="password" placeholder="Contraseña" minlength="8" required>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="confirmar_password" class="form-label">Confirma Contraseña</label>
                                <input type="password" class="form-control" name="confirmar_password" id="confirmar_password" placeholder="Confirma Contraseña" minlength="8" required>
                            </div>
                        </div>
                        <div class="d-grid gap-2">
                            <button type="submit" class="btn btn-primary">Registrar Paciente</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../WEB-INF/footer.jsp"/> 