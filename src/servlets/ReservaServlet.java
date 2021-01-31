package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.GeneroLogic;
import logic.LibroLogic;
import model.Libro;

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
		/*Servlet.VerificarSesionYUsuario(request, response, Usuario.tipoUsuario.Administrador);*/
		LibroLogic ll = new LibroLogic();
		GeneroLogic gl = new GeneroLogic();
		try {
			request.setAttribute("ListaLibros", ll.getAll());
			request.setAttribute("ListaGeneros", gl.getAll());
		} catch (SQLException e) {
			response.getWriter().println(e.getMessage());
		}
		request.setAttribute("JSP", "Reserva");
		request.getRequestDispatcher("WEB-INF/Socio.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		this.doGet(request, response);
	}
}
