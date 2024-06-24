<%@page import="java.sql.ResultSet"%>
<!DOCTYPE html>
<html>
<head>
    <title>Propiedades</title>
    <link rel="stylesheet" type="text/css" href="css/stilo.css">
</head>
<body>
    <div class="container">
        <h1>Propiedades</h1>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Dirección</th>
                    <th>Precio</th>
                    <th>Estado</th>
                    <th>Características</th>
                    <th>Dimensión</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <% ResultSet rs = (ResultSet) request.getAttribute("propiedades");
                   while (rs.next()) { %>
                    <tr>
                        <td><%= rs.getInt("id_propiedad") %></td>
                        <td><%= rs.getString("direccion") %></td>
                        <td>$ <%= rs.getBigDecimal("precio") %></td>
                        <td><%= rs.getString("estado") %></td>
                        <td><%= rs.getString("caracteristicas") %></td>
                        <td><%= rs.getString("dimension") %></td>
                        <td>
                            <a href="propiedades?action=edit&id=<%= rs.getInt("id_propiedad") %>" class="button">Editar</a>
                            <a href="propiedades?action=delete&id=<%= rs.getInt("id_propiedad") %>" class="button">Eliminar</a>
                        </td>
                    </tr>
                <% } %>
            </tbody>
        </table>
        <h2>Agregar Nueva Propiedad</h2>
        <form method="post" action="propiedades">
            <input type="hidden" name="action" value="insert" />
            <label>Dirección: <input type="text" name="direccion" /></label><br/>
            <label>Precio: <input type="text" name="precio" /></label><br/>
            <label>Estado: <input type="text" name="estado" /></label><br/>
            <label>Características: <input type="text" name="caracteristicas" /></label><br/>
            <label>Dimensión: <input type="text" name="dimension" /></label><br/>
            <input type="submit" value="Agregar" class="button" />
        </form>
        <br/>
        <a href="index.jsp" class="button">Volver al índice</a>
    </div>
</body>
</html>
