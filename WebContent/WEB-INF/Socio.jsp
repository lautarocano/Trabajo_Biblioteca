<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="es_AR">
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

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
        integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
        integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN"
        crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
        integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV"
        crossorigin="anonymous"></script>

</head>

<body>

  <div class="d-flex" id="wrapper">

    <!-- Sidebar -->
    <div class="bg-light border-right" id="sidebar-wrapper">
      <div class="sidebar-heading">Menú Socio </div>
      <div class="list-group list-group-flush">
        <a href="ReservaServlet" class="list-group-item list-group-item-action bg-light">Reservas</a>
        <a href="ReservasSocioServlet" class="list-group-item list-group-item-action bg-light">Mis Reservas</a>
        <a href="PrestamosSocioServlet" class="list-group-item list-group-item-action bg-light">Mis Préstamos</a>
        <a href="DatosSocioServlet" class="list-group-item list-group-item-action bg-light">Datos Personales</a>
        <a href="FinalizarReservaServlet" class="list-group-item list-group-item-action bg-light">Carrito de reserva</a>
      </div>
    </div>
    <!-- /#sidebar-wrapper -->

    <!-- Page Content -->
    <div id="page-content-wrapper">

      <nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom">
        <button class="btn btn-primary" id="menu-toggle"><span class="navbar-toggler-icon"></span></button>

          <ul class="navbar-nav ml-auto mt-2 mt-lg-0">
            <li class="nav-item">
              <a class="nav-link" href="LogoutServlet">Cerrar sesión</a>
            </li>
          </ul>
      </nav>

      <div class="container-fluid">
      <%String idMensaje = "mensaje";
      int numero = 0;
      while (request.getAttribute(idMensaje)!=null) {
      		if (request.getAttribute("clase-"+idMensaje)!=null) {%>
      	<div <%=request.getAttribute("clase-"+idMensaje)%> role="alert">
      	<% 	}
      		else {%>
      	<div class="alert alert-warning alert-dismissible fade show" role="alert">
      	<%	} %>
		  <%=request.getAttribute(idMensaje)%>
		  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
		    <span aria-hidden="true">&times;</span>
		  </button>
		</div>
      <%	numero++;
      		idMensaje = "mensaje"+numero;
      }
      	if (request.getAttribute("JSP")=="Reserva") { %>
       <%@ include file="/WEB-INF/Reserva.jsp"%>
       <%} 
      	else if (request.getAttribute("JSP")=="FinalizarReserva"){ %>
       <%@ include file="/WEB-INF/FinalizarReserva.jsp"%>
       <%}else if (request.getAttribute("JSP")=="DatosSocio"){%>
       <%@ include file="/WEB-INF/DatosSocio.jsp"%>
       <%}else if (request.getAttribute("JSP")=="MisReservas"){%>
       <%@ include file="/WEB-INF/MisReservas.jsp"%>
       <%}else if (request.getAttribute("JSP")=="MisPrestamos"){%>
       <%@ include file="/WEB-INF/MisPrestamos.jsp"%>
       <%}%>
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