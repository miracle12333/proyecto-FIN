<%@ page import="com.emergentes.model.Agente" %>
<%
    Agente agente = (Agente) request.getAttribute("agente");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Editar Agente</title>
    <link rel="stylesheet" type="text/css" href="css/stilo.css">
</head>
<body>
    <h2>Editar Agente</h2>
    <form action="agentes?action=update" method="post">
        <input type="hidden" name="id" value="<%= agente.getId() %>" />
        <div>
            <label>Nombre:</label>
            <input type="text" name="nombre" value="<%= agente.getNombre() %>" />
        </div>
        <div>
            <label>Apellido:</label>
            <input type="text" name="apellido" value="<%= agente.getApellido() %>" />
        </div>
        <div>
            <label>Correo:</label>
            <input type="text" name="correo" value="<%= agente.getCorreo() %>" />
        </div>
        <div>
            <label>Teléfono:</label>
            <input type="text" name="telefono" value="<%= agente.getTelefono() %>" />
        </div>
        <div>
            <label>Experiencia:</label>
            <input type="text" name="experiencia" value="<%= agente.getExperiencia() %>" />
        </div>
        <div>
            <input type="submit" value="Actualizar" />
        </div>
    </form>
    <br/>
    <a href="agentes"><button type="button">Cancelar</button></a>
</body>
</html>
