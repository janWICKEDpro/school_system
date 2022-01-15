package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.ComboBox;

public class AddCourseController implements Initializable {
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
	
	
	@FXML
	public void addCourse(ActionEvent event) {
			
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		try {
			ResultSet rs = con.createStatement().executeQuery("Select * from lecturer");
			ResultSet qs = con.createStatement().executeQuery("select * from class");
			while(rs.next()) {
				lecture.add(rs.getString("lecName"));
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
