package data;

import java.sql.SQLException;
import java.util.ArrayList;
import model.Entidad;

public interface IBaseDAO <T extends Entidad> {
	public void insert (T t) throws SQLException;
	public void update (T t) throws SQLException;
	public ArrayList<T> getAll() throws SQLException;
	public T getOne(int id) throws SQLException;
}