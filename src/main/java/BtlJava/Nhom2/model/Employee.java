package BtlJava.Nhom2.model;

import BtlJava.Nhom2.model.Department;

public class Employee {

	int employeeId;
	String name;
	Department department;
	String email;
	String phoneNumber;
	String address;
	String startDate;
	double salary;
	String status;
	Position position;

	public Employee() {
		super();
	}

    public Employee(/*int employeeId*/ String name, Department department, String email, String phoneNumber,
			String address, String startDate, double salary, String status, Position position) {
		super();
	/*	this.employeeId = employeeId;*/
		this.name = name;
		this.department = department;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.startDate = startDate;
		this.salary = salary;
		this.status = status;
		this.position = position;
	}
    
    public Employee(String name, Department department, String email, String phoneNumber,
			String address, String startDate, double salary, String status) {
		super();
	/*	this.employeeId = employeeId;*/
		this.name = name;
		this.department = department;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.startDate = startDate;
		this.salary = salary;
		this.status = status;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
	
	

}
