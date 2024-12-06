package lms;

import lms.controllers.*;

public class Library {
    private static Library instance;
    private final BookController bookController;
    private final PersonController personController;
    private final LoanController loanController;
    private final UserController userController;
    private final LibrarianController librarianController;

    private Library() {
        bookController = new BookController();
        personController = new PersonController();
        loanController = new LoanController();
        userController = new UserController();
        librarianController = new LibrarianController();
    }

    public static Library getInstance() {
        if (instance == null) {
            instance = new Library();
        }
        return instance;
    }

    public BookController getBookController() {
        return bookController;
    }

    public PersonController getPersonController() {
        return personController;
    }

    public LoanController getLoanController() {
        return loanController;
    }
    
    public UserController getUserController() {
        return userController;
    }
    
    public LibrarianController getLibrarianController() {
        return librarianController;
    }
}