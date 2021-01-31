<%@page import="java.util.ArrayList"%>
<%@page import="model.Reserva"%>
<%@page import="model.LibroReserva"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Retiro</title>

<link rel="stylesheet" type="text/css" href="CSS/table-style.css" >

</head>
<body>
<form action="RetiroServlet">
<span>ID de socio:</span><input name="id-socio" type="number" class="form-control" id="id-socio" placeholder="id">
</form>
<div class="table">
		<div class="theader">
		    <div class="tr">
		        <span class="td">ID Reserva</span>
		        <span class="td">Fecha Reserva</span>
		        <span class="td">Libros</span>
		        <span class="td">Socio</span>
		    </div>
	    </div>
	    <div class="tbody">
	    	<%
	    	@SuppressWarnings("unchecked")
	    	ArrayList<Reserva> listaReserva=(ArrayList<Reserva>)request.getAttribute("listaReserva");
		    	for (Reserva r : listaReserva) {
	        %>
	        <form class="tr" action="RetiroServlet" method="POST" name="Retiro">
	        		
	        	<input name="id_prestamo" type="hidden" class="form-control" id="id_prestamo" placeholder="id_prestamo" value="<%=r.getId() %>"  required>
	        	<span class="td">
	        		<%=r.getId()%>
		    	</span>
	        	<span class="td">
	        		<%=r.getFechaReserva()%>
		    	</span>
	        	<span class="td">
	        		<%=r.getLibros().size()%>
		    	</span>
	        	<span class="td">
	        		<%=r.getSocio().getNombreyApellido()%>
		    	</span>
	        	<span class="td">
		        	<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modal<%=r.getId()%>">
					  Ver Reserva
					</button>
				</span>
				<!-- Modal -->
				<div class="modal fade" id="modal<%=r.getId()%>" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel<%=r.getId()%>" aria-hidden="true">
				  <div class="modal-dialog" role="document">
				    <div class="modal-content">
				      <div class="modal-header">
				        <h5 class="modal-title" id="exampleModalLabel<%=r.getId()%>">Reserva N° <%=r.getId()%></h5>
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
				          <span aria-hidden="true">&times;</span>
				        </button>
				      </div>
				      <div class="modal-body">
				        <p>Socio: <%=r.getSocio().getNombreyApellido()%></p>
				        <p>Fecha: <%=r.getFechaReserva()%></p>
				        <p>Libros:</p>
				        <% for (LibroReserva l : r.getLibros()) {%>
				        <p>- <%=l.getLibro().getTitulo()%></p>
				        <% }  %>
				      </div>
				      <div class="modal-footer">
				        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
				        <button type="submit" name="action-type" value="retirar" class="btn btn-primary">Retirar</button>
				      </div>
				    </div>
				  </div>
				</div>
	        </form>
	        <%
		    	} 
	    %>
				</div>
</div>
	<% if (listaReserva.isEmpty()) { %>
							<p style="font-size: 16px;">No hay resultados</p>
						<%} %>
</body>
</html>