package edu.northeastern.mygym;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class DeleteMemberDialog extends JDialog {
    private JTextField usernameField;
    private JTextArea memberInfoArea;

    public DeleteMemberDialog(JFrame parent) {
        super(parent, "Delete Member", true);
        initializeComponents();
    }

    private void initializeComponents() {
        setLayout(new GridLayout(4, 1));

        JPanel inputPanel = new JPanel(new GridLayout(1, 2));
        inputPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        inputPanel.add(usernameField);
        add(inputPanel);

        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.add(new JLabel("Member Information:"));

        memberInfoArea = new JTextArea();
        memberInfoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(memberInfoArea);
        infoPanel.add(scrollPane);

        add(infoPanel);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String username = usernameField.getText();
                    // Call a method to get the existing name and email based on the username
                    UserInformation existingUser = DatabaseHelper.getUserInfoByUsername(username);

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
        add(searchButton);

        JButton deleteButton = new JButton("Delete Member");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this member?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    try {
                        String username = usernameField.getText();

                        // Call a method to delete the member from the database
                        DatabaseHelper.deleteMember(username);

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
        add(deleteButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the dialog if the user cancels the operation
                dispose();
            }
        });
        add(cancelButton);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
