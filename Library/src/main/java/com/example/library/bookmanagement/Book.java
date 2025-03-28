package com.example.library.bookmanagement;

public class Book {
    private String title;
    private String author;
    private String category;
    private String description;

    public Book (String title, String author, String category, String description) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        return title + "-" + author + "(" + category + ")" + description;
    }
}
