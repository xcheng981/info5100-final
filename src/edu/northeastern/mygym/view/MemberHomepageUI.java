package edu.northeastern.mygym.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MemberHomepageUI {
    private JFrame frame;
    private JPanel topPanel;
    private JPanel sidebar;
    private CardLayout cardLayout;
    private JPanel contentPanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                MemberHomepageUI window = new MemberHomepageUI();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public MemberHomepageUI() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("MyGym");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        // Top Panel (My Profile and My Course buttons)
        topPanel = new JPanel();
        JButton myProfileButton = new JButton("My Profile");
        JButton myCourseButton = new JButton("My Course");
        topPanel.add(myProfileButton);
        topPanel.add(myCourseButton);

        // Sidebar
        sidebar = new JPanel();
        JButton viewProfileButton = new JButton("View Profile");
        JButton updateProfileButton = new JButton("Update Profile");
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS)); // Vertical layout
        sidebar.add(viewProfileButton);
        sidebar.add(updateProfileButton);

        // Profile Panel
        JPanel coursePanel = new JPanel();
        JButton viewCourseButton = new JButton("View Course");
        JButton reserveCourseButton = new JButton("Reserve Course");
        JButton cancelReservationButton = new JButton("Cancel Reservation");
        coursePanel.setLayout(new BoxLayout(coursePanel, BoxLayout.Y_AXIS)); // Vertical layout
        coursePanel.add(viewCourseButton);
        coursePanel.add(reserveCourseButton);
        coursePanel.add(cancelReservationButton);

        // Add panels to contentPanel
        contentPanel.add(sidebar, "Profile");
        contentPanel.add(coursePanel, "Course");

        // Add topPanel and contentPanel to the frame
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(contentPanel, BorderLayout.CENTER);

        // Button actions
        myProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "Profile");
            }
        });

        myCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "Course");
            }
        });

        // Logout Button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add your logout logic here
                JOptionPane.showMessageDialog(frame, "Logout Successful!");
            }
        });

        // Add logoutButton to the frame (in the bottom-right corner)
        frame.add(logoutButton, BorderLayout.SOUTH);
    }
}
