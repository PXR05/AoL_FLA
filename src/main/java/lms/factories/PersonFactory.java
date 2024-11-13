
package lms.factories;

import lms.Library;
import lms.models.Librarian;
import lms.models.Person;
import lms.models.User;

public class PersonFactory {
    private static final Library library = Library.getInstance();

    public static Person createPerson(String type, String id, String name) {
        return switch (type.toLowerCase()) {
            case "librarian" -> new Librarian(id, name, library);
            case "user" -> new User(id, name);
            default -> throw new IllegalArgumentException("Unknown person type: " + type);
        };
    }
}