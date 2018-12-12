package pl.edu.agh.student.oop.webcrawler.frontend.views.results.presenter;

import javafx.fxml.FXML;

public class ResultViewPresenter {
    @FXML
    private ResultListPresenter resultListController;

    public ResultListPresenter getResultListController() {
        return this.resultListController;
    }
}
