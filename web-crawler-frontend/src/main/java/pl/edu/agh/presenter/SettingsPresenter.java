package pl.edu.agh.presenter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import pl.edu.agh.language.ELanguage;

import java.util.EnumSet;
import java.util.List;
import java.util.Observable;

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
