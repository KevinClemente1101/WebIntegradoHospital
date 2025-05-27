<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:include page="../WEB-INF/header.jsp">
    <jsp:param name="title" value="Agendar Nueva Cita"/>
</jsp:include>

<section class="py-5">
  <div class="container">
    <div class="row">
      <div class="col-lg-3">
        <jsp:include page="../WEB-INF/sidebar.jsp" />
      </div>
      <div class="col-lg-9">
        <h2>Agendar Nueva Cita</h2>

        <c:if test="${not empty error}">
          <div class="alert alert-danger">${error}</div>
        </c:if>

        <!-- Paso 1: Especialidad -->
        <form method="get" action="${pageContext.request.contextPath}/usuario/agendar">
          <div class="row mb-3">
            <div class="col-md-8">
              <select name="especialidad_id" class="form-select" required>
                <option value="">-- Seleccione especialidad --</option>
                <c:forEach var="e" items="${especialidades}">
                  <option value="${e.id}" ${param.especialidad_id==e.id? 'selected':''}>
                    ${e.nombre}
                  </option>
                </c:forEach>
              </select>
            </div>
            <div class="col-md-4 d-flex align-items-end">
              <button class="btn btn-primary w-100">Ver Médicos</button>
            </div>
          </div>
        </form>

        <!-- Paso 2: Médico -->
        <c:if test="${not empty medicos}">
          <form method="get" action="${pageContext.request.contextPath}/usuario/agendar">
            <input type="hidden" name="especialidad_id" value="${param.especialidad_id}"/>
            <div class="row mb-3">
              <div class="col-md-8">
                <select name="medico_id" class="form-select" required>
                  <option value="">-- Seleccione médico --</option>
                  <c:forEach var="m" items="${medicos}">
                    <option value="${m.id}" ${param.medico_id==m.id? 'selected':''}>
                      ${m.nombre}
                    </option>
                  </c:forEach>
                </select>
              </div>
              <div class="col-md-4 d-flex align-items-end">
                <button class="btn btn-primary w-100">Ver Horarios</button>
              </div>
            </div>
          </form>
        </c:if>
        
        <!-- Paso 3: Fecha -->
        <c:if test="${not empty horarios}">
            <form method="get" action="${pageContext.request.contextPath}/usuario/agendar">
              <input type="hidden" name="especialidad_id" value="${param.especialidad_id}"/>
              <input type="hidden" name="medico_id" value="${param.medico_id}"/>
              <div class="row mb-3">
                <div class="col-md-8">
                  <input type="date" name="fecha" class="form-control" min="${today}" value="${param.fecha}" required/>
                </div>
                <div class="col-md-4 d-flex align-items-end">
                  <button class="btn btn-primary w-100">Ver Horarios</button>
                </div>
              </div>
            </form>
        </c:if>


        <!-- Paso 3: Fecha, Hora, Motivo -->
        <c:if test="${not empty horarios}">
          <form method="post" action="${pageContext.request.contextPath}/usuario/agendar">
            <input type="hidden" name="medico_id" value="${param.medico_id}"/>
            <div class="row mb-3">
              <div class="col-md-4">
                <input type="date" name="fecha" class="form-control"
                       min="${today}" required />
              </div>
            </div>

            <div class="mb-3">
              <div class="row">
                <c:forEach var="h" items="${horarios}">
                  <div class="col-md-4 mb-2">
                    <div class="form-check">
                      <input type="radio" name="hora" value="${h.horaInicio}"
                             class="form-check-input" required />
                      <label class="form-check-label">
                        ${h.diaSemana} — ${h.horaInicio}
                      </label>
                    </div>
                  </div>
                </c:forEach>
              </div>
            </div>

            <div class="mb-3">
              <textarea name="motivo" class="form-control" rows="3" placeholder="Motivo" required></textarea>
            </div>

            <button type="submit" class="btn btn-success">Agendar Cita</button>
          </form>
        </c:if>

        <a href="${pageContext.request.contextPath}/usuario/dashboard"
           class="btn btn-secondary mt-4">Cancelar</a>
      </div>
    </div>
  </div>
</section>

<jsp:include page="../WEB-INF/footer.jsp"/>
