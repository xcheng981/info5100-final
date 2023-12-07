package edu.northeastern.mygym.view;

import edu.northeastern.mygym.model.user.Admin;
import edu.northeastern.mygym.model.user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class DeleteMemberDialog extends JDialog {
    private static final int FONT_SIZE = 22; // Font size constant
    private Admin admin;
    private JTextField usernameField;
    private JTextArea memberInfoArea;

    public DeleteMemberDialog(JFrame parent, Admin admin) {
        super(parent, "Delete Member", true);
        this.admin = admin;
        initializeComponents();
    }

    private void setFontSize(JComponent component, int fontSize) {
        Font font = new Font("Arial", Font.BOLD, fontSize);
        component.setFont(font);
    }

    private void initializeComponents() {
        setLayout(new GridBagLayout());

        // First row: Username label and text field
        JLabel usernameLabel = new JLabel("Username:");
        setFontSize(usernameLabel, FONT_SIZE);
        usernameField = new JTextField();
        setFontSize(usernameField, FONT_SIZE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(20, 50, 0, 0); // Left margin
        add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(20, 0, 0, 50); // Right margin
        add(usernameField, gbc);

        // Second row: Search button
        JButton searchButton = new JButton("Search");
        setFontSize(searchButton, FONT_SIZE);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String username = usernameField.getText();
                    // Call a method to get the existing name and email based on the username
                    User existingUser = admin.getUserInfoByUserName(username);

                    if (existingUser != null) {
                        memberInfoArea.setText("Name: " + existingUser.getName() + "\nEmail: " + existingUser.getEmail());
                    } else {
                        JOptionPane.showMessageDialog(null, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
                        memberInfoArea.setText(""); // Clear area
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    // Handle SQLException
                    JOptionPane.showMessageDialog(null, "Error searching for user.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 50, 0, 50); // Top margin
        add(searchButton, gbc);

        // Third row: Member Information
        memberInfoArea = new JTextArea();
        memberInfoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(memberInfoArea);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(20, 50, 0, 50); // Top margin
        add(scrollPane, gbc);

        // Fourth row: Delete Member and Cancel buttons
        JButton deleteButton = new JButton("Delete Member");
        setFontSize(deleteButton, FONT_SIZE);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this member?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    try {
                        String username = usernameField.getText();

                        // Call a method to delete the member from the database
                        admin.deleteMember(username);

                        // Inform the user about the successful deletion
                        JOptionPane.showMessageDialog(null, "Member deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                        // Close the dialog after deleting the member
                        dispose();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        // Handle SQLException
                        JOptionPane.showMessageDialog(null, "Error deleting member.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        JButton cancelButton = new JButton("Cancel");
        setFontSize(cancelButton, FONT_SIZE);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the dialog if the user cancels the operation
                dispose();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10, 50, 20, 20); // Top margin
        add(deleteButton, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(10, 20, 20, 50); // Top margin
        add(cancelButton, gbc);

        setSize(600, 300); // Set window size
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DeleteMemberDialog(null, null);
            }
        });
    }
}
