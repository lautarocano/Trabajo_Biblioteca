package model;

import java.util.Date;

public class Libro extends Entidad {
	private String _autor;
	private String _titulo;
	private String _nroEdicion;
	private Date _fechaEdicion;
	private int _cantDiasMaxPrestamo;
	private Genero _genero;
	private int _cantidadEjemplares;

	public String getAutor() {
		return _autor;
	}
	public void setAutor(String autor) {
		this._autor = autor;
	}
	public String getTitulo() {
		return _titulo;
	}
	public void setTitulo(String titulo) {
		this._titulo = titulo;
	}
	public String getNroEdicion() {
		return _nroEdicion;
	}
	public void setNroEdicion(String nroEdicion) {
		this._nroEdicion = nroEdicion;
	}
	public Date getFechaEdicion() {
		return _fechaEdicion;
	}
	public void setFechaEdicion(Date fechaEdicion) {
		this._fechaEdicion = fechaEdicion;
	}
	public int getCantDiasMaxPrestamo() {
		return _cantDiasMaxPrestamo;
	}
	public void setCantDiasMaxPrestamo(int cantDiasMaxPrestamo) {
		this._cantDiasMaxPrestamo = cantDiasMaxPrestamo;
	}
	public Genero getGenero() {
		return _genero;
	}
	public void setGenero(Genero genero) {
		this._genero = genero;
	}
	public int getCantEjemplares() {
		return _cantidadEjemplares;
	}
	public void setCantEjemplares(int cantidadEjemplares) {
		this._cantidadEjemplares = cantidadEjemplares;
	}
}