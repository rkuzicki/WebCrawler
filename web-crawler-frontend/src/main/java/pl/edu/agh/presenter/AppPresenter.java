package pl.edu.agh.presenter;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pl.edu.agh.Main;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class AppPresenter {

    private Stage primaryStage;

    public AppPresenter() {

    }

    public AppPresenter(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initRootLayout() {
        try
        {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.
                    getClassLoader().
                    getResource("views/MainPane.fxml"));
            loader.setResources(ResourceBundle.getBundle("bundles.lang"));
            BorderPane rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            scene.getStylesheets().add(Main.class.getClassLoader().
                    getResource("css/main.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();

        }
        catch (IOException e)
        {
            //TODO exception handling
            e.printStackTrace();
        }
    }
}
