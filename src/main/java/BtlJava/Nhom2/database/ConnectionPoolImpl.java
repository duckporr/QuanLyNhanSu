package BtlJava.Nhom2.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

public class ConnectionPoolImpl implements ConnectionPool{
	
	private String driver;
	private String username;
	private String userpass;
	private String url;
	private Stack<Connection> pool;
	
	public ConnectionPoolImpl() {
		this.driver = "com.mysql.cj.jdbc.Driver";
		try {
			Class.forName(this.driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.username="";
		this.userpass="";
		
//		this.username="";
//		this.userpass="";
		
		
		//Xac dinh duong dan thuc thi
		this.url="";
	//	this.url=";
	    //khởi tạo bộ nhớ đối tượng lưu trữ
		this.pool=new Stack<>();
	}
	

	@Override
	public Connection getConnection(String objectName) throws SQLException {
		// TODO Auto-generated method stub
		if(this.pool.isEmpty()) {
			System.out.println(objectName+"Đã khởi tạo một kết nối mới.");
			return DriverManager.getConnection(this.url,this.username,this.userpass);
		}else {
			System.out.println(objectName+"Đã lấy ra một kết nối.");
			return this.pool.pop();	
		}
	}

	@Override
	public void releaseConnection(Connection con, String objectName) throws SQLException {
		// TODO Auto-generated method stub
		System.out.println(objectName+"Đã trả về một kết nối.");
		this.pool.push(con);
		
	}
	
	protected void finalize() throws Throwable {
		this.pool.clear();
		this.pool=null;
		System.out.println("CPool is Closed!");
		
	}

}
