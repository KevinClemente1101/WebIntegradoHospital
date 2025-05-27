<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:include page="../WEB-INF/header.jsp">
    <jsp:param name="title" value="Panel Usuario"/>
</jsp:include>

<section class="py-5">
    <div class="container">
        <div class="row">
            <div class="col-lg-3">
                <jsp:include page="sidebar.jsp" />
            </div>
            <div class="col-lg-9">

                <h2 class="mt-3 mt-lg-0">Bienvenido, ${sessionScope.usuario.nombre}</h2>

                <div class="row mt-4">
                    <!-- Citas pendientes -->
                    <div class="col-md-6">
                        <div class="card text-white bg-warning mb-3">
                            <div class="card-body">
                                <h5 class="card-title">Citas Pendientes</h5>
                                <p class="card-text display-4">${citasPendientes}</p>
                            </div>
                        </div>
                    </div>

                    <!-- Citas completadas -->        
                    <div class="col-md-6">
                        <div class="card text-white bg-success mb-3">
                            <div class="card-body">
                                <h5 class="card-title">Citas Completadas</h5>
                                <p class="card-text display-4">${citasCompletadas}</p>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Próximas citas -->
                <c:if test="${not empty citas}">
                    <h4 class="mt-5">Próximas Citas</h4>
                    <div class="table-responsive">
                        <table class="table table-striped">
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
                                <c:forEach var="c" items="${citas}">
                                  <tr>
                                    <td><fmt:formatDate value="${c.fecha}" pattern="dd/MM/yyyy"/></td>
                                    <td><fmt:formatDate value="${c.hora}"  pattern="HH:mm"/></td>
                                    <td><c:out value="${c.medicoNombre}"/></td>
                                    <td><c:out value="${c.especialidad}"/></td>
                                    <td>
                                        <span class="badge bg-${c.estado eq 'completada' ? 'success' : (c.estado eq 'cancelada' ? 'danger' : 'warning')}">
                                          <c:out value="${
                                              fn:toUpperCase(fn:substring(c.estado, 0, 1))
                                            }${
                                              fn:substring(c.estado, 1, fn:length(c.estado))
                                            }"/>
                                        </span>
                                    </td>
                                    <td>
                                        <a href="cita?action=view&id=${c.id}"   class="btn btn-sm btn-info">Ver</a>
                                        <button class="btn btn-sm btn-warning" 
                                                data-bs-toggle="modal" 
                                                data-bs-target="#editarModal" 
                                                data-id="${c.id}">
                                          Editar
                                        </button>

                                        <a href="cita?action=cancel&id=${c.id}" class="btn btn-sm btn-danger"
                                           onclick="return confirm('¿Seguro que quieres cancelar esta cita?');">
                                          Cancelar
                                        </a>
                                    </td>
                                  </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</section>
                            
                            <!-- Modal para editar cita -->
<div class="modal fade" id="editarModal" tabindex="-1" aria-labelledby="editarModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="editarModalLabel">Editar Cita</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
      </div>
      <div class="modal-body">
        <div id="formEditarCitaContent">
          <!-- Aquí se carga dinámicamente el contenido del formulario -->
          <div class="text-center">
            <div class="spinner-border" role="status">
              <span class="visually-hidden">Cargando...</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>


<jsp:include page="../WEB-INF/footer.jsp"/>