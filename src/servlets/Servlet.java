package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Usuario;

/**
 * Servlet implementation class Servlet
 */
@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	public static Boolean VerificarSesion(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		//Si el Usuario no tiene una sesion se lo manda a la pagina de inicio.
		
		HttpSession sesion = request.getSession(false);	
		if(sesion==null) {
			response.sendRedirect("LoginServlet");
			return false;
		}
		else return true;
	}
	
	public static Boolean VerificarUsuario(HttpServletRequest request,HttpServletResponse response,Usuario.tipoUsuario tipoUsuario) throws IOException, ServletException{
		//Si el Usuario es un tipo de usuario incorrecto se lo manda a la pagina de inicio.
		HttpSession sesion = request.getSession();
		if(((Usuario)(sesion.getAttribute("usuario")))==null) {
			response.sendRedirect("LoginServlet");
			return false;
		}
		else if(tipoUsuario != ((Usuario)(sesion.getAttribute("usuario"))).getTipo()) {
			//Mmm revisar el invalidate, capaz es mucho
			//sesion.invalidate();
			response.sendRedirect("LoginServlet");
			return false;
		}	
		else return true;
	}

	public static Boolean VerificarSesionYUsuario(HttpServletRequest request, HttpServletResponse response, Usuario.tipoUsuario tipoUsuario) throws ServletException, IOException {			
		if (VerificarSesion(request, response)) {
			return VerificarUsuario(request, response, tipoUsuario);
		}
		else return false;
	}

}
