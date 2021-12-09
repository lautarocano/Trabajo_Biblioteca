package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.EjemplarLogic;
import logic.LibroLogic;
import model.Ejemplar;
import model.Libro;
import model.Usuario;

/**
 * Servlet implementation class ABMEjemplarServlet
 */
@WebServlet("/ABMEjemplarServlet")
public class ABMEjemplarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ABMEjemplarServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (Servlet.VerificarSesionYUsuario(request, response, Usuario.tipoUsuario.Administrador)) {
			if (request.getParameter("IdLibro") != null) {
				LibroLogic ll = new LibroLogic();
				EjemplarLogic el = new EjemplarLogic();
				Libro libro = null;
				try {
					libro = ll.getOne(Integer.parseInt(request.getParameter("IdLibro")));
				} catch (NumberFormatException e) {
					request.setAttribute("mensaje", "No se pudo realizar la operación debido a un error en los datos suministrados");
				} catch (SQLException e) {
        			Servlet.log(Level.SEVERE,e, request);
					request.setAttribute("mensaje", "No se pudieron obtener los ejemplares");
				} catch (Exception e) {
					Servlet.log(Level.SEVERE,e, request);
					request.setAttribute("mensaje", "Ha ocurrido un error durante la ejecución de la operación");
				}
				if (libro != null) {
					request.setAttribute("libro", libro);
					try {
						request.setAttribute("ListaEjemplares", el.getAllByLibro(libro.getId()));
					} catch (SQLException e) {
	        			Servlet.log(Level.SEVERE,e, request);
						request.setAttribute("mensaje", "No se pudieron obtener los ejemplares");
					} catch (Exception e) {
						Servlet.log(Level.SEVERE,e, request);
						request.setAttribute("mensaje", "Ha ocurrido un error durante la ejecución de la operación");
					}
					request.setAttribute("JSP", "ABMEjemplar");
					request.getRequestDispatcher("WEB-INF/Administrador.jsp").forward(request, response);
				}
				else {
					request.setAttribute("clase-mensaje", "class=\"alert alert-danger alert-dismissible fade show\"");
					request.setAttribute("mensaje", "Id de libro inválida");
					request.getRequestDispatcher("ABMLibroServlet").forward(request, response);
				}
			} else {
				request.getRequestDispatcher("ABMLibroServlet").forward(request, response);
			}
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
					Ejemplar ejemplar = new Ejemplar();
					LibroLogic ll = new LibroLogic();
					EjemplarLogic el = new EjemplarLogic();
					try {
						int idLibro = (Integer.parseInt(request.getParameter("IdLibro")));
						ejemplar.setLibro(ll.getOne(idLibro));
						if (ejemplar.getLibro() == null) {
							request.setAttribute("mensaje", "Id de libro inválida");
						}
						else {
							el.insert(ejemplar);
							request.setAttribute("clase-mensaje", "class=\"alert alert-success alert-dismissible fade show\"");
							request.setAttribute("mensaje", "Ejemplar agregado correctamente");
						}
					} catch (NumberFormatException e) {
						request.setAttribute("mensaje", "No se pudo realizar la operación debido a un error en los datos suministrados");
					} catch (SQLException e) {
	        			Servlet.log(Level.SEVERE,e, request);
						request.setAttribute("mensaje", "No se pudo agregar el ejemplar");
					}
				}
				else if (request.getParameter("action-type").equals("eliminar")) {	
					Ejemplar ejemplar = null;
					EjemplarLogic el = new EjemplarLogic();
					try {
						ejemplar = el.getOne(Integer.parseInt(request.getParameter("id")));
						if (ejemplar != null){
							el.delete(ejemplar);
							request.setAttribute("clase-mensaje", "class=\"alert alert-success alert-dismissible fade show\"");
							request.setAttribute("mensaje", "Ejemplar eliminado correctamente");
						}
						else {
							request.setAttribute("mensaje", "Id de ejemplar inválida");
						}
					} catch (NumberFormatException e) {
						request.setAttribute("mensaje", "No se pudo realizar la operación debido a un error en los datos suministrados");
					} catch (SQLException e) {
	        			Servlet.log(Level.SEVERE,e, request);
						request.setAttribute("mensaje", "No se pudo eliminar el ejemplar");
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

}