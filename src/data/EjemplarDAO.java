package data;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.Ejemplar;
import model.Libro;

public class EjemplarDAO  extends BaseDAO{

	public void insert(Libro lib) throws SQLException {
		PreparedStatement pst = null;
		try {
			this.openConnection();
			for (int i=0;i<=lib.getCantEjemplares();i++)
			{
				pst = conn.prepareStatement("INSERT INTO ejemplares(id_libro) VALUES(?)");
				pst.setInt(1, lib.getId());
				pst.executeUpdate();
				pst.close();
			}
		}
		catch (SQLException e){
			e.printStackTrace();
			throw e;
		}
		finally {
			this.closeConnection(pst);
		}
	}
	
	public void delete(Ejemplar eje) throws SQLException {
		PreparedStatement pst = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("DELETE FROM ejemplares WHERE id_ejemplar =?");
			pst.setInt(1, eje.getId());
			pst.executeUpdate();
		}
		catch (SQLException e){
			e.printStackTrace();
			throw e;
		}
		finally {
			this.closeConnection(pst);
		}
	}
}
