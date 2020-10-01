package model;

import java.util.Date;

public class PoliticaPrestamo extends Entidad {
	private int _cantMaxLibrosPend;
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

	public void setFechaPoliticaPrestamo(Date _fechaPoliticaPrestamo) {
		this._fechaPoliticaPrestamo = _fechaPoliticaPrestamo;
	}
}