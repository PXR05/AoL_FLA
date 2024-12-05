
package lms.views;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import lms.models.*;

public class UserScreen extends BaseScreen {
    private TableView<Book> availableBooksTable;
    private TableView<Loan> borrowedBooksTable;
    
    @Override
    protected Region createContent() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        
        Label welcomeLabel = new Label("Welcome, " + screenManager.getCurrentUser().getName());
        
        availableBooksTable = UIComponents.createBookTableView();
        availableBooksTable.getItems().addAll(library.getBookController().getAll());
        
        Button borrowButton = new Button("Borrow Selected Book");
        borrowButton.setOnAction(e -> handleBorrow());
        
        borrowedBooksTable = UIComponents.createLoanTableView();
        borrowedBooksTable.getItems().addAll(((User)screenManager.getCurrentUser()).getBorrowedBooks());
        
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> screenManager.showLoginScreen());
        
        Label avail = new Label("Available Books:");
        avail.styleProperty().set("-fx-font-weight: bold;");
        
        Label borrowed = new Label("Borrowed Books:");
        borrowed.styleProperty().set("-fx-font-weight: bold;");
        
        root.getChildren().addAll(
            welcomeLabel,
            avail,
            availableBooksTable,
            borrowButton,
            new Separator(),
            borrowed,
            borrowedBooksTable,
            logoutButton
        );
        return root;
    }

    private void handleBorrow() {
        Book selected = availableBooksTable.getSelectionModel().getSelectedItem();
        if (selected != null && selected.isAvailable()) {
            ((User)screenManager.getCurrentUser()).borrowBook(selected);
            availableBooksTable.getItems().setAll(library.getBookController().getAll());
            borrowedBooksTable.getItems().setAll(((User)screenManager.getCurrentUser()).getBorrowedBooks());
        }
    }

    @Override
    protected String getTitle() {
        return "LMS - User";
    }
}