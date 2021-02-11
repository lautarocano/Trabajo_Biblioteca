package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.SocioLogic;
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
		try {
			Usuario usActual = (Usuario) sesion.getAttribute("usuario");
			if(usActual.getTipo()==tipoUsuario.Socio) {
				response.sendRedirect("ReservaServlet");
			}
			else if(usActual.getTipo()==tipoUsuario.Bibliotecario) {
				response.sendRedirect("RetiroServlet");
			}
			else if(usActual.getTipo()==tipoUsuario.Administrador) {
				response.sendRedirect("ABMLibroServlet");
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
		if (request.getParameter("action-type").equals("ingresar")) {	
			sesion = request.getSession();
			Usuario usuario=new Usuario();
			UsuarioLogic ul = new UsuarioLogic();
			String nombreUsuario=request.getParameter("nombreUsuario");
			String password=request.getParameter("password");
			try {
				usuario=ul.login(nombreUsuario,password);
				sesion.setAttribute("usuario",usuario);
				if(usuario.getTipo()==tipoUsuario.Socio) {
					SocioLogic sl = new SocioLogic();
					sesion.setAttribute("socio", sl.getOneByUser(usuario.getId()));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		doGet(request, response);
	}
	
}