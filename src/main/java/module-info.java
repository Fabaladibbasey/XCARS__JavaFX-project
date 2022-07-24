module com.example.xcarsdemo {
	requires javafx.controls;
	requires javafx.fxml;


	opens com.example.xcarsdemo to javafx.fxml;
	exports com.example.xcarsdemo;
	exports com.example.xcarsdemo.model;
	opens com.example.xcarsdemo.model to javafx.fxml;
}