package com.example.library.bookmanagement;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GoogleBookAPI {
    private static final String BASEURL = "https://www.googleapis.com/books/v1/volumes?q=";

    public static String searchBooks(String title, String author, String categories, String isbn) throws Exception {
        OkHttpClient client = new OkHttpClient();
        StringBuilder query = new StringBuilder();
        if (!isbn.isEmpty()) {
            query.append("isbn:").append(isbn);
        }
        if (!title.isEmpty()) {
            if (query.length() > 0) {
                query.append("+");
            }
            query.append("intitle:").append(title.replace(" ", "+"));

        }
        if (!author.isEmpty()) {
            if (query.length() > 0) {
                query.append("+");
            }
            query.append("inauthor:").append(author.replace(" ", "+"));

        }
        if (!categories.isEmpty()) {
            if (query.length() > 0) {
                query.append("+");
            }
            query.append("subject:").append(categories.replace(" ", "+"));
        }

        String url = BASEURL + query.toString();

        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.out.println("Loi API: " + response.code() + "-" + response.message());
                return null;
            }
            return response.body().string();
        }
    }
}
