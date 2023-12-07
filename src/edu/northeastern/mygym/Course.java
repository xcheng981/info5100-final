package edu.northeastern.mygym.model;

import javax.swing.*;
import java.util.List;

public class Course {
    private String courseCode;
    private String courseName;
    private String schedule;
    private String equipment;
    private int capacity;

    // 添加静态成员用于保存用户信息
    private static String loggedInUsername;

    public Course(String courseCode, String courseName, String schedule, String equipment, int capacity) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.schedule = schedule;
        this.equipment = equipment;
        this.capacity = capacity;
    }

    // 设置loggedInUsername的静态方法
    public static void setLoggedInUsername(String username) {
        loggedInUsername = username;
    }

    // 在displayCourseInformation方法中使用loggedInUsername
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

    // Getter methods for private fields
    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getSchedule() {
        return schedule;
    }

    public String getEquipment() {
        return equipment;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public String toString() {
        return String.format("Code: %s, Name: %s, Schedule: %s, Equipment: %s, Capacity: %d",
                courseCode, courseName, schedule, equipment, capacity);
    }
}
