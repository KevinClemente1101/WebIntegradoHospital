<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../WEB-INF/header.jsp" />
<div class="container mt-4">
    <div class="row">
        <div class="col-md-6 offset-md-3">
            <h2>Cancelar o Reprogramar Cita</h2>
            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>
            <form action="${pageContext.request.contextPath}/usuario/cancelarReprogramarCitaPaciente" method="post">
                <input type="hidden" name="cita_id" value="${cita.id}" />
                <div class="mb-3">
                    <label for="motivo" class="form-label">Motivo</label>
                    <textarea class="form-control" id="motivo" name="motivo" rows="3" required></textarea>
                </div>
                <button type="submit" name="accion" value="cancelar" class="btn btn-danger">Cancelar Cita</button>
                <button type="submit" name="accion" value="reprogramar" class="btn btn-warning">Reprogramar Cita</button>
                <a href="${pageContext.request.contextPath}/usuario/citas" class="btn btn-secondary">Volver</a>
            </form>
        </div>
    </div>
</div> 