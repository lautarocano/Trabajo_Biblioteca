package model;

import java.util.Date;

public class PoliticaPrestamo extends Entidad {
	private int _cantMaxLibrosPend;
	private int _diasPrestamo;
	private Date _fechaPoliticaPrestamo;

	public int getCantMaxLibrosPend() {
		return _cantMaxLibrosPend;
	}

	public void setCantMaxLibrosPend(int cantMaxLibrosPend) {
		this._cantMaxLibrosPend = cantMaxLibrosPend;
	}

	public Date getFechaPoliticaPrestamo() {
		return _fechaPoliticaPrestamo;
	}

	public void setFechaPoliticaPrestamo(Date fechaPoliticaPrestamo) {
		this._fechaPoliticaPrestamo = fechaPoliticaPrestamo;
	}

	public int getDiasPrestamo() {
		return _diasPrestamo;
	}

	public void setDiasPrestamo(int diasPrestamo) {
		this._diasPrestamo = diasPrestamo;
	}
}