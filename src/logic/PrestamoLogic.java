package logic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import data.PoliticaSancionDAO;
import data.PrestamoDAO;
import data.SancionDAO;
import model.PoliticaSancion;
import model.Prestamo;
import model.Sancion;
import model.Socio;

public class PrestamoLogic {

private PrestamoDAO _PrestamoDAO;
	
	public PrestamoLogic() {
		this._PrestamoDAO = new PrestamoDAO();
	}
	
	public void insert(Prestamo pres) throws SQLException {
		try {
			this._PrestamoDAO.insert(pres);
	}
	catch (SQLException exception){
		throw exception;
	}
		
	}
	
	public void update(Prestamo pres) throws SQLException {
		
		try {
			this._PrestamoDAO.update(pres);
	}
	catch (SQLException exception){
		throw exception;
	}
	}
	
	public void delete(Prestamo pres) throws SQLException {
		try {
			this._PrestamoDAO.delete(pres);
	}
	catch (SQLException exception){
		throw exception;
	}
		
	}
	
	public ArrayList<Prestamo> getAll() throws SQLException {
		
		try {
			return this._PrestamoDAO.getAll();
		}
		catch (SQLException exception){
			throw exception;
		}
	}
	
	public ArrayList<Prestamo> getAllBySocio(Socio socio) throws SQLException {
		try {
			return this._PrestamoDAO.getAllBySocio(socio);
		}
		catch (SQLException exception){
			throw exception;
		}
		
	}
	
	
	public Prestamo getOne(int id) throws SQLException {
		try {
			return this._PrestamoDAO.getOne(id);
		}
		catch (SQLException exception){
			throw exception;
		}
		
		
	}
	
	public int getCantidadPrestamosPendientes(int id_socio) throws SQLException {
		try {
			return this._PrestamoDAO.getCantidadPrestamosPendientes(id_socio);
		}
		catch (SQLException exception){
			throw exception;
		}
		
		
	}
	
	/*Verifico si el usuario esta o no sancionado*/
	public Boolean isSancionado(Prestamo pres) throws SQLException {
		try {
			return this._PrestamoDAO.isSancionado(pres);
		}
		catch (SQLException exception){
			throw exception;
		}
		
	}
	
	/* verifico si excedio el limite de libros pendientes*/
	public Boolean isLimiteExcedido(Prestamo pres) throws SQLException {
		
		try {
			return this._PrestamoDAO.isLimiteExcedido(pres);
		}
		catch (SQLException exception){
			throw exception;
		}
		
	}

	/*Verifico si existe algun prestamo atrasado*/
	public Boolean isPrestamoAtrasado(Prestamo pres) throws SQLException {
		
		try {
			return this._PrestamoDAO.isPrestamoAtrasado(pres);
		}
		catch (SQLException exception){
			throw exception;
		}
	}

		/* FALTARIA ACA "VERIFICAR LA DISPONIBILIDAD DE LIBROS" */
	
	public Boolean isEntregadoATiempo(Prestamo pres) throws SQLException {
		try {
			
			PoliticaSancionDAO _PoliticaSancionDAO=new PoliticaSancionDAO();
			PoliticaSancion politicaSancion=_PoliticaSancionDAO.getOneByDiferencia(pres);
			if(politicaSancion!=null) {
				SancionDAO _SancionDAO=new SancionDAO();
				Sancion sancion=new Sancion();
				sancion.setDiasSancion(politicaSancion.getDiasDeSancion());
				sancion.setFechaSancion(Calendar.getInstance().getTime());
				sancion.setSocio(pres.getSocio());
				_SancionDAO.insert(sancion);
				return true;
			}
			
			else {
				return false;
			}
			
		
		}
		catch (SQLException exception){
			throw exception;
		}
		
	}







	
	
	
}
