
package lms.factories;

import lms.views.BaseScreen;
import lms.views.LibrarianScreen;
import lms.views.LoginScreen;
import lms.views.UserScreen;

public class ScreenFactory {
    public static BaseScreen createScreen(String type) {
        return switch (type.toLowerCase()) {
            case "login" -> new LoginScreen();
            case "librarian" -> new LibrarianScreen();
            case "user" -> new UserScreen();
            default -> throw new IllegalArgumentException("Unknown screen type: " + type);
        };
    }
}