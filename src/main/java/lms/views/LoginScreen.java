package lms.views;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import lms.factories.PersonFactory;
import lms.models.*;

import java.util.Objects;

public class LoginScreen extends BaseScreen {
    @Override
    protected Region createContent() {
        VBox loginRoot = new VBox(10);
        loginRoot.setPadding(new Insets(10));
        
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        
        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> handleLogin(usernameField.getText(), passwordField.getText()));
        
        Hyperlink registerLink = new Hyperlink("Don't have an account? Register here");
        registerLink.setOnAction(e -> screenManager.showRegisterScreen());
        
        Label header = new Label("LMS");
        header.styleProperty().set("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        loginRoot.getChildren().addAll(
                header,
                new Label("Login"),
                usernameField,
                passwordField,
                loginButton,
                registerLink
        );
        return loginRoot;
    }

    private void handleLogin(String username, String password) {
        Person user = library.getUserController().login(username, password);
        if (user != null) {
            if (Objects.equals(user.getId(), "ADMIN")) {
                screenManager.setCurrentUser(PersonFactory.createPerson("ADMIN", "admin", "admin", "admin"));
                screenManager.showLibrarianScreen();
            } else {
                screenManager.setCurrentUser(user);
                screenManager.showUserScreen();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid username or password");
            alert.showAndWait();
        }
    }

    @Override
    protected String getTitle() {
        return "LMS - Login";
    }
}