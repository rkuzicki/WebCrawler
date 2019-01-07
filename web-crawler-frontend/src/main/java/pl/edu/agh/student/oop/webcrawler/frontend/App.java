package pl.edu.agh.student.oop.webcrawler.frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pl.edu.agh.student.oop.webcrawler.frontend.util.ErrorMessage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class App extends Application {
    private final static String DEFAULT_LANG = "defaultLang.txt";
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Web Crawler");

        setLanguage();

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.
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

    private void setLanguage() {
        URL url = getClass().getClassLoader().getResource(DEFAULT_LANG);
        File file = new File(url.getPath());
        Scanner sc = null;
        try {
            sc = new Scanner(file);
            Locale.setDefault(new Locale(sc.nextLine()));
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't load language. Loading en_en");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
