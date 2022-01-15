package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import javafx.scene.control.ComboBox;

import javafx.scene.control.PasswordField;

public class SignUpController implements Initializable{
	private Scene scene;
	private Stage stage;
	private Parent root;
	@FXML
	private Button createAccountBtn;
	@FXML
	private Button loginBtn;
	@FXML
	private TextField usernameField;
	@FXML
	private ComboBox<String> roleField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private PasswordField confirmField;
	@FXML
	private Label usernameErr;
	@FXML
	private Label roleErr;
	@FXML
	private Label passwordErr;
	@FXML
	private Label confirmErr;
	@FXML
	private Label errorMessage;
	
	DatabaseConnection database  = new DatabaseConnection();
	Connection con = database.getConnection();
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<String> list = FXCollections.observableArrayList("Admin", "Student", "Lecturer");
		roleField.setItems(list);
	}
	//validate all the text, password, and combo fields 
	//Throws appropriate errors.
	public void fieldValidation(ActionEvent e) {
		boolean username = usernameField.getText().isEmpty();
		boolean password = passwordField.getText().isEmpty();
		boolean role = roleField.getSelectionModel().isEmpty();
		
		//check if user name field is empty
		if(username) {
			usernameErr.setText("Username should not be empty");
		}else {
			usernameErr.setText("");
		}
		if(role) {
			roleErr.setText("This field should not be empty");
		}else {
			roleErr.setText("");
		}
		//check if password field is empty
		if(password) {
			passwordErr.setText("This field should not be empty");
		}else {
			passwordErr.setText("");
		}
		
		if(passwordField.getText().contentEquals(confirmField.getText())){
			confirmErr.setText("");
		}else {
			confirmErr.setText("Passwords must match");
		}
		
		if(!username  && !password && passwordField.getText().contentEquals(confirmField.getText())) {
			SignUP(e);
		}
	}
	//to add a user into the database
	public void SignUP(ActionEvent e) {
	
		
		String query = "Insert into user(username, passwords, role) values ('"+usernameField.getText()+"','"+passwordField.getText()+"', '"+roleField.getSelectionModel().getSelectedItem().toString()+"') ";
		String query_two  = "Insert into student(username) values('"+usernameField.getText()+"' )";
		String query_three  = "Insert into administrator(Admin_name, signIn_date) values('"+usernameField.getText()+"', '"+LocalDateTime.now()+"')";
		String query_four  = "Insert into lecturer(lecName) values('"+usernameField.getText()+"')";
		try {
			int j;
			Statement ps = con.createStatement();
			int i = ps.executeUpdate(query);
			if(roleField.getSelectionModel().getSelectedItem().toString().contentEquals("Student")) {
			 j = ps.executeUpdate(query_two);
			}else if(roleField.getSelectionModel().getSelectedItem().toString().contentEquals("Admin")) {
			j = ps.executeUpdate(query_three);
			}else {
				j= ps.executeUpdate(query_four);
			}
			if(i == 1 && j ==1) {
				navigate(e);
			}
		}catch(Exception err) {
			errorMessage.setText("Username taken");
		}
	}
	public void navigateTOLogin(ActionEvent e)throws IOException {
		root  = FXMLLoader.load(getClass().getResource("Login.fxml"));
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
		root  = FXMLLoader.load(getClass().getResource("AdminPanel.fxml"));
		stage =(Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	public void switchToLectPanel(ActionEvent e)throws IOException{

			    FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("LecturerPanel.fxml"));
				root = (Parent)loader.load();
				LecturerPanelController cont = (LecturerPanelController)loader.getController();
				cont.getString(usernameField.getText());
				stage =(Stage)((Node)e.getSource()).getScene().getWindow();
				scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
		
		
	}
	//to navigate a users to the appropriate screen after signup
	public void navigate(ActionEvent e) throws IOException {
		if(roleField.getSelectionModel().getSelectedItem().contentEquals("Student")) {
			try {
				switchToStudPanel(e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else if(roleField.getSelectionModel().getSelectedItem().contentEquals("Student")) {
			try {
				switchToAdminPanel(e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else {
			
				switchToLectPanel(e);
			
		}
	}

}
