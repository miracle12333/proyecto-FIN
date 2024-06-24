<%@ page import="com.emergentes.model.Visita" %>
<%
    Visita visita = (Visita) request.getAttribute("visita");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Editar Visita</title>
    <link rel="stylesheet" type="text/css" href="css/stilo.css">
</head>
<body>
    <h2>Editar Visita</h2>
    <form action="visitas?action=update" method="post">
        <input type="hidden" name="id" value="<%= visita.getId() %>" />
        <div>
            <label for="visita">Descripción:</label>
            <input type="text" id="visita" name="visita" value="<%= visita.getVisita() %>" required />
        </div>
        <div>
            <label for="fecha">Fecha:</label>
            <input type="date" id="fecha" name="fecha" value="<%= visita.getFecha() %>" required />
        </div>
        <div>
            <label for="idPropiedad">ID Propiedad:</label>
            <input type="text" id="idPropiedad" name="idPropiedad" value="<%= visita.getId_propiedad() %>" required />
        </div>
        <div>
            <label for="idCliente">ID Cliente:</label>
            <input type="text" id="idCliente" name="idCliente" value="<%= visita.getId_cliente() %>" required />
        </div>
        <div>
            <label for="idAgente">ID Agente:</label>
            <input type="text" id="idAgente" name="idAgente" value="<%= visita.getId_agente() %>" required />
        </div>
        <div>
            <button type="submit">Guardar</button>
        </div>
    </form>
    <br/>
    <a href="visitas"><button type="button">Volver a la lista de visitas</button></a>
</body>
</html>
