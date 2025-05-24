-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS hospital_citas;
USE hospital_citas;

-- Tabla de usuarios
CREATE TABLE usuarios (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL CHECK (nombre REGEXP '^[A-Za-zÁáÉéÍíÓóÚúÑñ ]+$'),
    apellido VARCHAR(100) NOT NULL CHECK (apellido REGEXP '^[A-Za-zÁáÉéÍíÓóÚúÑñ ]+$'),
    email VARCHAR(100) UNIQUE NOT NULL CHECK (email REGEXP '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$'),
    password VARCHAR(255) NOT NULL,
    dni VARCHAR(8) UNIQUE NOT NULL CHECK (dni REGEXP '^[0-9]{8}$'),
    telefono VARCHAR(20),
    direccion TEXT,
    fecha_nacimiento DATE NOT NULL CHECK (TIMESTAMPDIFF(YEAR, fecha_nacimiento, CURDATE()) >= 18),
    foto_perfil VARCHAR(255),
    genero ENUM('M', 'F', 'O') NOT NULL,
    tipo_sangre VARCHAR(5),
    alergias TEXT,
    enfermedades_cronicas TEXT,
    rol ENUM('paciente', 'doctor', 'admin', 'recepcionista') NOT NULL,
    descripcion_doctor TEXT,
    foto_doctor VARCHAR(255),
    estado BOOLEAN DEFAULT TRUE,
    ultimo_acceso TIMESTAMP,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabla de especialidades
CREATE TABLE especialidades (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    imagen VARCHAR(255),
    duracion_cita INT DEFAULT 30 COMMENT 'Duración en minutos',
    precio_consulta DECIMAL(10,2),
    estado BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabla de doctores
CREATE TABLE doctores (
    id INT PRIMARY KEY AUTO_INCREMENT,
    usuario_id INT NOT NULL,
    especialidad_id INT NOT NULL,
    codigo_colegiatura VARCHAR(50) UNIQUE NOT NULL,
    biografia TEXT,
    experiencia_años INT,
    titulo VARCHAR(100),
    universidad VARCHAR(100),
    anio_graduacion INT,
    estado BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (especialidad_id) REFERENCES especialidades(id)
);

-- Tabla de horarios
CREATE TABLE horarios (
    id INT PRIMARY KEY AUTO_INCREMENT,
    doctor_id INT NOT NULL,
    dia_semana ENUM('Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado', 'Domingo') NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    intervalo_citas INT DEFAULT 30 COMMENT 'Intervalo entre citas en minutos',
    max_citas_dia INT DEFAULT 20,
    estado BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (doctor_id) REFERENCES doctores(id)
);

-- Tabla de citas
CREATE TABLE citas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    paciente_id INT NOT NULL,
    doctor_id INT NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    estado ENUM('pendiente', 'confirmada', 'cancelada', 'completada', 'no_asistio') NOT NULL,
    tipo_consulta ENUM('primera_vez', 'control', 'emergencia') NOT NULL,
    motivo TEXT,
    sintomas TEXT,
    diagnostico TEXT,
    tratamiento TEXT,
    observaciones TEXT,
    proxima_cita DATE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (paciente_id) REFERENCES usuarios(id),
    FOREIGN KEY (doctor_id) REFERENCES doctores(id)
);

-- Tabla de historial médico
CREATE TABLE historial_medico (
    id INT PRIMARY KEY AUTO_INCREMENT,
    paciente_id INT NOT NULL,
    doctor_id INT NOT NULL,
    fecha_consulta DATE NOT NULL,
    diagnostico TEXT,
    tratamiento TEXT,
    medicamentos TEXT,
    observaciones TEXT,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (paciente_id) REFERENCES usuarios(id),
    FOREIGN KEY (doctor_id) REFERENCES doctores(id)
);

-- Tabla de medicamentos
CREATE TABLE medicamentos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    dosis VARCHAR(100),
    frecuencia VARCHAR(100),
    duracion VARCHAR(100),
    contraindicaciones TEXT,
    estado BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de recetas médicas
CREATE TABLE recetas_medicas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    cita_id INT NOT NULL,
    medicamento_id INT NOT NULL,
    dosis VARCHAR(100),
    frecuencia VARCHAR(100),
    duracion VARCHAR(100),
    observaciones TEXT,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cita_id) REFERENCES citas(id),
    FOREIGN KEY (medicamento_id) REFERENCES medicamentos(id)
);

-- Tabla de pagos
CREATE TABLE pagos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    cita_id INT NOT NULL,
    monto DECIMAL(10,2) NOT NULL,
    metodo_pago ENUM('efectivo', 'tarjeta', 'transferencia') NOT NULL,
    estado ENUM('pendiente', 'completado', 'cancelado') NOT NULL,
    fecha_pago TIMESTAMP,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cita_id) REFERENCES citas(id)
);

-- Insertar especialidades de ejemplo
INSERT INTO especialidades (nombre, descripcion, duracion_cita, precio_consulta) VALUES
('Cardiología', 'Especialidad médica que se encarga del diagnóstico y tratamiento de las enfermedades del corazón y del sistema circulatorio.', 45, 150.00),
('Dermatología', 'Especialidad médica que se encarga del diagnóstico y tratamiento de las enfermedades de la piel.', 30, 120.00),
('Ginecología', 'Especialidad médica que se encarga del diagnóstico y tratamiento de las enfermedades del sistema reproductor femenino.', 45, 130.00),
('Neurología', 'Especialidad médica que se encarga del diagnóstico y tratamiento de las enfermedades del sistema nervioso.', 60, 180.00),
('Oftalmología', 'Especialidad médica que se encarga del diagnóstico y tratamiento de las enfermedades de los ojos.', 30, 140.00),
('Ortopedia', 'Especialidad médica que se encarga del diagnóstico y tratamiento de las enfermedades del sistema musculoesquelético.', 45, 150.00),
('Pediatría', 'Especialidad médica que se encarga del diagnóstico y tratamiento de las enfermedades de los niños.', 30, 120.00),
('Psiquiatría', 'Especialidad médica que se encarga del diagnóstico y tratamiento de las enfermedades mentales.', 60, 200.00);

-- Insertar usuario administrador por defecto
INSERT INTO usuarios (nombre, apellido, email, password, dni, genero, rol) VALUES
('Admin', 'Sistema', 'admin@hospital.com', '$2a$10$X7UrH5ZxX5ZxX5ZxX5ZxX.5ZxX5ZxX5ZxX5ZxX5ZxX5ZxX5ZxX5Zx', '12345678', 'O', 'admin');

-- Insertar algunos medicamentos comunes
INSERT INTO medicamentos (nombre, descripcion, dosis, frecuencia, contraindicaciones) VALUES
('Paracetamol', 'Analgésico y antipirético', '500mg', 'Cada 8 horas', 'Hipersensibilidad al paracetamol'),
('Ibuprofeno', 'Antiinflamatorio no esteroideo', '400mg', 'Cada 8 horas', 'Úlcera gástrica, insuficiencia renal'),
('Amoxicilina', 'Antibiótico de amplio espectro', '500mg', 'Cada 8 horas', 'Alergia a penicilinas'),
('Omeprazol', 'Inhibidor de la bomba de protones', '20mg', 'Una vez al día', 'Hipersensibilidad al omeprazol'); 