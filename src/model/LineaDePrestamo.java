package model;

import java.util.Date;

public class LineaDePrestamo {
	private int idLineaDePrestamo;
	private Date fechaDevolucion;
	private Boolean devuelto;
	private Ejemplar ejemplar;
	private Prestamo prestamo;
	
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
	public Ejemplar getEjemplar() {
		return ejemplar;
	}
	public void setEjemplar(Ejemplar ejemplar) {
		this.ejemplar = ejemplar;
	}
	public Prestamo getPrestamo() {
		return prestamo;
	}
	public void setPrestamo(Prestamo prestamo) {
		this.prestamo = prestamo;
	}
}
