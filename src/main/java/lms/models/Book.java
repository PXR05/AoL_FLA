package lms.models;

public class Book {
    private final String title;
    private final String author;
    private final String isbn;
    private final String section;
    private boolean isAvailable;
    
    public Book(String title, String author, String isbn, boolean isAvailable, String section) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isAvailable = isAvailable;
        this.section = section;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    public boolean isAvailable() {
        return isAvailable;
    }
    
    public String getSection() {
        return section;
    }
    
    public void setAvailable(boolean available) {
        isAvailable = available;
    }
    
    @Override
    public String toString() {
        return title + " by " + author + " (ISBN: " + isbn + ") - " +
                (isAvailable ? "Available" : "Borrowed");
    }
}
