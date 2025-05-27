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

@WebServlet("/usuarios/dashboard")
public class DashboardServlet extends HttpServlet {


    private final CitaService citaService = new CitaService();
    // … inyecta también NotificationService si lo necesitas …

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        UsuarioDTO u = (UsuarioDTO) req.getSession().getAttribute("usuario");
        if (u == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        try {
            // Tus citas para la tabla…
            List<CitaDTO> citas = citaService.getUpcomingCitas(u.getId());
            req.setAttribute("citas", citas);
            
            // Contadores nuevos:
            int pendientes  = citaService.getCountPendientes(u.getId());
            int completadas = citaService.getCountCompletadas(u.getId());
            req.setAttribute("citasPendientes", pendientes);
            req.setAttribute("citasCompletadas", completadas);
        } catch (SQLException e) {
            throw new ServletException("Error cargando citas", e);
        }
        req.setAttribute("userName", u.getNombre());
        req.getRequestDispatcher("/usuario/dashboard.jsp")
           .forward(req, resp);
    }

}
