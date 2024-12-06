
package lms.views;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import lms.models.*;

public class LibrarianScreen extends BaseScreen {
    private TableView<Book> bookTableView;
    private TableView<Loan> loanTableView;

    @Override
    protected Region createContent() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        
        VBox bookManagement = createBookManagementPane();
        VBox loanManagement = createLoanManagementPane();
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> screenManager.showLoginScreen());
        
        root.setCenter(loanManagement);
        root.setRight(bookManagement);
        root.setBottom(logoutButton);
        BorderPane.setMargin(logoutButton, new Insets(10));
        
        return root;
    }

    private VBox createBookManagementPane() {
        VBox pane = new VBox(10);
        pane.setPadding(new Insets(10));
        
        Label isbnLabel = new Label("ISBN");
        isbnLabel.styleProperty().set("-fx-font-weight: bold;");
        TextField isbnField = new TextField();
        isbnField.setPromptText("ISBN");
        Label titleLabel = new Label("Title");
        titleLabel.styleProperty().set("-fx-font-weight: bold;");
        TextField titleField = new TextField();
        titleField.setPromptText("Title");
        Label authorLabel = new Label("Author");
        authorLabel.styleProperty().set("-fx-font-weight: bold;");
        TextField authorField = new TextField();
        authorField.setPromptText("Author");
        Label sectionLabel = new Label("Section");
        sectionLabel.styleProperty().set("-fx-font-weight: bold;");
        TextField sectionField = new TextField();
        sectionField.setPromptText("Section");

        Label books = new Label("Book List:");
        books.styleProperty().set("-fx-font-weight: bold;");
        bookTableView = UIComponents.createBookTableView();
        bookTableView.getItems().addAll(library.getBookController().getAll());
        
        Button addButton = new Button("Add New Book");
        addButton.setOnAction(e -> handleAddBook(isbnField, titleField, authorField, sectionField));
        
        Button removeButton = new Button("Remove Book");
        removeButton.setOnAction(e -> handleRemoveBook());
        
        Label header = new Label("Book Management");
        header.styleProperty().set("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        pane.getChildren().addAll(
            header,
            isbnLabel, isbnField,
            titleLabel, titleField,
            authorLabel, authorField,
            sectionLabel, sectionField,
            addButton, removeButton,
            new Separator(),
            books,
            bookTableView
        );
        return pane;
    }

    private VBox createLoanManagementPane() {
        VBox pane = new VBox(10);
        pane.setPadding(new Insets(10));
        
        loanTableView = UIComponents.createLoanTableView();
        loanTableView.getItems().addAll(library.getLoanController().getActiveLoans());
        
        Button returnButton = new Button("Handle Return");
        returnButton.setOnAction(e -> handleReturn());
        
        Label header = new Label("Loan Management");
        header.styleProperty().set("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        Label loans = new Label("Active Loans:");
        loans.styleProperty().set("-fx-font-weight: bold;");
        
        Label pastLoans = new Label("Past Loans:");
        pastLoans.styleProperty().set("-fx-font-weight: bold;");
        
        TableView<Loan> pastLoanTableView = UIComponents.createLoanTableView();
        pastLoanTableView.getItems().addAll(library.getLoanController().getInactiveLoans());
        
        pane.getChildren().addAll(
            header,
            loans, loanTableView,
            returnButton,
            new Separator(),
            pastLoans, pastLoanTableView
        );
        return pane;
    }

    private void handleAddBook(TextField... fields) {
        if (fields[0].getText().isEmpty() || fields[1].getText().isEmpty() || 
            fields[2].getText().isEmpty() || fields[3].getText().isEmpty()) {
            return;
        }
        
        Book newBook = new Book(fields[1].getText(), fields[2].getText(), 
                               fields[0].getText(), true, fields[3].getText());
        library.getLibrarianController().addNewBook(newBook);
        bookTableView.getItems().setAll(library.getBookController().getAll());
        
        for (TextField field : fields) {
            field.clear();
        }
    }

    private void handleRemoveBook() {
        Book selected = bookTableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            library.getLibrarianController().removeBook(selected);
            bookTableView.getItems().setAll(library.getBookController().getAll());
        }
    }

    private void handleReturn() {
        Loan selected = loanTableView.getSelectionModel().getSelectedItem();
        if (selected != null && !selected.isReturned()) {
            selected.returnBook();
            loanTableView.getItems().setAll(library.getLoanController().getActiveLoans());
            bookTableView.getItems().setAll(library.getBookController().getAll());
        }
    }

    @Override
    protected String getTitle() {
        return "LMS - Librarian";
    }
}