package lms.models;

import lms.Library;
import lms.controllers.BookController;

public class Librarian extends Person {
    private final BookController bookController = new BookController();
    
    public Librarian() {
    }
    
    public Librarian(String id, String name, String password) {
        super(id, name, password);
    }
    
    public void addNewBook(Book book) {
        bookController.add(book);
    }
    
    public void removeBook(Book book) {
        bookController.remove(book);
    }
}
