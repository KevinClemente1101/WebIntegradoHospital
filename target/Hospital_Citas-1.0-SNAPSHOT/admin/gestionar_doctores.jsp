<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestionar Doctores - Sistema de Citas Médicas</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <jsp:include page="../WEB-INF/header.jsp"/>
    <div class="container-fluid mt-4">
        <div class="row">
            <div class="col-md-3">
                <jsp:include page="sdebar.jsp"/> <%-- Incluir el sidebar de admin --%>
            </div>
            <div class="col-md-9">
                <h2>Gestionar Doctores</h2>
                
                <%-- Mensaje de error si existe --%>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger" role="alert">
                        ${error}
                    </div>
                </c:if>

                <%-- Formulario para actualizar información de un doctor --%>
                <h3>Actualizar Doctor</h3>
                <form action="gestionarDoctores" method="post" enctype="multipart/form-data">
                    <div class="mb-3">
                        <label for="doctorId" class="form-label">Seleccionar Doctor</label>
                        <select class="form-select" id="doctorId" name="doctorId" required>
                            <option value="">Seleccione un Doctor</option>
                            <c:forEach var="doctor" items="${doctores}">
                                <option value="${doctor.id}">
                                    Dr. ${doctor.usuario.nombre} ${doctor.usuario.apellido} - 
                                    ${doctor.especialidad.nombre}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="descripcion" class="form-label">Descripción del Doctor</label>
                        <textarea class="form-control" id="descripcion" name="descripcion" rows="5"></textarea>
                    </div>
                    <div class="mb-3">
                        <label for="foto" class="form-label">Foto del Doctor</label>
                        <input type="file" class="form-control" id="foto" name="foto" accept="image/*">
                    </div>
                    <button type="submit" class="btn btn-primary">Actualizar Información del Doctor</button>
                </form>

                <%-- Tabla de doctores existentes --%>
                <h3 class="mt-4">Doctores Registrados</h3>
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nombre</th>
                                <th>Especialidad</th>
                                <th>Descripción</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="doctor" items="${doctores}">
                                <tr>
                                    <td>${doctor.id}</td>
                                    <td>Dr. ${doctor.usuario.nombre} ${doctor.usuario.apellido}</td>
                                    <td>${doctor.especialidad.nombre}</td>
                                    <td>${doctor.biografia}</td>
                                    <td>
                                        <button class="btn btn-sm btn-primary" onclick="editarDoctor(${doctor.id})">
                                            <i class="fas fa-edit"></i> Editar
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function editarDoctor(id) {
            document.getElementById('doctorId').value = id;
            document.getElementById('doctorId').scrollIntoView({ behavior: 'smooth' });
        }
    </script>
</body>
</html> 