package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.UsuarioLogic;
import model.Usuario;
import model.Usuario.tipoUsuario;

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
    HttpSession sesion;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*Servlet.VerificarSesionYUsuario(request, response, Usuario.tipoUsuario.Administrador);*/
		try {
		Usuario usActual = (Usuario) sesion.getAttribute("usuario");
		if(usActual.getTipo()==tipoUsuario.Socio) {
			request.getRequestDispatcher("WEB-INF/Socio.jsp").forward(request, response);
		}
		else if(usActual.getTipo()==tipoUsuario.Bibliotecario) {
			request.getRequestDispatcher("WEB-INF/Bibliotecario.jsp").forward(request, response);

		}
		else if(usActual.getTipo()==tipoUsuario.Administrador) {
			request.getRequestDispatcher("WEB-INF/ABMLibro.jsp").forward(request, response);

		}
	}
		catch(java.lang.NullPointerException e) {
			request.getRequestDispatcher("WEB-INF/Login.jsp").forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (request.getParameter("action-type").equals("ingresar")) {	
			sesion = request.getSession();
			Usuario usuario=new Usuario();
			UsuarioLogic ul = new UsuarioLogic();
			String nombreUsuario=request.getParameter("nombreUsuario");
			String password=request.getParameter("password");
			
			try {
				usuario=ul.login(nombreUsuario,password);
				sesion.setAttribute("usuario",usuario);
				/*
				switch(usuario.getTipo()) {
				case Socio:
					request.getRequestDispatcher("WEB-INF/ABMEjemplar.jsp").forward(request, response);
					break;
				case Bibliotecario:
					request.getRequestDispatcher("WEB-INF/Bibliotecario.jsp").forward(request, response);
					break;
				case Administrador:
					request.getRequestDispatcher("WEB-INF/ABMLibro.jsp").forward(request, response);
					break;
					
				}
			*/
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
		}
		doGet(request, response);
	}
}
