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
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    private void initializeComponents() {
        setLayout(new GridBagLayout());

        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JButton membershipManagementButton = createMembershipManagementButton();
        JButton courseManagementButton = createCourseManagementButton();

        // Add "My Account" button
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(50, 70, 50, 50); // Optional: Add some spacing
        buttonsPanel.add(membershipManagementButton, gbc);

        // Add "My Course" button
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(50, 50, 50, 70); // Optional: Add some spacing
        buttonsPanel.add(courseManagementButton, gbc);

        // Add buttonsPanel to the main frame
        add(buttonsPanel, gbc);

        JButton logoutButton = createLogoutButton();

        // Add "Logout" button in a new row with increased vertical gap
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2; // Span across both columns
        gbc.insets = new Insets(20, 10, 10, 10); // Increase the top inset for more spacing
        add(logoutButton, gbc);

        pack(); // Pack the frame to ensure proper layout
        setLocationRelativeTo(null); // Center the frame on the screen
    }



    private JButton createMembershipManagementButton() {
        JButton membershipManagementButton = new JButton("Membership Management");
        membershipManagementButton.setFont(new Font("Arial", Font.PLAIN, 25));
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
        courseManagementButton.setFont(new Font("Arial", Font.PLAIN, 25));
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
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 25));
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
