package BtlJava.Nhom2.model;

import java.sql.Date;
import java.time.LocalDateTime;

public class Position {
	private int positionId;
	private String title;
	private String description;
	
	public Position() {
		
	}
	
	
	
	public Position(int positionId, String title, String description) {
		super();
		this.positionId = positionId;
		this.title = title;
		this.description = description;
	}



	public int getPositionId() {
		return positionId;
	}
	public void setPositionId(int positionId) {
		this.positionId = positionId;
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

	@Override
	public String toString() {
		return "Position [positionId=" + positionId + ", title=" + title + ", description=" + description
				+ ", salaryRange=" + "]";
	}

	
	
		

}
