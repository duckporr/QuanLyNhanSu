package BtlJava.Nhom2.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class JDBCUtil {
	public static Connection getConnection() {
		Connection c = null;
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			String url = "jdbc:mySQL://localhost:3306/quanlynhansu";
			String userName = "root";
			String password = "123456";
			c = DriverManager.getConnection(url, userName, password);
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "Không thể kết nối đến cơ sở dữ liệu !","Lỗi", JOptionPane.ERROR_MESSAGE);
		}
		return c;
	}
	public static void closeConnection(Connection c) {
		try {
			if(c!=null) {
				c.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public static void printInfo(Connection c) {
		try {
			if (c != null) {
				DatabaseMetaData mtdt = c.getMetaData();
				System.out.println(mtdt.getDatabaseProductName());
				System.out.println(mtdt.getDatabaseProductVersion());
			} 
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
