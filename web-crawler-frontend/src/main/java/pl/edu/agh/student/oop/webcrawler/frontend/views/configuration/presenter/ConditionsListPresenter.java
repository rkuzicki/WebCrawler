package pl.edu.agh.student.oop.webcrawler.frontend.views.configuration.presenter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import pl.edu.agh.student.oop.webcrawler.frontend.views.configuration.model.ConditionsListCell;
import pl.edu.agh.student.oop.webcrawler.frontend.views.configuration.model.ConditionsListItem;

public class ConditionsListPresenter {
    private ObservableList<ConditionsListItem> conditions = FXCollections.observableArrayList();

    @FXML
    private ListView<ConditionsListItem> conditionsListView;

    @FXML
    private void initialize() {
        conditionsListView.setCellFactory(ConditionsListCell::new);
        conditionsListView.setItems(conditions);
    }

    public void addCondition(ConditionsListItem condition) {
        conditions.add(condition);
    }

    public void deleteCondition() {
        ObservableList<ConditionsListItem> selected = conditionsListView.getSelectionModel().getSelectedItems();
        conditions.removeAll(selected);
    }

    public ListView<ConditionsListItem> getConditionsListView() {
        return this.conditionsListView;
    }
}
