package edu.northeastern.mygym.view;

import java.awt.EventQueue;



import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;

public class DeleteMembersPage extends JFrame {

	private JPanel deleteMembersPage;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DeleteMembersPage frame = new DeleteMembersPage();
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
	public DeleteMembersPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		deleteMembersPage = new JPanel();
		deleteMembersPage.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(deleteMembersPage);
		deleteMembersPage.setLayout(null);
		
		JLabel deleteMemTitle = new JLabel("Member Information");
		deleteMemTitle.setBounds(22, 16, 137, 16);
		deleteMembersPage.add(deleteMemTitle);
		
		JButton deleteMemberButton = new JButton("Delete");
		deleteMemberButton.setBounds(103, 211, 117, 29);
		deleteMembersPage.add(deleteMemberButton);
		
		JLabel delMemName = new JLabel("Member Name :");
		delMemName.setBounds(103, 99, 104, 16);
		deleteMembersPage.add(delMemName);
		
		JLabel delMemEmail = new JLabel("Email :");
		delMemEmail.setBounds(103, 139, 61, 16);
		deleteMembersPage.add(delMemEmail);
		
		JLabel delMemNameText = new JLabel("");
		delMemNameText.setBounds(245, 99, 152, 16);
		deleteMembersPage.add(delMemNameText);
		
		JLabel delMemEmailText = new JLabel("");
		delMemEmailText.setBounds(245, 139, 145, 16);
		deleteMembersPage.add(delMemEmailText);
		
		JButton cancelDelMemButton = new JButton("Cancel");
		cancelDelMemButton.setBounds(257, 211, 117, 29);
		deleteMembersPage.add(cancelDelMemButton);
		
		cancelDelMemButton.addActionListener(e -> {
            // 关闭当前窗口
            dispose();
        });
	}

}
