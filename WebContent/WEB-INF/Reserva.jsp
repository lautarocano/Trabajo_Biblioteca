<%@page import="java.util.ArrayList"%>
<%@page import="model.Libro"%>
<%@page import="model.Genero"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Realizar Reservas</title>
</head>
<body>
<%
@SuppressWarnings("unchecked")
ArrayList<Libro> listaLibro=(ArrayList<Libro>)request.getAttribute("ListaLibros");
@SuppressWarnings("unchecked")
ArrayList<Genero> listaGenero=(ArrayList<Genero>)request.getAttribute("ListaGeneros");
String busqueda = "";
String andBusqueda = "";
if (request.getParameter("libro")!=null) {
	busqueda = "?libro="+request.getParameter("libro");
	andBusqueda = "&libro="+request.getParameter("libro");
}
%>
<div class="row">
	
	<div class="col-lg-9">
	
		<div class="border p-4 mb-4">
		<form action="ReservaServlet" method="GET" name="ReservaLibro">
			<%if (request.getParameter("genero")!=null) { %>
			<input name="genero" type="hidden" class="form-control" id="genero" value="<%=request.getParameter("genero") %>"  required>
			<%} %>
			<div class="input-group">
			<%if (request.getParameter("libro")!=null) { %>
			  <input name="libro" type="search" class="form-control rounded" placeholder="Búsqueda" aria-label="Search"
			    aria-describedby="search-addon" value="<%=request.getParameter("libro") %>"/>
			  <button type="submit" class="btn btn-outline-primary">Buscar</button>
			  <%if (request.getParameter("genero")!=null) { %>
			  <a href="ReservaServlet?genero=<%=request.getParameter("genero") %>" class="btn btn-danger">X</a>
			  <%} else {%>
			  <a href="ReservaServlet" class="btn btn-danger">X</a>
			  <%} %>
			<%} else {%>
			  <input name="libro" type="search" class="form-control rounded" placeholder="Búsqueda" aria-label="Search"
			    aria-describedby="search-addon" />
			  <button type="submit" class="btn btn-outline-primary">Buscar</button>
			<%} %>
			</div>
		</form>
		</div>
		
		<div class="row">
			<%for (Libro l : listaLibro) {%>
	          <div class="col-lg-4 col-md-6 mb-4">
	            <div class="card h-100">
	              <a href="#"><img class="card-img-top" src="ImagesServlet?id=<%=l.getId()%>" alt=""></a>
	              <div class="card-body">
	                <h4 class="card-title">
	                  <a href="#"><%=l.getTitulo() %></a>
	                </h4>
	                <h5><%=l.getAutor() %></h5>
	                <p class="card-text"><%="Edición N°"+l.getNroEdicion() %></p>
	              </div>
	              <div class="card-footer">
	                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modal<%=l.getId()%>">
					  Ver Libro
					</button>
	              </div>
	            </div>
	          </div>
	          <!-- Modal -->
				<div class="modal fade" id="modal<%=l.getId()%>" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel<%=l.getId()%>" aria-hidden="true">
				  <div class="modal-dialog" role="document">
				    <div class="modal-content">
				    <div class="modal-header">
				        <h5 class="modal-title" id="exampleModalLabel<%=l.getId()%>"><%=l.getTitulo()%></h5>
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
				          <span aria-hidden="true">&times;</span>
				        </button>
				      </div>
					    <div class="container-fluid">
						    <div class="row">
						      <div class="col-md-6">
						      	<img class="card-img-top" src="ImagesServlet?id=<%=l.getId()%>" alt="">
						      </div>
						      <div class="col">
						      	<p>Autor: <%=l.getAutor() %></p>
						      	<p>Nro de edición: <%=l.getNroEdicion() %> </p>
						      	<p>Fecha de edición: <%=l.getFechaEdicion() %> </p>
						      	<p>Género: <%=l.getGenero().getDescripcion() %></p>
						      </div>
						    </div>
						    <div class="row">
						      <p>Sinopsis:</p>
						      <p> </p>
						    </div>
						</div>
				      <div class="modal-footer">
				        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
				        <form action="ReservaServlet" method="POST" name="ReservaLibro">
				        <input name="id_libro" type="hidden" class="form-control" id="id_libro" placeholder="id_libro" value="<%=l.getId() %>"  required>
				        <button type="submit" name="action-type" value="reservar" class="btn btn-primary">Agregar a reserva</button>
				        </form>
				      </div>
				    </div>
				  </div>
				</div>
				<!-- Modal -->
	         <%} %>
		</div>
		       <!-- /.row -->
		
	</div>
	<!-- /.col-lg-9 -->

	<div class="col">
	
	  <h3 class="my-4">Filtrar:</h3>
	  <div class="list-group">
	  <%if (request.getParameter("genero")!=null) { %>
	    <a href="ReservaServlet<%=busqueda %>" class="list-group-item">Todos</a>
	    <%for (Genero gen : listaGenero) {
	    	if (Integer.parseInt(request.getParameter("genero"))==gen.getId()) {%>
	    <a href=# class="list-group-item active"><%=gen.getDescripcion() %></a>
	    	<%}
	    	else {%>
	    <a href="ReservaServlet?genero=<%=gen.getId()+andBusqueda %>" class="list-group-item"><%=gen.getDescripcion() %></a>
	    	<%}
	    	}%>
	  </div>
	  <%}
	  	else {%>
	  	<a href="#" class="list-group-item active">Todos</a>
	    <%for (Genero gen : listaGenero) { %>
	    <a href="ReservaServlet?genero=<%=gen.getId()+andBusqueda %>" class="list-group-item"><%=gen.getDescripcion() %></a>
	    <%}%>
	  <%} %>
	
	</div>
	
</div>
<!-- /.row -->
</body>
</html>