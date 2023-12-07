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
        pack();
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
        // 创建一个新的窗口或对话框来显示会员信息
        JFrame memberInfoFrame = new JFrame("Member Information");
        memberInfoFrame.setLayout(new BorderLayout());

        // 创建表格或其他组件来显示会员信息
        // 这里使用一个简单的文本区域来显示
        JTextArea textArea = new JTextArea();
        for (User member : members) {
            textArea.append("Username: " + member.getUserName() + "\n");
            textArea.append("Name: " + member.getName() + "\n");
            textArea.append("Email: " + member.getEmail() + "\n");
            textArea.append("\n");
        }

        // 添加文本区域到窗口
        memberInfoFrame.add(new JScrollPane(textArea), BorderLayout.CENTER);

        // 设置窗口属性
        memberInfoFrame.setSize(400, 300);
        memberInfoFrame.setLocationRelativeTo(null);
        memberInfoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 显示窗口
        memberInfoFrame.setVisible(true);
    }

    private JButton createAddMemberButton() {
        JButton addMemberButton = new JButton("Add Member");
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
        JButton backButton = new JButton("Back");
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
