
package lms.services;

import lms.models.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class DB {
    public ResultSet rs;
    public ResultSetMetaData rsm;
    
    private Statement st;
  
    private static DB instance;

    private DB() {
        try {
            Connection con = DriverManager.getConnection("jdbc:sqlite:lms.db");
            st = con.createStatement();
            st.execute("CREATE TABLE IF NOT EXISTS users " +
                    "(id TEXT PRIMARY KEY, " +
                    "name TEXT, " +
                    "password TEXT, " +
                    "type TEXT DEFAULT 'user')");
            st.execute("CREATE TABLE IF NOT EXISTS books " +
                    "(isbn TEXT PRIMARY KEY, " +
                    "title TEXT, " +
                    "author TEXT, " +
                    "available INTEGER, " +
                    "section TEXT)");
            st.execute("CREATE TABLE IF NOT EXISTS loans " +
                    "(id TEXT PRIMARY KEY, " +
                    "user_id TEXT, " +
                    "book_isbn TEXT, " +
                    "borrow_date TEXT, " +
                    "due_date TEXT, " +
                    "return_date TEXT, " +
                    "returned INTEGER, " +
                    "FOREIGN KEY(user_id) REFERENCES users(id), " +
                    "FOREIGN KEY(book_isbn) REFERENCES books(isbn))");
            seed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void seed() throws SQLException {
        st.execute("INSERT OR IGNORE INTO users VALUES ('ADMIN', 'admin', 'admin', 'admin')");
        st.execute("INSERT OR IGNORE INTO users VALUES ('U1', 'user', 'password', 'user')");
        st.execute("INSERT OR IGNORE INTO books VALUES ('978-1-56619-909-4', 'Book A', 'Author A', 1, 'Fiction')");
        st.execute("INSERT OR IGNORE INTO books VALUES ('978-1-56619-909-5', 'Book B', 'Author B', 1, 'Non-Fiction')");
        st.execute("INSERT OR IGNORE INTO books VALUES ('978-1-56619-909-6', 'Book C', 'Author C', 1, 'Reference')");
    }
    
    public PreparedStatement prepare(String sql) throws SQLException {
        return st.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    }

    public static DB getInstance() {
        if (instance == null) {
            instance = new DB();
        }
        return instance;
    }
}
