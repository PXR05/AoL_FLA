package lms.models;

import lms.services.DB;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Loan {
    private final DB db = DB.getInstance();
    private String id;
    private User user;
    private Book book;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private boolean isReturned;
    
    public Loan() {}
    
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
    
    public void returnBook() {
        this.returnDate = LocalDate.now();
        this.isReturned = true;
        calculateFee();
        this.book.setAvailable(true);
        String sql = "UPDATE loans SET return_date = ?, returned = 1 WHERE id = ?";
        try {
            var ps = db.prepare(sql);
            ps.setString(1, returnDate.toString());
            ps.setString(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void save() {
        String sql = "INSERT INTO loans (id, user_id, book_isbn, borrow_date, due_date, return_date, returned) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            var ps = db.prepare(sql);
            ps.setString(1, id);
            ps.setString(2, user.getId());
            ps.setString(3, book.getIsbn());
            ps.setString(4, borrowDate.toString());
            ps.setString(5, dueDate.toString());
            ps.setString(6, returnDate == null ? "null" : returnDate.toString());
            ps.setInt(7, isReturned ? 1 : 0);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void remove() {
        String sql = "DELETE FROM loans WHERE id = ?";
        try {
            var ps = db.prepare(sql);
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<Loan> getAll(String active) {
        List<Loan> loans = new ArrayList<>();
        String sql = active.isEmpty() ? "SELECT * FROM loans" : "SELECT * FROM loans WHERE returned = " + active;
        try {
            var rs = db.prepare(sql).executeQuery();
            while (rs.next()) {
                User user = (User) new User().find(rs.getString("user_id"));
                Book book = new Book().find(rs.getString("book_isbn"));
                loans.add(new Loan(
                        rs.getString("id"),
                        user,
                        book,
                        LocalDate.parse(rs.getString("borrow_date")),
                        LocalDate.parse(rs.getString("due_date")),
                        rs.getInt("returned") == 1,
                        Objects.equals(rs.getString("return_date"), "null") ? null : LocalDate.parse(rs.getString("return_date"))
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loans;
    }
    
    public Loan find(String id) {
        DB db = DB.getInstance();
        String sql = "SELECT * FROM loans WHERE id = ?";
        try {
            var ps = db.prepare(sql);
            ps.setString(1, id);
            var rs = ps.executeQuery();
            if (rs.next()) {
                User user = (User) new User().find(rs.getString("user_id"));
                Book book = new Book().find(rs.getString("book_isbn"));
                return new Loan(
                        rs.getString("id"),
                        user,
                        book,
                        LocalDate.parse(rs.getString("borrow_date")),
                        LocalDate.parse(rs.getString("due_date")),
                        rs.getInt("returned") == 1,
                        rs.getString("return_date") == null ? null : LocalDate.parse(rs.getString("return_date"))
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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