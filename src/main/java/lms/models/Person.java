package lms.models;

import lms.factories.PersonFactory;
import lms.services.DB;

import java.util.ArrayList;
import java.util.List;

public abstract class Person {
    protected final DB db = DB.getInstance();
    protected String id;
    protected String name;
    protected String password;
    
    public Person() {}
    
    public Person(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }
    
    public String getName() {
        return name;
    }
    
    public String getId() {
        return id;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void save() {
        String sql = "INSERT INTO users (name, password) VALUES (?, ?)";
        try {
            var ps = db.prepare(sql);
            ps.setString(1, name);
            ps.setString(2, password);
            ps.executeUpdate();
            var rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void remove() {
        String sql = "DELETE FROM users WHERE id = ?";
        try {
            var ps = db.prepare(sql);
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<Person> getAll() {
        List<Person> persons = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try {
            var rs = db.prepare(sql).executeQuery();
            while (rs.next()) {
                Person user = PersonFactory.createPerson(
                        rs.getString(1),
                        rs.getString(4),
                        rs.getString(2),
                        rs.getString(3)
                );
                persons.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return persons;
    }
    
    public Person find(String id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try {
            var ps = db.prepare(sql);
            ps.setString(1, id);
            var rs = ps.executeQuery();
            if (rs.next()) {
                Person user = PersonFactory.createPerson(
                        rs.getString(1),
                        rs.getString(4),
                        rs.getString(2),
                        rs.getString(3)
                );
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Person login(String name, String password) {
        String sql = "SELECT * FROM users WHERE name = ? AND password = ?";
        try {
            var ps = db.prepare(sql);
            ps.setString(1, name);
            ps.setString(2, password);
            var rs = ps.executeQuery();
            if (rs.next()) {
                Person user = PersonFactory.createPerson(
                        rs.getString(1),
                        rs.getString(4),
                        rs.getString(2),
                        rs.getString(3)
                );
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public String toString() {
        return name + " (ID: " + id + ")" + " - " + password + " - " + getClass().getSimpleName();
    }
}
