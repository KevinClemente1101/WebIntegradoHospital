package com.mycompany.hospital_citas.Servlets;

import com.mycompany.hospital_citas.Usuario;
import com.mycompany.hospital_citas.util.EmailUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Random;
import jakarta.mail.MessagingException;

@WebServlet("/verificar-correo")
public class VerificarCorreoServlet extends HttpServlet {
    public VerificarCorreoServlet() {
        super();
        System.out.println("[DEBUG] VerificarCorreoServlet cargado correctamente");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("[DEBUG] Entrando a VerificarCorreoServlet.doPost");
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String dni = request.getParameter("dni");
        String fechaNacimiento = request.getParameter("fecha_nacimiento");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmarPassword = request.getParameter("confirmar_password");

        System.out.println("[DEBUG] Datos recibidos: nombre=" + nombre + ", apellido=" + apellido + ", dni=" + dni + ", fechaNacimiento=" + fechaNacimiento + ", email=" + email);

        // Validaciones básicas (puedes agregar más si lo deseas)
        if (!password.equals(confirmarPassword)) {
            System.out.println("[DEBUG] Contraseñas no coinciden");
            request.setAttribute("error", "Las contraseñas no coinciden.");
            request.getRequestDispatcher("registro.jsp").forward(request, response);
            return;
        }
        if (!dni.matches("^[0-9]{8}$")) {
            System.out.println("[DEBUG] DNI inválido");
            request.setAttribute("error", "El DNI debe tener exactamente 8 números.");
            request.getRequestDispatcher("registro.jsp").forward(request, response);
            return;
        }
        if (!nombre.matches("^[A-Za-zÁáÉéÍíÓóÚúÑñ ]+$") || !apellido.matches("^[A-Za-zÁáÉéÍíÓóÚúÑñ ]+$")) {
            System.out.println("[DEBUG] Nombre o apellido inválido");
            request.setAttribute("error", "El nombre y apellido solo deben contener letras.");
            request.getRequestDispatcher("registro.jsp").forward(request, response);
            return;
        }
        java.time.LocalDate fechaNac = java.time.LocalDate.parse(fechaNacimiento);
        java.time.LocalDate hoy = java.time.LocalDate.now();
        int edad = java.time.Period.between(fechaNac, hoy).getYears();
        if (edad < 18) {
            System.out.println("[DEBUG] Usuario menor de edad");
            request.setAttribute("error", "Debes ser mayor de edad para registrarte.");
            request.getRequestDispatcher("registro.jsp").forward(request, response);
            return;
        }

        // Generar código de 6 dígitos
        String codigo = String.format("%06d", new java.util.Random().nextInt(1000000));
        System.out.println("[DEBUG] Código generado: " + codigo);

        // Guardar datos y código en la sesión
        HttpSession session = request.getSession();
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setDni(dni);
        usuario.setFechaNacimiento(fechaNacimiento);
        usuario.setEmail(email);
        usuario.setPassword(password); // Aún sin encriptar, se encripta al registrar
        usuario.setRol("paciente");
        session.setAttribute("usuarioRegistro", usuario);
        session.setAttribute("codigoVerificacion", codigo);
        session.setAttribute("correoVerificacion", email);

        System.out.println("[DEBUG] Datos guardados en sesión:");
        System.out.println("[DEBUG] - usuarioRegistro: " + usuario);
        System.out.println("[DEBUG] - codigoVerificacion: " + codigo);
        System.out.println("[DEBUG] - correoVerificacion: " + email);

        // Enviar el código al correo
        try {
            EmailUtil.enviarCodigoVerificacion(email, codigo);
            System.out.println("[DEBUG] Correo enviado exitosamente a: " + email);
        } catch (jakarta.mail.MessagingException e) {
            System.out.println("[DEBUG] Error al enviar correo: " + e.getMessage());
            request.setAttribute("error", "No se pudo enviar el correo de verificación: " + e.getMessage());
            request.getRequestDispatcher("registro.jsp").forward(request, response);
            return;
        }

        // Redirigir a la página para ingresar el código
        System.out.println("[DEBUG] Redirigiendo a verificar_codigo.jsp");
        response.sendRedirect("verificar_codigo.jsp");
    }
} 