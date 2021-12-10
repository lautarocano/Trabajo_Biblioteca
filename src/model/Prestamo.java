package model;

import java.util.ArrayList;
import java.util.Date;

public class Prestamo extends Entidad {
	private Date _fechaPrestamo;
	private int _diasPrestamo;
	private estadoPrestamo _estado;
	private Socio _socio;
	private ArrayList<LineaDePrestamo> _lineasPrestamo;
	
	public enum estadoPrestamo{
		EnCurso,
		Atrasado,
		Finalizado;
	}
	
	public Date getFechaPrestamo() {
		return _fechaPrestamo;
	}
	public void setFechaPrestamo(Date fechaPrestamo) {
		this._fechaPrestamo = fechaPrestamo;
	}
	public int getDiasPrestamo() {
		return _diasPrestamo;
	}
	public void setDiasPrestamo(int diasPrestamo) {
		this._diasPrestamo = diasPrestamo;
	}
	public estadoPrestamo getEstado() {
		return _estado;
	}
	public void setEstado(estadoPrestamo estado) {
		this._estado = estado;
	}
	public Socio getSocio() {
		return _socio;
	}
	public void setSocio(Socio socio) {
		this._socio = socio;
	}
	public ArrayList<LineaDePrestamo> getLineasPrestamo() {
		return _lineasPrestamo;
	}
	public void setLineasPrestamo(ArrayList<LineaDePrestamo> lineasPrestamo) {
		this._lineasPrestamo = lineasPrestamo;
	}
}