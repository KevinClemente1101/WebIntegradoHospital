<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:include page="../WEB-INF/header.jsp">
    <jsp:param name="title" value="Nueva Especialidad"/>
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

            <form action="especialidades" method="post">
                <div class="mb-3">
                    <label for="nombre" class="form-label">Nombre de la Especialidad</label>
                    <input type="text" class="form-control" id="nombre" name="nombre" required>
                </div>
                <div class="mb-3">
                    <label for="descripcion" class="form-label">Descripción</label>
                    <textarea class="form-control" id="descripcion" name="descripcion" rows="3"></textarea>
                </div>
                <button type="submit" class="btn btn-primary">Registrar Especialidad</button>
                <a href="especialidades" class="btn btn-secondary">Cancelar</a>
            </form>
        </div>
    </div>
</div>

<jsp:include page="../WEB-INF/footer.jsp"/> 