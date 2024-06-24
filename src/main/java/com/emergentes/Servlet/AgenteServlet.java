package com.emergentes.Servlet;

import com.emergentes.model.Agente;
import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/agentes")
public class AgenteServlet extends HttpServlet {
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
                deleteAgente(request, response);
                break;
            default:
                listAgentes(request, response);
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
                updateAgente(request, response);
                break;
            default:
                insertAgente(request, response);
                break;
        }
    }

    private void listAgentes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM agente_inmobiliario");

            request.setAttribute("agentes", rs);
            request.getRequestDispatcher("/agentes.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void insertAgente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String correo = request.getParameter("correo");
        int telefono = Integer.parseInt(request.getParameter("telefono"));
        int experiencia = Integer.parseInt(request.getParameter("experiencia"));

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
            String sql = "INSERT INTO agente_inmobiliario (nombre, apellido, correo, telefono, experiencia) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nombre);
            stmt.setString(2, apellido);
            stmt.setString(3, correo);
            stmt.setInt(4, telefono);
            stmt.setInt(5, experiencia);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        response.sendRedirect("agentes");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
            String sql = "SELECT * FROM agente_inmobiliario WHERE id_agente = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Agente agente = new Agente();
                agente.setId(rs.getInt("id_agente"));
                agente.setNombre(rs.getString("nombre"));
                agente.setApellido(rs.getString("apellido"));
                agente.setCorreo(rs.getString("correo"));
                agente.setTelefono(rs.getInt("telefono"));
                agente.setExperiencia(rs.getInt("experiencia"));

                request.setAttribute("agente", agente);
                request.getRequestDispatcher("/agente-form.jsp").forward(request, response);
            } else {
                response.sendRedirect("agentes");
            }
        } catch (SQLException e) {
            throw new ServletException("Error al obtener el agente para editar", e);
        }
    }

    private void updateAgente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String correo = request.getParameter("correo");
        int telefono = Integer.parseInt(request.getParameter("telefono"));
        int experiencia = Integer.parseInt(request.getParameter("experiencia"));

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
            String sql = "UPDATE agente_inmobiliario SET nombre = ?, apellido = ?, correo = ?, telefono = ?, experiencia = ? WHERE id_agente = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nombre);
            stmt.setString(2, apellido);
            stmt.setString(3, correo);
            stmt.setInt(4, telefono);
            stmt.setInt(5, experiencia);
            stmt.setInt(6, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        response.sendRedirect("agentes");
    }

    private void deleteAgente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
            String sql = "DELETE FROM agente_inmobiliario WHERE id_agente = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        response.sendRedirect("agentes");
    }
}
