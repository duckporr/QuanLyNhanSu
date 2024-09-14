package BtlJava.Nhom2.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import BtlJava.Nhom2.database.ConnectionPool;
import BtlJava.Nhom2.database.ConnectionPoolImpl;
import BtlJava.Nhom2.database.JDBCUtil;
import BtlJava.Nhom2.model.Department;
import BtlJava.Nhom2.model.Employee;
import BtlJava.Nhom2.model.Position;
import BtlJava.Nhom2.model.Salary;

public class SalaryDAOImpl implements DAOInterface<Salary>{
	
	
	private Connection con;
	private EmployeeDAOImpl emp;
	private DepartmentDAOImpl dpm;
	private PositionDAOImpl ps;
	private ConnectionPool cp;
	
	public SalaryDAOImpl(ConnectionPool cp) {
		if(cp == null) {
			cp = new ConnectionPoolImpl();
		}
		try {
			this.con = cp.getConnection("salary");
			this.emp = new EmployeeDAOImpl(cp);
	        this.dpm = new DepartmentDAOImpl(cp);
	        this.ps = new PositionDAOImpl(cp);

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
	
    public static SalaryDAOImpl getInstance(ConnectionPool cp){
        return new SalaryDAOImpl(cp);
    }
    
    @Override
	public boolean insert(Salary salary) {
    	StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO salary(month, year, baseSalary, allowance, deduction, adjustment, totalSalary, employeeId, departmentId, positionId) ");
		sql.append("VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		
		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			pre.setInt(1, salary.getMonth());
			pre.setInt(2, salary.getYear());
			pre.setDouble(3, salary.getBaseSalary());
			pre.setDouble(4, salary.getAllowance());
			pre.setDouble(5, salary.getDeduction());
			pre.setDouble(6, salary.getAdjustment());
			pre.setDouble(7, salary.calTotalSalary(salary.getBaseSalary(), salary.getAllowance(), salary.getDeduction(), salary.getAdjustment()));
			pre.setInt(8, salary.getEmployee().getEmployeeId());
			pre.setInt(9, salary.getDepartment().getDepartmentId());
			pre.setInt(10, salary.getPosition().getPositionId());
			return this.exe(pre);
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	@Override
	public boolean updateById(int id, Salary salary) {
		try {
        	String sql = "UPDATE salary SET baseSalary = ?, allowance = ? , deduction = ? , adjustment = ? WHERE salaryId = ?";
            PreparedStatement pre = this.con.prepareStatement(sql);
			pre.setDouble(1, salary.getBaseSalary());
			pre.setDouble(2, salary.getAllowance());
			pre.setDouble(3, salary.getDeduction());
			pre.setDouble(4, salary.getAdjustment());
            pre.setInt(5, id);
            return this.exe(pre);
        }catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				this.con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return false;
	}
   



	@Override
	public boolean deleteById(int id) {
		 try {
	            String sql = "DELETE FROM salary WHERE salaryId = ?";
	            PreparedStatement pre = this.con.prepareStatement(sql);
	            pre.setInt(1, id);
	            return this.exe(pre);
	        }catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
					this.con.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			return false;
	}

	@Override
	public ArrayList<Salary> findAll() {
		ArrayList<Salary> salaries = new ArrayList<>();
		
				String sql = "SELECT * FROM salary ORDER BY month DESC, employeeId ASC";
				try {
					PreparedStatement pre = this.con.prepareStatement(sql);
					ResultSet rs = pre.executeQuery();
					while (rs.next()) {
						Salary salary = new Salary();
						salary.setSalaryId(rs.getInt("salaryId"));
						salary.setMonth(rs.getInt("month"));
						salary.setYear(rs.getInt("year"));
						salary.setBaseSalary(rs.getDouble("baseSalary"));
						salary.setAllowance(rs.getDouble("allowance"));
						salary.setDeduction(rs.getDouble("deduction"));
						salary.setAdjustment(rs.getDouble("adjustment"));
						salary.setTotalSalary(rs.getDouble("totalSalary"));
						Employee e = emp.getInstance(cp).findById(rs.getInt("employeeId"));
						salary.setEmployee(e);
						Department d = dpm.getInstance(cp).findById(rs.getInt("departmentId"));
						salary.setDepartment(d);
						Position p = ps.findById(rs.getInt("positionId"));
						salary.setPosition(p);
						salaries.add(salary);
					}
				}catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
		
					// quay trở lại trạng thái an toàn
					try {
						this.con.rollback();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				return salaries;
	}
	
	
	
	
	@Override
	public Salary findById(int id) {
		Salary salary = null;
        try {
            String sql = "SELECT * FROM salary WHERE salaryId = ?";
            PreparedStatement pre = this.con.prepareStatement(sql);
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
            	salary = new Salary();
				salary.setSalaryId(rs.getInt("salaryId"));
				salary.setMonth(rs.getInt("month"));
				salary.setYear(rs.getInt("year"));
				salary.setBaseSalary(rs.getDouble("baseSalary"));
				salary.setAllowance(rs.getDouble("allowance"));
				salary.setDeduction(rs.getDouble("deduction"));
				salary.setAdjustment(rs.getDouble("adjustment"));
				salary.setTotalSalary(rs.getDouble("totalSalary"));
				Employee e = emp.findById(rs.getInt("employeeId"));
				salary.setEmployee(e);
				Department d = dpm.findById(rs.getInt("departmentId"));
				salary.setDepartment(d);
				Position p = ps.findById(rs.getInt("positionId"));
				salary.setPosition(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salary;
	}
	
	public Salary findByEmpId(int id) {
		Salary salary = null;
		String sql = "SELECT * FROM Salary WHERE employeeId = ?";
		try {
	        PreparedStatement pre = this.con.prepareStatement(sql);
	        pre.setInt(1, id);
	        ResultSet rs = pre.executeQuery();
            if (rs.next()) {
            	salary = new Salary();
				salary.setSalaryId(rs.getInt("salaryId"));
				salary.setMonth(rs.getInt("month"));
				salary.setYear(rs.getInt("year"));
				salary.setBaseSalary(rs.getDouble("baseSalary"));
				salary.setAllowance(rs.getDouble("allowance"));
				salary.setDeduction(rs.getDouble("deduction"));
				salary.setAdjustment(rs.getDouble("adjustment"));
				salary.setTotalSalary(rs.getDouble("totalSalary"));
				Employee e = emp.findById(rs.getInt("employeeId"));
				salary.setEmployee(e);
				Department d = dpm.findById(rs.getInt("departmentId"));
				salary.setDepartment(d);
				Position p = ps.findById(rs.getInt("positionId"));
				salary.setPosition(p);
            }
	    } catch (Exception e) {
            e.printStackTrace();
        }
	    return salary;
	}


	
	public ArrayList<Salary> paginationSalaryReverse(int at, int total) {
	    ArrayList<Salary> salaries = new ArrayList<>();
	    int off = (at - 1) * total;

	    String sql = "SELECT * FROM salary ORDER BY salaryId desc LIMIT ? OFFSET ?";
	    try {
	        PreparedStatement pre = this.con.prepareStatement(sql);
	        pre.setInt(1, total);
	        pre.setInt(2, off);
	        ResultSet rs = pre.executeQuery();
	        while (rs.next()) {
	        	Salary salary = new Salary();
	        	salary.setSalaryId(rs.getInt("salaryId"));
				salary.setMonth(rs.getInt("month"));
				salary.setYear(rs.getInt("year"));
				salary.setBaseSalary(rs.getDouble("baseSalary"));
				salary.setAllowance(rs.getDouble("allowance"));
				salary.setDeduction(rs.getDouble("deduction"));
				salary.setAdjustment(rs.getDouble("adjustment"));
				salary.setTotalSalary(rs.getDouble("totalSalary"));
				Employee e = emp.findById(rs.getInt("employeeId"));
				salary.setEmployee(e);
				Department d = dpm.findById(rs.getInt("departmentId"));
				salary.setDepartment(d);
				Position p = ps.findById(rs.getInt("positionId"));
				salary.setPosition(p);
				salaries.add(salary);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();

	        // quay trở lại trạng thái an toàn
	        try {
	            this.con.rollback();
	        } catch (SQLException e1) {
	            e1.printStackTrace();
	        }
	    }
	    return salaries;
	}

	
	public ArrayList<Salary> searchSalary(String key) throws SQLException {
        ArrayList<Salary> de = new ArrayList();
//        ConnectionPool cp = this.
        ArrayList<Salary> des = this.findAll();
        for(Salary p : des){
            if(String.valueOf(p.getEmployee().getName()).contains(key.toLowerCase()))
                     {
                de.add(p);
            }
        }
        return de;
    }
	
	public ArrayList<Salary> filterSalary(int month, int year, String key){
		ArrayList<Salary> salaries = this.findAll();
		ArrayList<Salary> result = new ArrayList<Salary>();
		for(Salary s : salaries) {
			if(month == -1) {
				if(s.getYear() == year && s.getEmployee().getName().toLowerCase()
						.contains(key.toLowerCase())) {
					result.add(s);
				}
			}else {
				if(s.getMonth() == month && s.getYear() == year && s.getEmployee().getName().toLowerCase()
						.contains(key.toLowerCase())) {
					result.add(s);
				}
			}
		}
		
		return result;
	}
	
	public ArrayList<Salary> filterSalaryByTime(int month, int year){
		ArrayList<Salary> salaries = this.findAll();
		ArrayList<Salary> result = new ArrayList<Salary>();
		for(Salary s : salaries) {
			if(s.getYear() == year && s.getMonth() == month) {
				result.add(s);
			}
		}
		
		return result;
	}
	
	public ArrayList<Salary> filterSalaryTimeBelow(int month, int year){
		ArrayList<Salary> salaries = this.findAll();
		ArrayList<Salary> result = new ArrayList<Salary>();
		for(Salary s : salaries) {
			if(s.getYear() == year && s.getMonth() < month) {
				result.add(s);
			}
		}
		
		return result;
	}
	
	
	public ArrayList<Salary> filterSalaryPage(int month, int year, String key, int pageIndex, int pageSize) throws SQLException {
	    ArrayList<Salary> de = new ArrayList<>();
	    ArrayList<Salary> des = this.filterSalary(month, year, key);
	    int totalRecords = des.size();

	    // Calculate starting index
	    int fromIndex = (pageIndex - 1) * pageSize;
	    if (fromIndex >= totalRecords) {
	        return de; // Return empty list if start index is out of range
	    }

	    // Calculate ending index
	    int toIndex = Math.min(fromIndex + pageSize, totalRecords);

	    for (int i = fromIndex; i < toIndex; i++) {
	        de.add(des.get(i));
	    }

	    return de;
	}
	
	public ArrayList<Salary> paginate(ArrayList<Salary> salaries, int currentPage, int pageSize) {
	    int totalRecords = salaries.size();
	    int startIndex = (currentPage - 1) * pageSize;
	    int endIndex = Math.min(startIndex + pageSize, totalRecords);
	    
	    if (startIndex >= totalRecords) {
	        return new ArrayList<>(); // Trả về danh sách rỗng nếu không có dữ liệu để hiển thị
	    } else {
	        return new ArrayList<>(salaries.subList(startIndex, endIndex));
	    }
	}
	

	
	
	public boolean updateSalaryAdjustment(int id, double adjustment, int month) {
	    String sql = "UPDATE salary SET adjustment = ? WHERE salaryId = ? AND month = ?";
	    try {
	    	
	        PreparedStatement pre = this.con.prepareStatement(sql);
	        pre.setDouble(1, adjustment);
	        pre.setInt(2, id);
	        pre.setInt(3, month);
	        return this.exe(pre);
	    } catch (SQLException e) {
	        e.printStackTrace();
	        try {
	            this.con.rollback();
	        } catch (SQLException e1) {
	            e1.printStackTrace();
	        }
	    }
	    return false;
	}
	
	public double calAllowance(String key) {
		if(key.contains("Trưởng phòng") || key.contains("Quản lý")
				|| key.contains("Thư ký") || key.contains("Kỹ sư")) {
			return 1000000;
		}
		else if(key.contains("Giám đốc")) return 2000000;
		else if(key.contains("Nhân viên")) return 500000;
		else if(key.contains("Phó giám đốc")) return 1500000;
		else if(key.contains("Phó trưởng phòng")) return 800000;
		else if(key.contains("Chuyên viên")) return 1500000;
		else return 1200000;
	}
	
	public boolean updateAllowance() {
		String sql = "UPDATE salary s "
                + "JOIN positions p ON s.positionId = p.positionId "
                + "SET s.allowance = CASE "
                + "    WHEN p.title LIKE '%Trưởng phòng%' OR p.title LIKE '%Quản lý%' "
                + "         OR p.title LIKE '%Thư ký%' OR p.title LIKE '%Kỹ sư%' THEN 1000000 "
                + "    WHEN p.title LIKE '%Giám đốc%' THEN 2000000 "
                + "    WHEN p.title LIKE '%Nhân viên%' THEN 500000 "
                + "    WHEN p.title LIKE '%Phó giám đốc%' THEN 1500000 "
                + "    WHEN p.title LIKE '%Phó trưởng phòng%' THEN 800000 "
                + "    WHEN p.title LIKE '%Chuyên viên%' THEN 1500000 "
                + "    ELSE 1200000 "
                + "END;";
		try {
	        PreparedStatement pre = this.con.prepareStatement(sql);
	        return this.exe(pre);
	    } catch (SQLException e) {
	        e.printStackTrace();
	        try {
	            this.con.rollback();
	        } catch (SQLException e1) {
	            e1.printStackTrace();
	        }
	    }
	    return false;
	}
	
	public boolean updateAllowanceById(int id, double allowance) {
		String sql = "UPDATE salary s "
				+ "SET s.allowance = ? "
				+ "WHERE s.employeeId = ? ";
		try {
	        PreparedStatement pre = this.con.prepareStatement(sql);
	        pre.setDouble(1, allowance);
	        pre.setInt(2, id);
	        return this.exe(pre);
	    } catch (SQLException e) {
	        e.printStackTrace();
	        try {
	            this.con.rollback();
	        } catch (SQLException e1) {
	            e1.printStackTrace();
	        }
	    }
	    return false;
	}
	
	public boolean updateDeductionById(int id, double deduction) {
		String sql = "UPDATE salary s "
				+ "SET s.deduction = ? "
				+ "WHERE s.employeeId = ? ";
		try {
	        PreparedStatement pre = this.con.prepareStatement(sql);
	        pre.setDouble(1, deduction);
	        pre.setInt(2, id);
	        return this.exe(pre);
	    } catch (SQLException e) {
	        e.printStackTrace();
	        try {
	            this.con.rollback();
	        } catch (SQLException e1) {
	            e1.printStackTrace();
	        }
	    }
	    return false;
	}
	
	
	
	public boolean updateBaseSalaryById(int id, double baseSalary) {
		String updateSalarySql = "UPDATE salary SET baseSalary = ? WHERE employeeId = ?";
		String updateEmployeeSql = "UPDATE employees SET salary = ? WHERE employeeId = ?";
				
		try {
	        PreparedStatement pre1 = this.con.prepareStatement(updateSalarySql);
	        PreparedStatement pre2 = this.con.prepareStatement(updateEmployeeSql);
	        pre1.setDouble(1, baseSalary);
	        pre1.setInt(2, id);
	        pre2.setDouble(1, baseSalary);
	        pre2.setInt(2, id);
	        boolean result = this.exe(pre1);
	        boolean result2 = this.exe(pre2);
	        if(result == true && result2 == true) return true;
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        try {
	            this.con.rollback();
	        } catch (SQLException e1) {
	            e1.printStackTrace();
	        }
	    }
	    return false;
	}

	public boolean checkExist(Salary s, int month) {
		boolean flag = false;
		String sql = "SELECT salaryId FROM salary WHERE employeeId= ? AND month = ?";
		try {
			PreparedStatement ps = this.con.prepareStatement(sql);
			ps.setInt(1, s.getEmployee().getEmployeeId());
			ps.setInt(2, month);
			ResultSet rs = ps.executeQuery();
			if(rs != null) {
				if(rs.next()) flag = true;
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
	
}
