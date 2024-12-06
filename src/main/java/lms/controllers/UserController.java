package lms.controllers;

import lms.models.Book;
import lms.models.Loan;
import lms.models.User;

import java.util.List;

public class UserController {
    public void borrowBook(User user, Book book) {
        user.borrowBook(book);
    }
    
    public List<Loan> getLoans(User user) {
        return user.getBorrowedBooks();
    }
}
