package BtlJava.Nhom2.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import BtlJava.Nhom2.database.ConnectionPool;
import BtlJava.Nhom2.database.ConnectionPoolImpl;
import BtlJava.Nhom2.model.Task;



public class TaskDAOImpl implements DAOInterface<Task> {
	private Connection con;
	
	public  TaskDAOImpl(ConnectionPool cp) {
		if(cp == null) {
			cp = new ConnectionPoolImpl();
		}
		try {
			this.con = cp.getConnection("user");

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
	
	
		public boolean TaskExist(Task task) {
		boolean flag = false;
		String sql = "SELECT taskId FROM tasks WHERE title = ?";
		try {
			PreparedStatement ps = this.con.prepareStatement(sql);
			ps.setString(1, task.getTitle());
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
	
    public static TaskDAOImpl getInstance(ConnectionPool cp){
        return new TaskDAOImpl(cp);
    }

	@Override
	public boolean insert(Task task) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO tasks(title, description) ");
		sql.append("VALUES(?, ?)");	
		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			pre.setString(1, task.getTitle());
			pre.setString(2, task.getDescription());
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
	public boolean updateById(int id, Task task) {	
        try {
        	String sql = "UPDATE tasks SET title = ?, description = ? WHERE taskId = ?";
            PreparedStatement ps = this.con.prepareStatement(sql);
            ps.setString(1, task.getTitle());
            ps.setString(2, task.getDescription());
            ps.setInt(3, id);
            return this.exe(ps);
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
	            String sql = "DELETE FROM tasks WHERE taskId = ?";
	            PreparedStatement ps = this.con.prepareStatement(sql);
	            ps.setInt(1, id);
	            return this.exe(ps);
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
	public ArrayList<Task> findAll() {
		ArrayList<Task> tasks = new ArrayList<>();

		String sql = "SELECT * FROM tasks";
		try {
			PreparedStatement ps = this.con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
              Task task = new Task();
              task.setTaskId(rs.getInt("taskId"));
              task.setTitle(rs.getString("title"));
              task.setDescription(rs.getString("description"));
              tasks.add(task);
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
		return tasks;
	}
	
	
	public ArrayList<Task> paginationTask(int at, int total) {
	    ArrayList<Task> tasks = new ArrayList<>();
	    int off = (at - 1) * total;

	    String sql = "SELECT * FROM tasks ORDER BY taskId desc LIMIT ? OFFSET ?";
	    try {
	        PreparedStatement ps = this.con.prepareStatement(sql);
	        ps.setInt(1, total);
	        ps.setInt(2, off);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            Task task = new Task();
	            task.setTaskId(rs.getInt("taskId"));
	            task.setTitle(rs.getString("title"));
	            task.setDescription(rs.getString("description"));
	            tasks.add(task);
	        }
	    } catch (SQLException e) {
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
	    return tasks;
	}

	
	
	

	@Override
	public Task findById(int id) {
		Task task = null;
        try {
            String sql = "SELECT * FROM tasks WHERE taskId = ?";
            PreparedStatement ps = this.con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
            	task = new Task();
            	task.setTaskId(rs.getInt("taskId"));
            	task.setTitle(rs.getString("title"));
            	task.setDescription(rs.getString("description"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return task;
	}

	public Task findByName(String title) {
		Task task = null;
	    try {
	        String sql = "SELECT * FROM tasks WHERE title = ?";
	        PreparedStatement ps = this.con.prepareStatement(sql);
	        ps.setString(1, title);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
            	task = new Task();
            	task.setTaskId(rs.getInt("taskId"));
            	task.setTitle(rs.getString("title"));
            	task.setDescription(rs.getString("description"));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return task;
	}
	
	public ArrayList<Task> searchTask(String key) throws SQLException {
        ArrayList<Task> de = new ArrayList();
//        ConnectionPool cp = this.
        ArrayList<Task> des = this.findAll();
        for(Task p : des){
            if(String.valueOf(p.getTaskId()).contains(key.toLowerCase()) ||
            		p.getTitle().toLowerCase().contains(key.toLowerCase()) ||
            		p.getDescription().toLowerCase().contains(key.toLowerCase()))
                    {
                de.add(p);
            }
        }
        return de;
    }
	
	public ArrayList<Task> searchTaskPage(String key, int pageIndex, int pageSize) throws SQLException {
	    ArrayList<Task> de = new ArrayList<>();
	    ArrayList<Task> des = this.searchTask(key);
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
	
	public Task getTaskByName(String title) {
		ConnectionPool cp = new ConnectionPoolImpl();
		ArrayList<Task> tasks = TaskDAOImpl.getInstance(cp).findAll();
	    for (Task task : tasks) {  // 'tasks' là danh sách các đối tượng Task sẵn có
	        if (task.getTitle().equals(title)) {
	            return task;
	        }
	    }
	    return null;
	}
}
