package edu.northeastern.mygym.view;

import edu.northeastern.mygym.model.Reservation;
import edu.northeastern.mygym.model.user.Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class CourseManagementScreen extends JFrame {
    private Admin loggedInAdmin;

    public CourseManagementScreen(Admin loggedInAdmin) {
        this.loggedInAdmin = loggedInAdmin;
        initializeComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeComponents() {
        setLayout(new GridBagLayout());

        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JButton viewReservationButton = createViewReservationsButton();

        // Add "View Reservations" button
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(50, 0, 50, 0); // Optional: Add some vertical spacing
        gbc.anchor = GridBagConstraints.CENTER; // Center-align the button
        buttonsPanel.add(viewReservationButton, gbc);

        // Add buttonsPanel to the main frame
        add(buttonsPanel, gbc);

        JButton goBackButton = createGoBackButton();

        // Add "Back" button in a new row with increased vertical gap
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(20, 0, 10, 0); // Increase the top inset for more spacing
        gbc.anchor = GridBagConstraints.CENTER; // Center-align the button
        add(goBackButton, gbc);

        pack(); // Pack the frame to ensure proper layout
        setLocationRelativeTo(null);
    }


    private JButton createViewReservationsButton() {
        JButton viewReservationsButton = new JButton("View Reservations");

        // Set preferred size for the button
        viewReservationsButton.setPreferredSize(new Dimension(200, 80));

        // Set font size for the button
        Font buttonFont = new Font("Arial", Font.BOLD, 20);
        viewReservationsButton.setFont(buttonFont);

        viewReservationsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<Reservation> reservations = loggedInAdmin.listAllReservations();

                    // Create a table model and add data
                    DefaultTableModel model = new DefaultTableModel();
                    model.addColumn("ID");
                    model.addColumn("Course Code");
                    model.addColumn("Course Name");
                    model.addColumn("User");

                    for (Reservation reservation : reservations) {
                        model.addRow(new Object[]{
                                reservation.getReservationId(),
                                reservation.getCourseCode(),
                                reservation.getCourseName(),
                                reservation.getUserName()
                        });
                    }

                    // Create JTable and add it to a JScrollPane
                    JTable table = new JTable(model);
                    JScrollPane scrollPane = new JScrollPane(table);
                    
                    Font headerFont = new Font("Arial", Font.BOLD, 20);
                    table.getTableHeader().setFont(headerFont);

                    // Set the font size for the cells
                    Font cellFont = new Font("Arial", Font.PLAIN, 18);
                    table.setFont(cellFont);
                    

                    // Set row height with increased spacing
                    int rowHeight = (int) (table.getRowHeight() * 1.8); // Adjust the multiplier as needed
                    table.setRowHeight(rowHeight);

                    // Create a new JFrame to display the reservations table
                    JFrame tableFrame = new JFrame("All Reservations");
                    tableFrame.setLayout(new BorderLayout());
                    tableFrame.add(scrollPane, BorderLayout.CENTER);
                    tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    tableFrame.setSize(800, 600); // Set an appropriate size
                    tableFrame.setLocationRelativeTo(null);
                    tableFrame.setVisible(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    // Handle SQLException
                }
            }
        });
        return viewReservationsButton;
    }

    private JButton createGoBackButton() {
        JButton backButton = new JButton("Back");

        // Set preferred size for the button
        backButton.setPreferredSize(new Dimension(150, 60));

        // Set font size for the button
        Font buttonFont = new Font("Arial", Font.BOLD, 18);
        backButton.setFont(buttonFont);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Go back to the admin homepage
                new AdminHomepage(loggedInAdmin);
                dispose(); // Close the current screen
            }
        });
        return backButton;
    }
}
