package pl.edu.agh.student.oop.webcrawler.frontend.presenter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import pl.edu.agh.student.oop.webcrawler.frontend.language.ELanguage;

import java.util.EnumSet;

public class SettingsPresenter {



    @FXML
    private ChoiceBox<String> languageChoiceBox;

    @FXML
    private Button applyButton;

    @FXML
    private void initialize() {
        ObservableList<String> languages = FXCollections.observableArrayList();
        EnumSet.allOf(ELanguage.class)
                .forEach(language -> languages.add(language.getName()));
        languageChoiceBox.setItems(languages);
    }

}
