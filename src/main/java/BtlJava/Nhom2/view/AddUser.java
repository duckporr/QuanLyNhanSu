package BtlJava.Nhom2.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import BtlJava.Nhom2.dao.UserDAOImpl;
import BtlJava.Nhom2.model.User;
import BtlJava.Nhom2.view.UserView2;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddUser extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtFullName;
	private JTextField txtUserName;
	private JTextField txtPassword;
	private JTextField txtEmail;
	private UserView2 userView;

    public void setUserView(UserView2 userView) {
        this.userView = userView;
    }
    public void resetFields() {
		txtFullName.setText("");
		txtUserName.setText("");
		txtPassword.setText("");
		txtEmail.setText("");
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddUser frame = new AddUser();
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
	public AddUser() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 424, 360);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Thêm tài khoản");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(137, 10, 126, 27);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Họ và tên");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(41, 76, 77, 17);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Tên tài khoản");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(41, 120, 99, 17);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Mật khẩu");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1_1.setBounds(41, 164, 68, 17);
		contentPane.add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Email");
		lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1_1_1.setBounds(41, 208, 68, 17);
		contentPane.add(lblNewLabel_1_1_1_1);
		
		txtFullName = new JTextField();
		txtFullName.setBounds(160, 77, 168, 19);
		contentPane.add(txtFullName);
		txtFullName.setColumns(10);
		
		txtUserName = new JTextField();
		txtUserName.setColumns(10);
		txtUserName.setBounds(160, 121, 168, 19);
		contentPane.add(txtUserName);
		
		txtPassword = new JTextField();
		txtPassword.setColumns(10);
		txtPassword.setBounds(160, 165, 168, 19);
		contentPane.add(txtPassword);
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(160, 209, 168, 19);
		contentPane.add(txtEmail);
		
		JButton btnThem = new JButton("Thêm");
		btnThem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        String fullName = txtFullName.getText();
		        String userName = txtUserName.getText();
		        String password = txtPassword.getText();
		        String email = txtEmail.getText();
		        JOptionPane optionPane = new JOptionPane();
		        if (fullName.isEmpty() || userName.isEmpty() || password.isEmpty() || email.isEmpty()) {
		            optionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
		            return;
		        }
		        // Tạo đối tượng User với creationDate
		        User user = new User(fullName, userName, password, email); 
		        System.out.println("Qua");
		        UserDAOImpl userDAO = new UserDAOImpl();
		        if (userDAO.existsUserName(userName) == 0) {
		            optionPane.showMessageDialog(null, "Tên tài khoản đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
		            return;
		        }
		        if (userDAO.existsEmail(email) == 0) {
		            optionPane.showMessageDialog(null, "Email đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
		            return;
		        }

		        boolean result = userDAO.insert(user);

		        if (result) { // Sử dụng result trực tiếp để kiểm tra thành công
		            optionPane.showMessageDialog(null, "Tạo người dùng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
		            dispose(); 
		            userView.loadUserList();
		            return;
		        } else {
		            optionPane.showMessageDialog(null, "Tạo người dùng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
		            return;
		        }
		    }
		});
		btnThem.setBounds(243, 268, 85, 21);
		contentPane.add(btnThem);
	}
}
