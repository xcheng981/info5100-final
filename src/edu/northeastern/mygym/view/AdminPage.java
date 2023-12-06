package edu.northeastern.mygym.view;

import java.awt.EventQueue;



import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminPage extends JFrame {

	private JPanel adminPageContent;
	private JTextField addUserNameText;
	private JTextField addMemberNameText;
	private JTextField addEmailText;
	private JTextField updateUsernameText;
	private JTextField deleteUsernameText;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminPage frame = new AdminPage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AdminPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar adminMenu = new JMenuBar();
		setJMenuBar(adminMenu);
		
		JMenu memManageMenu = new JMenu("Membership management");
		adminMenu.add(memManageMenu);
		
		JMenuItem viewMembersMenu = new JMenuItem("View members");
		memManageMenu.add(viewMembersMenu);
		
		JMenuItem addMembersMenu = new JMenuItem("Add members");
		memManageMenu.add(addMembersMenu);
		
		JMenuItem updateMembersMenu = new JMenuItem("Update members");
		memManageMenu.add(updateMembersMenu);
		
		JMenuItem deleteMembersMenu = new JMenuItem("Delete members");
		memManageMenu.add(deleteMembersMenu);
		
		JMenu courseManageMenu = new JMenu("Course management");
		adminMenu.add(courseManageMenu);
		
		JMenuItem registeredListMenu = new JMenuItem("Registered list");
		courseManageMenu.add(registeredListMenu);
		adminPageContent = new JPanel();
		adminPageContent.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(adminPageContent);
		adminPageContent.setLayout(new CardLayout(0, 0));
		
		JPanel viewMembers = new JPanel();
		viewMembers.setLayout(null);
		adminPageContent.add(viewMembers, "vm");
		
		JPanel addMembers = new JPanel();
		addMembers.setLayout(null);
		adminPageContent.add(addMembers, "am");
		
		JLabel addUserName = new JLabel("Username :");
		addUserName.setBounds(51, 31, 80, 16);
		addMembers.add(addUserName);
		
		JLabel addMemberName = new JLabel("Member Name :");
		addMemberName.setBounds(51, 79, 107, 16);
		addMembers.add(addMemberName);
		
		JLabel addEmail = new JLabel("Email :");
		addEmail.setBounds(51, 127, 50, 16);
		addMembers.add(addEmail);
		
		JButton addMembersButton = new JButton("Add");
		addMembersButton.setBounds(295, 183, 117, 29);
		addMembers.add(addMembersButton);
		
		addUserNameText = new JTextField();
		addUserNameText.setBounds(214, 26, 130, 26);
		addMembers.add(addUserNameText);
		addUserNameText.setColumns(10);
		
		addMemberNameText = new JTextField();
		addMemberNameText.setBounds(214, 74, 130, 26);
		addMembers.add(addMemberNameText);
		addMemberNameText.setColumns(10);
		
		addEmailText = new JTextField();
		addEmailText.setBounds(214, 122, 130, 26);
		addMembers.add(addEmailText);
		addEmailText.setColumns(10);
		
		JPanel updateMembers = new JPanel();
		updateMembers.setLayout(null);
		adminPageContent.add(updateMembers, "um");
		
		JLabel updateUsername = new JLabel("Username :");
		updateUsername.setBounds(117, 63, 80, 16);
		updateMembers.add(updateUsername);
		
		updateUsernameText = new JTextField();
		updateUsernameText.setBounds(209, 58, 130, 26);
		updateMembers.add(updateUsernameText);
		updateUsernameText.setColumns(10);
		
		JButton updateMemSearchButton = new JButton("Search");
		updateMemSearchButton.setBounds(161, 144, 117, 29);
		updateMembers.add(updateMemSearchButton);
		
		JPanel deleteMembers = new JPanel();
		deleteMembers.setLayout(null);
		adminPageContent.add(deleteMembers, "dm");
		
		JLabel deleteUsername = new JLabel("Username :");
		deleteUsername.setBounds(105, 51, 76, 16);
		deleteMembers.add(deleteUsername);
		
		deleteUsernameText = new JTextField();
		deleteUsernameText.setBounds(193, 46, 130, 26);
		deleteMembers.add(deleteUsernameText);
		deleteUsernameText.setColumns(10);
		
		JButton deleteMemSearchButton = new JButton("Search");
		deleteMemSearchButton.setBounds(148, 127, 117, 29);
		deleteMembers.add(deleteMemSearchButton);
		
		JPanel registeredList = new JPanel();
		registeredList.setLayout(null);
		adminPageContent.add(registeredList, "rl");
		
		JPanel adminPageHome = new JPanel();
		adminPageContent.add(adminPageHome, "ah");
		adminPageHome.setLayout(null);
		
		 viewMembersMenu.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                // 切换到 viewMembers 卡片
	                CardLayout cardLayout = (CardLayout) adminPageContent.getLayout();
	                cardLayout.show(adminPageContent, "vm");
	            }
	        });
		 
		 addMembersMenu.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                // 切换到 addMembersMenu 卡片
	                CardLayout cardLayout = (CardLayout) adminPageContent.getLayout();
	                cardLayout.show(adminPageContent, "am");
	            }
	        });
		 
		 updateMembersMenu.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                // 切换到 updateMembersMenu 卡片
	                CardLayout cardLayout = (CardLayout) adminPageContent.getLayout();
	                cardLayout.show(adminPageContent, "um");
	            }
	        });
		 
		 deleteMembersMenu.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                // 切换到 deleteMembersMenu 卡片
	                CardLayout cardLayout = (CardLayout) adminPageContent.getLayout();
	                cardLayout.show(adminPageContent, "dm");
	            }
	        });
		 
		 registeredListMenu.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                // 切换到 registeredListMenu 卡片
	                CardLayout cardLayout = (CardLayout) adminPageContent.getLayout();
	                cardLayout.show(adminPageContent, "rl");
	            }
	        });
		 
		 updateMemSearchButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                // 点击按钮后，实例化并显示 UpdateMembersPage 窗口
	                UpdateMembersPage updateMemPage = new UpdateMembersPage();
	                updateMemPage.setVisible(true);
	            }
	        });
		 
		 deleteMemSearchButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                // 点击按钮后，实例化并显示 deleteMemSearchButton 窗口
	                DeleteMembersPage deleteMemPage = new DeleteMembersPage();
	                deleteMemPage.setVisible(true);
	            }
	        });
	}
}
