package com.mycompany.hospital_citas.Servlets;

import com.mycompany.hospital_citas.Usuario;
import com.mycompany.hospital_citas.UsuarioDao;
import com.mycompany.hospital_citas.Doctor;
import com.mycompany.hospital_citas.DoctorDao;
import com.mycompany.hospital_citas.Especialidad;
import com.mycompany.hospital_citas.EspecialidadDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/admin/registroUsuarios")
public class RegistroUsuariosServlet extends HttpServlet {

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

        // Validar que los campos obligatorios no estén vacíos
        if (nombre == null || nombre.trim().isEmpty() ||
            apellido == null || apellido.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            dni == null || dni.trim().isEmpty() ||
            fechaNacimientoStr == null || fechaNacimientoStr.trim().isEmpty() ||
            genero == null || genero.trim().isEmpty() ||
            rol == null || rol.trim().isEmpty()) {

            request.setAttribute("error", "Por favor, complete todos los campos obligatorios.");
            request.getRequestDispatcher("registro_usuarios.jsp").forward(request, response);
            return;
        }

        Date fechaNacimiento = null;
        try {
            fechaNacimiento = Date.valueOf(fechaNacimientoStr);
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", "Formato de fecha de nacimiento incorrecto.");
            request.getRequestDispatcher("registro_usuarios.jsp").forward(request, response);
            return;
        }
        
        // Validar DNI (solo 8 dígitos numéricos)
         if (!dni.matches("[0-9]{8}")) {
            request.setAttribute("error", "El DNI debe contener exactamente 8 dígitos numéricos.");
            request.getRequestDispatcher("registro_usuarios.jsp").forward(request, response);
            return;
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
                    // Validar campos específicos de doctor si el rol es doctor
                    if (especialidadIdStr == null || especialidadIdStr.trim().isEmpty() ||
                        biografia == null || biografia.trim().isEmpty()) {
                        // Si faltan campos de doctor, eliminar el usuario recién creado y mostrar error
                         usuarioDao.deleteUsuario(usuarioId); // Clean up created user
                        request.setAttribute("error", "Por favor, complete los campos de especialidad y biografía para el doctor.");
                        request.getRequestDispatcher("registro_usuarios.jsp").forward(request, response);
                        return;
                    }
                     int especialidadId = Integer.parseInt(especialidadIdStr);

                    Doctor doctor = new Doctor();
                    doctor.setUsuarioId(usuarioId);
                    doctor.setEspecialidadId(especialidadId);
                    doctor.setBiografia(biografia);

                    boolean exitoDoctor = doctorDao.insertDoctor(doctor);

                    if (exitoDoctor) {
                        request.setAttribute("successMessage", "Doctor registrado exitosamente!");
                         // Redirigir a la página de gestión de doctores
                         response.sendRedirect("gestionarDoctores"); // Redirect to manage doctors page
                    } else {
                        // Si falla la inserción del doctor, eliminar el usuario creado
                        usuarioDao.deleteUsuario(usuarioId); // Clean up created user
                        request.setAttribute("error", "No se pudo registrar la información del doctor.");
                        request.getRequestDispatcher("registro_usuarios.jsp").forward(request, response);
                    }
                } else { // Rol no es doctor
                    request.setAttribute("successMessage", "Usuario registrado exitosamente!");
                    // Redirigir a otra página (por ejemplo, un dashboard admin)
                    response.sendRedirect("dashboardAdmin"); // Example redirect
                }
            } else {
                request.setAttribute("error", "No se pudo registrar el usuario.");
                 request.getRequestDispatcher("registro_usuarios.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            // Si ocurre un error de SQL durante la inserción de usuario o doctor
             e.printStackTrace(); // Print stack trace for debugging
            request.setAttribute("error", "Error de base de datos al registrar usuario/doctor: " + e.getMessage());
            // Intentar limpiar el usuario si ya se había insertado antes del error del doctor (opcional)
             // try { if (usuarioId > 0) usuarioDao.deleteUsuario(usuarioId); } catch (SQLException ex) { ex.printStackTrace(); }
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
                    System.out.println("DEBUG: Especialidad encontrada - ID: " + esp.getId() + ", Nombre: " + esp.getNombre());
                }
                
                // Asegurarnos de que las especialidades se pasen al JSP
                request.setAttribute("especialidades", especialidades);
                System.out.println("DEBUG: Especialidades establecidas en el request");
                
                // Verificar que las especialidades estén en el request
                List<Especialidad> especialidadesVerificadas = (List<Especialidad>) request.getAttribute("especialidades");
                if (especialidadesVerificadas != null) {
                    System.out.println("DEBUG: Verificación - Número de especialidades en el request: " + especialidadesVerificadas.size());
                    for (Especialidad esp : especialidadesVerificadas) {
                        System.out.println("DEBUG: Verificación - Especialidad en request - ID: " + esp.getId() + ", Nombre: " + esp.getNombre());
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