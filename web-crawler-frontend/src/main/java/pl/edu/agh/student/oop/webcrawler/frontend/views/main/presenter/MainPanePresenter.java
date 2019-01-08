package pl.edu.agh.student.oop.webcrawler.frontend.views.main.presenter;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import pl.edu.agh.student.oop.webcrawler.frontend.views.configuration.presenter.ConfigurationPresenter;
import pl.edu.agh.student.oop.webcrawler.frontend.views.results.presenter.ResultListPresenter;
import pl.edu.agh.student.oop.webcrawler.frontend.views.results.presenter.ResultViewPresenter;

public class MainPanePresenter {
    @FXML
    private TabPane tabPane;

    @FXML
    private Tab resultsTab;

    @FXML
    private ConfigurationPresenter configurationController;

    @FXML
    private ResultViewPresenter resultsController;

    @FXML
    private ResultListPresenter resultListController;

    @FXML
    private void initialize() {
        configurationController.setTabPane(tabPane);
        configurationController.setResultListController(
                resultsController.getResultListController());
        configurationController.setResultDiagramController(
                resultsController.getResultDiagramController());
    }
}
