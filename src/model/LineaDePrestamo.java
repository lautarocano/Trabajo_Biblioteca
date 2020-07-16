package model;

import java.util.Date;

public class LineaDePrestamo extends Entidad {
	private Date _fechaDevolucion;
	private Boolean _devuelto;
	private Ejemplar _ejemplar;

	public Date getFechaDevolucion() {
		return _fechaDevolucion;
	}
	public void setFechaDevolucion(Date fechaDevolucion) {
		this._fechaDevolucion = fechaDevolucion;
	}
	public Boolean getDevuelto() {
		return _devuelto;
	}
	public void setDevuelto(Boolean devuelto) {
		this._devuelto = devuelto;
	}
	public Ejemplar getEjemplar() {
		return _ejemplar;
	}
	public void setEjemplar(Ejemplar ejemplar) {
		this._ejemplar = ejemplar;
	}
}