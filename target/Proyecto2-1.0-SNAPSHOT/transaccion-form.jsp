<%@ page import="com.emergentes.model.Transaccion" %>
<%
    Transaccion transaccion = (Transaccion) request.getAttribute("transaccion");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Editar Transacción</title>
    <link rel="stylesheet" type="text/css" href="css/stilo.css">
</head>
<body>
    <h2>Editar Transacción</h2>
    <form action="transacciones?action=update" method="post">
        <input type="hidden" name="id" value="<%= transaccion.getId_transaccion() %>" />
        <div>
            <label for="fecha">Fecha:</label>
            <input type="date" id="fecha" name="fecha" value="<%= transaccion.getFecha() %>" required />
        </div>
        <div>
            <label for="id_cliente">ID Cliente:</label>
            <input type="text" id="id_cliente" name="id_cliente" value="<%= transaccion.getId_cliente() %>" required />
        </div>
        <div>
            <label for="id_propiedad">ID Propiedad:</label>
            <input type="text" id="id_propiedad" name="id_propiedad" value="<%= transaccion.getId_propiedad() %>" required />
        </div>
        <div>
            <label for="id_agente">ID Agente:</label>
            <input type="text" id="id_agente" name="id_agente" value="<%= transaccion.getId_agente() %>" required />
        </div>
        <div>
            <label for="monto">Monto:</label>
            <input type="text" id="monto" name="monto" value="<%= transaccion.getMonto() %>" required />
        </div>
        <div>
            <button type="submit">Guardar</button>
        </div>
    </form>
    <br/>
    <a href="transacciones"><button type="button">Volver a la lista de transacciones</button></a>
</body>
</html>
