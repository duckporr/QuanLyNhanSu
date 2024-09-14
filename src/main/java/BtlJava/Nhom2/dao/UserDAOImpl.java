package BtlJava.Nhom2.dao;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;

import BtlJava.Nhom2.database.ConnectionPoolImpl;
import BtlJava.Nhom2.model.User;

public class UserDAOImpl implements DAOInterface<User> {
	
	private static ConnectionPoolImpl conPool;
    private int pageSize = 10;
    private int currentPage = 1;

    public UserDAOImpl() {
        if (conPool == null) {
            conPool = new ConnectionPoolImpl();
        }
    }

    @Override
    public boolean insert(User user) {
        boolean result = false;
        try {
            Connection con = conPool.getConnection("user");
            String sql = "INSERT INTO users(fullName, userName, password, email, creationDate) VALUES(?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getUserName());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getEmail());
            ps.setObject(5, user.getCreationDate()); // Assuming creationDate is a java.sql.Date
            if (ps.executeUpdate() != 0) {
                result = true;
            }
            conPool.releaseConnection(con, "user");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean updateById(int id, User user) {
        boolean result = false;
        try {
            Connection con = conPool.getConnection("user");
            String sql = "UPDATE users SET fullName = ?, userName = ?, password = ?, email = ?, creationDate = ? WHERE userId = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getUserName());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getEmail());
            ps.setObject(5, user.getCreationDate()); // Assuming creationDate is a java.sql.Date
            ps.setInt(6, id);
            if (ps.executeUpdate() != 0) {
                result = true;
            }
            conPool.releaseConnection(con, "user");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean deleteById(int id) {
        boolean result = false;
        try {
            Connection con = conPool.getConnection("user");
            String sql = "DELETE FROM users WHERE userId = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            if (ps.executeUpdate() != 0) {
                result = true;
            }
            conPool.releaseConnection(con, "user");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ArrayList<User> findAll() {
        ArrayList<User> users = new ArrayList<>();
        try {
            Connection con = conPool.getConnection("user");
            String sql = "SELECT * FROM users";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setFullName(rs.getString("fullName"));
                user.setUserName(rs.getString("userName"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setCreationDate(rs.getDate("creationDate").toLocalDate()); // Convert to LocalDate
                users.add(user);
            }
            conPool.releaseConnection(con, "user");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User findById(int id) {
        User user = null;
        try {
            Connection con = conPool.getConnection("user");
            String sql = "SELECT * FROM users WHERE userId = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setFullName(rs.getString("fullName"));
                user.setUserName(rs.getString("userName"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setCreationDate(rs.getDate("creationDate").toLocalDate()); // Convert to LocalDate
            }
            conPool.releaseConnection(con, "user");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public User findByFullName(String fullName) {
        User user = null;
        try {
            Connection con = conPool.getConnection("user");
            String sql = "SELECT * FROM users WHERE fullName = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, fullName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setFullName(rs.getString("fullName"));
                user.setUserName(rs.getString("userName"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setCreationDate(rs.getDate("creationDate").toLocalDate()); // Convert to LocalDate
            }
            conPool.releaseConnection(con, "user");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
    
    public User findByUserName(String userName) {
        User user = null;
        try {
            Connection con = conPool.getConnection("user");
            String sql = "SELECT * FROM users WHERE userName = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setFullName(rs.getString("fullName"));
                user.setUserName(rs.getString("userName"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setCreationDate(rs.getDate("creationDate").toLocalDate()); // Convert to LocalDate
            }
            conPool.releaseConnection(con, "user");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public int getTotalPages(String searchTerm) {
        int totalRecords = 0;
        try {
            Connection con = conPool.getConnection("user");
            String sql;
            if (!searchTerm.isEmpty()) {
                sql = "SELECT COUNT(*) FROM users WHERE fullName LIKE ?";
            } else {
                sql = "SELECT COUNT(*) FROM users";
            }
            PreparedStatement ps = con.prepareStatement(sql);
            if (!searchTerm.isEmpty()) {
                ps.setString(1, "%" + searchTerm + "%");
            }
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                totalRecords = rs.getInt(1);
            }
            conPool.releaseConnection(con, "user");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (int) Math.ceil((double) totalRecords / pageSize);
    }

    public int existsUserName(String userName) {
        try {
            Connection con = conPool.getConnection("user");
            String checkUsernameQuery = "SELECT COUNT(*) FROM users WHERE userName = ?";
            PreparedStatement ps = con.prepareStatement(checkUsernameQuery);
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count == 0) {
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int existsEmail(String email) {
        try {
            Connection con = conPool.getConnection("user");
            String checkEmailQuery = "SELECT COUNT(*) FROM users WHERE email = ?";
            PreparedStatement ps = con.prepareStatement(checkEmailQuery);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            System.out.println(count);
            if (count == 0) {
                return 1;
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
   

    public ArrayList<User> findByFullNameLike(String searchTerm) {
        ArrayList<User> userList = new ArrayList<>();
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            con = conPool.getConnection("user");
            String sql = "SELECT * FROM users WHERE fullName LIKE ? LIMIT ?, ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, "%" + searchTerm + "%");
            statement.setInt(2, (currentPage - 1) * pageSize);
            statement.setInt(3, pageSize);

            rs = statement.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setFullName(rs.getString("fullName"));
                user.setUserName(rs.getString("userName"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setCreationDate(rs.getDate("creationDate").toLocalDate());
                userList.add(user);
            }
            conPool.releaseConnection(con, "user");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public ArrayList<User> findByPagination() {
        ArrayList<User> users = new ArrayList<>();
        try {
            Connection con = conPool.getConnection("user");
            String sql = "SELECT * FROM users LIMIT ?, ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, (currentPage - 1) * pageSize);
            ps.setInt(2, pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setFullName(rs.getString("fullName"));
                user.setUserName(rs.getString("userName"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setCreationDate(rs.getDate("creationDate").toLocalDate()); // Convert to LocalDate
                users.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public User login(String userName, String password) {
    	User user = findByUserName(userName);
        if (user != null && BCrypt.checkpw(password, user.getPassword())) { 
            return user;
        } else {
            return null;
        }
    }


    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
