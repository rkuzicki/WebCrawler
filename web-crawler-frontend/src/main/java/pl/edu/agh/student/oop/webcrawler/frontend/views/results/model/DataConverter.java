package pl.edu.agh.student.oop.webcrawler.frontend.views.results.model;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public class DataConverter {
    ObservableList<BarChartItem> items;
    ObservableList<XYChart.Data<String, Number>> data;

    public DataConverter( ObservableList<BarChartItem> items) {
        super();
        this.items = items;
        data =  FXCollections.observableArrayList();
        items.forEach(item -> data.add(new XYChart.Data<>(item.getName(), item.getValue())));
        items.addListener( ( ListChangeListener.Change<? extends BarChartItem> arg0 ) ->   update());
    }

    public void update() {
        for(int i =0; i < data.size(); i++){
            XYChart.Data<String, Number> d = data.get(i);
            d.setYValue(items.get(i).getValue());
        }
        data.clear();
        items.forEach(item -> data.add(new XYChart.Data<>(item.getName(), item.getValue())));
    }

    public ObservableList<XYChart.Data<String, Number>>getData(){
        return data;
    }
}
