package edu.northeastern.mygym;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class DatabaseHelper {
    // Example method to fetch the first 10 courses from the database
    public static List<CourseInformation> getFirst10Courses() throws SQLException {
        List<CourseInformation> courses = new ArrayList<>();
        Connection connection = null;

        try {
            // Replace these details with your actual database connection information
            String url = "jdbc:mysql://localhost:3306/mygym";
            String username = "root"; // replace with your MySQL username
            String password = "sql123456";

            connection = DriverManager.getConnection(url, username, password);

            String sql = "SELECT * FROM course LIMIT 10";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    String courseCode = resultSet.getString("courseCode");
                    String courseName = resultSet.getString("courseName");
                    String schedule = resultSet.getString("schedule");
                    String equipment = resultSet.getString("equipment");
                    int capacity = resultSet.getInt("capacity");

                    CourseInformation course = new CourseInformation(courseCode, courseName, schedule, equipment, capacity);
                    courses.add(course);
                }
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

        return courses;
    }

    public static boolean reserveCourse(String loggedInName, String courseCode) throws SQLException {
        Connection connection = null;

        try {
            // Replace these details with your actual database connection information
            String url = "jdbc:mysql://localhost:3306/mygym";
            String username = "root";
            String password = "sql123456";

            connection = DriverManager.getConnection(url, username, password);

            // Check if the user has already reserved the course
            if (!hasUserReservedCourse(connection, loggedInName, courseCode)) {
                // Check if there is still available capacity for the course
                if (isCapacityAvailable(connection, courseCode)) {
                    // Retrieve course name based on course code
                    String getCourseNameSql = "SELECT courseName FROM course WHERE courseCode = ?";
                    String courseName = null;
                    try (PreparedStatement getCourseNameStatement = connection.prepareStatement(getCourseNameSql)) {
                        getCourseNameStatement.setString(1, courseCode);
                        try (ResultSet resultSet = getCourseNameStatement.executeQuery()) {
                            if (resultSet.next()) {
                                courseName = resultSet.getString("courseName");
                            }
                        }
                    }

                    // Example SQL query to update the database (adjust based on your schema)
                    String updateCourseSql = "UPDATE course SET capacity = capacity - 1 WHERE courseCode = ?";
                    try (PreparedStatement updateCourseStatement = connection.prepareStatement(updateCourseSql)) {
                        updateCourseStatement.setString(1, courseCode);
                        updateCourseStatement.executeUpdate();
                    }

                    // Example SQL query to insert a reservation record
                    String insertReservationSql = "INSERT INTO reservation (courseCode, courseName, name) VALUES (?, ?, ?)";
                    try (PreparedStatement insertReservationStatement = connection.prepareStatement(insertReservationSql)) {
                        insertReservationStatement.setString(1, courseCode);
                        insertReservationStatement.setString(2, courseName);
                        insertReservationStatement.setString(3, loggedInName);
                        insertReservationStatement.executeUpdate();
                    }

                    // If we reached this point, the reservation was successful
                    return true;
                } else {
                    // Handle the case where the course is fully booked
                    JOptionPane.showMessageDialog(null, "Course is fully booked. Cannot reserve.", "Reservation Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } else {
                // Handle the case where the user has already reserved the course
                JOptionPane.showMessageDialog(null, "You have already reserved this course.", "Reservation Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    private static boolean hasUserReservedCourse(Connection connection, String loggedInName, String courseCode) throws SQLException {
        // Example SQL query to check if the user has already reserved the course
        String checkReservationSql = "SELECT COUNT(*) AS count FROM reservation WHERE name = ? AND courseCode = ?";
        try (PreparedStatement checkReservationStatement = connection.prepareStatement(checkReservationSql)) {
            checkReservationStatement.setString(1, loggedInName);
            checkReservationStatement.setString(2, courseCode);
            try (ResultSet resultSet = checkReservationStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    return count > 0;
                }
            }
        }
        return false;
    }

    private static boolean isCapacityAvailable(Connection connection, String courseCode) throws SQLException {
        // Example SQL query to check if there is available capacity
        String checkCapacitySql = "SELECT capacity FROM course WHERE courseCode = ?";
        try (PreparedStatement checkCapacityStatement = connection.prepareStatement(checkCapacitySql)) {
            checkCapacityStatement.setString(1, courseCode);
            try (ResultSet resultSet = checkCapacityStatement.executeQuery()) {
                if (resultSet.next()) {
                    int capacity = resultSet.getInt("capacity");
                    return capacity > 0;
                }
            }
        }
        return false;
    }

    public static List<ReservationInformation> getUserReservations(String loggedInName) throws SQLException {
        List<ReservationInformation> userReservations = new ArrayList<>();
        Connection connection = null;

        try {
            // Replace these details with your actual database connection information
            String url = "jdbc:mysql://localhost:3306/mygym";
            String username = "root";
            String password = "sql123456";

            connection = DriverManager.getConnection(url, username, password);

            // Ensure that the column name for username is correct in the SQL query
            String sql = "SELECT * FROM reservation WHERE name = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, loggedInName);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int reservationID = resultSet.getInt("reservationID");
                        String courseCode = resultSet.getString("courseCode");
                        String courseName = resultSet.getString("courseName");
                        String userName = resultSet.getString("name");

                        ReservationInformation reservation = new ReservationInformation(reservationID, courseCode, courseName, userName);
                        userReservations.add(reservation);
                    }
                }
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

        return userReservations;
    }

    public static void cancelReservation(int reservationID) throws SQLException {
        Connection connection = null;

        try {
            // Replace these details with your actual database connection information
            String url = "jdbc:mysql://localhost:3306/mygym";
            String username = "root";
            String password = "sql123456";

            connection = DriverManager.getConnection(url, username, password);

            // Fetch courseCode for the canceled reservation
            String getCourseCodeSql = "SELECT courseCode FROM reservation WHERE reservationID = ?";
            String courseCode = null;
            try (PreparedStatement getCourseCodeStatement = connection.prepareStatement(getCourseCodeSql)) {
                getCourseCodeStatement.setInt(1, reservationID);
                try (ResultSet resultSet = getCourseCodeStatement.executeQuery()) {
                    if (resultSet.next()) {
                        courseCode = resultSet.getString("courseCode");
                    }
                }
            }

            // Update reservations table
            String deleteReservationSql = "DELETE FROM reservation WHERE reservationID = ?";
            try (PreparedStatement deleteReservationStatement = connection.prepareStatement(deleteReservationSql)) {
                deleteReservationStatement.setInt(1, reservationID);
                deleteReservationStatement.executeUpdate();
            }

            // Update course table (increase capacity by 1)
            if (courseCode != null) {
                String updateCourseSql = "UPDATE course SET capacity = capacity + 1 WHERE courseCode = ?";
                try (PreparedStatement updateCourseStatement = connection.prepareStatement(updateCourseSql)) {
                    updateCourseStatement.setString(1, courseCode);
                    updateCourseStatement.executeUpdate();
                }
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
    
    public static List<UserInformation> getMembers() throws SQLException {
        List<UserInformation> members = new ArrayList<>();
        Connection connection = null;

        try {
            // Replace these details with your actual database connection information
            String url = "jdbc:mysql://localhost:3306/mygym";
            String usernameDB = "root";
            String password = "sql123456";

            connection = DriverManager.getConnection(url, usernameDB, password);

            String sql = "SELECT * FROM user WHERE userType = 'member'";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    String memberUsername = resultSet.getString("username");
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");

                    UserInformation member = new UserInformation(memberUsername, name, email);
                    members.add(member);
                }
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

        return members;
    }

    public static void addMember(String username, String name, String email) throws SQLException {
        Connection connection = null;

        try {
            // 获取数据库连接信息
            String url = "jdbc:mysql://localhost:3306/mygym";
            String usernameDb = "root";
            String passwordDb = "sql123456";

            connection = DriverManager.getConnection(url, usernameDb, passwordDb);

            // 执行插入会员的 SQL 语句
            String sql = "INSERT INTO user (username, name, email, userType) VALUES (?, ?, ?, 'member')";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, name);
                statement.setString(3, email);

                statement.executeUpdate();
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
    
    public static UserInformation getUserInfoByUsername(String username) throws SQLException {
        UserInformation user = null;
        Connection connection = null;

        try {
            // Replace these details with your actual database connection information
            String url = "jdbc:mysql://localhost:3306/mygym";
            String usernameDB = "root";
            String password = "sql123456";

            connection = DriverManager.getConnection(url, usernameDB, password);

            String sql = "SELECT * FROM user WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String name = resultSet.getString("name");
                        String email = resultSet.getString("email");

                        user = new UserInformation(username, name, email);
                    }
                }
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

        return user;
    }

    
    public static boolean isUsernameDuplicated(String username) throws SQLException {
        Connection connection = null;

        try {
            // Replace these details with your actual database connection information
            String url = "jdbc:mysql://localhost:3306/mygym";
            String usernameDB = "root";
            String password = "sql123456";

            connection = DriverManager.getConnection(url, usernameDB, password);

            String sql = "SELECT COUNT(*) AS count FROM user WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt("count");
                        return count > 0;
                    }
                }
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

        return false;
    }


    public static void updateMember(String username, String name, String email) throws SQLException {
        Connection connection = null;

        try {
            // 获取数据库连接信息
            String url = "jdbc:mysql://localhost:3306/mygym";
            String usernameDb = "root";
            String passwordDb = "sql123456";

            connection = DriverManager.getConnection(url, usernameDb, passwordDb);

            // 执行更新会员的 SQL 语句
            String sql = "UPDATE user SET name = ?, email = ? WHERE username = ? AND userType = 'member'";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, name);
                statement.setString(2, email);
                statement.setString(3, username);

                statement.executeUpdate();
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public static void deleteMember(String username) throws SQLException {
        Connection connection = null;

        try {
            // 获取数据库连接信息
            String url = "jdbc:mysql://localhost:3306/mygym";
            String usernameDb = "root";
            String passwordDb = "sql123456";

            connection = DriverManager.getConnection(url, usernameDb, passwordDb);

            // 执行删除会员的 SQL 语句
            String sql = "DELETE FROM user WHERE username = ? AND userType = 'member'";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);

                statement.executeUpdate();
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
    
    public static List<ReservationInformation> getAllReservations() throws SQLException {
        List<ReservationInformation> reservations = new ArrayList<>();
        Connection connection = null;

        try {
            // 替换为你的数据库连接信息
            String url = "jdbc:mysql://localhost:3306/mygym";
            String username = "root";
            String password = "sql123456";

            connection = DriverManager.getConnection(url, username, password);

            // 查询所有预约信息
            String sql = "SELECT * FROM reservation";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    int reservationID = resultSet.getInt("reservationID");
                    String courseCode = resultSet.getString("courseCode");
                    String courseName = resultSet.getString("courseName");
                    String userName = resultSet.getString("name");

                    ReservationInformation reservation = new ReservationInformation(reservationID, courseCode, courseName, userName);
                    reservations.add(reservation);
                }
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

        return reservations;
    }

}
