package lms.controllers;

import lms.models.Person;
import lms.models.User;
import lms.services.DB;

import java.util.List;

public class PersonController {
    private final Person model = new User();
    
    public void add(Person user) {
        user.save();
    }

    public void remove(Person user) {
        user.remove();
    }

    public Person find(String userId) {
        return model.find(userId);
    }

    public List<Person> getAll() {
        return model.getAll();
    }
    
    public Person login(String name, String password) {
        return model.login(name, password);
    }
}