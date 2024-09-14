package BtlJava.Nhom2.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

import BtlJava.Nhom2.Files.ExportExcel;
import BtlJava.Nhom2.Files.ExportFileEmployee;
import BtlJava.Nhom2.model.Department;
import BtlJava.Nhom2.model.Employee;
import BtlJava.Nhom2.dao.DepartmentDAOImpl;
import BtlJava.Nhom2.dao.EmployeeDAOImpl;
import BtlJava.Nhom2.database.ConnectionPool;
import BtlJava.Nhom2.database.ConnectionPoolImpl;
import BtlJava.Nhom2.view.AddEmployee; 
import javax.swing.JEditorPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTabbedPane;

public class EmployeeView extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel tableModel;
	private JTable ListEmployee;
	private int selectedRow = -1;
	private JTextField txtTimKiem;
	private int currentPage = 1;
	private int pageSize = 5;
	private JLabel lblCurrentPage = new JLabel("Trang 1");
	private JButton btnPrevious = new JButton("Trước");
	private JButton btnNext = new JButton("Sau");
	private JComboBox<Integer> cbPageSize;
	   private ConnectionPool cp = new ConnectionPoolImpl();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeView frame = new EmployeeView();
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
	public EmployeeView() {
		BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 774, 533);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
//        loadDepartments();

		JButton btnThem = new JButton("Thêm");
		btnThem.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
                new AddEmployee(EmployeeView.this); // Mở cửa sổ AddEmployeeForm
            }
		});
		btnThem.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnThem.setBounds(10, 442, 98, 28);
		contentPane.add(btnThem);

		
		 // Thêm listener cho bảng
		
		JButton btnSua = new JButton("Sửa");
		btnSua.addActionListener(new ActionListener() {
			

				 @Override
				    public void actionPerformed(ActionEvent e) {
				        if (selectedRow == -1) {
				            JOptionPane.showMessageDialog(null, "Vui lòng chọn nhân viên để sửa!", "Lỗi",
				                    JOptionPane.ERROR_MESSAGE);
				            return;
				        }
				      
				        int employeeId = (int) tableModel.getValueAt(selectedRow, 0);
				        String name = (String) tableModel.getValueAt(selectedRow, 1);
				        String email = (String) tableModel.getValueAt(selectedRow, 2);
				        String phoneNumber = (String) tableModel.getValueAt(selectedRow, 3);
				        String address = (String) tableModel.getValueAt(selectedRow, 4);
				        String startDate = (String) tableModel.getValueAt(selectedRow, 5);
				        double salary = (double)tableModel.getValueAt(selectedRow, 6);
				        String status = (String) tableModel.getValueAt(selectedRow, 7);
			            String department = (String) tableModel.getValueAt(selectedRow, 8);
			            String position = (String) tableModel.getValueAt(selectedRow, 9);
				       
				        new EditEmployee(EmployeeView.this, employeeId, name, email, phoneNumber, address,
				                startDate, salary, status, department,position); 
				    }
		});
		btnSua.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnSua.setBounds(138, 442, 98, 28);
		contentPane.add(btnSua);

		JButton btnXoa = new JButton("Xóa");
		btnXoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn nhân viên để xóa!", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
					return;
				}else {

				int employeeId = (int) tableModel.getValueAt(selectedRow, 0); // Lấy employeeId từ cột STT

				int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa nhân viên này?",
						"Xác nhận xóa", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
//					EmployeeDAOImpl employeeDAO = new EmployeeDAOImpl();
					Boolean deleteResult = EmployeeDAOImpl.getInstance(cp).deleteById(employeeId);

					if (deleteResult) {
						JOptionPane.showMessageDialog(null, "Xóa nhân viên thành công!", "Thông báo",
								JOptionPane.INFORMATION_MESSAGE);
						loadEmployeeList();
//						clearInputFields();
					} else {
						JOptionPane.showMessageDialog(null, "Xóa nhân viên thất bại!", "Lỗi",
								JOptionPane.ERROR_MESSAGE);
					}
				}
				}
			}
		});
		btnXoa.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnXoa.setBounds(284, 442, 98, 28);
		contentPane.add(btnXoa);

		JList list = new JList();
		list.setBounds(690, 384, 0, 0);
		contentPane.add(list);

		tableModel = new DefaultTableModel();
		ListEmployee = new JTable(tableModel);
		ListEmployee.setRowHeight(30);
		ListEmployee.setBounds(0, 150, 758, 236);
		contentPane.add(ListEmployee);
		 // Thêm listener cho bảng
		ListEmployee.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    selectedRow = ListEmployee.getSelectedRow();
                    if (selectedRow != -1) {
                        // Lấy dữ liệu từ hàng đã chọn
                       int employeeId = Integer.parseInt(ListEmployee.getValueAt(selectedRow, 0).toString());
                        String name = ListEmployee.getValueAt(selectedRow, 1).toString();
                        String email = ListEmployee.getValueAt(selectedRow, 2).toString();
                        String phoneNumber =ListEmployee.getValueAt(selectedRow, 3).toString();
                        String address = ListEmployee.getValueAt(selectedRow, 4).toString();
                        String startDate = ListEmployee.getValueAt(selectedRow, 5).toString();
                        String salary = ListEmployee.getValueAt(selectedRow, 6).toString();
                        String status = ListEmployee.getValueAt(selectedRow, 7).toString();
                       String department = ListEmployee.getValueAt(selectedRow, 8).toString();
                       String position = ListEmployee.getValueAt(selectedRow, 9).toString();

                        // Cập nhật nội dung các ô trong form
//                        txtEmployeeId.setText(String.valueOf(employeeId));
						/*
						 * txtName.setText(name); txtEmail.setText(email);
						 * txtPhoneNumber.setText(phoneNumber); txtAddress.setText(address);
						 * txtStartDate.setText(startDate); txtSalary.setText(salary);
						 * txtStatus.setText(status); cbDepartment.setSelectedItem(department);
						 */
                    }
                }
            }
        });

		txtTimKiem = new JTextField();
		txtTimKiem.setColumns(10);
		txtTimKiem.setBounds(140, 64, 249, 28);
		contentPane.add(txtTimKiem);

		JButton btnTimKiem = new JButton("Tìm kiếm");
		btnTimKiem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nameToFind = txtTimKiem.getText(); // Lấy tên từ txtTimKiem
				if (!nameToFind.isEmpty()) {
					List<Employee> foundEmployees= EmployeeDAOImpl.getInstance(cp).findByName(nameToFind);
		

					if (!foundEmployees.isEmpty()) {
						// Cập nhật JTable với thông tin người dùng tìm được
						tableModel.setRowCount(0);
						for (int i = 0; i < foundEmployees.size(); i++) {
							Employee employee = foundEmployees.get(i);
							tableModel.addRow(new Object[] { employee.getEmployeeId(), employee.getName(), employee.getEmail(),
									employee.getPhoneNumber(), employee.getAddress(), employee.getStartDate(), employee.getSalary(),
								employee.getStatus(),employee.getDepartment().getDepartmentName(),employee.getPosition().getTitle()});
						}
						ListEmployee.setModel(tableModel);
					} else {
						JOptionPane.showMessageDialog(null, "Không tìm thấy nhân viên với tên này!", "Thông báo",
								JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
		btnTimKiem.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnTimKiem.setBounds(470, 60, 122, 31);
		contentPane.add(btnTimKiem);

		JButton btnLamMoi = new JButton("Làm mới");
		btnLamMoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadEmployeeList();
			}
		});

		btnLamMoi.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnLamMoi.setBounds(614, 57, 103, 31);
		contentPane.add(btnLamMoi);

		JButton btnExportExcel = new JButton("Xuất Excel");
		btnExportExcel.setBackground(new Color(128, 255, 255));
		btnExportExcel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ExportFileEmployee.exportDepartmentCountToExcel(); // Gọi hàm xuất Excel
			}
		});
		btnExportExcel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnExportExcel.setBounds(537, 442, 150, 28); // Đặt vị trí và kích thước
		contentPane.add(btnExportExcel);
		JLabel lblNewLabel_6 = new JLabel("DANH SÁCH NHÂN VIÊN");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblNewLabel_6.setBounds(234, 115, 275, 34);
		contentPane.add(lblNewLabel_6);
		
//		setWidthTable();
		loadEmployeeList();

//		ListEmployee.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent evt) {
//				selectedRow = ListEmployee.getSelectedRow();
//				if (selectedRow != -1) {
//					txtEmployeeId.setText( tableModel.getValueAt(selectedRow, 0).toString());
//					txtName.setText((String) tableModel.getValueAt(selectedRow, 1));
//					txtEmail.setText((String) tableModel.getValueAt(selectedRow, 2));
//					txtPhoneNumber.setText((String) tableModel.getValueAt(selectedRow, 3));
//					txtAddress.setText((String) tableModel.getValueAt(selectedRow, 4));
//					txtStartDate.setText((String) tableModel.getValueAt(selectedRow, 5));
//					txtSalary.setText((String) tableModel.getValueAt(selectedRow, 6));
//					txtStatus.setText((String) tableModel.getValueAt(selectedRow, 7));
//				 String department =  tableModel.getValueAt(selectedRow, 8).toString();
//		         cbDepartment.setSelectedItem(department); 
//					
//				}
//			}
//		});
		JPanel paginationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		paginationPanel.setBounds(428, 387, 320, 32);
		contentPane.add(paginationPanel);

		 btnPrevious = new JButton("Trước");
		btnPrevious.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (currentPage > 1) {
					currentPage--;
					loadEmployeeList();
				}
			}
		});
		paginationPanel.add(btnPrevious);
		 lblCurrentPage = new JLabel("Trang 1");
		paginationPanel.add(lblCurrentPage);

		 btnNext = new JButton("Sau");
		btnNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int totalPages = (int) Math.ceil((double) EmployeeDAOImpl.getInstance(cp).countAll() / pageSize);
				if (currentPage < totalPages) {
					currentPage++;
					loadEmployeeList();
				}
			}
		});
		paginationPanel.add(btnNext);

		cbPageSize = new JComboBox<>(new Integer[] { 5, 10, 20 });
		cbPageSize.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pageSize = (int) cbPageSize.getSelectedItem();
				currentPage = 1;
				loadEmployeeList();
			}
		});
		paginationPanel.add(cbPageSize);
		loadEmployeeList();
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 0, 160));
		panel.setBounds(0, 10, 764, 40);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("QUẢN LÝ THÔNG TIN NHÂN VIÊN");
		lblNewLabel.setBounds(148, 0, 448, 37);
		lblNewLabel.setForeground(new Color(255, 255, 255));
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));

	}

	 public void updateEmployeeList() {
	        loadEmployeeList(); // Gọi hàm loadEmployeeList để load lại dữ liệu
	    }
	private void loadEmployeeList() {
		int totalPages = (int) Math.ceil((double) EmployeeDAOImpl.getInstance(cp).countAll() / pageSize);
//		EmployeeDAOImpl employeeDAO = new EmployeeDAOImpl();
		ArrayList<Employee> employeeList = EmployeeDAOImpl.getInstance(cp).findPage(currentPage, pageSize);
		   tableModel.setColumnIdentifiers(new String[]{"Mã Nhân Viên", "Tên", "Email", "Số điện thoại", "Địa chỉ", 
                   "Ngày bắt đầu", "Lương", "Trạng thái", "Phòng ban","Vị trí"}); 
		tableModel.setRowCount(0);
		
		for (int i = 0; i < employeeList.size(); i++) {
			Employee employee = employeeList.get(i);
			
			tableModel.addRow(new Object[] { employee.getEmployeeId(), employee.getName(), employee.getEmail(),
					employee.getPhoneNumber(), employee.getAddress(), employee.getStartDate(), employee.getSalary(),
				employee.getStatus(),employee.getDepartment().getDepartmentName(),employee.getPosition().getTitle()});
		}
		ListEmployee.setModel(tableModel);
		lblCurrentPage.setText("Trang " + currentPage);
        btnPrevious.setEnabled(currentPage > 1);
        btnNext.setEnabled(currentPage < totalPages);
	}
}
