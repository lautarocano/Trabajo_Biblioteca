package model;

import java.util.Date;

public class Prestamo {
	private int idPrestamo;
	private Date fechaPrestamo;
	private int diasPrestamo;
	private String estado;
	private Socio socio;
	
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
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Socio getSocio() {
		return socio;
	}
	public void setSocio(Socio socio) {
		this.socio = socio;
	}
}
