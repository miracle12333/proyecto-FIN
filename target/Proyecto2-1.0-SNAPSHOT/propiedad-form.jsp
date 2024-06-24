<%@ page import="com.emergentes.model.Propiedad" %>
<%
    Propiedad propiedad = (Propiedad) request.getAttribute("propiedad");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Editar Propiedad</title>
    <link rel="stylesheet" type="text/css" href="css/stilo.css">
</head>
<body>
    <h2>Editar Propiedad</h2>
    <form action="propiedades?action=update" method="post">
        <input type="hidden" name="id" value="<%= propiedad.getId() %>" />
        <div>
            <label for="direccion">Dirección:</label>
            <input type="text" id="direccion" name="direccion" value="<%= propiedad.getDireccion() %>" required />
        </div>
        <div>
            <label for="precio">Precio:</label>
            <input type="text" id="precio" name="precio" value="<%= propiedad.getPrecio() %>" required />
        </div>
        <div>
            <label for="estado">Estado:</label>
            <input type="text" id="estado" name="estado" value="<%= propiedad.getEstado() %>" required />
        </div>
        <div>
            <label for="caracteristicas">Características:</label>
            <textarea id="caracteristicas" name="caracteristicas" required><%= propiedad.getCaracteristicas() %></textarea>
        </div>
        <div>
            <label for="dimension">Dimensión:</label>
            <input type="text" id="dimension" name="dimension" value="<%= propiedad.getDimension() %>" required />
        </div>
        <div>
            <button type="submit">Guardar</button>
            <a href="propiedades"><button type="button">Volver</button></a>
        </div>
    </form>
</body>
</html>
