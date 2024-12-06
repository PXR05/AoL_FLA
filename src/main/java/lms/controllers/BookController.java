package lms.controllers;

import lms.models.Book;

import java.util.List;

public class BookController {
    private Book model;
    
    public BookController() {
        this.model = new Book();
    }
    
    public void add(Book book) {
        book.save();
    }

    public void remove(Book book) {
        book.remove();
    }

    public Book find(String isbn) {
        return model.find(isbn);
    }

    public List<Book> getAll() {
        return model.getAll();
    }
}