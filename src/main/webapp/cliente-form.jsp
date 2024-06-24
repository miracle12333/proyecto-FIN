<%@ page import="com.emergentes.model.Cliente" %>
<%
    Cliente cliente = (Cliente) request.getAttribute("cliente");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Editar Cliente</title>
    <link rel="stylesheet" type="text/css" href="css/stilo.css">
</head>
<body>
    <h2>Editar Cliente</h2>
    <form action="clientes?action=update" method="post">
        <input type="hidden" name="id" value="<%= cliente.getId() %>" />
        <div>
            <label for="nombre">Nombre:</label>
            <input type="text" id="nombre" name="nombre" value="<%= cliente.getNombre() %>" required />
        </div>
        <div>
            <label for="apellido">Apellido:</label>
            <input type="text" id="apellido" name="apellido" value="<%= cliente.getApellido() %>" required />
        </div>
        <div>
            <label for="correo">Correo:</label>
            <input type="text" id="correo" name="correo" value="<%= cliente.getCorreo() %>" required />
        </div>
        <div>
            <label for="telefono">Teléfono:</label>
            <input type="text" id="telefono" name="telefono" value="<%= cliente.getTelefono() %>" required />
        </div>
        <div>
            <button type="submit">Guardar</button>
        </div>
    </form>
    <br/>
    <a href="clientes"><button type="button">Volver a la lista de clientes</button></a>
</body>
</html>
