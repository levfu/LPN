package Books;

import javafx.beans.property.SimpleStringProperty;

public class BookData {
    private final SimpleStringProperty isbn;
    private final SimpleStringProperty title;
    private final SimpleStringProperty author;
    private final SimpleStringProperty dueDate;
    private final SimpleStringProperty thumbnail;
    private final SimpleStringProperty description;
    private final SimpleStringProperty tags;

    public BookData(String title, String author, String isbn, String dueDate, String thumbnail, String description, String tags) {
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.isbn = new SimpleStringProperty(isbn);
        this.dueDate = new SimpleStringProperty(dueDate);
        this.thumbnail = new SimpleStringProperty(thumbnail);
        this.description = new SimpleStringProperty(description);
        this.tags = new SimpleStringProperty(tags);
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

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String value) {
        description.set(value);
    }

    public String getTags() {
        return tags.get();
    }

    public void setTags(String value) {
        tags.set(value);
    }
}