package com.mycompany.hospital_citas.Servlets.api;

import com.google.gson.Gson;
import com.mycompany.hospital_citas.CitasDao;
import com.mycompany.hospital_citas.Horario;
import com.mycompany.hospital_citas.HorarioDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet({ "/api/horarios-disponibles", "/api/doctor-fechas-disponibles" })
public class HorarioDisponibleServlet extends HttpServlet {

}