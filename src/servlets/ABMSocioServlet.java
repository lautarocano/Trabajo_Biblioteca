package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.SocioLogic;
import logic.UsuarioLogic;
import model.Socio;
import model.Usuario;
import model.Usuario.tipoUsuario;

/**
 * Servlet implementation class ABMSocioServlet
 */
@WebServlet("/ABMSocioServlet")
public class ABMSocioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ABMSocioServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (Servlet.VerificarSesionYUsuario(request, response, Usuario.tipoUsuario.Administrador)) {
			SocioLogic sl = new SocioLogic();
			try {
				request.setAttribute("ListaSocios", sl.getAll());
			} catch (SQLException e) {
				response.getWriter().println(e.getMessage());
			}
			request.getRequestDispatcher("WEB-INF/ABMSocio.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (Servlet.VerificarSesionYUsuario(request, response, Usuario.tipoUsuario.Administrador)) {
			if (request.getParameter("action-type").equals("agregar")) {	
				Socio socio = new Socio();
				socio.setNombre(request.getParameter("nombre"));
				socio.setApellido(request.getParameter("apellido"));
				socio.setEmail(request.getParameter("email"));
				socio.setDni(Integer.parseInt(request.getParameter("dni")));
				socio.setDomicilio(request.getParameter("domicilio"));
				socio.setTelefono(request.getParameter("telefono"));
				socio.setEstado(true);
				Usuario user = new Usuario();
				user.setTipo(tipoUsuario.Socio);
				user.setNombreUsuario(Integer.toString(socio.getDni()));
				user.setPassword(socio.getApellido());
				user.setEstado(false);
				SocioLogic sl = new SocioLogic();
				UsuarioLogic ul = new UsuarioLogic();
				try {
					socio.setUsuario(ul.insertAndReturn(user));
					sl.insert(socio);
				} catch (SQLException e) {
					response.getWriter().println(e.getMessage());
				}
			}
			else if (request.getParameter("action-type").equals("eliminar")) {	
				Socio socio = new Socio();
				socio.setId(Integer.parseInt(request.getParameter("id")));
				SocioLogic sl = new SocioLogic();
				try {
					sl.delete(socio);
				} catch (SQLException e) {
					response.getWriter().println(e.getMessage());
				}
			}
			else if (request.getParameter("action-type").equals("editar")) {	
				Socio socio = new Socio();
				socio.setId(Integer.parseInt(request.getParameter("id")));
				socio.setNombre(request.getParameter("nombre"));
				socio.setApellido(request.getParameter("apellido"));
				socio.setEmail(request.getParameter("email"));
				socio.setDni(Integer.parseInt(request.getParameter("dni")));
				socio.setDomicilio(request.getParameter("domicilio"));
				socio.setTelefono(request.getParameter("telefono"));
				socio.setEstado(Boolean.parseBoolean(request.getParameter("estado")));
				SocioLogic sl = new SocioLogic();
				try {
					sl.update(socio);
				} catch (SQLException e) {
					response.getWriter().println(e.getMessage());
				}
			}
			this.doGet(request, response);
		}
	}
	
}