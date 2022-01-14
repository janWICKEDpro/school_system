package application;

import java.sql.Connection;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public class TableModel {
	

    int sn;
	String Name,cycleId;
	CheckBox check;
	Button register, unregister;
	Button file;
	
	Alert a = new Alert(AlertType.NONE);
	 
    
	
	public TableModel(int sn, String name, String cycleId, CheckBox check, Button register, Button unregister,
			Button file) {
		super();
		this.sn = sn;
		Name = name;
		this.cycleId = cycleId;
		this.check = check;
		 this.register = register;
		this.unregister = unregister;
		this.file = file;
		register.setStyle("-fx-background-color: #003638;"
				+ "-fx-text-fill: #f3f2c9;");
		unregister.setStyle("-fx-background-color: red;"
				+ "-fx-text-fill: #f3f2c9;");
		unregister.setOnAction(e ->{
		unregister();
    	});
		register.setOnAction(e ->{
		register();
		 a.setAlertType(AlertType.CONFIRMATION);
         a.show();
		});		
	}

	public void register() {
		if(unregister.getText().contentEquals("Unregister")) {
			for(TableModel row:RegisterStudentController.rows) {
				if(row.getRegister() == register) {
					DatabaseConnection con = new DatabaseConnection();
					Connection connect = con.getConnection();
					String query = "update student set regStatus = 'registered' where studId = '"+getSn()+"'";
					try {
						Statement  s = connect.createStatement();
						int i = s.executeUpdate(query);
						if(i == 1) {
							register.setText("Done");
							register.setStyle("-fx-background-color: grey");
							unregister.setText("done");
							unregister.setStyle("-fx-background-color: grey");
							
						}
					
					}catch(Exception event) {
				
					}
				}
		    }
		}
	}
		
	public void unregister() {
		if(register.getText().contentEquals("Register")) {
			for(TableModel row:RegisterStudentController.rows) {
				if(row.getUnregister() == unregister) {
					DatabaseConnection con = new DatabaseConnection();
					Connection connect = con.getConnection();
					String query = "update student set regStatus = 'rejected' where studId = '"+getSn()+"'";
					try {
						Statement  s = connect.createStatement();
						int i = s.executeUpdate(query);
						if(i == 1) {
							register.setText("rejected");
							register.setStyle("-fx-background-color: red");
							unregister.setText("rejected");
							unregister.setStyle("-fx-background-color: red");

						}
					
					}catch(Exception event) {
				
					}
				}
		    }
		}
	}
	public int getSn() {
		return sn;
	}

	public void setSn(int sn) {
		this.sn = sn;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getCycleId() {
		return cycleId;
	}

	public void setCycleId(String cycleId) {
		this.cycleId = cycleId;
	}

	public CheckBox getCheck() {
		return check;
	}

	public void setCheck(CheckBox check) {
		this.check = check;
	}

	public Button getRegister() {
		return register;
	}

	public void setRegister(Button register) {
		this.register = register;
	}

	public Button getUnregister() {
		return unregister;
	}

	public void setUnregister(Button unregister) {
		this.unregister = unregister;
	}

	public Button getFile() {
		return file;
	}

	public void setFile(Button file) {
		this.file = file;
	}


	
}
