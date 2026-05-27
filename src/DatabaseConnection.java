import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://kodama.proxy.rlwy.net:17133/railway";
    private static final String USER = "root";
    private static final String PASSWORD = "IiojIzQDpXVQCXyQweHzSMovCFpWvpgA";

    public static Connection getConnection() {
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            return con;
        } catch (Exception e) {
            System.out.println("Connection failed: " + e.getMessage());
            return null;
        }
    }
}