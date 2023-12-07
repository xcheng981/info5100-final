package edu.northeastern.mygym;

import edu.northeastern.mygym.database.DatabaseConstants;
import edu.northeastern.mygym.database.DatabaseHelper;
import edu.northeastern.mygym.model.user.Admin;
import edu.northeastern.mygym.model.user.Member;
import edu.northeastern.mygym.model.user.User;
import edu.northeastern.mygym.view.AdminHomepage;
import edu.northeastern.mygym.view.MemberHomepage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class App extends JFrame {
    private static final DatabaseHelper DATABASE_HELPER = new DatabaseHelper();
    private Connection connection;
    private JPanel loginPage;
    private JTextField userNameText;
    private JPasswordField passWordText;
    private JComboBox<String> userTypeComboBox;
    private JToggleButton showPasswordToggle;

    public App() {
        try {
            connection = DriverManager.getConnection(DatabaseConstants.URL, DatabaseConstants.USERNAME, DatabaseConstants.PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        setLayout(new BorderLayout());
        add(createWelcomePanel(), BorderLayout.NORTH);
        add(createLoginFormPanel(), BorderLayout.CENTER);

        setTitle("MyGym");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the window
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createWelcomePanel() {
        JPanel welcomePanel = new JPanel();
        JLabel welcomeLabel = new JLabel("Welcome to MyGym System!");
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 36)); // Increase font size
        welcomePanel.add(welcomeLabel);
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0)); // Add vertical space
        return welcomePanel;
    }

    private JPanel createLoginFormPanel() {
        JPanel loginFormPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20); // Increase vertical and horizontal insets

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 18)); // Increase font size
        loginFormPanel.add(usernameLabel, gbc);

        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.WEST;
        JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(300, 50)); // Increase width
        usernameField.setFont(new Font("Arial", Font.PLAIN, 18)); // Increase font size
        loginFormPanel.add(usernameField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 18)); // Increase font size
        loginFormPanel.add(passwordLabel, gbc);

        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.WEST;
        passWordText = new JPasswordField();
        passWordText.setPreferredSize(new Dimension(300, 50)); // Increase width
        passWordText.setFont(new Font("Arial", Font.PLAIN, 18)); // Increase font size
        loginFormPanel.add(passWordText, gbc);

        gbc.gridx = 3;
        gbc.anchor = GridBagConstraints.WEST;
        showPasswordToggle = new JToggleButton("Show Password");
        showPasswordToggle.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int state = e.getStateChange();
                if (state == ItemEvent.SELECTED) {
                    passWordText.setEchoChar((char) 0); // Show password
                } else {
                    passWordText.setEchoChar('*'); // Hide password
                }
            }
        });
        loginFormPanel.add(showPasswordToggle, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel userTypeLabel = new JLabel("UserType:"); // Prompt label for user type selection
        userTypeLabel.setFont(new Font("Arial", Font.PLAIN, 18)); // Increase font size
        loginFormPanel.add(userTypeLabel, gbc);

        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.WEST;
        userTypeComboBox = new JComboBox<>(new String[]{"Choose Identity", "Admin", "Member"});
        userTypeComboBox.setPreferredSize(new Dimension(300, 70)); // Increase height
        userTypeComboBox.setFont(new Font("Arial", Font.PLAIN, 18)); // Increase font size
        loginFormPanel.add(userTypeComboBox, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(300, 40)); // Increase width
        loginButton.setFont(new Font("Arial", Font.PLAIN, 18)); // Increase font size
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passWordText.getPassword());
                String userType = (String) userTypeComboBox.getSelectedItem();

                if ("Choose Identity".equals(userType)) {
                    JOptionPane.showMessageDialog(loginFormPanel, "Please select a valid login type", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (validateUser(username, password, userType)) {
                    dispose();

                    try {
                        User user = DATABASE_HELPER.getUserInfoByUsername(username);
                        if ("Admin".equals(userType)) {
                            Admin admin = new Admin(user.getProfile());
                            new AdminHomepage(admin);
                        } else if ("Member".equals(userType)) {
                            Member member = new Member(user.getProfile());
                            new MemberHomepage(member);
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(loginFormPanel, "Invalid username, password, or userType", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        loginButton.setPreferredSize(new Dimension(300, 40)); // Increase width
        loginFormPanel.add(loginButton, gbc);

        return loginFormPanel;
    }

    private boolean validateUser(String username, String password, String userType) {
        try {
            String query = "SELECT * FROM user WHERE username = ? AND password = ? AND usertype = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                statement.setString(2, password);
                statement.setString(3, userType);

                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        new App();
    }
}
