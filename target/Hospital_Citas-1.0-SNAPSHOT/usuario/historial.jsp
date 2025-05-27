<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:include page="../WEB-INF/header.jsp">
    <jsp:param name="title" value="Historial de Citas"/>
</jsp:include>

<section class="py-5">
  <div class="container">
    <div class="row">

      <!-- Sidebar -->
      <div class="col-lg-3">
        <jsp:include page="../WEB-INF/sidebar.jsp" />
      </div>

      <!-- Contenido principal -->
      <div class="col-lg-9">
        <h2 class="mt-3 mt-lg-0">Historial de Citas de ${sessionScope.usuario.nombre}</h2>
        
        <!-- Botón para agendar nueva cita -->
        <div class="d-flex justify-content-end mb-3">
          <a href="${pageContext.request.contextPath}/usuarios/agendar"
             class="btn btn-primary">
            <i class="bi bi-calendar-plus"></i> Agendar Nueva Cita
          </a>
        </div>

        <c:if test="${empty historial}">
          <div class="alert alert-info mt-4">
            No tienes citas en tu historial.
          </div>
        </c:if>

        <c:if test="${not empty historial}">
          <div class="table-responsive mt-4">
            <table class="table table-striped align-middle">
              <thead>
                <tr>
                  <th>Fecha</th>
                  <th>Hora</th>
                  <th>Medico</th>
                  <th>Especialidad</th>
                  <th>Estado</th>
                  <th>Acciones</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach var="c" items="${historial}">
                  <tr>
                    <td><fmt:formatDate value="${c.fecha}" pattern="dd/MM/yyyy"/></td>
                    <td><fmt:formatDate value="${c.hora}"  pattern="HH:mm"/></td>
                    <td><c:out value="${c.medicoNombre}"/></td>
                    <td><c:out value="${c.especialidad}"/></td>
                    <td>
                      <span class="badge bg-${
                            c.estado eq 'confirmada' ? 'success'
                          : (c.estado eq 'cancelada' ? 'danger' : 'warning')
                        }">
                        <c:out value="${
                            fn:toUpperCase(fn:substring(c.estado, 0, 1))
                          }${
                            fn:substring(c.estado, 1, fn:length(c.estado))
                          }"/>
                      </span>
                    </td>
                    <td>
                      <a href="${pageContext.request.contextPath}/usuarios/cita?action=view&id=${c.id}"
                         class="btn btn-sm btn-info">
                        Ver
                      </a>
                      <c:if test="${c.estado == 'pendiente'}">
                        <a href="${pageContext.request.contextPath}/usuarios/cita?action=cancel&id=${c.id}"
                           class="btn btn-sm btn-danger"
                           onclick="return confirm('¿Seguro que quieres cancelar esta cita?');">
                          Cancelar
                        </a>
                      </c:if>
                    </td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
        </c:if>

        <a href="${pageContext.request.contextPath}/usuarios/dashboard"
           class="btn btn-secondary mt-4">
          Volver al Dashboard
        </a>
      </div>
    </div>
  </div>
</section>

<jsp:include page="../WEB-INF/footer.jsp"/>
