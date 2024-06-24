package com.emergentes.Servlet;

import com.emergentes.model.Transaccion;
import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/transacciones")
public class TransaccionServlet extends HttpServlet {
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
                deleteTransaccion(request, response);
                break;
            default:
                listTransacciones(request, response);
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
                updateTransaccion(request, response);
                break;
            default:
                insertTransaccion(request, response);
                break;
        }
    }

    private void listTransacciones(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM transacciones");

            request.setAttribute("transacciones", rs);
            request.getRequestDispatcher("/transacciones.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void insertTransaccion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Date fecha = Date.valueOf(request.getParameter("fecha"));
        int id_cliente = Integer.parseInt(request.getParameter("id_cliente"));
        int id_propiedad = Integer.parseInt(request.getParameter("id_propiedad"));
        int id_agente = Integer.parseInt(request.getParameter("id_agente"));
        double monto = Double.parseDouble(request.getParameter("monto"));

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
            String sql = "INSERT INTO transacciones (fecha, id_cliente, id_propiedad, id_agente, monto) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDate(1, fecha);
            stmt.setInt(2, id_cliente);
            stmt.setInt(3, id_propiedad);
            stmt.setInt(4, id_agente);
            stmt.setDouble(5, monto);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        response.sendRedirect("transacciones");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
            String sql = "SELECT * FROM transacciones WHERE id_transaccion = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Transaccion transaccion = new Transaccion();
                transaccion.setId_transaccion(rs.getInt("id_transaccion"));
                transaccion.setFecha(rs.getDate("fecha"));
                transaccion.setId_cliente(rs.getInt("id_cliente"));
                transaccion.setId_propiedad(rs.getInt("id_propiedad"));
                transaccion.setId_agente(rs.getInt("id_agente"));
                transaccion.setMonto(rs.getDouble("monto"));

                request.setAttribute("transaccion", transaccion);
                request.getRequestDispatcher("/transaccion-form.jsp").forward(request, response);
            } else {
                response.sendRedirect("transacciones");
            }
        } catch (SQLException e) {
            throw new ServletException("Error al obtener la transacción para editar", e);
        }
    }

    private void updateTransaccion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Date fecha = Date.valueOf(request.getParameter("fecha"));
        int id_cliente = Integer.parseInt(request.getParameter("id_cliente"));
        int id_propiedad = Integer.parseInt(request.getParameter("id_propiedad"));
        int id_agente = Integer.parseInt(request.getParameter("id_agente"));
        double monto = Double.parseDouble(request.getParameter("monto"));

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
            String sql = "UPDATE transacciones SET fecha = ?, id_cliente = ?, id_propiedad = ?, id_agente = ?, monto = ? WHERE id_transaccion = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDate(1, fecha);
            stmt.setInt(2, id_cliente);
            stmt.setInt(3, id_propiedad);
            stmt.setInt(4, id_agente);
            stmt.setDouble(5, monto);
            stmt.setInt(6, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        response.sendRedirect("transacciones");
    }

    private void deleteTransaccion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
            String sql = "DELETE FROM transacciones WHERE id_transaccion = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        response.sendRedirect("transacciones");
    }
}
