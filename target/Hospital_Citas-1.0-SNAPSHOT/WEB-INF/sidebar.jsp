<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="current" value="${pageContext.request.servletPath}" />
<div class="sidebar p-3">
    <h4 class="text-center mb-4">Mi Panel</h4>

    <ul class="nav flex-column">
        <c:choose>
            <%-- Si es ADMIN --%>
            <c:when test="${sessionScope.usuario.rol == 'admin'}">
                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/admin/dashboard.jsp" class="nav-link ${current == '/admin/dashboard.jsp' ? 'active' : ''}">
                        <i class="fas fa-tachometer-alt"></i> Panel Admin
                    </a>
                </li>
                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/admin/usuarios.jsp" class="nav-link ${current == '/admin/usuarios.jsp' ? 'active' : ''}">
                        <i class="fas fa-users-cog"></i> Gestionar Usuarios
                    </a>
                </li>
            </c:when>

            <%-- Si es DOCTOR --%>
            <c:when test="${sessionScope.usuario.rol == 'doctor'}">
                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/doctor/dashboard.jsp" class="nav-link ${current == '/doctor/dashboard.jsp' ? 'active' : ''}">
                        <i class="fas fa-user-md"></i> Panel Doctor
                    </a>
                </li>
                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/doctor/citas.jsp" class="nav-link ${current == '/doctor/citas.jsp' ? 'active' : ''}">
                        <i class="fas fa-calendar-alt"></i> Citas Asignadas
                    </a>
                </li>
            </c:when>

            <%-- Si es PACIENTE --%>
            <c:when test="${sessionScope.usuario.rol == 'paciente'}">
                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/usuarios/dashboard" class="nav-link ${current == '/usuario/dashboard.jsp' ? 'active' : ''}">
                        <i class="fas fa-tachometer-alt"></i> Dashboard
                    </a>
                </li>
                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/usuario/perfil.jsp" class="nav-link ${current == '/usuario/perfil.jsp' ? 'active' : ''}">
                        <i class="fas fa-user"></i> Mi Perfil
                    </a>
                </li>
                <li class="nav-item">
                    <a href="${pageContext.request.contextPath}/usuarios/historial" class="nav-link ${current == '/usuario/historial.jsp' ? 'active' : ''}">
                        <i class="fas fa-calendar-check"></i> Historial
                    </a>
                </li>
            </c:when>
        </c:choose>

        <!-- Visible para todos -->
        <li class="nav-item mt-4">
            <a href="${pageContext.request.contextPath}/logout" class="nav-link text-danger">
                <i class="fas fa-sign-out-alt"></i> Cerrar sesión
            </a>
        </li>
    </ul>
</div>
