package pl.edu.agh.student.oop.webcrawler.frontend.views.results.presenter;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

public class ResultDiagramPresenter {

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    public void initialize() {
        xAxis.setLabel("condition");
        xAxis.setAutoRanging(true);
        yAxis.setLabel("occurrences");
        yAxis.setAutoRanging(true);
    }

}
