package pl.edu.agh.student.oop.webcrawler.frontend.views.results.presenter;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import pl.edu.agh.student.oop.webcrawler.core.matcher.Matcher;
import pl.edu.agh.student.oop.webcrawler.frontend.views.results.model.BarChartItem;
import pl.edu.agh.student.oop.webcrawler.frontend.views.results.model.DataConverter;

import java.util.*;

public class ResultDiagramPresenter {

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private BarChart<String, Number> barChart;

    private ObservableList<XYChart.Series<String, Number>> barChartData;

    private Map<Matcher, BarChartItem> matcherToBarChartItem = new HashMap<>();

    private Map<Matcher, String> matcherToString;
    private Map<String, Integer> stringToInteger = new HashMap<>();

    @FXML
    public void initialize() {
        barChartData = FXCollections.observableArrayList();
    }


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
//
//        for(Matcher key : matcherToString.keySet()) {
//            BarChartItem item = new BarChartItem(matcherToString.get(key), 0);
//            ObservableList<BarChartItem> items = FXCollections.observableArrayList(
//                    barChartItem -> new Observable[] { barChartItem.nameProperty(), barChartItem.valueProperty()});
//            items.add(item);
//            matcherToBarChartItem.put(key, item);
//
//            ObservableList<XYChart.Data<String, Number>> seriesData = new DataConverter(items).getData();
//            BarChart.Series<String, Number> series = new BarChart.Series<>();
//            series.setData(seriesData);
//            series.setName(item.getName());
//            barChartData.add(series);
//        }

        BarChart.Series<String, Number> series = new BarChart.Series<>();
        for(Matcher key: matcherToString.keySet()) {
            stringToInteger.put(matcherToString.get(key), 0);
            series.getData().add(new XYChart.Data<>(matcherToString.get(key), 0));
        }

        barChart.getData().add(series);
    }

}
