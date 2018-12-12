package pl.edu.agh.student.oop.webcrawler.frontend.views.configuration.presenter;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import pl.edu.agh.student.oop.webcrawler.frontend.views.configuration.model.ConditionsListItem;

public class ConditionsListElementPresenter {
    @FXML
    private Text positiveSubCondition;

    @FXML
    private Text negativeSubCondition;

    @FXML
    private VBox vBox;

    public void update(ConditionsListItem item) {
        this.positiveSubCondition.setText(item.getPositiveSubCondition());
        this.negativeSubCondition.setText(item.getNegativeSubCondition());
    }
}
