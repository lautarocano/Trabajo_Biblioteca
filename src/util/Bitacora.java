package util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import data.LogsDAO;
import model.Log;

public class Bitacora {

    private static Logger LOGGER;
    
    private static Logger getBitacora(String rootDirectory) {
    	if (LOGGER == null) {
    		LOGGER = Logger.getLogger("bitacora");
    		try {
                Path path = Paths.get(rootDirectory + "/WEB-INF/bitacora.txt");
                FileHandler fileHandler = new FileHandler(path.toString(), true);
                SimpleFormatter simpleFormatter = new SimpleFormatter();
                fileHandler.setFormatter(simpleFormatter);
                LOGGER.addHandler(fileHandler);
                fileHandler.setLevel(Level.ALL);
                LOGGER.setLevel(Level.ALL);
                LOGGER.log(Level.INFO, "Bitacora inicializada");
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, "Error de IO");
            } catch (SecurityException ex) {
                LOGGER.log(Level.SEVERE, "Error de Seguridad");
            }
    	}
    	return LOGGER;
    }
    
    public static void log(java.util.logging.Level level, String mensaje, String rootDirectory) {
    	getBitacora(rootDirectory).log(level, mensaje);
    	log(level,mensaje);
    }

    
    public static void log(java.util.logging.Level level, String mensaje) {

    	LogsDAO logsDAO=new LogsDAO();
    	Log log=new Log();
    	log.setLevel(level.toString());
    	log.setStack(mensaje);
    	log.setFecha(Calendar. getInstance(). getTime());
    	try {
			logsDAO.insert(log);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
    /**
     * Esta funcion nos permite convertir el stackTrace en un String, necesario
     * para poder imprimirlos al log
     *
     * @param e Excepcion de la que queremos el StackTrace
     * @return StackTrace de la excepcion en forma de String
     */
    public static String getStackTrace(Exception e) {
        StringWriter sWriter = new StringWriter();
        PrintWriter pWriter = new PrintWriter(sWriter);
        e.printStackTrace(pWriter);
        return sWriter.toString();
    }
}
