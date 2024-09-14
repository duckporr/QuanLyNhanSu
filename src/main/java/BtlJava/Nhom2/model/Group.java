package BtlJava.Nhom2.model;

public class Group {
	int groupId;
	String name;
	String description;
	public Group() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Group(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Group [groupId=" + groupId + ", name=" + name + ", description=" + description + "]";
	}
}

