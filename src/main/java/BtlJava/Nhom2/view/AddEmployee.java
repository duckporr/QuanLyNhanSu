package BtlJava.Nhom2.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import BtlJava.Nhom2.dao.DepartmentDAOImpl;
import BtlJava.Nhom2.dao.EmployeeDAOImpl;
import BtlJava.Nhom2.dao.PositionDAOImpl;
import BtlJava.Nhom2.database.ConnectionPool;
import BtlJava.Nhom2.database.ConnectionPoolImpl;
import BtlJava.Nhom2.model.Department;
import BtlJava.Nhom2.model.Employee;
import BtlJava.Nhom2.model.Position;

import javax.swing.border.LineBorder;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
public class AddEmployee extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DefaultTableModel model;
	private JTextField txtName;
	public static Department departmentSelected;
	private JTextField diachi;
	private JTextField email;
	private JTextField sdt;
	private JTextField ngaybatdau;
	private JTextField luong;
	private JTextField status ;
	private EmployeeView employeeView;
	
	private int selectedRow = -1;
	private PositionDAOImpl positionDAOImpl;

	   private ConnectionPool cp = new ConnectionPoolImpl();
	    private JComboBox<String> cbDepartment;
	    private JComboBox<String> cbPosition;
	    private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		/*
		 * EventQueue.invokeLater(new Runnable() { public void run() { try { AddEmployee
		 * frame = new AddEmployee(EmployeeView employeeView); frame.setVisible(true); }
		 * catch (Exception e) { e.printStackTrace(); } } });
		 */
	}

	/**
	 * Create the frame.
	 */
	public AddEmployee( EmployeeView employeeView) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		
		setBounds(100, 100, 728, 611);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtName = new JTextField();
		txtName.setBounds(209, 61, 304, 31);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Họ tên");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_3.setBounds(85, 69, 67, 13);
		contentPane.add(lblNewLabel_3);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(55, 155, 255));
		panel.setBounds(0, 0, 733, 38);
		contentPane.add(panel);
		
		JLabel lblNewLabel_1 = new JLabel("Thêm mới nhân viên");
		lblNewLabel_1.setForeground(new Color(0, 0, 0));
		panel.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		JButton btnHuybo = new JButton("Hủy bỏ");
		btnHuybo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(btnHuybo);
		        frame.dispose();
			}
		});
		btnHuybo.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnHuybo.setBackground(new Color(255, 0, 0));
		btnHuybo.setBounds(164, 507, 103, 27);
		contentPane.add(btnHuybo);
		
		JLabel lblNewLabel_3_1 = new JLabel("Địa chỉ");
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_3_1.setBounds(85, 110, 67, 13);
		contentPane.add(lblNewLabel_3_1);
		
		JLabel lblNewLabel_3_2 = new JLabel("Email");
		lblNewLabel_3_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_3_2.setBounds(85, 155, 67, 13);
		contentPane.add(lblNewLabel_3_2);
		
		JLabel lblNewLabel_3_3 = new JLabel("Số điện thoại");
		lblNewLabel_3_3.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_3_3.setBounds(85, 192, 103, 13);
		contentPane.add(lblNewLabel_3_3);
		
		JLabel lblNewLabel_3_4 = new JLabel("Phòng ban");
		lblNewLabel_3_4.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_3_4.setBounds(85, 233, 74, 13);
		contentPane.add(lblNewLabel_3_4);
		
		 cbDepartment = new JComboBox<>();
	     cbDepartment.setBounds(209, 226, 304, 28);
	     contentPane.add(cbDepartment);
	     loadDepartments();
		
		diachi = new JTextField();
		diachi.setColumns(10);
		diachi.setBounds(209, 102, 304, 31);
		contentPane.add(diachi);
		
		email = new JTextField();
		email.setColumns(10);
		email.setBounds(209, 147, 304, 31);
		contentPane.add(email);
		
		sdt = new JTextField();
		sdt.setColumns(10);
		sdt.setBounds(209, 184, 304, 31);
		contentPane.add(sdt);
		
		JLabel lblNewLabel_3_5_1 = new JLabel("Ngày bắt đầu");
		lblNewLabel_3_5_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_3_5_1.setBounds(85, 289, 103, 13);
		contentPane.add(lblNewLabel_3_5_1);
		
		JLabel lblNewLabel_3_5_2 = new JLabel("Trạng thái");
		lblNewLabel_3_5_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_3_5_2.setBounds(78, 390, 74, 13);
		contentPane.add(lblNewLabel_3_5_2);
		JLabel lblNewLabel_3_4_1 = new JLabel("Vị trí");
		lblNewLabel_3_4_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_3_4_1.setBounds(85, 437, 74, 13);
		contentPane.add(lblNewLabel_3_4_1);
		
		cbPosition = new JComboBox<>();
		cbPosition.setBounds(209, 437, 304, 28);
		contentPane.add(cbPosition);
		loadPositions();
		
		ngaybatdau = new JTextField();
		ngaybatdau.setColumns(10);
		ngaybatdau.setBounds(209, 281, 304, 31);
		contentPane.add(ngaybatdau);
		
		luong = new JTextField();
		luong.setColumns(10);
		luong.setBounds(209, 335, 304, 31);
		contentPane.add(luong);
		this.employeeView = employeeView;
		
		JButton btnThem = new JButton("Thêm");
		btnThem.setBackground(new Color(0, 128, 255));
		btnThem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String Name = txtName.getText();
				String mail = email.getText();
				String phoneNumber = sdt.getText();
				String address =diachi.getText();
				String startDate = ngaybatdau.getText();
				double salary = Double.parseDouble(luong.getText());
				String condition = status.getText();
				String departmentName = (String) cbDepartment.getSelectedItem();
				String positionTitle = (String) cbPosition.getSelectedItem();
				JOptionPane optionPane = new JOptionPane();
				if (Name.isEmpty() || mail.isEmpty() || phoneNumber.isEmpty() || address.isEmpty()
						|| startDate.isEmpty() || salary == 0 || condition.isEmpty()) {
					optionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin!", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				Department department = DepartmentDAOImpl.getInstance(cp).getDepartmentByName(departmentName); 
     			Position position = positionDAOImpl.getInstance(cp).findByName(positionTitle);
				Employee employee = new Employee(Name,department, mail, phoneNumber, address, startDate, salary, condition,position);
//				EmployeeDAOImpl employeeDAO = new EmployeeDAOImpl();
				Boolean result = EmployeeDAOImpl.getInstance(cp).insert(employee);

				if (result) {
					optionPane.showMessageDialog(null, "Tạo người dùng thànhg công!", "Thông báo",
							JOptionPane.INFORMATION_MESSAGE);
					employeeView.updateEmployeeList();;
                    dispose(); 
					return;
				} else {
					optionPane.showMessageDialog(null, "Tạo người dùng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		});
		btnThem.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnThem.setBounds(402, 505, 98, 28);
		contentPane.add(btnThem);
		
		JLabel lblNewLabel_3_5_2_1 = new JLabel("Lương");
		lblNewLabel_3_5_2_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_3_5_2_1.setBounds(85, 343, 74, 13);
		contentPane.add(lblNewLabel_3_5_2_1);
		
		status = new JTextField();
		status.setColumns(10);
		status.setBounds(209, 382, 304, 31);
		contentPane.add(status);
		
		
		 this.employeeView = employeeView;
	        setVisible(true);
	}
	
	 private void loadDepartments() {
	        ArrayList<Department> departments = DepartmentDAOImpl.getInstance(cp).findAll();
	        cbDepartment.removeAllItems();
	        for (Department department : departments) {
	            cbDepartment.addItem(department.getDepartmentName());
	        }
	    }
	 private void loadPositions() {
	        ArrayList<Position> positions = PositionDAOImpl.getInstance(cp).findAll();
	        cbPosition.removeAllItems();
	        for (Position position : positions) {
	        	cbPosition.addItem(position.getTitle());
	        }
	    }
	 
	 private void clearInputFields() {
			
			txtName.setText("");
			email.setText("");
			sdt.setText("");
			diachi.setText("");
			ngaybatdau.setText("");
			luong.setText("");
			status.setText("");
			selectedRow = -1; // Reset selectedRow
		}

	    private void openFile(String file) {
	        try {
	            File path = new File(file);
	            Desktop.getDesktop().open(path);
	        } catch (IOException e) {
	            System.out.println(e);
	        }
	    }
}
