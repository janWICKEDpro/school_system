package application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class RegisterStudentController implements Initializable {
	public File pdf ;
	private HostServices hostServices;
	public HostServices getHostServices() {
		return hostServices;
	}
	public void setHostServices(HostServices hostServices) {
		this.hostServices = hostServices;
	}
	@FXML 
	private TableView<TableModel> table;
	@FXML
	private TableColumn<TableModel, CheckBox> checkBox;
	@FXML
	private TableColumn<TableModel, Integer> number;
	@FXML
	private TableColumn<TableModel, String> name;
	@FXML
	private TableColumn<TableModel, String> cycleId;
	@FXML
	private TableColumn<TableModel, Button> Qualification;
	@FXML
	private TableColumn<TableModel, Button> register;
	@FXML
	private TableColumn<TableModel, Button> unregister;
	

	
	static ObservableList<TableModel> rows = FXCollections.observableArrayList();
	
	public void refreshTable(ActionEvent e) {
		rows.clear();
	
		try {
			DatabaseConnection con = new DatabaseConnection();
			Connection connect = con.getConnection();
			ResultSet rs = connect.createStatement().executeQuery("Select * from student where regStatus = 'pending'");
			
			while(rs.next()) {
		
				rows.add(new TableModel(rs.getInt("studId"), rs.getString("studName") + rs.getString("studSurname"), rs.getString("cycleId"), new CheckBox() , new Button("Register"), new Button("Unregister"), null));
			}
		} catch (SQLException ev) {
			// TODO Auto-generated catch block
			ev.printStackTrace();
		}
		
		
		table.setItems(rows);
	}


	public void selectAll(ActionEvent e) {
		for(TableModel row: rows) {
			row.check.setSelected(true);
		}
	}
	public void registerSelected() {
		for(TableModel row: rows) {
			if(row.check.isSelected()) {
				row.register();
			}
		}
	}
	
	public void unregisterSelected() {
		for(TableModel row: rows) {
			if(row.check.isSelected()) {
				row.unregister();
			}
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Button fileBtn = new Button();
		try {
			DatabaseConnection con = new DatabaseConnection();
			Connection connect = con.getConnection();
			ResultSet rs = connect.createStatement().executeQuery("Select * from student where regStatus = 'pending'");
			
			while(rs.next()) {
				String name = rs.getString("fileName");
			     pdf  = new File(name);
			     fileBtn.setText(name);
				 try(FileOutputStream fos = new FileOutputStream(pdf)){
					byte[] buffer = new byte[10000000];
					InputStream is = rs.getBinaryStream("qualification");
					while(is.read(buffer)>0) {
						fos.write(buffer);
					}
				}catch(IOException e) {
					System.out.println("huhu");
				}
				rows.add(new TableModel(rs.getInt("studId"), rs.getString("studName") + " " + rs.getString("studSurname"), rs.getString("cycleId"), new CheckBox() , new Button("Register"), new Button("Unregister"),new Button(name) ));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		checkBox.setCellValueFactory(new PropertyValueFactory<>("check"));
		number.setCellValueFactory(new PropertyValueFactory<>("sn"));
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		cycleId.setCellValueFactory(new PropertyValueFactory<>("cycleId"));
		Qualification.setCellValueFactory(new PropertyValueFactory<>("file"));
		register.setCellValueFactory(new PropertyValueFactory<>("register"));
		unregister.setCellValueFactory(new PropertyValueFactory<>("unregister"));
		fileBtn.setOnAction(e ->{
			HostServices host = getHostServices();
			host.showDocument(pdf.getAbsolutePath());
		});
		table.setItems(rows);
	}


}
