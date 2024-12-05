package lms;

import lms.controllers.*;
import lms.services.DB;

public class Library {
    private static Library instance;
    private final BookController bookController;
    private final PersonController personController;
    private final LoanController loanController;
    private final DB db;

    private Library() {
        db = DB.getInstance();
        bookController = new BookController();
        personController = new PersonController();
        loanController = new LoanController();
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

    public PersonController getUserController() {
        return personController;
    }

    public LoanController getLoanController() {
        return loanController;
    }
}