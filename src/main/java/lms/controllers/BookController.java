package lms.controllers;

import lms.models.Book;
import lms.services.DB;

import java.util.List;

public class BookController extends Controller<Book> {
    private final DB db = DB.getInstance();

    public void add(Book book) {
        db.addBook(book);
    }

    public void remove(Book book) {
        db.removeBook(book);
    }

    public Book find(String isbn) {
        return db.getBooks().stream()
                .filter(b -> b.getIsbn().equals(isbn))
                .findFirst()
                .orElse(null);
    }

    public List<Book> getAll() {
        return db.getBooks();
    }
}