package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

import javafx.application.HostServices;
import javafx.event.ActionEvent;

import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class AdminController {
	private Parent root;
	private Scene scene;
	private Stage stage;
	
	private HostServices hostServices;
	public HostServices getHostServices() {
		return hostServices;
	}
	public void setHostServices(HostServices hostServices) {
		this.hostServices = hostServices;
	}
	@FXML
	private Rectangle loginBtn;
	@FXML
	private Button regBtn;
	@FXML
	private Button reportBtn;
	@FXML
	private Button logouttBn;

	//switch to register students page
	@FXML
	public void getRegisterPage(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterStudent.fxml"));
		root  = loader.load();
		RegisterStudentController cont = loader.getController();
		cont.setHostServices(hostServices);
		stage =(Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	public void logout(ActionEvent event) throws IOException {
		root  = FXMLLoader.load(getClass().getResource("Login.fxml"));
		stage =(Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	public void getAddCourse(ActionEvent event) throws IOException {
		root  = FXMLLoader.load(getClass().getResource("AddCourse.fxml"));
		stage =(Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	// Go to reports page
	@FXML
	public void getReportsPage(ActionEvent event) throws IOException {	FXMLLoader loader = new FXMLLoader(getClass().getResource("report.fxml"));
	root  = loader.load();
	ReportController cont = loader.getController();
	cont.setHostServices(hostServices);
	stage =(Stage)((Node)event.getSource()).getScene().getWindow();
	scene = new Scene(root);
	stage.setScene(scene);
	stage.show();}
	// Logout from system
	
}
