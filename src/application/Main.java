package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws  Exception {
		
			//BorderPane root = new BorderPane();
			Parent root = FXMLLoader.load(getClass().getResource("Application.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Image icon = new Image(getClass().getResourceAsStream("/resources/icon.png"));
			primaryStage.getIcons().add(icon);
			
			primaryStage.setTitle("Billing Manager");
			primaryStage.setMaximized(true);
			//primaryStage.setFullScreen(true);
			primaryStage.setScene(scene);
			primaryStage.show();
		
	}
	
	
}
