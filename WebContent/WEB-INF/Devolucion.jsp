<%@page import="java.util.ArrayList"%>
<%@page import="model.Prestamo"%>
<%@page import="model.LineaDePrestamo"%>
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
<form action="DevolucionServlet">
<span>ID de socio:</span><input name="id-socio" type="number" class="form-control" id="id-socio" placeholder="id">
</form>
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
	    	if (listaPrestamo == null || listaPrestamo.isEmpty()) { %>
	    	<p style="font-size: 16px;">No hay resultados</p>
	    	<%}
	    	else {
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
	        	<span class="td">
		        	<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modal<%=p.getId()%>">
					  Ver Préstamo
					</button>
				</span>
				<!-- Modal -->
				<div class="modal fade" id="modal<%=p.getId()%>" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel<%=p.getId()%>" aria-hidden="true">
				  <div class="modal-dialog" role="document">
				    <div class="modal-content">
				      <div class="modal-header">
				        <h5 class="modal-title" id="exampleModalLabel<%=p.getId()%>">Préstamo N° <%=p.getId()%></h5>
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
				          <span aria-hidden="true">&times;</span>
				        </button>
				      </div>
				      <div class="modal-body">
				        <p>Socio: <%=p.getSocio().getNombreyApellido()%></p>
				        <p>Fecha: <%=p.getFechaPrestamo()%></p>
				        <p>Libros:</p>
				        <% for (LineaDePrestamo l : p.getLineasPrestamo()) { %>
				        <p>- <%=l.getEjemplar().getLibro().getTitulo() + " - " + l.getEjemplar().getLibro().getAutor() %></p>
				        <% }  %>
				      </div>
				      <div class="modal-footer">
				        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
				        <button type="submit" class="btn btn-primary" name="action-type" value="devolver">Realizar devolución</button>
				      </div>
				    </div>
				  </div>
				</div>
	        </form>
	        <%
	          	
		    	} 
	    	}
	    %>
	    
				</div>
</div>
</body>
</html>