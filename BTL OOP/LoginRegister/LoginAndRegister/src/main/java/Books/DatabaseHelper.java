package Books;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    private static final String DB_URL = "jdbc:sqlite:D:\\HL_OOP\\LPN\\BTL OOP\\LoginRegister\\LoginAndRegister\\src\\main\\resources\\borrowed_books.db";
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS borrowed_books ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "userId TEXT, "
                + "isbn TEXT, "
                + "title TEXT, "
                + "author TEXT, "
                + "dueDate TEXT, "
                + "thumbnail TEXT"
                + ");";

        String ratingsql = "CREATE TABLE IF NOT EXISTS book_ratings ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "userId INTEGER NOT NULL, "
                + "isbn TEXT NOT NULL, "
                + "rating INTEGER NOT NULL CHECK (rating >= 1 AND rating <= 5), "
                + "comment TEXT, "
                + "FOREIGN KEY (isbn) REFERENCES borrowed_books(isbn)"
                + ");";

        try (Connection connection = connect();
             Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            stmt.execute(ratingsql);
        } catch (SQLException e) {
            System.out.println("Lỗi khi tạo bảng: " + e.getMessage());
        }
    }
    public static void saveToDatabase(int userId, BookData book) {
        String sql = "INSERT INTO borrowed_books(userId, title, author, isbn, dueDate, thumbnail) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, book.getTitle());
            pstmt.setString(3, book.getAuthor());
            pstmt.setString(4, book.getIsbn());
            pstmt.setString(5, book.getDueDate());
            pstmt.setString(6, book.getThumbnail());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Lỗi khi lưu dữ liệu: ");
            e.printStackTrace();
        }
    }

    public static void saveRating(int userId, String isbn, int rating, String comment) {
        String checkRatingSql = "SELECT COUNT(*) FROM book_ratings WHERE userId = ? AND isbn = ?";


        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(checkRatingSql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, isbn);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {

                String updateRatingSql = "UPDATE book_ratings SET rating = ?, comment = ? WHERE userId = ? AND isbn = ?";
                try (PreparedStatement updatePstmt = connection.prepareStatement(updateRatingSql)) {
                    updatePstmt.setInt(1, rating);
                    updatePstmt.setString(2, comment);
                    updatePstmt.setInt(3, userId);
                    updatePstmt.setString(4, isbn);
                    updatePstmt.executeUpdate();
                }
            } else {

                String ratingsql = "INSERT INTO book_ratings(userId, isbn, rating, comment) VALUES (?, ?, ?, ?)";
                try (PreparedStatement insertPstmt = connection.prepareStatement(ratingsql)) {
                    insertPstmt.setInt(1, userId);
                    insertPstmt.setString(2, isbn);
                    insertPstmt.setInt(3, rating);
                    insertPstmt.setString(4, comment);
                    insertPstmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lưu đánh giá: ");
            e.printStackTrace();
        }
    }

    public static List<BookData> getAllBooks(int userId) {
        List<BookData> books = new ArrayList<>();
        String sql = "SELECT * FROM borrowed_books WHERE userId = ?";
        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String isbn = rs.getString("isbn");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String dueDate = rs.getString("dueDate");
                String thumbnail = rs.getString("thumbnail");
                books.add(new BookData(title, author, isbn, dueDate, thumbnail));
            }
        } catch (SQLException e) {
            System.out.println("Loi khi tai du lieu: " + e.getMessage());
        }
        return books;
    }

    public static List<UserComment> getCommentsForBook(String isbn) {
        List<UserComment> comments = new ArrayList<>();
        String sql = "SELECT userId, rating, comment FROM book_ratings WHERE isbn = ?";
        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, isbn);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("userId");
                int rating = rs.getInt("rating");
                String comment = rs.getString("comment");
                comments.add(new UserComment("User #" + userId, rating, comment)); // hoặc có thể lấy tên nếu có
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy comment: " + e.getMessage());
        }
        return comments;
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

    public static double getAverageRating(String isbn) {
        double averageRating = 0;
        int totalRatings = 0;
        String sql = "SELECT rating FROM book_ratings WHERE isbn = ?";
        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, isbn);
            try (ResultSet rs = pstmt.executeQuery()) {
                int sumRatings = 0;
                while (rs.next()) {
                    sumRatings += rs.getInt("rating");
                    totalRatings++;
                }
                if (totalRatings > 0) {
                    averageRating = (double) sumRatings / totalRatings;
                }
            }
        }catch (SQLException e) {
            System.out.println("Lỗi khi tính toán rating: " + e.getMessage());
        }
        return averageRating;
    }

    public static List<Book> getTopRatedBooks() {
        List<Book> topRatedBooks = new ArrayList<>();
        String sql = "SELECT b.isbn, b.title, b.author, b.thumbnail, AVG(r.rating) as avgRating " +
                "FROM borrowed_books b " +
                "JOIN book_ratings r ON b.isbn = r.isbn " +
                "GROUP BY b.isbn " +
                "ORDER BY avgRating DESC " +
                "LIMIT 7";
        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String isbn = rs.getString("isbn");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String thumbnail = rs.getString("thumbnail");
                topRatedBooks.add(new Book(title, List.of(author), new ArrayList<>(), "No description", isbn, thumbnail));
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy sách top-rated: " + e.getMessage());
        }
        return topRatedBooks;
    }


    public static List<Book> getTrendingBooks() {
        List<Book> trendingBooks = new ArrayList<>();
        String sql = "SELECT b.isbn, b.title, b.author, b.thumbnail, " +
                "AVG(r.rating) as avgRating, COUNT(r.id) as ratingCount " +
                "FROM borrowed_books b " +
                "JOIN book_ratings r ON b.isbn = r.isbn " +
                "GROUP BY b.isbn " +
                "ORDER BY ratingCount DESC, avgRating DESC " +
                "LIMIT 6";

        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String isbn = rs.getString("isbn");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String thumbnail = rs.getString("thumbnail");
                trendingBooks.add(new Book(title, List.of(author), new ArrayList<>(), "No description", isbn, thumbnail));
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy sách trending: " + e.getMessage());
        }
        return trendingBooks;
    }

}  