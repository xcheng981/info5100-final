package edu.northeastern.mygym.view;

import edu.northeastern.mygym.model.user.Admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AddMemberDialog extends JDialog {

    private Admin admin;
    private JTextField usernameField;
    private JTextField nameField;
    private JTextField emailField;

    public AddMemberDialog(JFrame parent, Admin admin) {
        super(parent, "Add Member", true);
        this.admin = admin;
        initializeComponents();
    }

    private void initializeComponents() {
        setLayout(new GridLayout(4, 2));

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        JButton addButton = new JButton("Add Member");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String username = usernameField.getText().trim();
                    String name = nameField.getText().trim();
                    String email = emailField.getText().trim();

                    // Validate input fields
                    if (username.isEmpty() || name.isEmpty() || email.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please enter values for all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Check for username duplication
                    if (admin.isUsernameDuplicated(username)) {
                        JOptionPane.showMessageDialog(null, "Username is already taken. Please choose a different one.", "Username Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Call a method to add the member to the database
                    admin.addMember(username, name, email);

                    // Close the dialog after adding the member
                    dispose();
                    JOptionPane.showMessageDialog(null, "Member added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    // Handle SQLException
                    JOptionPane.showMessageDialog(null, "Error adding member.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(addButton);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
