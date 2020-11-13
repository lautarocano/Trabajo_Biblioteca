package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.UsuarioLogic;
import model.Usuario;
import model.Usuario.tipoUsuario;

/**
 * Servlet implementation class ABMUsuarioServlet
 */
@WebServlet("/ABMUsuarioServlet")
public class ABMUsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ABMUsuarioServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*Servlet.VerificarSesionYUsuario(request, response, Usuario.tipoUsuario.Administrador);*/
		UsuarioLogic ul = new UsuarioLogic();
		try {
			request.setAttribute("ListaUsuarios", ul.getAll());
		} catch (SQLException e) {
			response.getWriter().println(e.getMessage());
		}
		request.getRequestDispatcher("WEB-INF/ABMUsuario.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("action-type").equals("agregar")) {	
			Usuario usuario = new Usuario();
			usuario.setNombreUsuario(request.getParameter("user"));
			usuario.setPassword(request.getParameter("password"));
			usuario.setEstado(Boolean.parseBoolean(request.getParameter("estado")));
			switch(Integer.parseInt(request.getParameter("tipo"))) {
			case (0):
				usuario.setTipo(tipoUsuario.Socio);
				break;
			case (1):
				usuario.setTipo(tipoUsuario.Bibliotecario);
				break;
			case (2):
				usuario.setTipo(tipoUsuario.Administrador);
				break;
			default:
				//Informar error
				break;
			}
			UsuarioLogic ul = new UsuarioLogic();
			try {
				ul.insert(usuario);
			} catch (SQLException e) {
				response.getWriter().println(e.getMessage());
			}
		}
		else if (request.getParameter("action-type").equals("eliminar")) {	
			Usuario usuario = new Usuario();
			usuario.setId(Integer.parseInt(request.getParameter("id")));
			UsuarioLogic ul = new UsuarioLogic();
			try {
				ul.delete(usuario);
			} catch (SQLException e) {
				response.getWriter().println(e.getMessage());
			}
		}
		else if (request.getParameter("action-type").equals("editar")) {	
			Usuario usuario = new Usuario();
			usuario.setId(Integer.parseInt(request.getParameter("id")));
			usuario.setNombreUsuario(request.getParameter("user"));
			usuario.setPassword(request.getParameter("password"));
			usuario.setEstado(Boolean.parseBoolean(request.getParameter("estado")));
			switch(Integer.parseInt(request.getParameter("tipo"))) {
			case (0):
				usuario.setTipo(tipoUsuario.Socio);
				break;
			case (1):
				usuario.setTipo(tipoUsuario.Bibliotecario);
				break;
			case (2):
				usuario.setTipo(tipoUsuario.Administrador);
				break;
			default:
				//Informar error
				break;
			}
			UsuarioLogic ul = new UsuarioLogic();
			try {
				ul.update(usuario);
			} catch (SQLException e) {
				response.getWriter().println(e.getMessage());
			}
		}
		this.doGet(request, response);
	}
}