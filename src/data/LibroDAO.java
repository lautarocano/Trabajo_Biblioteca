package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.time.LocalDate;

import model.Genero;
import model.Libro;

public class LibroDAO extends BaseDAO implements IBaseDAO<Libro>{
	
	public Libro mapearLibro(ResultSet rs) throws SQLException {
		Libro l = new Libro();
		l.setId(rs.getInt("id_libro"));
		l.setAutor(rs.getString("autor"));
		l.setTitulo(rs.getString("titulo"));
		l.setNroEdicion(rs.getString("nro_edicion"));
		l.setFechaEdicion(rs.getDate("fecha_edicion").toLocalDate());
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
	
	public ArrayList<Libro> getAllByGenero(Genero genero) throws SQLException {
		ArrayList<Libro> libros = new ArrayList<Libro>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("SELECT l.*, g.descripcion FROM libros l "
					+ "INNER JOIN generos g ON l.id_genero = g.id_genero where l.id_genero=?");
			pst.setInt(1, genero.getId());
			rs=pst.executeQuery();
			
			while (rs.next()) {
				libros.add(this.mapearLibro(rs));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			this.closeConnection(pst, rs);
		}
		return libros;
	}
	
	public ArrayList<Libro> getAllByTitulo(String titulo) throws SQLException {
		ArrayList<Libro> libros = new ArrayList<Libro>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("SELECT l.*, g.descripcion FROM libros l "
					+ "INNER JOIN generos g ON l.id_genero = g.id_genero where l.titulo LIKE ?");
			pst.setString(1, "%"+titulo+"%");
			rs=pst.executeQuery();
			while (rs.next()) {
				libros.add(this.mapearLibro(rs));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			this.closeConnection(pst, rs);
		}
		return libros;
	}
	
	public ArrayList<Libro> getAllByTituloAndGenero(String titulo, Genero genero) throws SQLException {
		ArrayList<Libro> libros = new ArrayList<Libro>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("SELECT l.*, g.descripcion FROM libros l "
					+ "INNER JOIN generos g ON l.id_genero = g.id_genero "
					+ "WHERE l.id_genero = ? AND l.titulo LIKE ?");
			pst.setInt(1, genero.getId());
			pst.setString(2, "%"+titulo+"%");
			rs=pst.executeQuery();
			while (rs.next()) {
				libros.add(this.mapearLibro(rs));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			this.closeConnection(pst, rs);
		}
		return libros;
	}
	
	public void insert(Libro lib) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("INSERT INTO libros(autor,titulo,nro_edicion,fecha_edicion,"
					+ " id_genero) VALUES(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, lib.getAutor());
			pst.setString(2, lib.getTitulo());
			pst.setString(3, lib.getNroEdicion());
			pst.setObject(4, lib.getFechaEdicion());
			pst.setInt(5, lib.getGenero().getId());
			pst.executeUpdate();
			rs = pst.getGeneratedKeys();
			if (rs.next()) {
				lib.setId(rs.getInt(1));			
			}
			rs.close();
			this.closeConnection(pst);
			agregarEjemplares(lib);
		}
		catch (SQLException e){
			e.printStackTrace();
			throw e;
		}
		finally {
			this.closeConnection(pst);
		}
	}
	
	public void agregarEjemplares(Libro lib) throws SQLException {
		try {
			EjemplarDAO eDAO=new EjemplarDAO();
			eDAO.insert(lib);
		}
		catch (SQLException e){
			e.printStackTrace();
			throw e;
		}
	}
	
	public void update(Libro lib) throws SQLException {
		PreparedStatement pst = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("UPDATE libros SET autor= ?,titulo=?,nro_edicion=?,fecha_edicion=?, "
					+ "id_genero=? WHERE id_libro = ?");
			pst.setString(1, lib.getAutor());
			pst.setString(2, lib.getTitulo());
			pst.setString(3, lib.getNroEdicion());
			pst.setObject(4, lib.getFechaEdicion());
			pst.setInt(5,lib.getGenero().getId());
			pst.setInt(6, lib.getId());
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
			this.eliminarEjemplares(lib);
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
	
	public void eliminarEjemplares(Libro lib) throws SQLException {
		try {
			EjemplarDAO eDAO=new EjemplarDAO();
			eDAO.deleteLibro(lib);
		}
		catch (SQLException e){
			e.printStackTrace();
			throw e;
		}
	}
	
	public ArrayList<LocalDate> getFechasDisponible(Libro lib, int cantMeses) throws SQLException {
		LocalDate date = LocalDate.now();
		ArrayList<LocalDate> fechas = new ArrayList<LocalDate>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		int ejemplaresDisponibles = 0;
		try {
			this.openConnection();
			while (date.isBefore(LocalDate.now().plusMonths(cantMeses))) {
				
				pst = conn.prepareStatement("SELECT e.id_ejemplar FROM prestamos p "
						+ "INNER JOIN lineasdeprestamo lp ON p.id_prestamo = lp.id_prestamo "
						+ "RIGHT JOIN ejemplares e ON lp.id_ejemplar = e.id_ejemplar "
						+ "WHERE e.id_libro = ?  "
						+ "GROUP BY e.id_ejemplar  "
						+ "HAVING (sum(if((date_add(ifnull(p.fecha_prestamo, now()), INTERVAL ifnull(p.dias_prestamo, 0) DAY) >= ?) AND (ifnull(lp.devuelto, 1)=0), 1, 0))= 0)");
				pst.setInt(1, lib.getId());
				pst.setObject(2, date);
				rs=pst.executeQuery();
				while (rs.next()) 
				{
				  ejemplaresDisponibles += 1;
				}
				rs.close();
				pst.close();
				pst = conn.prepareStatement("SELECT count(lr.id_libro) AS cantreservas FROM reservas r "
						+ "INNER JOIN libro_reserva lr ON lr.id_reserva = r.id_reserva "
						+ "WHERE lr.id_libro = ? AND ? BETWEEN r.fecha_reserva AND date_add(r.fecha_reserva, INTERVAL (select cant_dias_prestamo from politicaprestamo where fecha_politica_prestamo in ( select max(fecha_politica_prestamo) from politicaprestamo)) DAY) ");
				pst.setInt(1, lib.getId());
				pst.setObject(2, date);
				rs=pst.executeQuery();
				if (rs.next()) 
					ejemplaresDisponibles -= rs.getInt("cantreservas");
				if (ejemplaresDisponibles > 0) {
					fechas.add(date);
					System.out.print(date);
					System.out.print(" disponible");
				} 
				ejemplaresDisponibles = 0;
				date=date.plusDays(1);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			this.closeConnection(pst, rs);
			System.out.println("Fin");
		}
		return fechas;
	}
}