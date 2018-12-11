package pl.edu.agh.student.oop.webcrawler.frontend.presenter;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pl.edu.agh.student.oop.webcrawler.frontend.Main;
import pl.edu.agh.student.oop.webcrawler.frontend.util.ErrorMessage;

import java.io.IOException;
import java.util.ResourceBundle;

public class AppPresenter {
    private final Stage primaryStage;

    public AppPresenter(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.
                    getClassLoader().
                    getResource("views/MainPane.fxml"));
            loader.setResources(ResourceBundle.getBundle("bundles.lang"));
            BorderPane rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            ErrorMessage.show("Cannot load layout", e);
        }
    }

}
