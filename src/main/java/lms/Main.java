package lms;

import javafx.application.Application;
import javafx.stage.Stage;
import lms.models.*;
import lms.views.ScreenManager;

public class Main extends Application {
    private final Library library = Library.getInstance();
    
    @Override
    public void start(Stage primaryStage) {
        ScreenManager.getInstance().setPrimaryStage(primaryStage);
        ScreenManager.getInstance().showLoginScreen();
    }
    


    public static void main(String[] args) {
        launch(args);
    }
}

