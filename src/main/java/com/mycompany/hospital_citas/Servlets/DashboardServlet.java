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

@WebServlet("/usuario/dashboard")
public class DashboardServlet extends HttpServlet {
    private final CitaService citaService = new CitaService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        UsuarioDTO u = (UsuarioDTO) req.getSession().getAttribute("usuario");
        if (u == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        String rol = u.getRol(); // "admin", "doctor", "paciente"
        
try {
            switch (rol) {
                case "paciente" -> {
                    List<CitaDTO> citas = citaService.getUpcomingCitas(u.getId());
                    int pendientes = citaService.getCountPendientes(u.getId());
                    int completadas = citaService.getCountCompletadas(u.getId());

                    req.setAttribute("citas", citas);
                    req.setAttribute("citasPendientes", pendientes);
                    req.setAttribute("citasCompletadas", completadas);

                    req.getRequestDispatcher("/usuario/dashboard.jsp").forward(req, resp);
                }
                case "doctor" -> {
                    // Datos generales para el doctor
                    req.getRequestDispatcher("/usuario/dashboard.jsp").forward(req, resp);
                }
                case "admin" -> {
                    // Datos generales para el admin
                    req.getRequestDispatcher("/usuario/dashboard.jsp").forward(req, resp);
                }
                default -> {
                    // Rol desconocido, redirige al login o a error
                    resp.sendRedirect(req.getContextPath() + "/login.jsp");
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Error cargando dashboard", e);
        }
    }

}
