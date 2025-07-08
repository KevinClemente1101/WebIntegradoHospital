<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../WEB-INF/header_min_admin.jsp">
    <jsp:param name="title" value="Editar Medicamento"/>
</jsp:include>
<%
    String idParam = request.getParameter("id");
    com.mycompany.hospital_citas.Medicamento medicamento = null;
    if (idParam != null) {
        try {
            int id = Integer.parseInt(idParam);
            com.mycompany.hospital_citas.MedicamentoDao dao = new com.mycompany.hospital_citas.MedicamentoDao();
            medicamento = dao.getMedicamentoById(id);
        } catch (Exception e) {
            medicamento = null;
        }
    }
%>
<div class="container mt-4">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <h2>Editar Medicamento</h2>
            <% if (medicamento == null) { %>
                <div class="alert alert-danger">No se encontró el medicamento.</div>
            <% } else { %>
            <form action="${pageContext.request.contextPath}/admin/medicamentos" method="post">
                <input type="hidden" name="id" value="<%= medicamento.getId() %>" />
                <input type="hidden" name="editar" value="1" />
                <div class="mb-3">
                    <label for="nombre" class="form-label">Nombre</label>
                    <input type="text" class="form-control" id="nombre" name="nombre" value="<%= medicamento.getNombre() %>" required />
                </div>
                <div class="mb-3">
                    <label for="descripcion" class="form-label">Descripción</label>
                    <textarea class="form-control" id="descripcion" name="descripcion" required><%= medicamento.getDescripcion() %></textarea>
                </div>
                <div class="mb-3">
                    <label for="dosis" class="form-label">Dosis</label>
                    <input type="text" class="form-control" id="dosis" name="dosis" value="<%= medicamento.getDosis() %>" required />
                </div>
                <div class="mb-3">
                    <label for="frecuencia" class="form-label">Frecuencia</label>
                    <input type="text" class="form-control" id="frecuencia" name="frecuencia" value="<%= medicamento.getFrecuencia() %>" required />
                </div>
                <div class="mb-3">
                    <label for="duracion" class="form-label">Duración</label>
                    <input type="text" class="form-control" id="duracion" name="duracion" value="<%= medicamento.getDuracion() %>" required />
                </div>
                <div class="mb-3">
                    <label for="contraindicaciones" class="form-label">Contraindicaciones</label>
                    <textarea class="form-control" id="contraindicaciones" name="contraindicaciones" required><%= medicamento.getContraindicaciones() %></textarea>
                </div>
                <button type="submit" class="btn btn-primary">Guardar Cambios</button>
                <a href="medicamentos.jsp" class="btn btn-secondary">Cancelar</a>
            </form>
            <% } %>
        </div>
    </div>
</div> 