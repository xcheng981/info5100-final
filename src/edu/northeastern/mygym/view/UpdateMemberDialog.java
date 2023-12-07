package edu.northeastern.mygym.view;

import edu.northeastern.mygym.model.user.Admin;
import edu.northeastern.mygym.model.user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class UpdateMemberDialog extends JDialog {
    private static final int FONT_SIZE = 22; // Font size constant
    private Admin admin;
    private JTextField searchUsernameField;
    private JLabel existingNameLabel;
    private JLabel existingEmailLabel;
    private JTextField newNameField;
    private JTextField newEmailField;

    public UpdateMemberDialog(JFrame parent, Admin admin) {
        super(parent, "Update Member", true);
        this.admin = admin;
        initializeComponents();
    }

    private void setFontSize(JComponent component) {
        Font font = new Font("Arial", Font.BOLD, FONT_SIZE);
        component.setFont(font);
    }
    
    private void initializeComponents() {
        setLayout(new GridLayout(6, 2));

        add(createLabeledTextField("Search Username:"));
        searchUsernameField = new JTextField();
        setFontSize(searchUsernameField);
        add(searchUsernameField);

        add(createLabeledTextField("Existing Name:"));
        existingNameLabel = new JLabel();
        setFontSize(existingNameLabel);
        add(existingNameLabel);

        add(createLabeledTextField("Existing Email:"));
        existingEmailLabel = new JLabel();
        setFontSize(existingEmailLabel);
        add(existingEmailLabel);

        add(createLabeledTextField("New Name:"));
        newNameField = new JTextField();
        setFontSize(newNameField);
        add(newNameField);

        add(createLabeledTextField("New Email:"));
        newEmailField = new JTextField();
        setFontSize(newEmailField);
        add(newEmailField);

        JButton searchButton = new JButton("Search");
        setFontSize(searchButton);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String searchUsername = searchUsernameField.getText();
                    // Call a method to get the existing name and email based on the username
                    User existingUser = admin.getUserInfoByUserName(searchUsername);

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
        setFontSize(updateButton);
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
                        admin.updateMember(searchUsername, finalName, finalEmail);

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

        setSize(700, 500); // Set window size
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createLabeledTextField(String labelText) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        // Create label with left margin
        JLabel label = new JLabel(labelText);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 50)); // Add left margin
        setFontSize(label);
        
        panel.add(label);
        return panel;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UpdateMemberDialog(null, null);
            }
        });
    }
}
