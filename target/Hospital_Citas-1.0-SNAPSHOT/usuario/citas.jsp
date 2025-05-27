<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:include page="../WEB-INF/header.jsp">
    <jsp:param name="title" value="Panel Paciente"/>
</jsp:include>

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card shadow">
                <div class="card-header bg-danger text-white">
                    <h3 class="text-center mb-0">Cancelar Cita</h3>
                </div>
                <div class="card-body">
                    <form action="cancelarCita" method="post">
                        <input type="hidden" name="cita_id" value="${param.id}">
                        <p>¿Estás seguro que deseas cancelar esta cita?</p>
                        <div class="d-grid gap-2">
                            <button type="submit" class="btn btn-danger">Sí, cancelar</button>
                            <a href="citas.jsp" class="btn btn-secondary">No, volver</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../WEB-INF/footer.jsp"/>