package pl.edu.agh.student.oop.webcrawler.frontend.views.results.presenter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import pl.edu.agh.student.oop.webcrawler.frontend.views.results.model.Result;
import pl.edu.agh.student.oop.webcrawler.frontend.views.results.model.ResultListCell;

public class ResultListPresenter {
    @FXML
    private ListView<Result> list;

    @FXML
    public void initialize() {
        list.setCellFactory(ResultListCell::new);

        ObservableList<Result> items = FXCollections.observableArrayList(
                new Result());
        list.setItems(items);
    }
}
