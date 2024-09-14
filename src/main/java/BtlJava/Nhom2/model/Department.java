package BtlJava.Nhom2.model;


public class Department {

	int departmentId;
	String departmentName;
	String description;

	public Department() {
		
	}

	public Department( String departmentName, String description) {
		super();
		
		this.departmentName = departmentName;
		this.description = description;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
