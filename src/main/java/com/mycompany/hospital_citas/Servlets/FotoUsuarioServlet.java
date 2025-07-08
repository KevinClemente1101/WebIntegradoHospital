package com.mycompany.hospital_citas.Servlets;

import com.mycompany.hospital_citas.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/fotoUsuario")
public class FotoUsuarioServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta el parámetro id");
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID inválido");
            return;
        }
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT foto_blob, foto_tipo FROM usuarios WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next() && rs.getBytes("foto_blob") != null) {
                        byte[] imageBytes = rs.getBytes("foto_blob");
                        String tipo = rs.getString("foto_tipo");
                        if (tipo == null || tipo.isEmpty()) tipo = "image/jpeg";
                        response.setContentType(tipo);
                        response.setContentLength(imageBytes.length);
                        try (OutputStream out = response.getOutputStream()) {
                            out.write(imageBytes);
                        }
                        return;
                    }
                }
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
        // Si no hay imagen, servir una por defecto
        response.setContentType("image/png");
        try (InputStream in = getServletContext().getResourceAsStream("/assets/img/icons/default.jpg");
             OutputStream out = response.getOutputStream()) {
            if (in != null) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
        }
    }
} 