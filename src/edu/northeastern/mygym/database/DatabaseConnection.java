package edu.northeastern.mygym.database;

import java.sql.*;
public class DatabaseConnection {
    
    public static void main(String[] args) {
        // Step 1: Load the JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        // Step 2: Establish a connection

        try (Connection connection = DriverManager.getConnection(DatabaseConstants.URL,
                DatabaseConstants.USERNAME,
                DatabaseConstants.PASSWORD)) {

            // Step 3: Create a statement
            try (Statement statement = connection.createStatement()) {

                // Step 4: Execute a query
                try (ResultSet resultSet = statement.executeQuery("SELECT * FROM user")) {

                    // Step 5: Process the results
                    while (resultSet.next()) {
                        String userName = resultSet.getString("username");
                        String name = resultSet.getString("name");
                        String email = resultSet.getString("email");
                        String userPassword = resultSet.getString("password");
                        String userType = resultSet.getString("userType");

                        // Process the data as needed
                        System.out.println("User: " + userName + ", Name: " + name + ", Email: " + email + ", userType: " + userType);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
