package reports;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import application.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.ComboBox;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;

public class listByClassController implements Initializable{
	@FXML
	private TableView<classList> classTable;
	@FXML
	private TableColumn<classList, Integer> studId;
	@FXML
	private TableColumn<classList, String> studName;
	@FXML
	private TableColumn<classList, String> gender;
	@FXML
	private TableColumn<classList, String> classFiled;
	@FXML
	private ComboBox<String> classField;
	@FXML
	private ComboBox<String> genderField;
	@FXML
	private Button getListBtn;
	@FXML
	private Button downloadListBtn;
	ObservableList<classList> rows = FXCollections.observableArrayList();
	public void getList() {
		rows.clear();
		DatabaseConnection con = new DatabaseConnection();
		Connection connect = con.getConnection();
		try {
			ResultSet rs;
			if(genderField.getSelectionModel().isEmpty() && classField.getSelectionModel().isEmpty()) {

				 rs = connect.createStatement().executeQuery("Select * from student where regStatus = 'registered' ");
			}else if(classField.getSelectionModel().isEmpty()) {

				 rs = connect.createStatement().executeQuery("Select * from student where sex='"+genderField.getSelectionModel().getSelectedItem()+"' and regStatus = 'registered' ");
			}else if(genderField.getSelectionModel().isEmpty()) {

				 rs = connect.createStatement().executeQuery("Select * from student where classId='"+classField.getSelectionModel().getSelectedItem()+"' and regStatus = 'registered' ");
			}else {
			    rs = connect.createStatement().executeQuery("Select * from student where sex='"+genderField.getSelectionModel().getSelectedItem()+"' and classId='"+classField.getSelectionModel().getSelectedItem()+"' and regStatus = 'registered'");
			}
			
			while(rs.next()) {
				
				rows.add(new classList(rs.getInt("studId"), rs.getString("studName") + " " + rs.getString("studSurname"), rs.getString("sex"), rs.getString("classId")));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//"Select * from student where sex='"+genderField.getSelectionModel().getSelectedItem()+"' and classId='"+classField.getSelectionModel().getSelectedItem()+"' "

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<String> list = FXCollections.observableArrayList("BTCCE","BTCRE","BTCTP","BTCLS","OTCCE","OTCRE","OTCTP","OTCLS","HTCCE","HTCRE","HTCTP","HTCLS");
		classField.setItems(list);
		ObservableList<String> genders = FXCollections.observableArrayList("Male", "Female");
		genderField.setItems(genders);
		try {
			DatabaseConnection con = new DatabaseConnection();
			Connection connect = con.getConnection();
			ResultSet rs = connect.createStatement().executeQuery("Select * from student where regStatus= 'registered' ");
			while(rs.next()) {
				rows.add(new classList(rs.getInt("StudId"), rs.getString("studName") + " " + rs.getString("studSurname"), rs.getString("sex"), rs.getString("classId")));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	    studId.setCellValueFactory(new PropertyValueFactory<>("studId"));
		studName.setCellValueFactory(new PropertyValueFactory<>("studName"));
		gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
		classFiled.setCellValueFactory(new PropertyValueFactory<>("studClass"));
		
		classTable.setItems(rows);
		
	}

}
