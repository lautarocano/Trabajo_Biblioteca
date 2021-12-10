package model;

public class PoliticaSancion extends Entidad {
	private int _diasDeAtrasoDesde;
	private int _diasDeAtrasoHasta;
	private int _diasDeSancion;
	
	public int getDiasDeAtrasoDesde() {
		return _diasDeAtrasoDesde;
	}
	public void setDiasDeAtrasoDesde(int diasDeAtrasoDesde) {
		this._diasDeAtrasoDesde = diasDeAtrasoDesde;
	}
	public int getDiasDeAtrasoHasta() {
		return _diasDeAtrasoHasta;
	}
	public void setDiasDeAtrasoHasta(int diasDeAtrasoHasta) {
		this._diasDeAtrasoHasta = diasDeAtrasoHasta;
	}
	public int getDiasDeSancion() {
		return _diasDeSancion;
	}
	public void setDiasDeSancion(int diasDeSancion) {
		this._diasDeSancion = diasDeSancion;
	}	
}