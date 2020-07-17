package logic;

import java.sql.SQLException;
import java.util.ArrayList;

import data.PoliticaSancionDAO;
import model.PoliticaSancion;
import model.Prestamo;

public class PoliticaSancionLogic {

private PoliticaSancionDAO _PoliticaSancionDAO;
	
	public PoliticaSancionLogic() {
		this._PoliticaSancionDAO = new PoliticaSancionDAO();
	}
	
	public ArrayList<PoliticaSancion> getAll() throws SQLException {
		
		try {
			return this._PoliticaSancionDAO.getAll();
		}
		catch (SQLException exception){
			throw exception;
		}
	}

	public PoliticaSancion getOne(int id) throws SQLException {
		try {
			return this._PoliticaSancionDAO.getOne(id);
		}
		catch (SQLException exception){
			throw exception;
		}
		
	}
	
	public PoliticaSancion getOneByDiasAtraso(int dias_atraso) throws SQLException {
		
		try {
			return this._PoliticaSancionDAO.getOneByDiasAtraso(dias_atraso);
		}
		catch (SQLException exception){
			throw exception;
		}
	}
	
	public void insert(PoliticaSancion ps) throws SQLException {
		
		try {
			this._PoliticaSancionDAO.insert(ps);
	}
	catch (SQLException exception){
		throw exception;
	}
	
	}
	
	public void update(PoliticaSancion ps) throws SQLException {
		
		try {
			this._PoliticaSancionDAO.update(ps);
	}
	catch (SQLException exception){
		throw exception;
	}
	}
	
	public void delete(PoliticaSancion ps) throws SQLException {
		try {
			this._PoliticaSancionDAO.delete(ps);
	}
	catch (SQLException exception){
		throw exception;
	}
		
	}
	
	public PoliticaSancion getOneByDiferencia(Prestamo pres) throws SQLException {
		try {
			return this._PoliticaSancionDAO.getOneByDiferencia(pres);
		}
		catch (SQLException exception){
			throw exception;
		}
		
	}






	
}
