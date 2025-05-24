package com.mycompany.hospital_citas.Servlets;

import com.mycompany.hospital_citas.Doctor;
import com.mycompany.hospital_citas.DoctorDao;
import com.mycompany.hospital_citas.Usuario;
import com.mycompany.hospital_citas.UsuarioDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@WebServlet("/admin/gestionarDoctores")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
                 maxFileSize = 1024 * 1024 * 10, // 10MB
                 maxRequestSize = 1024 * 1024 * 50) // 50MB
public class GestionarDoctoresServlet extends HttpServlet {

    private static final String UPLOAD_DIRECTORY = "/uploads"; // Directorio donde se guardarán las fotos

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DoctorDao doctorDao = new DoctorDao();
        try {
            // Obtener todos los doctores con su información completa
            List<Doctor> doctores = doctorDao.getAllDoctores();

            // Añadir logging para debug
            System.out.println("DEBUG: Ejecutando doGet en GestionarDoctoresServlet.");
            if (doctores != null) {
                System.out.println("DEBUG: Número de doctores obtenidos: " + doctores.size());
                for (Doctor doc : doctores) {
                    System.out.println("DEBUG: Doctor encontrado - ID: " + doc.getId() + 
                                     ", Nombre: " + doc.getUsuario().getNombre() + 
                                     ", Especialidad: " + doc.getEspecialidad().getNombre());
                }
            } else {
                System.out.println("DEBUG: La lista de doctores es null.");
            }

            request.setAttribute("doctores", doctores);
        } catch (Exception e) {
            System.err.println("DEBUG: Error en doGet: " + e.getMessage());
            e.printStackTrace();
            throw new ServletException("Error al obtener la lista de doctores", e);
        }
        request.getRequestDispatcher("gestionar_doctores.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int doctorId = Integer.parseInt(request.getParameter("doctorId"));
        String descripcion = request.getParameter("descripcion");
        Part fotoPart = request.getPart("foto");

        UsuarioDao usuarioDao = new UsuarioDao();
        try {
            Usuario doctor = usuarioDao.getUsuarioById(doctorId); // Necesitamos añadir este método en UsuarioDao
            if (doctor == null || !"doctor".equals(doctor.getRol())) {
                request.setAttribute("error", "Doctor no encontrado o rol incorrecto.");
                request.getRequestDispatcher("gestionar_doctores.jsp").forward(request, response);
                return;
            }

            doctor.setDescripcion_doctor(descripcion);

            // Manejar la subida de la foto
            if (fotoPart != null && fotoPart.getSize() > 0) {
                String fileName = Paths.get(fotoPart.getSubmittedFileName()).getFileName().toString();
                String uploadPath = getServletContext().getRealPath(UPLOAD_DIRECTORY);
                
                // Crear directorio si no existe
                Path uploadDir = Paths.get(uploadPath);
                if (!Files.exists(uploadDir)) {
                    Files.createDirectories(uploadDir);
                }

                // Generar un nombre de archivo único
                String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
                Path filePath = Paths.get(uploadPath, uniqueFileName);

                try (InputStream fileContent = fotoPart.getInputStream()) {
                    Files.copy(fileContent, filePath);
                }

                // Guardar la ruta relativa de la foto en la base de datos
                doctor.setFoto_doctor(UPLOAD_DIRECTORY + "/" + uniqueFileName);
            }

            usuarioDao.updateDoctorInfo(doctor); // Necesitamos añadir/modificar este método en UsuarioDao
            request.setAttribute("successMessage", "Información del doctor actualizada exitosamente!");

        } catch (SQLException e) {
            throw new ServletException("Error al actualizar la información del doctor", e);
        } catch (IOException e) {
             request.setAttribute("error", "Error al subir la foto: " + e.getMessage());
             // Mantener los datos ingresados en el formulario (opcional)
             // request.setAttribute("descripcion", descripcion);
             // request.setAttribute("doctorId", doctorId);
             request.getRequestDispatcher("gestionar_doctores.jsp").forward(request, response);
             return;
        }
         catch (Exception e) { // Capturar otras posibles excepciones
            request.setAttribute("error", "Ocurrió un error: " + e.getMessage());
            request.getRequestDispatcher("gestionar_doctores.jsp").forward(request, response);
            return;
        }

        // Redirigir de vuelta a la página de gestión para mostrar la lista actualizada
        doGet(request, response);
    }
} 