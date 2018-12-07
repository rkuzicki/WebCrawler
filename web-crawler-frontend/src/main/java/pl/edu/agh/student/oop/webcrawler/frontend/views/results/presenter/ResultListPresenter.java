package pl.edu.agh.student.oop.webcrawler.frontend.views.results.presenter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import pl.edu.agh.student.oop.webcrawler.frontend.views.results.model.Result;
import pl.edu.agh.student.oop.webcrawler.frontend.views.results.model.ResultListCell;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;

public class ResultListPresenter {
    @FXML
    private ListView<Result> list;

    @FXML
    public void initialize() throws MalformedURLException {
        list.setCellFactory(ResultListCell::new);

        ObservableList<Result> items = FXCollections.observableArrayList(Result.builder()
                .matchedText("asdf")
                .creationDate(LocalDateTime.now())
                .source(new URL("http://google.com"))
                .build());
        list.setItems(items);
    }
}
