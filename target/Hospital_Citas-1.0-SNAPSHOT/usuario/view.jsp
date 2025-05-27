<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<h2>Detalle de Cita</h2>
<c:set var="c" value="${cita}" />
<ul class="list-group">
  <li class="list-group-item"><strong>ID:</strong> <c:out value="${c.id}" /></li>

  <li class="list-group-item"><strong>Fecha:</strong> <c:out value="${c.fecha}" /></li>
  <li class="list-group-item"><strong>Hora:</strong> <c:out value="${c.hora}" /></li>

  <li class="list-group-item"><strong>Estado:</strong> <c:out value="${c.estado}" /></li>

  <li class="list-group-item"><strong>ID del Paciente:</strong> <c:out value="${c.usuarioId}" /></li>
  <li class="list-group-item"><strong>ID del Doctor:</strong> <c:out value="${c.doctorId}" /></li>
</ul>

<a href="${pageContext.request.contextPath}/usuarios/dashboard" class="btn btn-secondary mt-3">
  Volver
</a>
