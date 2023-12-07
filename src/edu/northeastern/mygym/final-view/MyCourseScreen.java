package edu.northeastern.mygym.view;

import edu.northeastern.mygym.model.Course;
import edu.northeastern.mygym.model.Reservation;
import edu.northeastern.mygym.model.user.Member;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;


public class MyCourseScreen extends JFrame {
    private Member loggedInMember;
    private JFrame courseInfoFrame;

    public MyCourseScreen(Member loggedInMember) {
        this.loggedInMember = loggedInMember;
        initializeComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the window
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeComponents() {
        setLayout(new GridLayout(4, 1));
        add(createViewCourseButton());
        add(createReserveCourseButton());
        add(createCancelReservationButton());
        add(createGoBackButton());
    }

    private JButton createViewCourseButton() {
        JButton viewCourseButton = new JButton("View Course");
        viewCourseButton.setFont(new Font("Arial", Font.PLAIN, 24));
        viewCourseButton.setMargin(new Insets(20, 20, 20, 20));
        viewCourseButton.setPreferredSize(new Dimension(200, 60));
        viewCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<Course> courses = loggedInMember.listFirst10Courses();
                    displayCourseInformation(courses);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        return viewCourseButton;
    }

    // Display course information as an instance method
    private void displayCourseInformation(List<Course> courseList) {
        courseInfoFrame = new JFrame("Course Information");
        courseInfoFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Create a JTable and DefaultTableModel
        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel();

        // Set the column names
        String[] columns = {"Course Code", "Course Name", "Schedule", "Equipment", "Capacity"};
        model.setColumnIdentifiers(columns);
        table.setModel(model);

        // Populate the table with course information
        for (Course course : courseList) {
            model.addRow(new Object[]{
                    course.getCourseCode(),
                    course.getCourseName(),
                    course.getSchedule(),
                    course.getEquipment(),
                    course.getCapacity()
            });
        }

        // Set the font size for the header
        Font headerFont = new Font("Arial", Font.BOLD, 20);
        table.getTableHeader().setFont(headerFont);

        // Set the font size for the cells
        Font cellFont = new Font("Arial", Font.PLAIN, 18);
        table.setFont(cellFont);

        // Set row height with increased spacing
        int rowHeight = (int) (table.getRowHeight() * 1.8); // Adjust the multiplier as needed
        table.setRowHeight(rowHeight);

        // Create a JScrollPane to allow scrolling if the number of courses is large
        JScrollPane scrollPane = new JScrollPane(table);
        courseInfoFrame.add(scrollPane);

        // Set the default close operation
        courseInfoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set the frame to be visible
        courseInfoFrame.setVisible(true);
    }

    private JButton createReserveCourseButton() {
        JButton reserveCourseButton = new JButton("Reserve Course");
        reserveCourseButton.setFont(new Font("Arial", Font.PLAIN, 24));
        reserveCourseButton.setMargin(new Insets(20, 20, 20, 20));
        reserveCourseButton.setPreferredSize(new Dimension(200, 60));
        reserveCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Allow the user to input the course code
                String courseCode = JOptionPane.showInputDialog("Enter Course Code:");

                // Check if the course code is not null or empty before attempting to reserve
                if (courseCode != null && !courseCode.isEmpty()) {
                    reserveCourse(courseCode);
                } else {
                    // Handle the case when the user cancels the input or provides an empty string
                    JOptionPane.showMessageDialog(null, "Invalid Course Code. Please try again.");
                }
            }
        });
        return reserveCourseButton;
    }

    private void reserveCourse(String courseCode) {
        try {
            if (loggedInMember.reserveCourse(courseCode)) {
                // Reservation was successful, show success message
                JOptionPane.showMessageDialog(null, "Course reserved successfully!");
            } else {
                // Reservation failed, error message has already been shown in the method
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Handle the exception appropriately
            JOptionPane.showMessageDialog(null, "Error occurred while reserving the course. Please try again.");
        }
    }

    private JButton createCancelReservationButton() {
        JButton cancelReservationButton = new JButton("Cancel Reservation");
        cancelReservationButton.setFont(new Font("Arial", Font.PLAIN, 24));
        cancelReservationButton.setMargin(new Insets(20, 20, 20, 20));
        cancelReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<Reservation> userReservations = loggedInMember.getReservations();

                    // Create a custom JPanel for displaying reservations in a table-like format
                    JPanel panel = new JPanel(new BorderLayout());
                    JTable reservationTable = createReservationTable(userReservations);
                    JScrollPane scrollPane = new JScrollPane(reservationTable);
                    panel.add(scrollPane, BorderLayout.CENTER);

                    // Display the dialog with the custom panel
                    JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
                    JDialog dialog = optionPane.createDialog("Cancel Reservations");

                    // Set the size of the dialog
                    dialog.setSize(new Dimension(1000, 800));
                    dialog.setVisible(true);

                    if ((Integer) optionPane.getValue() == JOptionPane.OK_OPTION) {
                        // Process selected reservations for cancellation
                        boolean anyReservationCanceled = false;

                        for (int i = 0; i < userReservations.size(); i++) {
                            if ((Boolean) reservationTable.getValueAt(i, 0)) {
                                // Cancel the selected reservation
                                loggedInMember.cancelReservation(userReservations.get(i).getReservationId());
                                anyReservationCanceled = true;
                            }
                        }

                        if (anyReservationCanceled) {
                            JOptionPane.showMessageDialog(null, "Reservations canceled successfully!");
                        } else {
                            JOptionPane.showMessageDialog(null, "No reservations were selected for cancellation.");
                        }
                    } else {
                        // User clicked "Cancel" or closed the dialog
                        JOptionPane.showMessageDialog(null, "Cancellation operation aborted.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error occurred while canceling reservations. Please try again.");
                }
            }
        });
        return cancelReservationButton;
    }


    private JTable createReservationTable(List<Reservation> userReservations) {
        // Create a table model with checkboxes for selection
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 0) {
                    return Boolean.class; // Checkbox column
                }
                return super.getColumnClass(column);
            }
        };

        // Set the column names
        String[] columns = {"Select", "Reservation Details"};
        model.setColumnIdentifiers(columns);

        // Populate the table with reservation information
        for (Reservation reservation : userReservations) {
            model.addRow(new Object[]{
                    false, // Initially unchecked
                    reservation.toString()
            });
        }

        JTable table = new JTable(model);

        // Set the column width for the checkbox column (index 0)
        TableColumn checkboxColumn = table.getColumnModel().getColumn(0);
        checkboxColumn.setMinWidth(100); // Minimum width
        checkboxColumn.setMaxWidth(100); // Maximum width
        checkboxColumn.setPreferredWidth(100); // Preferred/actual width

        // Set the font size for the header
        Font headerFont = new Font("Arial", Font.BOLD, 22);
        table.getTableHeader().setFont(headerFont);

        // Set the font size for the cells
        Font cellFont = new Font("Arial", Font.PLAIN, 20);
        table.setFont(cellFont);

        // Set row height with increased spacing
        int rowHeight = (int) (table.getRowHeight() * 1.8); // Adjust the multiplier as needed
        table.setRowHeight(rowHeight);

        return table;
    }


    private JButton createGoBackButton() {
        JButton goBackButton = new JButton("Go Back");
        goBackButton.setFont(new Font("Arial", Font.PLAIN, 24));
        goBackButton.setMargin(new Insets(20, 20, 20, 20));
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Pass the loggedInUsername when creating a new MemberHomepage
                new MemberHomepage(loggedInMember);
                dispose();
            }
        });
        return goBackButton;
    }
}