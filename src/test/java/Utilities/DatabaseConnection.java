package Utilities;

import java.sql.*;

public class DatabaseConnection {

    public static String getEmail;
    public static String getPassword;

    public static void dbConnection() throws SQLException {
        String dbURL = "jdbc:mysql://102.222.124.22:3306/ndosian6b8b7_teaching";
        String dbUsername = "ndosian6b8b7_teaching";
        String dbPassword = "^{SF0a=#~[~p)@l1";

        try (Connection connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword)) {

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT * FROM loginUser WHERE id = 7")) {

                while (resultSet.next()) {
                    getEmail = resultSet.getString("email");
                    getPassword = resultSet.getString("password");
                    System.out.println("Email: " + getEmail + ", Password: " + getPassword);
                }
            } catch (SQLException e) {
                System.out.println("Error executing query: " + e.getMessage());
            }
        }

    }

}
