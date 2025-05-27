-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 17-05-2025 a las 09:20:16
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `hospital_citas`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `citas`
--

CREATE TABLE `citas` (
  `id` int(11) NOT NULL,
  `paciente_id` int(11) NOT NULL,
  `doctor_id` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `hora` time NOT NULL,
  `estado` enum('pendiente','confirmada','cancelada','completada','no_asistio') NOT NULL,
  `tipo_consulta` enum('primera_vez','control','emergencia') NOT NULL,
  `motivo` text DEFAULT NULL,
  `sintomas` text DEFAULT NULL,
  `diagnostico` text DEFAULT NULL,
  `tratamiento` text DEFAULT NULL,
  `observaciones` text DEFAULT NULL,
  `proxima_cita` date DEFAULT NULL,
  `fecha_creacion` timestamp NOT NULL DEFAULT current_timestamp(),
  `fecha_actualizacion` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `especialidades`
--

CREATE TABLE `especialidades` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `descripcion` text DEFAULT NULL,
  `imagen` varchar(255) DEFAULT NULL,
  `duracion_cita` int(11) DEFAULT 30 COMMENT 'Duración en minutos',
  `precio_consulta` decimal(10,2) DEFAULT NULL,
  `estado` tinyint(1) DEFAULT 1,
  `fecha_creacion` timestamp NOT NULL DEFAULT current_timestamp(),
  `fecha_actualizacion` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `especialidades`
--

INSERT INTO `especialidades` (`id`, `nombre`, `descripcion`, `imagen`, `duracion_cita`, `precio_consulta`, `estado`, `fecha_creacion`, `fecha_actualizacion`) VALUES
(1, 'Cardiología', 'Especialidad médica que se encarga del diagnóstico y tratamiento de las enfermedades del corazón y del sistema circulatorio.', NULL, 45, 150.00, 1, '2025-05-16 02:52:34', '2025-05-16 02:52:34'),
(2, 'Dermatología', 'Especialidad médica que se encarga del diagnóstico y tratamiento de las enfermedades de la piel.', NULL, 30, 120.00, 1, '2025-05-16 02:52:34', '2025-05-16 02:52:34'),
(3, 'Ginecología', 'Especialidad médica que se encarga del diagnóstico y tratamiento de las enfermedades del sistema reproductor femenino.', NULL, 45, 130.00, 1, '2025-05-16 02:52:34', '2025-05-16 02:52:34'),
(4, 'Neurología', 'Especialidad médica que se encarga del diagnóstico y tratamiento de las enfermedades del sistema nervioso.', NULL, 60, 180.00, 1, '2025-05-16 02:52:34', '2025-05-16 02:52:34'),
(5, 'Oftalmología', 'Especialidad médica que se encarga del diagnóstico y tratamiento de las enfermedades de los ojos.', NULL, 30, 140.00, 1, '2025-05-16 02:52:34', '2025-05-16 02:52:34'),
(6, 'Ortopedia', 'Especialidad médica que se encarga del diagnóstico y tratamiento de las enfermedades del sistema musculoesquelético.', NULL, 45, 150.00, 1, '2025-05-16 02:52:34', '2025-05-16 02:52:34'),
(7, 'Pediatría', 'Especialidad médica que se encarga del diagnóstico y tratamiento de las enfermedades de los niños.', NULL, 30, 120.00, 1, '2025-05-16 02:52:34', '2025-05-16 02:52:34'),
(8, 'Psiquiatría', 'Especialidad médica que se encarga del diagnóstico y tratamiento de las enfermedades mentales.', NULL, 60, 200.00, 1, '2025-05-16 02:52:34', '2025-05-16 02:52:34');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `historial_medico`
--

CREATE TABLE `historial_medico` (
  `id` int(11) NOT NULL,
  `paciente_id` int(11) NOT NULL,
  `doctor_id` int(11) NOT NULL,
  `fecha_consulta` date NOT NULL,
  `diagnostico` text DEFAULT NULL,
  `tratamiento` text DEFAULT NULL,
  `medicamentos` text DEFAULT NULL,
  `observaciones` text DEFAULT NULL,
  `fecha_creacion` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `horarios`
--

CREATE TABLE `horarios` (
  `id` int(11) NOT NULL,
  `doctor_id` int(11) NOT NULL,
  `dia_semana` enum('Lunes','Martes','Miércoles','Jueves','Viernes','Sábado','Domingo') NOT NULL,
  `hora_inicio` time NOT NULL,
  `hora_fin` time NOT NULL,
  `intervalo_citas` int(11) DEFAULT 30 COMMENT 'Intervalo entre citas en minutos',
  `max_citas_dia` int(11) DEFAULT 20,
  `estado` tinyint(1) DEFAULT 1,
  `fecha_creacion` timestamp NOT NULL DEFAULT current_timestamp(),
  `fecha_actualizacion` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `medicamentos`
--

CREATE TABLE `medicamentos` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `descripcion` text DEFAULT NULL,
  `dosis` varchar(100) DEFAULT NULL,
  `frecuencia` varchar(100) DEFAULT NULL,
  `duracion` varchar(100) DEFAULT NULL,
  `contraindicaciones` text DEFAULT NULL,
  `estado` tinyint(1) DEFAULT 1,
  `fecha_creacion` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `medicamentos`
--

INSERT INTO `medicamentos` (`id`, `nombre`, `descripcion`, `dosis`, `frecuencia`, `duracion`, `contraindicaciones`, `estado`, `fecha_creacion`) VALUES
(1, 'Paracetamol', 'Analgésico y antipirético', '500mg', 'Cada 8 horas', NULL, 'Hipersensibilidad al paracetamol', 1, '2025-05-16 02:52:34'),
(2, 'Ibuprofeno', 'Antiinflamatorio no esteroideo', '400mg', 'Cada 8 horas', NULL, 'Úlcera gástrica, insuficiencia renal', 1, '2025-05-16 02:52:34'),
(3, 'Amoxicilina', 'Antibiótico de amplio espectro', '500mg', 'Cada 8 horas', NULL, 'Alergia a penicilinas', 1, '2025-05-16 02:52:34'),
(4, 'Omeprazol', 'Inhibidor de la bomba de protones', '20mg', 'Una vez al día', NULL, 'Hipersensibilidad al omeprazol', 1, '2025-05-16 02:52:34');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `medicos`
--

CREATE TABLE `medicos` (
  `id` int(11) NOT NULL,
  `usuario_id` int(11) NOT NULL,
  `especialidad_id` int(11) NOT NULL,
  `codigo_colegiatura` varchar(50) NOT NULL,
  `biografia` text DEFAULT NULL,
  `experiencia_años` int(11) DEFAULT NULL,
  `titulo` varchar(100) DEFAULT NULL,
  `universidad` varchar(100) DEFAULT NULL,
  `anio_graduacion` int(11) DEFAULT NULL,
  `estado` tinyint(1) DEFAULT 1,
  `fecha_creacion` timestamp NOT NULL DEFAULT current_timestamp(),
  `fecha_actualizacion` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pagos`
--

CREATE TABLE `pagos` (
  `id` int(11) NOT NULL,
  `cita_id` int(11) NOT NULL,
  `monto` decimal(10,2) NOT NULL,
  `metodo_pago` enum('efectivo','tarjeta','transferencia') NOT NULL,
  `estado` enum('pendiente','completado','cancelado') NOT NULL,
  `fecha_pago` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `fecha_creacion` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `recetas_medicas`
--

CREATE TABLE `recetas_medicas` (
  `id` int(11) NOT NULL,
  `cita_id` int(11) NOT NULL,
  `medicamento_id` int(11) NOT NULL,
  `dosis` varchar(100) DEFAULT NULL,
  `frecuencia` varchar(100) DEFAULT NULL,
  `duracion` varchar(100) DEFAULT NULL,
  `observaciones` text DEFAULT NULL,
  `fecha_creacion` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL CHECK (`nombre` regexp '^[A-Za-zÁáÉéÍíÓóÚúÑñ ]+$'),
  `apellido` varchar(100) NOT NULL CHECK (`apellido` regexp '^[A-Za-zÁáÉéÍíÓóÚúÑñ ]+$'),
  `email` varchar(100) NOT NULL CHECK (`email` regexp '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,}$'),
  `password` varchar(255) NOT NULL,
  `dni` varchar(8) NOT NULL CHECK (`dni` regexp '^[0-9]{8}$'),
  `telefono` varchar(20) DEFAULT NULL,
  `direccion` text DEFAULT NULL,
  `fecha_nacimiento` date NOT NULL,
  `foto_perfil` varchar(255) DEFAULT NULL,
  `genero` enum('M','F','O') NOT NULL,
  `tipo_sangre` varchar(5) DEFAULT NULL,
  `alergias` text DEFAULT NULL,
  `enfermedades_cronicas` text DEFAULT NULL,
  `rol` enum('paciente','doctor','admin','recepcionista') NOT NULL,
  `estado` tinyint(1) DEFAULT 1,
  `ultimo_acceso` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `fecha_registro` timestamp NOT NULL DEFAULT current_timestamp(),
  `fecha_actualizacion` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `nombre`, `apellido`, `email`, `password`, `dni`, `telefono`, `direccion`, `fecha_nacimiento`, `foto_perfil`, `genero`, `tipo_sangre`, `alergias`, `enfermedades_cronicas`, `rol`, `estado`, `ultimo_acceso`, `fecha_registro`, `fecha_actualizacion`) VALUES
(1, 'Admin', 'Sistema', 'admin@hospital.com', '$2a$10$QxhTEl5FbUjctJ/Vm7f.U.rLoFFeBi4rf3pw2pL4gpwRR7SIHTYwy', '12345678', NULL, NULL, '0000-00-00', NULL, 'O', NULL, NULL, NULL, 'admin', 1, '2025-05-16 02:52:34', '2025-05-16 02:52:34', '2025-05-16 02:52:34'),
(3, 'Jennyfer', 'Sanchez', 'JSanchez@gmail.com', '$2a$10$QxhTEl5FbUjctJ/Vm7f.U.rLoFFeBi4rf3pw2pL4gpwRR7SIHTYwy', '76543210', NULL, NULL, '2005-03-18', NULL, 'M', NULL, NULL, NULL, 'paciente', 1, '2025-05-16 03:10:19', '2025-05-16 03:10:19', '2025-05-16 03:10:19'),
(6, 'Kevin', 'Clemente', 'kevclemnte11@gmail.com', '$2a$10$QxhTEl5FbUjctJ/Vm7f.U.rLoFFeBi4rf3pw2pL4gpwRR7SIHTYwy', '72229486', NULL, NULL, '2006-01-11', NULL, 'M', NULL, NULL, NULL, 'paciente', 1, '2025-05-17 06:36:44', '2025-05-17 06:36:44', '2025-05-17 06:36:44');

--
-- Disparadores `usuarios`
--
DELIMITER $$
CREATE TRIGGER `validar_edad_usuario` BEFORE INSERT ON `usuarios` FOR EACH ROW BEGIN
    IF TIMESTAMPDIFF(YEAR, NEW.fecha_nacimiento, CURDATE()) < 18 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El usuario debe tener al menos 18 años.';
    END IF;
END
$$
DELIMITER ;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `citas`
--
ALTER TABLE `citas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `paciente_id` (`paciente_id`),
  ADD KEY `doctor_id` (`doctor_id`);

--
-- Indices de la tabla `especialidades`
--
ALTER TABLE `especialidades`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `historial_medico`
--
ALTER TABLE `historial_medico`
  ADD PRIMARY KEY (`id`),
  ADD KEY `paciente_id` (`paciente_id`),
  ADD KEY `doctor_id` (`doctor_id`);

--
-- Indices de la tabla `horarios`
--
ALTER TABLE `horarios`
  ADD PRIMARY KEY (`id`),
  ADD KEY `doctor_id` (`doctor_id`);

--
-- Indices de la tabla `medicamentos`
--
ALTER TABLE `medicamentos`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `medicos`
--
ALTER TABLE `medicos`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `codigo_colegiatura` (`codigo_colegiatura`),
  ADD KEY `usuario_id` (`usuario_id`),
  ADD KEY `especialidad_id` (`especialidad_id`);

--
-- Indices de la tabla `pagos`
--
ALTER TABLE `pagos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `cita_id` (`cita_id`);

--
-- Indices de la tabla `recetas_medicas`
--
ALTER TABLE `recetas_medicas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `cita_id` (`cita_id`),
  ADD KEY `medicamento_id` (`medicamento_id`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `dni` (`dni`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `citas`
--
ALTER TABLE `citas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `especialidades`
--
ALTER TABLE `especialidades`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `historial_medico`
--
ALTER TABLE `historial_medico`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `horarios`
--
ALTER TABLE `horarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `medicamentos`
--
ALTER TABLE `medicamentos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `medicos`
--
ALTER TABLE `medicos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `pagos`
--
ALTER TABLE `pagos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `recetas_medicas`
--
ALTER TABLE `recetas_medicas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `citas`
--
ALTER TABLE `citas`
  ADD CONSTRAINT `citas_ibfk_1` FOREIGN KEY (`paciente_id`) REFERENCES `usuarios` (`id`),
  ADD CONSTRAINT `citas_ibfk_2` FOREIGN KEY (`doctor_id`) REFERENCES `medicos` (`id`);

--
-- Filtros para la tabla `historial_medico`
--
ALTER TABLE `historial_medico`
  ADD CONSTRAINT `historial_medico_ibfk_1` FOREIGN KEY (`paciente_id`) REFERENCES `usuarios` (`id`),
  ADD CONSTRAINT `historial_medico_ibfk_2` FOREIGN KEY (`doctor_id`) REFERENCES `medicos` (`id`);

--
-- Filtros para la tabla `horarios`
--
ALTER TABLE `horarios`
  ADD CONSTRAINT `horarios_ibfk_1` FOREIGN KEY (`doctor_id`) REFERENCES `medicos` (`id`);

--
-- Filtros para la tabla `medicos`
--
ALTER TABLE `medicos`
  ADD CONSTRAINT `medicos_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`),
  ADD CONSTRAINT `medicos_ibfk_2` FOREIGN KEY (`especialidad_id`) REFERENCES `especialidades` (`id`);

--
-- Filtros para la tabla `pagos`
--
ALTER TABLE `pagos`
  ADD CONSTRAINT `pagos_ibfk_1` FOREIGN KEY (`cita_id`) REFERENCES `citas` (`id`);

--
-- Filtros para la tabla `recetas_medicas`
--
ALTER TABLE `recetas_medicas`
  ADD CONSTRAINT `recetas_medicas_ibfk_1` FOREIGN KEY (`cita_id`) REFERENCES `citas` (`id`),
  ADD CONSTRAINT `recetas_medicas_ibfk_2` FOREIGN KEY (`medicamento_id`) REFERENCES `medicamentos` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
