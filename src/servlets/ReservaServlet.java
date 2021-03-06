package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.GeneroLogic;
import logic.LibroLogic;
import model.Genero;
import model.Libro;
import model.Usuario;
import util.Bitacora;

/**
 * Servlet implementation class ReservaServlet
 */
@WebServlet("/ReservaServlet")
public class ReservaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
 /**
  * @see HttpServlet#HttpServlet()
  */
 public ReservaServlet() {
     super();
     // TODO Auto-generated constructor stub
 }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (Servlet.VerificarSesionYUsuario(request, response, Usuario.tipoUsuario.Socio)) {
			LibroLogic ll = new LibroLogic();
			GeneroLogic gl = new GeneroLogic();
			Genero genero = null;
			ArrayList<Libro> listaLibros = null;
			try {
				if (request.getParameter("genero")!=null) {
					try {
						genero = gl.getOne(Integer.parseInt(request.getParameter("genero")));
					}
					catch (NumberFormatException e) {
						//response.sendRedirect("ReservaServlet");
					}
					if (genero!=null) {
						if (request.getParameter("libro")!=null) {
							listaLibros = ll.getAllByTituloAndGenero(request.getParameter("libro"), genero);
						}
						else listaLibros = ll.getAllByGenero(genero);
					}
					//else response.sendRedirect("ReservaServlet");
				}
				else if (request.getParameter("libro")!=null) {
					listaLibros = ll.getAllByTitulo(request.getParameter("libro"));
				}
				else listaLibros = ll.getAll();
				request.setAttribute("ListaGeneros", gl.getAll());
				if (request.getSession().getAttribute("libros") != null) {
					@SuppressWarnings("unchecked")
					ArrayList<Libro> librosCarrito=((ArrayList<Libro>)request.getSession().getAttribute("libros"));
					for (Libro l : librosCarrito) {
						listaLibros.removeIf(libro -> libro.getId() == l.getId());
					}
				}
				request.setAttribute("ListaLibros", listaLibros);
			} catch (SQLException e) {
				request.setAttribute("mensaje", "Error en la base de datos.");
				Bitacora.log(Level.SEVERE, Bitacora.getStackTrace(e));
				request.setAttribute("ListaLibros", new ArrayList<Libro>());
				request.setAttribute("ListaGeneros", new ArrayList<Genero>());
			}
			request.setAttribute("JSP", "Reserva");
			request.getRequestDispatcher("WEB-INF/Socio.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (Servlet.VerificarSesionYUsuario(request, response, Usuario.tipoUsuario.Socio)) {
			if (request.getParameter("action-type").equals("reservar")) {	
				LibroLogic ll=new LibroLogic();
				Libro libro;
				try {
					libro = ll.getOne(Integer.parseInt(request.getParameter("id_libro")));
					if(request.getSession().getAttribute("libros") == null) {
						ArrayList<Libro> libros=new ArrayList<Libro>(); 
						request.getSession().setAttribute("libros", libros);
					}
					((ArrayList<Libro>)request.getSession().getAttribute("libros")).add(libro);
					request.setAttribute("clase-mensaje", "class=\"alert alert-success alert-dismissible fade show\"");
					request.setAttribute("mensaje", "Libro agregado correctamente");
				} catch (NumberFormatException e) {
					request.setAttribute("mensaje", "Error en los datos suministrados.");
				} catch (SQLException e) {
					request.setAttribute("mensaje", "Error en la base de datos.");
				}
			}
			this.doGet(request, response);
		}
	}
	
}