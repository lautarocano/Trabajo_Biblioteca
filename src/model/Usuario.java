package model;

public class Usuario extends Entidad {
	private String _nombreUsuario;
	private String _password;
	private Boolean _estado;
	private tipoUsuario _tipo;
	
	public enum tipoUsuario{
		Socio,
		Bibliotecario,
		Administrador;
	}
	
	public String getNombreUsuario() {
		return _nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this._nombreUsuario = nombreUsuario;
	}
	public String getPassword() {
		return _password;
	}
	public void setPassword(String password) {
		this._password = password;
	}
	public Boolean getEstado() {
		return _estado;
	}
	public void setEstado(Boolean estado) {
		this._estado = estado;
	}
	public tipoUsuario getTipo() {
		return _tipo;
	}
	public void setTipo(tipoUsuario tipo) {
		this._tipo = tipo;
	}	
}