package pl.edu.agh.student.oop.webcrawler.frontend;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.edu.agh.student.oop.webcrawler.frontend.presenter.AppPresenter;

public class Main extends Application {

    private Stage primaryStage;
    private AppPresenter presenter;

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Web Crawler");

        this.presenter = new AppPresenter(primaryStage);
        this.presenter.initRootLayout();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
