package com.example.library.bookmanagement;

import javafx.beans.property.SimpleStringProperty;

public class BookData {
    private final SimpleStringProperty isbn;
    private final SimpleStringProperty title;
    private final SimpleStringProperty author;
    private final SimpleStringProperty dueDate;

    public BookData(String title, String author, String isbn, String dueDate) {
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.isbn = new SimpleStringProperty(isbn);
        this.dueDate = new SimpleStringProperty(dueDate);
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
}
