package lms;

import javafx.application.Application;
import javafx.stage.Stage;
import lms.models.*;
import lms.views.ScreenManager;

public class Main extends Application {
    private final Library library = Library.getInstance();
    
    @Override
    public void start(Stage primaryStage) {
        initializeSampleData();
        ScreenManager.getInstance().setPrimaryStage(primaryStage);
        ScreenManager.getInstance().showLoginScreen();
    }
    
    private void initializeSampleData() {
        if (library.getBookController().getAll().isEmpty()) {
            Book book1 = new Book("Book A", "Author A", "123456", true, "Fiction");
            Book book2 = new Book("Book B", "Author B", "789012", true, "Non-Fiction");
            Book book3 = new Book("Book C", "Author C", "345678", true, "Reference");
            
            library.getBookController().add(book1);
            library.getBookController().add(book2);
            library.getBookController().add(book3);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

