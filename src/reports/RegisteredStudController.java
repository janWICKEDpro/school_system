package reports;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

//import org.apache.poi.xssf.model.CommentsTable;
//import org.apache.poi.xssf.usermodel.XSSFComment;
//import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import application.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Cell;
import javafx.scene.control.TableColumn;

public class RegisteredStudController implements Initializable {
	@FXML
	private TableView<RegisteredTable> table;
	@FXML
	private TableColumn<RegisteredTable, Integer> studId;
	@FXML
	private TableColumn<RegisteredTable, String> studName;

	DatabaseConnection connection = new DatabaseConnection();
	Connection con = connection.getConnection();
    ObservableList<RegisteredTable> rows = FXCollections.observableArrayList();
	
	
	public void download() {
		try {
			
//			ResultSet rs = con.createStatement().executeQuery("select * from student where regStatus = 'registered'");
//			XSSFWorkbook wb = new XSSFWorkbook();
//			XSSFSheet sheet = wb.createSheet("Registered Students");
//			XSSFRow header = sheet.createRow(0);
		//	header.createCell(0).setCellCO
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			System.out.println("message");
			DatabaseConnection connection = new DatabaseConnection();
			Connection con = connection.getConnection();
			ResultSet rs = con.createStatement().executeQuery("Select * from student where regStatus = 'registered'");
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
