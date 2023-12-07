package edu.northeastern.mygym.view;

import edu.northeastern.mygym.model.Reservation;
import edu.northeastern.mygym.model.user.Admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class CourseManagementScreen extends JFrame {
    private Admin loggedInAdmin;

    public CourseManagementScreen(Admin loggedInAdmin) {
        this.loggedInAdmin = loggedInAdmin;
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
                    List<Reservation> reservations = loggedInAdmin.listAllReservations();
                    
                    // Display reservations using JOptionPane
                    StringBuilder reservationInfo = new StringBuilder("Reservations:\n");
                    for (Reservation reservation : reservations) {
                        reservationInfo.append("ID: ").append(reservation.getReservationId())
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
                new AdminHomepage(loggedInAdmin);
                dispose(); // Close the current screen
            }
        });
        return backButton;
    }
}

