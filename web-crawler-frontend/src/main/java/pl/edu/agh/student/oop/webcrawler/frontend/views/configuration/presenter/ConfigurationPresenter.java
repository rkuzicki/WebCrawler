package pl.edu.agh.student.oop.webcrawler.frontend.views.configuration.presenter;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.edu.agh.student.oop.webcrawler.core.configuration.Configuration;
import pl.edu.agh.student.oop.webcrawler.frontend.input.Parser;
import pl.edu.agh.student.oop.webcrawler.frontend.views.configuration.model.ConditionsListItem;

import java.util.ArrayList;

public class ConfigurationPresenter {

    private static final String NEGATION_MARK = "~";
    private static final String EMPTY_STRING = "";

    private TabPane tabPane;

    private ObservableList<String> domains = FXCollections.observableArrayList();

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button deleteConditionButton;

    @FXML
    private TextField domainTextField;

    @FXML
    private ListView<String> domainListView;

    @FXML
    private Button acceptConditionButton;

    @FXML
    private Button searchButton;

    @FXML
    private ConditionsListPresenter listController;

    @FXML
    private TextField posConditionTextField;

    @FXML
    private TextField negConditionTextField;

    @FXML
    private TextField depthTextField;

    @FXML
    private void initialize() {
        domainListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        domainListView.setItems(domains);

        addButton.disableProperty().bind(Bindings.isEmpty(domainTextField.textProperty()));
        deleteButton.disableProperty().bind(Bindings
                .isEmpty(domainListView.getSelectionModel().getSelectedItems()));
        searchButton.disableProperty().bind(Bindings
                .isEmpty(domainListView.getItems())
                .or(Bindings.isEmpty(listController.getConditionsListView().getItems())));
        acceptConditionButton.disableProperty().bind(Bindings
                .isEmpty(posConditionTextField.textProperty())
                .and(Bindings.isEmpty(negConditionTextField.textProperty())));
        deleteConditionButton.disableProperty().bind(Bindings
                .isEmpty(listController.getConditionsListView().getItems()));

    }

    @FXML
    private void handleAddAction(ActionEvent event) {
        domains.add(domainTextField.getText());
    }

    @FXML
    private void handleDeleteAction(ActionEvent event) {
        for (String domain : domainListView.getSelectionModel().getSelectedItems()) {
            domains.remove(domain);
        }
    }

    @FXML
    private void handleDeleteConditionAction(ActionEvent event) {
        listController.deleteCondition();
    }

    @FXML
    private void handleAcceptConditionAction(ActionEvent event) {
        listController.addCondition(new ConditionsListItem(posConditionTextField.getText(),
                                                           negate(negConditionTextField.getText())));
    }

    @FXML
    private void handleSearchAction(ActionEvent event) {
        Configuration configuration = Parser.createConfiguration(
                depthTextField.getText(),
                new ArrayList<String>(domains),
                new ArrayList<ConditionsListItem>(listController
                                                  .getConditionsListView()
                                                  .getItems()));
        
        this.tabPane.getSelectionModel().select(1);
    }

    private String negate(String condition) {
        if(condition.equals(EMPTY_STRING)) return EMPTY_STRING;
        else return NEGATION_MARK + "(" + condition + ")";
    }

    public void setTabPane(TabPane tabPane) {
        this.tabPane = tabPane;
    }
}
