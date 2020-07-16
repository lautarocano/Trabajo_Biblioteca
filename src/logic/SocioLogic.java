package logic;

import java.sql.SQLException;
import java.util.ArrayList;
import data.SocioDAO;
import model.Socio;

public class SocioLogic {
	private SocioDAO _SocioDAO;
	
	public SocioLogic() {
		this._SocioDAO = new SocioDAO();
	}
	
	public ArrayList<Socio> getAll() throws SQLException{
		try {
			return this._SocioDAO.getAll();
		}
		catch (SQLException exception){
			throw exception;
		}
	}
	
	public Socio getOne(int id) throws SQLException{
		try {
			return this._SocioDAO.getOne(id);
		}
		catch (SQLException exception){
			throw exception;
		}
	}
	
	public void insert(Socio socio) throws SQLException{
		try {
			if (!this.dniAlreadyExists(socio.getDni())) 
				this._SocioDAO.insert(socio);
		}
		catch (SQLException exception){
			throw exception;
		}
	}
	//Considerar agregar validaciones de campos en un futuro (Ejemplo: mail válido)
	public void update(Socio socio) throws SQLException{
		try {
			if (!this.dniAlreadyExists(socio.getDni())) 
				this._SocioDAO.update(socio);
		}
		catch (SQLException exception){
			throw exception;
		}
	}
	
	public void delete(Socio socio) throws SQLException{
		try {
			this._SocioDAO.delete(socio);
		}
		catch (SQLException exception){
			throw exception;
		}
	}
	
	public boolean dniAlreadyExists(int dni) throws SQLException{
		try {
			return this._SocioDAO.dniAlreadyExists(dni);
		}
		catch (SQLException exception){
			throw exception;
		}
	}
}