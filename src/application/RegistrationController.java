package application;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
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

import javafx.scene.control.Label;

import javafx.scene.control.TabPane;

import javafx.scene.control.ComboBox;

import javafx.scene.control.Tab;

import javafx.scene.input.MouseEvent;

import javafx.scene.control.DatePicker;

import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration; 

public class RegistrationController implements Initializable{
	private ByteArrayInputStream bais;
	private String fileName;
	private Stage stage;
	private Scene scene;
	private Parent root;
	@FXML
	private Pane slidingPane;
	@FXML
	private Label home;
	@FXML
	private Label register;
	@FXML
	private Label slideHome;
	@FXML
	private TabPane tabPane;
	@FXML
	private Tab tabHome;
	@FXML
	private Tab tabRegister;
	@FXML
	private Button nextBtn;
	@FXML
	private TextField nameField;
	@FXML
	private TextField surnameField;
	@FXML
	private TextField nationalityField;
	@FXML
	private TextField pobFIeld;
	@FXML
	private DatePicker dobFIeld;
	@FXML
	private TextField parentnameFIeld;
	@FXML
	private TextField parentaddressField;
	@FXML
	private TextField divisionField;
	@FXML
	private TextField statusField;
	@FXML
	private Tab tabSpecification;
	@FXML
	private ComboBox<String> cycleField;
	@FXML
	private ComboBox<String> departmentField;
	@FXML
	private Button registerBtn;
	@FXML
	private ComboBox<String>  sexField;
	@FXML
	private Label errField; 
	@FXML
	private Label username;
	@FXML
	private Label regStatus;
	@FXML 
	private Button slipBtn;
	// Event Listener on Label[#home].onMouseClicked
	@FXML
	public void switchToHome(MouseEvent event) {
		TranslateTransition toHome = new TranslateTransition(new Duration(500), slideHome);
		toHome.setToX(slidingPane.getTranslateX());
		toHome.play();
		toHome.setOnFinished((ActionEvent e)->{
			slideHome.setText("Home");
		});
		tabPane.getSelectionModel().select(tabHome);
		
	}
	// Event Listener on Label[#register].onMouseClicked
	@FXML
	public void switchToRegister(MouseEvent event) {
		TranslateTransition toRegister = new TranslateTransition(new Duration(500), slideHome);
		toRegister.setToX(slidingPane.getTranslateX()+(slidingPane.getPrefWidth()-slideHome.getPrefWidth()));
		toRegister.play();
		toRegister.setOnFinished((ActionEvent e)->{
			slideHome.setText("Register");
		});
		tabPane.getSelectionModel().select(tabRegister);
	}
	public void logout(ActionEvent event) throws IOException {
		root  = FXMLLoader.load(getClass().getResource("Login.fxml"));
		stage =(Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	//switch to specifications tab
	public void switchToSpecification(ActionEvent e) {
		if(validate()) {
			errField.setText("");
			tabPane.getSelectionModel().select(tabSpecification);
		}
		else errField.setText("Some fields are empty");
	}
	// validate the form field
	public boolean validate() {
		if(nameField.getText().isEmpty() || surnameField.getText().isEmpty() || sexField.getSelectionModel().isEmpty() || dobFIeld.getTypeSelector().isEmpty() || nationalityField.getText().isEmpty() || statusField.getText().isEmpty())return false;
		return true;
	}
	
	//select pdf from files
	public void chooseFile(ActionEvent e) throws IOException {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().addAll(new ExtensionFilter("PDF File", "*pdf"));
		File file = fc.showOpenDialog(stage);
		fileName = file.getName();
		slipBtn.setText(fileName);
	    bais = new ByteArrayInputStream(getByteArray(file));
	
	}
	private byte[] getByteArray( File doc)throws IOException {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final InputStream is = new FileInputStream(doc);
		final byte[] buffer = new byte[10000000];
		int read = -1;
		while((read = is.read(buffer))>0) {
			baos.write(buffer, 0, read);
		}
		is.close();
		return baos.toByteArray();
	}
	// Register the student
	public void Register(ActionEvent e) throws IOException {
		DatabaseConnection database  = new DatabaseConnection();
		Connection con = database.getConnection();
		  String cycle[] = cycleField.getSelectionModel().getSelectedItem().split(" ");
		  String cycleId = cycle[0].substring(0, 1) + cycle[1].substring(0, 1) + cycle[2].substring(0,1);
		  String department[] =departmentField.getSelectionModel().getSelectedItem().split(" ");
		  String departmentId =  department[0].substring(0, 1) + department[1].substring(0, 1);
		  String classId = cycleId + departmentId;
		String query = "update student set studName ='"+nameField.getText()+"', studSurname  = '"+surnameField.getText()+"', sex = '"+sexField.getSelectionModel().getSelectedItem()+"', dob = '"+dobFIeld.getValue().toString()+"', nationality = '"+nationalityField.getText()+"', pob = '"+pobFIeld.getText()+"' , parentName = '"+parentnameFIeld.getText()+"' , parentAddress = '"+parentaddressField.getText()+"', division = '"+divisionField.getText()+"', maritalStatus = '"+statusField.getText()+"', cycleId  = '"+cycleId+"' , departmentId = '"+departmentId+"' , classId = '"+classId+"' , qualification = '"+bais+"', fileName ='"+fileName+"' where username = '"+username.getText()+"' ";
		System.out.println(cycleId);
		System.out.println(departmentId);
	    try {
	    	PreparedStatement statement = con.prepareStatement(query);
	    	
	    	int i = statement.executeUpdate(query);
	    	if(i == 1) {
	    		//regStatus.setText("Your Registration request is pending");
	    		TranslateTransition toHome = new TranslateTransition(new Duration(500), slideHome);
	    		toHome.setToX(slidingPane.getTranslateX());
	    		toHome.play();
	    		toHome.setOnFinished((ActionEvent ev)->{
	    			slideHome.setText("Home");
	    		});
	    		tabPane.getSelectionModel().select(tabHome);
	    		regStatus.setText("Your Registration is pending");
	    		System.out.println("welldone");
	    	}else {
	    		System.out.println("wene");
	    	}
	    	
	    }catch(Exception event) {
	    	System.out.println(event);
	    }
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		ObservableList<String> list = FXCollections.observableArrayList("Male", "Female");
		sexField.setItems(list);
		ObservableList<String> cycles = FXCollections.observableArrayList("Basic Technical Cycle", "Ordinary Technical Cycle", "Higher Technical Cycle");
		cycleField.setItems(cycles);
		ObservableList<String> departments = FXCollections.observableArrayList("Civil Engineering (CE)", "Rural Engineering (RE)", "Town Planning (TP)", "Land Surveying (LS)");
		departmentField.setItems(departments);
		
	}
	 
	public void getUser(String name) {
		username.setText(name);
	}
	public void studentStatus(String name) {
	try {
		DatabaseConnection con  = new DatabaseConnection();
		Connection connect = con.getConnection();
		String query = "Select * from student where username = '"+name+"' ";
		Statement ps = connect.createStatement();
		ResultSet rs = ps.executeQuery(query);
		while(rs.next()) {
			System.out.println("hi");
			if(rs.getString("regStatus") == null) {
				regStatus.setText("You are not yet registered");
			}
			else if(rs.getString("regStatus").contentEquals("registered")) {
				regStatus.setText("You are successfully Registered");
			}
			else {
				regStatus.setText("Your registration is pending");
			}
		}
	}catch(Exception e) {
		System.out.println(e);
	}
 }
}
