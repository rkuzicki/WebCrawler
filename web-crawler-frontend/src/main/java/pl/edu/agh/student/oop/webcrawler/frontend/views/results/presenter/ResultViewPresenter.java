package pl.edu.agh.student.oop.webcrawler.frontend.views.results.presenter;

import javafx.fxml.FXML;

public class ResultViewPresenter {
    @FXML
    private ResultListPresenter resultListController;

    @FXML
    private ResultDiagramPresenter resultDiagramController;

    public ResultListPresenter getResultListController() {
        return this.resultListController;
    }

    public ResultDiagramPresenter getResultDiagramController() { return this.resultDiagramController; }
}
