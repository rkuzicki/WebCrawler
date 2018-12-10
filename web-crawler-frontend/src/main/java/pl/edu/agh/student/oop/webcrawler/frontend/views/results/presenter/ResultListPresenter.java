package pl.edu.agh.student.oop.webcrawler.frontend.views.results.presenter;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.util.Duration;
import pl.edu.agh.student.oop.webcrawler.frontend.views.results.model.ImmutableResult;
import pl.edu.agh.student.oop.webcrawler.frontend.views.results.model.Result;
import pl.edu.agh.student.oop.webcrawler.frontend.views.results.model.ResultListCell;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Random;

public class ResultListPresenter {
    @FXML
    private ListView<Result> list;

    @FXML
    public void initialize() throws URISyntaxException {
        list.setCellFactory(ResultListCell::new);

        ImmutableResult result = Result.builder()
                .matchedText("asdf")
                .creationDate(LocalDateTime.now())
                .source(new URI("http://google.com"))
                .build();
        ObservableList<Result> items = FXCollections.observableArrayList(result);

        Random random = new Random();
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(random.nextInt(5000) + 1000),
                ae -> {
                    items.add(0, Result.builder()
                            .from(result)
                            .creationDate(LocalDateTime.now())
                            .build());
                }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        list.setItems(items);
    }
}
