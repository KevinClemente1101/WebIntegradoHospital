<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../WEB-INF/header_min_admin.jsp">
    <jsp:param name="title" value="Editar Paciente"/>
</jsp:include>

<div class="container-fluid mt-4">
    <div class="row">
        <div class="col-md-3">
            <jsp:include page="sdebar.jsp"/>
        </div>
        <div class="col-md-9">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2>Editar Paciente</h2>
                <a href="${pageContext.request.contextPath}/admin/pacientes" class="btn btn-secondary">
                    <i class="fas fa-arrow-left"></i> Volver a Pacientes
                </a>
            </div>

            <div class="card">
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/admin/editar-paciente" method="post">
                        <input type="hidden" name="id" value="${paciente.id}">
                        
                        <div class="row">
                            <div class="col-md-6">
                                <div class="mb-3">
                                    <label for="nombre" class="form-label">Nombre</label>
                                    <input type="text" class="form-control" id="nombre" value="${paciente.nombre}" readonly>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="mb-3">
                                    <label for="apellido" class="form-label">Apellido</label>
                                    <input type="text" class="form-control" id="apellido" value="${paciente.apellido}" readonly>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6">
                                <div class="mb-3">
                                    <label for="dni" class="form-label">DNI</label>
                                    <input type="text" class="form-control" id="dni" value="${paciente.dni}" readonly>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="mb-3">
                                    <label for="email" class="form-label">Email</label>
                                    <input type="email" class="form-control" id="email" name="email" value="${paciente.email}" required>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6">
                                <div class="mb-3">
                                    <label for="telefono" class="form-label">Teléfono</label>
                                    <input type="tel" class="form-control" id="telefono" name="telefono" 
                                           value="${paciente.telefono}" placeholder="Ej: 999888777">
                                    <div class="form-text">Ingrese el número de teléfono sin espacios ni guiones</div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="mb-3">
                                    <label for="fechaNacimiento" class="form-label">Fecha de Nacimiento</label>
                                    <input type="date" class="form-control" id="fechaNacimiento" value="${paciente.fechaNacimiento}" readonly>
                                </div>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="direccion" class="form-label">Dirección</label>
                            <textarea class="form-control" id="direccion" name="direccion" rows="3" 
                                      placeholder="Ingrese la dirección completa">${paciente.direccion}</textarea>
                        </div>

                        <div class="row">
                            <div class="col-md-4">
                                <div class="mb-3">
                                    <label for="tipo_sangre" class="form-label">Tipo de Sangre</label>
                                    <select class="form-control" id="tipo_sangre" name="tipo_sangre">
                                        <option value="">Seleccionar tipo de sangre</option>
                                        <option value="A+" ${paciente.tipo_sangre eq 'A+' ? 'selected' : ''}>A+</option>
                                        <option value="A-" ${paciente.tipo_sangre eq 'A-' ? 'selected' : ''}>A-</option>
                                        <option value="B+" ${paciente.tipo_sangre eq 'B+' ? 'selected' : ''}>B+</option>
                                        <option value="B-" ${paciente.tipo_sangre eq 'B-' ? 'selected' : ''}>B-</option>
                                        <option value="AB+" ${paciente.tipo_sangre eq 'AB+' ? 'selected' : ''}>AB+</option>
                                        <option value="AB-" ${paciente.tipo_sangre eq 'AB-' ? 'selected' : ''}>AB-</option>
                                        <option value="O+" ${paciente.tipo_sangre eq 'O+' ? 'selected' : ''}>O+</option>
                                        <option value="O-" ${paciente.tipo_sangre eq 'O-' ? 'selected' : ''}>O-</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="mb-3">
                                    <label for="genero" class="form-label">Género</label>
                                    <input type="text" class="form-control" id="genero" value="${paciente.genero}" readonly>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="mb-3">
                                    <label for="estado" class="form-label">Estado</label>
                                    <input type="text" class="form-control" id="estado" 
                                           value="${paciente.estado ? 'Activo' : 'Inactivo'}" readonly>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6">
                                <div class="mb-3">
                                    <label for="alergias" class="form-label">Alergias</label>
                                    <textarea class="form-control" id="alergias" name="alergias" rows="3" 
                                              placeholder="Ingrese las alergias del paciente (si las tiene)">${paciente.alergias}</textarea>
                                    <div class="form-text">Deje vacío si no tiene alergias</div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="mb-3">
                                    <label for="enfermedades_cronicas" class="form-label">Enfermedades Crónicas</label>
                                    <textarea class="form-control" id="enfermedades_cronicas" name="enfermedades_cronicas" rows="3" 
                                              placeholder="Ingrese las enfermedades crónicas (si las tiene)">${paciente.enfermedades_cronicas}</textarea>
                                    <div class="form-text">Deje vacío si no tiene enfermedades crónicas</div>
                                </div>
                            </div>
                        </div>

                        <div class="d-flex justify-content-end gap-2">
                            <a href="${pageContext.request.contextPath}/admin/pacientes" class="btn btn-secondary">
                                <i class="fas fa-times"></i> Cancelar
                            </a>
                            <button type="submit" class="btn btn-primary">
                                <i class="fas fa-save"></i> Guardar Cambios
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../WEB-INF/footer.jsp"/> 