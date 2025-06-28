<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="list-group">
    <a href="dashboardD.jsp" class="list-group-item list-group-item-action <c:if test='${pageContext.request.servletPath == "/doctor/dashboardD.jsp"}'>active</c:if>'">
        <i class="fas fa-tachometer-alt"></i> Dashboard
    </a>
    <a href="perfilD.jsp" class="list-group-item list-group-item-action">
        <i class="fas fa-user"></i> Mi Perfil
    </a>
    <a href="${pageContext.request.contextPath}/doctor/horarios" class="list-group-item list-group-item-action">
        <i class="fas fa-clock"></i> Mis Horarios
    </a>
    <a href="${pageContext.request.contextPath}/doctor/citas" class="list-group-item list-group-item-action">
        <i class="fas fa-calendar-check"></i> Mis Citas
    </a>
    <a href="../logout" class="list-group-item list-group-item-action text-danger">
        <i class="fas fa-sign-out-alt"></i> Cerrar sesion
    </a>
</div>