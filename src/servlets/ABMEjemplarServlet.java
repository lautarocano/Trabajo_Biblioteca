package servlets;

import java.io.IOException;
import java.sql.SQLException;

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
				Libro libro;
				try {
					libro = ll.getOne(Integer.parseInt(request.getParameter("IdLibro")));
					request.setAttribute("libro", libro);
					request.setAttribute("ListaEjemplares", el.getAllByLibro(libro.getId()));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					request.setAttribute("mensaje", "No se pudieron obtener los ejemplares");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					request.setAttribute("mensaje", "No se pudieron obtener los ejemplares");
				}
				request.getRequestDispatcher("WEB-INF/ABMEjemplar.jsp").forward(request, response);
			}
			else {
				ABMLibroServlet abmlServlet = new ABMLibroServlet();
				abmlServlet.doGet(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (Servlet.VerificarSesionYUsuario(request, response, Usuario.tipoUsuario.Administrador)) {
			if (request.getParameter("action-type").equals("agregar")) {	
				Ejemplar ejemplar = new Ejemplar();
				LibroLogic ll = new LibroLogic();
				int idLibro = (Integer.parseInt(request.getParameter("IdLibro")));
				try {
					ejemplar.setLibro(ll.getOne(idLibro));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					request.setAttribute("mensaje", "No se pudo obtener ejemplares para el libro solicitado");
				}
				EjemplarLogic el = new EjemplarLogic();
				try {
					el.insert(ejemplar);
					request.setAttribute("clase-mensaje", "class=\"alert alert-success alert-dismissible fade show\"");
					request.setAttribute("mensaje", "Ejemplar agregado correctamente");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					request.setAttribute("mensaje", "No se pudo agregar el ejemplar");
				}
			}
			else if (request.getParameter("action-type").equals("eliminar")) {	
				Ejemplar ejemplar=new Ejemplar();
				ejemplar.setId(Integer.parseInt(request.getParameter("id")));
				EjemplarLogic ll=new EjemplarLogic();
				try {
					ll.delete(ejemplar);
					request.setAttribute("clase-mensaje", "class=\"alert alert-success alert-dismissible fade show\"");
					request.setAttribute("mensaje", "Ejemplar eliminado correctamente");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					request.setAttribute("mensaje", "No se pudo eliminar el ejemplar");
				}
			}
			this.doGet(request, response);
		}
	}

}