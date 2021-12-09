package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.PrestamoLogic;
import logic.SocioLogic;
import model.Prestamo;
import model.Socio;
import model.Usuario;

/**
 * Servlet implementation class DevolucionServlet
 */
@WebServlet("/DevolucionServlet")
public class DevolucionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DevolucionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (Servlet.VerificarSesionYUsuario(request, response, Usuario.tipoUsuario.Bibliotecario)) {
			try {
				PrestamoLogic pl = new PrestamoLogic();
				SocioLogic sl = new SocioLogic();
				Socio socio = null;
				if (request.getParameter("id-socio")!=null) {
					try {
						socio = sl.getOne(Integer.parseInt(request.getParameter("id-socio")));
						if (socio != null) {
							request.setAttribute("ListaPrestamo", pl.getAllPendientesBySocio(socio));
						}
						else {
							request.setAttribute("ListaPrestamo", pl.getAllPendientes());
							request.setAttribute("mensaje", "No existe socio para la id ingresada");
						}
					} catch (NumberFormatException e) {
						request.setAttribute("mensaje", "Id de socio debe ser un número.");
					}
					catch (SQLException e) {
	        			Servlet.log(Level.SEVERE,e, request);
						request.setAttribute("mensaje", "No se pudo obtener la lista de prestamos pendientes para ese socio");
					}
				}
				else {
					try {
						request.setAttribute("ListaPrestamo", pl.getAllPendientes());
					} catch (SQLException e) {
	        			Servlet.log(Level.SEVERE,e, request);
						request.setAttribute("mensaje", "No se pudo obtener la lista de prestamos pendientes");
					}
				}
			} catch (Exception e) {
				Servlet.log(Level.SEVERE,e, request);
				request.setAttribute("mensaje", "Ha ocurrido un error durante la ejecución de la operación");
			}
			request.setAttribute("JSP", "Devolucion");
			request.getRequestDispatcher("WEB-INF/Bibliotecario.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (Servlet.VerificarSesionYUsuario(request, response, Usuario.tipoUsuario.Bibliotecario)) {
			if (request.getParameter("action-type")!=null && request.getParameter("action-type").equals("devolver")) {	
				PrestamoLogic pl = new PrestamoLogic();
				Prestamo prestamo;
				try {
					prestamo = pl.getOne(Integer.parseInt(request.getParameter("id_prestamo")));
					if (prestamo != null) {
						SocioLogic sl = new SocioLogic();
						try {
							sl.entregaPrestamo(prestamo);
							request.setAttribute("clase-mensaje", "class=\"alert alert-success alert-dismissible fade show\"");
							request.setAttribute("mensaje", "Se realizo la devolución del prestamo correctamente");
						} catch (SQLException e) {
							request.setAttribute("mensaje", "No se pudo registrar la devolución del prestamo");
						}
					}
					else {
						request.setAttribute("mensaje", "Error en los datos suministrados. El préstamo no existe.");
					}
				} catch (NumberFormatException e) {
					request.setAttribute("mensaje", "Error en los datos suministrados.");
				} catch (SQLException e) {
        			Servlet.log(Level.SEVERE,e, request);
					request.setAttribute("mensaje", "Error en la base de datos.");
				} catch (Exception e) {
					Servlet.log(Level.SEVERE,e, request);
					request.setAttribute("mensaje", "Ha ocurrido un error durante la ejecución de la operación");
				}
			}
			this.doGet(request, response);
		}
	}

}