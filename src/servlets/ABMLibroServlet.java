package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.GeneroLogic;
import logic.LibroLogic;
import model.Libro;
import model.Usuario;

/**
 * Servlet implementation class ABMLibroServlet
 */
@WebServlet("/ABMLibroServlet")
public class ABMLibroServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ABMLibroServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (Servlet.VerificarSesionYUsuario(request, response, Usuario.tipoUsuario.Administrador)) {
			LibroLogic ll = new LibroLogic();
			GeneroLogic gl = new GeneroLogic();
			try {
				request.setAttribute("ListaLibros", ll.getAll());
				request.setAttribute("ListaGeneros", gl.getAll());
			} catch (SQLException e) {
				request.setAttribute("mensaje", "No se pudieron obtener los libros");
			}
			request.setAttribute("JSP", "ABMLibro");
			request.getRequestDispatcher("WEB-INF/Administrador.jsp").forward(request, response);		
			}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (Servlet.VerificarSesionYUsuario(request, response, Usuario.tipoUsuario.Administrador)) {
			if (request.getParameter("action-type").equals("agregar")) {	
				if (ValidarDatos(request)) {

				Libro libro=new Libro();
				libro.setTitulo(request.getParameter("titulo"));
				libro.setAutor(request.getParameter("autor"));
				libro.setFechaEdicion(LocalDate.parse(request.getParameter("fecha-edicion")));
				libro.setNroEdicion(request.getParameter("numero-edicion"));
				libro.setCantEjemplares(Integer.parseInt(request.getParameter("cant-ejemplares")));
				int idGenero = (Integer.parseInt(request.getParameter("genero")));
				GeneroLogic gl = new GeneroLogic();
				try {
					libro.setGenero(gl.getOne(idGenero));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					request.setAttribute("mensaje", "No se pudo encontrar el genero indicado");
				}
				LibroLogic ll = new LibroLogic();
				try {
					ll.insert(libro);
					request.setAttribute("clase-mensaje", "class=\"alert alert-success alert-dismissible fade show\"");
					request.setAttribute("mensaje", "Libro agregado correctamente");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					request.setAttribute("mensaje", "No se pudo agregar un libro");
				}

				}
			}
			else if (request.getParameter("action-type").equals("eliminar")) {	
				Libro libro=null;
				LibroLogic ll=new LibroLogic();
				try {
					libro = ll.getOne(Integer.parseInt(request.getParameter("id")));
					if(libro!=null) {
						ll.delete(libro);
						request.setAttribute("clase-mensaje", "class=\"alert alert-success alert-dismissible fade show\"");
						request.setAttribute("mensaje", "Libro eliminado correctamente");
					}
					else
					{
						request.setAttribute("clase-mensaje", "class=\"alert alert-danger alert-dismissible fade show\"");
						request.setAttribute("mensaje", "Id de libro invalida");
					}
				
				} 
				catch (NumberFormatException e) {
					request.setAttribute("clase-mensaje", "class=\"alert alert-danger alert-dismissible fade show\"");
					request.setAttribute("mensaje", "Id de libro invalida");
				}
				catch (SQLException e) {
					// TODO Auto-generated catch block
					request.setAttribute("mensaje", "No se pudo eliminar un libro");
				}
			}
			else if (request.getParameter("action-type").equals("editar")) {	
				if (ValidarDatos(request)) {
				LibroLogic ll=new LibroLogic();
				Libro libro;
				try {
					libro = ll.getOne(Integer.parseInt(request.getParameter("id")));
					if(libro!=null) {
						libro.setId(Integer.parseInt(request.getParameter("id")));
						libro.setTitulo(request.getParameter("titulo"));
						libro.setAutor(request.getParameter("autor"));
						libro.setFechaEdicion(LocalDate.parse(request.getParameter("fecha-edicion")));
						libro.setNroEdicion(request.getParameter("numero-edicion"));
						int idGenero = (Integer.parseInt(request.getParameter("genero")));
						GeneroLogic gl = new GeneroLogic();
						try {
							libro.setGenero(gl.getOne(idGenero));
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							request.setAttribute("mensaje", "No se pudo encontrar el genero indicado");
						}
						ll.update(libro);
						request.setAttribute("clase-mensaje", "class=\"alert alert-success alert-dismissible fade show\"");
						request.setAttribute("mensaje", "Libro actualizado correctamente");
					}
					else {
						request.setAttribute("clase-mensaje", "class=\"alert alert-danger alert-dismissible fade show\"");
						request.setAttribute("mensaje", "Id de libro invalida");
					}
				}
				 catch (NumberFormatException e) {
					request.setAttribute("clase-mensaje", "class=\"alert alert-danger alert-dismissible fade show\"");
					request.setAttribute("mensaje", "Id de socio invalida");
				}
				catch (SQLException e) {
				
				// TODO Auto-generated catch block
					request.setAttribute("mensaje", "No se pudo actualizar un libro");
				}
			}
			}
			this.doGet(request, response);
		}
	}
	private static Boolean ValidarDatos (HttpServletRequest request) {
		if (!request.getParameter("titulo").isBlank() && !request.getParameter("autor").isBlank() && 
				!request.getParameter("fecha-edicion").isBlank() && !request.getParameter("numero-edicion").isBlank()&&
				!request.getParameter("genero").isBlank()&&	!request.getParameter("cant-ejemplares").isBlank()) {
				try {
					Integer.parseInt(request.getParameter("numero-edicion"));
					Integer.parseInt(request.getParameter("genero"));
					Integer.parseInt(request.getParameter("cant-ejemplares"));
					return true;
				}
				catch (NumberFormatException e) {
					request.setAttribute("mensaje", "Por favor, ingrese el nro de edici�n y cantidad de ejemplares en formato numerico, sin puntos ni smbolos y seleccione un genero valido.");
					return false;
				}
			
		
		}
		else {
			request.setAttribute("mensaje", "Campos incompletos.");
			return false;
		}
	}
	
}