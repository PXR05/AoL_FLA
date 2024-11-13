package lms.controllers;

import lms.models.User;
import lms.services.DB;

import java.util.List;

public class UserController extends Controller<User> {
    private final DB db = DB.getInstance();

    public void add(User user) {
        db.addUser(user);
    }

    public void remove(User user) {
        db.removeUser(user);
    }

    public User find(String userId) {
        return db.getUsers().stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    public List<User> getAll() {
        return db.getUsers();
    }
}