package Books;

import java.util.List;

public class Book {
    private String title;
    private List<String> authors;
    private List<String> categories;
    private String description;
    private String isbn;
    private String thumbnail;

    public Book(String title, List<String> authors, List<String> categories, String description, String isbn, String thumbnail) {
        this.title = title;
        this.authors = authors;
        this.categories = categories;
        this.description = description;
        this.isbn = isbn;
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthor() {
        return authors;
    }

    public void setAuthor(List<String> authors) {
        this.authors = authors;
    }

    public List<String> getCategory() {
        return categories;
    }

    public void setCategory(List<String> categories) {
        this.categories = categories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String toString() {
        return title + " - " + String.join(", ", authors) + "(" + String.join(", ", categories) + ")" + description + " - " + isbn;
    }
}
