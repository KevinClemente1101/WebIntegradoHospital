<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>            
            </div>
        </main> 

        <footer class="bg-dark text-white py-4 mt-auto">
                <div class="container">
                    <div class="row">
                        <div class="col-md-6">
                            <h5>Hospital Carlos Lafranco La Hoz</h5>
                            <p>Sistema de agendamiento de citas médicas</p>
                        </div>
                        <div class="col-md-3">
                            <h5>Enlaces</h5>
                            <ul class="list-unstyled">
                                <li><a href="index" class="text-white">Inicio</a></li>
                                <li><a href="#" class="text-white">Contacto</a></li>
                                <li><a href="#" class="text-white">Términos y condiciones</a></li>
                            </ul>
                        </div>
                        <div class="col-md-3">
                            <h5>Contacto</h5>
                            <ul class="list-unstyled">
                                <li><i class="fas fa-map-marker-alt"></i> Dirección: Calle 123, Ciudad</li>
                                <li><i class="fas fa-phone"></i> Teléfono: +123 456 7890</li>
                                <li><i class="fas fa-envelope"></i> Email: info@hospital.com</li>
                            </ul>
                        </div>
                    </div>
                    <hr>
                    <div class="text-center">
                        <p class="mb-0">&copy; Todos los derechos reservados.</p>
                    </div>
                </div>

                <!-- Alertas -->
                <c:if test="${not empty sessionScope.error}">
                    <script>
                    document.addEventListener('DOMContentLoaded', function() {
                        Swal.fire({
                            icon: 'error',
                            title: 'Error',
                            text: '${fn:escapeXml(sessionScope.error)}',
                            confirmButtonText: 'Aceptar'
                        });
                    });
                    </script>
                    <c:remove var="error" scope="session" />
                </c:if>

                <c:if test="${not empty sessionScope.success}">
                    <script>
                    document.addEventListener('DOMContentLoaded', function() {
                        Swal.fire({
                            icon: 'success',
                            title: 'Éxito',
                            text: '${fn:escapeXml(sessionScope.success)}',
                            confirmButtonText: 'Aceptar'
                        });
                    });
                    </script>
                    <c:remove var="success" scope="session" />
                </c:if>


        </footer>

        <!-- Bootstrap 5.3 -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <!-- jQuery -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <!-- Script principal de Flatpickr, el plugin de selección de fechas y horas. -->
        <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
        <!-- Script de localización de Flatpickr para español (traducción de los textos del calendario). -->
        <script src="https://cdn.jsdelivr.net/npm/flatpickr/dist/l10n/es.js"></script>
        <!-- SweetAlert2 -->
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <!-- Custom JS -->
        <script src="${pageContext.request.contextPath}/assets/js/script.js"></script>

    </body>
</html>