package Books;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class BookParser {

    public static List<Book> parseBooks(String jsonResponse) {
        List<Book> bookList = new ArrayList<>();

        try {
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

            if (jsonObject.has("items")) {
                JsonArray itemsArray = jsonObject.getAsJsonArray("items");

                for (JsonElement itemElement : itemsArray) {
                    JsonObject itemObject = itemElement.getAsJsonObject();
                    JsonObject volumeInfo = itemObject.getAsJsonObject("volumeInfo");

                    String title = volumeInfo.has("title") ? volumeInfo.get("title").getAsString() : "Unknown";

                    List<String> authors = new ArrayList<>();
                    if (volumeInfo.has("authors")) {
                        for (JsonElement authorElement : volumeInfo.getAsJsonArray("authors")) {
                            authors.add(authorElement.getAsString());
                        }
                    }

                    List<String> categories = new ArrayList<>();
                    if (volumeInfo.has("categories")) {
                        for (JsonElement categoryElement : volumeInfo.getAsJsonArray("categories")) {
                            categories.add(categoryElement.getAsString());
                        }
                    }

                    String description = volumeInfo.has("description") ? volumeInfo.get("description").getAsString() : "No description";

                    String thumbnail = "";
                    if (volumeInfo.has("imageLinks")) {
                        JsonObject imageLinks = volumeInfo.getAsJsonObject("imageLinks");
                        if (imageLinks.has("thumbnail")) {
                            thumbnail = imageLinks.get("thumbnail").getAsString();
                        }
                    }

                    String isbn = "";
                    if (volumeInfo.has("industryIdentifiers")) {
                        JsonArray identifiers = volumeInfo.getAsJsonArray("industryIdentifiers");
                        for (JsonElement identifierElement : identifiers) {
                            JsonObject idObj = identifierElement.getAsJsonObject();
                            if (idObj.has("type") && "ISBN_13".equals(idObj.get("type").getAsString())) {
                                isbn = idObj.get("identifier").getAsString();
                                break;
                            }
                        }
                    }

                    Book book = new Book(title, authors, categories, description, isbn, thumbnail);
                    bookList.add(book);
                }
            }
        } catch (Exception e) {
            System.err.println("Error parsing JSON: " + e.getMessage());
        }

        return bookList;
    }
}
