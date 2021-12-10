<%@page import="java.util.ArrayList"%>
<%@page import="model.Prestamo"%>
<%@page import="model.LineaDePrestamo"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Mis Préstamos</title>
</head>
<body>
<%
@SuppressWarnings("unchecked")
ArrayList<Prestamo> listaPrestamo=(ArrayList<Prestamo>)request.getAttribute("ListaPrestamo");
if (listaPrestamo == null || listaPrestamo.isEmpty()) {
%>
<h1>No tienes préstamos en curso</h1>
<%
}
else {
%>
<h1>Prestamos actuales</h1>
<div class="card-deck">
<%
for (Prestamo p : listaPrestamo) {
	if (p.getEstado() == Prestamo.estadoPrestamo.EnCurso) {%>
<div class="card text-white bg-success mb-3" style="max-width: 18rem;">
  <div class="card-header">
  	<span>En curso</span>
  	<%} else { %>
<div class="card text-white bg-warning mb-3" style="max-width: 18rem;">
  <div class="card-header">
  	<span>Devolución atrasada</span>
  	<%} %>
  </div>
  <div class="card-body">
    <h5 class="card-title">Préstamo N° <%=p.getId()%></h5>
    <p class="card-text">Fecha: <%=p.getFechaPrestamo()%></p>
	<p class="card-text">Libros:</p>
	<% for (LineaDePrestamo ldp : p.getLineasPrestamo()) {%>
	<p class="card-text">- <%=ldp.getEjemplar().getLibro().getTitulo()%></p>
	<% }  %>
  </div>
</div>
<%} %>
</div>
<%} %>
</body>
</html>