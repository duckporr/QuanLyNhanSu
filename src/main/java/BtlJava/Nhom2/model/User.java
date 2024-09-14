package BtlJava.Nhom2.model;

import java.time.LocalDate;

import org.mindrot.jbcrypt.BCrypt;

public class User {
	 	private int userId;
	    private String fullName;
	    private String userName;
	    private String password;
	    private String email;
	    private LocalDate creationDate; 

	    public User() {
	        super();
	    }

	    public User(String fullName, String userName, String password, String email) {
	        super();
	        this.fullName = fullName;
	        this.userName = userName;
	        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
	        this.email = email;
	        this.creationDate = this.creationDate = LocalDate.now();;
	    }

	    public int getUserId() {
	        return userId;
	    }

	    public void setUserId(int userId) {
	        this.userId = userId;
	    }

	    public String getFullName() {
	        return fullName;
	    }

	    public void setFullName(String fullName) {
	        this.fullName = fullName;
	    }

	    public String getUserName() {
	        return userName;
	    }

	    public void setUserName(String userName) {
	        this.userName = userName;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	    public LocalDate getCreationDate() {
	        return creationDate;
	    }

	    public void setCreationDate(LocalDate creationDate) {
	        this.creationDate = creationDate;
	    }

	    @Override
	    public String toString() {
	        return "User [userId=" + userId + ", fullName=" + fullName + ", userName=" + userName + ", password=" + password
	                + ", email=" + email + ", creationDate=" + creationDate + "]";
	    }
}
