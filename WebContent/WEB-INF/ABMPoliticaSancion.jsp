<%@page import="java.util.ArrayList"%>
<%@page import="model.PoliticaSancion"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>ABMPoliticaSancion</title>
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
	<div class="table">
		<div class="theader">
		    <div class="tr">
		        <span class="td">ID PoliticaSanción</span>
		        <span class="td">Dias atrasodesde</span>
		        <span class="td">Dias atrasohasta</span>
				 <span class="td">Dias sanción</span>	        
		    </div>
	    </div>
	    <div class="tbody">
	    	<%
	    	@SuppressWarnings("unchecked")
	    	ArrayList<PoliticaSancion> listaPoliticaSancion=(ArrayList<PoliticaSancion>)request.getAttribute("ListaPoliticasSanciones");
	    	if (request.getParameter("editId") != null) {
		    	for (PoliticaSancion ps : listaPoliticaSancion) {
		    		if (Integer.parseInt(request.getParameter("editId")) == ps.getId()) {
	        %>
	        <form class="tr" action="ABMPoliticaSancionServlet" method="POST" name="ABMPoliticaSancion">
	        	<input name="id" type="hidden" class="form-control" id="id" placeholder="id" value=<%=ps.getId() %> required>
	        	<span class="td"><%=ps.getId() %> </span>
	        	<input name="dias_atraso_desde" type="text" class="form-control" id="dias_atraso_desde" placeholder="Dias atraso desde" value="<%=ps.getDiasDeAtrasoDesde()%>" required>
	        	<input name="dias_atraso_hasta" type="text" class="form-control" id="dias_atraso_hasta" placeholder="Dias atraso hasta" value="<%=ps.getDiasDeAtrasoHasta() %>" required>
	        	<input name="dias_sancion" type="text" class="form-control" id="dias_sancion" placeholder="Dias de sanción" value="<%=ps.getDiasDeSancion() %>" required>
	        	<span class="td"><button type="submit" name="action-type" value="editar" class="btn btn-success btn-block" >Aceptar</button> </span>
	        	<span class="td"><a class="btn btn-danger btn-block" href="ABMPoliticaSancionServlet">Cancelar</a></span>
	        </form>
	        <%  	} 
	    			else {
	        %><form class="tr" action="ABMPoliticaSancionServlet" method="POST" name="ABMPoliticaSancion">
	        	<input name="id" type="hidden" class="form-control" id="id" placeholder="id" value="<%=ps.getId()%>" required>
	        	<span class="td"><%=ps.getId() %> </span>
	        	<span class="td"><%=ps.getDiasDeAtrasoDesde() %> </span>
	        	<span class="td"><%=ps.getDiasDeAtrasoHasta() %> </span>
	        	<span class="td"><%=ps.getDiasDeSancion() %> </span>
	        
	        	<span class="td"><a class="btn btn-primary btn-block" href="ABMPoliticaSancionServlet?editId=<%=ps.getId() %>">Editar</a> </span>
	        	<span class="td"><button type="submit" name="action-type" value="eliminar" class="btn btn-danger btn-block">Eliminar</button></span>
	        </form>
	        <%		} %>
	        <%	}
	    	}
	    	else {
	    		for (PoliticaSancion ps : listaPoliticaSancion) {
	        %><form class="tr" action="ABMPoliticaSancionServlet" method="POST" name="ABMPoliticaSancion">
	        	<input name="id" type="hidden" class="form-control" id="id" placeholder="id" value=<%=ps.getId() %> required>
				<span class="td"><%=ps.getId() %> </span>
	        	<span class="td"><%=ps.getDiasDeAtrasoDesde() %> </span>
	        	<span class="td"><%=ps.getDiasDeAtrasoHasta() %> </span>
	        	<span class="td"><%=ps.getDiasDeSancion() %> </span>
	        	<span class="td"><a class="btn btn-primary btn-block" href="ABMPoliticaSancionServlet?editId=<%=ps.getId() %>">Editar</a> </span>
	        	<span class="td"><button type="submit" name="action-type" value="eliminar" class="btn btn-danger btn-block">Eliminar</button></span>
	        </form>
	        <%	} 
	    	}%>
	    </div>
	    <div class="tfoot">
		    	<form class="tr" action="ABMPoliticaSancionServlet" method="POST" name="ABMPoliticaSancion">
		    		<span class="td"></span>
		    		<span class="td">
			    	<input name="dias_atraso_desde" type="text" class="form-control" id="dias_atraso_desde" placeholder="Dias atraso desde" required>
		        	</span>
		        	<span class="td">
		        	<input name="dias_atraso_hasta" type="text" class="form-control" id="dias_atraso_hasta" placeholder="Dias atraso hasta" required>
		        	</span>
		        	<span class="td">
		        	<input name="dias_sancion" type="text" class="form-control" id="dias_sancion" placeholder="Dias de sanción"  required>
			    	</span>
		    		<span class="td">
		    			<button type="submit" name="action-type" value="agregar" class="btn btn-success btn-block" >Agregar</button>
		    		</span>
		    	</form>
	    </div>
	</div>
	<% if (listaPoliticaSancion.isEmpty()) { %>
							<p style="font-size: 16px;">No hay resultados</p>
						<%} %>
</div>
</body>
</html>