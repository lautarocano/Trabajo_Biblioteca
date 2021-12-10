package data;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.PoliticaPrestamo;

public class PoliticaPrestamoDAO extends BaseDAO implements IBaseDAO<PoliticaPrestamo>{

	public PoliticaPrestamo mapearPoliticaPrestamo(ResultSet rs) throws SQLException {
		PoliticaPrestamo pp = new PoliticaPrestamo();
		pp.setId(rs.getInt("idpoliticaprestamo"));
		pp.setCantMaxLibrosPend(rs.getInt("cant_max_libros_pend"));
		pp.setFechaPoliticaPrestamo(rs.getDate("fecha_politica_prestamo"));
		pp.setDiasPrestamo(rs.getInt("cant_dias_prestamo"));
		return pp;
	}
	
	public ArrayList<PoliticaPrestamo> getAll() throws SQLException {
		ArrayList<PoliticaPrestamo> politicasPrestamos = new ArrayList<PoliticaPrestamo>();
		Statement stm = null;
		ResultSet rs = null;
		try {
			this.openConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery("SELECT * FROM politicaprestamo");
			while (rs.next()) {
				politicasPrestamos.add(this.mapearPoliticaPrestamo(rs));
			}
		}
		catch (SQLException e) {
			throw e;
		}
		finally {
			this.closeConnection(stm, rs);
		}
		return politicasPrestamos;
	}
	
	public PoliticaPrestamo getOne(int id) throws SQLException {
		PoliticaPrestamo pp = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("SELECT * FROM politicaprestamo WHERE idpoliticaprestamo = ?");
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if (rs.next()) {
				pp = this.mapearPoliticaPrestamo(rs);
			}
		}
		catch (SQLException e){
			throw e;
		}
		finally {
			this.closeConnection(pst, rs);
		}
		return pp;
	}
	
	public PoliticaPrestamo getActual() throws SQLException {
		PoliticaPrestamo pp = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("select * from politicaprestamo where fecha_politica_prestamo in ( select max(fecha_politica_prestamo) from politicaprestamo)");
			rs = pst.executeQuery();
			if (rs.next()) {
				pp = this.mapearPoliticaPrestamo(rs);
			}
		}
		catch (SQLException e){
			throw e;
		}
		finally {
			this.closeConnection(pst, rs);
		}
		return pp;
	}
	
	public void insert(PoliticaPrestamo pp) throws SQLException {
		PreparedStatement pst = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("INSERT INTO politicaprestamo(cant_max_libros_pend,fecha_politica_prestamo,cant_dias_prestamo) VALUES(?,?,?)");
			pst.setInt(1, pp.getCantMaxLibrosPend());
			pst.setDate(2, (Date) pp.getFechaPoliticaPrestamo());
			pst.setInt(3,pp.getDiasPrestamo());
			pst.executeUpdate();
		}
		catch (SQLException e){
			throw e;
		}
		finally {
			this.closeConnection(pst);
		}
	}

	public void update(PoliticaPrestamo pp) throws SQLException {
		PreparedStatement pst = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("UPDATE politicaprestamo SET cant_max_libros_pend = ?,fecha_politica_prestamo=?,cant_dias_prestamo=? WHERE "
					+ "idpoliticaprestamo = ?");
			pst.setInt(1,pp.getCantMaxLibrosPend());
			pst.setDate(2, (Date) pp.getFechaPoliticaPrestamo());
			pst.setInt(3,pp.getDiasPrestamo());
			pst.setInt(4,pp.getId() );
			pst.executeUpdate();
		}
		catch (SQLException e){
			throw e;
		}
		finally {
			this.closeConnection(pst);
		}
	}
	
	public void delete(PoliticaPrestamo pp) throws SQLException {
		PreparedStatement pst = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("DELETE FROM politicaprestamo WHERE idpoliticaprestamo = ?");
			pst.setInt(1, pp.getId());
			pst.executeUpdate();
		}
		catch (SQLException e){
			throw e;
		}
		finally {
			this.closeConnection(pst);
		}
	}
}
