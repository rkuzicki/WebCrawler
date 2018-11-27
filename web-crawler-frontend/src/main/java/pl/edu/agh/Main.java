import controller.WebCrawlerAppController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage primaryStage;
    private WebCrawlerAppController appController;


    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Web Crawler");

        this.appController = new WebCrawlerAppController(primaryStage);
        this.appController.initRootLayout();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
