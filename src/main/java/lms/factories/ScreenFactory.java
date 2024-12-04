
package lms.factories;

import lms.views.*;

public class ScreenFactory {
    public static BaseScreen createScreen(String type) {
        return switch (type.toLowerCase()) {
            case "login" -> new LoginScreen();
            case "register" -> new RegisterScreen();
            case "librarian" -> new LibrarianScreen();
            case "user" -> new UserScreen();
            default -> throw new IllegalArgumentException("Unknown screen type: " + type);
        };
    }
}