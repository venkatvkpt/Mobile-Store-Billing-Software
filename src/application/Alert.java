package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Alert {
	static String printer;
	public static void makeAlert(String title,String msg) {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(250.0);
		window.setMinHeight(120.0);

		Label label = new Label();
		label.setText(msg);
		label.setFont(Font.font(null, FontWeight.NORMAL, 16));

		Button close = new Button("Close the Window");
		close.setOnAction(e -> window.close());

		VBox layout = new VBox(10);
		layout.getChildren().addAll(label,close);
		layout.setAlignment(Pos.CENTER);

		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}
	
	

}
