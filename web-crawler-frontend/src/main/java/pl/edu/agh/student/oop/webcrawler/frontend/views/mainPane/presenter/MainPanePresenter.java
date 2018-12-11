package pl.edu.agh.student.oop.webcrawler.frontend.views.mainPane.presenter;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import pl.edu.agh.student.oop.webcrawler.frontend.views.configuration.presenter.ConfigurationPresenter;

public class MainPanePresenter {

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab resultsTab;

    @FXML
    private void initialize() {
        configurationController.setTabPane(tabPane);
    }

    @FXML
    private ConfigurationPresenter configurationController;

    public void switchToResultsTab() {
        tabPane.getSelectionModel().select(resultsTab);
    }
}
