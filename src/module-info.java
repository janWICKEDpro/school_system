module School_System {
	requires javafx.controls;
	requires java.sql;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	
	opens application to javafx.graphics, javafx.fxml, javafx.base;
	opens reports to  javafx.graphics, javafx.fxml, javafx.base;
	exports reports;
}
