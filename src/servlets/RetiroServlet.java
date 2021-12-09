package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.ReservaLogic;
import model.Usuario;

/**
 * Servlet implementation class RetiroServlet
 */
@WebServlet("/RetiroServlet")
public class RetiroServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RetiroServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (Servlet.VerificarSesionYUsuario(request, response, Usuario.tipoUsuario.Bibliotecario)) {
			ReservaLogic rl = new ReservaLogic();
			if (request.getParameter("id-socio")!=null) {
				try {
					request.setAttribute("listaReserva", rl.getAllPendientesBySocio(Integer.parseInt(request.getParameter("id-socio"))));
				} catch (NumberFormatException e) {
					request.setAttribute("mensaje", "Error en los datos suministrados, id debe ser un número.");
				} catch (SQLException e) {
        			Servlet.log(Level.SEVERE,e, request);
					request.setAttribute("mensaje", "No se pudo obtener la lista de reservas para el socio solicitado");
				} catch (Exception e) {
					Servlet.log(Level.SEVERE,e, request);
					request.setAttribute("mensaje", "Ha ocurrido un error durante la ejecución de la operación");
				}
			}
			else {
				try {
					request.setAttribute("listaReserva", rl.getAllPendientes());
				} catch (SQLException e) {
        			Servlet.log(Level.SEVERE,e, request);
					request.setAttribute("mensaje", "No se pudo obtener la lista de reservas");
				} catch (Exception e) {
					Servlet.log(Level.SEVERE,e, request);
					request.setAttribute("mensaje", "Ha ocurrido un error durante la ejecución de la operación");
				}
			}
			request.setAttribute("JSP", "Retiro");
			request.getRequestDispatcher("WEB-INF/Bibliotecario.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (Servlet.VerificarSesionYUsuario(request, response, Usuario.tipoUsuario.Bibliotecario)) 
		doGet(request, response);
	}

}
