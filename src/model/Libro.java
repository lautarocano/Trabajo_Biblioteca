package model;

import java.util.Date;

public class Libro {
private int idLibro;
private String autor;
private String titulo;
private String nroEdicion;
private Date fechaEdicion;
private int cantDiasMaxPrestamo;
public int getIdLibro() {
	return idLibro;
}
public void setIdLibro(int idLibro) {
	this.idLibro = idLibro;
}
public String getAutor() {
	return autor;
}
public void setAutor(String autor) {
	this.autor = autor;
}
public String getTitulo() {
	return titulo;
}
public void setTitulo(String titulo) {
	this.titulo = titulo;
}
public String getNroEdicion() {
	return nroEdicion;
}
public void setNroEdicion(String nroEdicion) {
	this.nroEdicion = nroEdicion;
}
public Date getFechaEdicion() {
	return fechaEdicion;
}
public void setFechaEdicion(Date fechaEdicion) {
	this.fechaEdicion = fechaEdicion;
}
public int getCantDiasMaxPrestamo() {
	return cantDiasMaxPrestamo;
}
public void setCantDiasMaxPrestamo(int cantDiasMaxPrestamo) {
	this.cantDiasMaxPrestamo = cantDiasMaxPrestamo;
}


}
