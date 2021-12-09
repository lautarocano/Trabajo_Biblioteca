package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator; 
import java.util.List;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem; 
import org.apache.commons.fileupload.disk.DiskFileItemFactory; 
import org.apache.commons.fileupload.servlet.ServletFileUpload; 

import com.amazonaws.AmazonServiceException;
import com.amazonaws.AmazonClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;


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
    
    private static final String BUCKET_URL = "https://imagenesbibliotecabucket.s3-sa-east-1.amazonaws.com/";
    private static final int THRESHOLD_SIZE = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 140; // 140MB
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 150; // 150MB
    private static final String UUID_STRING = "uuid";
    private static final String AWS_ACCESS_KEY_ID = "AKIA6NS42AHIMPYBU7EU";
    private static final String AWS_SECRET_ACCESS_KEY = "";
    private static final String BUCKET_NAME = "imagenesbibliotecabucket";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(Servlet.VerificarSesionYUsuario(request, response)) {
			if(request.getParameter("id")!=null) {
				response.sendRedirect(BUCKET_URL+request.getParameter("id")+".jpg");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(Servlet.VerificarSesionYUsuario(request, response, Usuario.tipoUsuario.Administrador)) {
			try {
            System.setProperty("aws.accessKeyId", AWS_ACCESS_KEY_ID);
            System.setProperty("aws.secretKey", AWS_SECRET_ACCESS_KEY);
			final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.SA_EAST_1).build();
			if (request.getParameter("action")!=null && request.getParameter("action").equals("eliminar")) {	
					Libro libro=null;
					LibroLogic ll=new LibroLogic();
					try {
						libro = ll.getOne(Integer.parseInt(request.getParameter("id")));
						if (libro != null) {
							String aFileName = new String(request.getParameter("image").getBytes( 
							        "iso8859-1"), "gbk"); 
							try {
							    s3.deleteObject(BUCKET_NAME, aFileName);
							    request.setAttribute("clase-mensaje", "class=\"alert alert-success alert-dismissible fade show\"");
								request.setAttribute("mensaje", "Imagen eliminada.");
							} catch (AmazonServiceException ase) {
								Servlet.log(Level.SEVERE, ase, request);
								System.out.println(aFileName + ":error:" + ase.getMessage());
								request.setAttribute("mensaje", "No se pudo eliminar la imagen del libro.");
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
						Servlet.log(Level.SEVERE, e, request);
						request.setAttribute("mensaje", "No se pudo eliminar la imagen");
					} 
				}
				else {
					if (ServletFileUpload.isMultipartContent(request)) {
						DiskFileItemFactory factory = new DiskFileItemFactory();
				        factory.setSizeThreshold(THRESHOLD_SIZE);
				 
				        ServletFileUpload upload = new ServletFileUpload(factory);
				        upload.setFileSizeMax(MAX_FILE_SIZE);
				        upload.setSizeMax(MAX_REQUEST_SIZE);
				 
				        String uuidValue = "";
				        FileItem itemFile = null;

			            // parses the request's content to extract file data
			            List<FileItem> formItems = upload.parseRequest(request);
			            Iterator<FileItem> iter = formItems.iterator();
			 
			            // iterates over form's fields to get UUID Value
			            while (iter.hasNext()) {
			                FileItem item = (FileItem) iter.next();
			                if (item.isFormField()) {
			                    if (item.getFieldName().equalsIgnoreCase(UUID_STRING)) {
			                        uuidValue = item.getString();
			                    }
			                }
			                // processes only fields that are not form fields
			                if (!item.isFormField()) {
			                    itemFile = item;
			                }
			            }
			 
			            if (itemFile != null) {
			                // get item inputstream to upload file into s3 aws
			                try {
			                    ObjectMetadata om = new ObjectMetadata();
			                    om.setContentLength(itemFile.getSize());
			                    String keyName = uuidValue + ".jpg";
			 
			                    s3.putObject(new PutObjectRequest(BUCKET_NAME, keyName, itemFile.getInputStream(), om));
			                    s3.setObjectAcl(BUCKET_NAME, keyName, CannedAccessControlList.PublicRead);
			 
			                } catch (AmazonServiceException ase) {
			        			Servlet.log(Level.SEVERE, ase, request);
			                    System.out.println(uuidValue + ":error:" + ase.getMessage());
			 
			                } catch (AmazonClientException ace) {
			        			Servlet.log(Level.SEVERE, ace, request);
			                	System.out.println(uuidValue + ":error:" + ace.getMessage());
			                }				 
			            } else {
			            	System.out.println(uuidValue + ":error:" + "No Upload file");
			            }
			        }
				}
			} catch (Exception e) {
				Servlet.log(Level.SEVERE,e, request);
				request.setAttribute("mensaje", "Ha ocurrido un error durante la ejecución de la operación");
			}
			request.getRequestDispatcher("ABMLibroServlet").forward(request, response);
		}
	}

}
