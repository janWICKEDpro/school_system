package reports;

public class classList {
	int studId;
	String studName;
	String gender;
	String studClass;
	public classList(int studId, String studName, String gender, String studClass) {
		super();
		this.studId = studId;
		this.studName = studName;
		this.gender = gender;
		this.studClass = studClass;
	}
	public int getStudId() {
		return studId;
	}
	public void setStudId(int studId) {
		this.studId = studId;
	}
	public String getStudName() {
		return studName;
	}
	public void setStudName(String studName) {
		this.studName = studName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getStudClass() {
		return studClass;
	}
	public void setStudClass(String studClass) {
		this.studClass = studClass;
	}

}
