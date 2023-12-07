package edu.northeastern.mygym.view;

import edu.northeastern.mygym.App;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.northeastern.mygym.model.user.Member;

import javax.swing.*;

public class MemberHomepage extends JFrame {
    private Member loggedInMember;

    // Example of parameterized constructor
    public MemberHomepage(Member loggedInMember) {
        this.loggedInMember = loggedInMember;
        initializeComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void initializeComponents() {
        setLayout(new GridBagLayout());

        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JButton myAccountButton = createMyAccountButton();
        JButton myCourseButton = createMyCourseButton();

        // Add "My Account" button
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(50, 70, 50, 50); // Optional: Add some spacing
        buttonsPanel.add(myAccountButton, gbc);

        // Add "My Course" button
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(50, 50, 50, 70); // Optional: Add some spacing
        buttonsPanel.add(myCourseButton, gbc);

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


    private JButton createMyAccountButton() {
        JButton myAccountButton = new JButton("My Account");
        myAccountButton.setFont(new Font("Arial", Font.PLAIN, 25));
        myAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the MemberAccountScreen and pass the username
                new MemberAccountScreen(loggedInMember); // Pass the actual username
                dispose(); // Close the current homepage
            }
        });
        return myAccountButton;
    }

    private JButton createMyCourseButton() {
        JButton myCourseButton = new JButton("My Course");
        myCourseButton.setFont(new Font("Arial", Font.PLAIN, 25)); 
        myCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MyCourseScreen(loggedInMember);
                dispose();
            }
        });
        return myCourseButton;
    }

    private JButton createLogoutButton() {
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 25)); // Larger font
        logoutButton.addActionListener(e -> {
            new App();
            dispose();
        });
        return logoutButton;
    }
}
