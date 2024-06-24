package com.emergentes.Servlet;

import com.emergentes.model.Visita;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/visitas")
public class VisitaServlet extends HttpServlet {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/inmobiliaria";
    private static final String JDBC_USER = "postgres";
    private static final String JDBC_PASS = "admin";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteVisita(request, response);
                break;
            default:
                listVisitas(request, response);
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "insert";
        }

        switch (action) {
            case "update":
                updateVisita(request, response);
                break;
            case "insert":
                insertVisita(request, response);
                break;
            default:
                response.sendRedirect("visitas");
                break;
        }
    }

    private void listVisitas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, visita, fecha, id_propiedad, id_cliente, id_agente FROM visitas");

            request.setAttribute("visitas", rs);
            request.getRequestDispatcher("/visitas.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void insertVisita(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String visita = request.getParameter("visita");
    String fechaStr = request.getParameter("fecha");
    int idPropiedad = Integer.parseInt(request.getParameter("idPropiedad"));
    int idCliente = Integer.parseInt(request.getParameter("idCliente"));
    int idAgente = Integer.parseInt(request.getParameter("idAgente"));

    // Convertir fecha de String a java.sql.Date
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    java.util.Date fechaUtil;
    try {
        fechaUtil = sdf.parse(fechaStr);
        java.sql.Date fecha = new java.sql.Date(fechaUtil.getTime());

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
            String sql = "INSERT INTO visitas (visita, fecha, id_propiedad, id_cliente, id_agente) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, visita);
            stmt.setDate(2, fecha);
            stmt.setInt(3, idPropiedad);
            stmt.setInt(4, idCliente);
            stmt.setInt(5, idAgente);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        response.sendRedirect("visitas");
    } catch (ParseException e) {
        throw new ServletException("Error al parsear la fecha", e);
    }
}


    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
            String sql = "SELECT * FROM visitas WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Visita visita = new Visita();
                visita.setId(rs.getInt("id"));
                visita.setVisita(rs.getString("visita"));
                visita.setFecha(rs.getString("fecha"));
                visita.setId_propiedad(rs.getInt("id_propiedad"));
                visita.setId_cliente(rs.getInt("id_cliente"));
                visita.setId_agente(rs.getInt("id_agente"));

                request.setAttribute("visita", visita);
                request.getRequestDispatcher("/visita-form.jsp").forward(request, response);
            } else {
                response.sendRedirect("visitas");
            }
        } catch (SQLException e) {
            throw new ServletException("Error al obtener la visita para editar", e);
        }
    }

    private void updateVisita(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    int id = Integer.parseInt(request.getParameter("id"));
    String visita = request.getParameter("visita");
    String fechaStr = request.getParameter("fecha");
    int idPropiedad = Integer.parseInt(request.getParameter("idPropiedad"));
    int idCliente = Integer.parseInt(request.getParameter("idCliente"));
    int idAgente = Integer.parseInt(request.getParameter("idAgente"));

    // Convertir fecha de String a java.sql.Date
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    java.util.Date fechaUtil;
    try {
        fechaUtil = sdf.parse(fechaStr);
        java.sql.Date fecha = new java.sql.Date(fechaUtil.getTime());

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
            String sql = "UPDATE visitas SET visita = ?, fecha = ?, id_propiedad = ?, id_cliente = ?, id_agente = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, visita);
            stmt.setDate(2, fecha);
            stmt.setInt(3, idPropiedad);
            stmt.setInt(4, idCliente);
            stmt.setInt(5, idAgente);
            stmt.setInt(6, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        response.sendRedirect("visitas");
    } catch (ParseException e) {
        throw new ServletException("Error al parsear la fecha", e);
    }
}


    private void deleteVisita(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
            String sql = "DELETE FROM visitas WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        response.sendRedirect("visitas");
    }
}
