<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<!DOCTYPE html>
<html>
<head>
    <title>Visitas</title>
    <link rel="stylesheet" type="text/css" href="css/stilo.css">
</head>
<body>
    <h1>Visitas</h1>
    <table>
        <tr>
            <th>ID</th>
            <th>Descripción</th>
            <th>Fecha</th>
            <th>ID Propiedad</th>
            <th>ID Cliente</th>
            <th>ID Agente</th>
            <th>Acciones</th>
        </tr>
        <%
            ResultSet rs = (ResultSet) request.getAttribute("visitas");
            while (rs.next()) {
        %>
        <tr>
            <td><%= rs.getInt("id") %></td>
            <td><%= rs.getString("visita") %></td>
            <td><%= rs.getString("fecha") %></td>
            <td><%= rs.getInt("id_propiedad") %></td>
            <td><%= rs.getInt("id_cliente") %></td>
            <td><%= rs.getInt("id_agente") %></td>
            <td>
                <a href="visitas?action=edit&id=<%= rs.getInt("id") %>" class="button">Editar</a>
                <a href="visitas?action=delete&id=<%= rs.getInt("id") %>" class="button">Eliminar</a>
            </td>
        </tr>
        <% } %>
    </table>
    <h2>Agregar Nueva Visita</h2>
    <form method="post" action="visitas">
        <input type="hidden" name="action" value="insert" />
        <label>Descripción: <input type="text" name="visita" /></label><br/>
        <label>Fecha: <input type="date" name="fecha" /></label><br/>
        <label>ID Propiedad: <input type="text" name="idPropiedad" /></label><br/>
        <label>ID Cliente: <input type="text" name="idCliente" /></label><br/>
        <label>ID Agente: <input type="text" name="idAgente" /></label><br/>
        <input type="submit" value="Agregar" />
    </form>
    <br/>
    <a href="index.jsp"><button type="button">Volver al índice</button></a>
</body>
</html>
