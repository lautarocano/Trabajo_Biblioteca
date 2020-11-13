<%@page import="java.util.ArrayList"%>
<%@page import="model.Usuario"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>ABM Usuario</title>
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
	<div class="table">
		<div class="theader">
		    <div class="tr">
		        <span class="td">ID</span>
		        <span class="td">Tipo</span>
		        <span class="td">Nombre</span>
		        <span class="td">Contraseña</span>
		        <span class="td">Estado</span>
		    </div>
	    </div>
	    <div class="tbody">
	    	<%
	    	@SuppressWarnings("unchecked")
	    	ArrayList<Usuario> listaUsuario=(ArrayList<Usuario>)request.getAttribute("ListaUsuarios");
	    	if (request.getParameter("editId") != null) {
		    	for (Usuario u : listaUsuario) {
		    		if (Integer.parseInt(request.getParameter("editId")) == u.getId()) {
	        %>
	        <form class="tr" action="ABMUsuarioServlet" method="POST" name="ABMUsuario">
	        	<input name="id" type="hidden" class="form-control" id="id" placeholder="id" value=<%=u.getId() %> required>
	        	<span class="td"><%=u.getId() %> </span>
	        	<span class="td">
		    		<select name="tipo" class="form-control" id="tipo">
	        			<option value="0"> Socio </option>
	        			<option value="1"> Bibliotecario </option>
	        			<option value="2"> Administrador </option>
	        		</select>
		    	</span>
	        	<span class="td">
	        		<input name="user" type="text" class="form-control" id="user" placeholder="Nombre usuario" value=<%=u.getNombreUsuario() %> required>
		    	</span>
		    	<span class="td">
	        		<input name="password" type="text" class="form-control" id="password" placeholder="Contraseña" value=<%=u.getPassword() %> required>
		    	</span>
		    	<span class="td">
		    	<select name="estado" class="form-control" id="estado">
	        			<option value="true"> Habilitado </option>
	        			<option value="false"> Inhabilitado </option>
	        		</select>
		    	</span>
	        	<span class="td"><button type="submit" name="action-type" value="editar" class="btn btn-success btn-block" >Aceptar</button> </span>
	        	<span class="td"><a class="btn btn-danger btn-block" href="ABMUsuarioServlet">Cancelar</a></span>
	        </form>
	        <%  	} 
	    			else {
	        %><form class="tr" action="ABMUsuarioServlet" method="POST" name="ABMUsuario">
	        	<input name="id" type="hidden" class="form-control" id="id" placeholder="id" value=<%=u.getId() %> required>
	        	<span class="td"><%=u.getId() %> </span>
	        	<span class="td"><%=u.getTipo() %> </span>
	        	<span class="td"><%=u.getNombreUsuario() %> </span>
	        	<span class="td"><%=u.getPassword() %> </span>
	        	<span class="td"><%=u.getEstado() %> </span>
	        	<span class="td"><a class="btn btn-primary btn-block" href="ABMUsuarioServlet?editId=<%=u.getId() %>">Editar</a> </span>
	        	<span class="td"><button type="submit" name="action-type" value="eliminar" class="btn btn-danger btn-block">Eliminar</button></span>
	        </form>
	        <%		} 
	        	}
	    	}
	    	else {
	    		for (Usuario u : listaUsuario) {
	        %><form class="tr" action="ABMUsuarioServlet" method="POST" name="ABMUsuario">
	        	<input name="id" type="hidden" class="form-control" id="id" placeholder="id" value=<%=u.getId() %> required>
	        	<span class="td"><%=u.getId() %> </span>
	        	<span class="td"><%=u.getTipo() %> </span>
	        	<span class="td"><%=u.getNombreUsuario() %> </span>
	        	<span class="td"><%=u.getPassword() %> </span>
	        	<span class="td"><%=u.getEstado() %> </span>
	        	<span class="td"><a class="btn btn-primary btn-block" href="ABMUsuarioServlet?editId=<%=u.getId() %>">Editar</a> </span>
	        	<span class="td"><button type="submit" name="action-type" value="eliminar" class="btn btn-danger btn-block">Eliminar</button></span>
	        </form>
	        <%	} 
	    	}%>
	    </div>
	    <div class="tfoot">
		    	<form class="tr" action="ABMUsuarioServlet" method="POST" name="ABMUsuario">
		    		<span class="td"></span>
		    		<span class="td">
		    		<select name="tipo" class="form-control" id="tipo">
	        			<option value="0"> Socio </option>
	        			<option value="1"> Bibliotecario </option>
	        			<option value="2"> Administrador </option>
	        		</select>
		    	</span>
	        	<span class="td">
	        		<input name="user" type="text" class="form-control" id="user" placeholder="Nombre usuario" required>
		    	</span>
		    	<span class="td">
	        		<input name="password" type="text" class="form-control" id="password" placeholder="Contraseña" required>
		    	</span>
		    	<span class="td">
		    	<select name="estado" class="form-control" id="estado">
	        			<option value="true"> Habilitado </option>
	        			<option value="false"> Inhabilitado </option>
	        		</select>
		    	</span>
		    		<span class="td">
		    			<button type="submit" name="action-type" value="agregar" class="btn btn-success btn-block" >Agregar</button>
		    		</span>
		    	</form>
	    </div>
	</div>
	<% if (listaUsuario.isEmpty()) { %>
							<p style="font-size: 16px;">No hay resultados</p>
						<%} %>
</body>
</html>