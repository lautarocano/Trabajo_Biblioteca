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

  var availableDays = <%=request.getAttribute("availableDays") %>;

  function disableDays(date) {
  	  var ymd = $.datepicker.formatDate('yy-mm-dd', date);
      if($.inArray(ymd,availableDays) != -1) {
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
        beforeShowDay: disableDays
        });
  } );

  </script> 
<%
@SuppressWarnings("unchecked")
ArrayList<Libro> listaLibro=(ArrayList<Libro>)session.getAttribute("libros");
String datepicker;
for (Libro l : listaLibro) {
	datepicker="\"#datepicker"+l.getId()+'"';
%>
 <script>
 var availableDays<%=l.getId() %> = <%=request.getAttribute("availableDays"+l.getId()) %>;

 function disableDays<%=l.getId() %>(date) {
 	  var ymd = $.datepicker.formatDate('yy-mm-dd', date);
     if(eval(<%="$.inArray(ymd,availableDays"+l.getId()+") != -1" %>)) {
   	return [true,"","Disponible"];
     } else {
   	return [false, "","No disponible"];
     }
}
 
 $( function() {
   $( <%=datepicker%> ).datepicker({
   	minDate: 0,
   	maxDate: "+2M",
   	changeMonth: true,
       changeYear: true,
       dateFormat: "yy-mm-dd",
       beforeShowDay: eval('disableDays'+<%=l.getId() %>)
       });
 } );

 </script>
<%} %> 

  <script>
  $(document).ready(function() {
	    $("input[name$='tipo']").click(function() {
	        var test = $(this).val();

	        $("div.desc").hide();
	        $(eval(test)).show();
	    });
	});
  </script>

</head>
<body>
<div class="table">
		<div class="theader">
		    <div class="tr">
		        <span class="td">Titulo</span>
		        <span class="td">Autor</span>
		        <span class="td">Nro de Edición</span>
		        <span class="td">Fecha de Edición</span>
		        <span class="td">Genero</span>
		        <span class="td"></span>
		        <span class="td"></span>
		    </div>
	    </div>
	    <div class="tbody">
	    	<%
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

	        	<span class="td"><button type="submit" name="action-type" value="borrar" class="btn btn-danger btn-block" >Quitar libro</button> </span>
	        </form>
	        <%
	          	
		    	} 
	    %>
	    
				</div>
</div>
<form action="FinalizarReservaServlet" method="POST" name="FinalizarReserva">
<p>Tipo de reserva:</p>
<div>
  <input type="radio" id="conjunta" name="tipo" value="conjunta" checked>
  <label for="conjunta">Conjunta</label>
</div>

<div>
  <input type="radio" id="individual" name="tipo" value="individual">
  <label for="individual">Individual</label>
</div>

<div id="conjunta" class="desc">
<input name="fecha" type="text" class="form-control" id="datepicker" placeholder="Fecha de retiro" >
</div>
<div id="individual" class="desc card-deck" style="display: none">
<%
for (Libro l : listaLibro) {
%>
<div class="card text-white bg-success mb-3" style="max-width: 18rem;">
  <div class="card-body">
    <h5 class="card-title"><%=l.getTitulo()%></h5>
    <p class="card-text">Autor: <%=l.getAutor()%></p>
	<p class="card-text">Edición:  <%=l.getFechaEdicion()%></p>
  </div>
  <div class="card-footer">
  	<input name="fecha<%=l.getId()%>" type="text" class="form-control" id="datepicker<%=l.getId()%>" placeholder="Fecha de retiro">
  </div>
</div>
<%} %>
</div>
<span ><button type="submit" name="action-type" value="finalizar" class="btn btn-primary btn-block" >Finalizar Reservas</button> </span>
</form>
</body>
</html>