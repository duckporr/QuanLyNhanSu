package BtlJava.Nhom2.view;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;

import BtlJava.Nhom2.Files.ExportExcel;
import BtlJava.Nhom2.dao.DepartmentDAOImpl;
import BtlJava.Nhom2.dao.EmployeeDAOImpl;
import BtlJava.Nhom2.database.ConnectionPool;
import BtlJava.Nhom2.database.ConnectionPoolImpl;
import BtlJava.Nhom2.model.Department;
import BtlJava.Nhom2.model.Employee;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Canvas;

public class DepartmentView extends JInternalFrame {

	    private static final long serialVersionUID = 1L;
	    private JPanel contentPane;
	    private JTextField txtDepartmentId;
	    private JTextField txtDepartmentName;
	    private JTextField txtDescription;
	    private JTable departmentTable;
	    private DefaultTableModel tableModel;
	    private int selectedRow = -1;

	    private int currentPage = 1; 
	    private int pageSize = 5; 
	    private JLabel lblCurrentPage; 
	    private JButton btnPrevious; 
	    private JButton btnNext; 
	    private JComboBox<Integer> cbPageSize; 
	    private JTextField txtTimKiem; 
	    private ConnectionPool cp = new ConnectionPoolImpl();
//	    private DepartmentDAOImpl departmentDAO = new DepartmentDAOImpl(); 

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DepartmentView frame = new DepartmentView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public DepartmentView() {
			BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
	        ui.setNorthPane(null);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(0, 0, 774, 533);
	        contentPane = new JPanel();
	        contentPane.setBackground(new Color(192, 192, 192));
	        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	        setContentPane(contentPane);
	        contentPane.setLayout(null);

	        JLabel lblNewLabel_1 = new JLabel("Mã Khoa:");
	        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
	        lblNewLabel_1.setBounds(24, 301, 98, 16);
	        contentPane.add(lblNewLabel_1);


	        txtDepartmentId = new JTextField();
	        txtDepartmentId.setBounds(130, 289, 125, 28);
	        contentPane.add(txtDepartmentId);
	        txtDepartmentId.setColumns(10);

	        JLabel lblNewLabel_1_1 = new JLabel("Tên Khoa:");
	        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
	        lblNewLabel_1_1.setBounds(24, 347, 98, 16);
	        contentPane.add(lblNewLabel_1_1);

	        txtDepartmentName = new JTextField();
	        txtDepartmentName.setColumns(10);
	        txtDepartmentName.setBounds(130, 335, 125, 28);
	        contentPane.add(txtDepartmentName);

	        txtDescription = new JTextField();
	        txtDescription.setColumns(10);
	        txtDescription.setBounds(130, 377, 125, 28);
	        contentPane.add(txtDescription);

	        JLabel lblNewLabel_1_1_1 = new JLabel("Mô tả:");
	        lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
	        lblNewLabel_1_1_1.setBounds(24, 389, 98, 16);
	        contentPane.add(lblNewLabel_1_1_1);

	    
	        JButton btnThem = new JButton("Thêm");
	        btnThem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int id = Integer.parseInt(txtDepartmentId.getText());
					String Name = txtDepartmentName.getText();
	                String description = txtDescription.getText();
	               
	                JOptionPane optionPane = new JOptionPane();
	                if (id ==0 ||Name.isEmpty() || description.isEmpty() ) {
	                    optionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	                    return;
	                }
	                Department department = new Department(id,Name,description);
//	                DepartmentDAOImpl departmentDAOimpl = new DepartmentDAOImpl();
	          
	                Boolean result = DepartmentDAOImpl.getInstance(cp).insert(department);

	                if (result) {
	                	optionPane.showMessageDialog(null, "Tạo phòng ban thànhg công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	                	loadDepartmentList();
	                	return;
	                } else {
	                	optionPane.showMessageDialog(null, "Tạo phòng ban  thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	                	return;
	                }
				}
			});
	        btnThem.setFont(new Font("Tahoma", Font.PLAIN, 16));
	        btnThem.setBounds(24, 430, 73, 28);
	        contentPane.add(btnThem);

	        JButton btnSua = new JButton("Sửa");
	        btnSua.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                if (selectedRow == -1) {
	                    JOptionPane.showMessageDialog(null, "Vui lòng chọn Phòng để sửa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	                    return;
	                }
	                int departmentId;
	                try {
	                    departmentId = Integer.parseInt(txtDepartmentId.getText());
	                } catch (NumberFormatException ex) {
	                    JOptionPane.showMessageDialog(null, "Mã Phòng ban phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	                    return;
	                }
	                String name = txtDepartmentName.getText();
	                String description = txtDescription.getText();
	                if (name.isEmpty() || description.isEmpty() ) {
	                    JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	                    return;
	                }
	                Department department = new Department(departmentId,name,description);
//	                DepartmentDAOImpl departmentDAOimpl = new DepartmentDAOImpl();
	               Boolean result = DepartmentDAOImpl.getInstance(cp).updateById(departmentId, department);
	                if (result) {
	                    JOptionPane.showMessageDialog(null, "Cập nhật phòng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	                    loadDepartmentList();
	                    clearInputFields();
	                    return;
	                } else {
	                    JOptionPane.showMessageDialog(null, "Cập nhật phòng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	                    return;
	                }
	            }
	        });
	        btnSua.setFont(new Font("Tahoma", Font.PLAIN, 16));
	        btnSua.setBounds(110, 430, 73, 28);
	        contentPane.add(btnSua);

	    
	        JButton btnXoa = new JButton("Xóa");
	        btnXoa.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                if (selectedRow == -1) {
	                    JOptionPane.showMessageDialog(null, "Vui lòng chọn Khoa để xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	                    return;
	                }
	                int departmentId = (int) tableModel.getValueAt(selectedRow, 1);
	                int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa Khoa này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
	                if (result == JOptionPane.YES_OPTION) {
	                    try {
	                        Boolean deleteResult = DepartmentDAOImpl.getInstance(cp).deleteById(departmentId);
	                        if (deleteResult) {
	                            JOptionPane.showMessageDialog(null, "Xóa Khoa thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	                            loadDepartmentList();
	                            clearInputFields();
	                        } else {
	                            JOptionPane.showMessageDialog(null, "Xóa Khoa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	                        }
	                    } catch (RuntimeException ex) {
	                 
	                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
	                    }
	                }
	            }
	        });
	        btnXoa.setFont(new Font("Tahoma", Font.PLAIN, 16));
	        btnXoa.setBounds(193, 430, 73, 28);
	        contentPane.add(btnXoa);


	        JPanel paginationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	        paginationPanel.setBounds(371, 433, 377, 25);
	        contentPane.add(paginationPanel);

	        btnPrevious = new JButton("Trước");
	        btnPrevious.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                if (currentPage > 1) {
	                    currentPage--;
	                    loadDepartmentList();
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
	                int totalPages = (int) Math.ceil((double) DepartmentDAOImpl.getInstance(cp).countAll() / pageSize);
	                if (currentPage < totalPages) {
	                    currentPage++;
	                    loadDepartmentList();
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
	                loadDepartmentList();
	            }
	        });
	        paginationPanel.add(cbPageSize);

	        txtTimKiem = new JTextField();
	        txtTimKiem.setColumns(10);
	        txtTimKiem.setBounds(24, 144, 150, 32);
	        contentPane.add(txtTimKiem);

	        JButton btnTimKiem = new JButton("Tìm kiếm");
	        btnTimKiem.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                String nameToFind = txtTimKiem.getText();
	                if (!nameToFind.isEmpty()) {
	                    List<Department> foundDepartments = DepartmentDAOImpl.getInstance(cp).findByName(nameToFind);
	                    if (!foundDepartments.isEmpty()) {   
	                        tableModel.setRowCount(0);
	                        for (Department department : foundDepartments) {
	                            tableModel.addRow(new Object[] {
	                                    department.getDepartmentId(),
	                                    department.getDepartmentName(),
	                                    department.getDescription()
	                            });
	                        }
	                        departmentTable.setModel(tableModel);
	                    } else {
	                        JOptionPane.showMessageDialog(null, "Không tìm thấy Khoa với tên này!", "Thông báo", JOptionPane.WARNING_MESSAGE);
	                    }
	                }
	            }
	        });
	        
	        btnTimKiem.setFont(new Font("Tahoma", Font.PLAIN, 16));
	        btnTimKiem.setBounds(187, 142, 109, 31);
	        contentPane.add(btnTimKiem);

	        JButton btnLamMoi = new JButton("Làm mới");
	        btnLamMoi.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                loadDepartmentList();
	            }
	        });
	        btnLamMoi.setFont(new Font("Tahoma", Font.PLAIN, 16));
	        btnLamMoi.setBounds(187, 192, 103, 31);
	        contentPane.add(btnLamMoi);

	        JLabel lblNewLabel_6 = new JLabel("Danh sách phòng ban");
	        lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 24));
	        lblNewLabel_6.setBounds(446, 90, 284, 19);
	        contentPane.add(lblNewLabel_6);

	        JButton btnExportExcel = new JButton("Xuất Excel");
	        btnExportExcel.setBackground(new Color(128, 255, 255));
	        btnExportExcel.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                ExportExcel.exportDepartmentCountToExcel(); 
	            }
	        });
	        btnExportExcel.setFont(new Font("Tahoma", Font.PLAIN, 16));
	        btnExportExcel.setBounds(24, 193, 109, 28);
	        contentPane.add(btnExportExcel); 

	        tableModel = new DefaultTableModel() {
	            @Override
	            public boolean isCellEditable(int row, int column) {
	                return column != 0; 
	            }
	        };
	        tableModel.setColumnCount(0);
	        tableModel.addColumn("STT");
	        tableModel.addColumn("Mã Khoa");
	        tableModel.addColumn("Tên Khoa");
	        tableModel.addColumn("Mô tả");
	        departmentTable = new JTable(tableModel);
	        departmentTable.setRowHeight(30); 
	        departmentTable.setBounds(314, 132, 434, 291);
	        contentPane.add(departmentTable);
	        
	        JPanel panel = new JPanel();
	        panel.setBackground(new Color(0, 0, 160));
	        panel.setBounds(0, 0, 758, 71);
	        contentPane.add(panel);
	        	        panel.setLayout(null);
	        
	        	        JLabel lblNewLabel = new JLabel("QUẢN LÝ PHÒNG BAN");
	        	        lblNewLabel.setBounds(198, 11, 343, 37);
	        	        panel.add(lblNewLabel);
	        	        lblNewLabel.setForeground(new Color(255, 255, 255));
	        	        lblNewLabel.setBackground(new Color(192, 192, 192));
	        	        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
	        	        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));


	        departmentTable.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent evt) {
	                selectedRow = departmentTable.getSelectedRow();
	                if (selectedRow != -1) {
	                    txtDepartmentId.setText( tableModel.getValueAt(selectedRow, 1).toString());
	                    txtDepartmentName.setText((String) tableModel.getValueAt(selectedRow, 2));
	                    txtDescription.setText((String) tableModel.getValueAt(selectedRow, 3));
	                }
	            }
	        });

	        loadDepartmentList();
	        setVisible(true);
	    }

	    private void loadDepartmentList() {
	        int totalPages = (int) Math.ceil((double) DepartmentDAOImpl.getInstance(cp).countAll() / pageSize);
	        ArrayList<Department> departmentList = DepartmentDAOImpl.getInstance(cp).findPage(currentPage, pageSize);
	        tableModel.setRowCount(0);
	        for (int i = 0; i < departmentList.size(); i++) {
	            Department department = departmentList.get(i);
	            tableModel.addRow(new Object[] {
	                (currentPage - 1) * pageSize + i + 1, // STT
	                department.getDepartmentId(),
	                department.getDepartmentName(),
	                department.getDescription()
	            });
	        }
	        departmentTable.setModel(tableModel);
	        lblCurrentPage.setText("Trang " + currentPage);
	        btnPrevious.setEnabled(currentPage > 1);
	        btnNext.setEnabled(currentPage < totalPages);
	    }

	    public void setWidthTable() {
	        tableModel = new DefaultTableModel();
	        String[] headerTbl = new String[]{"Id", "Position Name", "Description"};
	        tableModel.setColumnIdentifiers(headerTbl);
	        departmentTable.setModel(tableModel);
	        departmentTable.getColumnModel().getColumn(0).setPreferredWidth(3);
	        departmentTable.getColumnModel().getColumn(1).setPreferredWidth(100);
	        departmentTable.getColumnModel().getColumn(2).setPreferredWidth(250);
	    }
	    private void clearInputFields() {
	        txtDepartmentId.setText("");
	        txtDepartmentName.setText("");
	        txtDescription.setText("");
	        selectedRow = -1; 
	    }
}
