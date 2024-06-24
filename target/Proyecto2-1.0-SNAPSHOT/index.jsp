<%@page import="java.sql.ResultSet"%>
<!DOCTYPE html>
<html>
<head>
    <title>Gestión de Inmobiliarias</title>
    <link rel="stylesheet" type="text/css" href="css/stilo.css">
    <style>
      body {
    background-image: url('images/fondo.jpg'); 
    background-size: cover; 
    background-repeat: no-repeat; 
    background-position-x: center; 
    background-position-y: 20%; 
}

    </style>
</head>
<body>
    <div class="container">
        <h1>Sistema de Gestión de Inmobiliarias</h1>
        <ul class="styled-list">
            <li><a href="propiedades" class="button">Propiedades</a></li>
            <li><a href="clientes" class="button">Clientes</a></li>
            <li><a href="agentes" class="button">Agentes</a></li>
            <li><a href="visitas" class="button">Visitas</a></li>
            <li><a href="transacciones" class="button">Transacciones</a></li>
            <li><a href="contratos" class="button">Contratos</a></li>
        </ul>
    </div>
</body>
</html>
