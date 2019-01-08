package pl.edu.agh.student.oop.webcrawler.frontend.views.settings.presenter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import pl.edu.agh.student.oop.webcrawler.frontend.language.Language;
import pl.edu.agh.student.oop.webcrawler.frontend.util.ErrorMessage;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.EnumSet;
import java.util.ResourceBundle;

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

        try (BufferedWriter writer = Files.newBufferedWriter(Language.LANG_CONFIG_FILE)){
            writer.write(Language.getLocaleByName(lang));
            label.setText(ResourceBundle.getBundle("bundles.lang")
                    .getString("restart_app_info"));
        } catch (IOException e) {
            ErrorMessage.show("Cannot save language preferences", e);
        }
    }
}
