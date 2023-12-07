package edu.northeastern.mygym.view;

import edu.northeastern.mygym.App;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.*;

public class MemberHomepage extends JFrame {
    private String loggedInUsername;

    public MemberHomepage() {
        this.loggedInUsername = "";
        initializeComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public MemberHomepage(String loggedInUsername) {
        this.loggedInUsername = loggedInUsername;
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
        gbc.insets = new Insets(20, 10, 30, 10); // Increased vertical gap
        buttonsPanel.add(myAccountButton, gbc);

        // Add "My Course" button
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 10, 30, 10); // Increased vertical gap
        buttonsPanel.add(myCourseButton, gbc);

        // Add buttonsPanel to the main frame
        add(buttonsPanel, gbc);

        JButton logoutButton = createLogoutButton();

        // Add "Logout" button in a new row with increased vertical gap
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(30, 10, 10, 10); // Further increased vertical gap
        add(logoutButton, gbc);

        pack();
        setLocationRelativeTo(null);
    }

    private JButton createMyAccountButton() {
        JButton myAccountButton = new JButton("My Account");
        myAccountButton.setFont(new Font("Arial", Font.PLAIN, 25)); // Larger font
        myAccountButton.addActionListener(e -> {
            new MemberAccountScreen(loggedInUsername);
            dispose();
        });
        return myAccountButton;
    }

    private JButton createMyCourseButton() {
        JButton myCourseButton = new JButton("My Course");
        myCourseButton.setFont(new Font("Arial", Font.PLAIN, 25)); // Larger font
        myCourseButton.addActionListener(e -> {
            new MyCourseScreen(loggedInUsername);
            dispose();
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
