package edu.northeastern.mygym;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberAccountScreen extends JFrame {
    private Connection connection; // Database connection
    private String loggedInUsername; // Keep track of the logged-in username

    public MemberAccountScreen(String loggedInUsername) {
        this.loggedInUsername = loggedInUsername;
        initializeComponents();

        // Initialize database connection
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mygym", "root", "sql123456");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeComponents() {
        setLayout(new GridLayout(3, 1));
        add(createViewProfileButton());
        add(createUpdateProfileButton());
        add(createGoBackButton());
    }

    private JButton createViewProfileButton() {
        JButton viewProfileButton = new JButton("View Profile");
        viewProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewMemberProfile(loggedInUsername);
            }
        });
        return viewProfileButton;
    }

    private void viewMemberProfile(String username) {
        try {
            String query = "SELECT * FROM user WHERE username = ?";
            System.out.println("Username: " + username);  // Add this line for debugging

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);

                // Execute the query
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        // Retrieve user information
                        String name = resultSet.getString("name");
                        String email = resultSet.getString("email");

                        // Display user information
                        System.out.println("Name: " + name);
                        System.out.println("Email: " + email);

                        JOptionPane.showMessageDialog(MemberAccountScreen.this,
                                "Username: " + username + "\nName: " + name + "\nEmail: " + email,
                                "Member Profile",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(MemberAccountScreen.this, "User not found", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(MemberAccountScreen.this, "Error retrieving user information: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }




    private void displayProfileInformation(String username, String name, String email) {
        // Replace these lines with your actual UI components
        JLabel usernameLabel = new JLabel("Username: " + username);
        JLabel nameLabel = new JLabel("Name: " + name);
        JLabel emailLabel = new JLabel("Email: " + email);

        // Create a panel to hold the labels
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        panel.add(usernameLabel);
        panel.add(nameLabel);
        panel.add(emailLabel);

        // Display the panel in a dialog
        JOptionPane.showMessageDialog(MemberAccountScreen.this, panel, "Member Profile", JOptionPane.INFORMATION_MESSAGE);
    }


    private JButton createUpdateProfileButton() {
        JButton updateProfileButton = new JButton("Update Profile");
        updateProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openUpdateProfileScreen(loggedInUsername);
            }
        });
        return updateProfileButton;
    }

 // ... (previous code)

    private void openUpdateProfileScreen(String username) {
        JFrame updateProfileFrame = new JFrame("Update Profile");
        updateProfileFrame.setLayout(new GridLayout(4, 2));

        JTextField usernameField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();

        // Load the existing user information
        loadUserInformation(username, nameField, emailField);

        // Set the username as read-only
        usernameField.setText(username);
        usernameField.setEditable(false);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newName = nameField.getText();
                String newEmail = emailField.getText();

                // Validate and save the updated information to the database
                if (validateAndSaveUpdate(username, newName, newEmail)) {
                    updateProfileFrame.dispose();
                }
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProfileFrame.dispose();
            }
        });

        updateProfileFrame.add(new JLabel("Username:"));
        updateProfileFrame.add(usernameField);
        updateProfileFrame.add(new JLabel("Name:"));
        updateProfileFrame.add(nameField);
        updateProfileFrame.add(new JLabel("Email:"));
        updateProfileFrame.add(emailField);
        updateProfileFrame.add(saveButton);
        updateProfileFrame.add(cancelButton); // Add the Cancel button

        updateProfileFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        updateProfileFrame.pack();
        updateProfileFrame.setLocationRelativeTo(null);
        updateProfileFrame.setVisible(true);
    }

    private void loadUserInformation(String username, JTextField nameField, JTextField emailField) {
        // Load the existing user information from the database and set it in the fields
        // Modify this method based on your database schema and queries
        try {
            String query = "SELECT * FROM user WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String name = resultSet.getString("name");
                        String email = resultSet.getString("email");

                        nameField.setText(name);
                        emailField.setText(email);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(MemberAccountScreen.this, "Error loading user information: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validateAndSaveUpdate(String username, String newName, String newEmail) {
        // Validate the input fields (e.g., check for non-empty fields)
        if (newName.isEmpty() || newEmail.isEmpty()) {
            JOptionPane.showMessageDialog(MemberAccountScreen.this, "Name and Email cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Save the updated information to the database
        // Modify this method based on your database schema and queries
        try {
            String updateQuery = "UPDATE user SET name = ?, email = ? WHERE username = ?";
            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                updateStatement.setString(1, newName);
                updateStatement.setString(2, newEmail);
                updateStatement.setString(3, username);

                int rowsUpdated = updateStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(MemberAccountScreen.this, "Profile updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    return true;
                } else {
                    JOptionPane.showMessageDialog(MemberAccountScreen.this, "Failed to update profile", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(MemberAccountScreen.this, "Error updating profile: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private JButton createGoBackButton() {
        JButton goBackButton = new JButton("Go Back");
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform actions to go back
                // For example, show the previous screen
                new MemberHomepage(); // Assuming MemberHomepage is your previous screen
                dispose(); // Close the current member account screen
            }
        });
        return goBackButton;
    }
}
