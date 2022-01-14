package reports;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import application.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;

public class listByCycleController implements Initializable {
	@FXML
	private TableView<CycleList> table;
	@FXML
	private TableColumn<CycleList,String> studId;
	@FXML
	private TableColumn<CycleList, String> studName;
	@FXML
	private ComboBox<String> cycleField;
	ObservableList<CycleList> rows = FXCollections.observableArrayList();
	DatabaseConnection connect = new DatabaseConnection();
	Connection con = connect.getConnection();
	
	public void getList() {
		rows.clear();
		  String cycle[] = cycleField.getSelectionModel().getSelectedItem().split(" ");
		  String cycleId = cycle[0].substring(0, 1) + cycle[1].substring(0, 1) + cycle[2].substring(0,1);
		try {
			ResultSet rs = con.createStatement().executeQuery("select * from student where regStatus = 'registered' and cycleId ='"+cycleId+"'");
			while(rs.next()) {
				rows.add(new CycleList(rs.getInt("StudId"), rs.getString("StudName") + " " +rs.getString("studSurname")));
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
				rows.add(new CycleList(rs.getInt("StudId"), rs.getString("StudName") + " " +rs.getString("studSurname")));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		  
		ObservableList<String> cycles = FXCollections.observableArrayList("Basic Technical Cycle","Ordinary Technical Cycle","Higher Technical Cycle");
		cycleField.setItems(cycles);
		
		  studId.setCellValueFactory(new PropertyValueFactory<>("studId"));
		  studName.setCellValueFactory(new PropertyValueFactory<>("studName"));
		  table.setItems(rows);
	}

}
