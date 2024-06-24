package com.emergentes.Servlet;

import com.emergentes.model.Propiedad;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/propiedades")
public class PropiedadServlet extends HttpServlet {
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
                deletePropiedad(request, response);
                break;
            default:
                listPropiedades(request, response);
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
                updatePropiedad(request, response);
                break;
            default:
                insertPropiedad(request, response);
                break;
        }
    }

    private void listPropiedades(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM propiedad");

            request.setAttribute("propiedades", rs);
            request.getRequestDispatcher("/propiedades.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void insertPropiedad(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String direccion = request.getParameter("direccion");
        String precioStr = request.getParameter("precio");
        String estado = request.getParameter("estado");
        String caracteristicas = request.getParameter("caracteristicas");
        String dimensionStr = request.getParameter("dimension");

        BigDecimal precio = null;
        BigDecimal dimension = null;

        try {
            precio = new BigDecimal(precioStr);
            dimension = new BigDecimal(dimensionStr);
        } catch (NumberFormatException e) {
            throw new ServletException("Los campos 'precio' y 'dimension' deben ser números.", e);
        }

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
            String sql = "INSERT INTO propiedad (direccion, precio, estado, caracteristicas, dimension) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, direccion);
            stmt.setBigDecimal(2, precio);
            stmt.setString(3, estado);
            stmt.setString(4, caracteristicas);
            stmt.setBigDecimal(5, dimension);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        response.sendRedirect("propiedades");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    int id = Integer.parseInt(request.getParameter("id"));

    try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
        String sql = "SELECT * FROM propiedad WHERE id_propiedad = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            // Mapear los datos del ResultSet a un objeto Propiedad
            Propiedad propiedad = new Propiedad();
            propiedad.setId(rs.getInt("id_propiedad"));
            propiedad.setDireccion(rs.getString("direccion"));
            propiedad.setPrecio(rs.getBigDecimal("precio"));
            propiedad.setEstado(rs.getString("estado"));
            propiedad.setCaracteristicas(rs.getString("caracteristicas"));
            propiedad.setDimension(rs.getBigDecimal("dimension"));

            // Pasar el objeto Propiedad como atributo al JSP
            request.setAttribute("propiedad", propiedad);
            request.getRequestDispatcher("/propiedad-form.jsp").forward(request, response);
        } else {
            response.sendRedirect("propiedades");
        }
    } catch (SQLException e) {
        throw new ServletException("Error al obtener la propiedad para editar", e);
    }
}


    private void updatePropiedad(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String direccion = request.getParameter("direccion");
        String precioStr = request.getParameter("precio");
        String estado = request.getParameter("estado");
        String caracteristicas = request.getParameter("caracteristicas");
        String dimensionStr = request.getParameter("dimension");

        BigDecimal precio = null;
        BigDecimal dimension = null;

        try {
            precio = new BigDecimal(precioStr);
            dimension = new BigDecimal(dimensionStr);
        } catch (NumberFormatException e) {
            throw new ServletException("Los campos 'precio' y 'dimension' deben ser números.", e);
        }

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
            String sql = "UPDATE propiedad SET direccion = ?, precio = ?, estado = ?, caracteristicas = ?, dimension = ? WHERE id_propiedad = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, direccion);
            stmt.setBigDecimal(2, precio);
            stmt.setString(3, estado);
            stmt.setString(4, caracteristicas);
            stmt.setBigDecimal(5, dimension);
            stmt.setInt(6, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        response.sendRedirect("propiedades");
    }

    private void deletePropiedad(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
            String sql = "DELETE FROM propiedad WHERE id_propiedad = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        response.sendRedirect("propiedades");
    }
}
