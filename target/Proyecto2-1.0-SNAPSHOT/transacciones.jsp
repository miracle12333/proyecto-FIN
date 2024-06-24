<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<!DOCTYPE html>
<html>
<head>
    <title>Transacciones</title>
    <link rel="stylesheet" type="text/css" href="css/stilo.css">
</head>
<body>
    <h1>Transacciones</h1>
    <table>
        <tr>
            <th>ID</th>
            <th>Fecha</th>
            <th>ID Cliente</th>
            <th>ID Propiedad</th>
            <th>ID Agente</th>
            <th>Monto</th>
            <th>Acciones</th>
        </tr>
        <%
            ResultSet rs = (ResultSet) request.getAttribute("transacciones");
            while (rs.next()) {
        %>
        <tr>
            <td><%= rs.getInt("id_transaccion") %></td>
            <td><%= rs.getDate("fecha") %></td>
            <td><%= rs.getInt("id_cliente") %></td>
            <td><%= rs.getInt("id_propiedad") %></td>
            <td><%= rs.getInt("id_agente") %></td>
            <td><%= rs.getDouble("monto") %></td>
            <td>
                <a href="transacciones?action=edit&id=<%= rs.getInt("id_transaccion") %>" class="button">Editar</a>
                <a href="transacciones?action=delete&id=<%= rs.getInt("id_transaccion") %>" class="button">Eliminar</a>
            </td>
        </tr>
        <% } %>
    </table>
    <h2>Agregar Nueva Transacción</h2>
    <form method="post" action="transacciones">
        <input type="hidden" name="action" value="insert" />
        <div>
            <label for="fecha">Fecha:</label>
            <input type="date" id="fecha" name="fecha" required />
        </div>
        <div>
            <label for="id_cliente">ID Cliente:</label>
            <input type="text" id="id_cliente" name="id_cliente" required />
        </div>
        <div>
            <label for="id_propiedad">ID Propiedad:</label>
            <input type="text" id="id_propiedad" name="id_propiedad" required />
        </div>
        <div>
            <label for="id_agente">ID Agente:</label>
            <input type="text" id="id_agente" name="id_agente" required />
        </div>
        <div>
            <label for="monto">Monto:</label>
            <input type="text" id="monto" name="monto" required />
        </div>
        <div>
            <button type="submit">Agregar</button>
        </div>
    </form>
    <br/>
    <a href="index.jsp"><button type="button">Volver al índice</button></a>
</body>
</html>
