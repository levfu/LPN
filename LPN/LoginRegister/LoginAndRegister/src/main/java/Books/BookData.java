package Books;

import javafx.beans.property.SimpleStringProperty;

public class BookData {
    private final SimpleStringProperty isbn;
    private final SimpleStringProperty title;
    private final SimpleStringProperty author;

    public BookData(String title, String author, String isbn) {
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.isbn = new SimpleStringProperty(isbn);
    }

    public String getTitle() {
        return title.get();
    }

    public String getAuthor() {
        return author.get();
    }

    public String getIsbn() {
        return isbn.get();
    }

    public void setTitle(String value) {
               title.set(value);
    }

    public void setAuthor(String value) {
           author.set(value);
    }

    public void setIsbn(String value) {
        isbn.set(value);
    }
}
