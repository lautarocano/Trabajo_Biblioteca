package model;

import java.time.LocalDate;

public class Libro extends Entidad {
	private String _autor;
	private String _titulo;
	private String _nroEdicion;
	private LocalDate _fechaEdicion;
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
	public LocalDate getFechaEdicion() {
		return _fechaEdicion;
	}
	public void setFechaEdicion(LocalDate fechaEdicion) {
		this._fechaEdicion = fechaEdicion;
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