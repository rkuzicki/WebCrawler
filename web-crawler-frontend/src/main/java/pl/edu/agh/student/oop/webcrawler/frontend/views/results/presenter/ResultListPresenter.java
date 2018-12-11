package pl.edu.agh.student.oop.webcrawler.frontend.views.results.presenter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import pl.edu.agh.student.oop.webcrawler.core.parser.Sentence;
import pl.edu.agh.student.oop.webcrawler.frontend.views.results.model.ImmutableResult;
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

    public void addHit(Sentence s, URI u) {
        ImmutableResult result =
                Result.builder().matchedText(s.toString())
                    .creationDate(LocalDateTime.now())
                    .source(u)
                    .build();

        items.add(result);
    }
}
