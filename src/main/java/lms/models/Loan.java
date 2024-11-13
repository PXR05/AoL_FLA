package lms.models;

import lms.Library;

import java.time.LocalDate;

public class Loan {
    private final String id;
    private final User user;
    private final Book book;
    private final LocalDate borrowDate;
    private final LocalDate dueDate;
    private LocalDate returnDate;
    private boolean isReturned;
    
    public Loan(String id, User user, Book book, LocalDate borrowDate, LocalDate dueDate,
                boolean isReturned, LocalDate returnDate) {
        this.id = id;
        this.user = user;
        this.book = book;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.isReturned = isReturned;
        this.returnDate = returnDate;
    }
    
    public Loan(String id, User user, Book book) {
        this.id = id;
        this.user = user;
        this.book = book;
        this.borrowDate = LocalDate.now();
        this.dueDate = borrowDate.plusDays(7);
        this.isReturned = false;
    }
    
    public void returnBook() {
        this.returnDate = LocalDate.now();
        this.isReturned = true;
        this.book.setAvailable(true);
        calculateFee();
        Library.getInstance().getLoanController().remove(this);
    }
    
    public double calculateFee() {
        if (!isReturned) return 0;
        if (returnDate.isAfter(dueDate)) {
            long daysLate = returnDate.toEpochDay() - dueDate.toEpochDay();
            return daysLate * 5000; // 5k per day late
        }
        return 0;
    }

    public String getId() {
        return id;
    }
    
    public Book getBook() {
        return book;
    }
    
    public User getUser() {
        return user;
    }
    
    public boolean isReturned() {
        return isReturned;
    }
    
    public LocalDate getBorrowDate() {
        return borrowDate;
    }
    
    public LocalDate getDueDate() {
        return dueDate;
    }
    
    public LocalDate getReturnDate() {
        return returnDate;
    }
    
    @Override
    public String toString() {
        return book.getTitle() + " - Borrowed by: " + user.getName() +
                "\nBorrowed: " + borrowDate +
                ", Due: " + dueDate +
                (isReturned ? ", Returned: " + returnDate : ", Not returned") +
                (calculateFee() > 0 ? " (Late fee: $" + calculateFee() + ")" : "");
    }
}