<%@page import="java.util.ArrayList"%>
<%@page import="model.Prestamo"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Devolucion</title>
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
</head>
<body>
<div class="table">
		<div class="theader">
		    <div class="tr">
		        <span class="td">ID Prestamo</span>
		        <span class="td">Fecha Prestamo</span>
		        <span class="td">DIas de prestamo</span>
		        <span class="td">Estado del prestamo</span>
		    </div>
	    </div>
	    <div class="tbody">
	    	<%
	    	@SuppressWarnings("unchecked")
	    	ArrayList<Prestamo> listaPrestamo=(ArrayList<Prestamo>)request.getAttribute("ListaPrestamo");
		    	for (Prestamo p : listaPrestamo) {
	        %>
	        <form class="tr" action="DevolucionServlet" method="POST" name="Devolucion">
	        		
	        	<input name="id_prestamo" type="hidden" class="form-control" id="id_prestamo" placeholder="id_prestamo" value="<%=p.getId() %>"  required>
	        	<span class="td">
	        		<%=p.getId()%>
		    	</span>
	        	<span class="td">
	        		<%=p.getFechaPrestamo()%>
		    	</span>
	        	<span class="td">
	        		<%=p.getDiasPrestamo()%>
		    	</span>
	        	<span class="td">
	        		<%=p.getEstado()%>
		    	</span>
	        	<span class="td"><button type="submit" name="action-type" value="devolver" class="btn btn-danger btn-block" >Realizar devolución</button> </span>
	        </form>
	        <%
	          	
		    	} 
	    %>
	    
				</div>
</div>
	<% if (listaPrestamo.isEmpty()) { %>
							<p style="font-size: 16px;">No hay resultados</p>
						<%} %>
</body>
</html>