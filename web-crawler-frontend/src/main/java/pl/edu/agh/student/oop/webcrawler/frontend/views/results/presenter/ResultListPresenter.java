package pl.edu.agh.student.oop.webcrawler.frontend.views.results.presenter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import pl.edu.agh.student.oop.webcrawler.core.parser.Sentence;
import pl.edu.agh.student.oop.webcrawler.frontend.views.results.model.Result;
import pl.edu.agh.student.oop.webcrawler.frontend.views.results.model.ResultListCell;

import java.net.URI;
import java.time.LocalDateTime;

public class ResultListPresenter {
    private ObservableList<Result> items = FXCollections.observableArrayList();

    @FXML
    private ListView<Result> list;

    @FXML
    public void initialize() {
        list.setCellFactory(ResultListCell::new);
        list.setItems(items);
    }

    public void addResult(Sentence sentence, URI source) {
        Result result = Result.builder()
                .matchedText(sentence.toString())
                .creationDate(LocalDateTime.now())
                .source(source)
                .build();

        items.add(result);
    }
}
