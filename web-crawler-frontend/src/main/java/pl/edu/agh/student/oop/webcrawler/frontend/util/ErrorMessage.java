package pl.edu.agh.student.oop.webcrawler.frontend.util;

import javafx.scene.control.Alert;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ErrorMessage {
    private ErrorMessage() {

    }

    public static void show(String message, Throwable cause) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error occurred");
        alert.setHeaderText(message);

        StringWriter stackTrace = new StringWriter();
        cause.printStackTrace(new PrintWriter(stackTrace));
        alert.setContentText(stackTrace.toString());

        alert.showAndWait();
    }
}
