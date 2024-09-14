package BtlJava.Nhom2.model;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Salary {
	private int salaryId;
	private int month;
	private int year;
	private double baseSalary;
	private double allowance;
	private double deduction;
	private double adjustment;
	private double totalSalary;
	private Employee employee;
	private Department department;
	private Position position;
	
	
	
	public Salary() {
		
	}
	
	

	public Salary(int salaryId, int month, int year, double baseSalary, double allowance, double deduction,
			double adjustment, double totalSalary, Employee employee, Department department, Position position) {
		super();
		this.salaryId = salaryId;
		this.month = month;
		this.year = year;
		this.baseSalary = baseSalary;
		this.allowance = allowance;
		this.deduction = deduction;
		this.adjustment = adjustment;
		this.totalSalary = baseSalary + allowance - deduction + adjustment;
		this.employee = employee;
		this.department = department;
		this.position = position;
	}



	public int getSalaryId() {
		return salaryId;
	}

	public void setSalaryId(int salaryId) {
		this.salaryId = salaryId;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public double getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(double baseSalary) {
		this.baseSalary = baseSalary;
	}

	public double getAllowance() {
		return allowance;
	}

	public void setAllowance(double allowance) {
		this.allowance = allowance;
	}

	public double getDeduction() {
		return deduction;
	}

	public void setDeduction(double deduction) {
		this.deduction = deduction;
	}

	public double getAdjustment() {
		return adjustment;
	}

	public void setAdjustment(double adjustment) {
		this.adjustment = adjustment;
	}

	public double getTotalSalary() {
		return totalSalary;
	}

	public void setTotalSalary(double totalSalary) {
		this.totalSalary = totalSalary;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	@Override
	public String toString() {
		return "Salary [salaryId=" + salaryId + ", month=" + month + ", year=" + year + ", baseSalary=" + baseSalary
				+ ", allowance=" + allowance + ", deduction=" + deduction + ", adjustment=" + adjustment
				+ ", totalSalary=" + totalSalary + ", employee=" + employee + ", department=" + department
				+ ", position=" + position + "]";
	}
	
	public double calTotalSalary(double baseSalary, double allowance, double deduction, double adjustment) {
		return baseSalary + allowance - deduction + (adjustment / 100 * baseSalary);
	}
	

	

}
