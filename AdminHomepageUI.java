import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminHomepageUI {
    private JFrame frame;
    private JPanel topPanel;
    private JPanel sidebar;
    private CardLayout cardLayout;
    private JPanel contentPanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                AdminHomepageUI window = new AdminHomepageUI();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public AdminHomepageUI() {
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
        JButton myProfileButton = new JButton("Member Management");
        JButton myCourseButton = new JButton("Course Management");
        topPanel.add(myProfileButton);
        topPanel.add(myCourseButton);

        // Sidebar
        sidebar = new JPanel();
        JButton viewMemberButton = new JButton("View Member");
        JButton addMemberButton = new JButton("Add Member");
        JButton updateMemberButton = new JButton("Update Member");
        JButton deleteMemberButton = new JButton("Delete Member");
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS)); // Vertical layout
        sidebar.add(viewMemberButton);
        sidebar.add(addMemberButton);
        sidebar.add(updateMemberButton);
        sidebar.add(deleteMemberButton);

        // Profile Panel
        JPanel coursePanel = new JPanel();
        JButton viewRegistrationButton = new JButton("View Registration List");
        
        coursePanel.setLayout(new BoxLayout(coursePanel, BoxLayout.Y_AXIS)); // Vertical layout
        coursePanel.add(viewRegistrationButton);
        

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
