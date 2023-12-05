package edu.northeastern.mygym;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MemberHomepage extends JFrame {
    private String loggedInUsername; // Keep track of the logged-in username

    // Default constructor
    public MemberHomepage() {
        // Initialize with a default username (you may modify this based on your logic)
        this.loggedInUsername = ""; // or null
        initializeComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

 // Example of parameterized constructor
    public MemberHomepage(String loggedInUsername) {
        this.loggedInUsername = loggedInUsername;
        initializeComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    private void initializeComponents() {
        setLayout(new GridLayout(3, 1));
        add(createMyAccountButton());
        add(createMyCourseButton());
        add(createLogoutButton());
    }

    private JButton createMyAccountButton() {
        JButton myAccountButton = new JButton("My Account");
        myAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the MemberAccountScreen and pass the username
                new MemberAccountScreen(loggedInUsername); // Pass the actual username
                dispose(); // Close the current homepage
            }
        });
        return myAccountButton;
    }

    private JButton createMyCourseButton() {
        JButton myCourseButton = new JButton("My Course");
        myCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MyCourseScreen(loggedInUsername);
                dispose();
            }
        });
        return myCourseButton;
    }

    
    private JButton createLogoutButton() {
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform logout actions if needed
                // For example, show the login screen again
                new App();
                dispose(); // Close the current homepage
            }
        });
        return logoutButton;
    }
}
