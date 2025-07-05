package com.mycompany.hospital_citas.Servlets;

import com.mycompany.hospital_citas.Cita;
import com.mycompany.hospital_citas.CitasDao;
import com.mycompany.hospital_citas.MedicamentoDao;
import com.mycompany.hospital_citas.Medicamento;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/doctor/atender-cita-form")
public class AtenderCitaFormServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendRedirect(request.getContextPath() + "/doctor/citas");
            return;
        }
        try {
            int citaId = Integer.parseInt(idParam);
            CitasDao citasDao = new CitasDao();
            Cita cita = citasDao.getCitaById(citaId);
            MedicamentoDao medicamentoDao = new MedicamentoDao();
            List<Medicamento> medicamentos = medicamentoDao.getAllMedicamentosActivos();
            request.setAttribute("cita", cita);
            request.setAttribute("medicamentos", medicamentos);
            request.getRequestDispatcher("/doctor/atenderCita.jsp").forward(request, response);
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/doctor/citas");
        }
    }
} 