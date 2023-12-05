package edu.northeastern.mygym;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class CourseManagementScreen extends JFrame {
    private String loggedInUsername;

    public CourseManagementScreen(String loggedInUsername) {
        this.loggedInUsername = loggedInUsername;
        initializeComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeComponents() {
        setLayout(new GridLayout(2, 1));
        add(createViewReservationsButton());
        add(createBackButton());
    }

    private JButton createViewReservationsButton() {
        JButton viewReservationsButton = new JButton("View Reservations");
        viewReservationsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<ReservationInformation> reservations = DatabaseHelper.getAllReservations();
                    
                    // Display reservations using JOptionPane
                    StringBuilder reservationInfo = new StringBuilder("Reservations:\n");
                    for (ReservationInformation reservation : reservations) {
                        reservationInfo.append("ID: ").append(reservation.getReservationID())
                                .append(", Course Code: ").append(reservation.getCourseCode())
                                .append(", Course Name: ").append(reservation.getCourseName())
                                .append(", User: ").append(reservation.getUserName())
                                .append("\n");
                    }
                    
                    JOptionPane.showMessageDialog(null, reservationInfo.toString(), "All Reservations", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    // Handle SQLException
                }
            }
        });
        return viewReservationsButton;
    }


    private JButton createBackButton() {
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Go back to the admin homepage
                new AdminHomepage(loggedInUsername);
                dispose(); // Close the current screen
            }
        });
        return backButton;
    }
}

