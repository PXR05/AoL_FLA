
package lms.factories;

import lms.Library;
import lms.models.Librarian;
import lms.models.Person;
import lms.models.User;

public class PersonFactory {
    private static final Library library = Library.getInstance();

    public static Person createPerson(String id, String type, String name, String password) {
        return switch (type.toLowerCase()) {
            case "admin" -> new Librarian(id, name, password);
            case "user" -> new User(id, name, password);
            default -> throw new IllegalArgumentException("Unknown person type: " + type);
        };
    }
}