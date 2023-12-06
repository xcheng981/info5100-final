package edu.northeastern.mygym.view;

import java.awt.EventQueue;



import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class UpdateMembersPage extends JFrame {

	private JPanel updateMembersPage;
	private JTextField updateNewNameText;
	private JTextField updateNewEmailText;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpdateMembersPage frame = new UpdateMembersPage();
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
	public UpdateMembersPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		updateMembersPage = new JPanel();
		updateMembersPage.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(updateMembersPage);
		updateMembersPage.setLayout(null);
		
		JLabel updateMembersTitile = new JLabel("Member Information");
		updateMembersTitile.setBounds(38, 20, 140, 16);
		updateMembersPage.add(updateMembersTitile);
		
		JLabel updateNewName = new JLabel("New Name :");
		updateNewName.setBounds(96, 114, 82, 16);
		updateMembersPage.add(updateNewName);
		
		JLabel updateNewEmail = new JLabel("New Email :");
		updateNewEmail.setBounds(96, 147, 102, 16);
		updateMembersPage.add(updateNewEmail);
		
		updateNewNameText = new JTextField();
		updateNewNameText.setBounds(227, 114, 130, 26);
		updateMembersPage.add(updateNewNameText);
		updateNewNameText.setColumns(10);
		
		updateNewEmailText = new JTextField();
		updateNewEmailText.setBounds(227, 142, 130, 26);
		updateMembersPage.add(updateNewEmailText);
		updateNewEmailText.setColumns(10);
		
		JButton updateMemberButton = new JButton("Update");
		updateMemberButton.setBounds(81, 211, 117, 29);
		updateMembersPage.add(updateMemberButton);
		
		JLabel existingName = new JLabel("Existing Name :");
		existingName.setBounds(96, 55, 102, 16);
		updateMembersPage.add(existingName);
		
		JLabel existingEmail = new JLabel("Existing Email :");
		existingEmail.setBounds(96, 83, 102, 16);
		updateMembersPage.add(existingEmail);
		
		JLabel existingNameText = new JLabel("");
		existingNameText.setBounds(231, 55, 126, 16);
		updateMembersPage.add(existingNameText);
		
		JLabel existingEmailText = new JLabel("");
		existingEmailText.setBounds(231, 86, 126, 16);
		updateMembersPage.add(existingEmailText);
		
		JButton cancelUpdateMemButton = new JButton("Cancel");
		cancelUpdateMemButton.setBounds(256, 211, 117, 29);
		updateMembersPage.add(cancelUpdateMemButton);
		
		cancelUpdateMemButton.addActionListener(e -> {
            // 关闭当前窗口
            dispose();
        });
	}
}
