package BtlJava.Nhom2.dao;

import java.util.ArrayList;

public interface DAOInterface<T> {
	public boolean insert(T t);
	
	public boolean updateById(int id,T t);
	
	public boolean deleteById(int id);
	
	public ArrayList<T> findAll();
	
	public T findById(int id);
	
}
