package data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.PoliticaSancion;

public class PoliticaSancionDAO extends BaseDAO implements IBaseDAO<PoliticaSancion> {
	
	public PoliticaSancion mapearPoliticaSancion(ResultSet rs) throws SQLException {
		PoliticaSancion ps = new PoliticaSancion();
		ps.setId(rs.getInt("id_genero"));
		ps.setDiasDeAtrasoDesde(rs.getInt("dias_atraso_desde"));
		ps.setDiasDeAtrasoHasta(rs.getInt("dias_atraso_hasta"));
		ps.setDiasDeSancion(rs.getInt("dias_sancion"));
		return ps;
	}
	
	public ArrayList<PoliticaSancion> getAll() throws SQLException {
		ArrayList<PoliticaSancion> politicassancion = new ArrayList<PoliticaSancion>();
		Statement stm = null;
		ResultSet rs = null;
		try {
			this.openConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery("SELECT * FROM politicasancion");
			while (rs.next()) {
				politicassancion.add(this.mapearPoliticaSancion(rs));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			this.closeConnection(stm, rs);
		}
		return politicassancion;
	}
	
	public PoliticaSancion getOne(int id) throws SQLException {
		PoliticaSancion ps = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("SELECT * FROM politicasancion WHERE id_politicasancion = ?");
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if (rs.next()) {
				ps = this.mapearPoliticaSancion(rs);
			}
		}
		catch (SQLException e){
			e.printStackTrace();
			throw e;
		}
		finally {
			this.closeConnection(pst, rs);
		}
		return ps;
	}
	
	public void insert(PoliticaSancion ps) throws SQLException {
		PreparedStatement pst = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("INSERT INTO politicasancion(id_politicasancion"
					+ ",dias_atraso_desde,dias_atraso_hasta,dias_sancion) VALUES(?,?,?,?)");
			pst.setInt(1, ps.getId());
			pst.setInt(2, ps.getDiasDeAtrasoDesde());
			pst.setInt(3, ps.getDiasDeAtrasoHasta());
			pst.setInt(4, ps.getDiasDeSancion());
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

	public void update(PoliticaSancion ps) throws SQLException {
		PreparedStatement pst = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("UPDATE politicasancion SET dias_atraso_desde= ?,"
					+ "dias_atraso_hasta=?,dias_sancion=? WHERE id_politicasancion = ?");
			pst.setInt(1, ps.getDiasDeAtrasoDesde());
			pst.setInt(2, ps.getDiasDeAtrasoHasta());
			pst.setInt(3, ps.getDiasDeSancion());
			pst.setInt(4, ps.getId());
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
	
	public void delete(PoliticaSancion ps) throws SQLException {
		PreparedStatement pst = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("DELETE FROM politicasancion WHERE id_politicasancion = ?");
			pst.setInt(1, ps.getId());
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
