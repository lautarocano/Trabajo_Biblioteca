package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.GeneroLogic;
import logic.UsuarioLogic;
import model.Genero;
import model.Usuario;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public LoginServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*Servlet.VerificarSesionYUsuario(request, response, Usuario.tipoUsuario.Administrador);*/
		request.getRequestDispatcher("WEB-INF/Login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (request.getParameter("action-type").equals("ingresar")) {	
			HttpSession sesion = request.getSession();
			Usuario usuario=new Usuario();
			UsuarioLogic ul = new UsuarioLogic();
			String nombreUsuario=request.getParameter("nombreUsuario");
			String password=request.getParameter("password");
			try {
				usuario=ul.login(nombreUsuario,password);
				sesion.setAttribute("usuario",usuario);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
		}
		

	this.doGet(request, response);

	}

}
