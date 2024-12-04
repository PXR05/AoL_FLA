package lms.views;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import lms.factories.PersonFactory;
import lms.models.*;

public class RegisterScreen extends BaseScreen {
    @Override
    protected Region createContent() {
        VBox registerRoot = new VBox(10);
        registerRoot.setPadding(new Insets(10));
        
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        
        Button registerButton = new Button("Register");
        registerButton.setOnAction(e -> handleRegister(usernameField.getText(), passwordField.getText()));
        
        Hyperlink loginLink = new Hyperlink("Already have an account? Login here");
        loginLink.setOnAction(e -> screenManager.showLoginScreen());
        
        Label header = new Label("LMS");
        header.styleProperty().set("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        registerRoot.getChildren().addAll(
                header,
                new Label("Register"),
                usernameField,
                passwordField,
                registerButton,
                loginLink
        );
        return registerRoot;
    }
    
    private void handleRegister(String username, String password) {
        User user = library.getUserController().find(username);
        if (user == null) {
            // TODO: ADD USER TO DB
            user = (User) PersonFactory.createPerson("user", username, username);
            library.getUserController().add(user);
        }
        screenManager.showLoginScreen();
    }
    
    @Override
    protected String getTitle() {
        return "LMS - Register";
    }
}