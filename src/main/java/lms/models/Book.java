package lms.models;

import lms.services.DB;

import java.util.ArrayList;

public class Book {
    private final DB db = DB.getInstance();
    private String title;
    private String author;
    private String isbn;
    private String section;
    private boolean isAvailable;
    
    public Book() {}
    
    public Book(String title, String author, String isbn, boolean isAvailable, String section) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isAvailable = isAvailable;
        this.section = section;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    public boolean isAvailable() {
        return isAvailable;
    }
    
    public String getSection() {
        return section;
    }
    
    public void setAvailable(boolean available) {
        this.isAvailable = available;
        String sql = "UPDATE books SET available = ? WHERE isbn = ?";
        try {
            var ps = db.prepare(sql);
            ps.setInt(1, available ? 1 : 0);
            ps.setString(2, isbn);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void remove() {
        String sql = "DELETE FROM books WHERE isbn = ?";
        try {
            var ps = db.prepare(sql);
            ps.setString(1, isbn);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void save() {
        String sql = "INSERT INTO books VALUES (?, ?, ?, ?, ?)";
        try {
            var ps = db.prepare(sql);
            ps.setString(1, isbn);
            ps.setString(2, title);
            ps.setString(3, author);
            ps.setInt(4, isAvailable ? 1 : 0);
            ps.setString(5, section);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public ArrayList<Book> getAll() {
        ArrayList<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try {
            var rs = db.prepare(sql).executeQuery();
            while (rs.next()) {
                Book book = new Book(
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(1),
                        rs.getInt(4) == 1,
                        rs.getString(5)
                );
                books.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }
    
    public Book find(String isbn) {
        String sql = "SELECT * FROM books WHERE isbn = ?";
        try {
            var ps = db.prepare(sql);
            ps.setString(1, isbn);
            var rs = ps.executeQuery();
            if (rs.next()) {
                return new Book(
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(1),
                        rs.getInt(4) == 1,
                        rs.getString(5)
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public String toString() {
        return title + " by " + author + " (ISBN: " + isbn + ") - " +
                (isAvailable ? "Available" : "Borrowed");
    }
}
