<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Contratos</title>
        <link rel="stylesheet" type="text/css" href="css/stilo.css">
    </head>
    <body>
        <div class="container">
            <h1>Contratos</h1>
            <table class="styled-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>ID Transacción</th>
                        <th>Fecha Inicio</th>
                        <th>Fecha Fin</th>
                        <th>Monto</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <% ResultSet rs = (ResultSet) request.getAttribute("contratos");
                    while (rs.next()) {%>
                    <tr>
                        <td><%= rs.getInt("id_contrato")%></td>
                        <td><%= rs.getInt("id_transaccion")%></td>
                        <td><%= rs.getString("fecha_inicio")%></td>
                        <td><%= rs.getString("fecha_fin")%></td>
                        <td>$ <%= rs.getDouble("monto")%></td>
                        <td>
                            <a href="contratos?action=edit&id=<%= rs.getInt("id_contrato")%>" class="button">Editar</a>
                            <a href="contratos?action=delete&id=<%= rs.getInt("id_contrato")%>" class="button">Eliminar</a>
                        </td>
                    </tr>
                    <% }%>
                </tbody>
            </table>
            <h2>Agregar Nuevo Contrato</h2>
            <form method="post" action="contratos">
                <input type="hidden" name="action" value="insert" />
                <label>ID Transacción: <input type="text" name="id_transaccion" /></label><br/>
                <label>Fecha Inicio: <input type="date" name="fecha_inicio" /></label><br/>
                <label>Fecha Fin: <input type="date" name="fecha_fin" /></label><br/>
                <label>Monto: <input type="text" name="monto" /></label><br/>
                <input type="submit" value="Agregar" class="button" />
            </form>
            <br/>
            <a href="index.jsp" class="button">Volver al índice</a>
        </div>
    </body>
</html>
