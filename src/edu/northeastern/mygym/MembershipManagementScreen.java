package edu.northeastern.mygym.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import edu.northeastern.mygym.model.user.Admin;
import edu.northeastern.mygym.model.user.User;

public class MembershipManagementScreen extends JFrame {
    private Admin loggedInAdmin;

    public MembershipManagementScreen(Admin loggedInAdmin) {
        this.loggedInAdmin = loggedInAdmin;
        initializeComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack(); // Pack to ensure proper sizing
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the window
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeComponents() {
        setLayout(new GridLayout(5, 1));
        add(createViewMembersButton());
        add(createAddMemberButton());
        add(createUpdateMemberButton());
        add(createDeleteMemberButton());
        add(createBackButton());
    }

    private JButton createViewMembersButton() {
        JButton viewMembersButton = new JButton("View Members");
        customizeButton(viewMembersButton);
        viewMembersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // 获取数据库中 userType 为 "member" 的所有用户信息
                    List<User> members = loggedInAdmin.listAllMembers();

                    // 显示会员信息（可以使用表格或其他控件）
                    showMemberInformation(members);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    // 处理 SQLException
                }
            }
        });
        return viewMembersButton;
    }

    // 在这里添加一个方法用于显示会员信息
    private void showMemberInformation(List<User> members) {
        // Create a new frame for member information
        JFrame memberInfoFrame = new JFrame("Member Information");
        memberInfoFrame.setLayout(new BorderLayout());

        // Set up table data and column names
        Object[][] data = new Object[members.size()][3]; // Assuming you want to display Username, Name, and Email
        String[] columnNames = {"Username", "Name", "Email"};

        // Populate table data
        for (int i = 0; i < members.size(); i++) {
            User member = members.get(i);
            data[i][0] = member.getUserName();
            data[i][1] = member.getName();
            data[i][2] = member.getEmail();
        }

        // Create a table with the data and column names
        JTable table = new JTable(data, columnNames);

        // Customize table appearance, e.g., font size
        Font headerFont = new Font("Arial", Font.BOLD, 20);
        table.getTableHeader().setFont(headerFont);

        // Set the font size for the cells
        Font cellFont = new Font("Arial", Font.PLAIN, 18);
        table.setFont(cellFont);

        // Set row height with increased spacing
        int rowHeight = (int) (table.getRowHeight() * 1.8); // Adjust the multiplier as needed
        table.setRowHeight(rowHeight);

        // Create a scroll pane for the table
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the scroll pane to the frame
        memberInfoFrame.add(scrollPane, BorderLayout.CENTER);

        // Set the frame properties
        memberInfoFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        memberInfoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Make the frame visible
        memberInfoFrame.setVisible(true);
    }


    private JButton createAddMemberButton() {
        JButton addMemberButton = new JButton("Add Member");
        customizeButton(addMemberButton);
        addMemberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open a dialog for adding a new member
                new AddMemberDialog(MembershipManagementScreen.this, loggedInAdmin);
            }
        });
        return addMemberButton;
    }

    private JButton createUpdateMemberButton() {
        JButton updateMemberButton = new JButton("Update Member");
        customizeButton(updateMemberButton);
        updateMemberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open a dialog for updating an existing member
                new UpdateMemberDialog(MembershipManagementScreen.this, loggedInAdmin);
            }
        });
        return updateMemberButton;
    }

    private JButton createDeleteMemberButton() {
        JButton deleteMemberButton = new JButton("Delete Member");
        customizeButton(deleteMemberButton);
        deleteMemberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open a dialog for deleting an existing member
                new DeleteMemberDialog(MembershipManagementScreen.this, loggedInAdmin);
            }
        });
        return deleteMemberButton;
    }

    private JButton createBackButton() {
        JButton backButton = new JButton("Go Back");
        customizeButton(backButton);
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

    private void customizeButton(JButton button) {
        Font font = new Font("Arial", Font.BOLD, 25);
        button.setFont(font);
    }
}
