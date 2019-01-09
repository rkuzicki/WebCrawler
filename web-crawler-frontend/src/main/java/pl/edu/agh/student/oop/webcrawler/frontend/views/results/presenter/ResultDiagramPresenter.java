package pl.edu.agh.student.oop.webcrawler.frontend.views.results.presenter;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import pl.edu.agh.student.oop.webcrawler.frontend.util.UserInputtedMatcher;

import java.util.List;

public class ResultDiagramPresenter {

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private BarChart<String, Number> barChart;

    private List<UserInputtedMatcher> matchers;

    @FXML
    public void initialize() {}

    public void addResult(UserInputtedMatcher matcher) {
        String string = matcher.getUserInput();

        for(XYChart.Series<String, Number> series : barChart.getData()) {
            for(XYChart.Data<String, Number> data : series.getData()) {
                if(data.getXValue().equals(string))
                    data.setYValue(data.getYValue().intValue() + 1);
            }
        }
    }

    public void initializeAxis(List<UserInputtedMatcher> matchers) {
        this.matchers = matchers;
        BarChart.Series<String, Number> series = new BarChart.Series<>();
        for(UserInputtedMatcher key: matchers) {
            series.getData().add(new XYChart.Data<>(key.getUserInput(), 0));
        }
        barChart.getData().add(series);
    }
}
