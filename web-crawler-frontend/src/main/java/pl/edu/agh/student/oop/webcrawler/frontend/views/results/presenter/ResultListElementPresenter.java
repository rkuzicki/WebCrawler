package pl.edu.agh.student.oop.webcrawler.frontend.views.results.presenter;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import pl.edu.agh.student.oop.webcrawler.frontend.util.DurationPrettyPrinter;
import pl.edu.agh.student.oop.webcrawler.frontend.views.results.model.Result;

import java.time.Duration;
import java.time.LocalDateTime;

public class ResultListElementPresenter {
    private final DurationPrettyPrinter durationPrinter = new DurationPrettyPrinter();

    @FXML
    private TextFlow matchedTextFlow;

    @FXML
    private Text timer;

    private Timeline timerTimeline;

    private Result item = null;

    {
        timerTimeline = new Timeline(new KeyFrame(javafx.util.Duration.seconds(1), event -> refreshTimer()));
        timerTimeline.setCycleCount(Timeline.INDEFINITE);
        timerTimeline.play();
    }

    private void refreshTimer() {
        if (item == null) {
            timer.setText("");
            return;
        }

        Duration duration = Duration.between(LocalDateTime.now(), item.getCreationDate());
        timer.setText(durationPrinter.prettyPrintAgo(duration));
    }

    public void update(Result item) {
        matchedTextFlow.getChildren().clear();
        matchedTextFlow.getChildren().add(new Text(item.getMatchedText()));

        this.item = item;
        refreshTimer();
    }
}
