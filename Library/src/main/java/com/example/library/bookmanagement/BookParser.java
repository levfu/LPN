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
                List<String> authors = new ArrayList<>();
                if( volumeInfo.has("authors")) {
                    for (var author : volumeInfo.getAsJsonArray("authors")) {
                        authors.add(author.getAsString());
                    }
                }
                String isbn = "";
                if (volumeInfo.has("industryIdentifiers")) {
                    for (var identifier : volumeInfo.getAsJsonArray("industryIdentifiers")) {
                        JsonObject idObj = identifier.getAsJsonObject();
                        if (idObj.has("type") && idObj.get("type").getAsString().equals("ISBN_13")) {
                            isbn = idObj.get("identifier").getAsString();
                            break;
                        }
                    }
                }
                bookList.add(new Book(title, authors,new ArrayList<>(), "", isbn));
            }
        }
        return bookList;
    }
}
