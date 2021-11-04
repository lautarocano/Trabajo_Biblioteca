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
import logic.LibroLogic;
import model.Libro;
import model.LibroReserva;
import model.Reserva;
import model.Socio;
import model.Usuario;

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
    @SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (Servlet.VerificarSesionYUsuario(request, response, Usuario.tipoUsuario.Socio)) {
			if (request.getSession().getAttribute("libros")!=null) {
				ArrayList<Libro> libros=((ArrayList<Libro>)request.getSession().getAttribute("libros"));
				if (libros.size() > 0) {
					LibroLogic ll = new LibroLogic();
					try {
						request.setAttribute("availableDays", ll.getFechasDisponible(ll.getOne(1), 2));
					} catch (SQLException e) {
						request.setAttribute("mensaje", "Error en la base de datos, inténtelo nuevamente en unos minutos.");
					}
					request.setAttribute("JSP", "FinalizarReserva");
					request.getRequestDispatcher("WEB-INF/Socio.jsp").forward(request, response);
				}
				else {
					request.setAttribute("mensaje", "Carrito vacío, por favor agregue al menos un elemento.");
					request.getRequestDispatcher("ReservaServlet").forward(request,response);
				}
			}
			else {
				request.setAttribute("mensaje", "Carrito vacío, por favor agregue al menos un elemento.");
				request.getRequestDispatcher("ReservaServlet").forward(request,response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (Servlet.VerificarSesionYUsuario(request, response, Usuario.tipoUsuario.Socio)) {
			if (request.getSession().getAttribute("libros")!=null && request.getParameter("action-type")!=null) {
				if (request.getParameter("action-type").equals("borrar")) {	
					try {
						int id=Integer.parseInt(request.getParameter("id_libro"));
						((ArrayList<Libro>)request.getSession().getAttribute("libros")).removeIf(l -> l.getId() == id);
					} catch (NumberFormatException e) {
						request.setAttribute("mensaje", "Error en los datos suministrados. Id de libro inválida.");
					}
				}
				else if(request.getParameter("action-type").equals("finalizar")) {
					ArrayList<Libro> libros=((ArrayList<Libro>)request.getSession().getAttribute("libros"));
					if (libros.size() > 0) {
						Reserva reserva=new Reserva();
						SocioLogic sl=new SocioLogic();
						ArrayList<LibroReserva> librosReservas = new ArrayList<LibroReserva>();
						LibroReserva lr = null;
						Socio socio = (Socio) request.getSession().getAttribute("socio");
						for(Libro l : libros) {
							lr=new LibroReserva();
							lr.setLibro(l);
							librosReservas.add(lr);
						}
						reserva.setLibros(librosReservas);
						try {
							reserva.setFechaReserva(java.sql.Date.valueOf(request.getParameter("fecha")));
							reserva.setSocio(socio);
							try {
								sl.realizaReserva(reserva);
								request.setAttribute("clase-mensaje", "class=\"alert alert-success alert-dismissible fade show\"");
								request.setAttribute("mensaje", "Reserva guardada.");
								request.getSession().setAttribute("libros", null);
								Servlet.enviarConGMail(socio.getEmail(), "Reserva Biblioteca", "Ha realizado una reserva con éxito");
							} catch (SQLException e) {
								request.setAttribute("mensaje", "Error en la base de datos, su reserva puede no haber sido realizada.");
							}
						}
						catch (IllegalArgumentException e ) {
							request.setAttribute("mensaje", "El formato de fecha ingresado es inválido");
						}
					}
					else {
						request.setAttribute("mensaje", "No se pudo agregar una reserva si el carrito está vacío.");
					}
				}
			}
			this.doGet(request, response);
		}
	}
	
}