package edu.northeastern.mygym;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class UpdateMemberDialog extends JDialog {
    private JTextField searchUsernameField;
    private JLabel existingNameLabel;
    private JLabel existingEmailLabel;
    private JTextField newNameField;
    private JTextField newEmailField;

    public UpdateMemberDialog(JFrame parent) {
        super(parent, "Update Member", true);
        initializeComponents();
    }

    private void initializeComponents() {
        setLayout(new GridLayout(6, 2));

        add(new JLabel("Search Username:"));
        searchUsernameField = new JTextField();
        add(searchUsernameField);

        add(new JLabel("Existing Name:"));
        existingNameLabel = new JLabel();
        add(existingNameLabel);

        add(new JLabel("Existing Email:"));
        existingEmailLabel = new JLabel();
        add(existingEmailLabel);

        add(new JLabel("New Name:"));
        newNameField = new JTextField();
        add(newNameField);

        add(new JLabel("New Email:"));
        newEmailField = new JTextField();
        add(newEmailField);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String searchUsername = searchUsernameField.getText();
                    // Call a method to get the existing name and email based on the username
                    UserInformation existingUser = DatabaseHelper.getUserInfoByUsername(searchUsername);

                    if (existingUser != null) {
                        existingNameLabel.setText(existingUser.getName());
                        existingEmailLabel.setText(existingUser.getEmail());
                    } else {
                        JOptionPane.showMessageDialog(null, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
                        existingNameLabel.setText(""); // Clear label
                        existingEmailLabel.setText(""); // Clear label
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    // Handle SQLException
                    JOptionPane.showMessageDialog(null, "Error searching for user.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(searchButton);

        JButton updateButton = new JButton("Update Member");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String searchUsername = searchUsernameField.getText();
                    String existingName = existingNameLabel.getText();
                    String existingEmail = existingEmailLabel.getText();
                    String newName = newNameField.getText();
                    String newEmail = newEmailField.getText();

                    // Determine the final name and email values
                    String finalName = (newName.isEmpty()) ? existingName : newName;
                    String finalEmail = (newEmail.isEmpty()) ? existingEmail : newEmail;

                    // Only update if at least one of the fields is non-empty
                    if (!newName.isEmpty() || !newEmail.isEmpty()) {
                        // Call a method to update the member in the database
                        DatabaseHelper.updateMember(searchUsername, finalName, finalEmail);

                        // Close the dialog after updating the member
                        dispose();
                        JOptionPane.showMessageDialog(null, "Member updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "No changes to update.", "Information", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    // Handle SQLException
                    JOptionPane.showMessageDialog(null, "Error updating member.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        
        });
        add(updateButton);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UpdateMemberDialog(null);
            }
        });
    }
}
