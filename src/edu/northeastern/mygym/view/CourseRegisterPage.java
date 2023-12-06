package edu.northeastern.mygym.view;

import java.awt.EventQueue;



import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;

public class CourseRegisterPage extends JFrame {

	private JPanel courseRegister;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CourseRegisterPage frame = new CourseRegisterPage();
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
	public CourseRegisterPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 527, 372);
		courseRegister = new JPanel();
		courseRegister.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(courseRegister);
		courseRegister.setLayout(null);
		
		JButton courseRegisterButton = new JButton("Register");
		courseRegisterButton.setBounds(388, 290, 117, 29);
		courseRegister.add(courseRegisterButton);
		
		JLabel lblNewLabel = new JLabel("Course name :");
		lblNewLabel.setBounds(133, 72, 103, 16);
		courseRegister.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Time :");
		lblNewLabel_1.setBounds(133, 110, 61, 16);
		courseRegister.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Coach :");
		lblNewLabel_2.setBounds(133, 152, 88, 16);
		courseRegister.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Equipment :");
		lblNewLabel_3.setBounds(133, 196, 88, 16);
		courseRegister.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setBounds(303, 72, 147, 16);
		courseRegister.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setBounds(303, 110, 147, 16);
		courseRegister.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("");
		lblNewLabel_6.setBounds(303, 152, 141, 16);
		courseRegister.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("");
		lblNewLabel_7.setBounds(303, 196, 147, 16);
		courseRegister.add(lblNewLabel_7);
		
		courseRegisterButton.addActionListener(e -> {
            // 关闭当前窗口
            dispose();
        });
	}
}
