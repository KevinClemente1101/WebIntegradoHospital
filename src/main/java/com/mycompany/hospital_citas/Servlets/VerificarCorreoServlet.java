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
        String telefono = request.getParameter("telefono");
        String genero = request.getParameter("genero");
        String direccion = request.getParameter("direccion");
        String password = request.getParameter("password");
        String confirmarPassword = request.getParameter("confirmar_password");

        System.out.println("[DEBUG] Datos recibidos: nombre=" + nombre + ", apellido=" + apellido + ", dni=" + dni
                + ", fechaNacimiento=" + fechaNacimiento + ", email=" + email + ", telefono=" + telefono + ", genero="
                + genero);

        // Debugging específico para género
        System.out.println("[DEBUG] Valor de género recibido: '" + genero + "'");
        System.out.println("[DEBUG] Género es null: " + (genero == null));
        System.out.println("[DEBUG] Género está vacío: " + (genero != null && genero.trim().isEmpty()));
        System.out.println("[DEBUG] Longitud del género: " + (genero != null ? genero.length() : "null"));

        // Validaciones básicas
        if (!password.equals(confirmarPassword)) {
            System.out.println("[DEBUG] Contraseñas no coinciden");
            request.setAttribute("error", "Las contraseñas no coinciden.");
            request.getRequestDispatcher("registro.jsp").forward(request, response);
            return;
        }

        // Validación de género (mover antes de otras validaciones)
        if (genero == null || genero.trim().isEmpty()) {
            System.out.println("[DEBUG] Género es null o vacío");
            request.setAttribute("error", "Debe seleccionar un género.");
            request.getRequestDispatcher("registro.jsp").forward(request, response);
            return;
        }

        if (!genero.matches("^(M|F|O)$")) {
            System.out.println("[DEBUG] Género inválido: '" + genero + "'");
            request.setAttribute("error", "Género inválido.");
            request.getRequestDispatcher("registro.jsp").forward(request, response);
            return;
        }

        // Validación de nombre y apellido (solo letras, espacios y caracteres
        // españoles)
        if (!nombre.matches("^[A-Za-zÁáÉéÍíÓóÚúÑñ ]{2,50}$")) {
            System.out.println("[DEBUG] Nombre inválido");
            request.setAttribute("error",
                    "El nombre solo debe contener letras, espacios y caracteres españoles (2-50 caracteres).");
            request.getRequestDispatcher("registro.jsp").forward(request, response);
            return;
        }

        if (!apellido.matches("^[A-Za-zÁáÉéÍíÓóÚúÑñ ]{2,50}$")) {
            System.out.println("[DEBUG] Apellido inválido");
            request.setAttribute("error",
                    "El apellido solo debe contener letras, espacios y caracteres españoles (2-50 caracteres).");
            request.getRequestDispatcher("registro.jsp").forward(request, response);
            return;
        }

        // Validación de email
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$") || email.length() > 100) {
            System.out.println("[DEBUG] Email inválido");
            request.setAttribute("error", "Formato de email inválido o demasiado largo (máximo 100 caracteres).");
            request.getRequestDispatcher("registro.jsp").forward(request, response);
            return;
        }

        // Validación de contraseña
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,50}$")) {
            System.out.println("[DEBUG] Contraseña inválida");
            request.setAttribute("error",
                    "La contraseña debe tener entre 8 y 50 caracteres, incluyendo mayúscula, minúscula, número y carácter especial.");
            request.getRequestDispatcher("registro.jsp").forward(request, response);
            return;
        }

        // Validación de DNI
        if (!dni.matches("[0-9]{8}")) {
            System.out.println("[DEBUG] DNI inválido");
            request.setAttribute("error", "El DNI debe contener exactamente 8 dígitos numéricos.");
            request.getRequestDispatcher("registro.jsp").forward(request, response);
            return;
        }

        // Validación de teléfono
        if (!telefono.matches("^\\+51[0-9]{9}$")) {
            System.out.println("[DEBUG] Teléfono inválido");
            request.setAttribute("error",
                    "El teléfono debe tener el formato: +51 seguido de 9 dígitos (ejemplo: +51987654321).");
            request.getRequestDispatcher("registro.jsp").forward(request, response);
            return;
        }

        // Validación de dirección
        if (direccion != null && direccion.length() > 200) {
            System.out.println("[DEBUG] Dirección demasiado larga");
            request.setAttribute("error", "La dirección no puede exceder 200 caracteres.");
            request.getRequestDispatcher("registro.jsp").forward(request, response);
            return;
        }

        // Validación de fecha de nacimiento y edad
        try {
            java.time.LocalDate fechaNac = java.time.LocalDate.parse(fechaNacimiento);
            java.time.LocalDate hoy = java.time.LocalDate.now();
            int edad = java.time.Period.between(fechaNac, hoy).getYears();

            if (fechaNac.isAfter(hoy)) {
                System.out.println("[DEBUG] Fecha futura");
                request.setAttribute("error", "La fecha de nacimiento no puede ser futura.");
                request.getRequestDispatcher("registro.jsp").forward(request, response);
                return;
            } else if (edad < 18) {
                System.out.println("[DEBUG] Usuario menor de edad");
                request.setAttribute("error", "Debes ser mayor de edad para registrarte.");
                request.getRequestDispatcher("registro.jsp").forward(request, response);
                return;
            } else if (edad > 80) {
                System.out.println("[DEBUG] Usuario mayor de 80 años");
                request.setAttribute("error", "La edad máxima permitida es 80 años.");
                request.getRequestDispatcher("registro.jsp").forward(request, response);
                return;
            }
        } catch (Exception e) {
            System.out.println("[DEBUG] Error al procesar fecha de nacimiento");
            request.setAttribute("error", "Formato de fecha de nacimiento incorrecto.");
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
        usuario.setTelefono(telefono);
        usuario.setGenero(genero);
        usuario.setDireccion(direccion);
        usuario.setPassword(password); // Aún sin encriptar, se encripta al registrar
        usuario.setRol("paciente");
        usuario.setEstado(true); // Nuevo usuario activo por defecto
        session.setAttribute("usuarioRegistro", usuario);
        session.setAttribute("codigoVerificacion", codigo);
        session.setAttribute("correoVerificacion", email);

        // Detectar si el registro viene del panel de recepcionista
        String referer = request.getHeader("referer");
        if (referer != null && referer.contains("/recepcionista/registrar_paciente.jsp")) {
            session.setAttribute("registroRecepcionista", true);
        } else {
            session.removeAttribute("registroRecepcionista");
        }

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