package BtlJava.Nhom2.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import BtlJava.Nhom2.database.ConnectionPool;
import BtlJava.Nhom2.database.ConnectionPoolImpl;

import BtlJava.Nhom2.model.Department;
import BtlJava.Nhom2.model.Employee;


public class DepartmentDAOImpl implements DAOInterface<Department> {

	 private Connection con;
     private static ConnectionPool connectionPool;
     
     public  DepartmentDAOImpl(ConnectionPool cp) {
 		if(cp == null) {
 			cp = new ConnectionPoolImpl();
 		}
 		try {
 			this.con = cp.getConnection("departments");

 			// kiểm tra chế độ thực thi tự động
 			if (this.con.getAutoCommit()) {
 				this.con.setAutoCommit(false);
 			}
 		} catch (SQLException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
 	}
     private boolean exe(PreparedStatement pre) {
 		if(pre!=null) {
 			try{
 				int results = pre.executeUpdate();
 				if(results==0) {
 					this.con.rollback();
 					return false;
 				}
 				this.con.commit();
 				return true;
 			}
 			catch (SQLException e1) {
 				// TODO Auto-generated catch block
 				e1.printStackTrace();
 			}
 			finally {
 				pre= null;
 			}
 		}
 			return false;
 	}
 	

    public static DepartmentDAOImpl getInstance( ConnectionPool cp){
        return new DepartmentDAOImpl(cp);
    }
	
	@Override
	public boolean insert(Department department) {
		// TODO Auto-generated method stub
		int result = 0;
        try (
             PreparedStatement ps = con.prepareStatement("INSERT INTO departments(departmentName,description) VALUES( ?, ?)")) {

 
            ps.setString(1, department.getDepartmentName());
            ps.setString(2, department.getDescription());
      
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(result!=0) return true;
        else return false;
	}

	@Override
	public boolean updateById(int departmentId, Department department) {
		// TODO Auto-generated method stub
		 int result = 0;
		    try (
		         PreparedStatement ps = con.prepareStatement("UPDATE departments SET departmentName=?,description=? WHERE departmentId=?")) {

		        if (con == null) {
		            System.err.println("Lỗi kết nối cơ sở dữ liệu");
		            return false;
		        }

		        // Đặt giá trị cho các tham số
		        ps.setString(1, department.getDepartmentName());
		        ps.setString(2, department.getDescription());
		        ps.setInt(3, department.getDepartmentId()); 
		        result = ps.executeUpdate();
		    } catch (SQLException e) {
		        // In lỗi chi tiết
		        System.err.println("Lỗi cập nhật dữ liệu: " + e.getMessage());
		        e.printStackTrace();
		    }
		    if(result!=0) return true;
		    else return false ;
	}

	@Override
	public   boolean deleteById(int departmentId) {
		// TODO Auto-generated method stub
		    int result = 0;
	        try (
	             PreparedStatement ps = con.prepareStatement("DELETE FROM departments WHERE departmentId = ?")) {
	            ps.setInt(1, departmentId);
	            result = ps.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        if(result!=0) return true;
	        else return false ;
	}

	@Override
	public ArrayList<Department> findAll() {
		// TODO Auto-generated method stub
		ArrayList<Department> departments = new ArrayList<>();
        try (
             PreparedStatement ps = con.prepareStatement("SELECT * FROM departments");
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
            	Department department = new Department();
            	department.setDepartmentId(rs.getInt("departmentId"));
            	department.setDepartmentName(rs.getNString("departmentName"));
            	department.setDescription(rs.getNString("description"));
               
                departments.add(department);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departments;
	}
	 public int getIdByDepartmentName(String name) {
	        int departmentId = 0;
	        try (
	             PreparedStatement ps = con.prepareStatement("SELECT departmentId FROM departments WHERE departmentName = ?")) {
	            ps.setString(1, name);
	            ResultSet rs = ps.executeQuery();
	            if (rs.next()) {
	                departmentId = rs.getInt("departmentId");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return departmentId;
	    }
	
	public ArrayList<String> findName() {
		// TODO Auto-generated method stub
		ArrayList<String> departments = new ArrayList<>();
        try (
             PreparedStatement ps = con.prepareStatement("SELECT * FROM departments");
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {              
                departments.add(rs.getNString("departmentName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departments;
	}

	@Override
	public Department findById(int id) {
		// TODO Auto-generated method stub
		Department department = null;
        try (
             PreparedStatement ps = con.prepareStatement("SELECT * FROM departments where departmentId=?")){
        	ps.setInt(1, id);
             ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
            	
            	department = new Department();
            	department.setDepartmentId(rs.getInt("departmentId"));
            	department.setDepartmentName(rs.getNString("departmentName"));
            	department.setDescription(rs.getNString("description"));
               
  
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return department;
	}
	public int countAll() {
        int count = 0;
        try (
             PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM departments")) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
	 public List<Department> findByName(String name) {
		    List<Department> departments = new ArrayList<>();
	        try (
	             PreparedStatement ps = con.prepareStatement("SELECT * FROM departments WHERE departmentName LIKE ?")) {
	            ps.setString(1, "%" + name + "%"); 
	            ResultSet rs = ps.executeQuery();
	            while (rs.next()) {
	                Department department = new Department();
	                department.setDepartmentId(rs.getInt("departmentId"));
	                department.setDepartmentName(rs.getString("departmentName"));
	                department.setDescription(rs.getString("description"));
	                departments.add(department);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return departments;
	    }
	 
	 public ArrayList<Department> findPage(int page, int pageSize) {
	        ArrayList<Department> departments = new ArrayList<>();
	        try (
	             PreparedStatement ps = con.prepareStatement("SELECT * FROM departments LIMIT ?, ?")) {
	            ps.setInt(1, (page - 1) * pageSize); 
	            ps.setInt(2, pageSize); 
	            ResultSet rs = ps.executeQuery();
	            while (rs.next()) {
	                Department department = new Department();
	                department.setDepartmentId(rs.getInt("departmentId"));
	                department.setDepartmentName(rs.getString("departmentName"));
	                department.setDescription(rs.getString("description"));
	                departments.add(department);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return departments;
	    }
	  public Department getDepartmentByName(String name) {
	        Department department = null;
	        try (
	             PreparedStatement ps = con.prepareStatement("SELECT * FROM departments WHERE departmentName = ?")) {
	            ps.setString(1, name); // Thiết lập giá trị cho tham số

	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) { // Kiểm tra nếu có kết quả
	                    department = new Department();
	                    department.setDepartmentId(rs.getInt("departmentId"));
	                    department.setDepartmentName(rs.getString("departmentName"));
	                    department.setDescription(rs.getString("description"));
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return department;
	    }

}
