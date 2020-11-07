<%@page import="java.util.ArrayList"%>
<%@page import="model.Ejemplar"%>
<%@page import="model.Libro"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>ABM Ejemplar</title>
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
</head>
<body>
<h2>Lista de ejemplares del libro "<%=((Libro)request.getAttribute("libro")).getTitulo()%>"</h2>
	<div class="table">
		<div class="theader">
		    <div class="tr">
		        <span class="td">ID Ejemplar</span>
		    </div>
	    </div>
	    <div class="tbody">
	    	<%
	    	@SuppressWarnings("unchecked")
	    	ArrayList<Ejemplar> listaEjemplar=(ArrayList<Ejemplar>)request.getAttribute("ListaEjemplares");
		    for (Ejemplar e : listaEjemplar) {
	        %><form class="tr" action="ABMEjemplarServlet" method="POST" name="ABMEjemplar">
	        	<input name="id" type="hidden" class="form-control" id="id" placeholder="id" value=<%=e.getId() %> required>
	        	<input name="IdLibro" type="hidden" class="form-control" id="IdLibro" placeholder="IdLibro" value=<%=((Libro)request.getAttribute("libro")).getId()%> required>
	        	<span class="td"><%=e.getId() %> </span>
	        	<span class="td"><button type="submit" name="action-type" value="eliminar" class="btn btn-danger btn-block">Eliminar</button></span>
	        </form>
	        <%	} %>
	    </div>
	    <div class="tfoot">
		    	<form class="tr" action="ABMEjemplarServlet" method="POST" name="ABMEjemplar">
		    		<span class="td"></span>
		    		<input name="IdLibro" type="hidden" class="form-control" id="IdLibro" placeholder="IdLibro" value=<%=((Libro)request.getAttribute("libro")).getId()%> required>
		    		<span class="td">
		    			<button type="submit" name="action-type" value="agregar" class="btn btn-success btn-block" >Sumar un ejemplar</button>
		    		</span>
		    	</form>
	    </div>
	</div>
	<% if (listaEjemplar.isEmpty()) { %>
							<p style="font-size: 16px;">No hay resultados</p>
						<%} %>
</body>
</html>