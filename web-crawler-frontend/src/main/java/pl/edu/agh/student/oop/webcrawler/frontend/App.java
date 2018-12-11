package pl.edu.agh.student.oop.webcrawler.frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pl.edu.agh.student.oop.webcrawler.frontend.util.ErrorMessage;

import java.io.IOException;
import java.util.ResourceBundle;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Web Crawler");
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.
                    getClassLoader().
                    getResource("views/MainPane.fxml"));
            loader.setResources(ResourceBundle.getBundle("bundles.lang"));
            BorderPane rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (IOException e)
        {
            ErrorMessage.show("Cannot load layout", e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
