package model;

import java.util.Date;

public class Sancion extends Entidad {
	private Date _fechaSancion;
	private int _diasSancion;
	private Socio _socio;
	
	public Date getFechaSancion() {
		return _fechaSancion;
	}
	public void setFechaSancion(Date fechaSancion) {
		this._fechaSancion = fechaSancion;
	}
	public int getDiasSancion() {
		return _diasSancion;
	}
	public void setDiasSancion(int diasSancion) {
		this._diasSancion = diasSancion;
	}
	public Socio getSocio() {
		return _socio;
	}
	public void setSocio(Socio socio) {
		this._socio = socio;
	}
}