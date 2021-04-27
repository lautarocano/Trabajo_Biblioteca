<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>

  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>TP Biblioteca - Administrador</title>

  <!-- Bootstrap core CSS -->
  <link href="CSS/bootstrap.min.css" rel="stylesheet">

  <!-- Custom styles for this template -->
  <link href="CSS/simple-sidebar.css" rel="stylesheet">

</head>

<body>

  <div class="d-flex" id="wrapper">

    <!-- Sidebar -->
    <div class="bg-light border-right" id="sidebar-wrapper">
      <div class="sidebar-heading">Menú Administrador </div>
      <div class="list-group list-group-flush">
        <a href="ABMLibroServlet" class="list-group-item list-group-item-action bg-light">ABM Libro</a>
        <a href="ABMGeneroServlet" class="list-group-item list-group-item-action bg-light">ABM Género</a>
        <a href="ABMSocioServlet" class="list-group-item list-group-item-action bg-light">ABM Socio</a>
        <a href="ABMUsuarioServlet" class="list-group-item list-group-item-action bg-light">ABM Usuario</a>
        <a href="ABMPoliticaPrestamoServlet" class="list-group-item list-group-item-action bg-light">ABM Politica Prestamo</a>
        <a href="ABMPoliticaSancionServlet" class="list-group-item list-group-item-action bg-light">ABM Politica Sanción</a>  
        <a href="LogsServlet" target="_blank" class="list-group-item list-group-item-action bg-light">Abrir log de errores</a>     
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
      	<div class=<%="alert alert-warning alert-dismissible fade show"%> role="alert">
      	<%	} %>
		  <%=request.getAttribute("mensaje")%>
		  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
		    <span aria-hidden="true">&times;</span>
		  </button>
		</div>
      <%}
      	if (request.getAttribute("JSP")=="ABMLibro") { %>
       <%@ include file="/WEB-INF/ABMLibro.jsp"%>
       <%} 
      	else if (request.getAttribute("JSP")=="ABMPoliticaPrestamo"){ %>
       <%@ include file="/WEB-INF/ABMPoliticaPrestamo.jsp"%>
       <%}   else if (request.getAttribute("JSP")=="ABMPoliticaSancion"){%>
       <%@ include file="/WEB-INF/ABMPoliticaSancion.jsp"%>
       <%}
       else if (request.getAttribute("JSP")=="ABMSocio"){%>
       <%@ include file="/WEB-INF/ABMSocio.jsp"%>
       <%}
       else if (request.getAttribute("JSP")=="ABMUsuario"){%>
       <%@ include file="/WEB-INF/ABMUsuario.jsp"%>
       <%}
        else if (request.getAttribute("JSP")=="ABMEjemplar"){%>
        <%@ include file="/WEB-INF/ABMEjemplar.jsp"%>
        <%}
       else if (request.getAttribute("JSP")=="ABMGenero"){%>
       <%@ include file="/WEB-INF/ABMGenero.jsp"%>
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