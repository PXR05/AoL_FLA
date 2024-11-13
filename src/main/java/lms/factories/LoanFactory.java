package lms.factories;

import lms.Library;
import lms.controllers.LoanController;
import lms.models.Book;
import lms.models.Loan;
import lms.models.User;

public class LoanFactory {
    private static final LoanController loanController = Library.getInstance().getLoanController();
    
    public static Loan createLoan(User user, Book book) {
        String id = "L" + (loanController.getAll().size() + 1);
        book.setAvailable(false);
        return new Loan(id, user, book);
    }
}
