package lms.controllers;

import lms.models.*;
import lms.services.DB;
import java.util.List;
import java.util.stream.Collectors;

public class LoanController extends Controller<Loan> {
    private final DB db = DB.getInstance();

    public void add(Loan loan) {
        db.addLoan(loan);
    }

    public void remove(Loan loan) {
        db.removeLoan(loan);
    }

    public Loan find(String loanId) {
        return db.getLoans().stream()
                .filter(l -> l.getId().equals(loanId))
                .findFirst()
                .orElse(null);
    }
    
    public List<Loan> getAll() {
        return db.getLoans();
    }

    public List<Loan> getActiveLoans() {
        return db.getLoans().stream()
                .filter(loan -> !loan.isReturned())
                .collect(Collectors.toList());
    }

    public List<Loan> getLoansForUser(User user) {
        return db.getLoans().stream()
                .filter(loan -> loan.getUser().getId().equals(user.getId()))
                .collect(Collectors.toList());
    }
}