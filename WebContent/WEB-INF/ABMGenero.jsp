<%@page import="java.util.ArrayList"%>
<%@page import="model.Genero"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>ABM Genero</title>
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
<div class="d-flex">
<%@ include file="/WEB-INF/Menu_Administrador.html"%>

	<div class="table">
		<div class="theader">
		    <div class="tr">
		        <span class="td">ID Genero</span>
		        <span class="td">Descripción</span>
		    </div>
	    </div>
	    <div class="tbody">
	    	<%
	    	@SuppressWarnings("unchecked")
	    	ArrayList<Genero> listaGenero=(ArrayList<Genero>)request.getAttribute("ListaGeneros");
	    	if (request.getParameter("editId") != null) {
		    	for (Genero g : listaGenero) {
		    		if (Integer.parseInt(request.getParameter("editId")) == g.getId()) {
	        %>
	        <form class="tr" action="ABMGeneroServlet" method="POST" name="ABMGenero">
	        	<input name="id" type="hidden" class="form-control" id="id" placeholder="id" value=<%=g.getId() %> required>
	        	<span class="td"><%=g.getId() %> </span>
	        	<span class="td">
	        		<input name="descripcion" type="text" class="form-control" id="descripcion" placeholder="descripcion" value=<%=g.getDescripcion() %> required>
		    	</span>
	        	<span class="td"><button type="submit" name="action-type" value="editar" class="btn btn-success btn-block" >Aceptar</button> </span>
	        	<span class="td"><a class="btn btn-danger btn-block" href="ABMGeneroServlet">Cancelar</a></span>
	        </form>
	        <%  	} 
	    			else {
	        %><form class="tr" action="ABMGeneroServlet" method="POST" name="ABMGenero">
	        	<input name="id" type="hidden" class="form-control" id="id" placeholder="id" value=<%=g.getId() %> required>
	        	<span class="td"><%=g.getId() %> </span>
	        	<span class="td"><%=g.getDescripcion() %> </span>
	        	<span class="td"><a class="btn btn-primary btn-block" href="ABMGeneroServlet?editId=<%=g.getId() %>">Editar</a> </span>
	        	<span class="td"><button type="submit" name="action-type" value="eliminar" class="btn btn-danger btn-block">Eliminar</button></span>
	        </form>
	        <%		} %>
	        <%	}
	    	}
	    	else {
	    		for (Genero g : listaGenero) {
	        %><form class="tr" action="ABMGeneroServlet" method="POST" name="ABMGenero">
	        	<input name="id" type="hidden" class="form-control" id="id" placeholder="id" value=<%=g.getId() %> required>
	        	<span class="td"><%=g.getId() %> </span>
	        	<span class="td"><%=g.getDescripcion() %> </span>
	        	<span class="td"><a class="btn btn-primary btn-block" href="ABMGeneroServlet?editId=<%=g.getId() %>">Editar</a> </span>
	        	<span class="td"><button type="submit" name="action-type" value="eliminar" class="btn btn-danger btn-block">Eliminar</button></span>
	        </form>
	        <%	} 
	    	}%>
	    </div>
	    <div class="tfoot">
		    	<form class="tr" action="ABMGeneroServlet" method="POST" name="ABMGenero">
		    		<span class="td"></span>
		    		<span class="td">
		    			<input name="descripcion" type="text" class="form-control" id="descripcion" placeholder="Descripción" required>
		    		</span>
		    		<span class="td">
		    			<button type="submit" name="action-type" value="agregar" class="btn btn-success btn-block" >Agregar</button>
		    		</span>
		    	</form>
	    </div>
	</div>
	<% if (listaGenero.isEmpty()) { %>
							<p style="font-size: 16px;">No hay resultados</p>
						<%} %>
 </div>						
</body>
</html>