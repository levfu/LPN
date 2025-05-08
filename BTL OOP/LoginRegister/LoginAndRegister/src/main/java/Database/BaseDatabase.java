package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class BaseDatabase {
    private static final String URL = "jdbc:sqlite:C:\\Users\\Admin\\Documents\\GitHub\\LPN\\BTL OOP\\LoginRegister\\LoginAndRegister\\src\\main\\resources\\library.db";

    protected static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}