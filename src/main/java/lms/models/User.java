package lms.models;

import lms.Library;
import lms.controllers.LoanController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User extends Person {
    public User() {}
    
    public User(String id, String name, String password) {
        super(id, name, password);
    }
    
    public void borrowBook(Book book) {
        if (book.isAvailable()) {
            List<Loan> loans = new Loan().getAll("");
            String id = "L" + (loans.size() + 1);
            new Loan(
                    id,
                    this,
                    book,
                    LocalDate.now(),
                    LocalDate.now().plusDays(7),
                    false,
                    null
            ).save();
            book.setAvailable(false);
        }
    }
    
    public List<Loan> getBorrowedBooks() {
        List<Loan> loans = new ArrayList<>();
        String sql = "SELECT * FROM loans WHERE user_id = ? AND returned = 0";
        try {
            var ps = db.prepare(sql);
            ps.setString(1, id);
            var rs = ps.executeQuery();
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
}