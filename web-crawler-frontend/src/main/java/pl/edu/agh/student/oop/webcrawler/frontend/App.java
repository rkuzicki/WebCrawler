package pl.edu.agh.student.oop.webcrawler.frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.edu.agh.student.oop.webcrawler.frontend.language.Language;
import pl.edu.agh.student.oop.webcrawler.frontend.util.ErrorMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.Locale;
import java.util.ResourceBundle;

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
            Parent rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            ErrorMessage.show("Cannot load layout", e);
        }
    }

    private void setLanguage() {
        if (Files.exists(Language.LANG_CONFIG_FILE)) {
            try (BufferedReader reader = Files.newBufferedReader(Language.LANG_CONFIG_FILE)) {
                Locale.setDefault(new Locale(reader.readLine()));
                return;
            } catch (IOException e) {

            }
        }

        InputStream defaultLang = ClassLoader.getSystemResourceAsStream(DEFAULT_LANG);
        if (defaultLang != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(defaultLang))) {
                Locale.setDefault(new Locale(reader.readLine()));
            } catch (IOException e) {

            }
        }

        System.out.println("Couldn't load language. Loading en_en");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
