package reports;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import application.DatabaseConnection;
import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;

public class RegisteredStudController implements Initializable {
	@FXML
	private TableView<RegisteredTable> table;
	@FXML
	private TableColumn<RegisteredTable, Integer> studId;
	@FXML
	private TableColumn<RegisteredTable, String> studName;
	
	private HostServices hostServices;
	public HostServices getHostServices() {
		return hostServices;
	}
	public void setHostServices(HostServices hostServices) {
		this.hostServices = hostServices;
	}
	static ObservableList<RegisteredTable> rows = FXCollections.observableArrayList();
	
	
	public void download(ActionEvent e) {
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			System.out.println("message");
			DatabaseConnection connection = new DatabaseConnection();
			Connection con = connection.getConnection();
			ResultSet rs = con.createStatement().executeQuery("Select * from student where regStatus = 'pending'");
			while(rs.next()) {
				
				rows.add(new RegisteredTable(rs.getInt("studId"), rs.getString("studName") + " " + rs.getString("studSurname")));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		studId.setCellValueFactory(new PropertyValueFactory<>("id"));
		studName.setCellValueFactory(new PropertyValueFactory<>("name"));

		table.setItems(rows);
	}
}
