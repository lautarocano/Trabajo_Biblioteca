package model;

public class Ejemplar extends Entidad {
	private Libro _libro;

	public Libro getLibro() {
		return _libro;
	}

	public void setLibro(Libro libro) {
		this._libro = libro;
	}
}