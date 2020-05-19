package model;

import java.util.Date;

public class Reserva extends Entidad {
	private Date _fechaReserva;
	private boolean _entregada;
	private Socio _socio;
	
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
}