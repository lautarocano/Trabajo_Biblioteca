package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import logic.SocioLogic;
import model.Socio;
import model.Usuario;

/**
 * Servlet implementation class DatosSocioServlet
 */
@WebServlet("/DatosSocioServlet")
public class DatosSocioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DatosSocioServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    HttpSession sesion;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (Servlet.VerificarSesionYUsuario(request, response, Usuario.tipoUsuario.Socio)) 
		{
			request.setAttribute("JSP", "DatosSocio");
			request.getRequestDispatcher("WEB-INF/Socio.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (Servlet.VerificarSesionYUsuario(request, response, Usuario.tipoUsuario.Socio)) {
			if (request.getParameter("action-type")!=null && request.getParameter("action-type").equals("editar")) {	
				if (ValidarDatos(request)) {
					SocioLogic sl = new SocioLogic();
					Socio socio;
					sesion = request.getSession();
					try {
						socio = sl.getOne(Integer.parseInt(request.getParameter("id")));
					    if(socio!=null) {
							socio.setId(Integer.parseInt(request.getParameter("id")));
							socio.setNombre(request.getParameter("nombre"));
							socio.setApellido(request.getParameter("apellido"));
							socio.setEmail(request.getParameter("email"));
							socio.setDni(Integer.parseInt(request.getParameter("dni")));
							socio.setDomicilio(request.getParameter("domicilio"));
							socio.setTelefono(request.getParameter("telefono"));
							socio.setEstado(Boolean.parseBoolean(request.getParameter("estado")));
							sesion.setAttribute("socio", socio );
							sl.update(socio);
							request.setAttribute("clase-mensaje", "class=\"alert alert-success alert-dismissible fade show\"");
							request.setAttribute("mensaje", "Datos actualizados correctamente");
					    }
					    else {
					    	request.setAttribute("clase-mensaje", "class=\"alert alert-danger alert-dismissible fade show\"");
							request.setAttribute("mensaje", "Id de socio invalida");
					    }
			
					}
					catch (NumberFormatException e) {
						request.setAttribute("clase-mensaje", "class=\"alert alert-danger alert-dismissible fade show\"");
						request.setAttribute("mensaje", "Id de socio invalida");
					}
		
					catch (SQLException e) {
						request.setAttribute("mensaje", "No se pudieron actualizar los datos");
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
					request.setAttribute("mensaje", "Por favor, ingrese su dni y telefono en formato numerico, sin puntos ni simbolos.");
					return false;
				}
			}
			else {
				request.setAttribute("mensaje", "Por favor, ingrese una dirección de email valida.");
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