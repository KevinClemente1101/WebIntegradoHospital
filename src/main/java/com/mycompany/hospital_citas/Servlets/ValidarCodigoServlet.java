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
import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/validar-codigo")
public class ValidarCodigoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("[DEBUG] Entrando a ValidarCodigoServlet.doPost");
        HttpSession session = request.getSession();
        String codigoIngresado = request.getParameter("codigo");
        String codigoCorrecto = (String) session.getAttribute("codigoVerificacion");
        Usuario usuario = (Usuario) session.getAttribute("usuarioRegistro");

        System.out.println("[DEBUG] Datos recibidos:");
        System.out.println("[DEBUG] - codigoIngresado: " + codigoIngresado);
        System.out.println("[DEBUG] - codigoCorrecto: " + codigoCorrecto);
        System.out.println("[DEBUG] - usuario: " + usuario);

        if (codigoIngresado == null || codigoCorrecto == null || usuario == null) {
            System.out.println("[DEBUG] Error: Datos faltantes en la sesión");
            request.setAttribute("error", "Error en la verificación. Por favor, intenta registrarte nuevamente.");
            request.getRequestDispatcher("registro.jsp").forward(request, response);
            return;
        }

        if (codigoIngresado.equals(codigoCorrecto)) {
            System.out.println("[DEBUG] Código correcto, procediendo a registrar usuario");
            // Encriptar la contraseña con BCrypt
            try {
                String hashedPassword = BCrypt.hashpw(usuario.getPassword(), BCrypt.gensalt());
                usuario.setPassword(hashedPassword);
                System.out.println("[DEBUG] Contraseña encriptada correctamente con BCrypt");
            } catch (Exception e) {
                System.out.println("[DEBUG] Error al encriptar contraseña: " + e.getMessage());
                throw new ServletException("Error al encriptar la contraseña", e);
            }

            // Guardar el usuario en la base de datos
            UsuarioDao usuarioDao = new UsuarioDao();
            try {
                usuarioDao.registrarUsuario(usuario);
                System.out.println("[DEBUG] Usuario registrado exitosamente");
                // Limpiar la sesión
                session.removeAttribute("usuarioRegistro");
                session.removeAttribute("codigoVerificacion");
                session.removeAttribute("correoVerificacion");
                // Redirigir al login
                response.sendRedirect("login.jsp");
            } catch (Exception e) {
                System.out.println("[DEBUG] Error al registrar usuario: " + e.getMessage());
                request.setAttribute("error", "Error al registrar el usuario: " + e.getMessage());
                request.getRequestDispatcher("verificar_codigo.jsp").forward(request, response);
            }
        } else {
            System.out.println("[DEBUG] Código incorrecto");
            request.setAttribute("error", "Código incorrecto. Inténtalo de nuevo.");
            request.getRequestDispatcher("verificar_codigo.jsp").forward(request, response);
        }
    }
} 