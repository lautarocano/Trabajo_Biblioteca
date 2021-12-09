<%@page import="java.util.ArrayList"%>
<%@page import="model.Libro"%>
<%@page import="model.Genero"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>ABM Libro</title>
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
  
</head>
<body>
<div class="d-flex">
<div class="table">
		<div class="theader">
		    <div class="tr">
		        <span class="td">ID</span>
		        <span class="td">Título</span>
		        <span class="td">Autor</span>
		        <span class="td">Nro de Edición</span>
		        <span class="td">Fecha de Edición</span>
		        <span class="td">Genero</span>
		        <span class="td">Ejemplares</span>
		        <span class="td"></span>
		        <span class="td"></span>
		        <span class="td"></span>
		    </div>
	    </div>
	    <div class="tbody">
	    	<%
	    	@SuppressWarnings("unchecked")
	    	ArrayList<Libro> listaLibro=(ArrayList<Libro>)request.getAttribute("ListaLibros");
	    	@SuppressWarnings("unchecked")
	    	ArrayList<Genero> listaGenero=(ArrayList<Genero>)request.getAttribute("ListaGeneros");
	    	if (listaLibro == null || listaLibro.isEmpty()) { %>
	    	<p style="font-size: 16px;">No hay resultados</p>
	    	<%}
	    		else if (request.getParameter("editId") != null) {
		    	for (Libro l : listaLibro) {
		    		if (Integer.parseInt(request.getParameter("editId")) == l.getId()) {
	        %>
	        <form class="tr" action="ABMLibroServlet" method="POST" name="ABMLibro">
	        	<input name="id" type="hidden" class="form-control" id="id" placeholder="id" value="<%=l.getId() %>" required>
	        	<span class="td"><%=l.getId() %> </span>
	        	<span class="td">
	        		<input name="titulo" type="text" class="form-control" id="titulo" placeholder="Título" value="<%=l.getTitulo() %>" required>
		    	</span>
	        	<span class="td">
	        		<input name="autor" type="text" class="form-control" id="autor" placeholder="Autor" value="<%=l.getAutor() %>" required>
		    	</span>
	        	<span class="td">
	        		<input name="numero-edicion" type="number" class="form-control" id="numero-edicion" placeholder="N° de edición" value="<%=l.getNroEdicion() %>" required>
		    	</span>
	        	<span class="td">
	        		<input name="fecha-edicion" type="text" class="form-control" id="datepicker" placeholder="Fecha edición" onkeydown="return false" required>
	        		<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
					<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
					<script>
					$( function() {
					  $( "#datepicker" ).datepicker({maxDate: -1,changeMonth: true,
					      changeYear: true});
					  $( "#datepicker" ).datepicker( "option", "dateFormat","yy-mm-dd");
					  $( "#datepicker" ).datepicker( "setDate", "<%=l.getFechaEdicion().toString()%>" );
					} );
					</script>
		    	</span>
		    	<span class="td">
		    		<select name="genero" class="form-control" id="genero">
	        		<%if (listaGenero != null) {
		        		for (Genero g : listaGenero) {
		        			if (g.getId() == l.getGenero().getId()) {%>
		        			<option value="<%=g.getId() %>" selected> <%=g.getDescripcion() %> </option>
		        		<%	}
		        			else {%>
		        			<option value="<%=g.getId() %>" > <%=g.getDescripcion() %> </option>
		        		<%	}
		        		}
		        	}%>
	        		</select>
		    	</span>
		    	<span class="td">
	        		<a href="ABMEjemplarServlet?IdLibro=<%=l.getId() %>">Ver ejemplares</a>
		    	</span>
	        	<span class="td"><button type="submit" name="action-type" value="editar" class="btn btn-success btn-block" >Aceptar</button> </span>
	        	<span class="td"><a class="btn btn-danger btn-block" href="ABMLibroServlet">Cancelar</a></span>
	        </form>
	        <%  	} 
	    			else {
	        %><form class="tr" action="ABMLibroServlet" method="POST" name="ABMLibro">
	        	<input name="id" type="hidden" class="form-control" id="id" placeholder="id" value=<%=l.getId() %> required>
	        	<span class="td"><%=l.getId() %> </span>
	        	<span class="td"><%=l.getTitulo() %> </span>
	        	<span class="td"><%=l.getAutor() %> </span>
	        	<span class="td"><%=l.getNroEdicion() %> </span>
	        	<span class="td"><%=l.getFechaEdicion() %> </span>
	        	<span class="td"><%=l.getGenero().getDescripcion() %> </span>
	        	<span class="td"><a href="ABMEjemplarServlet?IdLibro=<%=l.getId() %>">Ver ejemplares</a></span>
	        	<span class="td"><a class="btn btn-primary btn-block" href="ABMLibroServlet?editId=<%=l.getId() %>">Editar</a> </span>
	        	<span class="td"><button type="submit" name="action-type" value="eliminar" class="btn btn-danger btn-block">Eliminar</button></span>
	        </form>
	        <%		} %>
	        <%	}
	    	}
	    	else {
	    		for (Libro l : listaLibro) {
	        %><form class="tr" action="ABMLibroServlet" method="POST" name="ABMLibro">
	        	<input name="id" type="hidden" class="form-control" id="id" placeholder="id" value="<%=l.getId() %>" required>
	        	<span class="td"><%=l.getId() %> </span>
	        	<span class="td"><%=l.getTitulo() %> </span>
	        	<span class="td"><%=l.getAutor() %> </span>
	        	<span class="td"><%=l.getNroEdicion() %> </span>
	        	<span class="td"><%=l.getFechaEdicion() %> </span>
	        	<span class="td"><%=l.getGenero().getDescripcion() %> </span>
	        	<span class="td"><a href="ABMEjemplarServlet?IdLibro=<%=l.getId() %>">Ver ejemplares</a></span>
	        	<span class="td">
	        		<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modal<%=l.getId()%>">
					  Imagen
					</button>
		    	</span>
		    	<input name="image" type="hidden" class="form-control" id="image" value="<%=l.getId()+".jpg" %>"  required>	    	
	        	<span class="td"><a class="btn btn-primary btn-block" href="ABMLibroServlet?editId=<%=l.getId() %>">Editar</a> </span>
	        	<span class="td"><button type="submit" name="action-type" value="eliminar" class="btn btn-danger btn-block">Eliminar</button></span>
	        </form>
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
				    <div class="modal-body">
					  <img class="card-img-top" src="ImagesServlet?id=<%=l.getId()%>" alt="">
					</div>
				      <div class="modal-footer">
				        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
				        <form action="ImagesServlet" method="POST" name="DeleteImage">
				        <input name="id" type="hidden" class="form-control" id="id" value="<%=l.getId() %>"  required>
				        <input name="image" type="hidden" class="form-control" id="image" value="<%=l.getId()+".jpg" %>"  required>
				        <button type="submit" name="action" value="eliminar" class="btn btn-primary">Eliminar imagen</button>
				        </form>
				        <form action="ImagesServlet" method="POST" enctype="multipart/form-data" name="UploadImage">
				        <input name="uuid" type="hidden" class="form-control" id="uuid" value="<%=l.getId() %>"  required>
				        <input name="image" type="file" class="form-control" id="image" required>
				        <button type="submit" name="action" value="agregar" class="btn btn-primary">Agregar imagen</button>
				        </form>
				      </div>
				    </div>
				  </div>
				</div>
				<!-- Modal -->
	        <%	} 
	    	}%>
	    </div>
	    <div class="tfoot">
		    	<form class="tr" action="ABMLibroServlet" method="POST" name="ABMLibro">
		    		<span class="td"></span>
		    		<span class="td">
	        			<input name="titulo" type="text" class="form-control" id="titulo" placeholder="Título" required>
		    		</span>
		    		<span class="td">
	        			<input name="autor" type="text" class="form-control" id="autor" placeholder="Autor" required>
		    		</span>
		    		<span class="td">
	        			<input name="numero-edicion" type="number" class="form-control" id="numero-edicion" placeholder="N° de edición" required>
		    		</span>
		    		<span class="td">
	        			<input name="fecha-edicion" type="text" class="form-control" id="datepicker2" placeholder="Fecha edición" onkeydown="return false" required>
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
		    			<select name="genero" class="form-control" id="genero">
	        			<% if (listaGenero != null) {
		        			for (Genero g : listaGenero) {%>
		        				<option value="<%=g.getId() %>" > <%=g.getDescripcion() %> </option>
		        			<%}
	        			}%>
	        			</select>
		    		</span>
		    		<span class="td">
	        			<input name="cant-ejemplares" type="number" class="form-control" id="cant-ejemplares" placeholder="Cantidad de Ejemp." required>
		    		</span>
		    		<span class="td">
		    			<button type="submit" name="action-type" value="agregar" class="btn btn-success btn-block" >Agregar</button>
		    		</span>
		    	</form>
	    </div>
</div>
</div>

</body>
</html>