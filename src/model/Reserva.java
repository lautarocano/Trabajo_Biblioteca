package model;

import java.util.Date;

public class Reserva {
	private int idReserva;
	private Date fechaReserva;
	private boolean entregada;
	
	public int getIdReserva() {
		return idReserva;
	}
	public void setIdReserva(int idReserva) {
		this.idReserva = idReserva;
	}
	public Date getFechaReserva() {
		return fechaReserva;
	}
	public void setFechaReserva(Date fechaReserva) {
		this.fechaReserva = fechaReserva;
	}
	public boolean isEntregada() {
		return entregada;
	}
	public void setEntregada(boolean entregada) {
		this.entregada = entregada;
	}
}
