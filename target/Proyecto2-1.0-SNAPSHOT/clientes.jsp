<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<!DOCTYPE html>
<html>
<head>
    <title>Clientes</title>
    <link rel="stylesheet" type="text/css" href="css/stilo.css">
</head>
<body>
    <div class="container">
        <h1>Clientes</h1>
        <table class="styled-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Apellido</th>
                    <th>Correo</th>
                    <th>Teléfono</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <% ResultSet rs = (ResultSet) request.getAttribute("clientes");
                   while (rs.next()) { %>
                    <tr>
                        <td><%= rs.getInt("id_cliente") %></td>
                        <td><%= rs.getString("nombre") %></td>
                        <td><%= rs.getString("apellido") %></td>
                        <td><%= rs.getString("correo") %></td>
                        <td><%= rs.getInt("telefono") %></td>
                        <td>
                            <a href="clientes?action=edit&id=<%= rs.getInt("id_cliente") %>" class="button">Editar</a>
                            <a href="clientes?action=delete&id=<%= rs.getInt("id_cliente") %>" class="button">Eliminar</a>
                        </td>
                    </tr>
                <% } %>
            </tbody>
        </table>
        <h2>Agregar Nuevo Cliente</h2>
        <form method="post" action="clientes">
            <input type="hidden" name="action" value="insert" />
            <label>Nombre: <input type="text" name="nombre" /></label><br/>
            <label>Apellido: <input type="text" name="apellido" /></label><br/>
            <label>Correo: <input type="text" name="correo" /></label><br/>
            <label>Teléfono: <input type="text" name="telefono" /></label><br/>
            <input type="submit" value="Agregar" class="button" />
        </form>
        <br/>
        <a href="index.jsp" class="button">Volver al índice</a>
    </div>
</body>
</html>
