package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.SocioLogic;
import model.Libro;
import model.LibroReserva;
import model.Reserva;
import model.Socio;

/**
 * Servlet implementation class FinalizarReservaServlet
 */
@WebServlet("/FinalizarReservaServlet")
public class FinalizarReservaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FinalizarReservaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("JSP", "FinalizarReserva");
		request.getRequestDispatcher("WEB-INF/Socio.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (request.getParameter("action-type").equals("borrar")) {	
					
				int id=Integer.parseInt(request.getParameter("id_libro"));
				((ArrayList<Libro>)request.getSession().getAttribute("libros")).removeIf(l -> l.getId()== id);
				}
		else if(request.getParameter("action-type").equals("finalizar"))
		{
			Reserva reserva=new Reserva();
			SocioLogic sl=new SocioLogic();
			LibroReserva lr;
			ArrayList<LibroReserva> librosReservas=new ArrayList<LibroReserva>();
			ArrayList<Libro> libros=(((ArrayList<Libro>)request.getSession().getAttribute("libros")));
			for(Libro l : libros) {
				lr=new LibroReserva();
				lr.setLibro(l);
				librosReservas.add(lr);
				
			}
			reserva.setLibros(librosReservas);
			reserva.setFechaReserva(java.sql.Date.valueOf(request.getParameter("fecha")));
			reserva.setSocio((Socio) request.getSession().getAttribute("socio"));

			try {
				sl.realizaReserva(reserva);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
				this.doGet(request, response);
			}
}
