package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
				request.setAttribute("mensaje", "No se pudo obtener el listado de socios");
			}
			request.setAttribute("JSP", "ABMSocio");
			request.getRequestDispatcher("WEB-INF/Administrador.jsp").forward(request, response);		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (Servlet.VerificarSesionYUsuario(request, response, Usuario.tipoUsuario.Administrador)) {
			if (request.getParameter("action-type") != null) {
				if (request.getParameter("action-type").equals("agregar")) {	
					if (ValidarDatos(request)) {
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
						user.setPassword(socio.getApellido()+"123");
						user.setEstado(false);
						SocioLogic sl = new SocioLogic();
						UsuarioLogic ul = new UsuarioLogic();
						try {
							socio.setUsuario(ul.insertAndReturn(user));
							sl.insert(socio);
							request.setAttribute("clase-mensaje", "class=\"alert alert-success alert-dismissible fade show\"");
							request.setAttribute("mensaje", "Socio agregado correctamente");
						} catch (SQLException e) {
							request.setAttribute("mensaje", "No se pudo agregar un socio");
						}
						catch (Exception e) {
							request.setAttribute("mensaje", e.getMessage());
						}
					}
				}
				else if (request.getParameter("action-type").equals("eliminar")) {	
					Socio socio = null;
					SocioLogic sl = new SocioLogic();
					try {
						socio = sl.getOne(Integer.parseInt(request.getParameter("id")));
						if (socio != null) {
							sl.delete(socio);
							request.setAttribute("clase-mensaje", "class=\"alert alert-success alert-dismissible fade show\"");
							request.setAttribute("mensaje", "Socio eliminado correctamente");
						}
						else {
							request.setAttribute("clase-mensaje", "class=\"alert alert-danger alert-dismissible fade show\"");
							request.setAttribute("mensaje", "Id de socio inválida");
						}
					} catch (NumberFormatException e) {
						request.setAttribute("clase-mensaje", "class=\"alert alert-danger alert-dismissible fade show\"");
						request.setAttribute("mensaje", "Id de socio inválida");
					} catch (SQLException e) {
						request.setAttribute("mensaje", "No se pudo eliminar un socio");
					}
				}
				else if (request.getParameter("action-type").equals("editar")) {	
					if (ValidarDatos(request)) {
						SocioLogic sl = new SocioLogic();
						Socio socio;
						try {
							socio = sl.getOne(Integer.parseInt(request.getParameter("id")));
							if (socio != null) {
								socio.setNombre(request.getParameter("nombre"));
								socio.setApellido(request.getParameter("apellido"));
								socio.setEmail(request.getParameter("email"));
								socio.setDni(Integer.parseInt(request.getParameter("dni")));
								socio.setDomicilio(request.getParameter("domicilio"));
								socio.setTelefono(request.getParameter("telefono"));
								socio.setEstado(Boolean.parseBoolean(request.getParameter("estado")));
								sl.update(socio);
								request.setAttribute("clase-mensaje", "class=\"alert alert-success alert-dismissible fade show\"");
								request.setAttribute("mensaje", "Socio actualizado correctamente");
							}
							else {
								request.setAttribute("clase-mensaje", "class=\"alert alert-danger alert-dismissible fade show\"");
								request.setAttribute("mensaje", "Id de socio inválida");
							}
						} catch (NumberFormatException e) {
							request.setAttribute("clase-mensaje", "class=\"alert alert-danger alert-dismissible fade show\"");
							request.setAttribute("mensaje", "Id de socio inválida");
						} catch (SQLException e) {
							request.setAttribute("mensaje", "No se pudo actualizar un socio");
						}
					}
				}
			}
			this.doGet(request, response);
		}
	}
	
	private static Boolean ValidarDatos (HttpServletRequest request) {
		if (Servlet.parameterNotNullOrBlank(request.getParameter("nombre")) && Servlet.parameterNotNullOrBlank(request.getParameter("apellido")) && 
				Servlet.parameterNotNullOrBlank(request.getParameter("telefono")) && Servlet.parameterNotNullOrBlank(request.getParameter("domicilio")) &&
				Servlet.parameterNotNullOrBlank(request.getParameter("dni")) && Servlet.parameterNotNullOrBlank(request.getParameter("email"))) {
			if (ValidarMail(request.getParameter("email"))) {
				try {
					Integer.parseInt(request.getParameter("dni"));
					Long.parseUnsignedLong(request.getParameter("telefono"));
					return true;
				}
				catch (NumberFormatException e) {
					request.setAttribute("mensaje", "Por favor, ingrese su dni y teléfono en formato numérico, sin puntos ni símbolos.");
					return false;
				}
			}
			else {
				request.setAttribute("mensaje", "Por favor, ingrese una dirección de email válida.");
				return false;
			}
		}
		else {
			request.setAttribute("mensaje", "Campos incompletos.");
			return false;
		}
	}
	
	public static boolean ValidarMail(String email) {
        // Patron para validar el email
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
 
        Matcher mather = pattern.matcher(email);
        return mather.find();
    }
	
}