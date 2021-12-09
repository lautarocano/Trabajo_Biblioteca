package servlets;

import java.io.IOException;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Usuario;
import model.Usuario.tipoUsuario;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    HttpSession sesion;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			sesion = request.getSession();
			Usuario usActual = (Usuario) sesion.getAttribute("usuario");
			if(usActual.getTipo()==tipoUsuario.Socio) {
				sesion.setAttribute("socio", null);
				sesion.setAttribute("libros", null);
			}
			sesion.setAttribute("usuario", null);
		} catch (Exception e) {
			Servlet.log(Level.SEVERE,e, request);
			request.setAttribute("mensaje", "Ha ocurrido un error durante la ejecución de la operación");
		}
		request.getRequestDispatcher("WEB-INF/Login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
