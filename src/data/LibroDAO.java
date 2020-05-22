package data;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Libro;

public class LibroDAO extends BaseDAO implements IBaseDAO<Libro>{
	
	public Libro mapearLibro(ResultSet rs) throws SQLException {
		Libro l = new Libro();
		l.setId(rs.getInt("id_libro"));
		l.setAutor(rs.getString("autor"));
		l.setTitulo(rs.getString("titulo"));
		l.setNroEdicion(rs.getString("nro_edicion"));
		l.setFechaEdicion(rs.getDate("fecha_edicion"));
		l.setCantDiasMaxPrestamo(rs.getInt("cant_dias_max"));
		GeneroDAO gDAO = new GeneroDAO();
		l.setGenero(gDAO.mapearGenero(rs));
		
		return l;
	}
	
	public ArrayList<Libro> getAll() throws SQLException {
		ArrayList<Libro> libros = new ArrayList<Libro>();
		Statement stm = null;
		ResultSet rs = null;
		try {
			this.openConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery("SELECT l.*, g.descripcion FROM libros l "
					+ "INNER JOIN generos g ON l.id_genero = g.id_genero");
			while (rs.next()) {
				libros.add(this.mapearLibro(rs));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			this.closeConnection(stm, rs);
		}
		return libros;
	}
	
	public Libro getOne(int id) throws SQLException {
		Libro lib = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("SELECT l.*, g.descripcion FROM libros l "
					+ "INNER JOIN generos g ON l.id_genero = g.id_genero WHERE id_libro = ?");
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if (rs.next()) {
				lib = this.mapearLibro(rs);
			}
		}
		catch (SQLException e){
			e.printStackTrace();
			throw e;
		}
		finally {
			this.closeConnection(pst, rs);
		}
		return lib;
	}
	
	public void insert(Libro lib) throws SQLException {
		PreparedStatement pst = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("INSERT INTO libros(id_libro,autor,titulo,nro_edicion,fecha_edicion,"
					+ "cant_dias_max,id_genero) VALUES(?,?,?,?,?,?,?)");
			pst.setInt(1, lib.getId());
			pst.setString(2, lib.getAutor());
			pst.setString(3, lib.getTitulo());
			pst.setString(4, lib.getNroEdicion());
			pst.setDate(5, (Date) lib.getFechaEdicion());
			pst.setInt(6, lib.getCantDiasMaxPrestamo());
			pst.setInt(7,lib.getGenero().getId());
			pst.executeUpdate();
		}
		catch (SQLException e){
			e.printStackTrace();
			throw e;
		}
		finally {
			this.closeConnection(pst);
		}
	}
	
	public void update(Libro lib) throws SQLException {
		PreparedStatement pst = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("UPDATE libros SET autor= ?,titulo=?,nro_edicion=?,fecha_edicion=?, "
					+ "cant_dias_max=?,id_genero=? WHERE id_libro = ?");
			pst.setString(1, lib.getAutor());
			pst.setString(2, lib.getTitulo());
			pst.setString(3, lib.getNroEdicion());
			pst.setDate(4, (Date) lib.getFechaEdicion());
			pst.setInt(5, lib.getCantDiasMaxPrestamo());
			pst.setInt(6,lib.getGenero().getId());
			pst.setInt(7, lib.getId());
			pst.executeUpdate();
		}
		catch (SQLException e){
			e.printStackTrace();
			throw e;
		}
		finally {
			this.closeConnection(pst);
		}
	}
	
	public void delete(Libro lib) throws SQLException {
		PreparedStatement pst = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("DELETE FROM libros WHERE id_libro = ?");
			pst.setInt(1, lib.getId());
			pst.executeUpdate();
		}
		catch (SQLException e){
			e.printStackTrace();
			throw e;
		}
		finally {
			this.closeConnection(pst);
		}
	}
}