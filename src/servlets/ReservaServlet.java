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
import logic.SocioLogic;
import model.Genero;
import model.Libro;
import model.Reserva;

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
		request.getRequestDispatcher("WEB-INF/RealizarReserva.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("action-type").equals("reservar")) {	
			Reserva reserva=new Reserva();
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
				response.getWriter().println(e.getMessage());
			}
			LibroLogic ll = new LibroLogic();
			try {
				ll.insert(libro);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				response.getWriter().println(e.getMessage());
			}
		}
		
		this.doGet(request, response);
	}
}
