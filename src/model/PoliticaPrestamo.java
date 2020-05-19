package model;

public class PoliticaPrestamo extends Entidad {
	private int _cantMaxLibrosPend;

	public int getCantMaxLibrosPend() {
		return _cantMaxLibrosPend;
	}

	public void setCantMaxLibrosPend(int cantMaxLibrosPend) {
		this._cantMaxLibrosPend = cantMaxLibrosPend;
	}
}