package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.EjemplarLogic;
import logic.ReservaLogic;
import model.Ejemplar;
import model.LibroReserva;
import model.Reserva;
import model.Socio;
import model.Usuario;

/**
 * Servlet implementation class ReservasSocioServlet
 */
@WebServlet("/ReservasSocioServlet")
public class ReservasSocioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReservasSocioServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (Servlet.VerificarSesionYUsuario(request, response, Usuario.tipoUsuario.Socio)) {
			ReservaLogic rl = new ReservaLogic();
			EjemplarLogic el = new EjemplarLogic();
			Socio socio = (Socio) request.getSession().getAttribute("socio");
			ArrayList<Ejemplar> disponibles = null;
			try {
				ArrayList<Reserva> reservas = rl.getAllPendientesBySocio(socio.getId());
				for (Reserva reserva: reservas) {
					//En este caso utilizamos el atributo entregada con otro significado por comodidad.
					//Si dicho atributo es igual a true significa que todos los libros de la reserva
					//tienen actualmente ejemplares disponibles para retiro, de lo contrario ser� false.
					reserva.setEntregada(true);
					for (LibroReserva lr : reserva.getLibros()) {
						disponibles = el.getAllDisponibles(lr.getLibro());
						if (disponibles.isEmpty()) {
							reserva.setEntregada(false);
						}
					}
				}
				request.setAttribute("listaReserva", reservas);
			} catch (SQLException e) {
				request.setAttribute("mensaje", "No se pudo obtener la lista de reservas para el socio solicitado");
			}
			request.setAttribute("JSP", "MisReservas");
			request.getRequestDispatcher("WEB-INF/Socio.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (Servlet.VerificarSesionYUsuario(request, response, Usuario.tipoUsuario.Socio)) {
			if (request.getParameter("action").equals("cancel")) {
				ReservaLogic rl = new ReservaLogic();
				try {
					Reserva reserva = rl.getOne(Integer.parseInt(request.getParameter("reserva")));
					rl.delete(reserva);
					request.setAttribute("clase-mensaje", "class=\"alert alert-success alert-dismissible fade show\"");
					request.setAttribute("mensaje", "Se cancel� la reserva");
				} catch (NumberFormatException e) {
					request.setAttribute("mensaje", "No se pudo cancelar la reserva");
				} catch (SQLException e) {
					request.setAttribute("mensaje", "No se pudo cancelar la reserva");
				}
			}
			doGet(request, response);
		}
	}

}