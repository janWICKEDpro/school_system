package application;

import javafx.scene.control.Button;

public class LectTable {
	
	String courseId;
	String courseName;
	Button marks;
	public LectTable(String courseId, String courseName, Button marks) {
		super();
		this.courseId = courseId;
		this.courseName = courseName;
		this.marks = marks;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public Button getMarks() {
		return marks;
	}
	public void setMarks(Button marks) {
		this.marks = marks;
	}
	
}
