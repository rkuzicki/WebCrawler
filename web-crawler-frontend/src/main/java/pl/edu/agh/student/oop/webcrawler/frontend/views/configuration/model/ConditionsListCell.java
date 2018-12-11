package pl.edu.agh.student.oop.webcrawler.frontend.views.configuration.model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import pl.edu.agh.student.oop.webcrawler.frontend.App;
import pl.edu.agh.student.oop.webcrawler.frontend.util.ErrorMessage;
import pl.edu.agh.student.oop.webcrawler.frontend.views.configuration.presenter.ConditionsListElementPresenter;

import java.io.IOException;
import java.util.ResourceBundle;

public class ConditionsListCell extends ListCell<ConditionsListItem> {

    private final ConditionsListElementPresenter presenter;
    private final ListView<ConditionsListItem> listView;
    private final Node node;

    public ConditionsListCell(ListView<ConditionsListItem> listView) {
        this.listView = listView;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class
                .getClassLoader()
                .getResource("views/tabs/configuration/ConditionsListElement.fxml"));
        loader.setResources(ResourceBundle.getBundle("bundles.lang"));

        try
        {
            this.node = loader.load();
            this.presenter = loader.getController();
        }
        catch (IOException e)
        {
            ErrorMessage.show("Cannot load FXML", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void updateItem(ConditionsListItem item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) {
            presenter.update(item);
            setGraphic(node);
        } else {
            setGraphic(null);
        }
    }
}
