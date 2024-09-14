package BtlJava.Nhom2.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import BtlJava.Nhom2.database.ConnectionPool;
import BtlJava.Nhom2.database.ConnectionPoolImpl;
import BtlJava.Nhom2.database.JDBCUtil;
import BtlJava.Nhom2.model.Position;;

public class PositionDAOImpl implements DAOInterface<Position>{
	
	
	private Connection con;
	
	public PositionDAOImpl(ConnectionPool cp) {
		if(cp == null) {
			cp = new ConnectionPoolImpl();
		}
		try {
			this.con = cp.getConnection("position");

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
	
	public boolean PositionExist(Position position) {
		boolean flag = false;
		String sql = "SELECT positionId FROM positions WHERE title = ?";
		try {
			PreparedStatement ps = this.con.prepareStatement(sql);
			ps.setString(1, position.getTitle());
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
	
    public static PositionDAOImpl getInstance(ConnectionPool cp){
        return new PositionDAOImpl(cp);
    }
    
    @Override
	public boolean insert(Position position) {
    	StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO positions(title, description) ");
		sql.append("VALUES(?, ?)");
		
		
		try {
			PreparedStatement pre = this.con.prepareStatement(sql.toString());
			pre.setString(1, position.getTitle());
			pre.setString(2, position.getDescription());
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
	public boolean updateById(int id, Position position) {
		try {
        	String sql = "UPDATE positions SET title = ?, description = ? WHERE positionId = ?";
            PreparedStatement pre = this.con.prepareStatement(sql);
            pre.setString(1, position.getTitle());
			pre.setString(2, position.getDescription());
            pre.setInt(3, id);
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
	            String sql = "DELETE FROM positions WHERE positionId = ?";
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
	public ArrayList<Position> findAll() {
		ArrayList<Position> positions = new ArrayList<>();
		
				String sql = "SELECT * FROM positions";
				try {
					PreparedStatement ps = this.con.prepareStatement(sql);
					ResultSet rs = ps.executeQuery();
					while (rs.next()) {
		              Position position = new Position();
		              position.setPositionId(rs.getInt("positionId"));
		              position.setTitle(rs.getString("title"));
		              position.setDescription(rs.getString("description"));
		              positions.add(position);
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
				return positions;
	}
	
	@Override
	public Position findById(int id) {
		Position position = null;
        try {
            String sql = "SELECT * FROM positions WHERE positionId = ?";
            PreparedStatement ps = this.con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
            	position = new Position();
            	position.setPositionId(rs.getInt("positionId"));
                position.setTitle(rs.getString("title"));
                position.setDescription(rs.getString("description"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return position;
	}

	
	public ArrayList<Position> paginationPosition(int at, int total) {
	    ArrayList<Position> positions = new ArrayList<>();
	    int off = (at - 1) * total;

	    String sql = "SELECT * FROM positions ORDER BY positionId desc LIMIT ? OFFSET ?";
	    try {
	        PreparedStatement ps = this.con.prepareStatement(sql);
	        ps.setInt(1, total);
	        ps.setInt(2, off);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	        	Position position = new Position();
	              position.setPositionId(rs.getInt("positionId"));
	              position.setTitle(rs.getString("title"));
	              position.setDescription(rs.getString("description"));
	              positions.add(position);
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
	    return positions;
	}

	
	

	public Position findByName(String name) {
		Position position = null;
	    try {
	        String sql = "SELECT * FROM positions WHERE title = ?";
	        PreparedStatement ps = this.con.prepareStatement(sql);
	        ps.setString(1, name);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	        	  position = new Position();
	              position.setPositionId(rs.getInt("positionId"));
	              position.setTitle(rs.getString("title"));
	              position.setDescription(rs.getString("description"));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return position;
	}
	
	public ArrayList<Position> searchPosition(String key) throws SQLException {
        ArrayList<Position> de = new ArrayList();
//        ConnectionPool cp = this.
        ArrayList<Position> des = this.findAll();
        for(Position p : des){
            if(p.getTitle().toLowerCase().contains(key.toLowerCase()))
                     {
                de.add(p);
            }
        }
        return de;
    }
	
	public ArrayList<Position> searchPositionPage(String key, int pageIndex, int pageSize) throws SQLException {
	    ArrayList<Position> de = new ArrayList<>();
	    ArrayList<Position> des = this.searchPosition(key);
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
	

	

	
}
