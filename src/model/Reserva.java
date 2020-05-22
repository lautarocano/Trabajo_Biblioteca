package model;

import java.util.ArrayList;
import java.util.Date;

public class Reserva extends Entidad {
	private Date _fechaReserva;
	private boolean _entregada;
	private Socio _socio;
	private ArrayList <Libro> _libros;
	
	public Date getFechaReserva() {
		return _fechaReserva;
	}
	public void setFechaReserva(Date fechaReserva) {
		this._fechaReserva = fechaReserva;
	}
	public boolean isEntregada() {
		return _entregada;
	}
	public void setEntregada(boolean entregada) {
		this._entregada = entregada;
	}
	public Socio getSocio() {
		return _socio;
	}
	public void setSocio(Socio socio) {
		this._socio = socio;
	}
	public ArrayList <Libro> getLibros() {
		return _libros;
	}
	public void setLibros(ArrayList <Libro> libros) {
		this._libros = libros;
	}
}