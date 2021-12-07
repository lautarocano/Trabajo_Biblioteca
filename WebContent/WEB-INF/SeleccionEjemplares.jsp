<%@page import="java.util.ArrayList"%>
<%@page import="model.Ejemplar"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
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
<title>Selección de ejemplares</title>
</head>
<body>
<%
if (request.getAttribute("ejemplares")!=null) {
	@SuppressWarnings("unchecked")
	ArrayList<ArrayList<Ejemplar>> listaEjemplares=(ArrayList<ArrayList<Ejemplar>>)request.getAttribute("ejemplares");
	if (request.getAttribute("diasMaximoPrestamo")!=null) {;
%>
<p>Plazo máximo de devolución para el préstamo: <%=request.getAttribute("diasMaximoPrestamo")%></p>
<%} %>
<form class="card-deck" action="SeleccionEjemplaresServlet" method="POST" name="SeleccionEjemplares">
<input name="reserva" type="hidden" class="form-control" id="id_reserva" value=<%=request.getParameter("id_reserva") %> required>
<%	for (ArrayList<Ejemplar> ejemplares : listaEjemplares) {
		if (ejemplares.isEmpty()){
		%>
		<div class="card" style="width: 18rem;">
		  <div class="card-body">
		    <h5 class="card-title">Card title</h5>
		    <h6 class="card-subtitle mb-2 text-muted">No hay ejemplares disponibles</h6>
		  </div>
		</div>
		<%	
		}
		else {
		%>
		<div class="card" style="width: 18rem;">
		  <div class="card-body">
		    <h5 class="card-title"><%=ejemplares.get(0).getLibro().getTitulo() %></h5>
		    <h6 class="card-subtitle mb-2 text-muted"><%=ejemplares.get(0).getLibro().getAutor() %></h6>
			<label class="card-text" for=<%=ejemplares.get(0).getLibro().getId() %>>Id ejemplar:</label>
			<select name=<%=ejemplares.get(0).getLibro().getId() %> class="form-control" id=<%=ejemplares.get(0).getLibro().getId() %> >
	        	<% for (Ejemplar e : ejemplares) {%>
	        		<option value="<%=e.getId() %>" > <%=e.getId() %> </option>
	        	<%} %>
	        </select>
	        <label class="card-text" for=<%=ejemplares.get(0).getLibro().getId()+"checkbox" %> >
	        	<input name=<%=ejemplares.get(0).getLibro().getId()+"checkbox" %> type="checkbox" id=<%=ejemplares.get(0).getLibro().getId()+"checkbox" %> value="True" >
	        	Descartar libro
	        </label>
		  </div>
		</div>
<%
		}
	}
%>
<button type="submit" class="btn btn-success btn-block" >Iniciar Préstamo</button>
<a class="btn btn-danger btn-block" href="RetiroServlet">Cancelar</a>
</form>
<%} %>
</body>
</html>