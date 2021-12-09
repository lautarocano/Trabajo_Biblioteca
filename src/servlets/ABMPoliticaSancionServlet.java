package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;

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
    			Servlet.log(Level.SEVERE,e, request);
				request.setAttribute("mensaje", "No se pudieron obtener las politicas de sanción");
			} catch (Exception e) {
				Servlet.log(Level.SEVERE,e, request);
				request.setAttribute("mensaje", "Ha ocurrido un error durante la ejecución de la operación");
			}
			request.setAttribute("JSP", "ABMPoliticaSancion");
			request.getRequestDispatcher("WEB-INF/Administrador.jsp").forward(request, response);		
			}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (Servlet.VerificarSesionYUsuario(request, response, Usuario.tipoUsuario.Administrador)) {
			try {
			if (request.getParameter("action-type")!=null) {
				if (request.getParameter("action-type").equals("agregar")) {
					if (ValidarDatos(request)) {
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
		        			Servlet.log(Level.SEVERE,e, request);
							request.setAttribute("mensaje", "No se pudo agregar la politica de sanción");
						}
					}
				}
				else if (request.getParameter("action-type").equals("eliminar")) {	
					PoliticaSancion ps=null;
					PoliticaSancionLogic psl=new PoliticaSancionLogic();
					try {
						ps=psl.getOne(Integer.parseInt(request.getParameter("id")));
						if (ps != null) {
						psl.delete(ps);
						request.setAttribute("clase-mensaje", "class=\"alert alert-success alert-dismissible fade show\"");
						request.setAttribute("mensaje", "Politica de sanción eliminada correctamente");
						} else {
							request.setAttribute("clase-mensaje", "class=\"alert alert-danger alert-dismissible fade show\"");
							request.setAttribute("mensaje", "Id de politica sanción invalida");
						}
					}
					catch (NumberFormatException e) {
						request.setAttribute("clase-mensaje", "class=\"alert alert-danger alert-dismissible fade show\"");
						request.setAttribute("mensaje", "Id de politica sanción invalida");
					}
					catch (SQLException e) {
	        			Servlet.log(Level.SEVERE,e, request);
						request.setAttribute("mensaje", "No se pudo eliminar la politica de sanción");
					}
				}
				else if (request.getParameter("action-type").equals("editar")) {	
					if (ValidarDatos(request)) {
						PoliticaSancionLogic psl=new PoliticaSancionLogic();
						PoliticaSancion ps;
						try {
							ps=psl.getOne(Integer.parseInt(request.getParameter("id")));
							if (ps != null) {
								ps.setId(Integer.parseInt(request.getParameter("id")));
								ps.setDiasDeAtrasoDesde(Integer.parseInt(request.getParameter("dias_atraso_desde")));
								ps.setDiasDeAtrasoHasta(Integer.parseInt(request.getParameter("dias_atraso_hasta")));
								ps.setDiasDeSancion(Integer.parseInt(request.getParameter("dias_sancion")));
								psl.update(ps);
								request.setAttribute("clase-mensaje", "class=\"alert alert-success alert-dismissible fade show\"");
								request.setAttribute("mensaje", "Politica de sanción actualizada correctamente");
							}
							else {
								request.setAttribute("clase-mensaje", "class=\"alert alert-danger alert-dismissible fade show\"");
								request.setAttribute("mensaje", "Id de politica sanción invalida");
							}
						}
						catch (NumberFormatException e) {
							request.setAttribute("clase-mensaje", "class=\"alert alert-danger alert-dismissible fade show\"");
							request.setAttribute("mensaje", "Id de politica sanción invalida");
						} catch (SQLException e) {
		        			Servlet.log(Level.SEVERE,e, request);
							request.setAttribute("mensaje", "No se pudo actualizar la politica de sanción");
						}
					}
				}
			}
			} catch (Exception e) {
				Servlet.log(Level.SEVERE,e, request);
				request.setAttribute("mensaje", "Ha ocurrido un error durante la ejecución de la operación");
			}
			this.doGet(request, response);
		}
	}
	
	private static Boolean ValidarDatos (HttpServletRequest request) {
		if (Servlet.parameterNotNullOrBlank(request.getParameter("dias_atraso_desde")) && Servlet.parameterNotNullOrBlank(request.getParameter("dias_atraso_hasta")) && 
				Servlet.parameterNotNullOrBlank(request.getParameter("dias_sancion")) ) {
				try {
					Integer.parseInt(request.getParameter("dias_atraso_desde"));
					Integer.parseInt(request.getParameter("dias_atraso_hasta"));
					Integer.parseInt(request.getParameter("dias_sancion"));
					return true;
				}
				catch (NumberFormatException e) {
					request.setAttribute("mensaje", "Por favor, ingrese los dias de atraso/hasta  y sanción en formato numericos, sin puntos ni simbolos.");
					return false;
				}
		} else {
			request.setAttribute("mensaje", "Campos incompletos.");
			return false;
		}
	}
}