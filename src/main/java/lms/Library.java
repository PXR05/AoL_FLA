package lms;

import lms.controllers.*;
import lms.services.DB;

public class Library {
    private static Library instance;
    private final BookController bookController;
    private final UserController userController;
    private final LoanController loanController;
    private final DB db;

    private Library() {
        db = DB.getInstance();
        bookController = new BookController();
        userController = new UserController();
        loanController = new LoanController();
    }

    public static Library getInstance() {
        if (instance == null) {
            instance = new Library();
        }
        return instance;
    }

    public void saveData() {
        db.saveData();
    }

    public BookController getBookController() {
        return bookController;
    }

    public UserController getUserController() {
        return userController;
    }

    public LoanController getLoanController() {
        return loanController;
    }
}