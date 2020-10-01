package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.PoliticaSancionLogic;
import model.PoliticaSancion;

/**
 * Servlet implementation class ABMPoliticaSancionServlet
 */
@WebServlet("/ABMPoliticaSancionServlet")
public class ABMPoliticaSancionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ABMPoliticaSancionServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*Servlet.VerificarSesionYUsuario(request, response, Usuario.tipoUsuario.Administrador);*/
		PoliticaSancionLogic psl = new PoliticaSancionLogic();
		try {
			request.setAttribute("ListaPoliticasSanciones", psl.getAll());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			response.getWriter().println(e.getMessage());
		}
		request.getRequestDispatcher("WEB-INF/ABMPoliticaSancion.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (request.getParameter("action-type").equals("agregar")) {	
			PoliticaSancion ps=new PoliticaSancion();
			ps.setDiasDeAtrasoDesde(Integer.parseInt(request.getParameter("dias_atraso_desde")));
			ps.setDiasDeAtrasoHasta(Integer.parseInt(request.getParameter("dias_atraso_hasta")));
			ps.setDiasDeSancion(Integer.parseInt(request.getParameter("dias_sancion")));
			PoliticaSancionLogic psl=new PoliticaSancionLogic();
			try {
				psl.insert(ps);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				response.getWriter().println(e.getMessage());
			}
		}
		else if (request.getParameter("action-type").equals("eliminar")) {	
			PoliticaSancion ps=new PoliticaSancion();
			ps.setId(Integer.parseInt(request.getParameter("id")));
			PoliticaSancionLogic psl=new PoliticaSancionLogic();
			try {
				psl.delete(ps);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				response.getWriter().println(e.getMessage());
			}
		}
		else if (request.getParameter("action-type").equals("editar")) {	
			PoliticaSancion ps=new PoliticaSancion();
			ps.setId(Integer.parseInt(request.getParameter("id")));
			ps.setDiasDeAtrasoDesde(Integer.parseInt(request.getParameter("dias_atraso_desde")));
			ps.setDiasDeAtrasoHasta(Integer.parseInt(request.getParameter("dias_atraso_hasta")));
			ps.setDiasDeSancion(Integer.parseInt(request.getParameter("dias_sancion")));
			PoliticaSancionLogic psl=new PoliticaSancionLogic();
			try {
				psl.update(ps);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				response.getWriter().println(e.getMessage());
			}
		}
		this.doGet(request, response);
	}

}
