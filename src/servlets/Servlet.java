package servlets;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;

import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import model.Usuario;
import util.Bitacora;

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
	
	public static Boolean VerificarUsuario(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		//Si el Usuario es un tipo de usuario incorrecto se lo manda a la pagina de inicio.
		HttpSession sesion = request.getSession();
		if(((Usuario)(sesion.getAttribute("usuario")))==null) {
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
	
	public static Boolean VerificarSesionYUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {			
		if (VerificarSesion(request, response)) {
			return VerificarUsuario(request, response);
		}
		else return false;
	}
	
	public static void enviarConGMail(String destinatario, String asunto, String cuerpo) {
	    // Esto es lo que va delante de @gmail.com en tu cuenta de correo. Es el remitente también.
	    String remitente = "trabajobibliotecajava";  //Para la dirección nomcuenta@gmail.com
	    String clave = "trabajobibliotecajava123456789";
	    
	    InternetAddress direccion;
		try {
			direccion = new InternetAddress(destinatario);
			Properties props = System.getProperties();
		    props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
		    props.put("mail.smtp.user", remitente);
		    props.put("mail.smtp.clave", clave);    //La clave de la cuenta
		    props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
		    props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
		    props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google

		    Session session = Session.getDefaultInstance(props);
		    MimeMessage message = new MimeMessage(session);
		    message.setFrom(new InternetAddress(remitente));
	        message.addRecipient(RecipientType.TO, direccion);   //Se podrían añadir varios de la misma manera
	        message.setSubject(asunto);
	        message.setText(cuerpo);
	        Transport transport = session.getTransport("smtp");
	        transport.connect("smtp.gmail.com", remitente, clave);
	        transport.sendMessage(message, message.getAllRecipients());
	        transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//Otra forma:
		/*Properties props = new Properties();
	    props.put("mail.smtp.host", "smtp.gmail.com");
	    props.put("mail.smtp.socketFactory.port", "465");
	    props.put("mail.smtp.socketFactory.class",
	        "javax.net.ssl.SSLSocketFactory");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.port", "465");
	    Session session = Session.getDefaultInstance(props,
	      new javax.mail.Authenticator() {
	        protected PasswordAuthentication getPasswordAuthentication() {
	          return new PasswordAuthentication(remitente,clave);
	        }
	      });
	    try {
	      Message message = new MimeMessage(session);
	      message.setFrom(new InternetAddress(remitente+"@gmail.com"));
	      message.setRecipients(Message.RecipientType.TO,
	          InternetAddress.parse(destinatario));
	      message.setSubject(asunto);
	      message.setText(cuerpo);
	      Transport.send(message);
	      System.out.println("Correcto!");
		} 
		catch (MessagingException me) {
	        me.printStackTrace();   //Si se produce un error
	    }*/
	}
	
	public static void enviarConGMail(String destinatario, String asunto, String cuerpo, HttpServletRequest request) {
	    // Esto es lo que va delante de @gmail.com en tu cuenta de correo. Es el remitente también.
	    String remitente = "trabajobibliotecajava";  //Para la dirección nomcuenta@gmail.com
	    String clave = "trabajobibliotecajava123456789";
	    
	    InternetAddress direccion;
		try {
			direccion = new InternetAddress(destinatario);
			Properties props = System.getProperties();
		    props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
		    props.put("mail.smtp.user", remitente);
		    props.put("mail.smtp.clave", clave);    //La clave de la cuenta
		    props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
		    props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
		    props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google

		    Session session = Session.getDefaultInstance(props);
		    MimeMessage message = new MimeMessage(session);
		    message.setFrom(new InternetAddress(remitente));
	        message.addRecipient(RecipientType.TO, direccion);   //Se podrían añadir varios de la misma manera
	        message.setSubject(asunto);
	        message.setText(cuerpo);
	        Transport transport = session.getTransport("smtp");
	        transport.connect("smtp.gmail.com", remitente, clave);
	        transport.sendMessage(message, message.getAllRecipients());
	        transport.close();
		} catch (Exception e) {
			Servlet.log(Level.SEVERE, e, request);
		}
		//Otra forma:
		/*Properties props = new Properties();
	    props.put("mail.smtp.host", "smtp.gmail.com");
	    props.put("mail.smtp.socketFactory.port", "465");
	    props.put("mail.smtp.socketFactory.class",
	        "javax.net.ssl.SSLSocketFactory");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.port", "465");
	    Session session = Session.getDefaultInstance(props,
	      new javax.mail.Authenticator() {
	        protected PasswordAuthentication getPasswordAuthentication() {
	          return new PasswordAuthentication(remitente,clave);
	        }
	      });
	    try {
	      Message message = new MimeMessage(session);
	      message.setFrom(new InternetAddress(remitente+"@gmail.com"));
	      message.setRecipients(Message.RecipientType.TO,
	          InternetAddress.parse(destinatario));
	      message.setSubject(asunto);
	      message.setText(cuerpo);
	      Transport.send(message);
	      System.out.println("Correcto!");
		} 
		catch (MessagingException me) {
	        me.printStackTrace();   //Si se produce un error
	    }*/
	}
	
	public static Boolean parameterNotNullOrBlank(String parametro) {
		if (parametro != null) {
			/*if (parametro.isBlank()) return false;
			else*/ return true;
		}
		else return false;
	}
	
	public static void log(java.util.logging.Level level, Exception e, HttpServletRequest request) {
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");
		Bitacora.log(level, Bitacora.getStackTrace(e), rootDirectory);	
	}
}