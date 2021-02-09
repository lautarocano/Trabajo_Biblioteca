package data;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.LibroReserva;
import model.Reserva;

public class ReservaDAO extends BaseDAO implements IBaseDAO<Reserva> {

	public Reserva mapearReserva(ResultSet rs) throws SQLException {
		Reserva r = new Reserva();
		r.setId(rs.getInt("id_reserva"));
		r.setFechaReserva(rs.getDate("fecha_reserva"));
		SocioDAO sDAO = new SocioDAO();
		r.setSocio(sDAO.mapearSocio(rs));
		return r;
	}
	
	public void insert(Reserva res) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("INSERT INTO reservas(fecha_reserva, "
					+ "entregada, id_socio) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);
			pst.setDate(1, (Date)res.getFechaReserva());
			pst.setBoolean(2, false);
			pst.setInt(3, res.getSocio().getId());
			pst.executeUpdate();
			rs = pst.getGeneratedKeys();
			if (rs.next()) {
				res.setId(rs.getInt(1));
			}
			rs.close();
			for (LibroReserva l: res.getLibros()) {
				this.insertLibroReserva(l, res, pst);
			}
		}
		catch (SQLException e){
			e.printStackTrace();
			throw e;
		}
		finally {
			this.closeConnection(pst, rs);
		}
	}

	public void update(Reserva res) throws SQLException {
		PreparedStatement pst = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("UPDATE reservas SET fecha_reserva = ?, "
					+ "entregada = ?, id_socio = ? WHERE id_reserva = ?");
			pst.setDate(1, (Date)res.getFechaReserva());
			pst.setBoolean(2, res.isEntregada());
			pst.setInt(3, res.getSocio().getId());
			pst.setInt(4, res.getId());
			pst.executeUpdate();
			for (LibroReserva l: res.getLibros()) {
				if (l.getId() == 0) this.insertLibroReserva(l, res, pst);
				else if (l.getLibro() == null) this.deleteLibroReserva(l, pst);
				else this.updateLibroReserva(l, res, pst);
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

	public void delete(Reserva res) throws SQLException {
		PreparedStatement pst = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("DELETE FROM libro_reserva WHERE id_reserva = ?");
			pst.setInt(1, res.getId());
			pst.executeUpdate();
			pst.close();
			pst = conn.prepareStatement("DELETE FROM reservas WHERE id_reserva = ?");
			pst.setInt(1, res.getId());
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

	public ArrayList<Reserva> getAll() throws SQLException {
		ArrayList<Reserva> reservas = new ArrayList<Reserva>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("SELECT r.id_reserva, r.fecha_reserva, r.entregada, s.*, u.nombre_usuario, u.password, u.tipo, u.estado "
					+ "FROM reservas r INNER JOIN socios s ON r.id_socio = s.id_socio "
					+ "INNER JOIN usuarios u ON s.id_usuario = u.id_usuario");
			rs = pst.executeQuery();
			while (rs.next()) {
				reservas.add(this.mapearReserva(rs));
			}
			rs.close();
			pst.close();
			for (Reserva res: reservas) {
				res.setLibros(this.getAllLibroReserva(res, pst, rs));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			this.closeConnection(pst, rs);
		}
		return reservas;
	}
	
	public ArrayList<Reserva> getAllBySocio(int id) throws SQLException {
		ArrayList<Reserva> reservas = new ArrayList<Reserva>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("SELECT r.id_reserva, r.fecha_reserva, r.entregada, s.*, u.nombre_usuario, u.password, u.tipo, u.estado"
					+ "	FROM reservas r INNER JOIN socios s ON r.id_socio = s.id_socio "
					+ "	INNER JOIN usuarios u ON s.id_usuario = u.id_usuario"
					+ "	WHERE r.id_socio = ?");
			pst.setInt(1, id);
			rs=pst.executeQuery();
			while (rs.next()) {
				reservas.add(this.mapearReserva(rs));
			}
			rs.close();
			for (Reserva res: reservas) {
				res.setLibros(this.getAllLibroReserva(res, pst, rs));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			this.closeConnection(pst, rs);
		}
		return reservas;
	}

	public Reserva getOne(int id) throws SQLException {
		Reserva res = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("SELECT r.id_reserva, r.fecha_reserva, r.entregada, s.*, u.nombre_usuario, u.password, u.tipo, u.estado "
					+ "FROM reservas r INNER JOIN socios s ON r.id_socio = s.id_socio "
					+ "INNER JOIN usuarios u ON s.id_usuario = u.id_usuario "
					+ "WHERE id_reserva = ?");
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if (rs.next()) {
				res = this.mapearReserva(rs);
			}
			rs.close();
			res.setLibros(this.getAllLibroReserva(res, pst, rs));
		}
		catch (SQLException e){
			e.printStackTrace();
			throw e;
		}
		finally {
			this.closeConnection(pst, rs);
		}
		return res;
	}
	
	public ArrayList<Reserva> getReservasFuturas(int idLibro) throws SQLException {
		ArrayList<Reserva> reservas = new ArrayList<Reserva>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("SELECT r.id_reserva, r.fecha_reserva, r.entregada, s.*, u.nombre_usuario, u.password, u.tipo, u.estado "
					+ "FROM libro_reserva lr "
					+ "INNER JOIN reservas r ON r.id_reserva= lr.id_reserva "
					+ "INNER JOIN socios s ON r.id_socio = s.id_socio "
					+ "INNER JOIN usuarios u ON s.id_usuario = u.id_usuario "
					+ "WHERE lr.id_libro=? AND r.fecha_reserva >= curdate()");
			pst.setInt(1, idLibro);
			rs = pst.executeQuery();
			if (rs.next()) {
				reservas.add(this.mapearReserva(rs));
			}
			rs.close();
		}
		catch (SQLException e){
			e.printStackTrace();
			throw e;
		}
		finally {
			this.closeConnection(pst, rs);
		}
		return reservas;
	}
	
	public void entregarReserva(Reserva res) throws SQLException {
		PreparedStatement pst = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("UPDATE reservas SET entregada = ? WHERE id_reserva = ?");
			pst.setBoolean(1, true);
			pst.setInt(2, res.getId());
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
	
	public ArrayList<LibroReserva> getAllLibroReserva(Reserva res, PreparedStatement pst, ResultSet rs) throws SQLException {
		ArrayList<LibroReserva> librosRes = new ArrayList<LibroReserva>();
		if (conn == null || conn.isClosed()) this.openConnection();
		if (!pst.isClosed()) pst.close();
		if (!rs.isClosed()) rs.close();
		pst = conn.prepareStatement("SELECT lr.id_libro_reserva, lr.id_reserva,l.*,g.* FROM libro_reserva lr "
				+ "INNER JOIN libros l ON lr.id_libro = l.id_libro "
				+ "INNER JOIN generos g ON g.id_genero = l.id_genero "
				+ "WHERE lr.id_reserva = ?");
		pst.setInt(1, res.getId());
		rs = pst.executeQuery();
		LibroDAO lDAO = new LibroDAO();
		while (rs.next()) {
			LibroReserva libRes = new LibroReserva();
			libRes.setId(rs.getInt("id_libro_reserva"));
			libRes.setLibro(lDAO.mapearLibro(rs));
			librosRes.add(libRes);
		}
		rs.close();
		pst.close();
		return librosRes;
	}
	
	public void insertLibroReserva(LibroReserva l, Reserva res, PreparedStatement pst) throws SQLException {
		if (conn == null || conn.isClosed()) this.openConnection();
		if (!pst.isClosed()) pst.close();
		pst = conn.prepareStatement("INSERT INTO libro_reserva(id_libro, "
				+ "id_reserva) VALUES (?,?)");
		pst.setInt(1, l.getLibro().getId());
		pst.setInt(2, res.getId());
		pst.executeUpdate();
	}
	
	public void updateLibroReserva(LibroReserva libRes, Reserva res, PreparedStatement pst) throws SQLException {
		if (conn == null || conn.isClosed()) this.openConnection();
		if (!pst.isClosed()) pst.close();
		pst = conn.prepareStatement("UPDATE libro_reserva SET id_libro = ?, "
				+ "id_reserva = ? WHERE id_libro_reserva = ?");
		pst.setInt(1, libRes.getLibro().getId());
		pst.setInt(2, res.getId());
		pst.setInt(3, libRes.getId());
		pst.executeUpdate();
	}
	
	public void deleteLibroReserva(LibroReserva libRes, PreparedStatement pst) throws SQLException {
		if (conn == null || conn.isClosed()) this.openConnection();
		if (!pst.isClosed()) pst.close();
		pst = conn.prepareStatement("DELETE FROM libro_reserva "
				+ "WHERE id_libro_reserva = ?");
		pst.setInt(1, libRes.getId());
		pst.executeUpdate();
	}
}