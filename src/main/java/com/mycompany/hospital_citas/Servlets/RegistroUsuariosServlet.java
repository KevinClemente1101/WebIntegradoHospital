package com.mycompany.hospital_citas.Servlets;

import com.mycompany.hospital_citas.Usuario;
import com.mycompany.hospital_citas.UsuarioDao;
import com.mycompany.hospital_citas.Doctor;
import com.mycompany.hospital_citas.DoctorDao;
import com.mycompany.hospital_citas.Especialidad;
import com.mycompany.hospital_citas.EspecialidadDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/admin/registroUsuarios")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 15 // 15 MB
)
public class RegistroUsuariosServlet extends HttpServlet {
    private static final String UPLOAD_DIRECTORY = "assets/img/usuarios";

    private String getUploadPath(HttpServletRequest request) {
        String relativePath = UPLOAD_DIRECTORY;
        return request.getServletContext().getRealPath("") + File.separator + relativePath;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String dni = request.getParameter("dni");
        String telefono = request.getParameter("telefono");
        String direccion = request.getParameter("direccion");
        String fechaNacimientoStr = request.getParameter("fecha_nacimiento");
        String genero = request.getParameter("genero");
        String rol = request.getParameter("rol");

        // Obtener parámetros específicos del doctor (serán null si el rol no es doctor)
        String especialidadIdStr = request.getParameter("especialidad_id");
        String biografia = request.getParameter("biografia");
        String codigoColegiatura = request.getParameter("codigo_colegiatura");

        // Imagen de perfil
        Part filePart = request.getPart("imagen");

        // Debug: Verificar archivo
        if (filePart != null) {
            System.out.println("DEBUG: Archivo recibido: " + filePart.getSubmittedFileName());
        } else {
            System.out.println("DEBUG: No se recibió archivo");
        }

        // Crear el directorio si no existe
        String uploadPath = getUploadPath(request);
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Generar nombre único para el archivo
        String fileName = nombre.toLowerCase().replaceAll("\\s+", "_") + ".jpeg";
        Path filePath = Paths.get(uploadPath + File.separator + fileName);

        // Guardar el archivo
        if (filePart != null && filePart.getSize() > 0) {
            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, filePath, StandardCopyOption.REPLACE_EXISTING);
            }
        } else {
            // Si no se subió archivo, usar una imagen por defecto
            fileName = "default.jpeg";
        }

        // Validar que los campos obligatorios no estén vacíos
        if (nombre == null || nombre.trim().isEmpty() ||
                apellido == null || apellido.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                dni == null || dni.trim().isEmpty() ||
                telefono == null || telefono.trim().isEmpty() ||
                fechaNacimientoStr == null || fechaNacimientoStr.trim().isEmpty() ||
                genero == null || genero.trim().isEmpty() ||
                rol == null || rol.trim().isEmpty()) {

            request.setAttribute("error", "Por favor, complete todos los campos obligatorios.");
            request.getRequestDispatcher("registro_usuarios.jsp").forward(request, response);
            return;
        }

        // Validación de nombre y apellido (solo letras, espacios y caracteres
        // españoles)
        if (!nombre.matches("^[A-Za-zÁáÉéÍíÓóÚúÑñ ]{2,50}$")) {
            request.setAttribute("error",
                    "El nombre solo debe contener letras, espacios y caracteres españoles (2-50 caracteres).");
            request.getRequestDispatcher("registro_usuarios.jsp").forward(request, response);
            return;
        }

        if (!apellido.matches("^[A-Za-zÁáÉéÍíÓóÚúÑñ ]{2,50}$")) {
            request.setAttribute("error",
                    "El apellido solo debe contener letras, espacios y caracteres españoles (2-50 caracteres).");
            request.getRequestDispatcher("registro_usuarios.jsp").forward(request, response);
            return;
        }

        // Validación de email
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$") || email.length() > 100) {
            request.setAttribute("error", "Formato de email inválido o demasiado largo (máximo 100 caracteres).");
            request.getRequestDispatcher("registro_usuarios.jsp").forward(request, response);
            return;
        }

        // Validación de contraseña
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,50}$")) {
            request.setAttribute("error",
                    "La contraseña debe tener entre 8 y 50 caracteres, incluyendo mayúscula, minúscula, número y carácter especial.");
            request.getRequestDispatcher("registro_usuarios.jsp").forward(request, response);
            return;
        }

        // Validación de DNI (solo 8 dígitos numéricos)
        if (!dni.matches("[0-9]{8}")) {
            request.setAttribute("error", "El DNI debe contener exactamente 8 dígitos numéricos.");
            request.getRequestDispatcher("registro_usuarios.jsp").forward(request, response);
            return;
        }

        // Validación de teléfono (formato +51 + 9 dígitos)
        if (!telefono.matches("^\\+51[0-9]{9}$")) {
            request.setAttribute("error",
                    "El teléfono debe tener el formato: +51 seguido de 9 dígitos (ejemplo: +51987654321).");
            request.getRequestDispatcher("registro_usuarios.jsp").forward(request, response);
            return;
        }

        // Validación de dirección (máximo 200 caracteres)
        if (direccion != null && direccion.length() > 200) {
            request.setAttribute("error", "La dirección no puede exceder 200 caracteres.");
            request.getRequestDispatcher("registro_usuarios.jsp").forward(request, response);
            return;
        }

        // Validación de fecha de nacimiento y edad
        Date fechaNacimiento = null;
        try {
            fechaNacimiento = Date.valueOf(fechaNacimientoStr);

            // Calcular edad
            java.time.LocalDate fechaNac = fechaNacimiento.toLocalDate();
            java.time.LocalDate hoy = java.time.LocalDate.now();
            int edad = java.time.Period.between(fechaNac, hoy).getYears();

            if (fechaNac.isAfter(hoy)) {
                request.setAttribute("error", "La fecha de nacimiento no puede ser futura.");
                request.getRequestDispatcher("registro_usuarios.jsp").forward(request, response);
                return;
            } else if (edad < 18) {
                request.setAttribute("error", "El usuario debe tener al menos 18 años.");
                request.getRequestDispatcher("registro_usuarios.jsp").forward(request, response);
                return;
            } else if (edad > 80) {
                request.setAttribute("error", "La edad máxima permitida es 80 años.");
                request.getRequestDispatcher("registro_usuarios.jsp").forward(request, response);
                return;
            }
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", "Formato de fecha de nacimiento incorrecto.");
            request.getRequestDispatcher("registro_usuarios.jsp").forward(request, response);
            return;
        }

        // Validación de género
        if (!genero.matches("^(M|F|O)$")) {
            request.setAttribute("error", "Género inválido.");
            request.getRequestDispatcher("registro_usuarios.jsp").forward(request, response);
            return;
        }

        // Validación de rol
        if (!rol.matches("^(admin|doctor|recepcionista)$")) {
            request.setAttribute("error", "Rol inválido.");
            request.getRequestDispatcher("registro_usuarios.jsp").forward(request, response);
            return;
        }

        // Validaciones específicas para doctor
        if ("doctor".equals(rol)) {
            if (especialidadIdStr == null || especialidadIdStr.trim().isEmpty()) {
                request.setAttribute("error", "Debe seleccionar una especialidad para el doctor.");
                request.getRequestDispatcher("registro_usuarios.jsp").forward(request, response);
                return;
            }

            if (biografia != null && biografia.length() > 500) {
                request.setAttribute("error", "La biografía no puede exceder 500 caracteres.");
                request.getRequestDispatcher("registro_usuarios.jsp").forward(request, response);
                return;
            }

            if (codigoColegiatura == null || codigoColegiatura.trim().isEmpty()) {
                request.setAttribute("error", "El código de colegiatura es obligatorio para doctores.");
                request.getRequestDispatcher("registro_usuarios.jsp").forward(request, response);
                return;
            }

            if (!codigoColegiatura.matches("^[A-Za-z0-9]{5,20}$")) {
                request.setAttribute("error",
                        "El código de colegiatura debe contener solo números y letras (5-20 caracteres).");
                request.getRequestDispatcher("registro_usuarios.jsp").forward(request, response);
                return;
            }
        }

        // Encriptar la contraseña con BCrypt
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setApellido(apellido);
        nuevoUsuario.setEmail(email);
        nuevoUsuario.setPassword(hashedPassword);
        nuevoUsuario.setDni(dni);
        nuevoUsuario.setTelefono(telefono);
        nuevoUsuario.setDireccion(direccion);
        nuevoUsuario.setFechaNacimiento(fechaNacimientoStr); // Guardar como String para consistencia con Usuario class
        nuevoUsuario.setGenero(genero);
        nuevoUsuario.setRol(rol);
        nuevoUsuario.setEstado(true); // Nuevo usuario activo por defecto
        nuevoUsuario.setFoto(fileName);

        UsuarioDao usuarioDao = new UsuarioDao();
        DoctorDao doctorDao = new DoctorDao(); // Instantiate DoctorDao

        try {
            // Verificar si el email o DNI ya existen
            if (usuarioDao.getUsuarioByEmail(email) != null) {
                request.setAttribute("error", "El email ya está registrado.");
                request.getRequestDispatcher("registro_usuarios.jsp").forward(request, response);
                return;
            }
            if (usuarioDao.getUsuarioByDni(dni) != null) {
                request.setAttribute("error", "El DNI ya está registrado.");
                request.getRequestDispatcher("registro_usuarios.jsp").forward(request, response);
                return;
            }

            // Insertar usuario y obtener su ID
            int usuarioId = usuarioDao.insertUsuario(nuevoUsuario); // Use insertUsuario that returns ID

            if (usuarioId > 0) {
                // Si el rol es doctor, insertar también en la tabla medicos
                if ("doctor".equals(rol)) {
                    // Crear el registro en la tabla medicos
                    Doctor doctor = new Doctor();
                    doctor.setUsuarioId(usuarioId);
                    doctor.setEspecialidadId(Integer.parseInt(especialidadIdStr));
                    doctor.setBiografia(biografia);
                    doctor.setCodigoColegiatura(codigoColegiatura);

                    boolean exitoDoctor = doctorDao.insertDoctor(doctor);

                    if (exitoDoctor) {
                        request.setAttribute("successMessage", "Doctor registrado exitosamente!");
                        response.sendRedirect(request.getContextPath() + "/admin/medicos");
                    } else {
                        usuarioDao.deleteUsuario(usuarioId);
                        request.setAttribute("error", "No se pudo registrar la información del doctor.");
                        request.getRequestDispatcher("registro_usuarios.jsp").forward(request, response);
                    }
                } else { // Rol no es doctor
                    request.setAttribute("successMessage", "¡Usuario registrado exitosamente!");
                    response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                    return;
                }
            } else {
                request.setAttribute("error", "No se pudo registrar el usuario.");
                request.getRequestDispatcher("registro_usuarios.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            // Si ocurre un error de SQL durante la inserción de usuario o doctor
            e.printStackTrace(); // Print stack trace for debugging
            request.setAttribute("error", "Error de base de datos al registrar usuario/doctor: " + e.getMessage());
            // Intentar limpiar el usuario si ya se había insertado antes del error del
            // doctor (opcional)
            // try { if (usuarioId > 0) usuarioDao.deleteUsuario(usuarioId); } catch
            // (SQLException ex) { ex.printStackTrace(); }
            request.getRequestDispatcher("registro_usuarios.jsp").forward(request, response);

        } catch (NumberFormatException e) { // Catch error if especialidadId is not a valid number
            request.setAttribute("error", "ID de especialidad no válido.");
            request.getRequestDispatcher("registro_usuarios.jsp").forward(request, response);
        } catch (Exception e) { // Catch any other unexpected exceptions
            e.printStackTrace(); // Print stack trace for debugging
            request.setAttribute("error", "Ocurrió un error inesperado durante el registro: " + e.getMessage());
            request.getRequestDispatcher("registro_usuarios.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("DEBUG: Iniciando doGet en RegistroUsuariosServlet");

        EspecialidadDao especialidadDao = new EspecialidadDao();
        try {
            System.out.println("DEBUG: Intentando obtener especialidades de la base de datos");
            List<Especialidad> especialidades = especialidadDao.getAllEspecialidades();

            if (especialidades != null) {
                System.out.println("DEBUG: Número de especialidades obtenidas: " + especialidades.size());
                for (Especialidad esp : especialidades) {
                    System.out.println(
                            "DEBUG: Especialidad encontrada - ID: " + esp.getId() + ", Nombre: " + esp.getNombre());
                }

                // Asegurarnos de que las especialidades se pasen al JSP
                request.setAttribute("especialidades", especialidades);
                System.out.println("DEBUG: Especialidades establecidas en el request");

                // Verificar que las especialidades estén en el request
                List<Especialidad> especialidadesVerificadas = (List<Especialidad>) request
                        .getAttribute("especialidades");
                if (especialidadesVerificadas != null) {
                    System.out.println("DEBUG: Verificación - Número de especialidades en el request: "
                            + especialidadesVerificadas.size());
                    for (Especialidad esp : especialidadesVerificadas) {
                        System.out.println("DEBUG: Verificación - Especialidad en request - ID: " + esp.getId()
                                + ", Nombre: " + esp.getNombre());
                    }
                } else {
                    System.out.println("DEBUG: Verificación - Las especialidades no están en el request");
                }
            } else {
                System.out.println("DEBUG: La lista de especialidades es null");
            }

            System.out.println("DEBUG: Redirigiendo a registro_usuarios.jsp");
            request.getRequestDispatcher("/admin/registro_usuarios.jsp").forward(request, response);
        } catch (SQLException e) {
            System.err.println("ERROR: Error al cargar las especialidades: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error al cargar las especialidades: " + e.getMessage());
            request.getRequestDispatcher("/admin/registro_usuarios.jsp").forward(request, response);
        } catch (Exception e) {
            System.err.println("ERROR: Error inesperado: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error inesperado: " + e.getMessage());
            request.getRequestDispatcher("/admin/registro_usuarios.jsp").forward(request, response);
        }
    }
}