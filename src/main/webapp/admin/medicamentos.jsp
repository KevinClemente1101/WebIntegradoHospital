<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../WEB-INF/header_min_admin.jsp">
    <jsp:param name="title" value="Panel Admin"/>
</jsp:include>
<head>
    <style>
        .btn-editar-custom {
            background: linear-gradient(135deg, #36d1c4 0%, #1e90b8 100%);
            color: #fff !important;
            font-weight: bold;
            border: none;
            border-radius: 16px;
            padding: 8px 28px;
            margin-right: 10px;
            box-shadow: none;
            transition: background 0.3s;
        }
        .btn-editar-custom:hover {
            background: linear-gradient(135deg, #1e90b8 0%, #36d1c4 100%);
        }
        .btn-eliminar-custom {
            background: #e53945;
            color: #fff !important;
            font-weight: bold;
            border: none;
            border-radius: 10px;
            padding: 8px 28px;
            box-shadow: none;
            transition: background 0.3s;
        }
        .btn-eliminar-custom:hover {
            background: #b71c1c;
        }
    </style>
</head>
<div class="container-fluid mt-4">
    <div class="row">
        <div class="col-md-3">
            <jsp:include page="sdebar.jsp"/>
        </div>
        <div class="col-md-9">
            <h2>Gestión de Medicamentos</h2>
            <c:if test="${not empty error}">
                <div class="alert alert-danger" role="alert">
                    ${error}
                </div>
            </c:if>
            <c:if test="${not empty successMessage}">
                <div class="alert alert-success" role="alert" id="success-alert">
                    ${successMessage}
                </div>
            </c:if>
            <!-- Botón para agregar medicamento -->
            <a href="nueva_medicamento.jsp" class="btn btn-success mb-3">Agregar Medicamento</a>
            <!-- Tabla de medicamentos existentes -->
            <h3 class="mt-4">Medicamentos Registrados</h3>
            <div class="table-responsive">
                <table class="table table-bordered table-striped">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Descripción</th>
                            <th>Dosis</th>
                            <th>Frecuencia</th>
                            <th>Duración</th>
                            <th>Contraindicaciones</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="medicamento" items="${medicamentos}">
                            <tr>
                                <td>${medicamento.id}</td>
                                <td>${medicamento.nombre}</td>
                                <td>${medicamento.descripcion}</td>
                                <td>${medicamento.dosis}</td>
                                <td>${medicamento.frecuencia}</td>
                                <td>${medicamento.duracion}</td>
                                <td>${medicamento.contraindicaciones}</td>
                                <td style="min-width: 220px; display: flex; align-items: center; gap: 10px;">
                                    <a href="editar_medicamento.jsp?id=${medicamento.id}" class="btn-editar-custom">Editar</a>
                                    <form action="${pageContext.request.contextPath}/admin/medicamentos" method="post" style="display:inline; margin:0; padding:0;">
                                        <input type="hidden" name="id" value="${medicamento.id}" />
                                        <input type="hidden" name="eliminar" value="1" />
                                        <button type="submit" class="btn-eliminar-custom" onclick="return confirm('¿Seguro que deseas eliminar este medicamento?')">Eliminar</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <!-- Modales de edición fuera del tbody -->
            <c:forEach var="medicamento" items="${medicamentos}">
                <div class="modal fade" id="editarMedicamentoModal${medicamento.id}" tabindex="-1" aria-labelledby="editarMedicamentoModalLabel${medicamento.id}" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <form action="${pageContext.request.contextPath}/admin/medicamentos" method="post">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="editarMedicamentoModalLabel${medicamento.id}">Editar Medicamento</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <input type="hidden" name="id" value="${medicamento.id}" />
                                    <input type="hidden" name="editar" value="1" />
                                    <div class="mb-3">
                                        <label for="nombre${medicamento.id}" class="form-label">Nombre</label>
                                        <input type="text" class="form-control" id="nombre${medicamento.id}" name="nombre" value="${medicamento.nombre}" required />
                                    </div>
                                    <div class="mb-3">
                                        <label for="descripcion${medicamento.id}" class="form-label">Descripción</label>
                                        <textarea class="form-control" id="descripcion${medicamento.id}" name="descripcion" required>${medicamento.descripcion}</textarea>
                                    </div>
                                    <div class="mb-3">
                                        <label for="dosis${medicamento.id}" class="form-label">Dosis</label>
                                        <input type="text" class="form-control" id="dosis${medicamento.id}" name="dosis" value="${medicamento.dosis}" required />
                                    </div>
                                    <div class="mb-3">
                                        <label for="frecuencia${medicamento.id}" class="form-label">Frecuencia</label>
                                        <input type="text" class="form-control" id="frecuencia${medicamento.id}" name="frecuencia" value="${medicamento.frecuencia}" required />
                                    </div>
                                    <div class="mb-3">
                                        <label for="duracion${medicamento.id}" class="form-label">Duración</label>
                                        <input type="text" class="form-control" id="duracion${medicamento.id}" name="duracion" value="${medicamento.duracion}" required />
                                    </div>
                                    <div class="mb-3">
                                        <label for="contraindicaciones${medicamento.id}" class="form-label">Contraindicaciones</label>
                                        <textarea class="form-control" id="contraindicaciones${medicamento.id}" name="contraindicaciones" required>${medicamento.contraindicaciones}</textarea>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                                    <button type="submit" class="btn btn-primary">Guardar Cambios</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </c:forEach>
            <
        </div>
    </div>
</div>
<script>
// Cierra el modal de agregar si hay mensaje de éxito
window.addEventListener('DOMContentLoaded', function() {
    var successAlert = document.getElementById('success-alert');
    if (successAlert && successAlert.innerText.includes('agregado correctamente')) {
        var modal = bootstrap.Modal.getInstance(document.getElementById('agregarMedicamentoModal'));
        if (modal) {
            modal.hide();
        } else {
            // Si el modal no está abierto, lo cerramos forzadamente
            var modalEl = document.getElementById('agregarMedicamentoModal');
            if (modalEl && modalEl.classList.contains('show')) {
                var modalInstance = new bootstrap.Modal(modalEl);
                modalInstance.hide();
            }
        }
    }
});

document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('formAgregarMedicamento');
    form.addEventListener('submit', function(e) {
        alert('¡Enviando formulario!');
        e.preventDefault();
        const formData = new FormData(form);
        fetch(form.action, {
            method: 'POST',
            body: formData
        })
        .then(response => response.text())
        .then(html => {
            // Crear un DOM temporal para extraer la tabla y mensajes
            const parser = new DOMParser();
            const doc = parser.parseFromString(html, 'text/html');
            // Actualizar la tabla de medicamentos
            const nuevaTabla = doc.querySelector('.table-responsive');
            document.querySelector('.table-responsive').innerHTML = nuevaTabla.innerHTML;
            // Mostrar mensaje de éxito
            const successMsg = doc.querySelector('.alert-success');
            const errorMsg = doc.querySelector('.alert-danger');
            document.querySelectorAll('.alert-success, .alert-danger').forEach(el => el.remove());
            if (successMsg) {
                document.querySelector('.col-md-9').insertBefore(successMsg, document.querySelector('.col-md-9').children[2]);
            }
            if (errorMsg) {
                document.querySelector('.col-md-9').insertBefore(errorMsg, document.querySelector('.col-md-9').children[2]);
            }
            // Cerrar el modal
            var modal = bootstrap.Modal.getInstance(document.getElementById('agregarMedicamentoModal'));
            if (modal) modal.hide();
            form.reset();
            console.log('Respuesta HTML:', html);
        })
        .catch(err => {
            alert('Error al agregar medicamento.');
        });
    });
});
</script> 