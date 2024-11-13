
package lms.services;

import lms.models.Book;
import lms.models.Loan;
import lms.models.User;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

public class FileIO {
    private static final String LOANS_FILE = "library_loans.txt";
    private static final String USERS_FILE = "library_users.txt";

    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        try {
            Path usersPath = Paths.get(USERS_FILE);
            if (Files.exists(usersPath)) {
                List<String> userLines = Files.readAllLines(usersPath);
                for (String line : userLines) {
                    String[] parts = line.split(",");
                    if (parts.length >= 2) {
                        User user = new User(parts[0], parts[1]);
                        users.add(user);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }
        return users;
    }

    public static List<Loan> loadLoans(List<Book> books, List<User> users) {
        List<Loan> loans = new ArrayList<>();
        try {
            Path loansPath = Paths.get(LOANS_FILE);
            if (Files.exists(loansPath)) {
                List<String> loanLines = Files.readAllLines(loansPath);
                for (String line : loanLines) {
                    String[] parts = line.split(",");
                    if (parts.length >= 6) {
                        String loanId = parts[0];
                        String userId = parts[1];
                        String bookIsbn = parts[2];
                        LocalDate borrowDate = LocalDate.parse(parts[3]);
                        LocalDate dueDate = LocalDate.parse(parts[4]);
                        boolean isReturned = Boolean.parseBoolean(parts[5]);
                        LocalDate returnDate = parts[6].equals("null") ? null : LocalDate.parse(parts[6]);

                        User user = users.stream().filter(u -> u.getId().equals(userId)).findFirst().orElse(null);
                        Book book = books.stream().filter(b -> b.getIsbn().equals(bookIsbn)).findFirst().orElse(null);

                        if (user != null && book != null) {
                            Loan loan = new Loan(loanId, user, book, borrowDate, dueDate, isReturned, returnDate);
                            loans.add(loan);
                            if (!isReturned) {
                                book.setAvailable(false);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading loans: " + e.getMessage());
        }
        return loans;
    }

    public static void saveUsers(List<User> users) {
        try {
            List<String> userLines = new ArrayList<>();
            for (User user : users) {
                userLines.add(user.getId() + "," + user.getName());
            }
            Files.write(Paths.get(USERS_FILE), userLines);
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }

    public static void saveLoans(List<Loan> loans) {
        try {
            List<String> loanLines = new ArrayList<>();
            for (Loan loan : loans) {
                loanLines.add(String.join(",",
                        loan.getId(),
                        loan.getUser().getId(),
                        loan.getBook().getIsbn(),
                        loan.getBorrowDate().toString(),
                        loan.getDueDate().toString(),
                        String.valueOf(loan.isReturned()),
                        loan.getReturnDate() == null ? "null" : loan.getReturnDate().toString()));
            }
            Files.write(Paths.get(LOANS_FILE), loanLines);
        } catch (IOException e) {
            System.err.println("Error saving loans: " + e.getMessage());
        }
    }
}