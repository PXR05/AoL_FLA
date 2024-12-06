package lms.controllers;

import lms.models.Book;
import lms.models.Librarian;

public class LibrarianController {
    private final Librarian model = new Librarian();
    
    public void addNewBook(Book book) {
        model.addNewBook(book);
    }
    
    public void removeBook(Book book) {
        model.removeBook(book);
    }
}
