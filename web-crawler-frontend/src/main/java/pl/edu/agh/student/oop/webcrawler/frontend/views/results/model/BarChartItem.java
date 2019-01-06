package pl.edu.agh.student.oop.webcrawler.frontend.views.results.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BarChartItem {
    private StringProperty name = new SimpleStringProperty(this, "name");
    private IntegerProperty value = new SimpleIntegerProperty(this, "value");

    public BarChartItem(String name, int value) {
        this.name.set(name);
        this.value.setValue(value);
    }


    public StringProperty nameProperty() {
        return this.name;
    }

    public IntegerProperty valueProperty() {
        return this.value;
    }

    public String getName() {
        return this.nameProperty().get();
    }

    public int getValue() {
        return this.valueProperty().get();
    }

    public void setValue(int value) {
        this.valueProperty().set(value);
    }

    public void inc() {
        this.valueProperty().setValue(this.valueProperty().get()+1);
    }
}
