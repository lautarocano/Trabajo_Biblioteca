package model;

public class Usuario {
private int idUsuario;
private String nombreUsuario;
private String password;
private Boolean estado;
private tipoUsuario tipo;

public enum tipoUsuario{
	Socio,
	Bibliotecario,
	Administrador;
}

public int getIdUsuario() {
	return idUsuario;
}
public void setIdUsuario(int idUsuario) {
	this.idUsuario = idUsuario;
}
public String getNombreUsuario() {
	return nombreUsuario;
}
public void setNombreUsuario(String nombreUsuario) {
	this.nombreUsuario = nombreUsuario;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public Boolean getEstado() {
	return estado;
}
public void setEstado(Boolean estado) {
	this.estado = estado;
}
public tipoUsuario getTipo() {
	return tipo;
}
public void setTipo(tipoUsuario tipo) {
	this.tipo = tipo;
}	
}
