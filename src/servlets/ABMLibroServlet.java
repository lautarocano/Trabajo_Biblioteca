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
				e.printStackTrace();
			}
			request.getRequestDispatcher("WEB-INF/ABMLibro.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (Servlet.VerificarSesionYUsuario(request, response, Usuario.tipoUsuario.Administrador)) {
			if (request.getParameter("action-type").equals("agregar")) {	
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
					e.printStackTrace();
				}
				LibroLogic ll = new LibroLogic();
				try {
					ll.insert(libro);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if (request.getParameter("action-type").equals("eliminar")) {	
				Libro libro=new Libro();
				libro.setId(Integer.parseInt(request.getParameter("id")));
				LibroLogic ll=new LibroLogic();
				try {
					ll.delete(libro);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if (request.getParameter("action-type").equals("editar")) {	
				Libro libro=new Libro();
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
					e.printStackTrace();
				}
				LibroLogic ll=new LibroLogic();
				try {
					ll.update(libro);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			this.doGet(request, response);
		}
	}
	
}