<%@ page contentType="text/html;charset=UTF-8" %>
<div class="sidebar bg-dark text-white">
    <div class="sidebar-header p-3">
        <h5>Panel Recepcionista</h5>
    </div>
    <ul class="nav flex-column">
        <li class="nav-item">
            <a class="nav-link text-white" href="${pageContext.request.contextPath}/recepcionista/dashboard">
                <i class="fas fa-tachometer-alt"></i> Dashboard
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link text-white" href="${pageContext.request.contextPath}/recepcionista/citas">
                <i class="fas fa-calendar-check"></i> Gestionar Citas
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link text-white" href="${pageContext.request.contextPath}/recepcionista/pacientes">
                <i class="fas fa-users"></i> Pacientes
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link text-white" href="${pageContext.request.contextPath}/recepcionista/doctores">
                <i class="fas fa-user-md"></i> Doctores
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link text-white" href="${pageContext.request.contextPath}/recepcionista/especialidades">
                <i class="fas fa-stethoscope"></i> Especialidades
            </a>
        </li>
        <!-- <a class="nav-link text-white" href="${pageContext.request.contextPath}/recepcionista/perfil.jsp">Perfil</a> -->
        <li class="nav-item">
            <a class="nav-link text-white" href="${pageContext.request.contextPath}/logout">
                <i class="fas fa-sign-out-alt"></i> Cerrar Sesión
            </a>
        </li>
    </ul>
</div> 