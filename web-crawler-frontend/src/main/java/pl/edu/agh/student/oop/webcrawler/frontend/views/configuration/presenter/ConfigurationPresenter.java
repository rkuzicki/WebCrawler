package pl.edu.agh.student.oop.webcrawler.frontend.views.configuration.presenter;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.edu.agh.student.oop.webcrawler.core.Crawler;
import pl.edu.agh.student.oop.webcrawler.core.configuration.Configuration;
import pl.edu.agh.student.oop.webcrawler.core.matcher.Matcher;
import pl.edu.agh.student.oop.webcrawler.frontend.input.InputConditionsParser;
import pl.edu.agh.student.oop.webcrawler.frontend.views.configuration.model.ConditionsListItem;
import pl.edu.agh.student.oop.webcrawler.frontend.views.results.presenter.ResultListPresenter;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class ConfigurationPresenter {
    private static final String NEGATION_MARK = "~";

    private TabPane tabPane;

    private ObservableList<String> domains = FXCollections.observableArrayList();
    private ObservableList<URI> startingPoints = FXCollections.observableArrayList();

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
    private TextField startingPointTextField;

    @FXML
    private ListView<URI> startingPointsListView;

    @FXML
    private Button addStartingPointButton;

    @FXML
    private Button deleteStartingPointButton;

    @FXML
    private CheckBox subdomainsCheckBox;

    @FXML
    private TextField depthTextField;

    private ResultListPresenter resultListController;

    @FXML
    private void initialize() {
        domainListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        startingPointsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        domainListView.setItems(domains);
        startingPointsListView.setItems(startingPoints);

        addButton.disableProperty().bind(Bindings.isEmpty(domainTextField.textProperty()));
        deleteButton.disableProperty().bind(Bindings
                .isEmpty(domainListView.getSelectionModel().getSelectedItems()));
        searchButton.disableProperty().bind(Bindings
                .isEmpty(domainListView.getItems())
                .or(Bindings.isEmpty(listController.getConditionsListView().getItems()))
                .or(Bindings.isEmpty(startingPointsListView.getItems()))
                .or(Bindings.isEmpty(depthTextField.textProperty())));
        acceptConditionButton.disableProperty().bind(Bindings
                .isEmpty(posConditionTextField.textProperty())
                .and(Bindings.isEmpty(negConditionTextField.textProperty())));
        deleteConditionButton.disableProperty().bind(Bindings
                .isEmpty(listController.getConditionsListView().getItems()));
        addStartingPointButton.disableProperty().bind(Bindings
                .isEmpty(startingPointTextField.textProperty()));
        deleteStartingPointButton.disableProperty().bind(Bindings
                .isEmpty(startingPointsListView.getSelectionModel().getSelectedItems()));

    }

    @FXML
    private void handleAddAction(ActionEvent event) {
        domains.add(domainTextField.getText());
    }

    @FXML
    private void handleDeleteAction(ActionEvent event) {
        domains.removeAll(domainListView.getSelectionModel().getSelectedItems());
    }

    @FXML
    private void handleAddStartingPointAction(ActionEvent event) {
        try {
            startingPoints.add(new URI(startingPointTextField.getText()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteStartingPointAction(ActionEvent event) {
        startingPoints.removeAll(startingPointsListView.getSelectionModel().getSelectedItems());
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
        List<ConditionsListItem> conditionItems = listController.getConditionsListView().getItems();
        Matcher matcher = new InputConditionsParser().parseConditions(conditionItems);

        Configuration configuration = Configuration.builder().matcher(matcher)
                .domains(domains)
                .startingPoints(startingPoints)
                .depth(Integer.parseInt(depthTextField.getText()))
                .subdomainsEnabled(subdomainsCheckBox.isSelected())
                .build();

        Crawler crawler = new Crawler(configuration, (sentence, uri) ->
                Platform.runLater(() ->
                        resultListController.addResult(sentence, uri)));

        crawler.start();
        this.tabPane.getSelectionModel().select(1);
    }

    private String negate(String condition) {
        if (condition.isEmpty()) {
            return "";
        } else {
            return NEGATION_MARK + "(" + condition + ")";
        }
    }

    public void setTabPane(TabPane tabPane) {
        this.tabPane = tabPane;
    }

    public void setResultListController(ResultListPresenter controller) {
        this.resultListController = controller;
    }
}
