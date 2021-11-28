<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="es_AR">
<head>

  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>TP Biblioteca - Bibliotecario</title>
  
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

  <!-- Bootstrap core CSS -->
  <link href="CSS/bootstrap.min.css" rel="stylesheet">

  <!-- Custom styles for this template -->
  <link href="CSS/simple-sidebar.css" rel="stylesheet">

</head>

<body>

  <div class="d-flex" id="wrapper">

    <!-- Sidebar -->
    <div class="bg-light border-right" id="sidebar-wrapper">
      <div class="sidebar-heading">Menú Bibliotecario </div>
      <div class="list-group list-group-flush">
        <a href="RetiroServlet" class="list-group-item list-group-item-action bg-light">Retiro reserva</a>
        <a href="DevolucionServlet" class="list-group-item list-group-item-action bg-light">Devolución</a>
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
      <%if (request.getAttribute("mensaje")!=null) {
      		if (request.getAttribute("clase-mensaje")!=null) {%>
      	<div <%=request.getAttribute("clase-mensaje")%> role="alert">
      	<% 	}
      		else {%>
      	<div class="alert alert-warning alert-dismissible fade show" role="alert">
      	<%	} %>
		  <%=request.getAttribute("mensaje")%>
		  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
		    <span aria-hidden="true">&times;</span>
		  </button>
		</div>
      <%}
      	if (request.getAttribute("JSP")=="Devolucion"){ %>
       <%@ include file="/WEB-INF/Devolucion.jsp"%>
       <%} 
      	else if (request.getAttribute("JSP")=="Retiro"){ %>
      	<%@ include file="/WEB-INF/Retiro.jsp"%>
      <%}
      	else if (request.getAttribute("JSP")=="SeleccionEjemplar"){ %>
      	<%@ include file="/WEB-INF/SeleccionEjemplares.jsp"%>
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