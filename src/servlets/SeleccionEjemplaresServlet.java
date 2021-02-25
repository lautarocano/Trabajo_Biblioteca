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
import logic.SocioLogic;
import model.LibroReserva;
import model.LineaDePrestamo;
import model.Prestamo;
import model.Reserva;
import model.Usuario;
import model.Ejemplar;

/**
 * Servlet implementation class SeleccionEjemplaresServlet
 */
@WebServlet("/SeleccionEjemplaresServlet")
public class SeleccionEjemplaresServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SeleccionEjemplaresServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (Servlet.VerificarSesionYUsuario(request, response, Usuario.tipoUsuario.Bibliotecario)) {
			if (request.getParameter("id_reserva")!=null) {
				ReservaLogic rl = new ReservaLogic();
				EjemplarLogic el = new EjemplarLogic();
				try {
					ArrayList<ArrayList<Ejemplar>> disponibles = new ArrayList<ArrayList<Ejemplar>>();
					Reserva reserva = rl.getOne(Integer.parseInt(request.getParameter("id_reserva")));
					for (LibroReserva lr : reserva.getLibros()) {
						disponibles.add(el.getAllDisponibles(lr.getLibro()));
					}
					request.setAttribute("ejemplares", disponibles);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			request.setAttribute("JSP", "SeleccionEjemplar");
			request.getRequestDispatcher("WEB-INF/Bibliotecario.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (Servlet.VerificarSesionYUsuario(request, response, Usuario.tipoUsuario.Bibliotecario)) {
			if (request.getParameter("reserva")!=null) {
				ReservaLogic rl = new ReservaLogic();
				SocioLogic sl = new SocioLogic();
				Prestamo prestamo = new Prestamo();
				LineaDePrestamo ldp = null;
				ArrayList<LineaDePrestamo> lineasdp = new ArrayList<LineaDePrestamo>();
				Ejemplar ejemplar = null;
				String idLibro = null;
				Reserva reserva;
				try {
					reserva = rl.getOne(Integer.parseInt(request.getParameter("reserva")));
					prestamo.setSocio(reserva.getSocio());
					for (LibroReserva lr : reserva.getLibros()) {
						idLibro = Integer.toString(lr.getLibro().getId());
						if (request.getParameter(idLibro) != null) {
							if (!Boolean.parseBoolean(request.getParameter(idLibro+"checkbox"))) {
								ejemplar = new Ejemplar();
								ejemplar.setLibro(lr.getLibro());
								ejemplar.setId(Integer.parseInt(request.getParameter(idLibro)));
								ldp = new LineaDePrestamo();
								ldp.setEjemplar(ejemplar);
								lineasdp.add(ldp);
							}
						}
					}
					if(!lineasdp.isEmpty())
					{
						prestamo.setLineasPrestamo(lineasdp);
						sl.realizaPrestamo(prestamo);
						rl.entregarReserva(reserva);
					}
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			response.sendRedirect("RetiroServlet");
		}
	}

}