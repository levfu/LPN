package com.example.library.bookmanagement;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.List;
import java.util.ArrayList;
public class BookParser {
    public static List<Book> parseBooks(String jsonResponse) {
        List<Book> bookList = new ArrayList<>();
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
        if (jsonObject.has("items")) {
            for (var item : jsonObject.getAsJsonArray("items")) {
                JsonObject volumeInfo = item.getAsJsonObject().getAsJsonObject("volumeInfo");
                String title = volumeInfo.has("title") ? volumeInfo.get("title").getAsString() : "Unknown";
                String author = volumeInfo.has("authors") ? volumeInfo.getAsJsonArray("authors").get(0).getAsString() : "Unknown";
                String category = volumeInfo.has("categories") ? volumeInfo.getAsJsonArray("categories").get(0).getAsString() : "Unknown";
                String description = volumeInfo.has("description") ? volumeInfo.get("description").getAsString() : "No description";

                bookList.add(new Book(title, author, category, description));
            }
        }
        return bookList;
    }
}
