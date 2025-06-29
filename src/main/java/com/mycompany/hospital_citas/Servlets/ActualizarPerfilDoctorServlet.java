package com.mycompany.hospital_citas.Servlets;

import com.mycompany.hospital_citas.Usuario;
import com.mycompany.hospital_citas.UsuarioDao;
import com.mycompany.hospital_citas.DoctorDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;

@WebServlet("/doctor/actualizarPerfil")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 15 // 15 MB
)
public class ActualizarPerfilDoctorServlet extends HttpServlet {
    private static final String UPLOAD_DIRECTORY = "assets/img/usuarios";

    private String getUploadPath(HttpServletRequest request) {
        String relativePath = UPLOAD_DIRECTORY;
        return request.getServletContext().getRealPath("") + File.separator + relativePath;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String biografia = request.getParameter("biografia");
        Part filePart = request.getPart("foto");

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
        String fileName = usuario.getNombre().toLowerCase().replaceAll("\\s+", "_") + ".jpeg";
        Path filePath = Paths.get(uploadPath + File.separator + fileName);

        // Guardar el archivo
        if (filePart != null && filePart.getSize() > 0) {

            // Eliminar la foto anterior si existe y no es la predeterminada
            if (usuario.getFoto() != null && !usuario.getFoto().isEmpty()
                    && !usuario.getFoto().equals("default.jpg")) {
                Path oldFilePath = Paths.get(uploadPath + File.separator + usuario.getFoto());
                if (Files.exists(oldFilePath)) {
                    Files.delete(oldFilePath);
                }
            }

            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, filePath, StandardCopyOption.REPLACE_EXISTING);
            }
        } else {
            fileName = usuario.getFoto(); // Mantener la foto anterior si no se subió nueva
        }

        UsuarioDao usuarioDao = new UsuarioDao();
        DoctorDao doctorDao = new DoctorDao();
        try {

            usuarioDao.actualizarFotoPerfil(usuario.getId(), fileName);
            usuario.setFoto(fileName);

            doctorDao.actualizarBiografia(usuario.getId(), biografia);
            usuario.setBiografia(biografia); // Para la sesión
            session.setAttribute("usuario", usuario);
            request.setAttribute("success", "Perfil actualizado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al actualizar el perfil: " + e.getMessage());
        }
        response.sendRedirect(request.getContextPath() + "/doctor/perfilD.jsp");
    }
}