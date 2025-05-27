<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="list-group">
    <a href="dashboard.jsp" class="list-group-item list-group-item-action <c:if test='${pageContext.request.servletPath == "/admin/dashboard.jsp"}'>active</c:if>'">
        <i class="fas fa-tachometer-alt"></i> Dashboard
    </a>
    <a href="medicos.jsp" class="list-group-item list-group-item-action">
        <i class="fas fa-user-md"></i> M�dicos
    </a>
    <a href="horarios.jsp" class="list-group-item list-group-item-action">
        <i class="fas fa-clock"></i> Horarios
    </a>
    <a href="citas.jsp" class="list-group-item list-group-item-action">
        <i class="fas fa-calendar-check"></i> Citas
    </a>
    <a href="especialidades.jsp" class="list-group-item list-group-item-action">
        <i class="fas fa-stethoscope"></i> Especialidades
    </a>
    <a href="../logout" class="list-group-item list-group-item-action text-danger">
        <i class="fas fa-sign-out-alt"></i> Cerrar sesi�n
    </a>
</div>