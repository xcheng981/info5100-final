package edu.northeastern.mygym.model;

import javax.swing.*;
import java.util.List;

public class Course {
    private String courseCode;
    private String courseName;
    private String schedule;
    private String equipment;
    private int capacity;

    // Add a static object to store user information.
    private static String loggedInUsername;

    public Course(String courseCode, String courseName, String schedule, String equipment, int capacity) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.schedule = schedule;
        this.equipment = equipment;
        this.capacity = capacity;
    }

    // Create a static method for setting the loggedInUsername.
    public static void setLoggedInUsername(String username) {
        loggedInUsername = username;
    }

    // Utilize loggedInUsername in the displayCourseInformation method.
    public static void displayCourseInformation(List<Course> courseList) {
        JTextArea textArea = new JTextArea();
        textArea.append("Logged In User: " + loggedInUsername + "\n");

        for (Course course : courseList) {
            textArea.append(course.toString() + "\n");
        }

        JScrollPane scrollPane = new JScrollPane(textArea);

        JFrame frame = new JFrame("Course Information");
        frame.getContentPane().add(scrollPane);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public String toString() {
        return String.format("Code: %s, Name: %s, Schedule: %s, Equipment: %s, Capacity: %d",
                courseCode, courseName, schedule, equipment, capacity);
    }
}
