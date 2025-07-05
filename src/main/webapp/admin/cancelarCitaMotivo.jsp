<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../WEB-INF/header_min_admin.jsp">
    <jsp:param name="title" value="Cancelar Cita"/>
</jsp:include>
<div class="container mt-4">
    <div class="row">
        <div class="col-md-6 offset-md-3">
            <h2>Cancelar Cita</h2>
            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>
            <form action="${pageContext.request.contextPath}/admin/cancelarCitaAdmin" method="post">
                <input type="hidden" name="cita_id" value="${citaId}" />
                <div class="mb-3">
                    <label for="motivo" class="form-label">Motivo de cancelación</label>
                    <textarea class="form-control" id="motivo" name="motivo" rows="3" required></textarea>
                </div>
                <button type="submit" class="btn btn-warning">Cancelar Cita</button>
                <a href="${pageContext.request.contextPath}/admin/citas" class="btn btn-secondary">Volver</a>
            </form>
        </div>
    </div>
</div> 