package edu.northeastern.mygym.view;

import java.awt.EventQueue;

import javax.swing.DefaultComboBoxModel;



import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import edu.northeastern.mygym.model.user.UserType;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class LogIn extends JFrame {

	private JPanel loginPage;
	private JTextField userNameText;
	private JPasswordField passWordText;
	private JComboBox userTypeComboBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogIn frame = new LogIn();
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
	public LogIn() {
		setTitle("MyGym");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		loginPage = new JPanel();
		loginPage.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(loginPage);
		loginPage.setLayout(null);
		
		JLabel loginTitle = new JLabel("Welcome   to   MyGym   System !");
		loginTitle.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		loginTitle.setBounds(72, 6, 326, 32);
		loginPage.add(loginTitle);
		
		JLabel userName = new JLabel("Username :");
		userName.setBounds(134, 69, 70, 16);
		loginPage.add(userName);
		
		JLabel passWord = new JLabel("Password :");
		passWord.setBounds(134, 114, 75, 16);
		loginPage.add(passWord);
		
		userNameText = new JTextField();
		userNameText.setBounds(227, 64, 130, 26);
		loginPage.add(userNameText);
		userNameText.setColumns(10);
		
		passWordText = new JPasswordField();
		passWordText.setBounds(227, 109, 130, 26);
		loginPage.add(passWordText);
		
		userTypeComboBox = new JComboBox();
		userTypeComboBox.setModel(new DefaultComboBoxModel(new UserType [] {UserType.ADMIN,UserType.MEMBER}));
		userTypeComboBox.setBounds(227, 158, 130, 27);
		loginPage.add(userTypeComboBox);
		
		JLabel userType = new JLabel("Usertype :");
		userType.setBounds(134, 162, 75, 16);
		loginPage.add(userType);
		
		JButton logInButton = new JButton("Log in");
		logInButton.setBounds(181, 220, 117, 29);
		loginPage.add(logInButton);
	}
}
