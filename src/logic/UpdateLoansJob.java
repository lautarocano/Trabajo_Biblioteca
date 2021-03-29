package logic;
import java.sql.SQLException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class UpdateLoansJob implements Job {

	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		PrestamoLogic pl = new PrestamoLogic();
		try {
			pl.updateLoans();
			System.out.println("El estado de los préstamos ha sido actualizado en la base de datos.");
		} catch (SQLException e) {
			// Hay que ver como informar el error
			e.printStackTrace();
		}
	}

}