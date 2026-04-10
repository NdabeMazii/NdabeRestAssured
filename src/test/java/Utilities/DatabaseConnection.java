package Utilities;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    // kept public for backward compatibility with existing tests
    public static String getEmail;
    public static String getPassword;

    private static final Properties props = new Properties();
    private static boolean initialized = false;

    static {
        // load config.properties from test classpath (src/test/resources)
        try (InputStream in = DatabaseConnection.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (in != null) {
                props.load(in);
            } else {
                System.err.println("config.properties not found on classpath (src/test/resources)");
            }
        } catch (Exception e) {
            System.err.println("Failed to load config.properties: " + e.getMessage());
        }
    }

    /**
     * Initialize DB-backed values used by tests. Call this from your test setup
     * (e.g. @BeforeAll or @BeforeSuite). This method will throw on error so
     * the test run fails loudly instead of silently leaving values null.
     */
    public static void dbConnection() {
        if (initialized) return;

        String dbURL = System.getProperty("db.url", props.getProperty("db.url", System.getenv("DB_URL")));
        String dbUsername = System.getProperty("db.username", props.getProperty("db.username", System.getenv("DB_USERNAME")));
        String dbPassword = System.getProperty("db.password", props.getProperty("db.password", System.getenv("DB_PASSWORD")));
        String query = System.getProperty("db.query", props.getProperty("db.query", "SELECT * FROM loginUser WHERE id = 7"));

        if (dbURL == null || dbUsername == null) {
            throw new IllegalStateException("Database configuration missing. Provide db.url and db.username via config.properties, -D properties, or environment variables.");
        }

        try (Connection connection = java.sql.DriverManager.getConnection(dbURL, dbUsername, dbPassword);
             java.sql.Statement statement = connection.createStatement();
             java.sql.ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                getEmail = resultSet.getString("email");
                getPassword = resultSet.getString("password");
                initialized = true;
                System.out.println("DatabaseConnection initialized. Email: " + getEmail);
            } else {
                throw new RuntimeException("Query returned no rows: " + query);
            }

        } catch (SQLException e) {
            // Re-throw so test setup fails visibly instead of being silently ignored
            System.err.println("Error connecting/executing query: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
