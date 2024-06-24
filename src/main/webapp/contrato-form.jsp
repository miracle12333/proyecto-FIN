<%@ page import="com.emergentes.model.Contrato" %>
<%
    Contrato contrato = (Contrato) request.getAttribute("contrato");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Editar Contrato</title>
    <link rel="stylesheet" type="text/css" href="css/stilo.css">
</head>
<body>
    <div class="container">
        <h2>Editar Contrato</h2>
        <form action="contratos?action=update" method="post">
            <input type="hidden" name="id" value="<%= contrato.getId_contrato() %>" />
            <div>
                <label for="id_transaccion">ID Transacción:</label>
                <input type="text" id="id_transaccion" name="id_transaccion" value="<%= contrato.getId_transaccion() %>" required />
            </div>
            <div>
                <label for="fecha_inicio">Fecha Inicio:</label>
                <input type="date" id="fecha_inicio" name="fecha_inicio" value="<%= contrato.getFecha_inicio() %>" required />
            </div>
            <div>
                <label for="fecha_fin">Fecha Fin:</label>
                <input type="date" id="fecha_fin" name="fecha_fin" value="<%= contrato.getFecha_fin() %>" required />
            </div>
            <div>
                <label for="monto">Monto:</label>
                <input type="text" id="monto" name="monto" value="<%= contrato.getMonto() %>" required />
            </div>
            <div>
                <button type="submit">Guardar</button>
            </div>
        </form>
        <br/>
        <a href="contratos"><button type="button">Volver a la lista de contratos</button></a>
    </div>
</body>
</html>
