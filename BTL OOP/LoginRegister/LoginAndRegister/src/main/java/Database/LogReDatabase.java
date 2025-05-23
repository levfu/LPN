package Database;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.ArrayList;
import Controller.User;

public class LogReDatabase extends BaseDatabase{

    public static boolean checkLogin(String email, String password, String role) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ? AND role = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            pstmt.setString(3, role);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    public static boolean emailExists(String email, String role) {
        String sql = "SELECT * FROM users WHERE email = ? AND role = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, role);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    public static boolean registerUser(String email, String password, String role) {
        if (emailExists(email, role)) {
            System.out.println("Email already exists with this role, registration is not allowed !");
            return false;
        }

        String sql = "INSERT INTO users (email, password, role, name, phone, birthday, address, avatarPath) " +
                "VALUES (?, ?, ?, NULL, NULL, NULL, NULL, NULL)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            pstmt.setString(3, role);
            pstmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }




    public static User getUser(String email, String password, String role) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ? AND role = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            pstmt.setString(3, role);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                String birthStr = rs.getString("birthday");
                user.setBirthday(birthStr != null ? LocalDate.parse(birthStr) : null);
                user.setPassword(rs.getString("password"));
                user.setAddress(rs.getString("address"));
                user.setAvatarPath(rs.getString("avatarPath"));
                user.setRole(rs.getString("role"));
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    public static boolean updateUser(User user) {
        String sql = "UPDATE users SET name = ?, phone = ?, birthday = ?, address = ?, avatarPath = ?, password = ? WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getPhone());
            pstmt.setString(3, user.getBirthday() != null ? user.getBirthday().toString() : null);
            pstmt.setString(4, user.getAddress());
            pstmt.setString(5, user.getAvatarPath());
            pstmt.setString(6, user.getPassword());
            pstmt.setInt(7, user.getId());


            System.out.println("Updating user:");
            System.out.println("Name: " + user.getName());
            System.out.println("Phone: " + user.getPhone());
            System.out.println("Birthday: " + user.getBirthday());
            System.out.println("Address: " + user.getAddress());
            System.out.println("Avatar Path: " + user.getAvatarPath());
            System.out.println("Password: " + user.getPassword());
            System.out.println("ID: " + user.getId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    public static List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setRole(rs.getString("role"));
                user.setAddress(rs.getString("address"));
                user.setAvatarPath(rs.getString("avatarPath"));
                user.setPassword(rs.getString("password"));

                String birthStr = rs.getString("birthday");
                if (birthStr != null && !birthStr.isBlank()) {
                    try {

                        user.setBirthday(LocalDate.parse(birthStr));
                    } catch (DateTimeParseException e) {
                        System.out.println("Lỗi chuyển đổi ngày sinh cho người dùng ID " + user.getId() + ": " + e.getMessage());
                        user.setBirthday(null); // Gán null nếu ngày tháng không hợp lệ
                    }
                } else {
                    user.setBirthday(null);
                }

                userList.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userList;
    }



    public static boolean deleteUserById(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    public static boolean updatePasswordByEmail(String email, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE email = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newPassword);
            pstmt.setString(2, email);
            int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    public static boolean checkEmailExists(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    public static void updatePassword(String email, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE email = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newPassword);
            stmt.setString(2, email);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean insertUser(User user) {
        if (emailExists(user.getEmail(), user.getRole())) {
            System.out.println("Tài khoản với email và vai trò này đã tồn tại!");
            return false;
        }

        String sql = "INSERT INTO users (name, birthday, password, email, address ,phone, role, avatarPath) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getBirthday() != null ? user.getBirthday().toString() : null);
            pstmt.setString(3, user.getPhone());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getAddress());
            pstmt.setString(6, user.getRole());
            pstmt.setString(7, user.getPassword());
            pstmt.setString(8, user.getAvatarPath());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Insert user error: " + e.getMessage());
            return false;
        }
    }

}
