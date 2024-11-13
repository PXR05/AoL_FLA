
package lms.services;

import lms.models.*;

import java.util.*;

public class DB {
    private static DB instance;
    private final List<User> users = new ArrayList<>();
    private final List<Book> books = new ArrayList<>();
    private final List<Loan> loans = new ArrayList<>();

    private DB() {
        loadData();
    }

    public static DB getInstance() {
        if (instance == null) {
            instance = new DB();
        }
        return instance;
    }

    private void loadData() {
        users.addAll(FileIO.loadUsers());
        loans.addAll(FileIO.loadLoans(books, users));
    }

    public void saveData() {
        FileIO.saveUsers(users);
        FileIO.saveLoans(loans);
    }

    // Users
    public void addUser(User user) {
        users.add(user);
        saveData();
    }

    public void removeUser(User user) {
        users.remove(user);
        saveData();
    }

    public List<User> getUsers() {
        return new ArrayList<>(users);
    }

    // Books 
    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    public List<Book> getBooks() {
        return new ArrayList<>(books);
    }

    // Loans
    public void addLoan(Loan loan) {
        loans.add(loan);
        saveData();
    }

    public void removeLoan(Loan loan) {
        loans.remove(loan);
        saveData();
    }

    public List<Loan> getLoans() {
        return new ArrayList<>(loans);
    }
}