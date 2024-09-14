package BtlJava.Nhom2.view;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.mindrot.jbcrypt.BCrypt;

import BtlJava.Nhom2.dao.UserDAOImpl;
import BtlJava.Nhom2.model.User;
import BtlJava.Nhom2.view.UserView2;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UpdateUser extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtFullName;
	private JTextField txtUserName;
	private JTextField txtPassword;
	private JTextField txtEmail;
	private UserView2 userView;
	private int userId;
	private UserDAOImpl userDAO = new UserDAOImpl();

    public void setUserInfo(int userId, String fullName, String userName,String email) {
        this.userId = userId; 
        txtFullName.setText(fullName);
        txtUserName.setText(userName);
        txtPassword.setText("******************");
        txtEmail.setText(email);
    }
    public void setUserView(UserView2 userView) {
        this.userView = userView;
    }
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpdateUser frame = new UpdateUser();
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
	public UpdateUser() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 424, 360);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Cập nhật tài khoản");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(137, 10, 166, 27);
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
		txtFullName.setColumns(10);
		txtFullName.setBounds(160, 77, 168, 19);
		contentPane.add(txtFullName);
		
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
		
		JButton btnCapNhat = new JButton("Cập nhật");
		btnCapNhat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                
                String fullName = txtFullName.getText();
                String userName = txtUserName.getText();
                String password = txtPassword.getText();
                String email = txtEmail.getText();
                System.out.println(fullName);
                if (fullName.isEmpty() || userName.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (password.equalsIgnoreCase("******************")) {
                    password = BCrypt.hashpw(password, BCrypt.gensalt());
                }
                User user = new User(fullName, userName, password, email);
                boolean result = userDAO.updateById(userId, user);

                if (result != false) {
                    JOptionPane.showMessageDialog(null, "Cập nhật người dùng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    userView.loadUserList();
                    return;
                } else {
                    JOptionPane.showMessageDialog(null, "Cập nhật người dùng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
		});
		btnCapNhat.setBounds(243, 268, 85, 21);
		contentPane.add(btnCapNhat);
		
		JButton btnDoiMK = new JButton("Thay đổi mật khẩu");
		btnDoiMK.setBounds(99, 268, 134, 21);
		contentPane.add(btnDoiMK);
	}
	

}
