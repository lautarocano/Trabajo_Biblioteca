package model;

import java.util.Date;

public class Prestamo {
	private int idPrestamo;
	private Date fechaPrestamo;
	private int diasPrestamo;
	private estadoPrestamo estado;
	
	public enum estadoPrestamo{
		EnCurso,
		Atrasado,
		Finalizado;
	}
	
	public int getIdPrestamo() {
		return idPrestamo;
	}
	public void setIdPrestamo(int idPrestamo) {
		this.idPrestamo = idPrestamo;
	}
	public Date getFechaPrestamo() {
		return fechaPrestamo;
	}
	public void setFechaPrestamo(Date fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}
	public int getDiasPrestamo() {
		return diasPrestamo;
	}
	public void setDiasPrestamo(int diasPrestamo) {
		this.diasPrestamo = diasPrestamo;
	}
	public estadoPrestamo getEstado() {
		return estado;
	}
	public void setEstado(estadoPrestamo estado) {
		this.estado = estado;
	}
}
