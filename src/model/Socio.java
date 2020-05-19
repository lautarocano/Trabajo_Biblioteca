package model;

public class Socio extends Entidad {
	private String _nombre;
	private String _apellido;
	private String _email;
	private String _domicilio;
	private String _telefono;
	private int _dni;
	private Boolean _estado;
	private Usuario _usuario;

	public String getNombre() {
		return _nombre;
	}
	public void setNombre(String nombre) {
		this._nombre = nombre;
	}
	public String getApellido() {
		return _apellido;
	}
	public void setApellido(String apellido) {
		this._apellido = apellido;
	}
	public String getEmail() {
		return _email;
	}
	public void setEmail(String email) {
		this._email = email;
	}
	public String getDomicilio() {
		return _domicilio;
	}
	public void setDomicilio(String domicilio) {
		this._domicilio = domicilio;
	}
	public String getTelefono() {
		return _telefono;
	}
	public void setTelefono(String telefono) {
		this._telefono = telefono;
	}
	public int getDni() {
		return _dni;
	}
	public void setDni(int dni) {
		this._dni = dni;
	}
	public Boolean getEstado() {
		return _estado;
	}
	public void setEstado(Boolean estado) {
		this._estado = estado;
	}
	public Usuario getUsuario() {
		return _usuario;
	}
	public void setUsuario(Usuario usuario) {
		this._usuario = usuario;
	}
}