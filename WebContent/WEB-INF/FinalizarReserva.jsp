<%@page import="java.util.ArrayList"%>
<%@page import="model.Libro"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Finalizar Reserva</title>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
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
        
    <link rel="stylesheet" type="text/css" href="CSS/table-style.css" >
    
       <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <script>

  var disabledDays = <%=request.getAttribute("availableDays") %>;

  function disableAllTheseDays(date) {
  	  var ymd = $.datepicker.formatDate('yy-mm-dd', date);
      if($.inArray(ymd,disabledDays) != -1) {
    	return [true,"","Disponible"];
      } else {
    	return [false, "","No disponible"];
      }
 }
  
  $( function() {
    $( "#datepicker" ).datepicker({
    	minDate: 0,
    	maxDate: "+2M",
    	changeMonth: true,
        changeYear: true,
        dateFormat: "yy-mm-dd",
        beforeShowDay: disableAllTheseDays
        });
  } );

  </script>
</head>
<body>
<div class="table">
		<div class="theader">
		    <div class="tr">
		        <span class="td">Tï¿½tulo</span>
		        <span class="td">Autor</span>
		        <span class="td">Nro de Ediciï¿½n</span>
		        <span class="td">Fecha de Ediciï¿½n</span>
		        <span class="td">Genero</span>
		        <span class="td"></span>
		        <span class="td"></span>
		    </div>
	    </div>
	    <div class="tbody">
	    	<%
	    	@SuppressWarnings("unchecked")
	    	ArrayList<Libro> listaLibro=(ArrayList<Libro>)session.getAttribute("libros");
		    	for (Libro l : listaLibro) {
	        %>
	        <form class="tr" action="FinalizarReservaServlet" method="POST" name="FinalizarReserva">
	        		
	        		<input name="id_libro" type="hidden" class="form-control" id="id_libro" placeholder="id_libro" value="<%=l.getId() %>"  required>
	        	<span class="td">
	        		<%=l.getTitulo() %>
		    	</span>
	        	<span class="td">
	        		<%=l.getAutor() %>
		    	</span>
	        	<span class="td">
	        		<%=l.getNroEdicion() %>
		    	</span>
	        	<span class="td">
	        		<%=l.getFechaEdicion() %>
		    	</span>
		    	<span class="td">
		    		<%=l.getGenero().getDescripcion() %>
		    	</span>

	        	<span class="td"><button type="submit" name="action-type" value="borrar" class="btn btn-danger btn-block" >Borrar Reserva</button> </span>
	        </form>
	        <%
	          	
		    	} 
	    %>
	    
				</div>
</div>
<form action="FinalizarReservaServlet" method="POST" name="FinalizarReserva">
<p>Tipo de reserva:</p>
<div>
  <input type="radio" id="individual" name="tipo" value="individual"
         checked>
  <label for="individual">Individual</label>
</div>

<div>
  <input type="radio" id="conjunta" name="tipo" value="conjunta">
  <label for="conjunta">Conjunta</label>
</div>
<input name="fecha" type="text" class="form-control" id="datepicker" placeholder="Fecha de retiro" required>
<span ><button type="submit" name="action-type" value="finalizar" class="btn btn-primary btn-block" >Finalizar Reservas</button> </span>
<h1>O reserva tus libros individualmente:</h1>
<div class="card-deck">
<%
for (Libro l : listaLibro) {
%>
 <script>
 var disabledDays<%=l.getId()%> = <%=request.getAttribute("availableDays"+l.getId()) %>;

 function disableAllTheseDays(date) {
 	  var ymd = $.datepicker.formatDate('yy-mm-dd', date);
     if($.inArray(ymd,disabledDays<%=l.getId()%>) != -1) {
   	return [true,"","Disponible"];
     } else {
   	return [false, "","No disponible"];
     }
}
 
 $( function() {
   $( "#datepicker"<%=l.getId()%> ).datepicker({
   	minDate: 0,
   	maxDate: "+2M",
   	changeMonth: true,
       changeYear: true,
       dateFormat: "yy-mm-dd",
       beforeShowDay: disableAllTheseDays
       });
 } );

 </script>
<div class="card text-white bg-success mb-3" style="max-width: 18rem;">
  <div class="card-body">
    <h5 class="card-title"><%=l.getTitulo()%></h5>
    <p class="card-text">Autor: <%=l.getAutor()%></p>
	<p class="card-text">Edición:  <%=l.getFechaEdicion()%></p>
  </div>
  <div class="card-footer">
  	<input name="fecha<%=l.getId()%>" type="text" class="form-control" id="datepicker<%=l.getId()%>" placeholder="Fecha de retiro" required>
  </div>
</div>
<%} %>
</div>
</form>
</body>
</html>