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

	public static void VerificarSesion(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		//Si el Usuario no tiene una sesion se lo manda a la pagina de inicio.
		
		HttpSession sesion = request.getSession(false);	
		if(sesion==null) request.getRequestDispatcher("index.html").forward(request, response);
		
		}
	public static void VerificarUsuario(HttpServletRequest request,HttpServletResponse response,Usuario.tipoUsuario tipoUsuario) throws IOException, ServletException{
		//Si el Usuario es un tipo de usuario incorrecto se lo manda a la pagina de inicio.
		
		HttpSession sesion = request.getSession();
		if(((Usuario)(sesion.getAttribute("usuario")))==null) {
			request.getRequestDispatcher("index.html").forward(request, response);
		}
		if(tipoUsuario != ((Usuario)(sesion.getAttribute("usuario"))).getTipo()) {
			sesion.invalidate();
			request.getRequestDispatcher("index.html").forward(request, response);
			}	
		}

	public static void VerificarSesionYUsuario(HttpServletRequest request, HttpServletResponse response, Usuario.tipoUsuario tipoUsuario) throws ServletException, IOException {	
		//Si el Usuario no tiene una sesion o es un paciente se lo manda a la pagina de inicio.
		
		VerificarSesion(request, response);
		VerificarUsuario(request, response, tipoUsuario);
		}

}
