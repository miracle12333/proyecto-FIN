package com.emergentes.Servlet;

import com.emergentes.model.Contrato;
import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/contratos")
public class ContratoServlet extends HttpServlet {
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
                deleteContrato(request, response);
                break;
            default:
                listContratos(request, response);
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
                updateContrato(request, response);
                break;
            default:
                insertContrato(request, response);
                break;
        }
    }

    private void listContratos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM contratos");

            request.setAttribute("contratos", rs);
            request.getRequestDispatcher("/contratos.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void insertContrato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    int id_transaccion = Integer.parseInt(request.getParameter("id_transaccion"));
    String fecha_inicio = request.getParameter("fecha_inicio");
    String fecha_fin = request.getParameter("fecha_fin");
    double monto = Double.parseDouble(request.getParameter("monto"));

    try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
        String sql = "INSERT INTO contratos (id_transaccion, fecha_inicio, fecha_fin, monto) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id_transaccion);
        stmt.setDate(2, java.sql.Date.valueOf(fecha_inicio));
        stmt.setDate(3, java.sql.Date.valueOf(fecha_fin));
        stmt.setDouble(4, monto);

        stmt.executeUpdate();
    } catch (SQLException e) {
        throw new ServletException(e);
    }

    response.sendRedirect("contratos");
}


    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
            String sql = "SELECT * FROM contratos WHERE id_contrato = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Contrato contrato = new Contrato();
                contrato.setId_contrato(rs.getInt("id_contrato"));
                contrato.setId_transaccion(rs.getInt("id_transaccion"));
                contrato.setFecha_inicio(rs.getString("fecha_inicio"));
                contrato.setFecha_fin(rs.getString("fecha_fin"));
                contrato.setMonto(rs.getDouble("monto"));

                request.setAttribute("contrato", contrato);
                request.getRequestDispatcher("/contrato-form.jsp").forward(request, response);
            } else {
                response.sendRedirect("contratos");
            }
        } catch (SQLException e) {
            throw new ServletException("Error al obtener el contrato para editar", e);
        }
    }

    private void updateContrato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    int id = Integer.parseInt(request.getParameter("id"));
    int id_transaccion = Integer.parseInt(request.getParameter("id_transaccion"));
    String fecha_inicio = request.getParameter("fecha_inicio");
    String fecha_fin = request.getParameter("fecha_fin");
    double monto = Double.parseDouble(request.getParameter("monto"));

    try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
        String sql = "UPDATE contratos SET id_transaccion = ?, fecha_inicio = ?, fecha_fin = ?, monto = ? WHERE id_contrato = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id_transaccion);
        stmt.setDate(2, java.sql.Date.valueOf(fecha_inicio));
        stmt.setDate(3, java.sql.Date.valueOf(fecha_fin));
        stmt.setDouble(4, monto);
        stmt.setInt(5, id);

        stmt.executeUpdate();
    } catch (SQLException e) {
        throw new ServletException(e);
    }

    response.sendRedirect("contratos");
}


    private void deleteContrato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
            String sql = "DELETE FROM contratos WHERE id_contrato = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        response.sendRedirect("contratos");
    }
}
