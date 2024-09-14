package BtlJava.Nhom2.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import BtlJava.Nhom2.database.ConnectionPool;
import BtlJava.Nhom2.database.ConnectionPoolImpl;
import BtlJava.Nhom2.model.Group;



public class GroupDAOImpl implements DAOInterface<Group> {
	private Connection con;
	
	public  GroupDAOImpl(ConnectionPool cp) {
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
	
	
		public boolean GroupExist(Group group) {
		boolean flag = false;
		String sql = "SELECT groupId FROM nhom2groups WHERE name = ?";
		try {
			PreparedStatement ps = this.con.prepareStatement(sql);
			ps.setString(1, group.getName());
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
	
    public static GroupDAOImpl getInstance(ConnectionPool cp){
        return new GroupDAOImpl(cp);
    }

	@Override
	public boolean insert(Group group) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO nhom2groups(name, description) ");
		sql.append("VALUES(?, ?)");	
		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			pre.setString(1, group.getName());
			pre.setString(2, group.getDescription());
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
	public boolean updateById(int id, Group group) {	
        try {
        	String sql = "UPDATE groups SET name = ?, description = ? WHERE groupId = ?";
            PreparedStatement ps = this.con.prepareStatement(sql);
            ps.setString(1, group.getName());
            ps.setString(2, group.getDescription());
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
	            String sql = "DELETE FROM nhom2groups WHERE groupId = ?";
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
	public ArrayList<Group> findAll() {
		ArrayList<Group> groups = new ArrayList<>();

		String sql = "SELECT * FROM nhom2groups";
		try {
			PreparedStatement ps = this.con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
              Group group = new Group();
              group.setGroupId(rs.getInt("groupId"));
              group.setName(rs.getString("name"));
              group.setDescription(rs.getString("description"));
              groups.add(group);
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
		return groups;
	}
	
	
	public ArrayList<Group> paginationGroup(int at, int total) {
	    ArrayList<Group> groups = new ArrayList<>();
	    int off = (at - 1) * total;

	    String sql = "SELECT * FROM nhom2groups ORDER BY groupId desc LIMIT ? OFFSET ?";
	    try {
	        PreparedStatement ps = this.con.prepareStatement(sql);
	        ps.setInt(1, total);
	        ps.setInt(2, off);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            Group group = new Group();
	            group.setGroupId(rs.getInt("groupId"));
	            group.setName(rs.getString("name"));
	            group.setDescription(rs.getString("description"));
	            groups.add(group);
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
	    return groups;
	}

	
	
	

	@Override
	public Group findById(int id) {
		Group group = null;
        try {
            String sql = "SELECT * FROM nhom2groups WHERE groupId = ?";
            PreparedStatement ps = this.con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
            	group = new Group();
            	group.setGroupId(rs.getInt("groupId"));
            	group.setName(rs.getString("name"));
            	group.setDescription(rs.getString("description"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return group;
	}

	public Group findByName(String name) {
		Group group = null;
	    try {
	        String sql = "SELECT * FROM nhom2groups WHERE name = ?";
	        PreparedStatement ps = this.con.prepareStatement(sql);
	        ps.setString(1, name);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
            	group = new Group();
            	group.setGroupId(rs.getInt("groupId"));
            	group.setName(rs.getString("name"));
            	group.setDescription(rs.getString("description"));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return group;
	}
	
	public ArrayList<Group> searchGroup(String key) throws SQLException {
        ArrayList<Group> de = new ArrayList();
//        ConnectionPool cp = this.
        ArrayList<Group> des = this.findAll();
        for(Group p : des){
            if(String.valueOf(p.getGroupId()).contains(key.toLowerCase()) ||
            		p.getName().toLowerCase().contains(key.toLowerCase()) ||
            		p.getDescription().toLowerCase().contains(key.toLowerCase()))
                    {
                de.add(p);
            }
        }
        return de;
    }
	
	public ArrayList<Group> searchGroupPage(String key, int pageIndex, int pageSize) throws SQLException {
	    ArrayList<Group> de = new ArrayList<>();
	    ArrayList<Group> des = this.searchGroup(key);
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
	
	public Group getGroupByName(String name) {
		ConnectionPool cp = new ConnectionPoolImpl();
		ArrayList<Group> groups = GroupDAOImpl.getInstance(cp).findAll();
	    for (Group group : groups) {  // 'groups' là danh sách các đối tượng Group sẵn có
	        if (group.getName().equals(name)) {
	            return group;
	        }
	    }
	    return null;
	}
}
