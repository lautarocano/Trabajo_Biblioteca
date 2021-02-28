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
import model.Usuario;

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
		if (Servlet.VerificarSesionYUsuario(request, response, Usuario.tipoUsuario.Administrador)) {
			PoliticaSancionLogic psl = new PoliticaSancionLogic();
			try {
				request.setAttribute("ListaPoliticasSanciones", psl.getAll());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				request.setAttribute("mensaje", "No se pudieron obtener las politicas de sanción");
			}
			request.getRequestDispatcher("WEB-INF/ABMPoliticaSancion.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (Servlet.VerificarSesionYUsuario(request, response, Usuario.tipoUsuario.Administrador)) {
			if (request.getParameter("action-type").equals("agregar")) {	
				PoliticaSancion ps=new PoliticaSancion();
				ps.setDiasDeAtrasoDesde(Integer.parseInt(request.getParameter("dias_atraso_desde")));
				ps.setDiasDeAtrasoHasta(Integer.parseInt(request.getParameter("dias_atraso_hasta")));
				ps.setDiasDeSancion(Integer.parseInt(request.getParameter("dias_sancion")));
				PoliticaSancionLogic psl=new PoliticaSancionLogic();
				try {
					psl.insert(ps);
					request.setAttribute("clase-mensaje", "class=\"alert alert-success alert-dismissible fade show\"");
					request.setAttribute("mensaje", "Politica de sanción agregada correctamente");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					request.setAttribute("mensaje", "No se pudo agregar la politica de sanción");
				}
			}
			else if (request.getParameter("action-type").equals("eliminar")) {	
				PoliticaSancion ps=new PoliticaSancion();
				ps.setId(Integer.parseInt(request.getParameter("id")));
				PoliticaSancionLogic psl=new PoliticaSancionLogic();
				try {
					psl.delete(ps);
					request.setAttribute("clase-mensaje", "class=\"alert alert-success alert-dismissible fade show\"");
					request.setAttribute("mensaje", "Politica de sanción eliminada correctamente");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					request.setAttribute("mensaje", "No se pudo eliminar la politica de sanción");
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
					request.setAttribute("clase-mensaje", "class=\"alert alert-success alert-dismissible fade show\"");
					request.setAttribute("mensaje", "Politica de sanción actualizada correctamente");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					request.setAttribute("mensaje", "No se pudo actualizar la politica de sanción");
				}
			}
			this.doGet(request, response);
		}
	}

}