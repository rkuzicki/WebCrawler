package pl.edu.agh.student.oop.webcrawler.frontend.views.configuration.presenter;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.edu.agh.student.oop.webcrawler.core.configuration.Configuration;
import pl.edu.agh.student.oop.webcrawler.frontend.input.InputParser;
import pl.edu.agh.student.oop.webcrawler.frontend.views.configuration.model.ConditionsListItem;

import java.util.ArrayList;

public class ConfigurationPresenter {

    private static final String NEGATION_MARK = "~";
    private static final String EMPTY_STRING = "";

    private TabPane tabPane;

    private ObservableList<String> domains = FXCollections.observableArrayList();
    private ObservableList<String> webPages = FXCollections.observableArrayList();

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
    private TextField webPageTextField;

    @FXML
    private ListView<String> webPagesListView;

    @FXML
    private Button addWebPageButton;

    @FXML
    private Button deleteWebPageButton;

    @FXML
    private CheckBox subdomainsCheckBox;

    @FXML
    private TextField depthTextField;

    @FXML
    private void initialize() {
        domainListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        webPagesListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        domainListView.setItems(domains);
        webPagesListView.setItems(webPages);

        addButton.disableProperty().bind(Bindings.isEmpty(domainTextField.textProperty()));
        deleteButton.disableProperty().bind(Bindings
                .isEmpty(domainListView.getSelectionModel().getSelectedItems()));
        searchButton.disableProperty().bind(Bindings
                .isEmpty(domainListView.getItems())
                .or(Bindings.isEmpty(listController.getConditionsListView().getItems()))
                .or(Bindings.isEmpty(webPagesListView.getItems()))
                .or(Bindings.isEmpty(depthTextField.textProperty())));
        acceptConditionButton.disableProperty().bind(Bindings
                .isEmpty(posConditionTextField.textProperty())
                .and(Bindings.isEmpty(negConditionTextField.textProperty())));
        deleteConditionButton.disableProperty().bind(Bindings
                .isEmpty(listController.getConditionsListView().getItems()));
        addWebPageButton.disableProperty().bind(Bindings
                .isEmpty(webPageTextField.textProperty()));
        deleteWebPageButton.disableProperty().bind(Bindings
                .isEmpty(webPagesListView.getSelectionModel().getSelectedItems()));

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
    private void handleAddWebPageAction(ActionEvent event) {
        webPages.add(webPageTextField.getText());
    }

    @FXML
    private void handleDeleteWebPageAction(ActionEvent event) {
        for (String webPage : webPagesListView.getSelectionModel().getSelectedItems()) {
            webPages.remove(webPage);
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
        Configuration configuration = new InputParser().createConfiguration(
                new ArrayList<>(listController.getConditionsListView().getItems()),
                new ArrayList<>(domains),
                new ArrayList<>(webPages),
                depthTextField.getText(),
                subdomainsCheckBox.isSelected());

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
