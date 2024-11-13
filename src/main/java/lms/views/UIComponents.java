package lms.views;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lms.models.*;

public class UIComponents {
    @SuppressWarnings("unchecked")
    public static TableView<Book> createBookTableView() {
        TableView<Book> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        TableColumn<Book, String> isbnColumn = new TableColumn<>("ISBN");
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        
        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        
        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        
        TableColumn<Book, String> sectionColumn = new TableColumn<>("Section");
        sectionColumn.setCellValueFactory(new PropertyValueFactory<>("section"));
        
        final TableColumn<Book, Boolean> availabilityColumn = getAvailabilityColumn();
        
        tableView.getColumns().addAll(isbnColumn, titleColumn, authorColumn, sectionColumn, availabilityColumn);
        return tableView;
    }
    
    private static TableColumn<Book, Boolean> getAvailabilityColumn() {
        TableColumn<Book, Boolean> availabilityColumn = new TableColumn<>("Availability");
        availabilityColumn.setCellValueFactory(new PropertyValueFactory<>("available"));
        availabilityColumn.setCellFactory(column -> new TableCell<Book, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item ? "Available" : "Borrowed");
            }
        });
        return availabilityColumn;
    }
    
    @SuppressWarnings("unchecked")
    public static TableView<Loan> createLoanTableView() {
        TableView<Loan> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        TableColumn<Loan, String> isbnColumn = new TableColumn<>("ISBN");
        isbnColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getBook().getIsbn()));
        
        TableColumn<Loan, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getBook().getTitle()));
        
        TableColumn<Loan, String> borrowerColumn = new TableColumn<>("Borrower");
        borrowerColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getUser().getName()));
        
        TableColumn<Loan, String> dueDateColumn = new TableColumn<>("Due Date");
        dueDateColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getDueDate().toString()));
        
        TableColumn<Loan, String> feeColumn = new TableColumn<>("Fee");
        feeColumn.setCellValueFactory(cellData ->
            new SimpleStringProperty(String.format("%.2f", cellData.getValue().calculateFee()))
        );
        
        tableView.getColumns().addAll(isbnColumn, titleColumn, borrowerColumn, dueDateColumn, feeColumn);
        return tableView;
    }
}