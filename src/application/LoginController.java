package application;

import java.io.IOException;
import java.sql.*;

import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;


public class LoginController {
	private Scene scene;
	private Stage stage;
	private Parent root;

	private HostServices hostServices;
	public HostServices getHostServices() {
		return hostServices;
	}
	public void setHostServices(HostServices hostServices) {
		this.hostServices = hostServices;
	}
	@FXML
	private Button loginButton;
	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private Label labelErr;
	@FXML
	private Label usernameErr;
	@FXML
	private Label passwordErr;


	public void loginButton(ActionEvent e) {
		if(usernameField.getText().isEmpty()) {
			usernameErr.setText("username must not be empty");
		}else {
			usernameErr.setText("");
		}
		if(passwordField.getText().isEmpty()) {
			passwordErr.setText("password must not be empty");
		}else {
			passwordErr.setText("");
		}
		if(!usernameField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
			validate(e);
		}
	}
	
	public void switchToSignUp(ActionEvent e)throws IOException {
		root  = FXMLLoader.load(getClass().getResource("Sign.fxml"));
		stage =(Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	public void switchToStudPanel(ActionEvent e) throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("Registration.fxml"));
		root = (Parent)loader.load();
		RegistrationController cont = (RegistrationController)loader.getController();
		cont.getUser(usernameField.getText());
		cont.studentStatus(usernameField.getText());
		stage =(Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	public void switchToAdminPanel(ActionEvent e) throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminPanel.fxml"));
		root  = loader.load();
		AdminController cont = loader.getController();
		cont.setHostServices(hostServices);
		stage =(Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	public void validate(ActionEvent e) {
		
		DatabaseConnection connection = new DatabaseConnection();
		Connection connect = connection.getConnection();
		String query = "SELECT * FROM user where username = '"+usernameField.getText()+"' and passwords = '"+passwordField.getText()+"'";
		try {
		Statement ps = connect.createStatement();
		ResultSet rs = ps.executeQuery(query);
		if(rs.next()) {
			if(rs.getString("role").contentEquals("Student")) {
				switchToStudPanel(e);
			}else {
				switchToAdminPanel(e);
			}
			
		}else {
			labelErr.setText("Invalid Login Credentials");
		}
	
			}catch(Exception err) {
			    labelErr.setText("Oops something wierd occured try again");
				err.printStackTrace();
		}
	}
}
