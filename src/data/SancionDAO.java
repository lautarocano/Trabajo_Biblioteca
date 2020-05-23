package data;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Sancion;
import model.Socio;



public class SancionDAO extends BaseDAO implements IBaseDAO<Sancion> {
	
	public Sancion mapearSancion(ResultSet rs) throws SQLException {
		Sancion s = new Sancion();
		s.setId(rs.getInt("id_socio"));
		s.setFechaSancion(rs.getDate("fecha_sancion"));
		s.setDiasSancion(rs.getInt("dias_sancion"));
		SocioDAO sDAO=new SocioDAO();
		s.setSocio(sDAO.mapearSocio(rs));
		
		return s;
	}
	
	public ArrayList<Sancion> getAll() throws SQLException {
		ArrayList<Sancion> sanciones = new ArrayList<Sancion>();
		Statement stm = null;
		ResultSet rs = null;
		try {
			this.openConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery("SELECT san.*,soc.nombre,soc.apellido,soc.email,soc.telefono,soc.dni,soc.estado,"
					+ "u.nombre_usuario, u.password, u.tipo, u.estado"
					+ " FROM sanciones san inner join socios soc ON "
					+ " san.id_socio=soc.id_socio inner join usuarios u on soc.id_usuario=u.id_usuario");
			while (rs.next()) {
				sanciones.add(this.mapearSancion(rs));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			this.closeConnection(stm, rs);
		}
		return sanciones;
	}
	
	public Sancion getOne(int id_sancion,int id_socio) throws SQLException {
		Sancion san = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("SELECT san.*,soc.nombre,soc.apellido,soc.email,soc.telefono,soc.dni,soc.estado,"
					+ "u.nombre_usuario, u.password, u.tipo, u.estado"
					+ " FROM sanciones san inner join socios soc ON "
					+ " san.id_socio=soc.id_socio inner join usuarios u on soc.id_usuario=u.id_usuario"
					+ " where id_sancion=? and id_socio=?");
			pst.setInt(1, id_sancion);
			pst.setInt(1, id_socio);
			rs = pst.executeQuery();
			if (rs.next()) {
				san = this.mapearSancion(rs);
			}
		}
		catch (SQLException e){
			e.printStackTrace();
			throw e;
		}
		finally {
			this.closeConnection(pst, rs);
		}
		return san;
	}
	
	public void insert(Sancion san) throws SQLException {
		PreparedStatement pst = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("INSERT INTO sanciones(id_sancion,id_socio,fecha_sancion,"
					+ "dias_sancion) VALUES(?,?,?,?)");
			
			
			pst.setInt(1, san.getId());
			pst.setInt(2,san.getSocio().getId());
			pst.setDate(3, (Date) san.getFechaSancion());
			pst.setInt(4, san.getDiasSancion());
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
	
	public void update(Sancion san) throws SQLException {
		PreparedStatement pst = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("UPDATE sanciones SET fecha_sancion=?,dias_sancion=?"
					+ "WHERE id_sancion = ? and id_socio=?");
			
			
			pst.setDate(1,(Date) san.getFechaSancion());
			pst.setInt(2, san.getDiasSancion());
			pst.setInt(3, san.getId());
			pst.setInt(4, san.getSocio().getId());
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
	
	public void delete(Sancion san) throws SQLException {
		PreparedStatement pst = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("DELETE FROM sancion WHERE id_sancion = ? and id_socio=?");
			pst.setInt(1, san.getId());
			pst.setInt(2, san.getSocio().getId());
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
