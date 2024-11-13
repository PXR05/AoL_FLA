package lms.models;

import lms.Library;
import lms.controllers.LoanController;
import lms.factories.LoanFactory;

import java.util.List;

public class User extends Person {
    public User(String id, String name) {
        super(id, name);
    }
    
    public void borrowBook(Book book) {
        if (book.isAvailable()) {
            Loan loan = LoanFactory.createLoan(this, book);
            ((LoanController)Library.getInstance().getLoanController()).add(loan);
        }
    }
    
    public List<Loan> getBorrowedBooks() {
        return Library.getInstance().getLoanController().getLoansForUser(this);
    }
}