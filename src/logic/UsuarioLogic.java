package logic;

import java.sql.SQLException;
import java.util.ArrayList;

import data.UsuarioDAO;
import model.Usuario;

public class UsuarioLogic {
private UsuarioDAO _UsuarioDAO;
	
	public UsuarioLogic() {
		this._UsuarioDAO = new UsuarioDAO();
	}
	
	public ArrayList<Usuario> getAll() throws SQLException {
		try {
			return this._UsuarioDAO.getAll();
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
	public Usuario getOne(int id) throws SQLException {
		try {
			return this._UsuarioDAO.getOne(id);
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
	public void insert(Usuario usuario) throws SQLException {
		try {
			if (!this.userAlreadyExists(usuario.getNombreUsuario())) 
				this._UsuarioDAO.insert(usuario);
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	//Considerar agregar validaciones de campos en un futuro (Ejemplo: expresión regular para contraseña)
	public void update(Usuario usuario) throws SQLException {
		try {
			this._UsuarioDAO.update(usuario);
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
	public void delete(Usuario usuario) throws SQLException {
		try {
			this._UsuarioDAO.delete(usuario);
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
	public boolean userAlreadyExists(String user) throws SQLException {
		try {
			return this._UsuarioDAO.userAlreadyExists(user);
		}
		catch (SQLException exception) {
			throw exception;
		}
	}
	
	public Usuario insertAndReturn(Usuario usuario) throws SQLException {
		Usuario user = new Usuario();
		try {
			if (!this.userAlreadyExists(usuario.getNombreUsuario())) 
				user = this._UsuarioDAO.insertAndReturn(usuario);
		}
		catch (SQLException exception) {
			throw exception;
		}
		return user;
	}
}
