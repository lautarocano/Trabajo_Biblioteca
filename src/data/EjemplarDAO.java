package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Ejemplar;
import model.Libro;

public class EjemplarDAO  extends BaseDAO{
	
	public Ejemplar mapearEjemplar(ResultSet rs) throws SQLException {
		Ejemplar e = new Ejemplar();
		e.setId(rs.getInt("id_ejemplar"));
		LibroDAO lDAO = new LibroDAO();
		e.setLibro(lDAO.mapearLibro(rs));
		return e;
	}

	public void insert(Libro lib) throws SQLException{
		PreparedStatement pst = null;
		try {
			this.openConnection();
			for (int i=1;i<=lib.getCantEjemplares();i++)
			{
				pst = conn.prepareStatement("INSERT INTO ejemplares(id_libro) VALUES(?)");
				pst.setInt(1, lib.getId());
				pst.executeUpdate();
				pst.close();
			}
		}
		catch (SQLException e){
			e.printStackTrace();
			throw e;
		}
		finally {
			this.closeConnection(pst);
		}
	}
	
	public void insert(Ejemplar eje) throws SQLException {
		PreparedStatement pst = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("INSERT INTO ejemplares(id_libro) VALUES(?)");
			pst.setInt(1, eje.getLibro().getId());
			pst.executeUpdate();
			pst.close();
		}
		catch (SQLException e){
			e.printStackTrace();
			throw e;
		}
		finally {
			this.closeConnection(pst);
		}
	}
	
	public void delete(Ejemplar eje) throws SQLException {
		PreparedStatement pst = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("DELETE FROM ejemplares WHERE id_ejemplar =?");
			pst.setInt(1, eje.getId());
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
	
	public ArrayList<Ejemplar> getAll() throws SQLException {
		ArrayList<Ejemplar> ejemplares = new ArrayList<Ejemplar>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("SELECT e.id_ejemplar,l.*,g.descripcion FROM ejemplares e INNER JOIN libros l "
					+ "ON e.id_libro=l.id_libro INNER JOIN generos g ON l.id_genero = g.id_genero");
			rs = pst.executeQuery();
			while (rs.next()) {
				ejemplares.add(mapearEjemplar(rs));
			}
		}
		catch (SQLException e){
			e.printStackTrace();
			throw e;
		}
		finally {
			this.closeConnection(pst, rs);
		}
		return ejemplares;
	}
	
	public Ejemplar getOne(int idEjemplar) throws SQLException {
		Ejemplar ejemplar = new Ejemplar();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("SELECT e.id_ejemplar,l.*,g.descripcion FROM ejemplares e INNER JOIN libros l "
					+ "ON e.id_libro=l.id_libro INNER JOIN generos g ON l.id_genero = g.id_genero "
					+ "WHERE e.id_ejemplar = ?");
			pst.setInt(1, idEjemplar);
			rs = pst.executeQuery();
			if (rs.next()) {
				ejemplar = mapearEjemplar(rs);
			}
		}
		catch (SQLException e){
			e.printStackTrace();
			throw e;
		}
		finally {
			this.closeConnection(pst, rs);
		}
		return ejemplar;
	}
	
	public ArrayList<Ejemplar> getAllByLibro(int idLibro) throws SQLException {
		ArrayList<Ejemplar> ejemplares = new ArrayList<Ejemplar>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("SELECT * FROM ejemplares WHERE id_libro =?");
			pst.setInt(1, idLibro);
			rs = pst.executeQuery();
			while (rs.next()) {
				Ejemplar e = new Ejemplar();
				e.setId(rs.getInt("id_ejemplar"));
				ejemplares.add(e);
			}
		}
		catch (SQLException e){
			e.printStackTrace();
			throw e;
		}
		finally {
			this.closeConnection(pst, rs);
		}
		return ejemplares;
	}
	
	public void deleteLibro(Libro lib) throws SQLException {
		PreparedStatement pst = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("DELETE FROM ejemplares WHERE id_libro =?");
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
	
	public ArrayList<Ejemplar> getAllDisponibles(Libro lib) throws SQLException {
		ArrayList<Ejemplar> disponibles = new ArrayList<Ejemplar>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("SELECT e.id_ejemplar,l.*,g.descripcion FROM ejemplares e "
					+ "INNER JOIN libros l ON e.id_libro=l.id_libro "
					+ "INNER JOIN generos g ON l.id_genero = g.id_genero "
					+ "LEFT JOIN lineasdeprestamo lp ON lp.id_ejemplar = e.id_ejemplar "
					+ "WHERE e.id_libro = ? "
					+ "GROUP BY e.id_ejemplar "
					+ "HAVING (sum(if(ifnull(lp.devuelto, 1), 0, 1)) = 0)");
			pst.setInt(1, lib.getId());
			rs = pst.executeQuery();
			while (rs.next()) {
				disponibles.add(mapearEjemplar(rs));
			}
		}
		catch (SQLException e){
			e.printStackTrace();
			throw e;
		}
		finally {
			this.closeConnection(pst, rs);
		}
		return disponibles;
	}
}