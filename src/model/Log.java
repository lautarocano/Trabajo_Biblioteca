package model;

import java.util.Date;

public class Log extends Entidad {

	private String _level;
	private String _stack;
	private Date _fecha;

	public String getLevel() {
		return _level;
	}
	public void setLevel(String level) {
		this._level = level;
	}
	
	public String getStack() {
		return _stack;
	}
	public void setStack(String stack) {
		this._stack = stack;
	}
	
	public Date getFecha() {
		return _fecha;
	}
	public void setFecha(Date fecha) {
		this._fecha = fecha;
	}
	
}
