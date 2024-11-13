package lms.models;

import lms.Library;

public class Librarian extends Person {
    private final Library library;
    
    public Librarian(String id, String name, Library library) {
        super(id, name);
        this.library = library;
    }
    
    public void addNewBook(Book book) {
        library.getBookController().add(book);
    }
    
    public void removeBook(Book book) {
        library.getBookController().remove(book);
    }
}
