package edu.northeastern.mygym.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MemberPage extends JFrame {

	private JPanel memberPageContent;
	private JTextField memberNewNameText;
	private JTextField memberNewEmailText;
	private JTextField courseCodeText;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MemberPage frame = new MemberPage();
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
	public MemberPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar memberMenu = new JMenuBar();
		setJMenuBar(memberMenu);
		
		JMenu myAccountMenu = new JMenu("My account");
		memberMenu.add(myAccountMenu);
		
		JMenuItem viewProfileMenu = new JMenuItem("View profile");
		myAccountMenu.add(viewProfileMenu);
		
		JMenuItem updateProfileMenu = new JMenuItem("Update profile");
		myAccountMenu.add(updateProfileMenu);
		
		JMenu myCourseMenu = new JMenu("My Course");
		memberMenu.add(myCourseMenu);
		
		JMenuItem viewCoursesMenu = new JMenuItem("View courses");
		myCourseMenu.add(viewCoursesMenu);
		
		JMenuItem reserveCoursesMenu = new JMenuItem("Reserve courses");
		myCourseMenu.add(reserveCoursesMenu);
		
		JMenuItem myReservationMenu = new JMenuItem("My reservation");
		myCourseMenu.add(myReservationMenu);
		memberPageContent = new JPanel();
		memberPageContent.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(memberPageContent);
		memberPageContent.setLayout(new CardLayout(0, 0));
		
		JPanel memberPageHome = new JPanel();
		memberPageHome.setLayout(null);
		memberPageContent.add(memberPageHome, "mh");
		
		JPanel viewProfile = new JPanel();
		memberPageContent.add(viewProfile, "vp");
		viewProfile.setLayout(null);
		
		JLabel memUsername = new JLabel("Username :");
		memUsername.setBounds(70, 35, 84, 16);
		viewProfile.add(memUsername);
		
		JLabel memName = new JLabel("Name :");
		memName.setBounds(70, 81, 51, 16);
		viewProfile.add(memName);
		
		JLabel memEmail = new JLabel("Email :");
		memEmail.setBounds(70, 133, 51, 16);
		viewProfile.add(memEmail);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setBounds(218, 35, 159, 16);
		viewProfile.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setBounds(218, 81, 159, 16);
		viewProfile.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setBounds(218, 133, 153, 16);
		viewProfile.add(lblNewLabel_5);
		
		JPanel updateProfile = new JPanel();
		memberPageContent.add(updateProfile, "up");
		updateProfile.setLayout(null);
		
		JLabel upMemberUserName = new JLabel("");
		upMemberUserName.setBounds(213, 34, 141, 16);
		updateProfile.add(upMemberUserName);
		
		JLabel memberNewName = new JLabel("New Name :");
		memberNewName.setBounds(110, 88, 79, 16);
		updateProfile.add(memberNewName);
		
		JLabel memberNewEmail = new JLabel("New Email :");
		memberNewEmail.setBounds(110, 142, 79, 16);
		updateProfile.add(memberNewEmail);
		
		memberNewNameText = new JTextField();
		memberNewNameText.setBounds(213, 83, 130, 26);
		updateProfile.add(memberNewNameText);
		memberNewNameText.setColumns(10);
		
		memberNewEmailText = new JTextField();
		memberNewEmailText.setBounds(213, 137, 130, 26);
		updateProfile.add(memberNewEmailText);
		memberNewEmailText.setColumns(10);
		
		JButton updateProfileSave = new JButton("Save");
		updateProfileSave.setBounds(83, 186, 117, 29);
		updateProfile.add(updateProfileSave);
		
		JButton updateProfileCancel = new JButton("Cancel");
		updateProfileCancel.setBounds(249, 186, 117, 29);
		updateProfile.add(updateProfileCancel);
		
		JLabel updateExistingUsername = new JLabel("Username :");
		updateExistingUsername.setBounds(111, 34, 90, 16);
		updateProfile.add(updateExistingUsername);
		
		JPanel myReservation = new JPanel();
		memberPageContent.add(myReservation, "mr");
		myReservation.setLayout(null);
		
		JScrollPane myReservationScrollPane = new JScrollPane();
		myReservationScrollPane.setBounds(6, 6, 428, 189);
		myReservation.add(myReservationScrollPane);
		
		JButton cancelReservation = new JButton("Cancel Reservation");
		cancelReservation.setBounds(242, 207, 163, 29);
		myReservation.add(cancelReservation);
		
		JPanel viewCourses = new JPanel();
		memberPageContent.add(viewCourses, "vc");
		viewCourses.setLayout(null);
		
		JScrollPane courseListScrollPane = new JScrollPane();
		courseListScrollPane.setBounds(0, 33, 440, 207);
		viewCourses.add(courseListScrollPane);
		
		
		String[] columnNames = {"Select", "Course Name", "Course Code","Course Time", "Coach", "Equipment"};

        Object[][] data = {
                {false, "Course 1", "Time 1", "001","Coach 1", "Equipment 1"},
                {false, "Course 2", "Time 2", "002","Coach 2", "Equipment 2"},
                // Add more rows as needed...
        };

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 0 ? Boolean.class : Object.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0; // Make only the first column editable (checkbox)
            }
        };

        JTable myReservationTable = new JTable(model);
        myReservationScrollPane.setViewportView(myReservationTable);
        myReservationTable.setPreferredScrollableViewportSize(myReservationTable.getPreferredSize());

		
		
		
		JLabel courseList = new JLabel("Course  List");
		courseList.setBounds(6, 0, 168, 27);
		viewCourses.add(courseList);
		
		JPanel reserveCourses = new JPanel();
		memberPageContent.add(reserveCourses, "rc");
		reserveCourses.setLayout(null);
		
		JLabel courseCode = new JLabel("Course Code :");
		courseCode.setBounds(116, 80, 95, 16);
		reserveCourses.add(courseCode);
		
		courseCodeText = new JTextField();
		courseCodeText.setBounds(223, 75, 107, 26);
		reserveCourses.add(courseCodeText);
		courseCodeText.setColumns(10);
		
		
		
		
		
		JButton searchCourse = new JButton("Search");
		searchCourse.setBounds(164, 135, 117, 29);
		reserveCourses.add(searchCourse);
		
		viewProfileMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 切换到 registeredListMenu 卡片
                CardLayout cardLayout = (CardLayout) memberPageContent.getLayout();
                cardLayout.show(memberPageContent, "vp");
            }
        });
		
		updateProfileMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 切换到 updateProfileMenu 卡片
                CardLayout cardLayout = (CardLayout) memberPageContent.getLayout();
                cardLayout.show(memberPageContent, "up");
            }
        });
		
		viewCoursesMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 切换到 viewCoursesMenu 卡片
                CardLayout cardLayout = (CardLayout) memberPageContent.getLayout();
                cardLayout.show(memberPageContent, "vc");
            }
        });
		
		reserveCoursesMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 切换到 reserveCoursesMenu 卡片
                CardLayout cardLayout = (CardLayout) memberPageContent.getLayout();
                cardLayout.show(memberPageContent, "rc");
            }
        });
		
		myReservationMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 切换到 myReservationMenu 卡片
                CardLayout cardLayout = (CardLayout) memberPageContent.getLayout();
                cardLayout.show(memberPageContent, "mr");
            }
        });
		
		searchCourse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 点击按钮后，实例化并显示 CourseRegisterPage 窗口
            	CourseRegisterPage courseRegistPage = new CourseRegisterPage();
            	courseRegistPage.setVisible(true);
            }
        });
	}
}
