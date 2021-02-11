package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Socio;

public class SocioDAO extends BaseDAO implements IBaseDAO<Socio> {
	
	public Socio mapearSocio(ResultSet rs) throws SQLException {
		Socio s = new Socio();
		s.setId(rs.getInt("id_socio"));
		s.setNombre(rs.getString("nombre"));
		s.setApellido(rs.getString("apellido"));
		s.setEmail(rs.getString("email"));
		s.setDomicilio(rs.getString("domicilio"));
		s.setTelefono(rs.getString("telefono"));
		s.setDni(rs.getInt("dni"));
		s.setEstado(rs.getBoolean("estado"));
		UsuarioDAO uDAO = new UsuarioDAO();
		s.setUsuario(uDAO.mapearUsuario(rs));
		
		return s;
	}
	
	public ArrayList<Socio> getAll() throws SQLException {
		ArrayList<Socio> socios = new ArrayList<Socio>();
		Statement stm = null;
		ResultSet rs = null;
		try {
			this.openConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery("SELECT s.*, u.nombre_usuario, u.password, u.tipo, u.estado "
					+ "FROM socios s INNER JOIN usuarios u ON s.id_usuario = u.id_usuario");
			while (rs.next()) {
				socios.add(this.mapearSocio(rs));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			this.closeConnection(stm, rs);
		}
		return socios;
	}
	
	public Socio getOne(int id) throws SQLException {
		Socio soc = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("SELECT s.*, u.nombre_usuario, u.password, u.tipo, u.estado "
					+ "FROM socios s INNER JOIN usuarios u ON s.id_usuario = u.id_usuario "
					+ "WHERE id_socio = ?");
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if (rs.next()) {
				soc = this.mapearSocio(rs);
			}
		}
		catch (SQLException e){
			e.printStackTrace();
			throw e;
		}
		finally {
			this.closeConnection(pst, rs);
		}
		return soc;
	}
	
	public Socio getOneByUser(int idUser) throws SQLException {
		Socio soc = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("SELECT s.*, u.nombre_usuario, u.password, u.tipo, u.estado "
					+ "FROM socios s INNER JOIN usuarios u ON s.id_usuario = u.id_usuario "
					+ "WHERE s.id_usuario = ?");
			pst.setInt(1, idUser);
			rs = pst.executeQuery();
			if (rs.next()) {
				soc = this.mapearSocio(rs);
			}
		}
		catch (SQLException e){
			e.printStackTrace();
			throw e;
		}
		finally {
			this.closeConnection(pst, rs);
		}
		return soc;
	}
	
	public boolean dniAlreadyExists(int dni) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("SELECT * FROM socios WHERE dni = ?");
			pst.setInt(1, dni);
			rs = pst.executeQuery();
			if (rs.next()) {
				return true;
			}
			else return false;
		}
		catch (SQLException e){
			e.printStackTrace();
			throw e;
		}
		finally {
			this.closeConnection(pst, rs);
		}
	}
	
	public void insert(Socio soc) throws SQLException {
		PreparedStatement pst = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("INSERT INTO socios(nombre,apellido,email,domicilio,"
					+ "telefono,dni,estado,id_usuario)"
					+ " VALUES(?,?,?,?,?,?,?,?)");
			pst.setString(1,soc.getNombre());
			pst.setString(2, soc.getApellido());
			pst.setString(3, soc.getEmail());
			pst.setString(4, soc.getDomicilio());
			pst.setString(5, soc.getTelefono());
			pst.setInt(6, soc.getDni());
			pst.setBoolean(7, soc.getEstado());
			pst.setInt(8, soc.getUsuario().getId());
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
	
	public void update(Socio soc) throws SQLException {
		PreparedStatement pst = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("UPDATE socios SET nombre= ?,apellido=?,email=?,domicilio=?,"
					+ "telefono=?,dni=?,estado=? "
					+ "WHERE id_socio = ?");
			pst.setString(1,soc.getNombre());
			pst.setString(2, soc.getApellido());
			pst.setString(3, soc.getEmail());
			pst.setString(4, soc.getDomicilio());
			pst.setString(5, soc.getTelefono());
			pst.setInt(6, soc.getDni());
			pst.setBoolean(7, soc.getEstado());
			pst.setInt(8, soc.getId());
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
	
	public void delete(Socio soc) throws SQLException {
		PreparedStatement pst = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("DELETE FROM socios WHERE id_socio = ?");
			pst.setInt(1, soc.getId());
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
	
	/*Verificacion de si el usuario esta o no sancionado*/
	public Boolean isSancionado(Socio socio) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("call Is_Sancionado(1)");
			//pst.setInt(1, socio.getId());
			//pst.setInt(2, socio.getId());
			rs = pst.executeQuery();
			if (rs.next()) {
				if(rs.getInt("diferencia")<rs.getInt("dias_sancion")) {
					return false;
				}
				else {
					return true;
				}
			}
			else {
				return true;
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
	
	public int getNotReturnedBooks(Socio socio) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("select count(id_lineadeprestamo) cant_libros_pendientes "
					+ "from lineasdeprestamo lp join prestamos p on p.id_prestamo = lp.id_prestamo "
					+ "where devuelto=0 and id_socio=?");
			pst.setInt(1, socio.getId());
			rs = pst.executeQuery();
			if (rs.next()) {
				return rs.getInt("cant_libros_pendientes");
			}
			else {
				return 0;
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
	
	public Boolean hasOverdueLoan(Socio socio) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("select count(id_prestamo) as cantidad_atrasados from prestamos where id_socio=? and estado=1");
			pst.setInt(1, socio.getId());
			rs = pst.executeQuery();
			if (rs.next()) {
				if(rs.getInt("cantidad_atrasados")>0) {
					return true;
				}
				else {
					return false;
				}
			}
			else {
				return false;
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
}