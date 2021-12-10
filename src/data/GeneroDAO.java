package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Genero;

public class GeneroDAO extends BaseDAO implements IBaseDAO<Genero>{
	
	public Genero mapearGenero(ResultSet rs) throws SQLException {
		Genero g = new Genero();
		g.setId(rs.getInt("id_genero"));
		g.setDescripcion(rs.getString("descripcion"));
		return g;
	}
	
	public ArrayList<Genero> getAll() throws SQLException {
		ArrayList<Genero> generos = new ArrayList<Genero>();
		Statement stm = null;
		ResultSet rs = null;
		try {
			this.openConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery("SELECT * FROM generos");
			while (rs.next()) {
				generos.add(this.mapearGenero(rs));
			}
		}
		catch (SQLException e) {
			throw e;
		}
		finally {
			this.closeConnection(stm, rs);
		}
		return generos;
	}
	
	public Genero getOne(int id) throws SQLException {
		Genero gen = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("SELECT * FROM generos WHERE id_genero = ?");
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if (rs.next()) {
				gen = this.mapearGenero(rs);
			}
		}
		catch (SQLException e){
			throw e;
		}
		finally {
			this.closeConnection(pst, rs);
		}
		return gen;
	}
	
	public void insert(Genero gen) throws SQLException {
		PreparedStatement pst = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("INSERT INTO generos(descripcion) VALUES(?)");
			pst.setString(1, gen.getDescripcion());
			pst.executeUpdate();
		}
		catch (SQLException e){
			throw e;
		}
		finally {
			this.closeConnection(pst);
		}
	}

	public void update(Genero gen) throws SQLException {
		PreparedStatement pst = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("UPDATE generos SET descripcion = ? WHERE id_genero = ?");
			pst.setString(1, gen.getDescripcion());
			pst.setInt(2, gen.getId());
			pst.executeUpdate();
		}
		catch (SQLException e){
			throw e;
		}
		finally {
			this.closeConnection(pst);
		}
	}
	
	public void delete(Genero gen) throws SQLException {
		PreparedStatement pst = null;
		try {
			this.openConnection();
			pst = conn.prepareStatement("DELETE FROM generos WHERE id_genero = ?");
			pst.setInt(1, gen.getId());
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