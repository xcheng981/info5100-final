package edu.northeastern.mygym.view;

import edu.northeastern.mygym.database.DatabaseConstants;
import edu.northeastern.mygym.model.user.Member;

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
    private Member loggedInMember;

    public MemberAccountScreen(Member loggedInMember) {
        this.loggedInMember = loggedInMember;
        initializeComponents();

        // Initialize database connection
        try {
            connection = DriverManager.getConnection(DatabaseConstants.URL, DatabaseConstants.USERNAME, DatabaseConstants.PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the window
        setLocationRelativeTo(null);
        setVisible(true);
    }


    private void initializeComponents() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)); // Use BoxLayout with Y_AXIS

        // Create an empty panel to fill the remaining space
        JPanel emptyPanel1 = new JPanel();
        add(emptyPanel1);

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 3)); // Increase the number of columns to 3
        JButton viewProfileButton = createViewProfileButton();
        JButton updateProfileButton = createUpdateProfileButton();

        // Set a larger font size for the buttons
        viewProfileButton.setFont(new Font("Arial", Font.BOLD, 25));
        updateProfileButton.setFont(new Font("Arial", Font.BOLD, 25));

        JPanel emptySpacePanel1 = new JPanel();
        buttonsPanel.add(emptySpacePanel1);

        
        buttonsPanel.add(viewProfileButton);

        // Add an empty panel for spacing
        JPanel emptySpacePanel2 = new JPanel();
        buttonsPanel.add(emptySpacePanel2);

        buttonsPanel.add(updateProfileButton);
        
        JPanel emptySpacePanel3 = new JPanel();
        buttonsPanel.add(emptySpacePanel3);


        // Set preferred size for buttonsPanel to make it taller
        buttonsPanel.setPreferredSize(new Dimension(800, 100)); // Adjust the width accordingly

        add(buttonsPanel);

        JPanel emptyPanel2 = new JPanel();
        add(emptyPanel2);

        // Add the Go Back button and adjust its height
        JPanel goBackPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton goBackButton = createGoBackButton();

        // Set a larger font size for the Go Back button
        goBackButton.setFont(new Font("Arial", Font.PLAIN, 25));

        // Set preferred size for the Go Back button to make it larger
        goBackButton.setPreferredSize(new Dimension(200, 60));

        goBackPanel.add(goBackButton);
        add(goBackPanel);
    }


    private JButton createViewProfileButton() {
        JButton viewProfileButton = new JButton("View Profile");
        viewProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewMemberProfile(loggedInMember.getUserName());
            }
        });
        return viewProfileButton;
    }

    private void viewMemberProfile(String username) {
        try {
            String query = "SELECT * FROM user WHERE username = ?";
            System.out.println("Username: " + username);

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        // Retrieve user information
                        String name = resultSet.getString("name");
                        String email = resultSet.getString("email");

                        // Display user information in a maximized window
                        displayProfileInformation(username, name, email);
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

        // Set font size for each label
        Font labelFont = new Font("Arial", Font.PLAIN, 20);
        usernameLabel.setFont(labelFont);
        nameLabel.setFont(labelFont);
        emailLabel.setFont(labelFont);

        // Set horizontal alignment to center for each label
        usernameLabel.setHorizontalAlignment(JLabel.CENTER);
        nameLabel.setHorizontalAlignment(JLabel.CENTER);
        emailLabel.setHorizontalAlignment(JLabel.CENTER);

        // Set vertical alignment to center for each label
        usernameLabel.setVerticalAlignment(JLabel.CENTER);
        nameLabel.setVerticalAlignment(JLabel.CENTER);
        emailLabel.setVerticalAlignment(JLabel.CENTER);

        // Create a panel to hold the labels
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        panel.add(usernameLabel);
        panel.add(nameLabel);
        panel.add(emailLabel);

        // Display the panel in a dialog
        JDialog dialog = new JDialog(MemberAccountScreen.this, "Member Profile", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Set a preferred size for the panel (adjust as needed)
        panel.setPreferredSize(new Dimension(400, 200));

        // Pack the dialog to adjust its size based on the preferred size of the panel
        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null); // Center the dialog on the screen
        dialog.setVisible(true);
    }



    private JButton createUpdateProfileButton() {
        JButton updateProfileButton = new JButton("Update Profile");
        updateProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openUpdateProfileScreen(loggedInMember.getUserName());
            }
        });
        return updateProfileButton;
    }

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
        
        // Set the background color to make it look like a label
        usernameField.setBackground(null);
        usernameField.setBorder(null);

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

        // Set the size of the updateProfileFrame to (500, 250)
        updateProfileFrame.setSize(500, 250);

        // Set the font size for all components in the frame
        Font font = new Font("Arial", Font.PLAIN, 22);
        Component[] components = updateProfileFrame.getContentPane().getComponents();
        for (Component component : components) {
            if (component instanceof JComponent) {
                ((JComponent) component).setFont(font);

                // Add some padding between JLabel and the border
                if (component instanceof JLabel) {
                    ((JLabel) component).setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));
                }
            }
        }

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
                new MemberHomepage(loggedInMember); // Assuming MemberHomepage is your previous screen
                dispose(); // Close the current member account screen
            }
        });
        return goBackButton;
    }
}
