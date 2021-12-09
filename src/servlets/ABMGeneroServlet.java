package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.GeneroLogic;
import model.Genero;
import model.Usuario;

/**
 * Servlet implementation class ABMGeneroServlet
 */
@WebServlet("/ABMGeneroServlet")
public class ABMGeneroServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ABMGeneroServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (Servlet.VerificarSesionYUsuario(request, response, Usuario.tipoUsuario.Administrador)) {
			GeneroLogic gl = new GeneroLogic();
			try {
				request.setAttribute("ListaGeneros", gl.getAll());
			} catch (SQLException e) {
    			Servlet.log(Level.SEVERE,e, request);
				request.setAttribute("mensaje", "No se pudieron obtener los generos");
			} catch (Exception e) {
				Servlet.log(Level.SEVERE,e, request);
				request.setAttribute("mensaje", "Ha ocurrido un error durante la ejecución de la operación");
			}
			request.setAttribute("JSP", "ABMGenero");
			request.getRequestDispatcher("WEB-INF/Administrador.jsp").forward(request, response);		
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (Servlet.VerificarSesionYUsuario(request, response, Usuario.tipoUsuario.Administrador)) {
			try {
			if (request.getParameter("action-type")!=null) {
				if (request.getParameter("action-type").equals("agregar")) {	
					if (ValidarDatos(request)) {
						Genero genero=new Genero();
						genero.setDescripcion(request.getParameter("descripcion"));
						GeneroLogic gl=new GeneroLogic();
						try {
							gl.insert(genero);
							request.setAttribute("clase-mensaje", "class=\"alert alert-success alert-dismissible fade show\"");
							request.setAttribute("mensaje", "Género agregado correctamente");
						} catch (SQLException e) {
		        			Servlet.log(Level.SEVERE,e, request);
							request.setAttribute("mensaje", "No se pudo agregar un género");
						}
					}
				}
				else if (request.getParameter("action-type").equals("eliminar")) {	
					Genero genero=null;
					GeneroLogic gl=new GeneroLogic();
					try {
						genero = gl.getOne(Integer.parseInt(request.getParameter("id")));
						if (genero != null) {
						gl.delete(genero);
						request.setAttribute("clase-mensaje", "class=\"alert alert-success alert-dismissible fade show\"");
						request.setAttribute("mensaje", "Género eliminado correctamente");
						}
						else {
							request.setAttribute("clase-mensaje", "class=\"alert alert-danger alert-dismissible fade show\"");
							request.setAttribute("mensaje", "Id de género invalida");
						}
					} catch (NumberFormatException e) {
						request.setAttribute("clase-mensaje", "class=\"alert alert-danger alert-dismissible fade show\"");
						request.setAttribute("mensaje", "No se pudo realizar la operación debido a un error en los datos suministrados");
					} catch (SQLException e) {
	        			Servlet.log(Level.SEVERE,e, request);
						request.setAttribute("mensaje", "No se pudo eliminar un género");
					}
				}
				else if (request.getParameter("action-type").equals("editar")) {	
					if (ValidarDatos(request)) {
						GeneroLogic gl = new GeneroLogic();
						Genero genero;
						try {
							genero = gl.getOne(Integer.parseInt(request.getParameter("id")));
							if (genero != null) {
								genero.setId(Integer.parseInt(request.getParameter("id")));
								genero.setDescripcion(request.getParameter("descripcion"));
								gl.update(genero);
								request.setAttribute("clase-mensaje", "class=\"alert alert-success alert-dismissible fade show\"");
								request.setAttribute("mensaje", "Género actualizado correctamente");
							}
							else {
								request.setAttribute("clase-mensaje", "class=\"alert alert-danger alert-dismissible fade show\"");
								request.setAttribute("mensaje", "Id de género inválida");
							}
						} catch (NumberFormatException e) {
							request.setAttribute("clase-mensaje", "class=\"alert alert-danger alert-dismissible fade show\"");
							request.setAttribute("mensaje", "No se pudo realizar la operación debido a un error en los datos suministrados");
						} catch (SQLException e) {
		        			Servlet.log(Level.SEVERE,e, request);
							request.setAttribute("mensaje", "No se pudo actualizar el genero");
					 	}
					}
				} else {
					request.setAttribute("mensaje", "Error en los datos suministrados");
				}
			}
			} catch (Exception e) {
				Servlet.log(Level.SEVERE,e, request);
				request.setAttribute("mensaje", "Ha ocurrido un error durante la ejecución de la operación");
			}
			this.doGet(request, response);
		}		
	}
	
	private static Boolean ValidarDatos (HttpServletRequest request) {
		if (Servlet.parameterNotNullOrBlank(request.getParameter("descripcion"))) {
			return true;
		}
		else {
			request.setAttribute("mensaje", "Campos incompletos.");
			return false;
		}
	}

}