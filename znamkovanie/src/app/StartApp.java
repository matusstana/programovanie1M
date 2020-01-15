package app;

import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartApp extends Application
{
  @Override
  public void start(Stage primaryStage) throws Exception
  {
    primaryStage.setTitle("Známkovanie");
    Parent root = FXMLLoader.load(getClass().getResource("../fxml/Main.fxml"));
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
    
  }

  public static void main(String[] args)
  {
    launch(args);
  }
  
}
