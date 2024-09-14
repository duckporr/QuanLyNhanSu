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
public class EditEmployee extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DefaultTableModel model;
	private JTextField txtName;
	public static Department departmentSelected;
	private JTextField diachi;
	private JTextField email;
	private JTextField sdt;
	private JTextField vitri;
	private JTextField ngaybatdau;
	private JTextField luong;
	private JTextField status ;
	private EmployeeView employeeView;
	private int selectedRow = -1;

	   private ConnectionPool cp = new ConnectionPoolImpl();
	    private JComboBox<String> cbDepartment;
	    private JComboBox<String> cbPosition;
	    private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
	
	}

	/**
	 * Create the frame.
	 */
	public EditEmployee(EmployeeView employeeView, int employeeId, String name, String getemail, String phoneNumber,
            String address, String startDate, double salary, String getstatus, String department,String position) {
		// TODO Auto-generated constructor stub
	
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		
		setBounds(100, 100, 728, 611);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtName = new JTextField();
		txtName.setBounds(209, 69, 304, 31);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Họ tên");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_3.setBounds(85, 77, 67, 13);
		contentPane.add(lblNewLabel_3);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(55, 155, 255));
		panel.setBounds(0, 0, 751, 38);
		contentPane.add(panel);
		
		JLabel lblNewLabel_1 = new JLabel("Thông tin nhân viên");
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
		btnHuybo.setBounds(228, 510, 103, 27);
		contentPane.add(btnHuybo);
		
		JLabel lblNewLabel_3_1 = new JLabel("Địa chỉ");
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_3_1.setBounds(85, 118, 67, 13);
		contentPane.add(lblNewLabel_3_1);
		
		JLabel lblNewLabel_3_2 = new JLabel("Email");
		lblNewLabel_3_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_3_2.setBounds(85, 163, 67, 13);
		contentPane.add(lblNewLabel_3_2);
		
		JLabel lblNewLabel_3_3 = new JLabel("Số điện thoại");
		lblNewLabel_3_3.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_3_3.setBounds(85, 219, 103, 13);
		contentPane.add(lblNewLabel_3_3);
		
		JLabel lblNewLabel_3_4 = new JLabel("Phòng ban");
		lblNewLabel_3_4.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_3_4.setBounds(85, 270, 74, 13);
		contentPane.add(lblNewLabel_3_4);
		
		 cbDepartment = new JComboBox<>();
	     cbDepartment.setBounds(209, 263, 304, 28);
	     contentPane.add(cbDepartment);
	     loadDepartments();
	        
			
//			  JLabel lblNewLabel_3_5 = new JLabel("Vị trí"); lblNewLabel_3_5.setFont(new
//			  Font("Tahoma", Font.BOLD, 13)); lblNewLabel_3_5.setBounds(85, 316, 74, 13);
//			  contentPane.add(lblNewLabel_3_5);
//			 
		
		diachi = new JTextField();
		diachi.setColumns(10);
		diachi.setBounds(209, 110, 304, 31);
		contentPane.add(diachi);
		
		email = new JTextField();
		email.setColumns(10);
		email.setBounds(209, 155, 304, 31);
		contentPane.add(email);
		
		sdt = new JTextField();
		sdt.setColumns(10);
		sdt.setBounds(209, 211, 304, 31);
		contentPane.add(sdt);
		
		status = new JTextField();
		status.setColumns(10);
		status.setBounds(209, 423, 304, 31);
		contentPane.add(status);
		JLabel lblNewLabel_3_5_1 = new JLabel("Ngày bắt đầu");
		lblNewLabel_3_5_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_3_5_1.setBounds(85, 293, 103, 54);
		contentPane.add(lblNewLabel_3_5_1);
		
		JLabel lblNewLabel_3_5_2 = new JLabel("Trạng thái");
		lblNewLabel_3_5_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_3_5_2.setBounds(78, 431, 74, 13);
		contentPane.add(lblNewLabel_3_5_2);
		/*
		 * vitri = new JTextField(); vitri.setColumns(10); vitri.setBounds(209, 308,
		 * 304, 31); contentPane.add(vitri);
		 */
		JLabel lblNewLabel_3_4_1 = new JLabel("Vị trí");
		lblNewLabel_3_4_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_3_4_1.setBounds(85, 479, 74, 13);
		contentPane.add(lblNewLabel_3_4_1);
		
		cbPosition = new JComboBox<>();
		cbPosition.setBounds(209, 472, 304, 28);
		contentPane.add(cbPosition);
		loadPositions();
		ngaybatdau = new JTextField();
		ngaybatdau.setColumns(10);
		ngaybatdau.setBounds(209, 301, 304, 31);
		contentPane.add(ngaybatdau);
		
		luong = new JTextField();
		luong.setColumns(10);
		luong.setBounds(209, 361, 304, 31);
		contentPane.add(luong);
		
	   
        txtName.setText(name);
        email.setText(getemail);
        sdt.setText(phoneNumber);
        diachi.setText(address);
        ngaybatdau.setText(startDate);
        luong.setText(String.valueOf(salary));
        status.setText(getstatus);
       cbDepartment.setSelectedItem(department);

        JButton btnSave = new JButton("Lưu");
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
         
                String name = txtName.getText();
                String mail = email.getText();
                String phoneNumber = sdt.getText();
                String address = diachi.getText();
                String startDate = ngaybatdau.getText();
                double salary = Double.parseDouble(luong.getText());
                String condition = status.getText();
               String departmentName = (String) cbDepartment.getSelectedItem();
               String positionTitle = (String) cbPosition.getSelectedItem();

                // Kiểm tra dữ liệu trống
                if (name.isEmpty() || mail.isEmpty() || phoneNumber.isEmpty() || address.isEmpty()
                        || startDate.isEmpty() || salary == 0 || condition.isEmpty() /*|| departmentName.isEmpty()*/) {
                    JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin!", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Tạo đối tượng Employee
                Department department = DepartmentDAOImpl.getInstance(cp).getDepartmentByName(departmentName); 
                Position position = PositionDAOImpl.getInstance(cp).findByName(positionTitle);
                Employee employee = new Employee( name, department, mail, phoneNumber, address, startDate, salary, condition,position);

                // Sửa nhân viên trong database
                boolean result = EmployeeDAOImpl.getInstance(cp).updateById(employeeId, employee);

                if (result) {
                    JOptionPane.showMessageDialog(null, "Sửa nhân viên thành công!", "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                    // Cập nhật bảng danh sách trong EmployeeView
                    employeeView.updateEmployeeList();
                    dispose(); // Đóng cửa sổ EditEmployeeForm
                } else {
                    JOptionPane.showMessageDialog(null, "Sửa nhân viên thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnSave.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnSave.setBounds(405, 508, 98, 28);
        contentPane.add(btnSave);
		
		JLabel lblNewLabel_3_5_2_1 = new JLabel("Lương");
		lblNewLabel_3_5_2_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_3_5_2_1.setBounds(85, 369, 74, 13);
		contentPane.add(lblNewLabel_3_5_2_1);
		
		
		 this.employeeView = employeeView;
	        setVisible(true);
	}
	
	 private void loadPositions() {
	        ArrayList<Position> positions = PositionDAOImpl.getInstance(cp).findAll();
	        cbPosition.removeAllItems();
	        for (Position position : positions) {
	        	cbPosition.addItem(position.getTitle());
	        }
	    }
	 
	 private void loadDepartments() {
	        ArrayList<Department> departments = DepartmentDAOImpl.getInstance(cp).findAll();
	        cbDepartment.removeAllItems();
	        for (Department department : departments) {
	            cbDepartment.addItem(department.getDepartmentName());
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
