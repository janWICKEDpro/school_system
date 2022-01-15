package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;

public class LecturerPanelController implements Initializable{
	@FXML
	private TableView<LectTable> table;
	@FXML
	private TableColumn<LectTable, String> courseName;
	@FXML
	private TableColumn<LectTable, String> course_id;
	@FXML
	private TableColumn<LectTable, Button> marks;
	@FXML
	private Label err;
	ObservableList<LectTable> rows = FXCollections.observableArrayList();
	DatabaseConnection connect = new DatabaseConnection();
	Connection con = connect.getConnection();
	static public String names;
	
	public void getString(String name) {
		names = new String(name);
		//System.out.println(names);
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
			try {
			//	System.out.println(names);
			//	ResultSet q = con.createStatement().executeQuery("select * from lecturer where lecName = '"+names+"' ");
				ResultSet rs = con.createStatement().executeQuery("Select * from courses where lecId = '1' ");
				while(rs.next()) {
					rows.add(new LectTable(rs.getString("course_id"), rs.getString("course_name"), new Button("Marks")));
					
				}
				if(!rs.next()) {
					err.setText("No course has been assigned to you yet");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			 course_id.setCellValueFactory(new PropertyValueFactory<>("courseId"));
			 courseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
			 marks.setCellValueFactory(new PropertyValueFactory<>("marks"));
		table.setItems(rows);
		
	}

	
		
	
}
