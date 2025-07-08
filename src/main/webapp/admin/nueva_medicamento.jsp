<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../WEB-INF/header_min_admin.jsp">
    <jsp:param name="title" value="Agregar Medicamento"/>
</jsp:include>
<div class="container mt-4">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <h2>Agregar Medicamento</h2>
            <form action="${pageContext.request.contextPath}/admin/medicamentos" method="post">
                <input type="hidden" name="agregar" value="1" />
                <div class="mb-3">
                    <label for="nombre" class="form-label">Nombre</label>
                    <input type="text" class="form-control" id="nombre" name="nombre" required />
                </div>
                <div class="mb-3">
                    <label for="descripcion" class="form-label">Descripción</label>
                    <textarea class="form-control" id="descripcion" name="descripcion" required></textarea>
                </div>
                <div class="mb-3">
                    <label for="dosis" class="form-label">Dosis</label>
                    <input type="text" class="form-control" id="dosis" name="dosis" required />
                </div>
                <div class="mb-3">
                    <label for="frecuencia" class="form-label">Frecuencia</label>
                    <input type="text" class="form-control" id="frecuencia" name="frecuencia" required />
                </div>
                <div class="mb-3">
                    <label for="duracion" class="form-label">Duración</label>
                    <input type="text" class="form-control" id="duracion" name="duracion" required />
                </div>
                <div class="mb-3">
                    <label for="contraindicaciones" class="form-label">Contraindicaciones</label>
                    <textarea class="form-control" id="contraindicaciones" name="contraindicaciones" required></textarea>
                </div>
                <button type="submit" class="btn btn-success">Guardar</button>
                <a href="medicamentos.jsp" class="btn btn-secondary">Cancelar</a>
            </form>
        </div>
    </div>
</div> 