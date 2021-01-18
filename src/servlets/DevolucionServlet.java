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
import model.Socio;

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
		// TODO Auto-generated method stub
		PrestamoLogic pl = new PrestamoLogic();
		// ESTO ES SOLO PARA PROBAR QUE FUNCIONA
		SocioLogic sl = new SocioLogic();
		try {
			Socio socio= sl.getOne(1);
			request.setAttribute("ListaPrestamo", pl.getAllPendientesBySocio(socio));
		} catch (SQLException e) {
			response.getWriter().println(e.getMessage());
		}
		request.setAttribute("JSP", "Devolucion");
		request.getRequestDispatcher("WEB-INF/Bibliotecario.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
if (request.getParameter("action-type").equals("devolver")) {	
	Prestamo prestamo=new Prestamo();
	prestamo.setId(Integer.parseInt(request.getParameter("id_prestamo")));
	PrestamoLogic pl=new PrestamoLogic();
	try {
		pl.endLoan(prestamo);;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		response.getWriter().println(e.getMessage());
	}
		}
		this.doGet(request, response);
	}

}