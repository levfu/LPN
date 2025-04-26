package Books;

import javafx.beans.property.SimpleStringProperty;

public class BookData {
    private final SimpleStringProperty isbn;
    private final SimpleStringProperty title;
    private final SimpleStringProperty author;
    private final SimpleStringProperty dueDate;
    private final SimpleStringProperty thumbnail;

    public BookData(String title, String author, String isbn, String dueDate, String thumbnail) {
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.isbn = new SimpleStringProperty(isbn);
        this.dueDate = new SimpleStringProperty(dueDate);
        this.thumbnail = new SimpleStringProperty(thumbnail);
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

    public String getDueDate() {
        return dueDate.get();
    }

    public String getThumbnail() {
        return thumbnail.get();  // Getter cho thumbnail
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

    public void setDueDate(String value) {
        dueDate.set(value);
    }

    public void setThumbnail(String value) {
        thumbnail.set(value);
    }
}