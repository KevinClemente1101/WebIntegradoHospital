 <footer class="bg-dark text-white py-4 mt-auto">
        <div class="container">
            <div class="row">
                <div class="col-md-6">
                    <h5><?= SITE_NAME ?></h5>
                    <p>Sistema de agendamiento de citas médicas</p>
                </div>
                <div class="col-md-3">
                    <h5>Enlaces</h5>
                    <ul class="list-unstyled">
                        <li><a href="<?= BASE_URL ?>index.php" class="text-white">Inicio</a></li>
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
                <p class="mb-0">&copy; <?= date('Y') ?> <?= SITE_NAME ?>. Todos los derechos reservados.</p>
            </div>
        </div>
        
        <!<!-- Alertas -->
        <?php if (isset($_SESSION['error'])): ?>
        <script>
        document.addEventListener('DOMContentLoaded', function() {
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: '<?= addslashes($_SESSION['error']) ?>',
                confirmButtonText: 'Aceptar'
            });
        });
        </script>
        <?php unset($_SESSION['error']); ?>
        <?php endif; ?>

        <?php if (isset($_SESSION['success'])): ?>
        <script>
        document.addEventListener('DOMContentLoaded', function() {
            Swal.fire({
                icon: 'success',
                title: 'Éxito',
                text: '<?= addslashes($_SESSION['success']) ?>',
                confirmButtonText: 'Aceptar'
            });
        });
        </script>
        <?php unset($_SESSION['success']); ?>
        <?php endif; ?>


    </footer>