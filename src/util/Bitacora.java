package util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class Bitacora {

    private static Logger LOGGER;
    
    private static Logger getBitacora() {
    	if (LOGGER == null) {
    		LOGGER = Logger.getLogger("bitacora");
    		try {
                Handler consoleHandler = new ConsoleHandler();
                FileHandler fileHandler = new FileHandler("C:/Users/Danisa/repositories/Trabajo_Biblioteca/resources/bitacora.txt", true);
                SimpleFormatter simpleFormatter = new SimpleFormatter();
                fileHandler.setFormatter(simpleFormatter);
                LOGGER.addHandler(consoleHandler);
                LOGGER.addHandler(fileHandler);
                consoleHandler.setLevel(Level.ALL);
                fileHandler.setLevel(Level.ALL);
                LOGGER.log(Level.INFO, "Bitacora inicializada");
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, "Error de IO");
            } catch (SecurityException ex) {
                LOGGER.log(Level.SEVERE, "Error de Seguridad");
            }
    	}
    	return LOGGER;
    }
    
    public static void log(java.util.logging.Level level, String mensaje) {
    	getBitacora().log(level, mensaje);
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
