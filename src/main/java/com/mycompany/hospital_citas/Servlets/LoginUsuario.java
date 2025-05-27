package com.mycompany.hospital_citas.Servlets;

import com.mycompany.hospital_citas.dto.UsuarioDTO;
import com.mycompany.hospital_citas.dao.UsuarioDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/login")
public class LoginUsuario extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String dni = request.getParameter("dni");
        String password = request.getParameter("password");

        UsuarioDao usuarioDao = new UsuarioDao();
        try {
            UsuarioDTO usuario = usuarioDao.getUsuarioByDni(dni);
            if (usuario != null) {
                boolean passwordCorrecto = false;
                
                // Intentar verificar con BCrypt primero
                try {
                    passwordCorrecto = BCrypt.checkpw(password, usuario.getPassword());
                } catch (IllegalArgumentException e) {
                    // Si falla BCrypt, intentar con SHA-256
                    try {
                        MessageDigest md = MessageDigest.getInstance("SHA-256");
                        byte[] hash = md.digest(password.getBytes());
                        StringBuilder sb = new StringBuilder();
                        for (byte b : hash) {
                            sb.append(String.format("%02x", b));
                        }
                        String hashedPassword = sb.toString();
                        passwordCorrecto = hashedPassword.equals(usuario.getPassword());
                        
                        // Si la contraseña SHA-256 es correcta, actualizar a BCrypt
                        if (passwordCorrecto) {
                            String bcryptHash = BCrypt.hashpw(password, BCrypt.gensalt());
                            usuario.setPassword(bcryptHash);
                            usuarioDao.updateUsuario(usuario);
                        }
                    } catch (NoSuchAlgorithmException ex) {
                        throw new ServletException("Error al verificar la contraseña", ex);
                    }
                }

                if (passwordCorrecto) {
                    HttpSession session = request.getSession();
                    session.setAttribute("usuario", usuario);
                    if (usuario.getRol().equals("paciente")) {
                        response.sendRedirect("usuario/dashboard");
                    } else if (usuario.getRol().equals("admin")) {
                        response.sendRedirect("usuario/dashboard");
                    } else if (usuario.getRol().equals("doctor")) {
                        response.sendRedirect("usuario/dashboard");
                    } else {
                        request.setAttribute("error", "Usuario o contraseña incorrectos");
                        request.getRequestDispatcher("login.jsp").forward(request, response);
                    }
                } else {
                    request.setAttribute("error", "Usuario o contraseña incorrectos");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("error", "Usuario o contraseña incorrectos");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}
