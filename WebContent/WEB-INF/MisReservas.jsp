<%@page import="java.util.ArrayList"%>
<%@page import="model.Reserva"%>
<%@page import="model.LibroReserva"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Mis Reservas</title>
</head>
<body>
<%
@SuppressWarnings("unchecked")
ArrayList<Reserva> listaReserva=(ArrayList<Reserva>)request.getAttribute("listaReserva");
if (listaReserva.isEmpty()) {
%>
<h1>No tenés eservas pendientes de entrega</h1>
<%	
}
else {
%>
<h1>Reservas pendientes de entrega</h1>
<div class="card-deck">
<%
for (Reserva r : listaReserva) {
	if (r.isEntregada()) {%>
<div class="card text-white bg-success mb-3" style="max-width: 18rem;">
  <div class="card-header">
  	<span>Libros ya disponibles</span>
  	<%} else { %>
<div class="card text-white bg-warning mb-3" style="max-width: 18rem;">
  <div class="card-header">
  	<span>Aún no disponible</span>
  	<%} %>
  </div>
  <div class="card-body">
    <h5 class="card-title">Reserva N° <%=r.getId()%></h5>
    <p class="card-text">Fecha: <%=r.getFechaReserva()%></p>
	<p class="card-text">Libros:</p>
	<% for (LibroReserva l : r.getLibros()) {%>
	<p class="card-text">- <%=l.getLibro().getTitulo()%></p>
	<% }  %>
  </div>
  <div class="card-footer">
  	<form action="ReservasSocioServlet" method="POST" name="CancelarReserva">
  	<input name="reserva" type="hidden" id="id_reserva" value=<%=r.getId()%> required>
  	<button type="submit" class="btn btn-danger" name="action" value="cancel">
		Cancelar Reserva
  	</button>
  </form>
  </div>
</div>
<%} %>
</div>
<%} %>
</body>
</html>