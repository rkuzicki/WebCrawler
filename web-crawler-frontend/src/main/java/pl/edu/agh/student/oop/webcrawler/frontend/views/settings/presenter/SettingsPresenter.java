package pl.edu.agh.student.oop.webcrawler.frontend.views.settings.presenter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import pl.edu.agh.student.oop.webcrawler.frontend.App;
import pl.edu.agh.student.oop.webcrawler.frontend.language.Language;

import java.io.*;
import java.net.URL;
import java.util.EnumSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class SettingsPresenter {

    @FXML
    private ChoiceBox<String> languageChoiceBox;

    @FXML
    private Button applyButton;

    @FXML
    private Label label;

    @FXML
    private void initialize() {
        ObservableList<String> languages = FXCollections.observableArrayList();
        EnumSet.allOf(Language.class)
                .forEach(language -> languages.add(language.getName()));
        languageChoiceBox.setItems(languages);

    }

    @FXML
    private void handleApplyAction(ActionEvent event) {
        String lang = languageChoiceBox.getValue();


        URL url = getClass().getClassLoader().getResource("defaultLang.txt");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(url.getPath()));
            writer.write(Language.getLocaleByName(lang));
            writer.close();
            label.setText(ResourceBundle.getBundle("bundles.lang").getString("restart_app_info"));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
