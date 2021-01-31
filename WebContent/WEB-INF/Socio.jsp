<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>

  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>TP Biblioteca - Socio</title>

  <!-- Bootstrap core CSS -->
  <link href="CSS/bootstrap.min.css" rel="stylesheet">

  <!-- Custom styles for this template -->
  <link href="CSS/simple-sidebar.css" rel="stylesheet">

</head>

<body>

  <div class="d-flex" id="wrapper">

    <!-- Sidebar -->
    <div class="bg-light border-right" id="sidebar-wrapper">
      <div class="sidebar-heading">Menú Socio </div>
      <div class="list-group list-group-flush">
        <a href="ReservaServlet" class="list-group-item list-group-item-action bg-light">Reservas</a>
        <a href="#" class="list-group-item list-group-item-action bg-light">Mis Reservas</a>
        <a href="#" class="list-group-item list-group-item-action bg-light">Mis Préstamos</a>
        <a href="#" class="list-group-item list-group-item-action bg-light">Datos Personales</a>
        <a href="#" class="list-group-item list-group-item-action bg-light">Profile</a>
        <a href="#" class="list-group-item list-group-item-action bg-light">Status</a>
      </div>
    </div>
    <!-- /#sidebar-wrapper -->

    <!-- Page Content -->
    <div id="page-content-wrapper">

      <nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom">
        <button class="btn btn-primary" id="menu-toggle"><span class="navbar-toggler-icon"></span></button>

          <ul class="navbar-nav ml-auto mt-2 mt-lg-0">
            <li class="nav-item">
              <a class="nav-link" href="#">Cerrar sesión</a>
            </li>
          </ul>
      </nav>

      <div class="container-fluid">
      <%if (request.getAttribute("JSP")=="Reserva"){ %>
       <%@ include file="/WEB-INF/RealizarReserva.jsp"%>
       <%} 
      	else if (request.getAttribute("JSP")=="FinalizarReserva"){ %>
       <%@ include file="/WEB-INF/FinalizarReserva.jsp"%>
       <%} %>
      </div>
    </div>
    <!-- /#page-content-wrapper -->

  </div>
  <!-- /#wrapper -->


  <!-- Menu Toggle Script -->
  <script>
    $("#menu-toggle").click(function(e) {
      e.preventDefault();
      $("#wrapper").toggleClass("toggled");
    });
  </script>

</body>

</html>