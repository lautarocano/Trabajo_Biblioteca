package servlets;

import java.io.File; 
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Iterator; 
import java.util.List; 

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem; 
import org.apache.commons.fileupload.disk.DiskFileItemFactory; 
import org.apache.commons.fileupload.servlet.ServletFileUpload; 

import logic.LibroLogic;
import model.Libro;
import model.Usuario;

/**
 * Servlet implementation class ImagesServlet
 */
@WebServlet("/ImagesServlet")
public class ImagesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImagesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(Servlet.VerificarSesionYUsuario(request, response)) {
			if(request.getParameter("id")!=null) {
				request.getRequestDispatcher("WEB-INF/images/"+request.getParameter("id")+".jpg").forward(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(Servlet.VerificarSesionYUsuario(request, response, Usuario.tipoUsuario.Administrador)) {
			if (request.getParameter("action")!=null && request.getParameter("action").equals("eliminar")) {	
					Libro libro=null;
					LibroLogic ll=new LibroLogic();
					System.out.println("1");
					try {
						libro = ll.getOne(Integer.parseInt(request.getParameter("id")));
						if (libro != null) {
							System.out.println("2");
							String aFileName = new String(request.getParameter("image").getBytes( 
							        "iso8859-1"), "gbk"); 
							String rootDirectory = request.getSession().getServletContext().getRealPath("/");
						    Path path = Paths.get(rootDirectory + "/WEB-INF/images/");
						    File file = new File(path.toString(), aFileName); 
						    if (!file.isDirectory()) { 
						    	System.out.println(file.getAbsolutePath());
						      file.delete(); 
						      request.setAttribute("clase-mensaje", "class=\"alert alert-success alert-dismissible fade show\"");
								request.setAttribute("mensaje", "Imagen eliminada.");
						    }
						}
						else {
							request.setAttribute("clase-mensaje", "class=\"alert alert-danger alert-dismissible fade show\"");
							request.setAttribute("mensaje", "No existe libro con la id ingresada.");
						}
					} catch (NumberFormatException e) {
						request.setAttribute("clase-mensaje", "class=\"alert alert-danger alert-dismissible fade show\"");
						request.setAttribute("mensaje", "Id de libro inválida");
					} catch (SQLException e) {
						request.setAttribute("mensaje", "No se pudo eliminar la imagen");
					} 
				}
				else {
					try {
							      // Create a factory for disk-based file items 
							      DiskFileItemFactory factory = new DiskFileItemFactory(); 
							      // Create a new file upload handler 
							      ServletFileUpload upload = new ServletFileUpload(factory); 
							      List < FileItem > items = upload. parseRequest (request); // Get all the files 
							      Iterator<FileItem> i = items.iterator(); 
							      while (i.hasNext()) { 
							        FileItem fi = (FileItem) i.next(); 
							        String fileName = fi.getName(); 
							        if (fileName != null) { 
							          File fullFile = new File(fi.getName()); 
							          String rootDirectory = request.getSession().getServletContext().getRealPath("/");
									  Path path = Paths.get(rootDirectory + "/WEB-INF/images/");
							          File savedFile = new File(path.toString(), fullFile.getName()); 
							          fi.write(savedFile); 
							          request.setAttribute("clase-mensaje", "class=\"alert alert-success alert-dismissible fade show\"");
									request.setAttribute("mensaje", "Imagen agregada correctamente");
							        } 
							      }
					} catch (SQLException e) {
						request.setAttribute("mensaje", "No se pudo agregar la imagen");
					}
					catch (Exception e) { 
					      // You can jump to the wrong page 
					      e.printStackTrace(); 
					    } 
			}
			request.getRequestDispatcher("ABMLibroServlet").forward(request, response);
		}
	}

}
