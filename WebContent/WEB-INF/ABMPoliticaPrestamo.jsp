<%@page import="java.text.DateFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.PoliticaPrestamo"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>ABM Politica Prestamo</title>
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
  $( function() {
    $( "#datepicker" ).datepicker({maxDate: -1,changeMonth: true,
        changeYear: true});
    $( "#datepicker" ).datepicker( "option", "dateFormat","yy-mm-dd");
  } );
  </script>
  
   <script>
  $( function() {
    $( "#datepicker2" ).datepicker({minDate:+0,changeMonth: true,
        changeYear: true});
    $( "#datepicker2" ).datepicker( "option", "dateFormat","yy-mm-dd");
  } );
  </script>
</head>
<body>
<div class="d-flex">
	<div class="table">
		<div class="theader">
		    <div class="tr">
		        <span class="td">ID PoliticaPrestamo</span>
		        <span class="td">CantMax</span>
		        <span class="td">Fecha</span>
		        <span class="td">Cantidad Dias Prestamo</span>     
		    </div>
	    </div>
	    <div class="tbody">
	    	<%
	    	@SuppressWarnings("unchecked")
	    	ArrayList<PoliticaPrestamo> ListaPoliticasPrestamos=(ArrayList<PoliticaPrestamo>)request.getAttribute("ListaPoliticasPrestamos");
	    	if (request.getParameter("editId") != null) {
		    	for (PoliticaPrestamo pp : ListaPoliticasPrestamos) {
		    		if (Integer.parseInt(request.getParameter("editId")) == pp.getId()) {
	        %>
	        <form class="tr" action="ABMPoliticaPrestamoServlet" method="POST" name="ABMPoliticaPrestamo">
	        	<input name="id" type="hidden" class="form-control" id="id" placeholder="id" value=<%=pp.getId() %> required>
	        	<span class="td"><%=pp.getId() %> </span>
	        	<input name="cant_max_libros_pend" type="number" class="form-control" id="cant_max_libros_pend" placeholder="Cant. maxima de libros pendientes" value="<%=pp.getCantMaxLibrosPend() %>" required>
	        	<input name="fecha_politica_prestamo" type="text" class="form-control" id="datepicker" placeholder="Fecha" value="<%=pp.getFechaPoliticaPrestamo()%>" onkeydown="return false"  required>
	        		<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
					<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
					<script>
					$( function() {
					  $( "#datepicker" ).datepicker({maxDate: -1,changeMonth: true,
					      changeYear: true});
					  $( "#datepicker" ).datepicker( "option", "dateFormat","yy-mm-dd");
					  $( "#datepicker" ).datepicker( "setDate", "<%=pp.getFechaPoliticaPrestamo().toString()%>" );
					} );
					</script>
	        	<input name="cant_dias_prestamo" type="number" class="form-control" id="cant_dias_prestamo" placeholder="Cant. dias de prestamo" value="<%=pp.getDiasPrestamo() %>" required>
	        	<span class="td"><button type="submit" name="action-type" value="editar" class="btn btn-success btn-block" >Aceptar</button> </span>
	        	<span class="td"><a class="btn btn-danger btn-block" href="ABMPoliticaPrestamoServlet">Cancelar</a></span>
	        </form>
	        <%  	} 
	    			else {
	        %><form class="tr" action="ABMPoliticaPrestamoServlet" method="POST" name="ABMPoliticaPrestamo">
	        	<input name="id" type="hidden" class="form-control" id="id" placeholder="id" value="<%=pp.getId() %>" required>
	        	<span class="td"><%=pp.getId() %> </span>
	        	<span class="td"><%=pp.getCantMaxLibrosPend() %> </span>
	        	<span class="td"><%=pp.getFechaPoliticaPrestamo() %> </span>
	        	<span class="td"><%=pp.getDiasPrestamo() %> </span>
	        	<span class="td"><a class="btn btn-primary btn-block" href="ABMPoliticaPrestamoServlet?editId=<%=pp.getId() %>">Editar</a> </span>
	        	<span class="td"><button type="submit" name="action-type" value="eliminar" class="btn btn-danger btn-block">Eliminar</button></span>
	        </form>
	        
	        <%}	}
	    	}
	    	else {
	    		for (PoliticaPrestamo pp : ListaPoliticasPrestamos) {
	        %><form class="tr" action="ABMPoliticaPrestamoServlet" method="POST" name="ABMPoliticaPrestamo">
	        	<input name="id" type="hidden" class="form-control" id="id" placeholder="id" value=<%=pp.getId() %> required>
	        	<span class="td"><%=pp.getId() %> </span>
	        	<span class="td"><%=pp.getCantMaxLibrosPend() %> </span>
	        	<span class="td"><%=pp.getFechaPoliticaPrestamo() %> </span>
	        	<span class="td"><%=pp.getDiasPrestamo() %> </span>
	        	<span class="td"><a class="btn btn-primary btn-block" href="ABMPoliticaPrestamoServlet?editId=<%=pp.getId() %>">Editar</a> </span>
	        	<span class="td"><button type="submit" name="action-type" value="eliminar" class="btn btn-danger btn-block">Eliminar</button></span>
	        </form>
	        <%	} 
	    	}%>
	    </div>
	    <div class="tfoot">
		    	<form class="tr" action="ABMPoliticaPrestamoServlet" method="POST" name="ABMPoliticaPrestamo">
		    		<span class="td"></span>
		    		<span class="td">
		    			<input name="cant_max_libros_pend" type="number" class="form-control" id="cant_max_libros_pend" placeholder="Cant. maxima de libros pendientes" required>
		    		</span>
		    		<span class="td">	
		    			<input name="fecha_politica_prestamo" type="text" class="form-control" id="datepicker2" placeholder="Fecha" onkeydown="return false" required>
		    		</span>	
		    		<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
					<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
				   <script>
					  $( function() {
					    $( "#datepicker2" ).datepicker({maxDate: -1,changeMonth: true,
					        changeYear: true});
					    $( "#datepicker2" ).datepicker( "option", "dateFormat","yy-mm-dd");
					  } );
					  </script>
		    		<span class="td">
		    			<input name="cant_dias_prestamo" type="number" class="form-control" id="cant_dias_prestamo" placeholder="Cant. dias de prestamo" required>
		    		</span>
		    			    		<span class="td">
		    			<button type="submit" name="action-type" value="agregar" class="btn btn-success btn-block" >Agregar</button>
		    		</span>
		    	</form>
	    </div>
	</div>
	<% if (ListaPoliticasPrestamos.isEmpty()) { %>
							<p style="font-size: 16px;">No hay resultados</p>
						<%} %>
</div>
</body>
</html>