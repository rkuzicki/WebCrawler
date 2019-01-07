package pl.edu.agh.student.oop.webcrawler.frontend.views.results.presenter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import pl.edu.agh.student.oop.webcrawler.core.matcher.Matcher;

import java.util.*;

public class ResultDiagramPresenter {

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private BarChart<String, Number> barChart;

    private Map<Matcher, String> matcherToString;

    @FXML
    public void initialize() {}

    public void addResult(Matcher matcher) {
        String string = matcherToString.get(matcher);

        for(XYChart.Series<String, Number> series : barChart.getData()) {
            for(XYChart.Data<String, Number> data : series.getData()) {
                if(data.getXValue().equals(string))
                    data.setYValue(data.getYValue().intValue() + 1);
            }
        }
    }

    public void initializeAxis(Map<Matcher, String> matcherToString) {
        this.matcherToString = matcherToString;
        BarChart.Series<String, Number> series = new BarChart.Series<>();
        for(Matcher key: matcherToString.keySet()) {
            series.getData().add(new XYChart.Data<>(matcherToString.get(key), 0));
        }
        barChart.getData().add(series);
    }

}
