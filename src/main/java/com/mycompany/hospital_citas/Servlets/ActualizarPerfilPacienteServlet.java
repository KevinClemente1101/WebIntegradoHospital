package com.mycompany.hospital_citas.Servlets;

import com.mycompany.hospital_citas.Usuario;
import com.mycompany.hospital_citas.UsuarioDao;
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
import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/usuario/actualizarPerfil")
@MultipartConfig
public class ActualizarPerfilPacienteServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // Cambiar foto de perfil
        Part filePart = request.getPart("foto");
        String fileName = null;
        String uploadPath = getServletContext().getRealPath("/assets/img/usuarios/");
        boolean nuevaFoto = false;

        if (filePart != null && filePart.getSize() > 0 && filePart.getSubmittedFileName() != null && !filePart.getSubmittedFileName().isEmpty()) {
            String ext = filePart.getSubmittedFileName().substring(filePart.getSubmittedFileName().lastIndexOf('.'));
            fileName = usuario.getId() + ext;
            File uploads = new File(uploadPath);
            if (!uploads.exists()) uploads.mkdirs();
            File file = new File(uploads, fileName);
            filePart.write(file.getAbsolutePath());
            nuevaFoto = true;
        } else {
            fileName = usuario.getFoto();
        }

        UsuarioDao usuarioDao = new UsuarioDao();
        try {
            if (nuevaFoto) {
                usuarioDao.actualizarFotoPerfil(usuario.getId(), fileName);
                usuario.setFoto(fileName);
            }
            // Cambiar contraseña
            if (request.getParameter("cambiarPassword") != null) {
                String passwordActual = request.getParameter("passwordActual");
                String nuevaPassword = request.getParameter("nuevaPassword");
                String confirmarPassword = request.getParameter("confirmarPassword");
                if (!BCrypt.checkpw(passwordActual, usuario.getPassword())) {
                    request.setAttribute("error", "La contraseña actual es incorrecta.");
                    request.getRequestDispatcher("/usuario/perfil.jsp").forward(request, response);
                    return;
                }
                if (!nuevaPassword.equals(confirmarPassword)) {
                    request.setAttribute("error", "Las contraseñas no coinciden.");
                    request.getRequestDispatcher("/usuario/perfil.jsp").forward(request, response);
                    return;
                }
                // Restricciones de seguridad
                if (nuevaPassword.length() < 8 ||
                    !nuevaPassword.matches(".*[A-Z].*") ||
                    !nuevaPassword.matches(".*[a-z].*") ||
                    !nuevaPassword.matches(".*[0-9].*")) {
                    request.setAttribute("error", "La nueva contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula y un número.");
                    request.getRequestDispatcher("/usuario/perfil.jsp").forward(request, response);
                    return;
                }
                String hashedPassword = BCrypt.hashpw(nuevaPassword, BCrypt.gensalt());
                usuario.setPassword(hashedPassword);
                usuarioDao.updateUsuario(usuario);
                request.setAttribute("success", "Contraseña actualizada correctamente.");
            }
            session.setAttribute("usuario", usuario);
            response.sendRedirect(request.getContextPath() + "/usuario/perfil.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al actualizar el perfil: " + e.getMessage());
            request.getRequestDispatcher("/usuario/perfil.jsp").forward(request, response);
        }
    }
} 