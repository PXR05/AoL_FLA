package lms.views;

import javafx.stage.Stage;
import lms.factories.ScreenFactory;
import lms.models.Person;

public class ScreenManager {
    private static ScreenManager instance;
    private Stage primaryStage;
    private Person currentUser;

    private ScreenManager() {}

    public static ScreenManager getInstance() {
        if (instance == null) {
            instance = new ScreenManager();
        }
        return instance;
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setCurrentUser(Person user) {
        this.currentUser = user;
    }

    public Person getCurrentUser() {
        return currentUser;
    }

    public void showLoginScreen() {
        ScreenFactory.createScreen("login").show();
    }
    
    public void showRegisterScreen() {
        ScreenFactory.createScreen("register").show();
    }

    public void showLibrarianScreen() {
        ScreenFactory.createScreen("librarian").show();
    }

    public void showUserScreen() {
        ScreenFactory.createScreen("user").show();
    }
}