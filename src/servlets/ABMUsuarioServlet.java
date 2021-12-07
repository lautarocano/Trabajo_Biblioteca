package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;

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
		if (Servlet.VerificarSesionYUsuario(request, response, Usuario.tipoUsuario.Administrador)) {
			UsuarioLogic ul = new UsuarioLogic();
			try {
				request.setAttribute("ListaUsuarios", ul.getAll());
			} catch (SQLException e) {
    			Servlet.log(Level.SEVERE,e, request);
				request.setAttribute("mensaje", "No se pudo obtener el listado de usuarios");
			}
			request.setAttribute("JSP", "ABMUsuario");
			request.getRequestDispatcher("WEB-INF/Administrador.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (Servlet.VerificarSesionYUsuario(request, response, Usuario.tipoUsuario.Administrador)) {
			if (request.getParameter("action-type")!=null) {
				if (request.getParameter("action-type").equals("agregar")) {
					if (ValidarDatos(request)) {
						try {
							Usuario usuario = new Usuario();
							usuario.setNombreUsuario(request.getParameter("user"));
							usuario.setPassword(request.getParameter("password"));
							usuario.setEstado(Boolean.parseBoolean(request.getParameter("estado")));
							switch(Integer.parseInt(request.getParameter("tipo"))) {
							case (1):
								usuario.setTipo(tipoUsuario.Bibliotecario);
								break;
							case (2):
								usuario.setTipo(tipoUsuario.Administrador);
								break;
							default:
								throw new Exception("Tipo de usuario inválido.");
							}
							UsuarioLogic ul = new UsuarioLogic();
							ul.insert(usuario);
							request.setAttribute("clase-mensaje", "class=\"alert alert-success alert-dismissible fade show\"");
							request.setAttribute("mensaje", "Usuario agregado correctamente");
						} catch (SQLException e) {
		        			Servlet.log(Level.SEVERE,e, request);
							request.setAttribute("mensaje", "No se pudo agregar un usuario");
						} catch (Exception e) {
							request.setAttribute("mensaje", e.getMessage());
						}
					}
				}
				else if (request.getParameter("action-type").equals("eliminar")) {	
					Usuario usuario =null;
					UsuarioLogic ul = new UsuarioLogic();
					try {
						usuario=ul.getOne(Integer.parseInt(request.getParameter("id")));
						if(usuario!=null) {
							ul.delete(usuario);
							request.setAttribute("clase-mensaje", "class=\"alert alert-success alert-dismissible fade show\"");
							request.setAttribute("mensaje", "Usuario eliminado correctamente");
						}
						else {
							request.setAttribute("clase-mensaje", "class=\"alert alert-danger alert-dismissible fade show\"");
							request.setAttribute("mensaje", "Id de usuario invalida");
						}
					} catch (NumberFormatException e) {
							request.setAttribute("clase-mensaje", "class=\"alert alert-danger alert-dismissible fade show\"");
							request.setAttribute("mensaje", "Id de usuario invalida");
					
					} catch (SQLException e) {
	        			Servlet.log(Level.SEVERE,e, request);
						request.setAttribute("mensaje", "No se pudo eliminar un usuario");
					}
				}
				else if (request.getParameter("action-type").equals("editar")) {	
					if (ValidarDatos(request)) {
						Usuario usuario;
						UsuarioLogic ul = new UsuarioLogic();
						try {
							usuario=ul.getOne(Integer.parseInt(request.getParameter("id")));
							if(usuario!=null) {
								usuario.setNombreUsuario(request.getParameter("user"));
								usuario.setPassword(request.getParameter("password"));
								usuario.setEstado(Boolean.parseBoolean(request.getParameter("estado")));
								if (usuario.getTipo() != tipoUsuario.Socio) {
									switch(Integer.parseInt(request.getParameter("tipo"))) {
									case (1):
										usuario.setTipo(tipoUsuario.Bibliotecario);
										break;
									case (2):
										usuario.setTipo(tipoUsuario.Administrador);
										break;
									default:
										throw new Exception("Tipo de usuario inválido.");
									}
								}								
								ul.update(usuario);
								request.setAttribute("clase-mensaje", "class=\"alert alert-success alert-dismissible fade show\"");
								request.setAttribute("mensaje", "Usuario actualizado correctamente");
							} else {
								request.setAttribute("clase-mensaje", "class=\"alert alert-danger alert-dismissible fade show\"");
								request.setAttribute("mensaje", "Id de usuario invalida");
							}
						} catch (NumberFormatException e) {
							request.setAttribute("clase-mensaje", "class=\"alert alert-danger alert-dismissible fade show\"");
							request.setAttribute("mensaje", "Id de usuario invalida");
						} catch (SQLException e) {
		        			Servlet.log(Level.SEVERE,e, request);
							request.setAttribute("mensaje", "No se pudo actualizar un usuario");
						} catch (Exception e) {
							request.setAttribute("mensaje", e.getMessage());
						}
					}
				}
			}
			this.doGet(request, response);
		}
	}
	
	private static Boolean ValidarDatos (HttpServletRequest request) {
		if (Servlet.parameterNotNullOrBlank(request.getParameter("user")) && Servlet.parameterNotNullOrBlank(request.getParameter("password")) && 
				Servlet.parameterNotNullOrBlank(request.getParameter("tipo"))) {
				try {
					Integer.parseInt(request.getParameter("tipo"));
					return true;
				}
				catch (NumberFormatException e) {
					request.setAttribute("mensaje", "Por favor, ingrese un tipo de usuario valido.");
					return false;
				}
		}
		else {
			request.setAttribute("mensaje", "Campos incompletos.");
			return false;
		}
	}
	
}