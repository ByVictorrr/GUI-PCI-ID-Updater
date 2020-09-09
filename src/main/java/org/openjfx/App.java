package org.openjfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        // Step 1 - get username and password
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("welcome.fxml"));

        primaryStage.setTitle("PCI-ID Update Tool");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
    }

}