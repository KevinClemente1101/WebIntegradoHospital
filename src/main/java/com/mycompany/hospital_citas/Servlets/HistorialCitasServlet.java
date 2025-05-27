package com.mycompany.hospital_citas.Servlets;

import com.mycompany.hospital_citas.dto.UsuarioDTO;
import com.mycompany.hospital_citas.dto.CitaDTO;
import com.mycompany.hospital_citas.service.CitaService;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/paciente/historial")
public class HistorialCitasServlet extends HttpServlet {

    private final CitaService service = new CitaService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 1) Recupera el objeto Usuario de la sesión
        UsuarioDTO usuario = (UsuarioDTO) req.getSession().getAttribute("usuario");
        if (usuario == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        try {
            // 2) Ahora sí pide el historial usando usuario.getId()
            List<CitaDTO> historial = service.getHistorial(usuario.getId());
            req.setAttribute("historial", historial);
            req.getRequestDispatcher("/paciente/historial.jsp")
               .forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Error al obtener el historial de citas", e);
        }
    }

}
