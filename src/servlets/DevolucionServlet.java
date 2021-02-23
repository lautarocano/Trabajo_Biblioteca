package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.PrestamoLogic;
import logic.SocioLogic;
import model.Prestamo;
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
			PrestamoLogic pl = new PrestamoLogic();
			SocioLogic sl = new SocioLogic();
			if (request.getParameter("id-socio")!=null) {
				try {
					request.setAttribute("ListaPrestamo", pl.getAllPendientesBySocio(sl.getOne(Integer.parseInt(request.getParameter("id-socio")))));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				try {
					request.setAttribute("ListaPrestamo", pl.getAllPendientes());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
			if (request.getParameter("action-type").equals("devolver")) {	
				PrestamoLogic pl = new PrestamoLogic();
				Prestamo prestamo;
				try {
					prestamo = pl.getOne(Integer.parseInt(request.getParameter("id_prestamo")));
					SocioLogic sl = new SocioLogic();
					try {
						sl.entregaPrestamo(prestamo);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			this.doGet(request, response);
		}
	}

}