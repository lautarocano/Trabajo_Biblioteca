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
import model.Socio;
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
		if (request.getParameter("action-type") != null) {
			if (request.getParameter("action-type").equals("recuperar")) {
				String email = request.getParameter("email");
				SocioLogic sl = new SocioLogic();
				UsuarioLogic ul = new UsuarioLogic();
				try {
					Socio socio = sl.getOneByEmail(email);
					try {
						Usuario usuario = ul.getOneBySocio(socio.getId());
						Servlet.enviarConGMail(socio.getEmail(), "Recuperación de usuario", "Su usuario es: "+usuario.getNombreUsuario()+"\nSu contraseña es: "+usuario.getPassword());
						request.setAttribute("clase-mensaje", "class=\"alert alert-success alert-dismissible fade show\"");
						request.setAttribute("mensaje", "Información de recuperación enviada.");
					} catch (SQLException e) {
						request.setAttribute("mensaje", "No existe usuario con ese email.");
					}
					
				}
				catch (SQLException e) {
					
					request.setAttribute("mensaje", "No existe un socio con ese mail");
				}
			}
		}
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
		if (request.getParameter("action-type")!=null && request.getParameter("action-type").equals("ingresar")) {	
			if (Servlet.parameterNotNullOrBlank(request.getParameter("nombreUsuario")) && Servlet.parameterNotNullOrBlank(request.getParameter("password"))) {
				sesion = request.getSession();
				Usuario usuario = null;
				UsuarioLogic ul = new UsuarioLogic();
				String nombreUsuario = request.getParameter("nombreUsuario");
				String password = request.getParameter("password");
				try {
					usuario=ul.login(nombreUsuario,password);
					if (usuario != null) {
						sesion.setAttribute("usuario",usuario);
						if(usuario.getTipo()==tipoUsuario.Socio) {
							SocioLogic sl = new SocioLogic();
							sesion.setAttribute("socio", sl.getOneByUser(usuario.getId()));
						}
					}
					else request.setAttribute("mensaje", "Usuario y/o contraseña incorrectos.");
				} catch (SQLException e) {
					request.setAttribute("mensaje", "Error en la base de datos.");
				}		
			} else request.setAttribute("mensaje", "Campos incompletos.");
		}
		doGet(request, response);
	}
	
}