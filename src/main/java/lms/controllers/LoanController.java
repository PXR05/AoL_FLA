package lms.controllers;

import lms.models.*;
import lms.services.DB;
import java.util.List;
import java.util.stream.Collectors;

public class LoanController {
    private final Loan model = new Loan();

    public void add(Loan loan) {
        loan.save();
    }

    public void remove(Loan loan) {
        loan.remove();
    }

    public Loan find(String loanId) {
        return model.find(loanId);
    }
    
    public List<Loan> getAll() {
        return model.getAll("");
    }

    public List<Loan> getActiveLoans() {
        return model.getAll("0");
    }
    
    public List<Loan> getInactiveLoans() {
        return model.getAll("1");
    }
}