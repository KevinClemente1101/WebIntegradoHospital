<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nosotros - Hospital Carlos Lanfranco La Hoz</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .hero-section {
            background: linear-gradient(135deg, #0066cc 0%, #004499 100%);
            color: white;
            padding: 80px 0;
        }
        .timeline-item {
            position: relative;
            padding-left: 30px;
            margin-bottom: 30px;
        }
        .timeline-item::before {
            content: '';
            position: absolute;
            left: 0;
            top: 0;
            width: 12px;
            height: 12px;
            background: #0066cc;
            border-radius: 50%;
        }
        .timeline-item::after {
            content: '';
            position: absolute;
            left: 5px;
            top: 12px;
            width: 2px;
            height: calc(100% + 18px);
            background: #e9ecef;
        }
        .timeline-item:last-child::after {
            display: none;
        }
        .specialty-card {
            transition: transform 0.3s ease;
            border: none;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .specialty-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 4px 20px rgba(0,0,0,0.15);
        }
        .value-item {
            text-align: center;
            padding: 20px;
            background: #f8f9fa;
            border-radius: 10px;
            margin-bottom: 20px;
            transition: all 0.3s ease;
        }
        .value-item:hover {
            background: #e9ecef;
            transform: translateY(-2px);
        }
        .value-icon {
            font-size: 2.5rem;
            color: #0066cc;
            margin-bottom: 15px;
        }
        .contact-info {
            background: #f8f9fa;
            border-radius: 10px;
            padding: 25px;
        }
        .stat-box {
            text-align: center;
            padding: 30px 20px;
            background: white;
            border-radius: 10px;
            box-shadow: 0 2px 15px rgba(0,0,0,0.1);
            margin-bottom: 20px;
        }
        .stat-number {
            font-size: 2.5rem;
            font-weight: bold;
            color: #0066cc;
            display: block;
        }
        .section-title {
            color: #0066cc;
            font-weight: bold;
            margin-bottom: 30px;
            position: relative;
            padding-bottom: 10px;
        }
        .section-title::after {
            content: '';
            position: absolute;
            bottom: 0;
            left: 0;
            width: 50px;
            height: 3px;
            background: #0066cc;
        }
    </style>
</head>
<body>
    <!-- Hero Section -->
    <section class="hero-section">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-lg-8">
                    <h1 class="display-4 mb-4">Hospital Carlos Lanfranco La Hoz</h1>
                    <p class="lead mb-4">Más de 50 años brindando servicios de salud de calidad a la comunidad de Lima Norte</p>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="stat-box">
                                <span class="stat-number">50+</span>
                                <span class="text-muted">Años de historia</span>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="stat-box">
                                <span class="stat-number">500K+</span>
                                <span class="text-muted">Personas atendidas</span>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="stat-box">
                                <span class="stat-number">109</span>
                                <span class="text-muted">Camas disponibles</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-4">
                    <div class="text-center">
                        <i class="fas fa-hospital-alt" style="font-size: 8rem; opacity: 0.3;"></i>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Historia y Evolución -->
    <section class="py-5">
        <div class="container">
            <div class="row">
                <div class="col-lg-6">
                    <h2 class="section-title">Historia y Evolución</h2>
                    <div class="timeline-item">
                        <h5><i class="fas fa-calendar-alt text-primary me-2"></i>17 de Septiembre de 1971</h5>
                        <p>Inauguración como pequeño puesto de salud con <strong>25 camas</strong> y cinco especialidades básicas: Medicina, Pediatría, Gineco-Obstetricia, Cirugía y Odontología para una población de aproximadamente 35,000 habitantes.</p>
                    </div>
                    <div class="timeline-item">
                        <h5><i class="fas fa-expand-arrows-alt text-primary me-2"></i>Expansión y Modernización</h5>
                        <p>Reasignación como Hospital Materno-Infantil con ampliación de infraestructura: tomógrafo, mamógrafo, UCI, laboratorio, radiología, centro transfusional, farmacias y unidad de shock trauma.</p>
                    </div>
                    <div class="timeline-item">
                        <h5><i class="fas fa-chart-line text-primary me-2"></i>Crecimiento Actual</h5>
                        <p>Actualmente atiende a más de <strong>500,000 personas</strong> con <strong>109 camas</strong>, representando un incremento del <strong>412%</strong> respecto a su apertura inicial.</p>
                    </div>
                </div>
                <div class="col-lg-6">
                    <div class="card h-100">
                        <div class="card-body">
                            <h4 class="card-title text-primary">Ubicación Estratégica</h4>
                            <p class="card-text">Situado estratégicamente entre Chancay y los hospitales de Lima, ha expandido su capacidad y especialidades funcionando como hospital de apoyo local con atención las 24 horas.</p>
                            <div class="row mt-4">
                                <div class="col-6">
                                    <div class="text-center p-3 bg-light rounded">
                                        <i class="fas fa-users text-primary mb-2" style="font-size: 2rem;"></i>
                                        <h6>Zonas de Cobertura</h6>
                                        <small class="text-muted">Puente Piedra, Ancón, Santa Rosa, Carabayllo</small>
                                    </div>
                                </div>
                                <div class="col-6">
                                    <div class="text-center p-3 bg-light rounded">
                                        <i class="fas fa-clock text-primary mb-2" style="font-size: 2rem;"></i>
                                        <h6>Atención 24/7</h6>
                                        <small class="text-muted">Emergencias disponibles las 24 horas</small>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Misión, Visión y Valores -->
    <section class="py-5 bg-light">
        <div class="container">
            <h2 class="section-title text-center">Misión, Visión y Valores</h2>
            <div class="row">
                <div class="col-lg-6 mb-4">
                    <div class="card h-100">
                        <div class="card-body">
                            <h4 class="card-title text-primary"><i class="fas fa-eye me-2"></i>Visión</h4>
                            <p class="card-text">Ser un hospital de nivel II-2 que brinda servicios de salud básica y especializada con inclusión social e integral a personas, familias y comunidad, con un enfoque preventivo, curativo y recuperativo para la población de Puente Piedra, Ancón, Santa Rosa y Carabayllo.</p>
                        </div>
                    </div>
                </div>
                <div class="col-lg-6 mb-4">
                    <div class="card h-100">
                        <div class="card-body">
                            <h4 class="card-title text-primary"><i class="fas fa-bullseye me-2"></i>Misión</h4>
                            <p class="card-text">Satisfacer necesidades de atención en salud integral, respetando los derechos de los usuarios y brindando servicios de calidad con un enfoque humanizado y profesional.</p>
                        </div>
                    </div>
                </div>
            </div>
            
            <div class="row mt-4">
                <div class="col-12">
                    <h4 class="text-center mb-4 text-primary">Valores Institucionales</h4>
                    <div class="row">
                        <div class="col-md-2 col-sm-4 mb-3">
                            <div class="value-item">
                                <div class="value-icon"><i class="fas fa-handshake"></i></div>
                                <h6>Lealtad</h6>
                            </div>
                        </div>
                        <div class="col-md-2 col-sm-4 mb-3">
                            <div class="value-item">
                                <div class="value-icon"><i class="fas fa-hands-helping"></i></div>
                                <h6>Solidaridad</h6>
                            </div>
                        </div>
                        <div class="col-md-2 col-sm-4 mb-3">
                            <div class="value-item">
                                <div class="value-icon"><i class="fas fa-user-check"></i></div>
                                <h6>Responsabilidad</h6>
                            </div>
                        </div>
                        <div class="col-md-2 col-sm-4 mb-3">
                            <div class="value-item">
                                <div class="value-icon"><i class="fas fa-heart"></i></div>
                                <h6>Honestidad</h6>
                            </div>
                        </div>
                        <div class="col-md-2 col-sm-4 mb-3">
                            <div class="value-item">
                                <div class="value-icon"><i class="fas fa-praying-hands"></i></div>
                                <h6>Respeto</h6>
                            </div>
                        </div>
                        <div class="col-md-2 col-sm-4 mb-3">
                            <div class="value-item">
                                <div class="value-icon"><i class="fas fa-balance-scale"></i></div>
                                <h6>Ética</h6>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    

    <!-- Servicios Adicionales -->
    <section class="py-5 bg-light">
        <div class="container">
            <h2 class="section-title text-center">Servicios Adicionales Esenciales</h2>
            <div class="row">
                <div class="col-md-3 mb-4">
                    <div class="text-center p-4 bg-white rounded shadow-sm h-100">
                        <i class="fas fa-ambulance text-primary mb-3" style="font-size: 3rem;"></i>
                        <h5>Emergencias 24h</h5>
                        <p class="text-muted">Atención de urgencias las 24 horas del día</p>
                    </div>
                </div>
                <div class="col-md-3 mb-4">
                    <div class="text-center p-4 bg-white rounded shadow-sm h-100">
                        <i class="fas fa-bed text-primary mb-3" style="font-size: 3rem;"></i>
                        <h5>Hospitalización</h5>
                        <p class="text-muted">109 camas disponibles con UCI especializada</p>
                    </div>
                </div>
                <div class="col-md-3 mb-4">
                    <div class="text-center p-4 bg-white rounded shadow-sm h-100">
                        <i class="fas fa-pills text-primary mb-3" style="font-size: 3rem;"></i>
                        <h5>Farmacia</h5>
                        <p class="text-muted">Farmacia completa con medicamentos esenciales</p>
                    </div>
                </div>
                <div class="col-md-3 mb-4">
                    <div class="text-center p-4 bg-white rounded shadow-sm h-100">
                        <i class="fas fa-tint text-primary mb-3" style="font-size: 3rem;"></i>
                        <h5>Centro Transfusional</h5>
                        <p class="text-muted">Banco de sangre y servicios de transfusión</p>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Información de Contacto -->
    <section class="py-5">
        <div class="container">
            <h2 class="section-title text-center">Información de Contacto</h2>
            <div class="row">
                <div class="col-lg-6 mb-4">
                    <div class="contact-info">
                        <h4 class="text-primary mb-3"><i class="fas fa-map-marker-alt me-2"></i>Ubicación</h4>
                        <p><strong>Dirección:</strong> Av. Sáenz Peña cuadra 6 s/n, Puente Piedra, Lima, Perú</p>
                        
                        <h5 class="text-primary mt-4 mb-3"><i class="fas fa-phone me-2"></i>Teléfonos</h5>
                        <ul class="list-unstyled">
                            <li><strong>Central:</strong> (01) 548-5334 / 548-3331 / 548-4481</li>
                            <li><strong>Emergencias:</strong> (01) 548-3935</li>
                            <li><strong>Citas:</strong> (01) 548-2010 anexo 208 / (01) 369-2684</li>
                        </ul>
                        
                        <h5 class="text-primary mt-4 mb-3"><i class="fas fa-envelope me-2"></i>Correos</h5>
                        <ul class="list-unstyled">
                            <li>tramitedoc.hcllh@gmail.com</li>
                            <li>mesadepartes@hcllh.gob.pe</li>
                        </ul>
                    </div>
                </div>
                <div class="col-lg-6 mb-4">
                    <div class="contact-info">
                        <h4 class="text-primary mb-3"><i class="fas fa-clock me-2"></i>Horarios de Atención</h4>
                        <div class="row">
                            <div class="col-12 mb-3">
                                <div class="p-3 bg-white rounded">
                                    <h6><i class="fas fa-user-md text-primary me-2"></i>Consultas Externas</h6>
                                    <p class="mb-1"><strong>Lunes - Viernes:</strong> 8:00 - 16:00</p>
                                    <p class="mb-0"><strong>Sábados:</strong> 8:00 - 12:00</p>
                                </div>
                            </div>
                            <div class="col-12 mb-3">
                                <div class="p-3 bg-white rounded">
                                    <h6><i class="fas fa-bed text-primary me-2"></i>Hospitalización</h6>
                                    <p class="mb-0"><strong>Diariamente:</strong> 14:00 - 17:00</p>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="p-3 bg-white rounded">
                                    <h6><i class="fas fa-ambulance text-primary me-2"></i>Emergencias</h6>
                                    <p class="mb-0"><strong>24 horas:</strong> Todos los días</p>
                                </div>
                            </div>
                        </div>
                        
                        <h5 class="text-primary mt-4 mb-3"><i class="fas fa-globe me-2"></i>Web y Redes</h5>
                        <ul class="list-unstyled">
                            <li><strong>Sitio web:</strong> www.hcllh.gob.pe</li>
                            <li><strong>Facebook:</strong> Hospital Carlos Lanfranco La Hoz</li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- COVID-19 Info -->
    <section class="py-4 bg-primary text-white">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-lg-8">
                    <h5 class="mb-2"><i class="fas fa-shield-virus me-2"></i>Cobertura COVID-19</h5>
                    <p class="mb-0">Centro de aislamiento con 40 camas adicionales y equipamiento completo durante la pandemia</p>
                </div>
                <div class="col-lg-4 text-lg-end">
                    <i class="fas fa-virus-slash" style="font-size: 3rem; opacity: 0.7;"></i>
                </div>
            </div>
        </div>
    </section>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>