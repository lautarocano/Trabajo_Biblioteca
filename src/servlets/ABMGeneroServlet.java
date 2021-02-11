package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.GeneroLogic;
import model.Genero;
import model.Usuario;

/**
 * Servlet implementation class ABMGeneroServlet
 */
@WebServlet("/ABMGeneroServlet")
public class ABMGeneroServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ABMGeneroServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (Servlet.VerificarSesionYUsuario(request, response, Usuario.tipoUsuario.Administrador)) {
			GeneroLogic gl = new GeneroLogic();
			try {
				request.setAttribute("ListaGeneros", gl.getAll());
			} catch (SQLException e) {
				response.getWriter().println(e.getMessage());
			}
			request.getRequestDispatcher("WEB-INF/ABMGenero.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (Servlet.VerificarSesionYUsuario(request, response, Usuario.tipoUsuario.Administrador)) {
			if (request.getParameter("action-type").equals("agregar")) {	
				Genero genero=new Genero();
				genero.setDescripcion(request.getParameter("descripcion"));
				GeneroLogic gl=new GeneroLogic();
				try {
					gl.insert(genero);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					response.getWriter().println(e.getMessage());
				}
			}
			else if (request.getParameter("action-type").equals("eliminar")) {	
				Genero genero=new Genero();
				genero.setId(Integer.parseInt(request.getParameter("id")));
				GeneroLogic gl=new GeneroLogic();
				try {
					gl.delete(genero);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					response.getWriter().println(e.getMessage());
				}
			}
			else if (request.getParameter("action-type").equals("editar")) {	
				Genero genero=new Genero();
				genero.setId(Integer.parseInt(request.getParameter("id")));
				genero.setDescripcion(request.getParameter("descripcion"));
				GeneroLogic gl=new GeneroLogic();
				try {
					gl.update(genero);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					response.getWriter().println(e.getMessage());
				}
			}
			this.doGet(request, response);
		}
	}
	
}