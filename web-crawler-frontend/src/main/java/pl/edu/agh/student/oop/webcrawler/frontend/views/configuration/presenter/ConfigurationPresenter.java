package pl.edu.agh.student.oop.webcrawler.frontend.views.configuration.presenter;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.edu.agh.student.oop.webcrawler.core.Crawler;
import pl.edu.agh.student.oop.webcrawler.core.configuration.Configuration;
import pl.edu.agh.student.oop.webcrawler.frontend.input.InputParser;
import pl.edu.agh.student.oop.webcrawler.frontend.views.configuration.model.ConditionsListItem;
import pl.edu.agh.student.oop.webcrawler.frontend.views.results.presenter.ResultListPresenter;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class ConfigurationPresenter {

    private static final String NEGATION_MARK = "~";
    private static final String EMPTY_STRING = "";

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
        for (String domain : domainListView.getSelectionModel().getSelectedItems()) {
            domains.remove(domain);
        }
    }

    @FXML
    private void handleAddStartingPointAction(ActionEvent event) {
        try
        {
            startingPoints.add(new URI(startingPointTextField.getText()));
        }
        catch (URISyntaxException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteStartingPointAction(ActionEvent event) {
        for (URI startingPoint : startingPointsListView.getSelectionModel().getSelectedItems()) {
            startingPoints.remove(startingPoint);
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
                new ArrayList<>(startingPoints),
                depthTextField.getText(),
                subdomainsCheckBox.isSelected());

        Crawler crawler = new Crawler(configuration,
                (sentence, uri) -> resultListController.addHit(sentence, uri));

        crawler.start();
        this.tabPane.getSelectionModel().select(1);
    }

    private String negate(String condition) {
        if(condition.equals(EMPTY_STRING)) return EMPTY_STRING;
        else return NEGATION_MARK + "(" + condition + ")";
    }

    public void setTabPane(TabPane tabPane) {
        this.tabPane = tabPane;
    }

    public void setResultListController(ResultListPresenter controller) {
        this.resultListController = controller;
    }

}
