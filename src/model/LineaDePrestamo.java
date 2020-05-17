package model;

import java.util.Date;

public class LineaDePrestamo {
	private int idLineaDePrestamo;
	private Date fechaDevolucion;
	private Boolean devuelto;
	
	public int getIdLineaDePrestamo() {
		return idLineaDePrestamo;
	}
	public void setIdLineaDePrestamo(int idLineaDePrestamo) {
		this.idLineaDePrestamo = idLineaDePrestamo;
	}
	public Date getFechaDevolucion() {
		return fechaDevolucion;
	}
	public void setFechaDevolucion(Date fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}
	public Boolean getDevuelto() {
		return devuelto;
	}
	public void setDevuelto(Boolean devuelto) {
		this.devuelto = devuelto;
	}
}
