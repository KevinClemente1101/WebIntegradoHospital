package com.mycompany.hospital_citas.Servlets;

import com.mycompany.hospital_citas.Especialidad;
import com.mycompany.hospital_citas.EspecialidadDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.annotation.MultipartConfig;
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
import java.util.List;

@WebServlet("/admin/especialidades")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1 MB
    maxFileSize = 1024 * 1024 * 10,  // 10 MB
    maxRequestSize = 1024 * 1024 * 15 // 15 MB
)
public class RegitroEspecialidad extends HttpServlet {
    private static final String UPLOAD_DIRECTORY = "assets/img/especialidades";

    private String getUploadPath(HttpServletRequest request) {
        String relativePath = UPLOAD_DIRECTORY;
        return request.getServletContext().getRealPath("") + File.separator + relativePath;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Debug: Imprimir todos los parámetros
        System.out.println("DEBUG: Parámetros recibidos:");
        request.getParameterMap().forEach((key, values) -> {
            System.out.println("  " + key + ": " + String.join(", ", values));
        });

        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        
        // Debug: Verificar valores
        System.out.println("DEBUG: nombre = " + nombre);
        System.out.println("DEBUG: descripcion = " + descripcion);
        
        // Validar que los campos requeridos no sean null
        if (nombre == null || nombre.trim().isEmpty()) {
            request.setAttribute("error", "El nombre de la especialidad es requerido.");
            request.getRequestDispatcher("/admin/nueva_especialidad.jsp").forward(request, response);
            return;
        }
        
        if (descripcion == null) {
            descripcion = "";
        }

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

        Especialidad especialidad = new Especialidad();
        especialidad.setNombre(nombre.trim());
        especialidad.setDescripcion(descripcion.trim());
        especialidad.setImagen(fileName);

        EspecialidadDao especialidadDao = new EspecialidadDao();
        try {
            boolean exito = especialidadDao.insertEspecialidad(especialidad);
            if (exito) {
                response.sendRedirect(request.getContextPath() + "/admin/especialidades");
            } else {
                request.setAttribute("error", "No se pudo registrar la especialidad.");
                request.getRequestDispatcher("/admin/nueva_especialidad.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            System.err.println("ERROR SQL: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error al guardar en la base de datos: " + e.getMessage());
            request.getRequestDispatcher("/admin/nueva_especialidad.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EspecialidadDao especialidadDao = new EspecialidadDao();
        try {
            List<Especialidad> especialidades = especialidadDao.getAllEspecialidades();
            request.setAttribute("especialidades", especialidades);
            request.getRequestDispatcher("/admin/especialidades.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
