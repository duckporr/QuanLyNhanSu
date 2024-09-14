package BtlJava.Nhom2.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.LineBorder;

public class AdminView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private PositionView positionView;
	private SalaryView salaryView;
	private TaskView taskView;
	private GroupView groupView;
	private UserView2 userView;
	private DepartmentView departmentView;
	private EmployeeView employeeView;
	Color DefaultColor, ClickedColor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminView frame = new AdminView();
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
	public AdminView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 128, 192));
		panel.setBounds(0, 8, 984, 47);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblQunLNhn = new JLabel("Ứng dụng quản lý nhân sự");
		lblQunLNhn.setForeground(new Color(255, 255, 255));
		lblQunLNhn.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblQunLNhn.setBounds(8, 8, 232, 31);
		panel.add(lblQunLNhn);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(0, 55, 200, 508);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JPanel vitri = new JPanel();
		vitri.setBorder(new LineBorder(new Color(0, 128, 255)));
		
		vitri.setBackground(new Color(255, 255, 255));
		vitri.setBounds(0, 385, 200, 40);
		panel_1.add(vitri);
		vitri.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Chức vụ");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setBounds(67, 10, 59, 24);
		vitri.add(lblNewLabel_1);
		
		JPanel luong = new JPanel();
		luong.setBorder(new LineBorder(new Color(0, 128, 255)));
		
		luong.setBackground(new Color(255, 255, 255));
		luong.setBounds(0, 425, 200, 40);
		panel_1.add(luong);
		luong.setLayout(null);
		
		
		
		JLabel lblNewLabel_1_1 = new JLabel("Lương");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_1.setBounds(68, 8, 59, 24);
		luong.add(lblNewLabel_1_1);
		
		JPanel mainContent = new JPanel();
		mainContent.setBackground(new Color(255, 255, 255));
		mainContent.setBounds(208, 63, 776, 500);
		contentPane.add(mainContent);
		setLocationRelativeTo(null);
		mainContent.setLayout(null);
		
		 positionView = new PositionView();
		 positionView.setBorder(null);
		 positionView.setLocation(0, 0);
        mainContent.add(positionView).setVisible(true);
        
		DefaultColor = new Color(255, 255, 255);
        ClickedColor = new Color(0, 153, 255);
        panel_1.setBackground(DefaultColor);
        vitri.setBackground(ClickedColor);
        luong.setBackground(DefaultColor);
        
        JPanel Task = new JPanel();
        Task.setBorder(new LineBorder(new Color(0, 128, 255)));
        
        Task.setLayout(null);
        Task.setBackground(Color.WHITE);
        Task.setBounds(0, 345, 200, 40);
        panel_1.add(Task);
        
        JLabel lblNewLabel_1_1_1 = new JLabel("Công tác");
        lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_1_1_1.setBounds(60, 9, 83, 24);
        Task.add(lblNewLabel_1_1_1);
        
        JPanel Group = new JPanel();
        Group.setBorder(new LineBorder(new Color(0, 128, 255)));
        
        Group.setLayout(null);
        Group.setBackground(Color.WHITE);
        Group.setBounds(0, 305, 200, 40);
        panel_1.add(Group);
        
        
        
        JLabel lblNewLabel_1_1_2 = new JLabel("Nhóm");
        lblNewLabel_1_1_2.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_1_1_2.setBounds(68, 8, 59, 24);
        Group.add(lblNewLabel_1_1_2);
        
        JPanel User = new JPanel();
        
        User.setLayout(null);
        User.setBorder(new LineBorder(new Color(0, 128, 255)));
        User.setBackground(Color.WHITE);
        User.setBounds(0, 265, 200, 40);
        panel_1.add(User);
        
        JLabel lblNewLabel_1_1_2_1 = new JLabel("Tài khoản");
        lblNewLabel_1_1_2_1.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_1_1_2_1.setBounds(57, 11, 77, 24);
        User.add(lblNewLabel_1_1_2_1);
        
        JPanel Department = new JPanel();
        
        Department.setLayout(null);
        Department.setBorder(new LineBorder(new Color(0, 128, 255)));
        Department.setBackground(Color.WHITE);
        Department.setBounds(0, 225, 200, 40);
        panel_1.add(Department);
        
        JLabel lblNewLabel_1_1_2_2 = new JLabel("Phòng ban");
        lblNewLabel_1_1_2_2.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_1_1_2_2.setBounds(68, 8, 77, 24);
        Department.add(lblNewLabel_1_1_2_2);
        
        JPanel Employee = new JPanel();
        
        Employee.setLayout(null);
        Employee.setBorder(new LineBorder(new Color(0, 128, 255)));
        Employee.setBackground(Color.WHITE);
        Employee.setBounds(0, 184, 200, 40);
        panel_1.add(Employee);
        
        JLabel lblNewLabel_1_1_2_3 = new JLabel("Nhân viên");
        lblNewLabel_1_1_2_3.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_1_1_2_3.setBounds(68, 8, 75, 24);
        Employee.add(lblNewLabel_1_1_2_3);
        vitri.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				vitri.setBackground(ClickedColor);
				luong.setBackground(DefaultColor);
				Task.setBackground(DefaultColor);
				Group.setBackground(DefaultColor);
				Employee.setBackground(DefaultColor);
				User.setBackground(DefaultColor);
				Department.setBackground(DefaultColor);
				mainContent.removeAll();
				positionView = new PositionView();
		        mainContent.add(positionView).setVisible(true);
			}
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		mainContent.removeAll();
        		positionView = new PositionView();
		        mainContent.add(positionView).setVisible(true);
        	}
		});
        luong.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				vitri.setBackground(DefaultColor);
				luong.setBackground(ClickedColor);
				Task.setBackground(DefaultColor);
				Group.setBackground(DefaultColor);
				Employee.setBackground(DefaultColor);
				User.setBackground(DefaultColor);
				Department.setBackground(DefaultColor);
				mainContent.removeAll();
				salaryView = new SalaryView();
		        mainContent.add(salaryView).setVisible(true);
			}
		});
        
        Group.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		vitri.setBackground(DefaultColor);
				luong.setBackground(DefaultColor);
				Task.setBackground(DefaultColor);
				Group.setBackground(ClickedColor);
				Employee.setBackground(DefaultColor);
				User.setBackground(DefaultColor);
				Department.setBackground(DefaultColor);
				
				mainContent.removeAll();
				groupView = new GroupView();
		        mainContent.add(groupView).setVisible(true);
        	}
        });
        
        Task.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		vitri.setBackground(DefaultColor);
				luong.setBackground(DefaultColor);
				Task.setBackground(ClickedColor);
				Group.setBackground(DefaultColor);
				Employee.setBackground(DefaultColor);
				User.setBackground(DefaultColor);
				Department.setBackground(DefaultColor);
				mainContent.removeAll();
				taskView = new TaskView();
		        mainContent.add(taskView).setVisible(true);
        	}
        });
        
        User.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		vitri.setBackground(DefaultColor);
				luong.setBackground(DefaultColor);
				Task.setBackground(DefaultColor);
				Group.setBackground(DefaultColor);
				Employee.setBackground(DefaultColor);
				User.setBackground(ClickedColor);
				Department.setBackground(DefaultColor);
				mainContent.removeAll();
				userView = new UserView2();
		        mainContent.add(userView).setVisible(true);
        	}
        });
        
        Department.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		vitri.setBackground(DefaultColor);
				luong.setBackground(DefaultColor);
				Task.setBackground(DefaultColor);
				Group.setBackground(DefaultColor);
				Employee.setBackground(DefaultColor);
				User.setBackground(DefaultColor);
				Department.setBackground(ClickedColor);
				mainContent.removeAll();
				departmentView = new DepartmentView();
		        mainContent.add(departmentView).setVisible(true);
        	}
        });
        
        Employee.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		vitri.setBackground(DefaultColor);
				luong.setBackground(DefaultColor);
				Task.setBackground(DefaultColor);
				Group.setBackground(DefaultColor);
				Employee.setBackground(ClickedColor);
				User.setBackground(DefaultColor);
				Department.setBackground(DefaultColor);
				mainContent.removeAll();
				employeeView = new EmployeeView();
		        mainContent.add(employeeView).setVisible(true);
        	}
        });
	}
}
