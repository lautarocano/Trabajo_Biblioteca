package logic;
import java.sql.SQLException;
import java.util.logging.Level;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import util.Bitacora;

public class UpdateLoansJob implements Job {

	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		PrestamoLogic pl = new PrestamoLogic();
		try {
			pl.updateLoans();
			System.out.println("El estado de los préstamos ha sido actualizado en la base de datos.");
		} catch (SQLException e) {
			Bitacora.log(Level.SEVERE, Bitacora.getStackTrace(e));
		}
	}

}