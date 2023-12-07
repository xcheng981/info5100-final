package edu.northeastern.mygym.view;

import edu.northeastern.mygym.model.Course;
import edu.northeastern.mygym.model.Reservation;
import edu.northeastern.mygym.model.user.Member;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class MyCourseScreen extends JFrame {
    private Member loggedInMember;

    public MyCourseScreen(Member loggedInMember) {
        this.loggedInMember = loggedInMember;
        initializeComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeComponents() {
        setLayout(new GridLayout(4, 1));
        add(createViewCourseButton());
        add(createReserveCourseButton());
        add(createCancelReservationButton());
        add(createGoBackButton());
    }

    private JButton createViewCourseButton() {
        JButton viewCourseButton = new JButton("View Course");
        viewCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<Course> courses = loggedInMember.listFirst10Courses();
                    displayCourseInformation(courses);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        return viewCourseButton;
    }

    // Display course information as an instance method
    private void displayCourseInformation(List<Course> courseList) {
        // 在调用displayCourseInformation之前设置loggedInUsername
        Course.setLoggedInUsername(loggedInMember.getUserName());

        // 调用CourseInformation类中的静态方法
        Course.displayCourseInformation(courseList);
    }

    private JButton createReserveCourseButton() {
        JButton reserveCourseButton = new JButton("Reserve Course");
        reserveCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Allow the user to input the course code
                String courseCode = JOptionPane.showInputDialog("Enter Course Code:");

                // Check if the course code is not null or empty before attempting to reserve
                if (courseCode != null && !courseCode.isEmpty()) {
                    reserveCourse(courseCode);
                } else {
                    // Handle the case when the user cancels the input or provides an empty string
                    JOptionPane.showMessageDialog(null, "Invalid Course Code. Please try again.");
                }
            }
        });
        return reserveCourseButton;
    }

    private void reserveCourse(String courseCode) {
        try {
            if (loggedInMember.reserveCourse(courseCode)) {
                // Reservation was successful, show success message
                JOptionPane.showMessageDialog(null, "Course reserved successfully!");
            } else {
                // Reservation failed, error message has already been shown in the method
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Handle the exception appropriately
            JOptionPane.showMessageDialog(null, "Error occurred while reserving the course. Please try again.");
        }
    }

    private JButton createCancelReservationButton() {
        JButton cancelReservationButton = new JButton("Cancel Reservation");
        cancelReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<Reservation> userReservations = loggedInMember.getReservations();

                    // Display a dialog with custom buttons for cancel reservation and go back
                    String[] options = {"Cancel Reservation", "Go Back"};
                    JCheckBox[] checkboxes = new JCheckBox[userReservations.size()];

                    for (int i = 0; i < userReservations.size(); i++) {
                        checkboxes[i] = new JCheckBox(userReservations.get(i).toString());
                    }

                    int result = JOptionPane.showOptionDialog(null, checkboxes, "Cancel Reservations", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

                    if (result == 0) { // User clicked "Cancel Reservation"
                        // Process selected reservations for cancellation
                        for (int i = 0; i < checkboxes.length; i++) {
                            if (checkboxes[i].isSelected()) {
                                // Cancel the selected reservation
                                loggedInMember.cancelReservation(userReservations.get(i).getReservationId());
                            }
                        }
                        JOptionPane.showMessageDialog(null, "Reservations canceled successfully!");
                    } else {
                        // User clicked "Go Back"
                        JOptionPane.showMessageDialog(null, "Cancellation operation aborted.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error occurred while canceling reservations. Please try again.");
                }
            }
        });
        return cancelReservationButton;
    }

    private JButton createGoBackButton() {
        JButton goBackButton = new JButton("Go Back");
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Pass the loggedInUsername when creating a new MemberHomepage
                new MemberHomepage(loggedInMember);
                dispose();
            }
        });
        return goBackButton;
    }
}
