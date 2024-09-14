package BtlJava.Nhom2.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionPool {
	public Connection getConnection(String objectName) throws SQLException;
	public void releaseConnection (Connection con,String objectName) throws SQLException;
}