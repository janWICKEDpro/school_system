package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;

public class AddCourseController implements Initializable {
	private Parent root;
	private Scene scene;
	private Stage stage;
	@FXML
	private TextField courseId;
	@FXML
	private TextField courseName;
	@FXML
	private ComboBox<String> classField;
	@FXML
	private ComboBox<String> lecturer;
	DatabaseConnection connect = new DatabaseConnection();
	Connection con = connect.getConnection();
	ObservableList<String> classes = FXCollections.observableArrayList();
	ObservableList<String> lecture = FXCollections.observableArrayList();
	ArrayList<Integer> lectureID = new ArrayList<>();
	
	
	@FXML
	public void addCourse(ActionEvent event) {
			
		try {
			int i = con.createStatement().executeUpdate("insert into courses(course_id, course_name, course_class,lecId) values('"+courseId.getText()+"', '"+courseName.getText()+"', '"+classField.getSelectionModel().getSelectedItem()+"', '"+lectureID.get(lecturer.getSelectionModel().getSelectedIndex()).intValue()+"') ");
			if(i == 1) {
				root  = FXMLLoader.load(getClass().getResource("AdminPanel.fxml"));
				stage =(Stage)((Node)event.getSource()).getScene().getWindow();
				scene = new Scene(root);
				stage.setScene(scene);
				stage.show();

				System.out.println("success");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		try {
			ResultSet rs = con.createStatement().executeQuery("Select * from lecturer");
			ResultSet qs = con.createStatement().executeQuery("select * from class");
			while(rs.next()) {
				lecture.add(rs.getString("lecName"));
				lectureID.add(rs.getInt("lecId"));
			}
			while(qs.next()) {
				classes.add(qs.getString("classId"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		classField.setItems(classes);
		lecturer.setItems(lecture);
	}
}
