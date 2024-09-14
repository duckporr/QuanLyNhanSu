package BtlJava.Nhom2.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import BtlJava.Nhom2.dao.UserDAOImpl;
import BtlJava.Nhom2.model.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.ImageIcon;

public class LoginView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtTenTk;
	private JTextField txtMatkhau;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginView frame = new LoginView();
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
	public LoginView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Đăng nhập");
		lblNewLabel.setBounds(60, 90, 234, 55);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 45));
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Tên tài khoản");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(60, 185, 98, 20);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Mật khẩu");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(60, 263, 72, 20);
		contentPane.add(lblNewLabel_2);
		
		txtTenTk = new JTextField();
		txtTenTk.setBounds(60, 215, 253, 33);
		contentPane.add(txtTenTk);
		txtTenTk.setColumns(10);
		
		txtMatkhau = new JPasswordField();
		txtMatkhau.setColumns(10);
		txtMatkhau.setBounds(60, 293, 253, 33);
		contentPane.add(txtMatkhau);
		
		JButton btnDangnhap = new JButton("Đăng nhập");
		btnDangnhap.setBackground(new Color(255, 255, 255));
		btnDangnhap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        String userName = txtTenTk.getText();
		        String password = txtMatkhau.getText();

		        UserDAOImpl userDAO = new UserDAOImpl();
		        User user = userDAO.login(userName, password);

		        if (user != null) {
		        	dispose(); 
                    AdminView userView = new AdminView();
                    userView.setVisible(true);
		        } else {
		            JOptionPane.showMessageDialog(null, "Tên tài khoản hoặc mật khẩu không chính xác!", "Lỗi đăng nhập", JOptionPane.ERROR_MESSAGE);
		        }
		    }
		});
		btnDangnhap.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnDangnhap.setBounds(60, 378, 253, 33);
		contentPane.add(btnDangnhap);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0,128,192));
		panel.setBounds(383, 0, 616, 563);
		contentPane.add(panel);
		panel.setLayout(null);
	}
}
