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
import java.sql.SQLException;

@WebServlet("/doctor/actualizarPerfil")
@MultipartConfig
public class ActualizarPerfilDoctorServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String biografia = request.getParameter("biografia");
        Part filePart = request.getPart("foto");
        String fileName = null;
        String uploadPath = getServletContext().getRealPath("/assets/img/doctores/");
        boolean nuevaFoto = false;

        // Guardar la imagen si se subió una nueva
        if (filePart != null && filePart.getSize() > 0 && filePart.getSubmittedFileName() != null && !filePart.getSubmittedFileName().isEmpty()) {
            String ext = filePart.getSubmittedFileName().substring(filePart.getSubmittedFileName().lastIndexOf('.'));
            fileName = usuario.getId() + ext;
            File uploads = new File(uploadPath);
            if (!uploads.exists()) uploads.mkdirs();
            File file = new File(uploads, fileName);
            filePart.write(file.getAbsolutePath());
            nuevaFoto = true;
        } else {
            fileName = usuario.getFoto(); // Mantener la foto anterior si no se subió nueva
        }

        UsuarioDao usuarioDao = new UsuarioDao();
        DoctorDao doctorDao = new DoctorDao();
        try {
            if (nuevaFoto) {
                usuarioDao.actualizarFotoPerfil(usuario.getId(), fileName);
                usuario.setFoto(fileName);
            }
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