package lms;

import javafx.application.Application;
import javafx.stage.Stage;
import lms.views.ScreenManager;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        ScreenManager.getInstance().setPrimaryStage(primaryStage);
        ScreenManager.getInstance().showLoginScreen();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

