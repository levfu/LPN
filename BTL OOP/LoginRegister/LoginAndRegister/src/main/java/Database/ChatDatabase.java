package Database;

import Controller.User;
import Model.Message;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ChatDatabase extends BaseDatabase {

    public void sendMessage(int senderId, String content) {
        // Lấy giờ hệ thống theo múi giờ Việt Nam
        ZonedDateTime vietnamTime = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        Timestamp timestamp = Timestamp.valueOf(vietnamTime.toLocalDateTime());

        String sql = "INSERT INTO messages (sender_id, content, timestamp) VALUES (?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, senderId);
            pstmt.setString(2, content);
            pstmt.setTimestamp(3, timestamp);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Message> getAllMessages () {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM messages ORDER BY timestamp ASC";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int senderId = rs.getInt("sender_id");
                String content = rs.getString("content");

                Timestamp timestamp = rs.getTimestamp("timestamp");


                messages.add(new Message(id, senderId, content, timestamp));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messages;
    }

    public User getUserById(int userId) {
        User user = null;
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setAvatarPath(rs.getString("avatarPath"));
                String birthdayStr = rs.getString("birthday");
                if (birthdayStr != null && !birthdayStr.isEmpty()) {
                    try {
                        user.setBirthday(LocalDate.parse(birthdayStr));
                    } catch (DateTimeParseException e) {
                        e.printStackTrace();
                    }
                }
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}