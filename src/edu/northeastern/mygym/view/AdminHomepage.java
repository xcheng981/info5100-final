package edu.northeastern.mygym.view;

import edu.northeastern.mygym.App;
import edu.northeastern.mygym.model.user.Admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AdminHomepage extends JFrame {
    private Admin loggedInAdmin;

    public AdminHomepage(Admin loggedInAdmin) {
        this.loggedInAdmin = loggedInAdmin;
        initializeComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    private void initializeComponents() {
        setLayout(new GridLayout(3, 1));
        add(createMembershipManagementButton());
        add(createCourseManagementButton());
        add(createLogoutButton());
    }


    private JButton createMembershipManagementButton() {
        JButton membershipManagementButton = new JButton("Membership Management");
        membershipManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the MemberAccountScreen and pass the username
                new MembershipManagementScreen(loggedInAdmin); // Pass the actual username
                dispose(); // Close the current homepage
            }
        });
        return membershipManagementButton;
    }

    private JButton createCourseManagementButton() {
        JButton courseManagementButton = new JButton("Course Management");
        courseManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CourseManagementScreen(loggedInAdmin);
                dispose();
            }
        });
        return courseManagementButton;
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
