package pl.edu.agh.student.oop.webcrawler.frontend.views.configuration.presenter;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;
import pl.edu.agh.student.oop.webcrawler.core.configuration.Configuration;
import pl.edu.agh.student.oop.webcrawler.core.crawler.Crawler;
import pl.edu.agh.student.oop.webcrawler.core.crawler.Statistics;
import pl.edu.agh.student.oop.webcrawler.core.matcher.Matcher;
import pl.edu.agh.student.oop.webcrawler.core.parser.Sentence;
import pl.edu.agh.student.oop.webcrawler.frontend.input.InputConditionsParser;
import pl.edu.agh.student.oop.webcrawler.frontend.views.configuration.model.ConditionsListItem;
import pl.edu.agh.student.oop.webcrawler.frontend.views.results.presenter.ResultDiagramPresenter;
import pl.edu.agh.student.oop.webcrawler.frontend.views.results.presenter.ResultListPresenter;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ConfigurationPresenter {
    private static final String NEGATION_MARK = "~";

    private TabPane tabPane;
    private Label statistics;

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

    private ResultDiagramPresenter resultDiagramController;

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
        Map<Matcher, String>  matcherToString = new InputConditionsParser().parseConditions(conditionItems);

        resultDiagramController.initializeAxis(matcherToString);

        Configuration configuration = Configuration.builder()
                .matchers(new ArrayList<>(matcherToString.keySet()))
                .domains(domains)
                .startingPoints(startingPoints)
                .depth(Integer.parseInt(depthTextField.getText()))
                .subdomainsEnabled(subdomainsCheckBox.isSelected())
                .matchListener((sentence, uri, matcher) ->
                        Platform.runLater(() -> updateResults(sentence, uri, matcher)))
                .build();

        Crawler crawler = new Crawler(configuration);
        setupStatisticsUpdater(crawler);

        crawler.start();
        this.tabPane.getSelectionModel().select(1);
    }

    private void setupStatisticsUpdater(Crawler crawler) {
        Timeline fiveSecondsWonder = new Timeline(
                new KeyFrame(Duration.millis(200),
                event -> statistics.setText(getStatisticsText(crawler.statistics()))));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();
    }

    private String getStatisticsText(Statistics stat) {
        Instant last1Second = Instant.now().minus(1, ChronoUnit.SECONDS);
        Instant last5Seconds = Instant.now().minus(5, ChronoUnit.SECONDS);
        Instant last15Seconds = Instant.now().minus(15, ChronoUnit.SECONDS);
        String down = "Download: " +
                speedToString(stat.getDownloadStatisticFrom(last1Second).speed()) + " " +
                speedToString(stat.getDownloadStatisticFrom(last5Seconds).speed()) + " " +
                speedToString(stat.getDownloadStatisticFrom(last15Seconds).speed());
        String crawl = "Crawl: " +
                speedToString(stat.getCrawlStatisticFrom(last1Second).speed()) + " " +
                speedToString(stat.getCrawlStatisticFrom(last5Seconds).speed()) + " " +
                speedToString(stat.getCrawlStatisticFrom(last15Seconds).speed());

        return down + "\n" + crawl;
    }

    private String speedToString(double speed) {
        DecimalFormat format = new DecimalFormat("###0.0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        if (speed < 0.1) {
            return "~0 B/s";
        } else if (speed < 100) {
            return format.format(speed) + " B/s";
        } else if (speed < 100e3) {
            return format.format(speed / 1e3) + " KB/s";
        } else if (speed < 100e6) {
            return format.format(speed / 1e6) + " MB/s";
        } else if (speed < 100e9) {
            return format.format(speed / 1e9) + " GB/s";
        } else if (Double.isNaN(speed)) {
            return "?";
        } else {
            return format.format(speed / 1e12) + " TB/s";
        }
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

    public void setStatisticsLabel(Label statistics) {
        this.statistics = statistics;
    }

    public void setResultListController(ResultListPresenter controller) {
        this.resultListController = controller;
    }

    public void setResultDiagramController(ResultDiagramPresenter controller) { this.resultDiagramController = controller; }

    private void updateResults(Sentence sentence, URI uri, Matcher matcher) {
        resultListController.addResult(sentence, uri);
        resultDiagramController.addResult(matcher);
    }

}
