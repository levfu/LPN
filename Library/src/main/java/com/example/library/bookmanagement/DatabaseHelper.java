package com.example.library.bookmanagement;
import javafx.scene.control.Label;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    private static final String DB_URL = "jdbc:sqlite:borrowed_books.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS borrowed_books ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "isbn TEXT, "
                + "title TEXT, "
                + "author TEXT, "
                + "dueDate TEXT"
                + ");";
        try (Connection connection = connect();
             Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Lỗi khi tạo bảng: " + e.getMessage());
        }
    }

    public static void saveToDatabase(BookData book) {
           String sql = "INSERT INTO borrowed_books(title, author, isbn, dueDate) VALUES (?, ?, ?, ?)";
           try (Connection connection = connect();
           PreparedStatement pstmt = connection.prepareStatement(sql)) {
               pstmt.setString(1, book.getTitle());
               pstmt.setString(2, book.getAuthor());
               pstmt.setString(3, book.getIsbn());
               pstmt.setString(4, book.getDueDate());
               pstmt.executeUpdate();
           } catch (SQLException e) {
               System.err.println("Lỗi khi lưu dữ liệu: ");
               e.printStackTrace();
           }
    }
    public static List<BookData> getAllBooks() {
        List<BookData> books = new ArrayList<>();
        String sql = "SELECT * FROM borrowed_books";
        try (Connection connection = connect();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String isbn = rs.getString("isbn");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String dueDate = rs.getString("dueDate");
                books.add(new BookData(title, author, isbn, dueDate));
            }
        } catch (SQLException e) {
            System.out.println("Loi khi tai du lieu: " + e.getMessage());
        }
        return books;
    }
    public static void deleteFromDatabase(String isbn) {
        String sql = "DELETE FROM borrowed_books WHERE isbn = ?";
        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, isbn);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Loi khi xoa sach: " + e.getMessage());
        }
    }

    public static void clearAll() {
        String sql = "DELETE FROM borrowed_books";
        try (Connection connection = connect();
             Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Loi khi xoa toan bo du lieu: " + e.getMessage());
        }
    }
}
