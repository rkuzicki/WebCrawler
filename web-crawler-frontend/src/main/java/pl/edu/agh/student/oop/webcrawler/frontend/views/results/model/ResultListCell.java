package pl.edu.agh.student.oop.webcrawler.frontend.views.results.model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import pl.edu.agh.student.oop.webcrawler.frontend.Main;
import pl.edu.agh.student.oop.webcrawler.frontend.util.ErrorMessage;

import java.io.IOException;
import java.util.ResourceBundle;

public class ResultListCell extends ListCell<Result> {
    private final ListView<Result> listView;
    private final Node node;

    public ResultListCell(ListView<Result> listView) {
        this.listView = listView;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.
                getClassLoader().
                getResource("views/results/ResultListElement.fxml"));
        loader.setResources(ResourceBundle.getBundle("bundles.lang"));
        try {
            this.node = loader.load();
        } catch (IOException e) {
            ErrorMessage.show("Cannot load FXML", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void updateItem(Result item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) {
            setGraphic(node);
        } else {
            setGraphic(null);
        }
    }
}
