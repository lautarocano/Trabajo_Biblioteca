<%@page import="java.util.ArrayList"%>
<%@page import="model.Socio"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>ABM Socio</title>
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
		        <span class="td">ID</span>
		        <span class="td">Nombre</span>
		        <span class="td">Apellido</span>
		        <span class="td">DNI</span>
		        <span class="td">Email</span>
		        <span class="td">Domicilio</span>
		        <span class="td">Teléfono</span>
		        <span class="td">Estado</span>
		    </div>
	    </div>
	    <div class="tbody">
	    	<%
	    	@SuppressWarnings("unchecked")
	    	ArrayList<Socio> listaSocio = (ArrayList<Socio>)request.getAttribute("ListaSocios");
	    	if (request.getParameter("editId") != null) {
		    	for (Socio s : listaSocio) {
		    		if (Integer.parseInt(request.getParameter("editId")) == s.getId()) {
	        %>
	        <form class="tr" action="ABMSocioServlet" method="POST" name="ABMSocio">
	        	<input name="id" type="hidden" class="form-control" id="id" placeholder="id" value=<%=s.getId() %> required>
	        	<span class="td"><%=s.getId() %> </span>
	        	<span class="td">
	        		<input name="nombre" type="text" class="form-control" id="nombre" placeholder="Nombre" value=<%=s.getNombre() %> required>
		    	</span>
		    	<span class="td">
	        		<input name="apellido" type="text" class="form-control" id="apellido" placeholder="Apellido" value=<%=s.getApellido() %> required>
		    	</span>
		    	<span class="td">
	        		<input name="dni" type="number" class="form-control" id="dni" placeholder="dni" value=<%=s.getDni() %> required>
		    	</span>
		    	<span class="td">
	        		<input name="email" type="email" class="form-control" id="email" placeholder="Email" value=<%=s.getEmail() %> required>
		    	</span>
		    	<span class="td">
	        		<input name="domicilio" type="text" class="form-control" id="domicilio" placeholder="Domicilio" value=<%=s.getDomicilio() %> required>
		    	</span>
		    	<span class="td">
	        		<input name="telefono" type="text" class="form-control" id="telefono" placeholder="telefono" value=<%=s.getTelefono() %> required>
		    	</span>
		    	<span class="td">
	        		<input name="estado" type="checkbox" class="form-control" id="estado" placeholder="Estado" <% if (s.getEstado()) { %> checked <%}%> value="True">
		    	</span>
	        	<span class="td"><button type="submit" name="action-type" value="editar" class="btn btn-success btn-block" >Aceptar</button> </span>
	        	<span class="td"><a class="btn btn-danger btn-block" href="ABMSocioervlet">Cancelar</a></span>
	        </form>
	        <%  	} 
	    			else {
	        %><form class="tr" action="ABMSocioServlet" method="POST" name="ABMSocio">
	        	<input name="id" type="hidden" class="form-control" id="id" placeholder="id" value=<%=s.getId() %> required>
	        	<span class="td"><%=s.getId() %> </span>
	        	<span class="td"><%=s.getNombre() %> </span>
	        	<span class="td"><%=s.getApellido() %> </span>
	        	<span class="td"><%=s.getDni() %> </span>
	        	<span class="td"><%=s.getEmail() %> </span>
	        	<span class="td"><%=s.getDomicilio() %> </span>
	        	<span class="td"><%=s.getTelefono() %> </span>
	        	<span class="td"><%=s.getEstado() %> </span>
	        	<span class="td"><a class="btn btn-primary btn-block" href="ABMSocioServlet?editId=<%=s.getId() %>">Editar</a> </span>
	        	<span class="td"><button type="submit" name="action-type" value="eliminar" class="btn btn-danger btn-block">Eliminar</button></span>
	        </form>
	        <%		} %>
	        <%	}
	    	}
	    	else {
	    		for (Socio s : listaSocio) {
	        %><form class="tr" action="ABMSocioServlet" method="POST" name="ABMSocio">
	        	<input name="id" type="hidden" class="form-control" id="id" placeholder="id" value=<%=s.getId() %> required>
	        	<span class="td"><%=s.getId() %> </span>
	        	<span class="td"><%=s.getNombre() %> </span>
	        	<span class="td"><%=s.getApellido() %> </span>
	        	<span class="td"><%=s.getDni() %> </span>
	        	<span class="td"><%=s.getEmail() %> </span>
	        	<span class="td"><%=s.getDomicilio() %> </span>
	        	<span class="td"><%=s.getTelefono() %> </span>
	        	<span class="td"><%=s.getEstado() %> </span>
	        	<span class="td"><a class="btn btn-primary btn-block" href="ABMSocioServlet?editId=<%=s.getId() %>">Editar</a> </span>
	        	<span class="td"><button type="submit" name="action-type" value="eliminar" class="btn btn-danger btn-block">Eliminar</button></span>
	        </form>
	        <%	} 
	    	}%>
	    </div>
	    <div class="tfoot">
		    	<form class="tr" action="ABMSocioServlet" method="POST" name="ABMSocio">
		    		<span class="td"></span>
		    		<span class="td">
		        		<input name="nombre" type="text" class="form-control" id="nombre" placeholder="Nombre" required>
			    	</span>
			    	<span class="td">
		        		<input name="apellido" type="text" class="form-control" id="apellido" placeholder="Apellido" required>
			    	</span>
			    	<span class="td">
		        		<input name="dni" type="number" class="form-control" id="dni" placeholder="dni" required>
			    	</span>
			    	<span class="td" class="form-control">
		        		<input name="email" type="email" class="form-control" id="email" placeholder="Email" required>
			    	</span>
			    	<span class="td">
		        		<input name="domicilio" type="text" class="form-control" id="domicilio" placeholder="Domicilio" required>
			    	</span>
			    	<span class="td">
		        		<input name="telefono" type="text" class="form-control" id="telefono" placeholder="telefono" required>
			    	</span>
			    	<span class="td">
		        		<input name="estado" type="checkbox" class="form-control" id="estado" placeholder="Estado" value="True">
			    	</span>
		    		<span class="td">
		    			<button type="submit" name="action-type" value="agregar" class="btn btn-success btn-block" >Agregar</button>
		    		</span>
		    	</form>
	    </div>
	</div>
	<% if (listaSocio.isEmpty()) { %>
							<p style="font-size: 16px;">No hay resultados</p>
						<%} %>
</div>
</body>
</html>