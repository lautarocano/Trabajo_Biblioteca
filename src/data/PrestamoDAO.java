package data;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Prestamo;
import model.Socio;
import model.Ejemplar;
import model.LineaDePrestamo;

public class PrestamoDAO extends BaseDAO implements IBaseDAO<Prestamo> {
	
	public Prestamo mapearPrestamo(ResultSet rs, Boolean mapearSocio) throws SQLException {
		Prestamo p = new Prestamo();
		p.setId(rs.getInt("id_prestamo"));
		p.setFechaPrestamo(rs.getDate("fecha_prestamo"));
		p.setDiasPrestamo(rs.getInt("dias_prestamo"));
		switch(rs.getInt("estado")) {
		case 0:
			p.setEstado(Prestamo.estadoPrestamo.EnCurso);
			break;
		case 1:
			p.setEstado(Prestamo.estadoPrestamo.Atrasado);
			break;
		case 2:
			p.setEstado(Prestamo.estadoPrestamo.Finalizado);
			break;
		}
		if (mapearSocio) {
			SocioDAO sDAO = new SocioDAO();
			p.setSocio(sDAO.mapearSocio(rs));
		}
		return p;
	}
	
	public void insert(Prestamo pres) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("INSERT INTO prestamo(fecha_prestamo, "
					+ "dias_prestamo, estado, id_socio) VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			pst.setDate(1, (Date) pres.getFechaPrestamo());
			pst.setInt(2, pres.getDiasPrestamo());
			pst.setInt(3, pres.getSocio().getId());
			pst.setInt(4, 0);
			pst.executeUpdate();
			rs = pst.getGeneratedKeys();
			if (rs.next()) {
				pres.setId(rs.getInt(1));
			}
			rs.close();
			for (LineaDePrestamo lp: pres.getLineasPrestamo()) {
				this.insertLineaDePrestamo(lp, pres, pst);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			this.closeConnection(pst, rs);
		}
	}
	
	public void update(Prestamo pres) throws SQLException {
		PreparedStatement pst = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("UPDATE prestamo SET fecha_prestamo = ?, "
					+ "dias_prestamo = ?, estado = ?, id_socio = ? WHERE id_prestamo = ?");
			pst.setDate(1, (Date) pres.getFechaPrestamo());
			pst.setInt(2, pres.getDiasPrestamo());
			switch(pres.getEstado()) {
			case EnCurso:
				pst.setInt(3, 0);
				break;
			case Atrasado:
				pst.setInt(3, 1);
			case Finalizado:
				pst.setInt(3, 2);
				break;
			}
			pst.setInt(4, pres.getSocio().getId());
			pst.executeUpdate();
			for (LineaDePrestamo lp: pres.getLineasPrestamo()) {
				if (lp.getId() == 0) this.insertLineaDePrestamo(lp, pres, pst);
				else if (lp.getEjemplar() == null) this.deleteLineaDePrestamo(lp, pst);
				else this.updateLineaDePrestamo(lp, pres, pst);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			this.closeConnection(pst);
		}
	}
	
	public void delete(Prestamo pres) throws SQLException {
		PreparedStatement pst = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("DELETE FROM lineadeprestamo WHERE id_prestamo = ?");
			pst.setInt(1, pres.getId());
			pst.executeUpdate();
			pst.close();
			pst = conn.prepareStatement("DELETE FROM prestamo WHERE id_prestamo = ?");
			pst.setInt(1, pres.getId());
			pst.executeUpdate();
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			this.closeConnection(pst);
		}
	}
	
	public ArrayList<Prestamo> getAll() throws SQLException {
		ArrayList<Prestamo> prestamos = new ArrayList<Prestamo>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("SELECT p.id_prestamo, p.fecha_prestamo, p.dias_prestamo, "
					+ "p.estado, s.*, u.nombre_usuario, u.password, u.tipo, u.estado FROM prestamo p "
					+ "INNER JOIN socio s ON p.id_socio = s.id_socio "
					+ "INNER JOIN usuarios u ON s.id_usuario = u.id_usuario");
			rs = pst.executeQuery();
			while (rs.next()) {
				prestamos.add(this.mapearPrestamo(rs, true));
			}
			rs.close();
			pst.close();
			for(Prestamo pres: prestamos) {
				pres.setLineasPrestamo(this.getAllLineasPrestamo(pres, pst, rs));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			this.closeConnection(pst, rs);
		}
		return prestamos;
	}
	
	public ArrayList<Prestamo> getAllBySocio(Socio socio) throws SQLException {
		ArrayList<Prestamo> prestamos = new ArrayList<Prestamo>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("SELECT * FROM prestamo WHERE id_socio = ?");
			pst.setInt(1, socio.getId());
			rs = pst.executeQuery();
			while (rs.next()) {
				prestamos.add(this.mapearPrestamo(rs, false));
			}
			rs.close();
			pst.close();
			for(Prestamo pres: prestamos) {
				pres.setLineasPrestamo(this.getAllLineasPrestamo(pres, pst, rs));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			this.closeConnection(pst, rs);
		}
		return prestamos;
	}
	
	public Prestamo getOne(int id) throws SQLException {
		Prestamo pres = new Prestamo();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("SELECT p.id_prestamo, p.fecha_prestamo, p.dias_prestamo, "
					+ "p.estado, s.*, u.nombre_usuario, u.password, u.tipo, u.estado FROM prestamo p "
					+ "INNER JOIN socio s ON p.id_socio = s.id_socio "
					+ "INNER JOIN usuarios u ON s.id_usuario = u.id_usuario "
					+ "WHERE p.id_prestamo = ?");
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if (rs.next()) {
				this.mapearPrestamo(rs, true);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			this.closeConnection(pst, rs);
		}
		return pres;
	}

	public int getLimiteLibrosPendientes() throws SQLException {
		int cant_prestamos_pendientes = 0;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("select cant_max_libros_pend from politicaprestamo where idpoliticaprestamo=(select max(idpoliticaprestamo) from politicaprestamo)");
			rs = pst.executeQuery();
			if (rs.next()) {
				cant_prestamos_pendientes = rs.getInt("cant_max_libros_pend");
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			this.closeConnection(pst, rs);
		}
		return cant_prestamos_pendientes;
	}
	
	private ArrayList<LineaDePrestamo> getAllLineasPrestamo(Prestamo p, PreparedStatement pst, ResultSet rs) throws SQLException {
		ArrayList<LineaDePrestamo> lineas = new ArrayList<LineaDePrestamo>();
		if (conn == null || conn.isClosed()) this.openConnection();
		if (!pst.isClosed()) pst.close();
		if (!rs.isClosed()) rs.close();
		pst = conn.prepareStatement("SELECT l.*, ldp.*, g.descripcion FROM lineadeprestamo ldp "
				+ "INNER JOIN ejemplar e ON ldp.id_ejemplar = e.id_ejemplar "
				+ "INNER JOIN libros l ON e.id_libro = l.id_libro "
				+ "INNER JOIN generos g ON l.id_genero = g.id_genero "
				+ "WHERE ldp.id_prestamo = ?");
		pst.setInt(1, p.getId());
		rs = pst.executeQuery();
		while (rs.next()) {
			lineas.add(this.mapearLineaDePrestamo(rs));
		}
		rs.close();
		pst.close();
		return lineas;
	}
	
	private LineaDePrestamo mapearLineaDePrestamo(ResultSet rs) throws SQLException{
		LineaDePrestamo linea = new LineaDePrestamo();
		linea.setId(rs.getInt("id_lineadeprestamo"));
		linea.setDevuelto(rs.getBoolean("devuelto"));
		linea.setFechaDevolucion(rs.getDate("fecha_devolucion"));
		Ejemplar ejemp = new Ejemplar();
		ejemp.setId(rs.getInt("id_ejemplar"));
		LibroDAO lDAO = new LibroDAO();
		ejemp.setLibro(lDAO.mapearLibro(rs));
		linea.setEjemplar(ejemp);
		return linea;
	}
	
	private void insertLineaDePrestamo(LineaDePrestamo lp, Prestamo pres, PreparedStatement pst) throws SQLException {
		if (conn == null || conn.isClosed()) this.openConnection();
		if (!pst.isClosed()) pst.close();
		pst = conn.prepareStatement("INSERT INTO lineadeprestamo(id_ejemplar, id_prestamo, "
				+ "fecha_devolucion, devuelto) VALUES (?,?,?,?)");
		pst.setInt(1, lp.getEjemplar().getId());
		pst.setInt(2, pres.getId());
		pst.setDate(3, (Date) lp.getFechaDevolucion());
		pst.setBoolean(4, false);
		pst.executeUpdate();
	}
	
	private void deleteLineaDePrestamo(LineaDePrestamo lp, PreparedStatement pst) throws SQLException {
		if (conn == null || conn.isClosed()) this.openConnection();
		if (!pst.isClosed()) pst.close();
		pst = conn.prepareStatement("DELETE FROM lineadeprestamo "
				+ "WHERE id_lineadeprestamo = ?");
		pst.setInt(1, lp.getId());
		pst.executeUpdate();
	}
	
	private void updateLineaDePrestamo (LineaDePrestamo lp, Prestamo pres, PreparedStatement pst) throws SQLException {
		if (conn == null || conn.isClosed()) this.openConnection();
		if (!pst.isClosed()) pst.close();
		pst = conn.prepareStatement("UPDATE lineadeprestamo SET id_ejemplar = ?, "
				+ "id_prestamo = ?, fecha_devolucion = ?, devuelto = ? "
				+ "WHERE id_lineadeprestamo = ?");
		pst.setInt(1, lp.getEjemplar().getId());
		pst.setInt(2, pres.getId());
		pst.setDate(3, (Date) lp.getFechaDevolucion());
		pst.setBoolean(4, lp.getDevuelto());
		pst.executeUpdate();
	}
}