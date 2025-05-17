package Books;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    private static final HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:sqlite:D:\\btl\\LPN\\BTL OOP\\LoginRegister\\LoginAndRegister\\src\\main\\resources\\borrowed_books.db");
        config.setMaximumPoolSize(10);
        config.setConnectionTimeout(30000);
        config.setAutoCommit(false);
        dataSource = new HikariDataSource(config);

        Runtime.getRuntime().addShutdownHook(new Thread(dataSource::close));
    }


    public static Connection connect() throws SQLException {
        return dataSource.getConnection();
    }


    public static synchronized void addMissingColumns() {
        try (Connection connection = connect();
             Statement stmt = connection.createStatement()) {

            stmt.execute("ALTER TABLE borrowed_books ADD COLUMN description TEXT");
            connection.commit();
        } catch (SQLException e) {
            if (!e.getMessage().contains("duplicate column")) {
                System.out.println("Error description: " + e.getMessage());
            }
        }

        try (Connection connection = connect();
             Statement stmt = connection.createStatement()) {

            stmt.execute("ALTER TABLE borrowed_books ADD COLUMN tags TEXT");
            connection.commit();
        } catch (SQLException e) {
            if (!e.getMessage().contains("duplicate column")) {
                System.out.println("Error tags: " + e.getMessage());
            }
        }
    }


    public static synchronized void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS borrowed_books ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "userId TEXT, "
                + "isbn TEXT, "
                + "title TEXT, "
                + "author TEXT, "
                + "dueDate TEXT, "
                + "thumbnail TEXT, "
                + "description TEXT, "
                + "tags TEXT"
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
            addMissingColumns();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Error while create table: " + e.getMessage());
        }
    }


    public static void saveToDatabase(int userId, BookData book) {
        String sql = "INSERT INTO borrowed_books(userId, title, author, isbn, dueDate, thumbnail, description, tags) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setString(2, book.getTitle());
            pstmt.setString(3, book.getAuthor());
            pstmt.setString(4, book.getIsbn());
            pstmt.setString(5, book.getDueDate());
            pstmt.setString(6, book.getThumbnail());
            pstmt.setString(7, book.getDescription());
            pstmt.setString(8, book.getTags());

            pstmt.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.err.println("Error while save data: ");
            e.printStackTrace();
        }
    }


    public static void saveRating(int userId, String isbn, int rating, String comment) {
        try (Connection connection = connect()) {
            // Kiểm tra và khóa bản ghi nếu tồn tại
            String checkSql = "SELECT 1 FROM book_ratings WHERE userId = ? AND isbn = ?";
            try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
                checkStmt.setInt(1, userId);
                checkStmt.setString(2, isbn);

                boolean exists = checkStmt.executeQuery().next();

                if (exists) {
                    String updateSql = "UPDATE book_ratings SET rating = ?, comment = ? WHERE userId = ? AND isbn = ?";
                    try (PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
                        updateStmt.setInt(1, rating);
                        updateStmt.setString(2, comment);
                        updateStmt.setInt(3, userId);
                        updateStmt.setString(4, isbn);
                        updateStmt.executeUpdate();
                    }
                } else {
                    String insertSql = "INSERT INTO book_ratings(userId, isbn, rating, comment) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
                        insertStmt.setInt(1, userId);
                        insertStmt.setString(2, isbn);
                        insertStmt.setInt(3, rating);
                        insertStmt.setString(4, comment);
                        insertStmt.executeUpdate();
                    }
                }
                connection.commit();
            }
        } catch (SQLException e) {
            System.err.println("Error while save rating: ");
            e.printStackTrace();
        }
    }


    public static List<BookData> getAllBooks(int userId) {
        List<BookData> books = new ArrayList<>();
        String sql = "SELECT * FROM borrowed_books WHERE userId = ?";

        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    books.add(new BookData(
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("isbn"),
                            rs.getString("dueDate"),
                            rs.getString("thumbnail"),
                            rs.getString("description"),
                            rs.getString("tags")
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while load data: " + e.getMessage());
        }
        return books;
    }


    public static List<UserComment> getCommentsForBook(String isbn) {
        List<UserComment> comments = new ArrayList<>();
        String sql = "SELECT userId, rating, comment FROM book_ratings WHERE isbn = ?";

        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, isbn);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    comments.add(new UserComment(
                            "User #" + rs.getInt("userId"),
                            rs.getInt("rating"),
                            rs.getString("comment")
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while get comment: " + e.getMessage());
        }
        return comments;
    }


    public static void deleteFromDatabase(String isbn) {
        try (Connection connection = connect()) {
            // Xóa ratings trước do ràng buộc khóa ngoại
            String deleteRatingSql = "DELETE FROM book_ratings WHERE isbn = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(deleteRatingSql)) {
                pstmt.setString(1, isbn);
                pstmt.executeUpdate();
            }

            // Xóa sách
            String deleteBookSql = "DELETE FROM borrowed_books WHERE isbn = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(deleteBookSql)) {
                pstmt.setString(1, isbn);
                pstmt.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            System.out.println("Error while delete book: " + e.getMessage());
        }
    }


    public static void clearAll() {
        try (Connection connection = connect();
             Statement stmt = connection.createStatement()) {

            stmt.execute("DELETE FROM book_ratings");
            stmt.execute("DELETE FROM borrowed_books");
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Error while delete all data: " + e.getMessage());
        }
    }


    public static double getAverageRating(String isbn) {
        double averageRating = 0;
        String sql = "SELECT AVG(rating) as avgRating, COUNT(rating) as count FROM book_ratings WHERE isbn = ?";

        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, isbn);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next() && rs.getInt("count") > 0) {
                    averageRating = rs.getDouble("avgRating");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while calculating rating: " + e.getMessage());
        }
        return averageRating;
    }


    public static List<Book> getTopRatedBooks() {
        List<Book> topRatedBooks = new ArrayList<>();
        String sql = "SELECT b.isbn, b.title, b.author, b.thumbnail, b.description, b.tags, AVG(r.rating) as avgRating " +
                "FROM borrowed_books b " +
                "JOIN book_ratings r ON b.isbn = r.isbn " +
                "GROUP BY b.isbn " +
                "ORDER BY avgRating DESC " +
                "LIMIT 7";

        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                topRatedBooks.add(new Book(
                        rs.getString("title"),
                        List.of(rs.getString("author")),
                        List.of(rs.getString("tags")),
                        rs.getString("description"),
                        rs.getString("isbn"),
                        rs.getString("thumbnail")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error get top-rated books: " + e.getMessage());
        }
        return topRatedBooks;
    }


    public static List<Book> getTrendingBooks() {
        List<Book> trendingBooks = new ArrayList<>();
        String sql = "SELECT b.isbn, b.title, b.author, b.thumbnail, b.description, b.tags, " +
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
                trendingBooks.add(new Book(
                        rs.getString("title"),
                        List.of(rs.getString("author")),
                        List.of(rs.getString("tags")),
                        rs.getString("description"),
                        rs.getString("isbn"),
                        rs.getString("thumbnail")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error get trending books: " + e.getMessage());
        }
        return trendingBooks;
    }


    public static List<Book> getAllBooksWithRatings() {
        List<Book> ratedBooks = new ArrayList<>();
        String sql = "SELECT b.isbn, b.title, b.author, b.thumbnail, b.description, b.tags " +
                "FROM borrowed_books b " +
                "JOIN book_ratings r ON b.isbn = r.isbn " +
                "GROUP BY b.isbn, b.title, b.author, b.thumbnail, b.description, b.tags";

        try (Connection connection = connect();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String tags = rs.getString("tags");
                ratedBooks.add(new Book(
                        rs.getString("title"),
                        List.of(rs.getString("author")),
                        (tags != null && !tags.isEmpty()) ? List.of(tags.split(",")) : List.of(),
                        rs.getString("description"),
                        rs.getString("isbn"),
                        rs.getString("thumbnail")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching books with ratings: " + e.getMessage());
        }
        return ratedBooks;
    }
}