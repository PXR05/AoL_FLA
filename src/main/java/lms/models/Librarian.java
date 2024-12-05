package lms.models;

import lms.Library;

public class Librarian extends Person {
    private final Library library;
    
    public Librarian() {
        this.library = Library.getInstance();
    }
    
    public Librarian(String id, String name, String password) {
        super(id, name, password);
        this.library = Library.getInstance();
    }
    
    public void addNewBook(Book book) {
        library.getBookController().add(book);
    }
    
    public void removeBook(Book book) {
        library.getBookController().remove(book);
    }
}
