<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
  <jsp:include page="../WEB-INF/header_min_admin.jsp">
      <jsp:param name="title" value="Panel Admin"/>
  </jsp:include>

<div class="container-fluid mt-4">
    <div class="row">
        <div class="col-md-3">
            <jsp:include page="sdebar.jsp"/>
        </div>
        <div class="col-md-9">
            <h2>Registrar Nueva Especialidad</h2>
            
            <c:if test="${not empty error}">
                <div class="alert alert-danger" role="alert">
                    ${error}
                </div>
            </c:if>

            <form action="especialidades" method="post" enctype="multipart/form-data">
                <div class="mb-3">
                    <label for="nombre" class="form-label">Nombre de la Especialidad</label>
                    <input type="text" class="form-control" id="nombre" name="nombre" required>
                </div>
                <div class="mb-3">
                    <label for="descripcion" class="form-label">Descripción</label>
                    <textarea class="form-control" id="descripcion" name="descripcion" rows="3"></textarea>
                </div>
                <div class="mb-3">
                    <label for="imagen" class="form-label">Imagen de la Especialidad</label>
                    <input type="file" class="form-control" id="imagen" name="imagen" accept="image/*" required>
                    <small class="form-text text-muted">Seleccione una imagen representativa para la especialidad.</small>
                </div>
                <button type="submit" class="btn btn-primary">Registrar Especialidad</button>
                <a href="especialidades" class="btn btn-secondary">Cancelar</a>
            </form>
        </div>
    </div>
</div>

