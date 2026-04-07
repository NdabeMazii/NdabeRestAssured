package Utilities;

import java.sql.*;

public class DatabaseConnection {


    public static String getEmail;
    public static String getPassword;

    public static void dbConnection() {
        String dbURL = "jdbc:mysql://102.222.124.22:3306/ndosian6b8b7_teaching";
        String dbUsername = "ndosian6b8b7_teaching";
        String dbPassword = "^{SF0a=#~[~p)@l1";

        try (Connection connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword)) {
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT email, password FROM loginUser LIMIT 1")) {

                if (resultSet.next()) {
                    getEmail = resultSet.getString("email");
                    getPassword = resultSet.getString("password");
                    System.out.println("DB -> Email: " + getEmail + ", Password: " + getPassword);
                } else {
                    System.out.println("DB -> No loginUser rows found (query returned empty result set)");
                }
            } catch (SQLException e) {
                System.out.println("Error executing query: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to DB: " + e.getMessage());
        }

    }

}
