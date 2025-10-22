package mypack;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    // --- CONFIGURE YOUR DATABASE HERE ---
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Semester_Planner";
    private static final String DB_USER = "root"; 
    private static final String DB_PASSWORD = "hridya@123"; //enter the database password
    // ------------------------------------

    private static Connection connection = null;

    private DatabaseManager() {} // Private constructor to prevent instantiation

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            } catch (ClassNotFoundException e) {
                System.err.println("MySQL JDBC Driver not found! Please add it to your project's build path.");
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}