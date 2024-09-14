package BtlJava.Nhom2.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.poifs.filesystem.POIFSDocument;

import BtlJava.Nhom2.dao.DepartmentDAOImpl;
import BtlJava.Nhom2.database.ConnectionPool;
import BtlJava.Nhom2.database.ConnectionPoolImpl;

import BtlJava.Nhom2.model.Department;
import BtlJava.Nhom2.model.Employee;
import BtlJava.Nhom2.model.Position;

public class EmployeeDAOImpl implements DAOInterface<Employee> {
   
	private Connection con;
    private static ConnectionPool connectionPool;
    
    public EmployeeDAOImpl(ConnectionPool cp) {
    	if(cp == null) {
 			cp = new ConnectionPoolImpl();
 		}
 		try {
 			this.con = cp.getConnection("employees");

 			// kiểm tra chế độ thực thi tự động
 			if (this.con.getAutoCommit()) {
 				this.con.setAutoCommit(false);
 			}
 		} catch (SQLException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
    }
    
    public static EmployeeDAOImpl getInstance(ConnectionPool cp){
        return new EmployeeDAOImpl(cp);
    }
	

    @Override
    public  boolean insert(Employee employee) {
        int result = 0;
        try (
             PreparedStatement ps = con.prepareStatement("INSERT INTO employees(name,email,departmentId, phoneNumber,address,startDate,salary,status,positionId) VALUES(?, ?, ?, ?, ?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, employee.getName());
            ps.setString(2, employee.getEmail());
            ps.setInt(3, employee.getDepartment().getDepartmentId()); 
            ps.setString(4, employee.getPhoneNumber());
            ps.setString(5, employee.getAddress());
            ps.setString(6, employee.getStartDate());
            ps.setDouble(7, employee.getSalary());
            ps.setString(8, employee.getStatus());
            ps.setInt(9, employee.getPosition().getPositionId());
            result = ps.executeUpdate();

            // Lấy ID được tạo tự động
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    employee.setEmployeeId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(result !=0) return true ;
        else return false;
    }

    @Override
    public boolean updateById(int employeeId, Employee employee) {
        int result = 0;
        try (
             PreparedStatement ps = con.prepareStatement("UPDATE employees SET name = ?,email = ?,departmentId=?,phoneNumber = ?,address=?,startDate=?,salary=?,status=?,positionId=? WHERE employeeId = ?")) {

            ps.setString(1, employee.getName());
            ps.setString(2, employee.getEmail());
            
            ps.setInt(3, employee.getDepartment().getDepartmentId());
            ps.setString(4, employee.getPhoneNumber());
            ps.setString(5, employee.getAddress());
            ps.setString(6, employee.getStartDate());
            ps.setDouble(7, employee.getSalary());
            ps.setString(8, employee.getStatus());
            ps.setInt(9, employee.getPosition().getPositionId());
            ps.setInt(10, employeeId);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(result!=0) return true;
        else return false;
    }

    @Override
    public boolean deleteById(int employeeId) {
        int result = 0;
        try (
             PreparedStatement ps = con.prepareStatement("DELETE FROM employees WHERE employeeId = ?")) {

            ps.setInt(1, employeeId);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(result!=0) return true;
        else return false;
    }

    @Override
    public ArrayList<Employee> findAll() {
        ArrayList<Employee> employees = new ArrayList<>();
        try (
             PreparedStatement ps = con.prepareStatement("SELECT * FROM employees");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(rs.getInt("employeeId"));
                employee.setName(rs.getString("name"));
                employee.setEmail(rs.getString("email"));
                employee.setPhoneNumber(rs.getString("phoneNumber"));
                employee.setAddress(rs.getString("address"));
                employee.setStartDate(rs.getString("startDate"));
                employee.setSalary(rs.getDouble("salary"));
                employee.setStatus(rs.getString("status"));
                Department department = this.getDepartmentById(rs.getInt("departmentId"));
                employee.setDepartment(department);
                Position position = this.getPositionById(rs.getInt("positionId"));
                employee.setPosition(position);
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public Employee findById(int employeeId) {
        Employee employee = null;
        try (
             PreparedStatement ps = con.prepareStatement("SELECT * FROM employees WHERE employeeId = ?")){
    		 ps.setInt(1, employeeId);
        	
             ResultSet rs = ps.executeQuery();
             
            
            if (rs.next()) {
                employee = new Employee();
                employee.setEmployeeId(rs.getInt("employeeId"));
                employee.setName(rs.getString("name"));
                employee.setEmail(rs.getString("email"));
                employee.setPhoneNumber(rs.getString("phoneNumber"));
                employee.setAddress(rs.getString("address"));
                employee.setStartDate(rs.getString("startDate"));
                employee.setSalary(rs.getDouble("salary"));
                employee.setStatus(rs.getString("status"));
                Department department = this.getDepartmentById(rs.getInt("departmentId"));
                Position position = this.getPositionById(rs.getInt("positionId"));
                employee.setPosition(position);
                employee.setDepartment(department);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    public List<Employee> findByName(String name) {
    	List<Employee>  employees = new ArrayList<Employee>();
        try (
             PreparedStatement ps = con.prepareStatement("SELECT * FROM employees WHERE name LIKE ?")){
        	ps.setString(1,"%"+ name+"%");
             ResultSet rs = ps.executeQuery(); 

          
            while(rs.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(rs.getInt("employeeId"));
                employee.setName(rs.getString("name"));
                employee.setEmail(rs.getString("email"));
                employee.setPhoneNumber(rs.getString("phoneNumber"));
                employee.setAddress(rs.getString("address"));
                employee.setStartDate(rs.getString("startDate"));
                employee.setSalary(rs.getDouble("salary"));
                employee.setStatus(rs.getString("status"));
                Department department = this.getDepartmentById(rs.getInt("departmentId"));
                employee.setDepartment(department);
           
                Position position = this.getPositionById(rs.getInt("positionId"));
                employee.setPosition(position);
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public int countAll() {
        int count = 0;
        try (
             PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM employees")) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public ArrayList<Employee> findPage(int page, int pageSize) {
        ArrayList<Employee> employees = new ArrayList<>();
        try (
             PreparedStatement ps = con.prepareStatement("SELECT * FROM employees LIMIT ?, ?")) {

            ps.setInt(1, (page - 1) * pageSize);
            ps.setInt(2, pageSize);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(rs.getInt("employeeId"));
                employee.setName(rs.getString("name"));
                employee.setEmail(rs.getString("email"));
                employee.setPhoneNumber(rs.getString("phoneNumber"));
                employee.setAddress(rs.getString("address"));
                employee.setStartDate(rs.getString("startDate"));
                employee.setSalary(rs.getDouble("salary"));
                employee.setStatus(rs.getString("status"));
                int departmentId = rs.getInt("departmentId"); 
                Department department = this.getDepartmentById(departmentId);
                employee.setDepartment(department);
                int positionId = rs.getInt("positionId");
                Position position = this.getPositionById(positionId);
                employee.setPosition(position);
                employees.add(employee);
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public Department getDepartmentById(int id) {
        Department department = null;
        try (
             PreparedStatement ps = con.prepareStatement("SELECT * FROM departments where departmentId=?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
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
    public Position getPositionById(int id) {
        Position position = null;
        try (
             PreparedStatement ps = con.prepareStatement("SELECT * FROM positions where positionId=?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    position = new Position();
                    position.setPositionId(rs.getInt("positionId"));
                    position.setTitle(rs.getString("title"));
                    position.setDescription(rs.getString("description"));
                    
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return position;
    }
  
}