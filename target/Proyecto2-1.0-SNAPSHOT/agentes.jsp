<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Agentes</title>
        <link rel="stylesheet" type="text/css" href="css/stilo.css">
    </head>
    <body>
        <h1>Agentes</h1>
        <table>
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Apellido</th>
                <th>Correo</th>
                <th>Teléfono</th>
                <th>Experiencia</th>
                <th>Acciones</th>
            </tr>
            <% ResultSet rs = (ResultSet) request.getAttribute("agentes");
            while (rs.next()) {%>
            <tr>
                <td><%= rs.getInt("id_agente")%></td>
                <td><%= rs.getString("nombre")%></td>
                <td><%= rs.getString("apellido")%></td>
                <td><%= rs.getString("correo")%></td>
                <td><%= rs.getInt("telefono")%></td>
                <td><%= rs.getInt("experiencia")%></td>
                <td>
                    <a href="agentes?action=edit&id=<%= rs.getInt("id_agente")%>" class="button">Editar</a>
                    <a href="agentes?action=delete&id=<%= rs.getInt("id_agente")%>" class="button">Eliminar</a>
                </td>
            </tr>
            <% }%>
        </table>
        <h2>Agregar Nuevo Agente</h2>
        <form method="post" action="agentes">
            <input type="hidden" name="action" value="insert" />
            <label>Nombre: <input type="text" name="nombre" /></label><br/>
            <label>Apellido: <input type="text" name="apellido" /></label><br/>
            <label>Correo: <input type="text" name="correo" /></label><br/>
            <label>Teléfono: <input type="text" name="telefono" /></label><br/>
            <label>Experiencia: <input type="text" name="experiencia" /></label><br/>
            <input type="submit" value="Agregar" />
        </form>
        <br/>
        <a href="index.jsp"><button type="button">Volver al índice</button></a>
    </body>
</html>
