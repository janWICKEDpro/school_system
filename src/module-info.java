module School_System {
	requires javafx.controls;
	requires java.sql;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	//requires org.apache.poi.ooxml;
	
	opens application to javafx.graphics, javafx.fxml, javafx.base;
	opens reports to  javafx.graphics, javafx.fxml, javafx.base;
	exports reports;
}
