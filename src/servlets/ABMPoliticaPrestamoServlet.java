package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.PoliticaPrestamoLogic;
import model.PoliticaPrestamo;
import model.Usuario;

/**
 * Servlet implementation class ABMPoliticaPrestamoServlet
 */
@WebServlet("/ABMPoliticaPrestamoServlet")
public class ABMPoliticaPrestamoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
     * Default constructor. 
     */
    public ABMPoliticaPrestamoServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (Servlet.VerificarSesionYUsuario(request, response, Usuario.tipoUsuario.Administrador)) {
			PoliticaPrestamoLogic ppl = new PoliticaPrestamoLogic();
			try {
				request.setAttribute("ListaPoliticasPrestamos", ppl.getAll());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				request.setAttribute("mensaje", "No se pudieron obtener las politicas de prestamo");
			}
			request.setAttribute("JSP", "ABMPoliticaPrestamo");
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
						PoliticaPrestamo pp=new PoliticaPrestamo();
						pp.setCantMaxLibrosPend(Integer.parseInt(request.getParameter("cant_max_libros_pend")));
						pp.setFechaPoliticaPrestamo(java.sql.Date.valueOf(request.getParameter("fecha_politica_prestamo")));
						pp.setDiasPrestamo(Integer.parseInt(request.getParameter("cant_dias_prestamo")));
						PoliticaPrestamoLogic ppl=new PoliticaPrestamoLogic();
						try {
							ppl.insert(pp);
							request.setAttribute("clase-mensaje", "class=\"alert alert-success alert-dismissible fade show\"");
							request.setAttribute("mensaje", "Politica de prestamo agregada correctamente");
						} catch (SQLException e) {
							request.setAttribute("mensaje", "No se pudo agregar la politica de prestamo");
						}
					}
				} 
				else if (request.getParameter("action-type").equals("eliminar")) {	
					PoliticaPrestamo pp=null;
					PoliticaPrestamoLogic ppl=new PoliticaPrestamoLogic();
					try {
						pp=ppl.getOne(Integer.parseInt(request.getParameter("id")));
						if (pp != null) {
							ppl.delete(pp);
							request.setAttribute("clase-mensaje", "class=\"alert alert-success alert-dismissible fade show\"");
							request.setAttribute("mensaje", "Politica de prestamo eliminada correctamente");
						} 
						else {
							request.setAttribute("clase-mensaje", "class=\"alert alert-danger alert-dismissible fade show\"");
							request.setAttribute("mensaje", "Id de politica prestamo invalida");
						}
					} catch (NumberFormatException e) {
						request.setAttribute("clase-mensaje", "class=\"alert alert-danger alert-dismissible fade show\"");
						request.setAttribute("mensaje", "Id de politica prestamo invalida");
					} catch (SQLException e) {
						request.setAttribute("mensaje", "No se pudo eliminar la politica de prestamo");
					}
				}
				else if (request.getParameter("action-type").equals("editar")) {	
					if (ValidarDatos(request)) {
						PoliticaPrestamoLogic ppl=new PoliticaPrestamoLogic();
						PoliticaPrestamo pp;
						try {
							pp=ppl.getOne(Integer.parseInt(request.getParameter("id")));
							if(pp!=null) {
								pp.setId(Integer.parseInt(request.getParameter("id")));
								pp.setCantMaxLibrosPend(Integer.parseInt(request.getParameter("cant_max_libros_pend")));
								pp.setFechaPoliticaPrestamo(java.sql.Date.valueOf(request.getParameter("fecha_politica_prestamo")));
								pp.setDiasPrestamo(Integer.parseInt(request.getParameter("cant_dias_prestamo")));
								ppl.update(pp);
								request.setAttribute("clase-mensaje", "class=\"alert alert-success alert-dismissible fade show\"");
								request.setAttribute("mensaje", "Politica de prestamo actualizada correctamente");	
							} else {
								request.setAttribute("clase-mensaje", "class=\"alert alert-danger alert-dismissible fade show\"");
								request.setAttribute("mensaje", "Id de politica prestamo invalida");
							}
						} catch (NumberFormatException e) {
							request.setAttribute("clase-mensaje", "class=\"alert alert-danger alert-dismissible fade show\"");
							request.setAttribute("mensaje", "Id de politica prestamo invalida");
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							request.setAttribute("mensaje", "No se pudo actualizar la politica de prestamo");
						}
					}
				}
			}
			this.doGet(request, response);
		}
	}
	
	private static Boolean ValidarDatos (HttpServletRequest request) {
		if (Servlet.parameterNotNullOrBlank(request.getParameter("cant_max_libros_pend")) && Servlet.parameterNotNullOrBlank(request.getParameter("fecha_politica_prestamo")) && 
				Servlet.parameterNotNullOrBlank(request.getParameter("cant_dias_prestamo")) ) {
				try {
					Integer.parseInt(request.getParameter("cant_max_libros_pend"));
					Integer.parseInt(request.getParameter("cant_dias_prestamo"));
					return true;
				}
				catch (NumberFormatException e) {
					request.setAttribute("mensaje", "Por favor, ingrese la cantidad maxima de libros pendientes y cantidad dias de prestamo en formato numerico, sin puntos ni simbolos.");
					return false;
				}
		}
		else {
			request.setAttribute("mensaje", "Campos incompletos.");
			return false;
		}
	}
	
	
}