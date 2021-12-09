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

import logic.SocioLogic;
import logic.ReservaLogic;
import logic.BusinessLogicException;
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
	private static final String prefijoMensaje = "mensaje";
	private int idMensaje;
       
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
    	idMensaje = 0;
		if (Servlet.VerificarSesionYUsuario(request, response, Usuario.tipoUsuario.Socio)) {
			if (request.getSession().getAttribute("libros")!=null) {
				ArrayList<Libro> libros=((ArrayList<Libro>)request.getSession().getAttribute("libros"));
				ArrayList<ArrayList<String>> fechasDisponiblesTotal = new ArrayList<ArrayList<String>>();
				ArrayList<String> fechasDisponiblesLibro;
				if (libros.size() > 0) {
					ReservaLogic rl = new ReservaLogic();
					try {
						for(Libro l : libros) {
							fechasDisponiblesLibro = rl.getFechasDisponible(l, 2);
							request.setAttribute("availableDays"+l.getId(), fechasDisponiblesLibro);
							fechasDisponiblesTotal.add(fechasDisponiblesLibro);
						}
						request.setAttribute("availableDays", rl.getFechasDisponible(fechasDisponiblesTotal, 2));
					} catch (SQLException e) {
	        			Servlet.log(Level.SEVERE,e, request);
						request.setAttribute(this.getIdMensaje(), "Error en la base de datos, inténtelo nuevamente en unos minutos.");
					} catch (Exception e) {
						Servlet.log(Level.SEVERE,e, request);
						request.setAttribute("mensaje", "Ha ocurrido un error durante la ejecución de la operación");
					}
					request.setAttribute("JSP", "FinalizarReserva");
					request.getRequestDispatcher("WEB-INF/Socio.jsp").forward(request, response);
				}
				else {
					request.setAttribute(this.getIdMensaje(), "Carrito vacío, por favor agregue al menos un elemento.");
					request.getRequestDispatcher("ReservaServlet").forward(request,response);
				}
			}
			else {
				request.setAttribute(this.getIdMensaje(), "Carrito vacío, por favor agregue al menos un elemento.");
				request.getRequestDispatcher("ReservaServlet").forward(request,response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		idMensaje = 0;
		if (Servlet.VerificarSesionYUsuario(request, response, Usuario.tipoUsuario.Socio)) {
			try {
				if (request.getSession().getAttribute("libros")!=null && request.getParameter("action-type")!=null) {
					if (request.getParameter("action-type").equals("borrar")) {	
						try {
							int id=Integer.parseInt(request.getParameter("id_libro"));
							((ArrayList<Libro>)request.getSession().getAttribute("libros")).removeIf(l -> l.getId() == id);
						} catch (NumberFormatException e) {
							request.setAttribute(this.getIdMensaje(), "Error en los datos suministrados. Id de libro inválida.");
						}
						this.doGet(request, response);
					}
					else if(request.getParameter("action-type").equals("finalizar")  && request.getParameter("tipo")!=null) {
						ArrayList<Libro> libros=((ArrayList<Libro>)request.getSession().getAttribute("libros"));
						Socio socio = (Socio) request.getSession().getAttribute("socio");
						SocioLogic sl = new SocioLogic();
						Reserva reserva;
						ArrayList<LibroReserva> librosReservas;
						LibroReserva lr;
						if (libros.size() > 0) {
							if (request.getParameter("tipo").equals("conjunta")) {
								reserva=new Reserva();
								librosReservas = new ArrayList<LibroReserva>();
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
										request.setAttribute("clase-"+this.getIdMensaje(), "class=\"alert alert-success alert-dismissible fade show\"");
										request.setAttribute(this.getIdMensaje(), "Reserva guardada.");
										Servlet.enviarConGMail(socio.getEmail(), "Reserva Biblioteca", "Ha realizado una reserva con éxito", request);
										request.getSession().setAttribute("libros", null);
									} catch (BusinessLogicException ble) {
										request.setAttribute(this.getIdMensaje(), ble.getMessage());
									} catch (SQLException e) {
					        			Servlet.log(Level.SEVERE,e, request);
										request.setAttribute(this.getIdMensaje(), "Error en la base de datos, su reserva puede no haber sido realizada.");
									}
								}
								catch (IllegalArgumentException e ) {
									e.printStackTrace();
									request.setAttribute(this.getIdMensaje(), "El formato de fecha ingresado es inválido");
								}
							}
							else if (request.getParameter("tipo").equals("individual")) {
								for(Libro l :libros) {
									reserva=new Reserva();
									librosReservas = new ArrayList<LibroReserva>();
									lr=new LibroReserva();
									lr.setLibro(l);
									librosReservas.add(lr);
									reserva.setLibros(librosReservas);
									try {
										reserva.setFechaReserva(java.sql.Date.valueOf(request.getParameter("fecha"+l.getId())));
										reserva.setSocio(socio);
										try {
											sl.realizaReserva(reserva);
											request.setAttribute("clase-"+this.getIdMensaje(), "class=\"alert alert-success alert-dismissible fade show\"");
											request.setAttribute(this.getIdMensaje(), "Reserva guardada para el libro\""+l.getTitulo()+"\".");
											Servlet.enviarConGMail(socio.getEmail(), "Reserva Biblioteca", "Ha realizado una reserva con éxito", request);
											request.getSession().setAttribute("libros", null);
										} catch (BusinessLogicException ble) {
											request.setAttribute(this.getIdMensaje(), ble.getMessage());
										} catch (SQLException e) {
						        			Servlet.log(Level.SEVERE,e, request);
											request.setAttribute(this.getIdMensaje(), "Reserva de "+l.getTitulo()+" : Error en la base de datos, su reserva puede no haber sido realizada.");
										}
									}
									catch (IllegalArgumentException e ) {
										request.setAttribute(this.getIdMensaje(), "Reserva de "+l.getTitulo()+" : El formato de fecha ingresado es inválido");
									} finally {
										this.idMensaje++;
									}
								}
							}
						}
						else {
							request.setAttribute(this.getIdMensaje(), "No se pudo agregar una reserva si el carrito está vacío.");
						}
						request.getRequestDispatcher("ReservaServlet").forward(request,response);
					}
					else {
						request.setAttribute(this.getIdMensaje(), "Error en los datos suministrados.");
						this.doGet(request, response);
					}
				}
				else {
					request.setAttribute(this.getIdMensaje(), "Error en los datos suministrados.");
					this.doGet(request, response);
				}
			} catch (Exception e) {
				Servlet.log(Level.SEVERE,e, request);
				request.setAttribute("mensaje", "Ha ocurrido un error durante la ejecución de la operación");
				this.doGet(request, response);
			}
		}
	}
	
	private String getIdMensaje() {
		if (idMensaje == 0) {
			return prefijoMensaje;
		}
		else return prefijoMensaje + idMensaje;
	}
	
}