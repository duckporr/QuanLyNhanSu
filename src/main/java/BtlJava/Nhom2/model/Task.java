package BtlJava.Nhom2.model;
import java.util.Date;

public class Task {

	int taskId;
	String title;
	String description;
	Date dueDate;
	String priority;
	int status;
	int employeeId;
	int departmentId;
	int positionId;
	
	public Task() {
		super();
	}
	
	public Task(String title, String description, Date dueDate, String priority, String status) {
		super();
		
		this.title = title;
		this.description = description;
		this.dueDate = dueDate;
		this.priority = priority;
		if(status.equals("Hiệu lực"))
			this.status = 1;
		else
		    this.status = 0;
	
	}

	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	public int getPositionId() {
		return positionId;
	}
	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}

	@Override
	public String toString() {
		return "Task [taskId=" + taskId + ", title=" + title + ", description=" + description + ", dueDate=" + dueDate
				+ ", priority=" + priority + ", status=" + status + ", employeeId=" + employeeId + ", departmentId="
				+ departmentId + ", positionId=" + positionId + "]";
	}
	
	
}

