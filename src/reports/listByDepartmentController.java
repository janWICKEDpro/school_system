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
import javafx.scene.control.ComboBox;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;

public class listByDepartmentController  implements Initializable{
	@FXML
	private TableView<DeptList> table;
	@FXML
	private TableColumn<DeptList, String> studId;
	@FXML
	private TableColumn<DeptList, String> studName;
	@FXML
	private ComboBox<String> deptField;
	ObservableList<DeptList> rows = FXCollections.observableArrayList();
	DatabaseConnection connect = new DatabaseConnection();
	Connection con = connect.getConnection();
	
	public void getList() {
		rows.clear();
		  String dept[] = deptField.getSelectionModel().getSelectedItem().split(" ");
		  String deptId = dept[0].substring(0, 1) + dept[1].substring(0, 1);
		try {
			ResultSet rs = con.createStatement().executeQuery("select * from student where regStatus = 'registered' and departmentId ='"+deptId+"'");
			while(rs.next()) {
				rows.add(new DeptList(rs.getInt("StudId"), rs.getString("StudName") + " " +rs.getString("studSurname")));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		try {
			ResultSet rs = con.createStatement().executeQuery("select * from student where regStatus = 'registered' ");
			while(rs.next()) {
				rows.add(new DeptList(rs.getInt("StudId"), rs.getString("StudName") + " " +rs.getString("studSurname")));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		  
		ObservableList<String> cycles = FXCollections.observableArrayList("Civil Engineering","Rural Engineering","Town Planning", "Land Sureying");
		deptField.setItems(cycles);
		
		  studId.setCellValueFactory(new PropertyValueFactory<>("studId"));
		  studName.setCellValueFactory(new PropertyValueFactory<>("studName"));
		  table.setItems(rows);
	}
}
