package com.mycompany.hospital_citas.Servlets;

import com.mycompany.hospital_citas.Usuario;
import com.mycompany.hospital_citas.UsuarioDao;
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
        System.out.println("DEBUG: Iniciando proceso de login");
        String dni = request.getParameter("dni");
        String password = request.getParameter("password");
        
        System.out.println("DEBUG: DNI recibido: " + dni);
        System.out.println("DEBUG: Password recibido: " + password);

        UsuarioDao usuarioDao = new UsuarioDao();
        try {
            System.out.println("DEBUG: Buscando usuario por DNI");
            Usuario usuario = usuarioDao.getUsuarioByDni(dni);
            if (usuario != null) {
                System.out.println("DEBUG: Usuario encontrado - ID: " + usuario.getId() + ", Rol: " + usuario.getRol());
                boolean passwordCorrecto = false;
                
                // Intentar verificar con BCrypt primero
                try {
                    System.out.println("DEBUG: Intentando verificar con BCrypt");
                    passwordCorrecto = BCrypt.checkpw(password, usuario.getPassword());
                    System.out.println("DEBUG: Resultado verificación BCrypt: " + passwordCorrecto);
                } catch (IllegalArgumentException e) {
                    System.out.println("DEBUG: Falló BCrypt, intentando con SHA-256");
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
                        System.out.println("DEBUG: Resultado verificación SHA-256: " + passwordCorrecto);
                        
                        // Si la contraseña SHA-256 es correcta, actualizar a BCrypt
                        if (passwordCorrecto) {
                            System.out.println("DEBUG: Actualizando contraseña a BCrypt");
                            String bcryptHash = BCrypt.hashpw(password, BCrypt.gensalt());
                            usuario.setPassword(bcryptHash);
                            usuarioDao.updateUsuario(usuario);
                        }
                    } catch (NoSuchAlgorithmException ex) {
                        System.err.println("ERROR: Error al verificar la contraseña: " + ex.getMessage());
                        throw new ServletException("Error al verificar la contraseña", ex);
                    }
                }

                if (passwordCorrecto) {
                    System.out.println("DEBUG: Login exitoso, creando sesión");
                    HttpSession session = request.getSession();
                    session.setAttribute("usuario", usuario);
                    
                    // Redirigir según el rol del usuario
                    if ("admin".equals(usuario.getRol())) {
                        System.out.println("DEBUG: Redirigiendo a dashboard de admin");
                        response.sendRedirect("admin/dashboard.jsp");
                    } else if ("doctor".equals(usuario.getRol())) {
                        System.out.println("DEBUG: Redirigiendo a dashboard de doctor");
                        response.sendRedirect("doctor/dashboardD.jsp");
                    } else {
                        System.out.println("DEBUG: Redirigiendo a index");
                        response.sendRedirect("index");
                    }
                } else {
                    System.out.println("DEBUG: Contraseña incorrecta");
                    request.setAttribute("error", "Usuario o contraseña incorrectos");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
            } else {
                System.out.println("DEBUG: Usuario no encontrado");
                request.setAttribute("error", "Usuario o contraseña incorrectos");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            System.err.println("ERROR: Error en la base de datos: " + e.getMessage());
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}
